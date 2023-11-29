import java.util.Objects;

public class BotMiniMax extends Rules {
    int row, col;
    String player;
    String opponent;
    String playerLastValueUndoMove;
    String opponetLastValueUndoMove;
    String[][] table;

    BotMiniMax() {
        this.player = Constans.player1;
        this.opponent = Constans.player2;
        this.table = board;
    }
    BotMiniMax(String[][] table) {
        this.player = Constans.player1;
        this.opponent = Constans.player2;
        this.table = table;
    }
    // This function returns true if there are moves
// remaining on the board. It returns false if
// there are no moves left to play.
    Boolean isMovesLeft(String[][] board) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (!board[i][j].equalsIgnoreCase("X") && !board[i][j].equalsIgnoreCase("Y"))
                    return true;
        return false;
    }

    // This is the evaluation function as discussed

    int evaluate(String[][] b) {
        // Checking for Rows for X or O victory.
        for (int row = 0; row < 3; row++) {
            if (Objects.equals(b[row][0], b[row][1]) &&
                    Objects.equals(b[row][1], b[row][2])) {
                if (Objects.equals(b[row][0], player))
                    return +10;
                else if (Objects.equals(b[row][0], opponent))
                    return -10;
            }
        }

        // Checking for Columns for X or O victory.
        for (int col = 0; col < 3; col++) {
            if (Objects.equals(b[0][col], b[1][col]) &&
                    Objects.equals(b[1][col], b[2][col])) {
                if (Objects.equals(b[0][col], player))
                    return +10;

                else if (Objects.equals(b[0][col], opponent))
                    return -10;
            }
        }

        // Checking for Diagonals for X or O victory.
        if (Objects.equals(b[0][0], b[1][1]) && Objects.equals(b[1][1], b[2][2])) {
            if (Objects.equals(b[0][0], player))
                return +10;
            else if (Objects.equals(b[0][0], opponent))
                return -10;
        }

        if (Objects.equals(b[0][2], b[1][1]) && Objects.equals(b[1][1], b[2][0])) {
            if (Objects.equals(b[0][2], player))
                return +10;
            else if (Objects.equals(b[0][2], opponent))
                return -10;
        }
        // Else if none of them have won then return 0
        return 0;
    }

    // This is the minimax function. It considers all
// the possible ways the game can go and returns
// the value of the board
    int minimax(String[][] board,
                int depth, Boolean isMax) {
        int score = evaluate(board);

        // If Maximizer has won the game
        // return his/her evaluated score
        if (score == 10)
            return score;

        // If Minimizer has won the game
        // return his/her evaluated score
        if (score == -10)
            return score;

        // If there are no more moves and
        // no winner then it is a tie
        if (!isMovesLeft(board))
            return 0;

        // If this maximizer's move
        if (isMax) {
            int best = -1000;

            // Traverse all cells
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check if cell is empty
                    if (!board[i][j].equalsIgnoreCase("X") && !board[i][j].equalsIgnoreCase("Y")) {
                        // Make the move
                        playerLastValueUndoMove = board[i][j];
                        board[i][j] = player;

                        // Call minimax recursively and choose
                        // the maximum value
                        best = Math.max(best, minimax(board,
                                depth + 1, !isMax));

                        // Undo the move
                        board[i][j] = playerLastValueUndoMove;
                    }
                }
            }
            return best;
        }

        // If this minimizer's move
        else {
            int best = 1000;

            // Traverse all cells
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check if cell is empty
                    if (!board[i][j].equalsIgnoreCase("X") && !board[i][j].equalsIgnoreCase("Y")) {
                        // Make the move
                        opponetLastValueUndoMove = board[i][j];
                        board[i][j] = opponent;

                        // Call minimax recursively and choose
                        // the minimum value
                        best = Math.min(best, minimax(board,
                                depth + 1, !isMax));
                        // Undo the move
                        board[i][j] = opponetLastValueUndoMove;
                    }
                }
            }
            return best;
        }
    }


    public void setBotValueinBoard(int i, int j) {
        table[i][j] = player;
    }

    // This will return the best possible
    // move for the player
    public String[][] botMove(String[][] board) { //findBestMoveforBot
        int bestVal = -1000;
        BotMiniMax bestMove = new BotMiniMax();
        bestMove.row = -1;
        bestMove.col = -1;

        // Traverse all cells, evaluate minimax function
        // for all empty cells. And return the cell
        // with optimal value.
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Check if cell is empty
                if (!board[i][j].equalsIgnoreCase("X") && !board[i][j].equalsIgnoreCase("Y")) {
                    // Make the move
                    playerLastValueUndoMove = board[i][j];
                    board[i][j] = player;
                    // compute evaluation function for this
                    // move.
                    int moveVal = minimax(board, 0, false);

                    // Undo the move
                    board[i][j] = playerLastValueUndoMove;

                    // If the value of the current move is
                    // more than the best value, then update
                    // best/
                    if (moveVal > bestVal) {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }

        //System.out.printf("The value of the best Move " +
        //  "is : %d\n\n", bestVal);

        setBotValueinBoard(bestMove.row, bestMove.col);
        return this.table;
    }

   /* BotMiniMax findBestMove(String[][] board) {
        int bestVal = -1000;
        BotMiniMax bestMove = new BotMiniMax();
        bestMove.row = -1;
        bestMove.col = -1;

        // Traverse all cells, evaluate minimax function
        // for all empty cells. And return the cell
        // with optimal value.
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Check if cell is empty
                if (!board[i][j].equalsIgnoreCase("X") && !board[i][j].equalsIgnoreCase("Y")) {
                    // Make the move
                    playerLastValueUndoMove = board[i][j];
                    board[i][j] = player;
                    // compute evaluation function for this
                    // move.
                    int moveVal = minimax(board, 0, false);

                    // Undo the move
                    board[i][j] = playerLastValueUndoMove;

                    // If the value of the current move is
                    // more than the best value, then update
                    // best/
                    if (moveVal > bestVal) {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }

        System.out.printf("The value of the best Move " +
                "is : %d\n\n", bestVal);

        return bestMove;
    }*/
}
