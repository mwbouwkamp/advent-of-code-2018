package Day17;

public class Point {
    public int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point point) {
        this.x = point.x;
        this.y = point.y;
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }

    @Override
    public boolean equals(Object o) {
        Point toCompare = (Point) o;
        return (x == toCompare.x && y == toCompare.y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
