import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TicTacToeFrame extends JFrame {
    private TicTacToeTile[][] board; // 3x3 grid
    private char currentPlayer;
    private boolean gameEnded;

    public TicTacToeFrame() {
        currentPlayer = 'X'; // X start
        gameEnded = false;
        board = new TicTacToeTile[3][3];
        initializeUI();
    }

    private void initializeUI() {
        this.setTitle("VUVQ: Tic Tac Toe");
        this.setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));

        ButtonClickListener listener = new ButtonClickListener();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col] = new TicTacToeTile(row, col);
                board[row][col].setText(" ");
                board[row][col].addActionListener(listener);
                boardPanel.add(board[row][col]);
            }
        }

        this.add(boardPanel, BorderLayout.CENTER);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "Quit",
                    JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        this.add(quitButton, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    private void checkForWin() {
        // Check rows
        for (int row = 0; row < 3; row++) {
            if (!board[row][0].getText().equals(" ") &&
                board[row][0].getText().equals(board[row][1].getText()) &&
                board[row][1].getText().equals(board[row][2].getText())) {
                gameEnded = true;
                promptPlayAgain(currentPlayer + " wins!");
                return;
            }
        }
    
        // Check columns
        for (int col = 0; col < 3; col++) {
            if (!board[0][col].getText().equals(" ") &&
                board[0][col].getText().equals(board[1][col].getText()) &&
                board[1][col].getText().equals(board[2][col].getText())) {
                gameEnded = true;
                promptPlayAgain(currentPlayer + " wins!");
                return;
            }
        }
    
        // Check diagonal (top-left to bottom-right)
        if (!board[0][0].getText().equals(" ") &&
            board[0][0].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][2].getText())) {
            gameEnded = true;
            promptPlayAgain(currentPlayer + " wins!");
            return;
        }
    
        // Check diagonal (top-right to bottom-left)
        if (!board[0][2].getText().equals(" ") &&
            board[0][2].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][0].getText())) {
            gameEnded = true;
            promptPlayAgain(currentPlayer + " wins!");
            return;
        }
    }

    private void checkForTie() {
        if (gameEnded) {
            return; // Skip tie check if the game has already been won
        }
    
        boolean boardFull = true;
    
        // Check if all cells are filled
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col].getText().equals(" ")) {
                    boardFull = false; // Found an empty cell, so it's not a tie
                    break;
                }
            }
            if (!boardFull) break; // Break out of the outer loop if an empty cell is found
        }
    
        if (boardFull) {
            gameEnded = true;
            promptPlayAgain("It's a tie!");
        }
    }

    private void promptPlayAgain(String message) {
        int response = JOptionPane.showConfirmDialog(this, message + "\nWould you like to play again?", "Game Over",
                JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            resetGame(); // Reset the game
        } else {
            System.exit(0); // Exit the game
        }
    }

    private void resetGame() {
        currentPlayer = 'X'; // Reset to player X
        gameEnded = false; // Reset game state
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col].setText(" "); // Clear the board
            }
        }
    }

    private void togglePlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TicTacToeTile tileClicked = (TicTacToeTile) e.getSource();
    
            // Ignore the click if the game is over or the tile is not empty
            if (gameEnded || !tileClicked.getText().equals(" ")) {
                JOptionPane.showMessageDialog(null, "Invalid move!");
                return;
            }
    
            // Set the text of the tile to the current player's symbol
            tileClicked.setText(String.valueOf(currentPlayer));
    
            // Check for a win or a tie
            checkForWin();
            checkForTie();
    
            // If the game hasn't ended, switch the player
            if (!gameEnded) {
                togglePlayer();
            }
        }
    }
}
