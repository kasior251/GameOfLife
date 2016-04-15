package controller;


import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.GameBoard;
import model.PatternFormatException;
import model.RuleSet;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameOfLife implements Initializable {

    private GameBoard gameBoard;
    private RuleSet rules;
    private boolean isRunning;
    private double speed;
    private int generation;
    private int cellSize;
    private double zoom;
    private AnimationTimer gameLoop;
    private boolean dragVal;
    private int dragX;
    private int dragY;
    private double canvasOffsetX;
    private double canvasOffsetY;
    private double zoomOffsetX;
    private double zoomOffsetY;
    private double blurStrength;


    @FXML private Button StartStopButton;
    @FXML private Text GenCounter;
    @FXML private Canvas CellCanvas;
    @FXML private Canvas GridCanvas;
    @FXML private Canvas BlurCanvas;
    @FXML private Slider SpeedSlider;
    @FXML private Slider ZoomSlider;


    public GameOfLife() {
        rules = new RuleSet();
        gameBoard = new GameBoard();
        cellSize = 2;
        canvasOffsetX = 0;
        canvasOffsetY = 0;
        blurStrength = 5;

        gameLoop = new GameLoop();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GaussianBlur backgroundBlur = new GaussianBlur();

        backgroundBlur.setRadius(blurStrength);

        BlurCanvas.setEffect(backgroundBlur);

        CellCanvas.getGraphicsContext2D().setFill(Color.ORANGE);
        BlurCanvas.getGraphicsContext2D().setFill(Color.ORANGE);

        GridCanvas.getGraphicsContext2D().setFill(Color.BLACK);
        GridCanvas.getGraphicsContext2D().fillRect(0, 0, cellSize * gameBoard.getColumns(), cellSize * gameBoard.getRows());

        draw(CellCanvas.getGraphicsContext2D());

        speed = SpeedSlider.getValue();
        zoom = ZoomSlider.getValue();
    }

    public void nextGeneration() {
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
        gc.clearRect(0, 0, gameBoard.getColumns()*cellSize, gameBoard.getRows()*cellSize);
        BlurCanvas.getGraphicsContext2D().clearRect(0, 0, gameBoard.getColumns()*cellSize, gameBoard.getRows()*cellSize);
        for (int i = 0; i < gameBoard.getColumns(); i++) {
            for (int j = 0; j < gameBoard.getRows(); j++) {
                if (gameBoard.getCell(i,j)) {
                    BlurCanvas.getGraphicsContext2D().fillRect(cellSize * i, cellSize * j, cellSize, cellSize);
                    gc.fillRect(cellSize * i, cellSize * j, cellSize, cellSize);
                }
            }
        }
    }

    @FXML protected void handleSpeedSlider() {
        speed = SpeedSlider.getValue();
    }

    @FXML protected void handleZoomSlider() {
        zoom = ZoomSlider.getValue();

        ((GaussianBlur)BlurCanvas.getEffect()).setRadius(blurStrength*zoom);

        this.zoomOffsetX = (1200-(1200*zoom))/2;
        this.zoomOffsetY = (600-(600*zoom))/2;

        BlurCanvas.getGraphicsContext2D().setTransform(zoom,0,0,zoom,canvasOffsetX*zoom+zoomOffsetX,canvasOffsetY*zoom+zoomOffsetY);
        CellCanvas.getGraphicsContext2D().setTransform(zoom,0,0,zoom,canvasOffsetX*zoom+zoomOffsetX,canvasOffsetY*zoom+zoomOffsetY);
        draw(CellCanvas.getGraphicsContext2D());
    }

    @FXML protected void handleGridEvent(MouseEvent event) {
        int xPos = (int)Math.floor((zoomOffsetX * -1 + event.getX()) / zoom-canvasOffsetX)/cellSize;
        int yPos = (int)Math.floor((zoomOffsetY * -1 + event.getY()) / zoom-canvasOffsetY)/cellSize;

        if (event.getButton() == MouseButton.PRIMARY) {
            if ((xPos >= 0) && (xPos < gameBoard.getColumns()) && (yPos >= 0) && (yPos < gameBoard.getRows())) {
                this.dragVal = gameBoard.getCell(xPos, yPos);
                gameBoard.toggleCell(xPos, yPos);
                if (dragVal) {
                    BlurCanvas.getGraphicsContext2D().clearRect(cellSize * xPos, cellSize * yPos, cellSize, cellSize);
                    CellCanvas.getGraphicsContext2D().clearRect(cellSize * xPos, cellSize * yPos, cellSize, cellSize);
                } else {
                    BlurCanvas.getGraphicsContext2D().fillRect(cellSize * xPos, cellSize * yPos, cellSize, cellSize);
                    CellCanvas.getGraphicsContext2D().fillRect(cellSize * xPos, cellSize * yPos, cellSize, cellSize);
                }
            }
        } else {
            this.dragX = (int) Math.floor(zoomOffsetX+event.getX()*zoom);
            this.dragY = (int) Math.floor(zoomOffsetY+event.getY()*zoom);
        }
    }

    @FXML protected void handleGridDragEvent(MouseEvent event) {
        int dragX = (int) Math.floor(zoomOffsetX+event.getX()*zoom);
        int dragY = (int) Math.floor(zoomOffsetY+event.getY()*zoom);

        if (event.getButton() == MouseButton.PRIMARY) {
            int xPos = (int)Math.floor((zoomOffsetX * -1 + event.getX()) / zoom-canvasOffsetX)/cellSize;
            int yPos = (int)Math.floor((zoomOffsetY * -1 + event.getY()) / zoom-canvasOffsetY)/cellSize;

            if (/*!(this.dragX == dragX && this.dragY == dragY) &&*/ (xPos >= 0) && (xPos < gameBoard.getColumns()) && (yPos >= 0) && (yPos < gameBoard.getRows())) {
                gameBoard.setCell(xPos, yPos, !dragVal);
                if (dragVal) {
                    BlurCanvas.getGraphicsContext2D().clearRect(cellSize * xPos, cellSize * yPos, cellSize, cellSize);
                    CellCanvas.getGraphicsContext2D().clearRect(cellSize * xPos, cellSize * yPos, cellSize, cellSize);
                } else {
                    BlurCanvas.getGraphicsContext2D().fillRect(cellSize * xPos, cellSize * yPos, cellSize, cellSize);
                    CellCanvas.getGraphicsContext2D().fillRect(cellSize * xPos, cellSize * yPos, cellSize, cellSize);
                }

            }
        } else {
/*            if (!(this.dragX == dragX && this.dragY == dragY) && (dragX >= 0) && (dragX < gameBoard.getColumns()) && (dragY >= 0) && (dragY < gameBoard.getRows())) {
                System.out.println((dragX-this.dragX)*zoom);
                System.out.println((dragY-this.dragY)*zoom);*/

                BlurCanvas.getGraphicsContext2D().setTransform(zoom,0,0,zoom,canvasOffsetX*zoom+zoomOffsetX,canvasOffsetY*zoom+zoomOffsetY);
                CellCanvas.getGraphicsContext2D().setTransform(zoom,0,0,zoom,canvasOffsetX*zoom+zoomOffsetX,canvasOffsetY*zoom+zoomOffsetY);
                draw(CellCanvas.getGraphicsContext2D());
            canvasOffsetX += ((dragX-this.dragX)/zoom)/zoom;
            canvasOffsetY += ((dragY-this.dragY)/zoom)/zoom;
            System.out.println(canvasOffsetX);
            System.out.println(canvasOffsetY);
            this.dragX = dragX;
            this.dragY = dragY;
            //}
        }
    }


    @FXML protected void handleResetButton() {
        gameLoop.stop();
        isRunning = false;
        StartStopButton.setText("▶");
        generation = 0;
        GenCounter.setText(Integer.toString(generation));

        gameBoard = new GameBoard(600,300);
        draw(CellCanvas.getGraphicsContext2D());
    }

    @FXML protected void handleStepButton() {
        nextGeneration();
    }

    @FXML protected void handleToggleStartButton() {
        if (isRunning) {
            StartStopButton.setText("\u23F5");
            isRunning = false;
            gameLoop.stop();
        } else {
            StartStopButton.setText("\u23F8");
            isRunning = true;
            gameLoop.start();
        }
    }

    @FXML protected void handleImportFile() {
        try {
            GameBoardImporter importer = new GameBoardImporter(SpeedSlider);

            if (importer.gameBoard != null) {
                gameBoard.addBoard(importer.gameBoard);
            } else {
                System.out.println("Importer.gameboard = null: FileformatException?");
            }

            if (importer.ruleSet != null) {
                rules = new RuleSet(importer.ruleSet);
            }

            draw(CellCanvas.getGraphicsContext2D());
        } catch (IOException e) {
            System.out.println("IOException in instantiation of GameBoardImporter in GameOfLife");
        } catch (PatternFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML protected void handleChangeRules() {
        RuleEditor ruleHandler = new RuleEditor(rules.getRules());
        ruleHandler.showAndWait();

        rules = new RuleSet(ruleHandler.rules);
    }

    @FXML protected void handleResetRules() {
        rules.resetRules();
    }

    private class GameLoop extends AnimationTimer {
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
    }
}