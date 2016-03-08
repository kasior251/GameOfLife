package controller;


import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.GameBoard;
import model.RuleSet;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameOfLife implements Initializable {

    private GameBoard gameBoard;
    private RuleSet rules;
    private boolean isRunning = false;
    private double speed;
    private int generation;
    private int cellSize;
    private AnimationTimer gameLoop;
    private boolean dragVal;
    private int dragX;
    private int dragY;


    @FXML private Button StartStopButton;
    @FXML private Text GenCounter;
    @FXML private Canvas CellCanvas;
    @FXML private Canvas GridCanvas;
    @FXML private Canvas BlurCanvas;
    @FXML private Slider SpeedSlider;
    @FXML private MenuItem importerButton;

    public GameOfLife() {
        rules = new RuleSet();
        gameBoard = new GameBoard();
        cellSize = 2;

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
        GaussianBlur backgroundBlur = new GaussianBlur();
//        backgroundBlur.setRadius(10);
        backgroundBlur.setRadius(15);
        BlurCanvas.setEffect(backgroundBlur);

        CellCanvas.getGraphicsContext2D().setFill(Color.GREENYELLOW);
//        CellCanvas.setRotate(45.0);
        BlurCanvas.getGraphicsContext2D().setFill(Color.GREENYELLOW);
//        BlurCanvas.setRotate(45.0);
        GridCanvas.getGraphicsContext2D().setFill(Color.BLACK);
        GridCanvas.getGraphicsContext2D().fillRect(0, 0, cellSize * gameBoard.getColumns(), cellSize * gameBoard.getRows());

//        BlurCanvas.setScaleX(1.5);
//        BlurCanvas.setScaleY(1.5);
//        CellCanvas.setScaleX(1.5);
//        CellCanvas.setScaleY(1.5);

        draw(CellCanvas.getGraphicsContext2D());

        speed = SpeedSlider.getValue();
    }

    public void nextGeneration() {
/*        if (generation == 47) {
            rules.setRule(false, 6, true);
            rules.setRule(false, 1, false);
            rules.setRule(false, 2, false);
        }*/

/*        if ((generation % 20) < 15) {
            System.out.println("True: "+generation);
            rules.setRule(false, 1, true);
            rules.setRule(true, 1, true);
            rules.setRule(false, 2, true);
            rules.setRule(false, 3, true);
            rules.setRule(false, 6, true);
        } else {
            System.out.println("False: "+generation);
            rules.setRule(false, 1, false);
            rules.setRule(true, 1, false);
            rules.setRule(false, 2, false);
            rules.setRule(false, 3, false);
            rules.setRule(false, 6, false);
        }*/

        generation++;
        GenCounter.setText(Integer.toString(generation));

        boolean[][] nextBoard = new boolean[gameBoard.getColumns()][gameBoard.getRows()];
        for (int i = 0; i < gameBoard.getColumns(); i++) {
            for (int j = 0; j < gameBoard.getRows(); j++) {
                nextBoard[i][j] = rules.nextState(gameBoard.getCell(i,j), gameBoard.countNeighbours(i, j));
            }
        }
        gameBoard.setBoard(nextBoard);
        draw(CellCanvas.getGraphicsContext2D());
    }

    public void draw(GraphicsContext gc) {
        if (generation == 225) {
            BlurCanvas.getGraphicsContext2D().setFill(Color.WHITE);
            CellCanvas.getGraphicsContext2D().setFill(Color.WHITE);
        }
        gc.clearRect(0, 0, gameBoard.getColumns()*cellSize, gameBoard.getRows()*cellSize);
        BlurCanvas.getGraphicsContext2D().clearRect(0, 0, gameBoard.getColumns()*cellSize, gameBoard.getRows()*cellSize);
        for (int i = 0; i < gameBoard.getColumns(); i++) {
            for (int j = 0; j < gameBoard.getRows(); j++) {
                if (gameBoard.getCell(i,j)) {
                    BlurCanvas.getGraphicsContext2D().fillRect(cellSize * i, cellSize * j, cellSize, cellSize);
                    gc.fillRect(cellSize * i, cellSize * j, cellSize, cellSize);
                }

                /*gc.setStroke(Color.DARKGRAY);
                gc.strokeRect(cellSize * i, cellSize * j, cellSize, cellSize);
                gc.setStroke(Color.TRANSPARENT);*/
            }
        }
    }

    @FXML protected void handleSpeedSlider() {
        speed = SpeedSlider.getValue();
    }

    @FXML protected void handleGridEvent(MouseEvent event) {
        int xPos = (int) Math.floor(event.getX()/cellSize);
        int yPos = (int) Math.floor(event.getY()/cellSize);
        if ((xPos >= 0) && (xPos < gameBoard.getColumns()) && (yPos >= 0) && (yPos < gameBoard.getRows())) {
            this.dragVal = gameBoard.getCell(xPos,yPos);
            gameBoard.toggleCell(xPos,yPos);
            draw(CellCanvas.getGraphicsContext2D());
        }
    }

    @FXML protected void handleGridDragEvent(MouseEvent event) {
        int dragX = (int) Math.floor(event.getX()/cellSize);
        int dragY = (int) Math.floor(event.getY()/cellSize);
        if (!(this.dragX == dragX && this.dragY == dragY) && (dragX >= 0) && (dragX < gameBoard.getColumns()) && (dragY >= 0) && (dragY < gameBoard.getRows())) {
            this.dragX = dragX;
            this.dragY = dragY;
            gameBoard.setCell(dragX, dragY, !dragVal);
            draw(CellCanvas.getGraphicsContext2D());
        }
    }


    @FXML protected void handleResetButton() {
        gameLoop.stop();
        isRunning = false;
        StartStopButton.setText("▶");
        generation = 0;
        GenCounter.setText(Integer.toString(generation));

        gameBoard = new GameBoard(400,300);
        draw(CellCanvas.getGraphicsContext2D());
    }

    @FXML protected void handleStepButton() {
        nextGeneration();
    }

    @FXML protected void handleToggleStartButton() {
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

    @FXML protected void handleImportFile() {
        try {
            GameBoardImporter importer = new GameBoardImporter(SpeedSlider);

            rules = new RuleSet(importer.ruleSet);
            gameBoard.setBoard(importer.gameBoard);
            draw(CellCanvas.getGraphicsContext2D());
        } catch (IOException e) {
            System.out.println("IOException in instantiation of GameBoardImporter in GameOfLife");
        }
    }

    @FXML protected void handleChangeRules() {
        RuleEditor ruleHandler = new RuleEditor();
        ruleHandler.showAndWait();

        rules = new RuleSet(ruleHandler.rules);
    }
}