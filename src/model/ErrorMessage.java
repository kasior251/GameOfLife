package model;

import javafx.scene.control.Alert;

/**
 * Created by kasia on 14.04.2016.
 */
public class ErrorMessage {

   private Alert alert;


    public ErrorMessage(String title, String errorText) {
        alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(errorText);
    }

    public void show() {
        try {
            alert.showAndWait();
        }
        catch (Exception e) {
            alert.show();
        }
    }

}
