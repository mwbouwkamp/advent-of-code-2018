package Day22;

import java.awt.*;

public class Region {

    static final int MOD = 20183;

    Point target;
    int depth;
    int[][] geographicalIndex;
    int[][] erosionIndex;
    int[][] regionType;

    public Region(Point target, int depth) {
        this.target = target;
        this.depth = depth;

//        geographicalIndex = new int[target.x + 1][target.y + 1];
//        erosionIndex = new int[target.x + 1][target.y + 1];
//        regionType = new int[target.x + 1][target.y + 1];

        geographicalIndex = new int[1000 * target.x + 1][3 * target.y + 1];
        erosionIndex = new int[1000 * target.x + 1][3 * target.y + 1];
        regionType = new int[1000 * target.x + 1][3 * target.y + 1];

        geographicalIndex[0][0] = 0;
        erosionIndex[0][0] = (geographicalIndex[0][0] + depth) % MOD;
        setErosionIndex(geographicalIndex, erosionIndex, new Point(0, 0), new Point(1000 * target.x, 3 * target.y));
        geographicalIndex[target.x][target.y] = 0;
        erosionIndex[target.x][target.y] = (geographicalIndex[target.x][target.y] + depth) % MOD;
        setRegionType(erosionIndex, regionType, new Point(0, 0), new Point(1000 * target.x, 3 * target.y));
    }

    private void setErosionIndex(int[][] geographicalIndex, int[][] erosionIndex, Point startPoint, Point endPoint) {
        for (int x = startPoint.x + 1; x <= endPoint.x; x++) {
            geographicalIndex[x][0] = 16807 * x;
            erosionIndex[x][0] = (geographicalIndex[x][0] + depth) % MOD;
            if (geographicalIndex[x][0] < 0) {
                System.out.println("!!!");

            }
        }
        for (int y = startPoint.y + 1; y <= endPoint.y; y++) {
            geographicalIndex[0][y] = 48271 * y;
            erosionIndex[0][y] = (geographicalIndex[0][y] + depth) % MOD;
        }
        for (int x = startPoint.x + 1; x <= endPoint.x; x++) {
            for (int y = startPoint.y + 1; y <= endPoint.y; y++) {
                geographicalIndex[x][y] = erosionIndex[x - 1][y] * erosionIndex[x][y - 1];
                erosionIndex[x][y] = (geographicalIndex[x][y] + depth) % MOD;
                if (geographicalIndex[x][y] < 0) {
                    System.out.println("!!!");

                }
            }
        }
    }

    private void setRegionType(int[][] erosionIndex, int[][] regionType, Point startPoint, Point endPoint) {
        for (int x = startPoint.x; x <= endPoint.x; x++) {
            for (int y = startPoint.y; y <= endPoint.y; y++) {
                int erosionLevel = erosionIndex[x][y] %3;
                regionType[x][y] = erosionLevel;
            }
        }
    }

    int getRisk() {
        int risk = 0;
        for (int x = 0; x <= target.x; x++) {
            for (int y = 0; y <= target.y; y++) {
                risk += regionType[x][y];
            }
        }
        return risk;
    }

    void extendX() {
        int newWidth = geographicalIndex.length * 2 - 1;
        int newHeight = geographicalIndex[0].length;
        extendRegion(newWidth, newHeight);
        System.out.println("extend X");
    }

    void extendY() {
        int newWidth = geographicalIndex.length;
        int newHeight = geographicalIndex[0].length * 2 - 1;
        extendRegion(newWidth, newHeight);
        System.out.println("extend Y");
    }

    private void extendRegion(int newWidth, int newHeight) {
        int oldWidth = geographicalIndex.length;
        int oldHeight = geographicalIndex[0].length;
        int[][] newGeographicalIndex = new int[newWidth][newHeight];
        int[][] newErosionIndex = new int[newWidth][newHeight];
        int[][] newRegionType = new int[newWidth][newHeight];
        copyExistingRegion(newGeographicalIndex, newErosionIndex, newRegionType);
        setErosionIndex(newGeographicalIndex, newErosionIndex, new Point(newWidth - oldWidth, newHeight - oldHeight), new Point(newWidth - 1, newHeight - 1));
        setRegionType(newErosionIndex, newRegionType, new Point(newWidth - oldWidth, newHeight - oldHeight), new Point(newWidth - 1, newHeight - 1));
        geographicalIndex = newGeographicalIndex;
        erosionIndex = newErosionIndex;
        regionType = newRegionType;
    }

    private void copyExistingRegion(int[][] newGeographicalIndex, int[][] newErosionIndex, int[][] newRegionType) {
        for (int x = 0; x < geographicalIndex.length; x++) {
            for (int y = 0; y < geographicalIndex[0].length; y++) {
                newGeographicalIndex[x][y] = geographicalIndex[x][y];
                newErosionIndex[x][y] = erosionIndex[x][y];
                newRegionType[x][y] = regionType[x][y];
            }
        }
    }

    int getRegionType(Point point) {
        return regionType[point.x][point.y];
    }

    @Override
    public String toString() {
        StringBuilder toPrint = new StringBuilder("\n");
        for (int y = 0; y < regionType[0].length; y++) {
            for (int[] ints : regionType) {
                switch (ints[y]) {
                    case 0:
                        toPrint.append(".");
                        break;
                    case 1:
                        toPrint.append("=");
                        break;
                    case 2:
                        toPrint.append("|");
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            }
            toPrint.append("\n");
        }
        return toPrint.toString();
    }

}
