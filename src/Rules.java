import java.io.InputStream;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.Stream;

public class Rules extends Constans {
    // String[] table = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
    String[][] board;
    String player1;
    String botOrPlayer2;
    String winnerOrDraw;
    String win;
    String gameResoult;

    boolean check;

    Rules() {                            // ------------- Class Constructor --------------
        this.board = Constans.board;
        this.player1 = Constans.player1;
        this.botOrPlayer2 = Constans.player2;
    }

    String printTable() {
        String str =
                "|---|---|---|"
                        + "\n| " + board[0][0] + "   " + board[1][0] + "   " + board[2][0] + " |"
                        + "\n|           |"
                        + "\n| " + board[0][1] + "   " + board[1][1] + "   " + board[2][1] + " |"
                        + "\n|           |"
                        + "\n| " + board[0][2] + "   " + board[1][2] + "   " + board[2][2] + " |"
                        + "\n|---|---|---|";
        return str;
    }

    String winCheck(String[][] boardToCheck) {

        for (int resoult = 0; resoult < 8; resoult++) {
            String line = null;
            switch (resoult) {
                case 0:
                    line = boardToCheck[0][0] + boardToCheck[1][0] + boardToCheck[2][0];
                    break;
                case 1:
                    line = boardToCheck[0][1] + boardToCheck[1][1] + boardToCheck[2][1];
                    break;
                case 2:
                    line = boardToCheck[0][2] + boardToCheck[1][2] + boardToCheck[2][2];
                    break;
                case 3:
                    line = boardToCheck[0][0] + boardToCheck[0][1] + boardToCheck[0][2];
                    break;
                case 4:
                    line = boardToCheck[1][0] + boardToCheck[1][1] + boardToCheck[1][2];
                    break;
                case 5:
                    line = boardToCheck[2][0] + boardToCheck[2][1] + boardToCheck[2][2];
                    break;
                case 6:
                    line = boardToCheck[0][0] + boardToCheck[1][1] + boardToCheck[2][2];
                    break;
                case 7:
                    line = boardToCheck[2][0] + boardToCheck[1][1] + boardToCheck[0][2];
                    break;
            }
            if (line.equalsIgnoreCase("XXX")) {
                winnerOrDraw = "X";
            } else if (line.equalsIgnoreCase("OOO")) {
                winnerOrDraw = "O";
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Check if cell is empty
                if (!board[i][j].equalsIgnoreCase("X") && !board[i][j].equalsIgnoreCase("Y")) {
                    break;
                } else if (i == 8) {
                    winnerOrDraw = "DRAW";
                }
            }
        }
        return winnerOrDraw;
    } // return String winnerOrDraw = X / O / DRAW


    public int random() {
        int randomPlayer;
        int random = (int) (Math.random() * 2 + 1);
        if (random == 1) randomPlayer = 1;
        else randomPlayer = 2;
        return randomPlayer;
    }

    public boolean checkForValue(int val, String[][] arr) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (arr[i][j].equalsIgnoreCase(String.valueOf(val)))
                    return true;
            }
        }
        return false; //it will reach here if return true was not called.
    }

    String[][] playerMove(String[][] board) {
        Scanner scanner = new Scanner(System.in);
        int scan;
        boolean goodMove = false;
        System.out.println(
                "Player: " + player1 + " Make your move. \nEnter the slot number."
                        + "\n" );
        scan = scanner.nextInt();
        // Stream<String> boardStream = (Stream<String>) Stream.of(board);
        while (!goodMove) {
            try {
                if (!(scan > 0 && scan <= 9)) {
                    System.out.println(
                            "Invalid number. Try again: ");
                    continue;
                }
            } catch (InputMismatchException e) {
                System.out.println(
                        "Invalid number. Try again: ");
                continue;
            }
            goodMove = true;
        }
        goodMove = false;
        while (!goodMove) {
            if (checkForValue(scan, board)) { // if 2
                setPlayer1ValueinBoard(scan);
                goodMove = true;
            } else {                                                     // else 2
                System.out.println(
                        "Slot already taken!! Choose another number:");
            }
        }

        return board;
    }

    public void setPlayer1ValueinBoard(int scanValue) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (count == scanValue) break;
                    board[i][j] = player1;
                    count++;
            }
        }
    }

    String start() {
        Scanner scanner = new Scanner(System.in);
        int playerpriority = random();
        BotMiniMax bot = new BotMiniMax(board);
        do {
            //int playerpriority = random();
            if (playerpriority == 1) {
                Constans.player1 = "X";
                Constans.player2 = "O";
                System.out.println(printTable());
                System.out.println("!! PLAYER TURN !!");
                board = playerMove(board);
                win = winCheck(board);
                System.out.println(printTable());
                board = bot.botMove(board);

                System.out.println("!! BOT MOVED !!");
                win = winCheck(board);
            } else {
                Constans.player1 = "O";
                Constans.player2 = "X";
                System.out.println(printTable());
                board = new BotMiniMax().botMove(board);
                System.out.println("!! BOT MOVED !!");
                win = winCheck(board);
                System.out.println(printTable());
                System.out.println("!! PLAYER MOVE !!");
                board = playerMove(board);
                win = winCheck(board);

            }
        } while (win == null);
        if (win.equalsIgnoreCase("DRAW")) {
            gameResoult = "!!AFTER HEROIC WAR NOOBODY WINS!!";
        } else gameResoult = "!! AFTER HEROIC WAR " + win + " WIN THIS DUEL !!";
        return gameResoult;
    }


}
