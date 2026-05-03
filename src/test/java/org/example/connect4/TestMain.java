package org.example.connect4;

public class TestMain {

    public static void main(String[] args) {

        // ===== 1. ÁTLÓS WIN TESZT =====
        System.out.println("=== ÁTLÓS WIN TESZT ===");

        char[][] diagonalWinBoard = {
                {'.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', 'X', '.', '.', '.'},
                {'.', '.', 'X', '.', '.', '.', '.'},
                {'.', 'X', '.', '.', '.', '.', '.'},
                {'X', '.', '.', '.', '.', '.', '.'}
        };

        GameState state1 = new GameState(diagonalWinBoard, true);

        printBoard(state1.getBoard());
        System.out.println("isTerminal: " + state1.isTerminal()); // elvárt: true

        // ===== 2. makeMove TESZT =====
        System.out.println("\n=== makeMove TESZT ===");

        char[][] emptyBoard = {
                {'.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.'}
        };

        GameState state2 = new GameState(emptyBoard, true);
        GameState state3 = state2.makeMove(3);

        System.out.println("Eredeti tábla:");
        printBoard(state2.getBoard());

        System.out.println("Új állapot (lépés után):");
        printBoard(state3.getBoard());

        // ===== 3. TÖBB LÉPÉS TESZT =====
        System.out.println("\n=== TÖBB LÉPÉS TESZT ===");

        GameState state4 = state2.makeMove(3); // X
        GameState state5 = state4.makeMove(3); // O
        GameState state6 = state5.makeMove(3); // X

        printBoard(state6.getBoard());

        // ===== 4. DEEP COPY TESZT =====
        System.out.println("\n=== DEEP COPY TESZT ===");

        char[][] original = {
                {'.', '.', '.'},
                {'.', '.', '.'},
                {'.', '.', '.'}
        };

        GameState state7 = new GameState(original, true);

        original[2][1] = 'X';

        System.out.println("GameState board:");
        printBoard(state7.getBoard());

        System.out.println("Original board:");
        printBoard(original);

        System.out.println("\n=== EVALUATE TESZT ===");

        char[][] evalBoard = {
                {'.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.'},
                {'X', 'X', 'X', '.', '.', '.', '.'}
        };

        GameState evalState = new GameState(evalBoard, true);

        printBoard(evalState.getBoard());
        System.out.println("Értékelés: " + evalState.evaluate());

        System.out.println("\n=== MINIMAX TESZT ===");

        char[][] testBoard = {
                {'.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.'},
                {'X', 'X', 'X', '.', '.', '.', '.'}
        };

        GameState testState = new GameState(testBoard, true);

        MinimaxAI ai = new MinimaxAI();

        int bestMove = ai.findBestMove(testState, 6);

        System.out.println("AI által választott oszlop: " + bestMove);

        System.out.println("\n=== MINIMAX BLOKKOLÁS TESZT ===");

        char[][] blockTestBoard = {
                {'.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.'},
                {'.', 'O', '.', '.', '.', '.', '.'},
                {'.', 'O', '.', '.', '.', '.', '.'},
                {'X', 'O', '.', '.', '.', 'X', 'X'}
        };

        GameState blockState = new GameState(blockTestBoard, true);

        printBoard(blockState.getBoard());

        MinimaxAI ai2 = new MinimaxAI();
        int blockMove = ai2.findBestMove(blockState, 8);

        System.out.println("AI blokkoló lépése: " + blockMove);
    }




    private static void printBoard(char[][] board) {
        for (char[] row : board) {
            for (char c : row) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }


}
