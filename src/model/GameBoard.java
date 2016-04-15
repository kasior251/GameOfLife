package model;

public class GameBoard {

    private boolean[][] board;
    private int columns;
    private int rows;

    public GameBoard() {
        this(150, 100);


        board[13][18] = true;
        board[14][18] = true;
        board[14][19] = true;
        board[14][20] = true;
        board[15][21] = true;
        board[16][20] = true;
        board[16][21] = true;

        board[13][34] = true;
        board[14][34] = true;
        board[14][33] = true;
        board[14][32] = true;
        board[15][31] = true;
        board[16][32] = true;
        board[16][31] = true;

        board[24][22] = true;
        board[24][23] = true;
        board[24][24] = true;
        board[24][28] = true;
        board[24][29] = true;
        board[24][30] = true;

        board[25][22] = true;
        board[25][25] = true;
        board[25][27] = true;
        board[25][30] = true;

        board[26][22] = true;
        board[26][30] = true;

        board[28][23] = true;
        board[28][29] = true;

        board[29][24] = true;
        board[29][25] = true;
        board[29][27] = true;
        board[29][28] = true;

        board[38][20] = true;
        board[38][21] = true;
        board[39][20] = true;
        board[39][21] = true;
        board[38][32] = true;
        board[38][31] = true;
        board[39][32] = true;
        board[39][31] = true;

        board[48][24] = true;
        board[49][24] = true;
        board[48][28] = true;
        board[49][28] = true;

        board[52][21] = true;
        board[52][22] = true;
        board[52][24] = true;
        board[52][28] = true;
        board[52][30] = true;
        board[52][31] = true;

        board[53][22] = true;
        board[53][23] = true;
        board[53][24] = true;
        board[53][28] = true;
        board[53][29] = true;
        board[53][30] = true;

        board[54][23] = true;
        board[54][29] = true;

        board[61][21] = true;
        board[61][31] = true;
        board[61][20] = true;
        board[61][32] = true;

        board[62][21] = true;
        board[62][31] = true;

        board[63][18] = true;
        board[63][19] = true;
        board[63][20] = true;
        board[63][32] = true;
        board[63][33] = true;
        board[63][34] = true;

        board[64][34] = true;
        board[64][18] = true;

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

}
