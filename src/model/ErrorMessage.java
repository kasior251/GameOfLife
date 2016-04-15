package model;

import javafx.scene.control.Alert;

/**
 * Created by kasia on 14.04.2016.
 */
public class ErrorMessage {

   private Alert alert;


    public ErrorMessage(String errorText) {
        alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText(errorText);
        alert.show();
    }
}
