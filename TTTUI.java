import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TTTUI extends JFrame {
    private TTTTileButton[][] buttons = new TTTTileButton[3][3];
    private Game game;

    public TTTUI() {
        game = new Game(this);  // Initialize the game with the UI
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Tic Tac Toe");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());  // Use BorderLayout for easier button placement

        // Panel for the game board
        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new TTTTileButton(i, j);
                int row = i;
                int col = j;
                buttons[i][j].addActionListener(e -> handleTileClick(row, col));
                boardPanel.add(buttons[i][j]);
            }
        }

        // Add the board panel to the center of the window
        add(boardPanel, BorderLayout.CENTER);

        // Panel for the control buttons (Reset & Quit)
        JPanel controlPanel = new JPanel();
        JButton resetButton = new JButton("Reset");
        JButton quitButton = new JButton("Quit");

        resetButton.addActionListener(e -> resetGame());
        quitButton.addActionListener(e -> quitGame());

        controlPanel.add(resetButton);
        controlPanel.add(quitButton);

        // Add control panel to the bottom
        add(controlPanel, BorderLayout.SOUTH);
    }

    // Handle tile click for a move
    private void handleTileClick(int row, int col) {
        // Check if the game is not over and the tile is not already taken
        if (game.isGameOver() || buttons[row][col].getText().length() > 0) {
            return;  // Ignore click if game is over or the tile is already filled
        }

        // Make the move in the game logic
        game.makeMove(row, col);

        // After the move, update the board UI
        updateBoard();
    }

    // Update the board after a move or game reset
    public void updateBoard() {
        // Clear the UI by resetting all buttons to be empty
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");  // Clear text
            }
        }

        // Now update the board with the current state of the game
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                char tile = game.getBoard().getTile(i, j);
                if (tile != ' ') {
                    buttons[i][j].setText(String.valueOf(tile));  // Set 'X' or 'O'
                }
            }
        }
    }

    // Display a message after a win or tie
    public void displayMessage(String message) {
        // Delay the message so the board updates first
        SwingUtilities.invokeLater(() -> {
            int response = JOptionPane.showOptionDialog(this, message + "\nWould you like to play again?", 
                                                        "Game Over", JOptionPane.YES_NO_OPTION, 
                                                        JOptionPane.INFORMATION_MESSAGE, 
                                                        null, new Object[]{"Yes", "No"}, "Yes");

            if (response == JOptionPane.YES_OPTION) {
                game.startNewGame();
                updateBoard();  // Clear the board and reset for the new game
            } else {
                quitGame();
            }
        });
    }

    // Reset the game
    private void resetGame() {
        game.startNewGame();  // Start a new game
        updateBoard();  // Clear the board and reset the game UI
    }

    // Quit the game
    private void quitGame() {
        System.exit(0);  // Exit the program
    }
}
