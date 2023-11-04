/*
 * CS2050 - Computer Science II - Fall 2022
 * Instructor: Thyago Mota
 * Student(s) Name(s): Vincent Cordova, Ubaldo Bogarin
 * Description: prg_02 - Minesweeper
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Minesweeper extends JFrame {

    private boolean map[][];   // true if it's mined; false otherwise
    private char    board[][]; // control the game's board
    private int     size;
    private int     mines;
    private int     flagCounter;
//    MIN_SIZE and MIN_MINES are redundant since the board defaults to BEGINNER_LEVEL
//    private static final int MIN_SIZE = 6;
//    private static final int MIN_MINES = 1;

    private JPanel boardPanel;
    private JButton[][] buttonBoard;

    // TODOd #1: finish the implementation of the constructor according to the instructions
    public Minesweeper(int size, int mines) {

        this.size = size;
        this.mines = mines;
        this.flagCounter = mines;
        this.map = new boolean[this.size][this.size];
        this.board = new char[this.size][this.size];

        // Fill map with false
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.map[i][j] = false;
            }
        }
        // Randomly place mines around the map
        for (int m = 0; m < this.mines; m++) {
            hideMine();
        }
        // Fill the board with ?
        for (int k = 0; k < size; k++) {
            for (int l = 0; l < size; l++) {
                this.board[k][l] = '?';
            }
        }
        // Print the board for the first time
        System.out.println(this);
    }

    // displays the board
    @Override
    public String toString() {
        String str = "x\\y\t";
        str +=  "   ";
        for (int i = 0; i < this.size; i++) {
            str = (i > 9) ? (str + i + "    ") : (str + i + "     ");
        }
        str += "\n";
        str += "\t";
        str += "+";
        for (int i = 0; i < this.size;i++){
            str += "-----+";
        }
        str += "\n";
        for (int i = 0; i < this.size; i++) {
            str += i + "\t|  ";
            for (int j = 0; j < this.size; j++)
                str += board[i][j] + "  |  ";
            str += "\n";
            str += "\t";
            str += "+";
        for (int k = 0; k < this.size; k++)
                str += "-----+";
            str += "\n";
        }
        return str;
    }

    // tries to hide a mine at a random location; returns true if attempt was successful; false otherwise
    private boolean hideMine() {
        Random rnd = new Random();
        int x = rnd.nextInt(size);
        int y = rnd.nextInt(size);
        if (!map[x][y]) {
            map[x][y] = true;
            return true;
        }
        return false;
    }

    // returns true if the location is valid and mined, false otherwise
    private boolean isMined(int x, int y) {
        if (x < 0 || x > size - 1 || y < 0 || y > size - 1)
            return false;
        return map[x][y];
    }

    // returns true if the location is valid and flagged, false otherwise
    public boolean isFlagged(int x, int y) {
        if (x < 0 || x > size - 1 || y < 0 || y > size - 1)
            return false;
        return board[x][y] == '+';
    }

    // returns the number of hidden mines
    public int getMines() {
        return mines;
    }

    // returns the number of flags the player has left
    public int getFlags() {
        return flagCounter;
    }

    // TODOd #2: this method should return the number of mines (immediately) around the given location
    private int minesAround(int x, int y) {
        int mineCount = 0;
        // Loop through all spaces around x,y and return a number based on how many mines are found
        for (int i = x-1; i <= x+1; i++) {
            for (int j = y-1; j <= y+1; j++) {
                if (isMined(i, j)) mineCount++;
            }
        }
        return mineCount;
    }

    // TODOd #3: this method should return true if there is at least one tile in the board that reveals a mine
    public boolean isMineDetonated() {
        // Check the entire board, and if any coordinate on the visible board is found, a mine has been detonated
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == '*') {
                    System.out.println("KABOOM YOU LOST!");
                    return true;
                }
            }
        }
        return false;
    }

    // TODOd #4: this method should return true if ALL non-mined tiles were revealed OR a mine was detonated; false otherwise
    // hint: check if the number of tiles marked with '?' matches the number of mines; also look for a "detonated" mine
    public boolean isGameOver() {
        // Search the entire board and count how many '?' there are, end the game victoriously if num '?' == num '*'
        int qCounter = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++){
                if(board[i][j] == '?' || board[i][j] == '+') qCounter += 1;
            }
        }
        if (qCounter == getMines()) {
            System.out.println("YOU WON!");
            return true;
        }
        // If a mine is detonated, the game will end
        return isMineDetonated();
    }

    // same as reveal but with flag set to false
    public void reveal(int x, int y) {
        reveal(x, y, false);
    }

    // TODOd #5: this method should reveal the board tile based on the given location;
    // you must use the char conventions detailed in the README file
    // if you are not implementing the flag feature (bonus points) just ignore parameter "flag"
    void reveal(int x, int y, boolean flag) {
        // Make sure the input is within the board
        if(x < 0 || x > size || y < 0 || y > size) System.out.println("OUT OF BOUNDS! TRY AGAIN!");

        // If the player wants a flag, there is not already a flag at x,y and x,y isn't a numbered/cleared space, place a flag and decrement the flag counter
        if (flag && !isFlagged(x, y) && this.board[x][y] != ' ' && this.board[x][y] != (char)(minesAround(x, y)) + '0') {
            flagCounter--;
            this.board[x][y] = '+';
        }
        // If the player wants to remove a flag, change the flag back to a '?' and add it back to flagCounter
        else if (flag && isFlagged(x, y)) {
            flagCounter++;
            this.board[x][y] = '?';
        }
        // if x,y is on top of a mine, reveal mines
        else if (isMined(x, y)) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++){
                    if(map[i][j]) this.board[i][j] = '*';
                }
            }
        }
        // If x,y has any mines surrounding it, reveal the number of mines around it
        else if (minesAround(x, y) > 0) this.board[x][y] = (char)(minesAround(x, y) + '0');
        // If there is nothing on x,y reveal an empty space
        // Recursively check all adjacent spaces and reveal them if they are empty or have mines around them
        else
            for(int i = x - 1; i <= x + 1; i++)
                for(int j = y - 1;j <= y + 1; j++)
                    // If surrounding spaces are within the board's bounds, and haven't already been revealed, reveal them
                    if ((j > -1) && (j < board[0].length) && (i > -1) && (i < board.length) && (board[i][j] != ' ')){
                        board[i][j] = ' ';
                        reveal(i, j);
                    }
    }
}