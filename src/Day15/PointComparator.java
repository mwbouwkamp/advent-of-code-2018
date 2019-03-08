package Day15;

import java.awt.*;
import java.util.Comparator;

public class PointComparator implements Comparator<Point> {

    @Override
    public int compare(Point point1, Point point2) {
        if (point1.y > point2.y) {
            return 1;
        }
        else if (point1.y < point2.y) {
            return -1;
        }
        else if (point1.x > point2.x) {
            return 1;
        }
        else if (point1.x < point2.x) {
            return -1;
        }
        return 0;
    }
}

