package ticTacToe;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameMain extends JPanel implements MouseListener {
    // Serialization ID (optional, added for completeness)
    private static final long serialVersionUID = 472142173103249314L;

    // Constants for game
    public static final int ROWS = 3;
    public static final int COLS = 3;
    public static final String TITLE = "Tic Tac Toe";
    public static final int CELL_SIZE = 100;
    public static final int CANVAS_WIDTH = CELL_SIZE * COLS;
    public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
    public static final int CELL_PADDING = CELL_SIZE / 6;
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
    public static final int SYMBOL_STROKE_WIDTH = 8;

    private Board board; // Game board
    private GameState currentState; // Current state of the game
    private Player currentPlayer; // Current player's turn
    private JLabel statusBar; // Status bar to display messages

    // Constructor
    public GameMain() {
        addMouseListener(this); // Register mouse listener
        statusBar = new JLabel("         "); // Create status bar label
        statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14)); // Set font
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5)); // Set border
        statusBar.setOpaque(true);
        statusBar.setBackground(Color.LIGHT_GRAY); // Set background color
        setLayout(new BorderLayout()); // Set layout
        add(statusBar, BorderLayout.SOUTH); // Add status bar to panel
        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT + 30)); // Set preferred size

        board = new Board(); // Create game board
        initGame(); // Initialize game state
    }

    // Main method to start the game
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame(TITLE); // Create frame
                GameMain gameMain = new GameMain(); // Create game panel
                frame.add(gameMain); // Add game panel to frame
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set close operation
                frame.pack(); // Pack components
                frame.setLocationRelativeTo(null); // Center frame on screen
                frame.setVisible(true); // Display frame
            }
        });
    }

    // Mouse click event handler
    public void mouseClicked(MouseEvent e) {
        // Get mouse click coordinates and calculate selected row and column
        int mouseX = e.getX();
        int mouseY = e.getY();
        int rowSelected = mouseY / CELL_SIZE;
        int colSelected = mouseX / CELL_SIZE;

        // Check game state and update board if it's a valid move
        if (currentState == GameState.Playing) {
            if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0 && colSelected < COLS
                    && board.cells[rowSelected][colSelected].content == Player.Empty) {
                board.cells[rowSelected][colSelected].content = currentPlayer; // Set player's move
                updateGame(currentPlayer, rowSelected, colSelected); // Update game state
                currentPlayer = (currentPlayer == Player.Cross) ? Player.Nought : Player.Cross; // Switch player
            }
        } else {
            initGame(); // Initialize game state
        }
        repaint(); // Redraw the panel
    }

    // Custom painting of the game panel
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);
        board.paint(g); // Paint the game board

        // Update status bar based on game state
        if (currentState == GameState.Playing) {
            statusBar.setForeground(Color.BLACK);
            statusBar.setText((currentPlayer == Player.Cross) ? "X's Turn" : "O's Turn");
        } else if (currentState == GameState.Draw) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("It's a Draw! Click to play again.");
        } else if (currentState == GameState.Cross_won) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'X' Won! Click to play again.");
        } else if (currentState == GameState.Nought_won) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'O' Won! Click to play again.");
        }
    }

    // Initialize the game state
    public void initGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                board.cells[row][col].content = Player.Empty; // Clear the board cells
            }
        }
        currentState = GameState.Playing; // Set game state to playing
        currentPlayer = Player.Cross; // Set current player to Cross
    }

    // Update game state after a move
    public void updateGame(Player thePlayer, int row, int col) {
        if (board.hasWon(thePlayer, row, col)) {
            currentState = (thePlayer == Player.Cross) ? GameState.Cross_won : GameState.Nought_won; // Set winner
        } else if (board.isDraw()) {
            currentState = GameState.Draw; // Set draw
        }
    }

    // Unimplemented mouse event methods
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
