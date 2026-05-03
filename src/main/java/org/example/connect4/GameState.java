

package org.example.connect4;
import java.util.ArrayList;
import java.util.List;

public class GameState {
    private static final char AI = 'X';
    private static final char HUMAN = 'O';
    private static final char EMPTY = '.';

    private char[][] board;
    private boolean isMaxPlayerTurn;

    public GameState(char[][] board, boolean isMaxPlayerTurn) {
        this.board = copyBoard(board);
        this.isMaxPlayerTurn = isMaxPlayerTurn;
    }

    private char[][] copyBoard(char[][] original) {
        char[][] copy = new char[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, original[i].length);
        }
        return copy;
    }

    public char[][] getBoard() {
        return board;
    }

    public boolean isMaxPlayerTurn() {
        return isMaxPlayerTurn;
    }

    // 👉 IDE add hozzá
    public boolean isTerminal() {
        return checkWin('X') || checkWin('O') || getValidMoves().isEmpty();
    }

    public int evaluate() {
        if (checkWin(AI)) {
            return 100000;
        }

        if (checkWin(HUMAN)) {
            return -100000;
        }

        int score = 0;

        score += evaluateWindows(AI);
        score -= evaluateWindows(HUMAN);

        return score;
    }

    private int evaluateWindows(char player) {
        int score = 0;

        // vízszintes ablakok
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length - 3; col++) {
                score += evaluateWindow(
                        board[row][col],
                        board[row][col + 1],
                        board[row][col + 2],
                        board[row][col + 3],
                        player
                );
            }
        }

        // függőleges ablakok
        for (int col = 0; col < board[0].length; col++) {
            for (int row = 0; row < board.length - 3; row++) {
                score += evaluateWindow(
                        board[row][col],
                        board[row + 1][col],
                        board[row + 2][col],
                        board[row + 3][col],
                        player
                );
            }
        }

        // átlós lefelé jobbra
        for (int row = 0; row < board.length - 3; row++) {
            for (int col = 0; col < board[0].length - 3; col++) {
                score += evaluateWindow(
                        board[row][col],
                        board[row + 1][col + 1],
                        board[row + 2][col + 2],
                        board[row + 3][col + 3],
                        player
                );
            }
        }

        // átlós felfelé jobbra
        for (int row = 3; row < board.length; row++) {
            for (int col = 0; col < board[0].length - 3; col++) {
                score += evaluateWindow(
                        board[row][col],
                        board[row - 1][col + 1],
                        board[row - 2][col + 2],
                        board[row - 3][col + 3],
                        player
                );
            }
        }

        return score;
    }

    private int evaluateWindow(char a, char b, char c, char d, char player) {
        int playerCount = 0;
        int emptyCount = 0;

        char[] window = {a, b, c, d};

        for (char cell : window) {
            if (cell == player) {
                playerCount++;
            } else if (cell == EMPTY) {
                emptyCount++;
            }
        }

        if (playerCount == 4) {
            return 100000;
        }

        if (playerCount == 3 && emptyCount == 1) {
            return 100;
        }

        if (playerCount == 2 && emptyCount == 2) {
            return 10;
        }

        if (playerCount == 1 && emptyCount == 3) {
            return 1;
        }

        return 0;
    }

    // 👉 és ez is kell, mert használod
    public List<Integer> getValidMoves() {
        List<Integer> moves = new ArrayList<>();
        for (int col = 0; col < board[0].length; col++) {
            if (board[0][col] == '.') {
                moves.add(col);
            }
        }
        return moves;
    }
    private boolean checkWin(char player) {
        // vízszintes
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length - 3; col++) {
                if (board[row][col] == player &&
                        board[row][col + 1] == player &&
                        board[row][col + 2] == player &&
                        board[row][col + 3] == player) {
                    return true;
                }
            }
        }
        // függőleges
        for (int col = 0; col < board[0].length; col++) {
            for (int row = 0; row < board.length - 3; row++) {
                if (board[row][col] == player &&
                        board[row + 1][col] == player &&
                        board[row + 2][col] == player &&
                        board[row + 3][col] == player) {
                    return true;
                }
            }
        }
        // átlós lefelé jobbra: \
        for (int row = 0; row < board.length - 3; row++) {
            for (int col = 0; col < board[0].length - 3; col++) {
                if (board[row][col] == player &&
                        board[row + 1][col + 1] == player &&
                        board[row + 2][col + 2] == player &&
                        board[row + 3][col + 3] == player) {
                    return true;
                }
            }
        }

        // átlós felfelé jobbra: /
        for (int row = 3; row < board.length; row++) {
            for (int col = 0; col < board[0].length - 3; col++) {
                if (board[row][col] == player &&
                        board[row - 1][col + 1] == player &&
                        board[row - 2][col + 2] == player &&
                        board[row - 3][col + 3] == player) {
                    return true;
                }
            }
        }


        return false;
    }

    public GameState makeMove(int col) {
        char[][] newBoard = copyBoard(board);

        char currentSymbol = isMaxPlayerTurn ? 'X' : 'O';

        for (int row = newBoard.length - 1; row >= 0; row--) {
            if (newBoard[row][col] == '.') {
                newBoard[row][col] = currentSymbol;
                return new GameState(newBoard, !isMaxPlayerTurn);
            }
        }

        throw new IllegalArgumentException("Az oszlop tele van: " + col);
    }



}