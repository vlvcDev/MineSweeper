/*
 * CS2050 - Computer Science II - Fall 2022
 * Instructor: Thyago Mota
 * Student(s) Name(s): Vincent Cordova, Ubaldo Bogarin
 * Description: prg_02 - Minesweeper
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class MinesweeperGame {

    static final int BEGINNER_LEVEL     = 1;
    static final int INTERMEDIATE_LEVEL = 2;
    static final int EXPERT_LEVEL       = 3;


    // TODOd #6: finish the implementation of the game according to the instructions
    public static void main(String[] args) {

        boolean playing = true;
        int mines;
        int size;
        String again;

        Scanner s = new Scanner(System.in);
        while (playing) {
            System.out.print(""" 
            Choose your difficulty number:
            BEGINNER (1)
            INTERMEDIATE (2)
            EXPERT (3)
            """);
            System.out.print("\nDifficulty (1, 2 or 3): ");
            int diff = 0;
            while (diff == 0) {
                try {
                    diff = s.nextInt();
                } catch (InputMismatchException e) {
                    s.next();
                    System.out.println("Try again, input: 1, 2, or 3");
                }
            }
            if (diff == EXPERT_LEVEL) {
                size = 20;
                mines = 50;
            } else if (diff == INTERMEDIATE_LEVEL) {
                size = 15;
                mines = 25;
            } else {
                size = 10;
                mines = 10;
            }

            Minesweeper minesweeper = new Minesweeper(size, mines);

            int row;
            int column;

            while (!minesweeper.isGameOver()) {
                System.out.println("Flags remaining: " + minesweeper.getFlags());
                System.out.println("Choose a row and column in range 0 - " + (size - 1));
                System.out.print("(Flag)Row, Column: ");
                try {
                    String x = s.next();

                    // If player wants to add/remove flag
                    if (x.charAt(0) == '+') {
                        // Isolate the numbers x and y into an array, use substring to remove '+' from parseInt call
                        row = Integer.parseInt(x.substring(1).split(",")[0]);
                        column = Integer.parseInt(x.substring(1).split(",")[1]);
                        // if the player has already flagged this coordinate, remove it
                        // If the player is out of flags, print message and do nothing
                        // If the player has flags and the coordinate is not already flagged, flag it
                        if (minesweeper.isFlagged(row, column))
                            minesweeper.reveal(row, column, true);
                        else if (minesweeper.getFlags() == 0) System.out.println("Out of flags!");
                        else minesweeper.reveal(row, column, true);

                    } else {
                        // If the player doesn't want a flag, isolate the x and y coordinates and call reveal
                        // Flag is defaulted to false
                        row = Integer.parseInt(x.split(",")[0]);
                        column = Integer.parseInt(x.split(",")[1]);
                        minesweeper.reveal(row, column);
                    }
                    System.out.println("\n" + minesweeper);
                // If the player inputs anything other than x,y or +x,y do not let the program crash, and retry input
                } catch (NumberFormatException | InputMismatchException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("\nTRY AGAIN! Your input must look like: x,y or +x,y\n");
                }
            }
            // If the player would like to play again, do not change playing to false
            // If the player would like to stop, change playing to false, ending the gameplay loop
            System.out.print("Would you like to play again? (y/n): ");
            again = s.next();
            if (again.equals("n")) {
                playing = false;
                System.out.println("Thank you for playing!");
            }


        }
    }
}
