package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GoLController {

    @FXML
    private Button testbutton;
    @FXML
    private Text testtext;
    @FXML
    private Canvas gridcanvas;

    private Random r;


    @FXML
    protected void handleSubmitButtonAction(MouseEvent event) {
        if (testtext.getText() == "Test") {
            testtext.setText("Not");
        } else {
            testtext.setText("Test");
        }
    }


    @FXML
    protected void handleStepButton(ActionEvent event) {
        GraphicsContext gc = gridcanvas.getGraphicsContext2D();

        r = new Random();

        byte[][] board = new byte[100][100];

        draw(gc, board);

       /* for (Cell p : cellList) {
            gc.beginPath();
            gc.moveTo(10*p.getPosX(), 10*p.getPosY());
            gc.lineTo(10*(p.getPosX()+1), 10*p.getPosY());
            gc.lineTo(10*(p.getPosX()+1), 10*(p.getPosY()+1));
            gc.lineTo(10*(p.getPosX()), 10*(p.getPosY()+1));
            gc.lineTo(10*p.getPosX(), 10*p.getPosY());
            gc.fill();
            gc.stroke();
            gc.closePath();
        }*/

    }

    private void draw(GraphicsContext gc, byte[][] board ) {
        gc.setFill(Color.YELLOW.brighter());

/*        for (byte[] boardY : board) {
            for (byte boardX : boardY) {

                System.out.println(boardX);
            }
        }*/

        for (int y = 0; y<board.length;y++) {
            for (int x = 0; x<board[y].length;x++) {

                board[y][x] = (byte)(r.nextFloat()+0.1);

                if (board[y][x] == 1) {
                    drawsquare(gc,x,y);
                }
            }

        }

    }

    private void drawsquare(GraphicsContext gc, int x, int y) {
        gc.fillRect(10 * x, 10 * y, 10, 10);
    }

}