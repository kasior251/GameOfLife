package GoL.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

/**
 * Created by Benjamin on 25.01.2016.
 */
public class Cell extends Rectangle {

    public boolean active;

    public Cell(double x, double y, double width, double height, boolean active) {
        super(width, height);
        super.setX(x);
        super.setY(y);
        super.setStrokeType(StrokeType.INSIDE);
        super.setStrokeWidth(1);
        this.active = active;
        if (active == true) {
            super.setFill(Color.YELLOW);
            //super.setStroke(Color.YELLOW.deriveColor(1, 1, 1, 0.5));
        } else {
            super.setFill(Color.TRANSPARENT);
            super.setStroke(Color.TRANSPARENT);
        }

        super.setOnMousePressed(event -> {
            toggleState();
            /*if (this.getFill() == Color.TRANSPARENT) {
                this.setFill(Color.YELLOW);
                this.setStroke(Color.BLACK);
                this.active = true;
            } else {
                this.setFill(Color.TRANSPARENT);
                this.setStroke(Color.TRANSPARENT);
                this.active = false;
            }*/
        });
    }

    public void toggleState() {
        if (this.getFill() == Color.TRANSPARENT) {
            activate();
        } else {
            deactivate();
        }
    }

    public void activate() {
        this.setFill(Color.YELLOW);
        this.setStroke(Color.BLACK);
        this.active = true;
    }

    public void deactivate() {
        this.setFill(Color.TRANSPARENT);
        this.setStroke(Color.TRANSPARENT);
        this.active = false;
    }

}