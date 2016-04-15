package test;

import junit.framework.TestCase;
import model.GameBoard;
import org.junit.Test;

/**
 * Created by Consilium on 16.02.2016.
 */
public class GameBoardTest extends TestCase {
    protected GameBoard gB = new GameBoard(1200, 600);
    protected GameBoard gB1;

    protected void setUp() {
        gB.setBoard(new boolean[10][20]);

    }

    @Test
    public void testSetBoard() throws Exception {
        boolean[][] table = new boolean[10][20];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                assertTrue(gB.getBoard()[i][j] == table[i][j]);
            }
        }

    }

    @org.junit.Test
    public void testGetColumns() throws Exception {
        int col = gB.getColumns();
        assertTrue(col == 10);

    }

    @org.junit.Test
    public void testCountNeighbours() throws Exception {
        boolean[][] array = {{true,true,false},{false,true,false},{true,true,false}};
        gB1 = new GameBoard(3,3);
        gB1.setBoard(array);
        assertEquals(4,gB1.countNeighbours(1,1));
    }

    @org.junit.Test
    public void testGetRows() throws Exception {
        int row = gB.getRows();
        assertTrue(row == 20);


    }
}