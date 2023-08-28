package ticTacToe;

import java.awt.Color;
import java.awt.Graphics;

public class Board {
    public static final int GRID_WIDTH = 8;
    public static final int GRID_WIDHT_HALF = GRID_WIDTH / 2;
    Cell[][] cells; // 2D array to represent the game board cells

    // Constructor to create the game board and initialize cells
    public Board() {
        cells = new Cell[GameMain.ROWS][GameMain.COLS];
        for (int row = 0; row < GameMain.ROWS; ++row) {
            for (int col = 0; col < GameMain.COLS; ++col) {
                cells[row][col] = new Cell(row, col);
            }
        }
    }

    // Check if the game has ended in a draw
    public boolean isDraw() {
        for (int row = 0; row < GameMain.ROWS; ++row) {
            for (int col = 0; col < GameMain.COLS; ++col) {
                if (cells[row][col].content == Player.Empty) {
                    return false; // If any cell is empty, the game is not a draw
                }
            }
        }
        return true; // All cells are filled, indicating a draw
    }

    // Check if the specified player has won after making a move
    public boolean hasWon(Player thePlayer, int playerRow, int playerCol) {
        // Check rows, columns, and diagonals for a win
        if (cells[playerRow][0].content == thePlayer &&
            cells[playerRow][1].content == thePlayer &&
            cells[playerRow][2].content == thePlayer) {
            return true; // 3 in a row
        }

        if (cells[0][playerCol].content == thePlayer &&
            cells[1][playerCol].content == thePlayer &&
            cells[2][playerCol].content == thePlayer) {
            return true; // 3 in a column
        }

        if (playerRow == playerCol &&
            cells[0][0].content == thePlayer &&
            cells[1][1].content == thePlayer &&
            cells[2][2].content == thePlayer) {
            return true; // Diagonal from top-left to bottom-right
        }

        if (playerRow + playerCol == 2 &&
            cells[0][2].content == thePlayer &&
            cells[1][1].content == thePlayer &&
            cells[2][0].content == thePlayer) {
            return true; // Diagonal from top-right to bottom-left
        }

        return false; // No win condition met
    }

    // Paint the game board on the graphics canvas
    public void paint(Graphics g) {
        g.setColor(Color.gray);

        // Draw grid lines
        for (int row = 1; row < GameMain.ROWS; ++row) {
            g.fillRoundRect(0, GameMain.CELL_SIZE * row - GRID_WIDHT_HALF,
                    GameMain.CANVAS_WIDTH - 1, GRID_WIDTH,
                    GRID_WIDTH, GRID_WIDTH);
        }
        for (int col = 1; col < GameMain.COLS; ++col) {
            g.fillRoundRect(GameMain.CELL_SIZE * col - GRID_WIDHT_HALF, 0,
                    GRID_WIDTH, GameMain.CANVAS_HEIGHT - 1,
                    GRID_WIDTH, GRID_WIDTH);
        }

        // Draw the cells' content
        for (int row = 0; row < GameMain.ROWS; ++row) {
            for (int col = 0; col < GameMain.COLS; ++col) {
                cells[row][col].paint(g); // Paint each cell
            }
        }
    }
}