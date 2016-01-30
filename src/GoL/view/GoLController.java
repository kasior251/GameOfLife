package GoL.view;

import GoL.Main;
import GoL.model.Cell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import GoL.model.GameBoard;

public class GoLController {

    @FXML private Button testbutton;
    @FXML private Text testtext;
    //@FXML private Canvas gridcanvas;
    @FXML private Pane improv_canvas;
    private Main mainApp;
    private GameBoard current_board;
    private GameBoard next_board;

    @FXML
    private void initialize() {
        for (int y=0;y<112;y++) {
            for (int x=0;x<160;x++) {
                mainApp.squares.add(new Cell((x * 5), (y * 5), 5, 5, false));
            }
        }

        initializeGameBoard();

        improv_canvas.getChildren().addAll(mainApp.squares);

        Scale test = new Scale();
        improv_canvas.getTransforms().addAll(test);
        improv_canvas.setStyle("-fx-background-color: darkgray");

    }

    @FXML protected void handleSubmitButtonAction(MouseEvent event) {
        if (testtext.getText() == "Test") {
            testtext.setText("Not");
        } else {
            testtext.setText("Test");
        }
    }


    @FXML protected void handleStepButton(ActionEvent event) {
        nextIteration();
    }

    public void initializeGameBoard() {
        this.createGameBoard();
        this.nextIteration();
    }

    public void nextIteration() {
        this.createGameBoard();
        for(int i=0;i<mainApp.squares.size();i++) {
            Cell current_cell = mainApp.squares.get(i);
            int x = (int)current_cell.getX()/5;
            int y = (int)current_cell.getY()/5;
            //System.out.println(x+" "+y);
            byte board_cell = next_board.getBoard()[y][x];
            if (board_cell == 1) {
                current_cell.activate();
            }
        }
    }

    public void createGameBoard() {
        this.next_board = new GameBoard();
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

}