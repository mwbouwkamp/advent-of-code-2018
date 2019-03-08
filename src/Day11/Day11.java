package Day11;

import java.awt.*;

public class Day11 {

    public static final int GRID_SIZE = 300;
    public static int[][] grid;
    public static int serial;

    public static void main(String[] args) {

//        Fuel cell at  122,79, grid serial number 57: power level -5.
//        Fuel cell at 217,196, grid serial number 39: power level  0.
//        Fuel cell at 101,153, grid serial number 71: power level  4.
//        Serial: 5235

//        // Test cases
//        serial = 57;
//        System.out.println(calcLevel(new Point(122, 79)));
//        serial = 39;
//        System.out.println(calcLevel(new Point(217, 196)));
//        serial = 71;
//        System.out.println(calcLevel(new Point(101, 153)));

        serial = 5235;
//        serial = 18;
        grid = new int[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = calcLevel(new Point(i, j));
            }
        }

        Point result = new Point(-1, -1);
        int max = Integer.MIN_VALUE;

        int squareSize = 0;
        while (squareSize <= GRID_SIZE) {
            for (int i = 0; i <= GRID_SIZE - squareSize; i++) {
                for (int j = 0; j <= GRID_SIZE - squareSize; j++) {
                    int curMax = 0;
                    for (int k = 0; k < squareSize; k++) {
                        for (int l = 0; l < squareSize; l++) {
                            curMax += grid[k + i][l + j];
                        }
                    }
                    if (curMax > max) {
                        result = new Point(i, j);
                        max = curMax;
                    }
                }
            }

            System.out.println("Square size: " + squareSize + "; max = " + max);
            squareSize++;
        }
        System.out.println(result);

    }

    public static int calcLevel(Point point) {
        int rackID = point.x + 10;
        int level = rackID * point.y;
        level += serial;
        level *= rackID;
        int hundred = (level - (1000 * (level / 1000))) / 100;
        level = hundred - 5;
        return level;
    }
}
