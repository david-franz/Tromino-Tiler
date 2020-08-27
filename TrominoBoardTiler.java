import java.awt.*;
import java.util.Set;
import java.util.HashSet;

public class TrominoBoardTiler {

    private int[][] board;

    private int trominoID = 1;
    public static final int MISSING_SQUARE = -1;

    public TrominoBoardTiler(int n, Point p) {
        if(!isPowerOfTwo(n)) throw new RuntimeException("Board size needs to be power of 2");

        initialiseBoard(n, p);
        tile(n, p);
    }

    private boolean isPowerOfTwo(int number) {
        return number > 0 && ((number & (number - 1)) == 0);
    }

    private void initialiseBoard(int n, Point p) {
        board = new int[n][n];
        board[p.y][p.x] = MISSING_SQUARE;
    }

    private void tile(int n, Point p) {
        if(n == 2) {
            Point startPoint = findStartPoint(p);
            for(int row = startPoint.y; row <= startPoint.y + 1; row++) {
                for(int col = startPoint.x; col <= startPoint.x + 1; col++) {
                    if(board[row][col] == 0) board[row][col] = trominoID;
                }
            }

            trominoID++;
            return;
        }

        Set<Point> missingSquares = findListOfMissingSquares(n, p);

        for(Point m : missingSquares) {
            board[m.y][m.x] = trominoID;
        }

        trominoID++;

        tile(n/2, p);

        for(Point m : missingSquares) {
            tile(n/2, m);
        }
    }

    private Point findStartPoint(Point p) {
        return new Point(getStartIndex(p.x), getStartIndex(p.y));
    }

    private int getStartIndex(int x) {
        if(isEven(x)) return x;
        else return x - 1;
    }

    private boolean isEven(int x) {
        return x % 2 == 0;
    }

    private Set<Point> findListOfMissingSquares(int n, Point p) {
        Set<Point> missingPoints = new HashSet<>();

        int[] partitions = { 1, 2, 3, 4 };

        Point displacement = new Point(
                calculateDisplacement(p.x, n), calculateDisplacement(p.y, n)
        );

        Point p0 = new Point(p.x - (n * displacement.x), p.y - (n * displacement.y));

        int partitionOfP = calculatePartitionOfP(
                new Point(calculatePartitionComponent(p0.x, n), calculatePartitionComponent(p0.y, n))
        );

        for(Integer i : partitions) {
            if(i == partitionOfP) continue;
            Point partitionOfi = partition(i, n);

            missingPoints.add(
                    new Point(n * displacement.x + partitionOfi.x, n * displacement.y + partitionOfi.y)
            );
        }

        return missingPoints;
    }

    private int calculateDisplacement(int x, int n) {
        int displacement = 0;
        while((x -= n) >= 0) displacement++;

        return displacement;
    }

    // x can be row or col
    private int calculatePartitionComponent(int x, int n) {
        if(x < (n/2)) return 0;
        else return 1;
    }

    private int calculatePartitionOfP(Point partition) {
        if(partition.y == 0 && partition.x == 0) return 1;
        if(partition.y == 0 && partition.x == 1) return 2;
        if(partition.y == 1 && partition.x == 0) return 3;
        if(partition.y == 1 && partition.x == 1) return 4;

        throw new Error("error");
    }

    private Point partition(int partitionType, int n) {
        switch(partitionType) {
            case 1: return new Point((n/2) - 1, (n/2) -1);
            case 2: return new Point((n/2), (n/2) - 1);
            case 3: return new Point((n/2) - 1, (n/2));
            case 4: return new Point((n/2), (n/2));
        }

        throw new Error("error");
    }

    public int[][] getBoard() {
        return board;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int row = 0; row < Main.BOARD_SIZE; row++) {
            for(int col = 0; col < Main.BOARD_SIZE; col++) {
                boolean isMissing = board[row][col] == MISSING_SQUARE;
                sb.append((isMissing? " " : "  ") + board[row][col]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
