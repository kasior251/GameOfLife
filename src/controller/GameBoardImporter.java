package controller;

import javafx.scene.Node;
import javafx.stage.FileChooser;
import model.GameBoard;
import model.PatternFormatException;
import model.RuleSet;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Consilium on 24.02.2016.
 */
public class GameBoardImporter {
    public boolean[][] ruleSet;
    public boolean[][] gameBoard;
    private int rows;
    private int columns;


    public GameBoardImporter(Node node) throws IOException, PatternFormatException {
        readFromFile(node);
    }

    public void readFromFile(Node node) throws IOException, PatternFormatException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Gameboard pattern file (.rle, .sof)");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("RLE files", "*.rle"),
                new FileChooser.ExtensionFilter("SOF files", "*.sof")
        );
        File selectedFile = fileChooser.showOpenDialog(node.getScene().getWindow());

        if (selectedFile != null) {
            readGameBoard(new FileReader(selectedFile));
        }
    }

    public void readGameBoard(Reader r) throws IOException, PatternFormatException {
        BufferedReader file = new BufferedReader(r);

        String line;
        StringBuilder patternString = new StringBuilder();
        String parameters = "";
        List<String> comments = new ArrayList<String>();
        int paramLineCounter = 0;

        while ((line = file.readLine()) != null) {
            if (line.matches("^[#](.*)$")) {
                //This matches every comment line. Posd ibly adjust for #r later?
                comments.add(line);
            } else if (line.matches("^(.*)(x|y|rule)(.*)$")) {
                //^(x=[0-9]+,y=[0-9]+(,rules=(([a-zA-Z][0-8]{1,9})|([bB][0-8]{1,9}\/[ac-zAC-Z][0-8]{1,9})|([ac-zAC-Z][0-8]{1,9}\/[bB][0-8]{1,9})))?$)
                parameters = line.replaceAll("\\s","").toLowerCase();
                System.out.println(parameters);
                paramLineCounter++;
            } else if (line.matches("(.*)[\\$!b](.*)")) {
                //This matches every pattern line - lines with a $ or ! in it.
                patternString.append(line);
            } else {
                System.out.println("Unknown line: "+line);
            }
        }

        file.close();
        System.out.println(paramLineCounter);
        System.out.println(parameters.matches("^(x=[0-9]+,y=[0-9]+(,rules=(([a-zA-Z][0-8]{1,9})|([bB][0-8]{1,9}\\/[ac-zAC-Z][0-8]{1,9})|([ac-zAC-Z][0-8]{1,9}\\/[bB][0-8]{1,9})))?$)"));
        if(paramLineCounter != 1 || !(parameters.matches("^(x=[0-9]+,y=[0-9]+(,rules=(([a-zA-Z][0-8]{1,9})|([bB][0-8]{1,9}\\/[ac-zAC-Z][0-8]{1,9})|([ac-zAC-Z][0-8]{1,9}\\/[bB][0-8]{1,9})))?$)"))) {
            throw new PatternFormatException("Invalid format in .RLE file!");
        }

        if (!(parameters.equals(""))) {
            parseParameters(parameters);
        }

        if (!(patternString.equals(""))) {
            parsePattern(patternString);
        }


        System.out.println("File comments: ");
        for (String comment : comments) {
            System.out.println(comment);
        }
    }

    public void parseParameters(String parameters) {
        String[] param_arr = parameters.split(",");

        for (String parameter : param_arr) {
            String parameter_name = parameter.split("=")[0].toLowerCase();
            String parameter_value = parameter.split("=")[1].toLowerCase();

            if (parameter_name.equals("x")) {
                //Handling of x parameter
                this.columns = Integer.parseInt(parameter_value);
            } else if (parameter_name.equals("y")) {
                //Handling of y parameter
                this.rows = Integer.parseInt(parameter_value);
            } else if (parameter_name.equals("rule")) {
                //Rule handling
                boolean[][] rule_array = new boolean[2][9];

                String[] rules = parameter_value.split("/");
                for (String rule: rules) {
                    char[] ruleCharArray = rule.toCharArray();
                    Character currentState = ruleCharArray[0];
                    char[] neighbours = Arrays.copyOfRange(ruleCharArray,1,ruleCharArray.length);
                    for (Character ch : neighbours) {
                        rule_array[(currentState.equals('b')) ? 0 : 1][Integer.parseInt(ch.toString())] = true;
                    }
                }

                //ruleSet = new RuleSet(rule_array); Best way?
                ruleSet = rule_array;
            }
        }
    }

    public void parsePattern(StringBuilder patternSB) {
        String[] pattern = patternSB.toString().split("(?<=[^0-9])");
        gameBoard = new boolean[rows][columns];
        int y = 0;
        int x = 0;
        boolean[] gameBoardRow = new boolean[columns];
        for (String pattern_row : pattern) {
            String token = pattern_row.substring(pattern_row.length()-1,pattern_row.length());
            String countStr = pattern_row.substring(0,pattern_row.length()-1);
            int count = 1;

            if (countStr.length() > 0) {
                count = Integer.parseInt(countStr);
            }

            for (int i = 0; i < count; i++) {
                if (token.equals("b")) {
                    gameBoardRow[x] = false;
                    x++;
                } else if (token.equals("$") || token.equals("!")) {
                    gameBoard[y] = gameBoardRow;
                    gameBoardRow = new boolean[columns];
                    x = 0;
                    y++;
                } else {
                    gameBoardRow[x] = true;
                    x++;
                }
            }

        }
    }

    public boolean[][] getGameBoard() {
        return gameBoard;
    }

    public boolean[][] getRuleSet() {
        return ruleSet;
    }
}
