package model;

/**
 * Created by Benjamin on 25.01.2016.
 */
public class Cell {

    private final int posX;
    private final int posY;

    public Cell(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }


    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
