import java.util.Objects;

public class BotMiniMax {

    Util utils = new Util();
    String player_X;
    String player_O;
    public BotMiniMax(String player_X, String player_O) {
        this.player_X = player_X;
        this.player_O = player_O;
    }

    public String[][] putingSymbolByComputer(String[][] board) {

        int[] bestMove = minimax(0, player_O, board);
        board[bestMove[0]][bestMove[1]] = player_O;
        System.out.println("Computer chooses: Col " + (bestMove[0] + 1) + " Row " + (bestMove[1] + 1));
        return board;
    }

    public int[] minimax(int depth, String player, String[][] board) {
        int[] bestMove = new int[]{-1, -1};
        int bestScore = (player.equals(player_O)) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!board[i][j].equalsIgnoreCase("X") && !board[i][j].equalsIgnoreCase("O")) {

                    var undoPlayerMove = board[i][j];
                    board[i][j] = player;
                    int score = minimaxHelper(depth + 1, player_X, board);

                    board[i][j] = undoPlayerMove;

                    if ((player.equals(player_O) && score > bestScore) || (player.equals(player_X) && score < bestScore)) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }

        return bestMove;
    }

    public int minimaxHelper(int depth, String player, String[][] board) {
        if (utils.checkWin(player_X, board)) return -1;
        if (utils.checkWin(player_O, board)) return 1;
        if (isBoardFull(board)) return 0;

        int bestScore = (player.equals(player_O)) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!board[i][j].equalsIgnoreCase("X") && !board[i][j].equalsIgnoreCase("O")) {
                    var undoPlayerMove = board[i][j];
                    board[i][j] = player;
                    int score = minimaxHelper(depth + 1, player.equals(player_O) ? player_X : player_O, board);
                    board[i][j] = undoPlayerMove;

                    if ((player.equals(player_O) && score > bestScore) || (player.equals(player_X) && score < bestScore)) {
                        bestScore = score;
                    }
                }
            }
        }
        return bestScore;
    }


    public boolean isBoardFull(String[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!board[i][j].equalsIgnoreCase("X")
                        && !board[i][j].equalsIgnoreCase("O")) {
                    return false;
                }
            }
        }
        return true;
    }
}