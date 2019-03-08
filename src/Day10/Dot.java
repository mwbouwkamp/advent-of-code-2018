package Day10;

import java.awt.*;

class Dot {
    Point point;
    int vX, vY;

    public Dot(Point point, int vX, int vY) {
        this.point = point;
        this.vX = vX;
        this.vY = vY;
    }

    public void move() {
        point.x = point.x + vX;
        point.y = point.y + vY;
    }
}
