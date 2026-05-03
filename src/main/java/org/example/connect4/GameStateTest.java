package org.example.connect4;

public class GameStateTest {
    public static void main(String[] args) {
        char[][] original = {
                {'.', '.', '.'},
                {'.', '.', '.'},
                {'.', '.', '.'}
        };

        GameState state = new GameState(original, true);

        // Módosítjuk az EREDETI tömböt
        original[2][1] = 'X';

        // Kiírjuk a GameState boardját
        System.out.println("GameState board:");
        printBoard(state.getBoard());

        // Kiírjuk az eredetit
        System.out.println("Original board:");
        printBoard(original);
    }

    private static void printBoard(char[][] board) {
        for (char[] row : board) {
            for (char c : row) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
