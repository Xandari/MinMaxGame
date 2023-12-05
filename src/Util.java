public class Util {
   public String printBoard(String[][] board) {
        String str;
        str = "|---|---|---|"
                + "\n| " + board[0][0] + "   " + board[1][0] + "   " + board[2][0] + " |"
                + "\n|           |"
                + "\n| " + board[0][1] + "   " + board[1][1] + "   " + board[2][1] + " |"
                + "\n|           |"
                + "\n| " + board[0][2] + "   " + board[1][2] + "   " + board[2][2] + " |"
                + "\n|---|---|---|";
        return str;
    }

    public int random() {
        return (int) (Math.random() * 2 + 1);
    }

    public boolean checkWin(String player, String[][] board) {
        for (int i = 0; i < 3; i++) {
            if ((board[i][0].equals(player) && board[i][1].equals(player) && board[i][2].equals(player)) ||
                    (board[0][i].equals(player) && board[1][i].equals(player) && board[2][i].equals(player))) {
                return true;
            }
        }
        return (board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) ||
                (board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player));
    }

}
