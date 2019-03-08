package Day25;

import java.util.ArrayList;

public class Constellation {
    ArrayList<XYZTPoint> points;

    Constellation(XYZTPoint firstPoint) {
        points = new ArrayList<XYZTPoint>();
        points.add(firstPoint);
    }

    boolean inConstellation(XYZTPoint newPoint) {
        return points
                .stream()
                .anyMatch(point -> ManhattenCalculator.manhattenCalculator(point, newPoint) <= 3);
    }

}
