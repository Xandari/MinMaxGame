import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Rules {
    String player1;
    String botOrPlayer2;
    int turn = 0;
    String win;
    String gameResoult;
    boolean validated;

    String printBoard(String[][] board) {
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
                win = "X";
            } else if (line.equalsIgnoreCase("OOO")) {
                win = "O";
            }

        }
        turn++;
        if (turn == 9) {
            win = "Draw";
        }
        System.out.println("Turn: " + turn);
        return win;
    } // return String winnerOrDraw = X / O / DRAW


    public int random() {
        return (int) (Math.random() * 2 + 1);
    }


    String start() {
        Scanner scanner = new Scanner(System.in);
        var board = initBoard();
        int priority = random();
        String move;
        player1 = "X";
        botOrPlayer2 = "O";

        while (win == null) {

            if (priority == 2) {
                board = new BotMiniMax(botOrPlayer2, player1).computerMove(board);
                System.out.println("!! BOT MOVED !!");
                win = winCheck(board);

            } else {

                System.out.println(printBoard(board));
                System.out.println("!! PLAYER TURN !!");


                move = scanner.next();
                while (!playerMoveValidation(move, board)) {

                    System.out.println("Wrong input");
                    move = scanner.next();
                }

                board = playerMove(move, board);
                win = winCheck(board);

            }


            if (!"DRAW".equalsIgnoreCase(win)) {

                board = new BotMiniMax(player1, botOrPlayer2).computerMove(board);
                System.out.println("!! BOT MOVED !!");
                win = winCheck(board);
            }
        } else{
            player1 = "O";
            botOrPlayer2 = "X";
            System.out.println(printBoard(board));

            if (!"DRAW".equalsIgnoreCase(win)) {

                board = new BotMiniMax(player1, botOrPlayer2).computerMove(board);
                System.out.println("!! BOT MOVED !!");
                win = winCheck(board);
            }

            System.out.println(printBoard(board));

            System.out.println("!! PLAYER TURN !!");
            do {
                move = scanner.next();
                board = playerMoveValidation(move, board);
                if (!validated) {
                    System.out.println("Wrong input");
                }
            } while (!validated);

            win = winCheck(board);

        }

    }
        if(win.equalsIgnoreCase("DRAW"))

    {
        gameResoult = "!!AFTER HEROIC WAR NOOBODY WINS!!";

    } else gameResoult ="!! AFTER HEROIC WAR "+win +" WIN THIS DUEL !!";
        return gameResoult;
}

    boolean playerMoveValidation(String move, String[][] boardToChange) {
        final List<String> validMoves = List.of("1", "2", "3", "4", "5", "6", "7", "8", "9");
//var moveAsIntiger = Integer.parseInt(move); // Po to aby ograniczyć parseInt x2 - ale potrzeba 2x if
        if (!move.matches("^[1-9]$")
                && !validMoves.contains(boardToChange[(Integer.parseInt(move) - 1) / 9][(Integer.parseInt(move) - 1) % 9])) {
            return false;
        }
        /*
        if (!move.matches("^[1-9]$")). {
            return false;
        }
        // Check if the cell is already occupied
        int startY = (Integer.parseInt(move) - 1) / 9;
        int startX = (Integer.parseInt(move) - 1) % 9;

       return  !boardToChange[startY][startX].equalsIgnoreCase("X")
                        && !boardToChange[startY][startX].equalsIgnoreCase("O") ;*/
        return true;
    }

    public String[][] playerMove(String move, String[][] boardToChange) {
        var moveAsInt = Integer.parseInt(move);
        int startY = (moveAsInt - 1) / 9;
        int startX = (moveAsInt - 1) % 9;

        boardToChange[startY][startX] = player1;

        return boardToChange;
    }

    private String[][] initBoard() {
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
