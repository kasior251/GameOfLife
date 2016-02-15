package controller;


import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.GameBoard;
import model.RuleSet;

import java.net.URL;
import java.util.ResourceBundle;

public class GameOfLife implements Initializable {

    private GameBoard gameBoard;
    private RuleSet rules;
    private boolean isRunning = false;
    private double speed;
    private int cellSize;
    private AnimationTimer gameLoop;

    @FXML private Button StartStopButton;
    @FXML private Text GenCounter;
    @FXML private Canvas GridCanvas;
    @FXML private Slider SpeedSlider;

    public GameOfLife() {
        rules = new RuleSet();
        gameBoard = new GameBoard(100, 150);
        cellSize = 10;

        gameLoop = new AnimationTimer() {
            long lastTimer = System.currentTimeMillis();

            @Override
            public void handle(long now) {
                long currentTimer = System.currentTimeMillis();

                long tickLength = (long) ((speed*-1)+500);

                if ((currentTimer - lastTimer) > tickLength) {
                    nextGeneration();
                    lastTimer = currentTimer;
                }
            }
        };

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        draw(GridCanvas.getGraphicsContext2D());

        speed = SpeedSlider.getValue();
    }

    public void nextGeneration() {
        boolean[][] tempBoard = new boolean[gameBoard.getColumns()][gameBoard.getRows()];
        for (int i = 0; i < gameBoard.getColumns(); i++) {
            for (int j = 0; j < gameBoard.getRows(); j++) {
                tempBoard[i][j] = rules.nextState(gameBoard.getBoard()[i][j], gameBoard.countNeighbours(i, j));
            }
        }
        gameBoard.setBoard(tempBoard);
        draw(GridCanvas.getGraphicsContext2D());

    }

    public void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, gameBoard.getColumns()*cellSize, gameBoard.getRows()*cellSize);
        for (int i = 0; i < gameBoard.getColumns(); i++) {
            for (int j = 0; j < gameBoard.getRows(); j++) {
                gc.setStroke(Color.DARKGRAY);
                gc.strokeRect(cellSize * i, cellSize * j, cellSize, cellSize);
                gc.setStroke(Color.TRANSPARENT);
                if (gameBoard.getBoard()[i][j]) {
                    gc.fillRect(cellSize * i, cellSize * j, cellSize, cellSize);
                }

            }
        }


    }

    @FXML protected void handleSpeedSlider() {
        speed = SpeedSlider.getValue();
    }


    @FXML protected void handleResetButton(ActionEvent event) {
        GridCanvas.getGraphicsContext2D().clearRect(0, 0, gameBoard.getColumns()*cellSize, gameBoard.getRows()*cellSize);
        boolean[][] tempBoard = new boolean[gameBoard.getColumns()][gameBoard.getRows()];
        gameBoard.setBoard(tempBoard);
    }

    @FXML protected void handleStepButton(ActionEvent event) {
        nextGeneration();
    }

    @FXML protected void handleToggleStartButton(ActionEvent event) {
        if (isRunning) {
            StartStopButton.setText("▶");
            isRunning = false;
            gameLoop.stop();
        } else {
            StartStopButton.setText("❚❚");
            isRunning = true;
            gameLoop.start();
        }
    }


}