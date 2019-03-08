package Day17;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;



public class Day17 {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> lines = getInput();
        Slice original = new Slice(lines);
        Slice slice = new Slice(lines);

        Point startingPoint = new Point(500, 0);
        ArrayList<Point> fringe = new ArrayList<>();
        ArrayList<Point> visited = new ArrayList<>();
        fringe.add(startingPoint);
        while (fringe.size() > 0) {
//            printIntermediateState(slice, fringe);
//            Point pointToProcess = fringe.remove(fringe.size() - 1);
            Point pointToProcess = fringe.remove(0);
            if (pointToProcess.y < slice.getYMax() - 1) {
                Point pointBelow = new Point(pointToProcess.x, pointToProcess.y + 1);
                char characterBelow = slice.getElement(pointBelow);
                switch (characterBelow) {
                    case '|':
                        break;
                    case '.':
                        //Continue water stream downwards
                        slice.setElement(pointBelow.x, pointBelow.y, '|');
                        if (!fringe.contains(pointBelow)) {
                            fringe.add(pointBelow);
                        }
                        break;
                    case '#':
                        //Fill element and elements to the left and right until barrier is hit
                        //If no barrier is hit, add the point of overflow to the fringe
                        //Add the point above back to the fringe if no overflow

                        slice.setElement(pointToProcess.x, pointToProcess.y, '~');

                        //Left& Right
                        moveLeftRight(slice, fringe, pointToProcess);
                        break;
                    case '~':

                        //Check if the row below is not already overflowing, otherwise do the same thing as with #
                        char leftMostChar = characterBelow;
                        Point pointBelowToCheck = new Point(pointBelow);
                        while (true) {
                            pointBelowToCheck = new Point(pointBelowToCheck.x - 1, pointBelowToCheck.y);
                            if (slice.getElement(pointBelowToCheck) != '~') {
                                leftMostChar = slice.getElement(pointBelowToCheck);
                                break;
                            }
                        }

                        char rightMostChar = characterBelow;
                        while (true) {
                            pointBelowToCheck = new Point(pointBelowToCheck.x + 1, pointBelowToCheck.y);
                            if (slice.getElement(pointBelowToCheck) != '~') {
                                rightMostChar = slice.getElement(pointBelowToCheck);
                                break;
                            }
                        }

                        if (leftMostChar == '#' && rightMostChar == '#') {
                            //Fill element and elements to the left and right until barrier is hit
                            //If no barrier is hit, add the point of overflow to the fringe
                            //Add the point above back to the fringe if no overflow

                            slice.setElement(pointToProcess.x, pointToProcess.y, '~');

                            //Left& Right
                            moveLeftRight(slice, fringe, pointToProcess);
                        }



                        break;
                    default:
                        throw new IllegalArgumentException("Illegal char (" + characterBelow + ") at Point " + pointBelow);
                }
            }

        }
        System.out.println(slice.countWet());
        slice.removeOverflow();
        System.out.println(slice.countAlwaysWet());

    }

    private static void moveLeftRight(Slice slice, ArrayList<Point> fringe, Point pointToProcess) {
        boolean overflowing = false;
        for (int leftOrRight = -1; leftOrRight <=1; leftOrRight += 2) { //leftOrRight == -1: left; leftOrRight == 1; right
            boolean moving = true;
            int steps = 1;
            while (moving) {
                Point neighbourToCheck = new Point(pointToProcess.x + leftOrRight * steps, pointToProcess.y);
                switch (slice.getElement(neighbourToCheck)) {
                    case '#':
                        moving = false;
                        break;
                    case '~':
                        moving = false;
                        break;
                    case '|':
                        if (slice.getElement(new Point(neighbourToCheck.x, neighbourToCheck.y + 1)) == '#' || slice.getElement(new Point(neighbourToCheck.x, neighbourToCheck.y + 1)) == '~') {
                            fringe.remove(neighbourToCheck);
                            slice.setElement(neighbourToCheck.x, neighbourToCheck.y, '~');
                            if (slice.getElement(new Point(neighbourToCheck.x, neighbourToCheck.y - 1)) == '|') {
                                fringe.add(new Point(neighbourToCheck.x, neighbourToCheck.y - 1));
                            }
                            steps++;
                        }
                        else {
                            moving = false;
                            overflowing = true;
                        }
                        break;
                    case '.':
                        if (slice.getElement(new Point(neighbourToCheck.x, neighbourToCheck.y + 1)) == '#' || slice.getElement(new Point(neighbourToCheck.x, neighbourToCheck.y + 1)) == '~') {
                            fringe.remove(neighbourToCheck);
                            slice.setElement(neighbourToCheck.x, neighbourToCheck.y, '~');
                            steps++;
                        } else {
                            slice.setElement(neighbourToCheck.x, neighbourToCheck.y, '|');
                            if (!fringe.contains(neighbourToCheck)) {
                                fringe.add(neighbourToCheck);
                            }
                            moving = false;
                            overflowing = true;
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("Illegal char (" + slice.getElement(neighbourToCheck) + ") at Point " + neighbourToCheck);
                }
            }
        }
        if (!overflowing) {
            if (slice.getElement(new Point(pointToProcess.x, pointToProcess.y - 1)) != '#') {
                if (!fringe.contains(new Point(pointToProcess.x, pointToProcess.y - 1))) {
                    fringe.add(new Point(pointToProcess.x, pointToProcess.y - 1));
                }
            }
        }
    }

    private static void printIntermediateState(Slice slice, ArrayList<Point> fringe) {
        slice.print();
        System.out.println(fringe);
        System.out.println("---------------------------------");
    }

    private static List<String> getInput() throws FileNotFoundException {
        File file = new File("E:\\input17.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        return bufferedReader.lines().collect(Collectors.toList());
    }
}
