package controller;

import javafx.scene.Node;
import javafx.stage.FileChooser;
import model.GameBoard;
import model.RuleSet;
import java.io.File;

/**
 * Created by Consilium on 24.02.2016.
 */
public class GameBoardImporter {
    public RuleSet ruleSet;
    public GameBoard gameBoard;

    public GameBoardImporter(Node node) {
        readFromFile(node);
    }

    public void readFromFile(Node node) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Gameboard pattern file (.rle)");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("RLE files", "*.rle")
        );
        File selectedFile = fileChooser.showOpenDialog(node.getScene().getWindow());
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public RuleSet ruleSet() {
        return ruleSet;
    }
}
