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

    // - - - - Branch test - - - -
    public class GoLController {

    @FXML private Button testbutton;
    @FXML private Text testtext;
    @FXML private Canvas gridcanvas;


    @FXML protected void handleSubmitButtonAction(MouseEvent event) {
        if (testtext.getText() == "Test") {
            testtext.setText("Not");
        } else {
            testtext.setText("Test");
        }
    }


    @FXML protected void handleStepButton(ActionEvent event) {
        GraphicsContext gc = gridcanvas.getGraphicsContext2D();


        List<Cell> cellList = new ArrayList<Cell>();

        for(int i=0; i<100; i++){
            cellList.add(new Cell((int)(Math.random() * 100),(int)(Math.    random() * 100)));
        }

        gc.setFill(Color.YELLOW.brighter());

        for (Cell p : cellList) {
            gc.fillRect(10*p.getPosX(), 10*p.getPosY(), 10, 10);
        }

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

}