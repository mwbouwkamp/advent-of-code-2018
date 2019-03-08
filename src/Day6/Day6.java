package Day6;

import sun.awt.image.ImageWatched;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.security.cert.PolicyNode;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Day6 {

    public static void main(String[] args) {
        File file = new File("E:\\input5.txt");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            List<String> lines = bufferedReader.lines().collect(Collectors.toList());
            List<Point> points = new LinkedList<>();
            for (String line: lines) {
                String[] coords = line.split(", ");
                Point pointToAdd = new Point(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
                points.add(pointToAdd);
            }

//            points = new LinkedList<>();
//            points.add(new Point(1, 1));
//            points.add(new Point(1, 6));
//            points.add(new Point(8, 3));
//            points.add(new Point(3, 4));
//            points.add(new Point(5, 5));
//            points.add(new Point(8, 9));

            int minX = points.stream().mapToInt(p -> p.x).min().orElse(Integer.MIN_VALUE);
            int minY = points.stream().mapToInt(p -> p.y).min().orElse(Integer.MIN_VALUE);
            int maxX = points.stream().mapToInt(p -> p.x).max().orElse(Integer.MAX_VALUE);
            int maxY = points.stream().mapToInt(p -> p.y).max().orElse(Integer.MAX_VALUE);
            LinkedList<Point> infinites = new LinkedList<>();
            for (Point point: points) {
                if (isFinite(point, points)) {
                    infinites.add(point);
                }
            }
//            for (Point point: points) {
//                if (point.x == minX || point.x == maxX || point.y == minY || point.y == maxY) {
//                    infinites.add(point);
//                }
//            }
            int width = maxX - minX;
            int height = maxY - minY;
            int xOffset = minX;
            int yOffset = minY;
            Point[][] board = new Point[width * 3][height * 3];
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    Point toCheck = new Point(j - width, i - width);
                    List<Point> closestPoints = new LinkedList<>();
                    double distanceClosestPoint = Double.MAX_VALUE;
                    for (Point pointToCompare: points) {
                        double currentDistance = mDistance(pointToCompare, toCheck);
                        if (currentDistance < distanceClosestPoint) {
                            closestPoints = new LinkedList<>();
                            closestPoints.add(pointToCompare);
                            distanceClosestPoint = currentDistance;
                        }
                        else if (currentDistance == distanceClosestPoint) {
                            closestPoints.add(pointToCompare);
                        }
                    }
                    if (closestPoints.size() == 1) {
                        board[i][j] = closestPoints.get(0);
                    }
                    else {
                        board[i][j] = new Point(-1, -1);
                    }
                }
            }
            Map<Point, Integer> pointFrequency = new HashMap<>();
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (!pointFrequency.keySet().contains(board[i][j])) {
                        pointFrequency.put(board[i][j], 1);
                    }
                    else {
                        pointFrequency.put(board[i][j], pointFrequency.get(board[i][j]) + 1);
                    }
                }
            }

            LinkedList<Point> originalPoints = new LinkedList<>(points);
            for (Point point: infinites) {
                pointFrequency.remove(point);
            }
            pointFrequency.remove(new Point(-1, -1));
            int result = pointFrequency.values().stream().mapToInt(a -> a).max().orElse(-1);
            System.out.println(result);



            //Day 6b
            int MAXDIST = 10000;
            int[][] resultArray = new int[board.length][board.length];
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    int distanceToCheck = 0;
                    Point toCheck = new Point(j - width, i - width);
                    for (Point point: originalPoints) {
                        distanceToCheck += mDistance(point, toCheck);
                        if (distanceToCheck > MAXDIST) {
                            resultArray[i][j] = 0;
                            break;
                        }
                    }
                    if (distanceToCheck <= MAXDIST) {
                        resultArray[i][j] = 1;
                    }
                }
            }
            result = 0;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    result += resultArray[i][j];
                }
            }
            System.out.println(result);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static long mDistance(Point a, Point b) {
        return Math.abs((a.x - b.x)) + Math.abs((a.y - b.y));
    }

    private static boolean isFinite(Point point, List<Point> points) {
        boolean xMinFinite = true;
        boolean xMaxFinite = true;
        boolean yMinFinite = true;
        boolean yMaxFinite = true;
        for (Point pointToCheck: points) {
            if (point != pointToCheck) {
                if (point.x - pointToCheck.x >= Math.abs(pointToCheck.y - point.y)) {
                    xMinFinite = false;
                }
                if (pointToCheck.x - point.x >= Math.abs(pointToCheck.y - point.y) ) {
                    xMaxFinite = false;
                }
                if (point.y - pointToCheck.y >= Math.abs(pointToCheck.x - point.x)) {
                    yMinFinite = false;
                }
                if (pointToCheck.y - point.y >= Math.abs(pointToCheck.x - point.x) ) {
                    yMaxFinite = false;
                }
            }
        }
        return xMinFinite || xMaxFinite || yMinFinite || yMaxFinite;
//
//
//
//        Point minXExtreme = new Point(Integer.MIN_VALUE, point.y);
//        Point minYExtreme = new Point(point.x, Integer.MIN_VALUE);
//        Point maxXExtreme = new Point(Integer.MAX_VALUE, point.y);
//        Point maxYExtreme = new Point(point.x, Integer.MAX_VALUE);
//        boolean toReturn = true;
//        for (Point pointToCheck: points) {
//            if (mDistance(point, minXExtreme) < mDistance(pointToCheck, minXExtreme)
//                    || mDistance(point, minYExtreme) < mDistance(pointToCheck, minYExtreme)
//                    || mDistance(point, maxXExtreme) < mDistance(pointToCheck, maxXExtreme)
//                    || mDistance(point, maxYExtreme) < mDistance(pointToCheck, maxYExtreme)) {
//                toReturn = false;
//            }
//
//        }
//        return toReturn;

    }
}
