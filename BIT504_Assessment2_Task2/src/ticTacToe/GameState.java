package ticTacToe;

// Enumeration representing the possible game states
public enum GameState {
    Playing, // The game is currently being played
    Draw,    // The game ended in a draw (no winner)
    Cross_won, // Player Cross (X) has won
    Nought_won // Player Nought (O) has won
}