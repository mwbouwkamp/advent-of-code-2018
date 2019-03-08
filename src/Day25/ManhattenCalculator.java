package Day25;

public class ManhattenCalculator {
    static int manhattenCalculator(XYZTPoint point1, XYZTPoint point2) {
        return Math.abs(point1.x - point2.x) + Math.abs(point1.y - point2.y) + Math.abs(point1.z - point2.z) + Math.abs(point1.t - point2.t);
    }
}
