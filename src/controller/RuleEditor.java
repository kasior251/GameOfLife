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

    public boolean[][] rules;

    public RuleEditor(boolean[][] currentRules) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/RuleDialog.fxml"));
        loader.setController(this);
        this.rules = currentRules;

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

        loadRules();
    }

    private void loadRules() {
        for (int state = 0; state < 2; state++) {
            for (int neighbours = 0; neighbours < 9; neighbours++) {
/*                System.out.println(currentRules[state][neighbours]);
                System.out.println(state+"  "+neighbours);*/
                ((CheckBox)grid.getChildren().toArray()[neighbours+9*state]).setSelected(rules[state][neighbours]);
            }
        }
    }

    @FXML protected void handleOk() {
        handleApply();
        handleCancel();
    }

    @FXML protected void handleCancel() {
        close();
    }

    @FXML protected void handleApply() {
        for (int state = 0; state < 2; state++) {
            for (int neighbours = 0; neighbours < 9; neighbours++) {
                rules[state][neighbours] = ((CheckBox)grid.getChildren().toArray()[neighbours+9*state]).isSelected();
            }
        }
    }
}
