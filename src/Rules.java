
import java.util.List;
import java.util.Scanner;


public class Rules {
    private String player1;
    private String player2;
    private String win;
    Util utils = new Util();

    public void start() {
        var board = initBoard();
        int priority = utils.random();
        Scanner scanner = new Scanner(System.in);
        String currentPlayer;
        int turn = 1;

        while (win == null) {
            currentPlayer = priority == 1 ? player1 : player2;
            player1 = priority==1 ? "X" : "O";
            player2 = player1.equalsIgnoreCase("X") ? "O" : "X";


            if (priority == 1) {

                board = playerMove(board, scanner);

            } else {

                board = computerMove(player2, player1, board);
            }

            if (utils.checkWin(currentPlayer,board)) {
                win = currentPlayer;
                break;
            }
            priority = 3 - priority;

            if (turn == 9){
                win = "DRAW";
            }
            turn++;
        }

        if (win.equalsIgnoreCase("DRAW")) {
            System.out.println(utils.printBoard(board) + "\n!!AFTER HEROIC WAR NOOBODY WINS!!");

        } else {
            System.out.println(utils.printBoard(board) + "\n !! AFTER HEROIC WAR " + win + " WIN THIS DUEL !!");
        }
    }
    private String[][] playerMove(String[][] board, Scanner scanner) {

        System.out.println(utils.printBoard(board));
        System.out.println("!! PLAYER TURN !!");

        String move = scanner.next();


        while (!playerMoveValidation(move, board)) {
            System.out.println("Wrong input");
            move = scanner.next();
        }

        return putingSymbolByPlayer(move, board);
    }
    private String[][] computerMove(String player1, String player2, String[][] board) {
        System.out.println("!! BOT MOVED  !!");
        return new BotMiniMax(player1,player2).putingSymbolByComputer(board);
    }
    private boolean playerMoveValidation(String move, String[][] boardToChange) {
        final List<String> inValidMoves = List.of("X","O");
        //var moveAsIntiger = Integer.parseInt(move); // Po to aby ograniczyć parseInt x2 - ale potrzeba 2x if
        return move.matches("^[1-9]$")
                && !inValidMoves.contains(boardToChange[(Integer.parseInt(move) - 1) % 3][(Integer.parseInt(move) - 1) / 3]);
        /*
        if (!move.matches("^[1-9]$")). {
            return false;
        }
        // Check if the cell is already occupied
        int startX = (Integer.parseInt(move) - 1) / 3;
        int startY = (Integer.parseInt(move) - 1) % 3;

       return  !boardToChange[startY][startX].equalsIgnoreCase("X")
                        && !boardToChange[startY][startX].equalsIgnoreCase("O") ;*/
    }
    private String[][] putingSymbolByPlayer(String move, String[][] boardToChange) {
        var moveAsInt = Integer.parseInt(move);
        int startX = (moveAsInt - 1) / 3;
        int startY = (moveAsInt - 1) % 3;//%

        boardToChange[(moveAsInt - 1) % 3][(moveAsInt - 1) / 3] = player1;

        return boardToChange;
    }
    private String[][] initBoard() {
        String[] table = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[][] a = new String[3][3];
        int count = 0;
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                a[i][j] = table[count];
                count++;
            }
        }
        return a;
    }
}