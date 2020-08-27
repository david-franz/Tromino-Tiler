import java.awt.*;
import javax.swing.*;
import java.util.Set;
import java.util.HashSet;

public class TrominoBoardDrawer extends JFrame {

    private static final int SQUARE_SIZE = 16;

    private static final int TOOLBAR_HEIGHT = 22;
    private static final int OFFSET = 5;

    enum Direction { NORTH, EAST, SOUTH, WEST };

    public TrominoBoardDrawer(int[][] board) {
        setTitle("Tromino Tiler");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setBackground(Color.WHITE);

        int rows = board.length;
        int cols = board[0].length;
        setPreferredSize(
                new Dimension(2 * OFFSET + rows * SQUARE_SIZE, 2 * OFFSET + cols * SQUARE_SIZE + TOOLBAR_HEIGHT));

        add(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(Color.BLACK);
                for(int row = 0; row < rows; row++) {
                    for(int col = 0; col < cols; col++) {

                        int trominoID = board[row][col];

                        if(trominoID == TrominoBoardTiler.MISSING_SQUARE) {
                            g.fillRect(OFFSET + col * SQUARE_SIZE,
                                    OFFSET + row * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                            g.drawRect(OFFSET + col * SQUARE_SIZE,
                                    OFFSET + row * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                            continue;
                        }

                        drawAdjacentSquareLines(g, row, col);
                    }
                }
            }

            private void drawAdjacentSquareLines(Graphics g, int row, int col) {
                Set<Direction> directions = findAdjacentSquares(row, col);
                for(Direction d : directions) {
                    switch(d) {
                        case NORTH:
                            g.drawLine(OFFSET + col * SQUARE_SIZE, OFFSET + row * SQUARE_SIZE,
                                    OFFSET + (col * SQUARE_SIZE) + SQUARE_SIZE, OFFSET + row * SQUARE_SIZE);
                            break;
                        case EAST:
                            g.drawLine(OFFSET + (col * SQUARE_SIZE) + SQUARE_SIZE, OFFSET + row * SQUARE_SIZE,
                                    OFFSET + (col * SQUARE_SIZE) + SQUARE_SIZE, OFFSET + (row * SQUARE_SIZE) + SQUARE_SIZE);
                            break;
                        case SOUTH:
                            g.drawLine(OFFSET + col * SQUARE_SIZE, OFFSET + (row * SQUARE_SIZE) + SQUARE_SIZE,
                                    OFFSET + (col * SQUARE_SIZE) + SQUARE_SIZE, OFFSET + (row * SQUARE_SIZE) + SQUARE_SIZE);
                            break;
                        case WEST:
                            g.drawLine(OFFSET + col * SQUARE_SIZE, OFFSET + row * SQUARE_SIZE,
                                    OFFSET + col * SQUARE_SIZE, OFFSET + (row * SQUARE_SIZE) + SQUARE_SIZE);
                            break;
                    }
                }
            }

            private Set<Direction> findAdjacentSquares(int row, int col) {
                Set<Direction> d = new HashSet<>();
                int trominoID = board[row][col];

                int rows = board.length;
                int cols = board[0].length;

                if((row - 1 < 0) || trominoID != board[row - 1][col]) {
                    d.add(Direction.NORTH);
                }
                if((row + 1 == rows) || trominoID != board[row + 1][col]) {
                    d.add(Direction.SOUTH);
                }
                if((col - 1 < 0) || trominoID != board[row][col - 1]) {
                    d.add(Direction.WEST);
                }
                if((col + 1 == cols) || trominoID != board[row][col + 1]) {
                    d.add(Direction.EAST);
                }

                return d;
            }
        });

        pack();
    }
}
