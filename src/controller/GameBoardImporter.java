package controller;

import javafx.scene.Node;
import javafx.stage.FileChooser;
import model.*;

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

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
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
        String parameters = " ";
        List<String> comments = new ArrayList<String>();
        int paramLineCounter = 0;
        int eofCharCounter = 0;

        while ((line = file.readLine()) != null) {
            if (line.matches("^[#](.*)$")) {
                //This matches every comment line. Posd ibly adjust for #r later?
                comments.add(line);
            } else {
                line = line.replaceAll("\\s","").toLowerCase();
                if (line.matches("^(.*)(x|y|rule)(.*)$")) {
                    parameters = line;
                    paramLineCounter++;
                } else if (line.matches("((.)*[!](.)+)")) {
                    //This matches lines with exlamation marks(!) with trailing characters
                    file.close();
                    throw new PatternFormatException("Characters detected after supposed end of pattern");
                } else if (line.matches("(.*)[!](.*)")) {
                    //This matches every pattern line - lines with a $ or ! in it.
                    patternString.append(line);
                    eofCharCounter++;
                } else if (line.matches("(.*)[\\$b](.*)")) {
                    //This matches every pattern line - lines with a $ or ! in it.
                    patternString.append(line);
                } else {
                    if (line.matches(".")) {
                        file.close();
                        throw new PatternFormatException("Characters detected after supposed end of pattern");
                    }
                }
            }
        }

        file.close();

        //Throw an exception if the following is true:
        // - More than one line with an end of pattern char(!) exists
        // - More than one parameter/rule line exists
        // - Use Regex to check for incorrect parameter line syntax
        if(eofCharCounter != 1 || paramLineCounter != 1 || !(parameters.matches("^(x=[0-9]+,y=[0-9]+(,rule=(([a-zA-Z][0-8]{1,9})|([bB][0-8]{1,9}\\/[ac-zAC-Z][0-8]{1,9})|([ac-zAC-Z][0-8]{1,9}\\/[bB][0-8]{1,9})))?$)"))) {
            throw new PatternFormatException("Invalid format in .RLE file!");
        }

        if (!(parameters.equals(""))) {
            parseParameters(parameters);
        }

        if (!(patternString.equals(""))) {
            try {
                parsePattern(patternString);
            }
            catch (TooLargePatternException e) {
                new ErrorMessage("The pattern is too large");
            }
            catch (ArrayIndexOutOfBoundsException e) {
                new ErrorMessage("The file is corrupted, the bounding box doesn't match the pattern size");
            }
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

                ruleSet = rule_array;
            }
        }
    }

    public void parsePattern(StringBuilder patternSB) throws PatternFormatException, TooLargePatternException {
        String patternStr = patternSB.toString();
        if ((patternStr.matches("((.)*[!](.)+)"))) {
            throw new PatternFormatException("Characters detected after supposed end of pattern.");
        }
        String[] pattern = patternStr.split("(?<=[^0-9])");
        gameBoard = new boolean[rows][columns];
        int y = 0;
        int x = 0;
        boolean[] gameBoardRow = new boolean[columns];
        for (String pattern_row : pattern) {
            String token = pattern_row.substring(pattern_row.length() - 1, pattern_row.length());
            String countStr = pattern_row.substring(0, pattern_row.length() - 1);
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
