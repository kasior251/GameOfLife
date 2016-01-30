package GoL.model;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Consilium on 30.01.2016.
 */
public class GameBoard {
    private Random r;
    private byte[][] board;

    public GameBoard() {
        this.board = new byte[112][160];
        Random r = new Random();
        this.board[r.nextInt(112)][r.nextInt(160)] = 1;
        this.board[r.nextInt(112)][r.nextInt(160)] = 1;
        this.board[r.nextInt(112)][r.nextInt(160)] = 1;
        this.board[r.nextInt(112)][r.nextInt(160)] = 1;
        this.board[r.nextInt(112)][r.nextInt(160)] = 1;
        this.board[r.nextInt(112)][r.nextInt(160)] = 1;
        this.board[r.nextInt(112)][r.nextInt(160)] = 1;
        this.board[r.nextInt(112)][r.nextInt(160)] = 1;
        this.board[r.nextInt(112)][r.nextInt(160)] = 1;
        this.board[r.nextInt(112)][r.nextInt(160)] = 1;
        this.board[r.nextInt(112)][r.nextInt(160)] = 1;
        this.board[r.nextInt(112)][r.nextInt(160)] = 1;
        this.board[r.nextInt(112)][r.nextInt(160)] = 1;
        this.board[r.nextInt(112)][r.nextInt(160)] = 1;

    }

    public byte[][] getBoard() {
        return board;
    }
}