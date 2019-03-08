package Day15_backup;

import Day15_backup.CreatureComparator;
import Day15_backup.PointComparator;
import Day15_backup.State;

import java.awt.*;
import java.util.*;

public class Creature {

    public static final int DAMAGE = 3;
    public static final int MAX_SCORE = 200;

    private char typeOfCreature;
    private Point location;

    private int score;

    public Creature(Point location, char typeOfCreature) {
        this.typeOfCreature = typeOfCreature;
        this.location = location;
        this.score = MAX_SCORE;
    }


    /**
     * Moves and returns the list of creatures that are still alive after this round
     *
     * @param state
     * @return
     */
    public ArrayList<Creature> move(State state) {
        if (!checkForEnemyNeighbours(state)) {
            ArrayList<Point> pointsInRange = getReachablePointsInRange(state, location);

            if (pointsInRange.size() > 0) {
                TreeMap<Integer, ArrayList<ArrayList<Point>>> solutions = getShortestPath(pointsInRange, state);
                if (solutions.size() > 0) {
                    ArrayList<ArrayList<Point>> shortestSolutions = new ArrayList<>();
                    if (solutions.size() > 0) {
                        shortestSolutions = solutions.get(solutions.firstKey());
                    }
                    TreeMap<Point, ArrayList<ArrayList<Point>>> solutionsByEndPoint = new TreeMap<>(new PointComparator());
                    for (ArrayList<Point> solution : shortestSolutions) {
                        if (solutionsByEndPoint.keySet().contains(solution.get(solution.size() - 1))) {
                            solutionsByEndPoint.get(solution.get(solution.size() - 1)).add(solution);
                        } else {
                            ArrayList<ArrayList<Point>> solutionToAdd = new ArrayList<>();
                            solutionToAdd.add(solution);
                            solutionsByEndPoint.put(solution.get(solution.size() - 1), solutionToAdd);
                        }
                    }
                    ArrayList<ArrayList<Point>> solutionsToChooseFromBasedOnEndPoint = solutionsByEndPoint.get(solutionsByEndPoint.firstKey());
                    Point chosen = null;
                    if (solutionsToChooseFromBasedOnEndPoint.size() > 0) {
                        SortedSet<Point> startingPoints = new TreeSet<>(new PointComparator());
                        for (ArrayList<Point> solutionsToChooseFrom : solutionsToChooseFromBasedOnEndPoint) {
                            startingPoints.add(solutionsToChooseFrom.get(1));
                        }
                        if (startingPoints.size() > 0) {
                            chosen = startingPoints.first();
                        }
                        if (chosen != null) {
                            state.setSquare(location.x, location.y, '.');
                            this.location = chosen;
                            state.setSquare(location.x, location.y, typeOfCreature);
                        }
                    }
                }
            }
        }
        if (score > 0) {
            attack(state);
        }
        ArrayList<Creature> creaturesStillAlive = new ArrayList<>();
        for (Creature creature: state.getCreatures()) {
            if (creature.getScore() > 0) {
                creaturesStillAlive.add(creature);
            }
        }
        return creaturesStillAlive;
    }

    private ArrayList<Point> getReachablePointsInRange(State state, Point location) {
        //Check which points are reachable in the first place
        SortedSet<Point> reachable = getReachable(state, location);
        //Get points in range
        ArrayList<Point> pointsInRange = getPointsInRange(state);
        //Reduce points in range to remove those that are not reachable in the first place
        ArrayList<Point> pointsInRangeToRemove= new ArrayList<>();
        for (Point pointInRange: pointsInRange) {
            if (!reachable.contains(pointInRange)) {
                pointsInRangeToRemove.add(pointInRange);
            }
        }
        for (Point pointInRangeToRemove: pointsInRangeToRemove) {
            pointsInRange.remove(pointInRangeToRemove);
        }
        return pointsInRange;
    }

    private SortedSet<Point> getReachable(State state, Point location) {
        SortedSet<Point> reachable = new TreeSet<>(new PointComparator());
        ArrayList<Point> fringe = new ArrayList<>();
        fringe.add(location);
        while (fringe.size() > 0) {
            Point toExpand = fringe.remove(0);
            reachable.add(toExpand);
            ArrayList<Point> children = getSurrounding(state, toExpand);
            for (Point child: children) {
                if (!fringe.contains(child) && !reachable.contains(child)) {
                    fringe.add(child);
                }
            }

        }
        return reachable;
    }

    public void attack (State state) {
        ArrayList<Point> neighbours =  getAllSurrounding(state, location);
        neighbours.sort(new PointComparator());
        SortedMap<Integer, SortedSet<Creature>> neighbouringCreatures = new TreeMap<>();
        for (Point neighbour: neighbours) {
            Creature neighbouringCreature = getCreatureAtLocation(neighbour, state.getCreatures());
            if (neighbouringCreature != null) {
                if (neighbouringCreature.getTypeOfCreature() != typeOfCreature) {
                    if (neighbouringCreatures.keySet().contains(neighbouringCreature.score)) {
                        neighbouringCreatures.get(neighbouringCreature.score).add(neighbouringCreature);
                    } else {
                        SortedSet<Creature> neighboursToAdd = new TreeSet<>(new CreatureComparator());
                        neighboursToAdd.add(neighbouringCreature);
                        neighbouringCreatures.put(neighbouringCreature.score, neighboursToAdd);
                    }
                }
            }
        }
        if (neighbouringCreatures.size() > 0) {
            SortedSet<Creature> lowestScoringNeighbours = neighbouringCreatures.get(neighbouringCreatures.firstKey());
            Creature lowestScoringCreatureReadingOrder = lowestScoringNeighbours.first();
            lowestScoringCreatureReadingOrder.loosePoints();
            if (lowestScoringCreatureReadingOrder.getScore() < 0) {
                state.getCreatures().remove(lowestScoringCreatureReadingOrder);
            }
        }
    }

    public TreeMap<Integer, ArrayList<ArrayList<Point>>> getShortestPath (ArrayList<Point> pointsInRange, State state) {
        TreeMap<Integer, ArrayList<ArrayList<Point>>> solution = new TreeMap<Integer, ArrayList<ArrayList<Point>>>();
        ArrayList<Point> initialPath = new ArrayList<>();
        ArrayList<ArrayList<Point>> initialArrayListOfPaths = new ArrayList<>();
        TreeMap<Integer, ArrayList<ArrayList<Point>>> fringe = new TreeMap<Integer, ArrayList<ArrayList<Point>>>();
        initialPath.add(location);
        initialArrayListOfPaths.add(initialPath);
        fringe.put(1, initialArrayListOfPaths);
        while (fringe.size() > 0) {
            //Get path with lowest heuristic from fringe (in case of multiple with lowest value, the first one will be selected
            int heuristicValueOfPathFromFringe = fringe.firstKey();
            ArrayList<ArrayList<Point>> arrayListOfPathsFromFringe = fringe.remove(heuristicValueOfPathFromFringe);
            ArrayList<Point> pathToCheck = arrayListOfPathsFromFringe.remove(arrayListOfPathsFromFringe.size() - 1);
            if (arrayListOfPathsFromFringe.size() > 0) {
                fringe.put(heuristicValueOfPathFromFringe, arrayListOfPathsFromFringe);
            }

            //Check if the end of the path is next to a creature
            boolean nextToPointInRange = false;
            for (Point pointInRange: pointsInRange) {
                if (pathToCheck.get(pathToCheck.size() - 1).equals(pointInRange)) {
                    nextToPointInRange = true;
                    break;
                }
            }

            //If end of the path is next to a point in range, either add it to the solution or add its children to the fringe
            if (nextToPointInRange) {
                if (solution.keySet().contains(pathToCheck.size())) {
                    solution.get(pathToCheck.size()).add(pathToCheck);
                }
                else {
                    ArrayList<ArrayList<Point>> arrayListWithPathToCheck = new ArrayList<>();
                    arrayListWithPathToCheck.add(pathToCheck);
                    solution.put(pathToCheck.size(), arrayListWithPathToCheck);
                }
            }
            else {
                // add children to the fringe
                ArrayList<Point> children = getSurrounding(state, pathToCheck.get(pathToCheck.size() - 1));
                children.sort(new PointComparator());
                if (children.size() != 0) {
                    for (Point child : children) {
                        if (!pathToCheck.contains(child)) {
                            boolean addChild = true;
                            ArrayList<Point> avaiableNeighboursForChild = getSurrounding(state, child);
                            if (!getPointsInRange(state).contains(child)) {
                                if (avaiableNeighboursForChild.size() == 0) {
                                    addChild = false;
                                } else if (avaiableNeighboursForChild.size() == 1) {
                                    ArrayList<Point> pointsInRangeAndReachableForChild = getReachablePointsInRange(state, child);
                                    if (pointsInRangeAndReachableForChild.size() == 0) {
                                        addChild = false;
                                    }
                                }
                            }
                            if (addChild) {
                                ArrayList<Point> pathToPotentiallyAdd = new ArrayList<>(pathToCheck);
                                pathToPotentiallyAdd.add(child);
                                int heuristicsOfPathToPotentiallyAdd = getHeuristicValue(pathToPotentiallyAdd, pointsInRange);
                                boolean addToFringe = false;
                                if (solution.size() == 0) {
                                    addToFringe = true;
                                } else if (pathToCheck.size() <= solution.firstKey()) {
                                    addToFringe = true;
                                }
                                if (addToFringe) {
                                    if (fringe.keySet().contains(heuristicsOfPathToPotentiallyAdd)) {
                                        ArrayList<ArrayList<Point>> currentPathsWithSameHeuristics = fringe.get(heuristicsOfPathToPotentiallyAdd);
                                        currentPathsWithSameHeuristics.add(pathToPotentiallyAdd);

                                    } else {
                                        ArrayList<ArrayList<Point>> pathsWithLowerHeuristics = new ArrayList<>();
                                        pathsWithLowerHeuristics.add(pathToPotentiallyAdd);
                                        fringe.put(heuristicsOfPathToPotentiallyAdd, pathsWithLowerHeuristics);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return solution;
    }

    public int getHeuristicValue(ArrayList<Point> path, ArrayList<Point> pointsInRange) {
//        int averageManhatten = 0;
//        for (Point pointInRange: pointsInRange) {
//            int manhatten = Math.abs(pointInRange.x - path.get(path.size() - 1).getLocation().x) + Math.abs(pointInRange.y - path.get(path.size() - 1).getLocation().y);
//            averageManhatten += manhatten;
//        }
//        averageManhatten /= pointsInRange.size();
//        return path.size() + averageManhatten * 100;
        int shortestManhatten = Integer.MAX_VALUE;
        int maxManhatten = Integer.MIN_VALUE;
        for (Point pointInRange: pointsInRange) {
            int manhatten = Math.abs(pointInRange.x - path.get(path.size() - 1).getLocation().x) + Math.abs(pointInRange.y - path.get(path.size() - 1).getLocation().y);
            if (manhatten < shortestManhatten) {
                shortestManhatten = manhatten;
            }
            else if (manhatten > maxManhatten) {
                maxManhatten = manhatten;
            }
        }
        return path.size() * 2 + (shortestManhatten + maxManhatten);
    }

    private ArrayList<Point> getPointsInRange(State state) {
        ArrayList<Point> inRange = new ArrayList<>();
        for (Creature creature : state.getCreatures()) {
            if (this.typeOfCreature != creature.getTypeOfCreature()) {
                ArrayList<Point> allowedSurroundingPoints = getSurrounding(state, creature.getLocation());
                inRange.addAll(allowedSurroundingPoints);
            }
        }
        return inRange;
    }

    private boolean checkForEnemyNeighbours(State state) {
        ArrayList<Point> neighbours =  getAllSurrounding(state, this.location);
        for (Point neighbour: neighbours) {
            if (this.typeOfCreature == 'E' && state.getSquare(neighbour) == 'G') {
                return true;
            }
            if (this.typeOfCreature == 'G' && state.getSquare(neighbour) == 'E') {
                return true;
            }
        }
        return false;
    }

    public int getScore() {
        return score;
    }

    public Creature getCreatureAtLocation(Point location, ArrayList<Creature> creatures) {
        for (Creature creature: creatures) {
            if (creature.getLocation().equals(location)) {
                return creature;
            }
        }
        return null;
    }

    public void loosePoints() {
        this.score -= DAMAGE;
    }

    private boolean thisOrNeighboringPoint(Point point1, Point point2) {
        return ((Math.abs(point1.x - point2.x) < 2) && (point1.y == point2.y)) || ((Math.abs(point1.y - point2.y) < 2) && (point1.x == point2.x));
    }

    private ArrayList<Point> getSurrounding(State state, Point point) {
        ArrayList<Point> allowedSurroundingPoints = new ArrayList<>();
        Point above = new Point(point.getLocation().x, point.getLocation().y -1);
        Point below = new Point(point.getLocation().x, point.getLocation().y +1);
        Point left = new Point(point.getLocation().x - 1, point.getLocation().y);
        Point right = new Point(point.getLocation().x + 1, point.getLocation().y);
        if (state.getSquare(above) == '.') {
            allowedSurroundingPoints.add(above);
        }
        if (state.getSquare(below) == '.') {
            allowedSurroundingPoints.add(below);
        }
        if (state.getSquare(left) == '.') {
            allowedSurroundingPoints.add(left);
        }
        if (state.getSquare(right) == '.') {
            allowedSurroundingPoints.add(right);
        }
        return allowedSurroundingPoints;
    }

    private ArrayList<Point> getAllSurrounding(State state, Point point) {
        ArrayList<Point> allowedSurroundingPoints = new ArrayList<>();
        Point above = new Point(point.getLocation().x, point.getLocation().y -1);
        Point below = new Point(point.getLocation().x, point.getLocation().y + 1);
        Point left = new Point(point.getLocation().x - 1, point.getLocation().y);
        Point right = new Point(point.getLocation().x + 1, point.getLocation().y);
        if (state.getSquare(above) != '#') {
            allowedSurroundingPoints.add(above);
        }
        if (state.getSquare(below) != '#') {
            allowedSurroundingPoints.add(below);
        }
        if (state.getSquare(left) != '#') {
            allowedSurroundingPoints.add(left);
        }
        if (state.getSquare(right) != '#') {
            allowedSurroundingPoints.add(right);
        }
        return allowedSurroundingPoints;
    }

    public char getTypeOfCreature() {
        return typeOfCreature;
    }

    public Point getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return location + " " + typeOfCreature + " " + score;
    }
}
