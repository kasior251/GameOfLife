package model;

public class GameBoard {

    private boolean[][] board;
    private int columns;
    private int rows;

    public GameBoard(int columns, int rows) {
        board = new boolean[columns][rows];

        board[1+12][1+17] = true;
        board[2+12][1+17] = true;
        board[2+12][2+17] = true;
        board[2+12][3+17] = true;
        board[3+12][4+17] = true;
        board[4+12][3+17] = true;
        board[4+12][4+17] = true;

        board[1+12][17+17] = true;
        board[2+12][17+17] = true;
        board[2+12][16+17] = true;
        board[2+12][15+17] = true;
        board[3+12][14+17] = true;
        board[4+12][15+17] = true;
        board[4+12][14+17] = true;

        board[12+12][5+17] = true;
        board[12+12][6+17] = true;
        board[12+12][7+17] = true;
        board[12+12][11+17] = true;
        board[12+12][12+17] = true;
        board[12+12][13+17] = true;

        board[13+12][5+17] = true;
        board[13+12][8+17] = true;
        board[13+12][10+17] = true;
        board[13+12][13+17] = true;

        board[14+12][5+17] = true;
        board[14+12][13+17] = true;

        board[16+12][6+17] = true;
        board[16+12][12+17] = true;

        board[17+12][7+17] = true;
        board[17+12][8+17] = true;
        board[17+12][10+17] = true;
        board[17+12][11+17] = true;

        board[26+12][3+17] = true;
        board[26+12][4+17] = true;
        board[27+12][3+17] = true;
        board[27+12][4+17] = true;
        board[26+12][15+17] = true;
        board[26+12][14+17] = true;
        board[27+12][15+17] = true;
        board[27+12][14+17] = true;

        board[36+12][7+17] = true;
        board[37+12][7+17] = true;
        board[36+12][11+17] = true;
        board[37+12][11+17] = true;

        board[40+12][4+17] = true;
        board[40+12][5+17] = true;
        board[40+12][7+17] = true;
        board[40+12][11+17] = true;
        board[40+12][13+17] = true;
        board[40+12][14+17] = true;

        board[41+12][5+17] = true;
        board[41+12][6+17] = true;
        board[41+12][7+17] = true;
        board[41+12][11+17] = true;
        board[41+12][12+17] = true;
        board[41+12][13+17] = true;

        board[42+12][6+17] = true;
        board[42+12][12+17] = true;

        board[49+12][4+17] = true;
        board[49+12][14+17] = true;
        board[49+12][3+17] = true;
        board[49+12][15+17] = true;

        board[50+12][4+17] = true;
        board[50+12][14+17] = true;

        board[51+12][1+17] = true;
        board[51+12][2+17] = true;
        board[51+12][3+17] = true;
        board[51+12][15+17] = true;
        board[51+12][16+17] = true;
        board[51+12][17+17] = true;

        board[52+12][17+17] = true;
        board[52+12][1+17] = true;

        this.columns = columns;
        this.rows = rows;
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
