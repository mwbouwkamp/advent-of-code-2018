package Day25;

public class XYZTPoint {
    int x, y, z, t;

    XYZTPoint(int x, int y, int z, int t) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.t = t;
    }

    XYZTPoint(XYZTPoint point) {
        this.x = point.x;
        this.y = point.y;
        this.z = point.z;
        this.t = point.t;
    }
}
