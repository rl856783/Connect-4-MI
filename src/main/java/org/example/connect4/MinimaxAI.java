package org.example.connect4;

import java.util.List;

public class MinimaxAI {

    public int findBestMove(GameState state, int depth) {
        int bestScore = Integer.MIN_VALUE;
        int bestMove = -1;

        List<Integer> moves = state.getValidMoves();

        for (int move : moves) {
            GameState newState = state.makeMove(move);

            int score = minimax(newState, depth - 1, false,
                    Integer.MIN_VALUE, Integer.MAX_VALUE);

            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

        return bestMove;
    }

    private int minimax(GameState state, int depth, boolean maximizing,
                        int alpha, int beta) {

        // STOP feltétel
        if (depth == 0 || state.isTerminal()) {
            return state.evaluate();
        }

        List<Integer> moves = state.getValidMoves();

        if (maximizing) {
            int maxEval = Integer.MIN_VALUE;

            for (int move : moves) {
                int eval = minimax(
                        state.makeMove(move),
                        depth - 1,
                        false,
                        alpha,
                        beta
                );

                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);

                if (beta <= alpha) {
                    break; // ALFA-BÉTA VÁGÁS
                }
            }

            return maxEval;

        } else {
            int minEval = Integer.MAX_VALUE;

            for (int move : moves) {
                int eval = minimax(
                        state.makeMove(move),
                        depth - 1,
                        true,
                        alpha,
                        beta
                );

                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);

                if (beta <= alpha) {
                    break; // ALFA-BÉTA VÁGÁS
                }
            }

            return minEval;
        }
    }
}
