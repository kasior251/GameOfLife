package test;

import junit.framework.TestCase;
import model.GameBoard;
import org.junit.Test;

/**
 * Created by Consilium on 16.02.2016.
 */
public class GameBoardTest extends TestCase {
    GameBoard gB;
    @Test
    public void testSetBoard() throws Exception {

    }

    @Test
    public void testGetColumns() throws Exception {

    }

    @Test
    public void testCountNeighbours() throws Exception {
        boolean[][] array = {{true,true,false},{false,true,false},{true,true,false}};
        gB = new GameBoard(3,3);
        gB.setBoard(array);
        assertEquals(4,gB.countNeighbours(1,1));
    }

    @Test
    public void testGetRows() throws Exception {

    }
}