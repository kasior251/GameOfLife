package model;

public class GameBoard {

    private boolean[][] board;
    private int columns;
    private int rows;

    public GameBoard(int columns, int rows) {
        board = new boolean[columns][rows];
        board[1][1] = true;
        board[1][2] = true;
        board[2][2] = true;
    }

    public void setBoard(boolean[][] board) {
        this.board = board;
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

    public int countNeighbours(int x, int y) {
        int count = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (!(i == x && j == y) && (i >= 0) && (i <= columns) && (j >= 0) && (j <= rows)) {
                    if (board[i][j]) {
                        count++;
                    }
                }

            }
        }
        return count;
    }

}
