import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Minesweeper2 extends JFrame {

    private static int size;
    private static int mines;
    private boolean map[][];
    

    private JPanel boardPanel;
    private JButton[][] board;

    public Minesweeper2(int size, int mines) {

        this.size = size;
        this.mines = mines;

        this.map = new boolean[this.size][this.size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.map[i][j] = false;
            }
        }

        for (int m = 0; m < this.mines; m++) {
            hideMine();
        }

        boardPanel = new JPanel(new GridLayout(size, size));
        board = new JButton[size][size];
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                board[r][c] = new JButton("");
                board[r][c].addActionListener(new ButtonListener());
                boardPanel.add(board[r][c]);
            }
        }
        add(boardPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private boolean hideMine() {
        Random r = new Random();
        int x = r.nextInt(size);
        int y = r.nextInt(size);
        if (!map[x][y]) {
            map[x][y] = true;
            return true;
        }
        return false;
    }

    private boolean isMined(int x, int y) {
        if (x < 0 || x > size - 1 || y < 0 || y > size - 1)
            return false;
        return map[x][y];
    }

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

    public boolean isMineDetonated() {
        // Check the entire board, and if any coordinate on the visible board is found, a mine has been detonated
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JButton button = board[i][j];
                Color backgroundColor = button.getBackground();
                if (backgroundColor.equals(Color.RED)) {
                    System.out.println("KABOOM YOU LOST!");
                    return true;
                }
            }
        }
        return false;
    }

    public int getMines() {
        return mines;
    }

    public boolean isGameOver() {
        // Search the entire board and count how many '?' there are, end the game victoriously if num '?' == num '*'
        int qCounter = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++){
                JButton button = board[i][j];
                Color backgroundColor = button.getBackground();
                if(backgroundColor.equals(Color.white)) qCounter ++;
            }
        }
        if (qCounter == getMines()) {
            System.out.println("YOU WON!");
            return true;
        }
        // If a mine is detonated, the game will end
        return isMineDetonated();
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton)e.getSource();
            button.setBackground(Color.GREEN);

        }
    }

    void reveal(int x, int y, JButton button) {
        
        if(isMined(x,y)) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if(map[i][j]) button.setBackground(Color.RED);
                }
            }
        }
        else if (minesAround)

    }

}
