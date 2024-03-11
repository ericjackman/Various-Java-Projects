// Eric Jackman

import java.io.*;
import java.net.*;
import java.util.Random;

public class ServerThread extends Thread {
    private Socket socket;
    private int turn;

    public ServerThread(Socket socket) {
        this.socket = socket;
        this.turn = 0;
    }

    // Pick a random open space to play and update game board
    private int[] takeTurn(char[][] board) {
        int row;
        int col;
        Random rand = new Random();

        while (true) {  // loop until a valid move is found
            row = rand.nextInt(4);
            col = rand.nextInt(4);
            if (board[row][col] == ' ') {  // check if move is valid
                int[] move = {row, col};
                board[row][col] = 'X';
                turn++;

                // Short delay to make client interface more readable
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }

                return move;
            }
        }
    }

    // Check to see if server's move ended the game
    private String checkServerWin(char[][] board, int x, int y) {
        // Check rows
        for (int i = 0; i < board.length; i++) {
            if (board[i][y] != 'X')
                break;
            if (i == board.length - 1) {
                return "MOVE " + x + " " + y + " LOSS";
            }
        }

        // Check columns
        for (int i = 0; i < board.length; i++) {
            if (board[x][i] != 'X')
                break;
            if (i == board.length - 1) {
                return "MOVE " + x + " " + y + " LOSS";
            }
        }

        // Check diagonals
        if ((board[0][0] == 'X' && board[1][1] == 'X' && board[2][2] == 'X' && board[3][3] == 'X') ||
                (board[0][3] == 'X' && board[1][2] == 'X' && board[2][1] == 'X' && board[3][0] == 'X')) {
            return "MOVE " + x + " " + y + " LOSS";
        }

        // Check for tie
        if (turn == 16) {
            return "MOVE " + x + " " + y + " TIE";
        }

        return "MOVE " + x + " " + y;
    }

    // Check to see if the client's move ended the game
    private String checkClientWin(char[][] board, String move) {
        int x;
        int y;
        x = Integer.parseInt(move.substring(5, 6));
        y = Integer.parseInt(move.substring(7, 8));
        board[x][y] = 'O';

        // Check rows
        for (int i = 0; i < board.length; i++) {
            if (board[i][y] != 'O')
                break;
            if (i == board.length - 1) {
                return "MOVE -1 -1 WIN";
            }
        }

        // Check columns
        for (int i = 0; i < board.length; i++) {
            if (board[x][i] != 'O')
                break;
            if (i == board.length - 1) {
                return "MOVE -1 -1 WIN";
            }
        }

        // Check diagonals
        if ((board[0][0] == 'O' && board[1][1] == 'O' && board[2][2] == 'O' && board[3][3] == 'O') ||
                (board[0][3] == 'O' && board[1][2] == 'O' && board[2][1] == 'O' && board[3][0] == 'O')) {
            return "MOVE -1 -1 WIN";
        }

        // Check for tie
        if (turn == 16) {
            return "MOVE -1 -1 TIE";
        }

        return "";
    }

    public void run() {
        try (

        ) {
            char[][] board = {{' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' '},
                    {' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' '}};
            int[] move;  // array of two integers representing a space on the board
            String message;  // stores message from client
            String result;  // stores message to clientDataInputStream instream = new DataInputStream(socket.getInputStream());
            DataOutputStream outstream = new DataOutputStream(socket.getOutputStream());
            PrintWriter out = new PrintWriter(outstream, true);
            BufferedReader in = new BufferedReader(new InputStreamReader(instream));

            // Flip a coin to see who goes first
            Random rand = new Random();
            int coin = rand.nextInt(2);

            if (coin == 0) {  // server goes first
                while (true) {  // loop until the game ends
                    // Server's turn
                    move = takeTurn(board);
                    result = checkServerWin(board, move[0], move[1]);
                    out.println(result);

                    if (result.length() > 8) {  // check for game end
                        break;
                    }

                    // Client's turn
                    message = in.readLine();
                    turn++;
                    result = checkClientWin(board, message);

                    if (!result.equals("")) {  // check for the end of the game
                        out.println(result);
                        break;
                    }
                }
            } else {  // client goes first
                out.println("Client");  // let client know they go first

                while (true) {  // loop until the game ends
                    // Client's turn
                    message = in.readLine();
                    turn++;
                    result = checkClientWin(board, message);

                    if (!result.equals("")) {  // check for the end of the game
                        out.println(result);
                        break;
                    }

                    // Server's turn
                    move = takeTurn(board);
                    result = checkServerWin(board, move[0], move[1]);
                    out.println(result);

                    if (result.length() > 8) {  // check for game end
                        break;
                    }
                }
            }
        } catch (IOException e) {
        }
    }
}
