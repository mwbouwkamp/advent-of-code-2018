import Day16.Day16;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
        Point pointToProcess = new Point(500,0);
        int x = pointToProcess.x;
        int y = pointToProcess.y + 1;
        Point pointBelow = new Point(x, y);
        System.out.println(pointBelow);
        Day16 day16 = new Day16();

    }
}
