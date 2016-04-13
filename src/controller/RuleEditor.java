package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Consilium on 03.03.2016.
 */
public class RuleEditor extends Stage {

    @FXML
    protected GridPane grid;

    public boolean[][] rules = new boolean[2][9];

    @FXML protected void handleOk() {
        handleApply();
        handleCancel();
    }

    @FXML protected void handleCancel() {
        close();
    }

    @FXML protected void handleApply() {
        int y,x;
        for (Node cbox : grid.getChildren()) {
            if (GridPane.getColumnIndex(cbox) == null) {
                x = 0;
            } else {
                x = GridPane.getColumnIndex(cbox);
            }
            if (GridPane.getRowIndex(cbox) == null) {
                y = 0;
            } else {
                y = GridPane.getRowIndex(cbox);
            }

            rules[y][x] = ((CheckBox)cbox).isSelected();
        }
    }

    public RuleEditor() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/RuleDialog.fxml"));
        loader.setController(this);

        setTitle("Customize the Game of Life Ruleset");

        try
        {
            setScene(new Scene(loader.load()));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("Error when loading RuleEditor FXML scene");
        }
    }
}
