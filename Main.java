import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class Main {

    public static final int BOARD_SIZE = 32;

    public static void main(String[] args) {
        Random r = new Random();

        int[][] board = new TrominoBoardTiler(
                BOARD_SIZE, new Point(r.nextInt(BOARD_SIZE), r.nextInt(BOARD_SIZE))
        ).getBoard();

        SwingUtilities.invokeLater(
                () -> new TrominoBoardDrawer(board).setVisible(true)
        );
    }

}
