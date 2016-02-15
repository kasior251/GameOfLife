package model;

public class GameBoard {

    private boolean[][] board;
    private int columns;
    private int rows;


    public GameBoard(int columns, int rows) {
        board = new boolean[columns][rows];

        board[1][1] = true;
        board[2][1] = true;
        board[2][2] = true;
        board[2][3] = true;
        board[3][4] = true;
        board[4][3] = true;
        board[4][4] = true;

        board[1][17] = true;
        board[2][17] = true;
        board[2][16] = true;
        board[2][15] = true;
        board[3][14] = true;
        board[4][15] = true;
        board[4][14] = true;

        board[12][5] = true;
        board[12][6] = true;
        board[12][7] = true;
        board[12][11] = true;
        board[12][12] = true;
        board[12][13] = true;

        board[13][5] = true;
        board[13][8] = true;
        board[13][10] = true;
        board[13][13] = true;

        board[14][5] = true;
        board[14][13] = true;

        board[16][6] = true;
        board[16][12] = true;

        board[17][7] = true;
        board[17][8] = true;
        board[17][10] = true;
        board[17][11] = true;

        board[26][3] = true;
        board[26][4] = true;
        board[27][3] = true;
        board[27][4] = true;
        board[26][15] = true;
        board[26][14] = true;
        board[27][15] = true;
        board[27][14] = true;

        board[36][7] = true;
        board[37][7] = true;
        board[36][11] = true;
        board[37][11] = true;

        board[40][4] = true;
        board[40][5] = true;
        board[40][7] = true;
        board[40][11] = true;
        board[40][13] = true;
        board[40][14] = true;

        board[41][5] = true;
        board[41][6] = true;
        board[41][7] = true;
        board[41][11] = true;
        board[41][12] = true;
        board[41][13] = true;

        board[42][6] = true;
        board[42][12] = true;

        board[49][4] = true;
        board[49][14] = true;
        board[49][3] = true;
        board[49][15] = true;

        board[50][4] = true;
        board[50][14] = true;

        board[51][1] = true;
        board[51][2] = true;
        board[51][3] = true;
        board[51][15] = true;
        board[51][16] = true;
        board[51][17] = true;

        board[52][17] = true;
        board[52][1] = true;

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
