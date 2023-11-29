import java.util.List;

public class Constans {
    protected  static String[][] board = firstArrSet();
    protected static String player1 = "X";
    protected static String player2 = "O";

    static public String[][] firstArrSet() {
        String[] table = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[][] a = new String[3][3];
        int count = 0;
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                if (count == table.length) break; // if count == 7
                a[i][j] = table[count];
                count++;
            }
        }
        return a;
    }

}
