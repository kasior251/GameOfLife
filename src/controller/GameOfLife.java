package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.GameBoard;
import model.RuleSet;

import java.net.URL;
import java.util.ResourceBundle;

public class GameOfLife implements Initializable {

    private GameBoard gameBoard;
    private RuleSet rules;
    private boolean isRunning;
    private double speed;
    private int cellSize;

    @FXML private Button testbutton;
    @FXML private Text testtext;
    @FXML private Canvas gridcanvas;

    public GameOfLife() {
        rules = new RuleSet();
        gameBoard = new GameBoard(100, 150);
        cellSize = 10;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        draw(gridcanvas.getGraphicsContext2D());
    }

    public void nextGeneration() {
        boolean[][] tempBoard = new boolean[gameBoard.getColumns()][gameBoard.getRows()];
        for (int i = 0; i < gameBoard.getColumns(); i++) {
            for (int j = 0; j < gameBoard.getRows(); j++) {
                tempBoard[i][j] = rules.nextState(gameBoard.getBoard()[i][j], gameBoard.countNeighbours(i, j));
            }
        }
        gameBoard.setBoard(tempBoard);
        draw(gridcanvas.getGraphicsContext2D());

    }

    public void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, gameBoard.getColumns()*cellSize, gameBoard.getRows()*cellSize);
        for (int i = 0; i < gameBoard.getColumns(); i++) {
            for (int j = 0; j < gameBoard.getRows(); j++) {
                if (gameBoard.getBoard()[i][j]) {
                    gc.fillRect(cellSize * i, cellSize * j, cellSize, cellSize);
                }

            }
        }


    }


    @FXML
    protected void handleSubmitButtonAction(MouseEvent event) {

    }


    @FXML protected void handleStepButton(ActionEvent event) {
        nextGeneration();
    }

}