package Day17;

import java.util.ArrayList;
import java.util.List;

public class Slice {

    int minX, minY, maxX, maxY;
    int minClayY;
    char[][] sliceArray;

    public Slice(List<String> lines) {
        minX = Integer.MAX_VALUE;
        minY = 0;
        maxX = Integer.MIN_VALUE;
        maxY = Integer.MIN_VALUE;
        minClayY = Integer.MAX_VALUE;
        for (String line: lines) {
            String[] instruction = line.split(", ");
            int coord = Integer.parseInt(instruction[0].substring(2));
            String[] range = instruction[1].substring(2).split("\\..");
            int startRange = Integer.parseInt(range[0]);
            int endRange = Integer.parseInt(range[1]);
            if (line.charAt(0) == 'x') {
                minX = Math.min(minX, coord);
                maxX = Math.max(maxX, coord);
                minY = Math.min(minY, startRange);
                maxY = Math.max(maxY, endRange);
                minClayY = Math.min(minClayY, startRange);
            }
            else {
                minY = Math.min(minY, coord);
                maxY = Math.max(maxY, coord);
                minClayY = Math.min(minClayY, coord);
                minX = Math.min(minX, startRange);
                maxX = Math.max(maxX, endRange);
            }
        }
        minX--;
        maxX++;
        maxX++;
        maxY++;
        sliceArray = new char[maxX - minX][maxY - minY];
        for (int i = minX; i < maxX; i++) {
            for (int j = minY; j < maxY; j++) {
                setElement(i, j, '.');
            }
        }

        ArrayList<int[]> xInstructions = new ArrayList<>();
        ArrayList<int[]> yInstructions = new ArrayList<>();
        for (String line: lines) {
            String[] instruction = line.split(", ");
            int coord = Integer.parseInt(instruction[0].substring(2));
            String[] range = instruction[1].substring(2).split("\\..");
            int startRange = Integer.parseInt(range[0]);
            int endRange = Integer.parseInt(range[1]);
            if (line.charAt(0) == 'x') {
                xInstructions.add(new int[] {coord, startRange, endRange});
            }
            else {
                yInstructions.add(new int[] {coord, startRange, endRange});
            }
        }
        for (int[] xInstruction: xInstructions) {
            for (int i = xInstruction[1]; i <= xInstruction[2]; i++) {
                setElement(xInstruction[0], i, '#');
            }
        }
        for (int[] yInstruction: yInstructions) {
            for (int i = yInstruction[1]; i <= yInstruction[2]; i++) {
                setElement(i, yInstruction[0], '#');
            }
        }
        setElement(500, 0, '+');
    }

    char[][] getSliceArray() {
        return sliceArray;
    }

    void combineWithOriginal(Slice original) {
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                if (original.getSliceArray()[i][j] == '#') {
                    sliceArray[i][j] = '▓';
                }
            }
        }

    }
    void setElement(int x, int y, char type) {
        sliceArray[x - minX][y - minY] = type;
    }

    public int getWidth() {
        return maxX - minX;
    }

    public int getHeight() {
        return maxY - minY;
    }

    char getElement(Point point) {
        return sliceArray[point.x - minX][point.y - minY];
    }

    int getYMax() {
        return maxY;
    }

    public void print() {
        for (int i = 0; i < sliceArray[0].length; i++) {
            System.out.println();
            for (int j = 0; j < sliceArray.length; j++) {
                System.out.print(sliceArray[j][i]);
            }
        }
        System.out.println();
    }

    public int countWet() {
        int wet = 0;
        for (int i = minClayY; i < sliceArray[0].length; i++) {
            for (int j = 0; j < sliceArray.length; j++) {
                if (sliceArray[j][i] =='~' || sliceArray[j][i] == '|' || sliceArray[j][i] == '+') {
                    wet++;
                }
            }
        }
        return wet;
    }

    public int countAlwaysWet() {
        int wet = 0;
        for (int i = minClayY; i < sliceArray[0].length; i++) {
            for (int j = 0; j < sliceArray.length; j++) {
                if (sliceArray[j][i] =='~' || sliceArray[j][i] == '+') {
                    wet++;
                }
            }
        }
        return wet;
    }

    public void removeOverflow() {
        for (int i = 0; i < sliceArray[0].length; i++) {
            for (int j = 0; j < sliceArray.length - 1; j++) {
                String twoChars = sliceArray[j][i] + "" + sliceArray[j + 1][i];
                if (twoChars.equals("|~")) {
                    sliceArray[j + 1][i] = '|';
                }
            }
            for (int j = sliceArray.length - 1; j > 0; j--) {
                String twoChars = sliceArray[j][i] + "" + sliceArray[j - 1][i];
                if (twoChars.equals("|~")) {
                    sliceArray[j - 1][i] = '|';
                }
            }
        }
    }

}
