package model;

import java.util.Arrays;

public class GameBoard {

    private boolean[][] board;
    private int columns;
    private int rows;

    public GameBoard() {
        this(1200, 600);
        int xOffset = (int)Math.floor((this.columns - 80)/2);
        int yOffset = (int)Math.floor((this.rows - 50)/2);

        board[xOffset+13][yOffset+18] = true;
        board[xOffset+14][yOffset+18] = true;
        board[xOffset+14][yOffset+19] = true;
        board[xOffset+14][yOffset+20] = true;
        board[xOffset+15][yOffset+21] = true;
        board[xOffset+16][yOffset+20] = true;
        board[xOffset+16][yOffset+21] = true;

        board[xOffset+13][yOffset+34] = true;
        board[xOffset+14][yOffset+34] = true;
        board[xOffset+14][yOffset+33] = true;
        board[xOffset+14][yOffset+32] = true;
        board[xOffset+15][yOffset+31] = true;
        board[xOffset+16][yOffset+32] = true;
        board[xOffset+16][yOffset+31] = true;

        board[xOffset+24][yOffset+22] = true;
        board[xOffset+24][yOffset+23] = true;
        board[xOffset+24][yOffset+24] = true;
        board[xOffset+24][yOffset+28] = true;
        board[xOffset+24][yOffset+29] = true;
        board[xOffset+24][yOffset+30] = true;

        board[xOffset+25][yOffset+22] = true;
        board[xOffset+25][yOffset+25] = true;
        board[xOffset+25][yOffset+27] = true;
        board[xOffset+25][yOffset+30] = true;

        board[xOffset+26][yOffset+22] = true;
        board[xOffset+26][yOffset+30] = true;

        board[xOffset+28][yOffset+23] = true;
        board[xOffset+28][yOffset+29] = true;

        board[xOffset+29][yOffset+24] = true;
        board[xOffset+29][yOffset+25] = true;
        board[xOffset+29][yOffset+27] = true;
        board[xOffset+29][yOffset+28] = true;

        board[xOffset+38][yOffset+20] = true;
        board[xOffset+38][yOffset+21] = true;
        board[xOffset+39][yOffset+20] = true;
        board[xOffset+39][yOffset+21] = true;
        board[xOffset+38][yOffset+32] = true;
        board[xOffset+38][yOffset+31] = true;
        board[xOffset+39][yOffset+32] = true;
        board[xOffset+39][yOffset+31] = true;

        board[xOffset+48][yOffset+24] = true;
        board[xOffset+49][yOffset+24] = true;
        board[xOffset+48][yOffset+28] = true;
        board[xOffset+49][yOffset+28] = true;

        board[xOffset+52][yOffset+21] = true;
        board[xOffset+52][yOffset+22] = true;
        board[xOffset+52][yOffset+24] = true;
        board[xOffset+52][yOffset+28] = true;
        board[xOffset+52][yOffset+30] = true;
        board[xOffset+52][yOffset+31] = true;

        board[xOffset+53][yOffset+22] = true;
        board[xOffset+53][yOffset+23] = true;
        board[xOffset+53][yOffset+24] = true;
        board[xOffset+53][yOffset+28] = true;
        board[xOffset+53][yOffset+29] = true;
        board[xOffset+53][yOffset+30] = true;

        board[xOffset+54][yOffset+23] = true;
        board[xOffset+54][yOffset+29] = true;

        board[xOffset+61][yOffset+21] = true;
        board[xOffset+61][yOffset+31] = true;
        board[xOffset+61][yOffset+20] = true;
        board[xOffset+61][yOffset+32] = true;

        board[xOffset+62][yOffset+21] = true;
        board[xOffset+62][yOffset+31] = true;

        board[xOffset+63][yOffset+18] = true;
        board[xOffset+63][yOffset+19] = true;
        board[xOffset+63][yOffset+20] = true;
        board[xOffset+63][yOffset+32] = true;
        board[xOffset+63][yOffset+33] = true;
        board[xOffset+63][yOffset+34] = true;

        board[xOffset+64][yOffset+34] = true;
        board[xOffset+64][yOffset+18] = true;

    }

    public GameBoard(int columns, int rows) {
        board = new boolean[columns][rows];

        this.columns = columns;
        this.rows = rows;
    }

    public void setBoard(boolean[][] board) {
        this.board = board;
        this.columns = board.length;
        this.rows = board[0].length;
    }

    public void addBoard(boolean[][] board) {
        int xOffset = (int)Math.floor((this.columns - board[0].length)/2);
        int yOffset = (int)Math.floor((this.rows - board.length)/2);

        for(int y = 0; y < board.length;y++) {
            for(int x = 0; x < board[0].length;x++) {
                this.setCell(x+xOffset, y+yOffset, this.getCell(x+xOffset,y+yOffset)|| board[y][x]);
            }
        }

    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public boolean[][] getBoard() {
        return board;
    }

    public void setCell(int x, int y, boolean state) {
        board[x][y] = state;
    }

    public boolean getCell(int x, int y) {
        return board[x][y];
    }

    public void toggleCell(int x, int y) {
        board[x][y] = !board[x][y];
    }


    public int countNeighbours(int x, int y) {
        int count = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (!(i == x && j == y) && (i >= 0) && (i < columns) && (j >= 0) && (j < rows)) {
                    if (board[i][j]) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    @Override
    public String toString() {
        String boardString = "";
        for (boolean[] row : board) {
            for (boolean col : row) {
                boardString += (col) ? "x" : "o";
            }
            boardString += "\n";
        }

        return boardString;
    }
}
