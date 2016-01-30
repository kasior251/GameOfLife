package GoL;

import GoL.model.Cell;
import GoL.view.GoLController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    public static List<Cell> squares = new ArrayList<Cell>();

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/GoL.fxml"));
        AnchorPane root = (AnchorPane) loader.load();

        GoLController controller = loader.getController();
        controller.setMainApp(this);

        Scene scene = new Scene(root, 800, 600);

        stage.setTitle("Game of Life - Alpha 0.1.1");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
