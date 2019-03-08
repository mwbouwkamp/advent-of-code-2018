package Day17;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;



public class Test {

    public static void main(String[] args) {
        char[][] slice = new char[1000][1000];
        Point startingPoint = new Point(500, 2);
        ArrayList<Point> fringe = new ArrayList<>();
        fringe.add(startingPoint);
        int wet = 0;
        while (fringe.size() > 0) {
            Point pointToProcess = fringe.remove(0);
            int x = pointToProcess.x;
            int y = pointToProcess.y + 1;
            Point pointBelow = new Point(x, y);
            int i = 9;


        }

    }

}
