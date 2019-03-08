package Day10;

import java.awt.*;

class PointStatistics {
    private int minX = Integer.MAX_VALUE;
    private int maxX = Integer.MIN_VALUE;

    private int minY = Integer.MAX_VALUE;
    private int maxY = Integer.MIN_VALUE;

    public void accept(Point p) {
        minX = Math.min(minX, p.x);
        maxX = Math.max(maxX, p.x);

        minY = Math.min(minY, p.y);
        maxY = Math.max(minY, p.y);
    }

    public void combine(PointStatistics o) {
        minX = Math.min(minX, o.minX);
        maxX = Math.max(maxX, o.maxX);

        minY = Math.min(minY, o.minY);
        maxY = Math.max(maxY, o.maxY);
    }

    // getters
    public int getMinX() {
        return minX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }
}
