// Eric Jackman

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    // Read user input, update game board, and send message to server
    private static String takeTurn(Scanner userIn, char[][] board) {
        int row;
        int col;
        while (true) {
            System.out.print("Enter your move (row and column): ");
            row = userIn.nextInt();
            col = userIn.nextInt();
            if ((row >= 0 && row < 4) && (col >= 0 && col < 4) && (board[row][col] == ' ')) {  // is move valid
                board[row][col] = 'O';
                return "MOVE " + row + " " + col;
            } else {
                System.out.println("Invalid move");
            }
        }
    }

    // Read server's move and update game board
    private static void updateBoard(String move, char[][] board) {
        int row;
        int col;
        row = Integer.parseInt(move.substring(5, 6));
        col = Integer.parseInt(move.substring(7, 8));
        board[row][col] = 'X';
    }

    // Read server's message to determine if the game has ended, and update and print the board if
    // the server made the final move
    private static boolean checkEnd(String message, char[][] board) {
        int row;
        int col;
        if (message.length() > 8) {
            if (message.length() == 14) {  // client won or tied
                if (message.startsWith("WIN", 11)) System.out.println("You won");
                else System.out.println("It's a tie");
            } else if (message.length() == 13) {  // server won
                updateBoard(message, board);
                printBoard(board);
                System.out.println("You lost");
            } else if (message.length() == 12) {  // tie (server played last)
                updateBoard(message, board);
                printBoard(board);
                System.out.println("It's a tie");
            }
            return true;
        }
        return false;
    }

    // Print the game board to the console
    private static void printBoard(char[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                System.out.print(board[row][col]);
                if (col != 3) {
                    System.out.print(" | ");
                } else {
                    System.out.print("\n");
                }
            }
        }
        System.out.print("\n");
    }

    public static void main(String[] args) {
        try (
                Socket toServer = new Socket("localhost", 8775);
                DataInputStream instream = new DataInputStream(toServer.getInputStream());
                DataOutputStream outstream = new DataOutputStream(toServer.getOutputStream());
                PrintWriter out = new PrintWriter(outstream, true);
                BufferedReader in = new BufferedReader(new InputStreamReader(instream));
                Scanner userIn = new Scanner(System.in);
        ) {
            char[][] board = {{' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' '},
                    {' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' '}};
            String message;  // stores message from server
            int turn = 1;  // used for first turn logic

            // Find out who goes first
            message = in.readLine();
            if (message.equals("Client")) {
                // Client goes first
                while (true) {  // loop for the rest of the game
                    // Client's turn
                    if (checkEnd(message, board)) break;
                    out.println(takeTurn(userIn, board));
                    printBoard(board);

                    // Server's turn
                    message = in.readLine();
                    if (checkEnd(message, board)) break;
                    System.out.println("Server Move");
                    updateBoard(message, board);
                    printBoard(board);
                }
            } else {
                // Server goes first
                while (true) {  // loop for the rest of the game
                    // Server's turn
                    if (turn != 1) {  // prevents client from attempting to read a second message on the first turn
                        message = in.readLine();
                    } else turn++;
                    if (checkEnd(message, board)) break;
                    System.out.println("Server Move");
                    updateBoard(message, board);
                    printBoard(board);

                    // Client's turn
                    if (checkEnd(message, board)) break;
                    out.println(takeTurn(userIn, board));
                    printBoard(board);
                }
            }
        } catch (IOException e) {
            System.err.println("Could not connect to server");
            System.exit(1);
        }
    }
}
