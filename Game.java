public class Game {
    private char currentPlayer = 'X';
    private boolean gameOver = false;
    private TTTBoard board;
    private TTTUI ui;

    public Game(TTTUI ui) {
        this.ui = ui;
        board = new TTTBoard(this);
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void startNewGame() {
        currentPlayer = 'X';
        gameOver = false;
        board.resetBoard();
        ui.updateBoard();
    }

    public void togglePlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    public void makeMove(int row, int col) {
        if (!gameOver && board.setTile(row, col, currentPlayer)) {
            ui.updateBoard(); // Update the UI after the move is made

            if (board.checkWin()) {
                gameOver = true;
                ui.displayMessage("Player " + currentPlayer + " wins!");
            } else if (board.isFull()) {
                gameOver = true;
                ui.displayMessage("It's a tie!");
            } else {
                togglePlayer();
            }
        }
    }

    public TTTBoard getBoard() {
        return board;
    }
}
