package Day22;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class State implements Comparable<State> {

    public enum Equipment {
        NEITHER,
        TORCH,
        CLIMBING_GEAR,
        NO_CHANGE,
        NOT_ALLOWED
    }

    Region region;
    Point position;
    Equipment equipment;
    ArrayList<Point> path;
    int cost, heuristics;

    public State(Region region, Point position, Equipment equipment, ArrayList<Point> path, int cost) {
        this.region = region;
        this.position = position;
        this.equipment = equipment;
        this.path = path;
        this.cost = cost;
//        this.heuristics = cost + Math.abs(region.target.x - position.x) + Math.abs(region.target.y - position.y);
//        this.heuristics = cost + Math.abs(region.target.x - position.x) + Math.abs(region.target.y - position.y) / 4;
//        this.heuristics = cost + Math.abs(region.target.x - position.x);
        this.heuristics = Math.abs(region.target.x - position.x) + Math.abs(region.target.y - position.y);
//        this.heuristics = cost;
    }

    SmallState getSmallState() {
        return new SmallState(cost, equipment);
    }

    ArrayList<State> getChildren() {
        ArrayList<State> children = new ArrayList<>();
        children.add(getChild(1, 0));
        children.add(getChild(0, 1));
        children.add(getChild(0, -1));
        children.add(getChild(-1, 0));
        children.removeAll(Collections.singleton(null));
        return children;
    }

    State getChild(int xDif, int yDif) {
        Point newPosition = new Point(position.x + xDif, position.y + yDif);
        if (newPosition.x < 0 || newPosition.y < 0) {
            return null;
        }
//        if (newPosition.x >= region.regionType.length) {
//            region.extendX();
//        }
//        else if (newPosition.y >= region.regionType[0].length) {
//            region.extendY();
//        }
        int regionType = region.regionType[position.x][position.y];
        int newRegionType = region.regionType[newPosition.x][newPosition.y];
        if (!path.contains(newPosition)) {
            Equipment[][] regionMatrix = new Equipment[3][3];
            regionMatrix[0][0] = Equipment.NO_CHANGE;       //Rocky to rocky
            regionMatrix[0][1] = Equipment.CLIMBING_GEAR;   //Rocky to wet
            regionMatrix[0][2] = Equipment.NOT_ALLOWED;     //Rocky to narrow
            regionMatrix[1][0] = Equipment.CLIMBING_GEAR;   //Wet to rocky
            regionMatrix[1][1] = Equipment.NO_CHANGE;       //Wet to wet
            regionMatrix[1][2] = Equipment.NEITHER;         //Wet to narrow
            regionMatrix[2][0] = Equipment.NOT_ALLOWED;     //Narrow to rocky
            regionMatrix[2][1] = Equipment.NEITHER;         //Narrow to wet
            regionMatrix[2][2] = Equipment.NO_CHANGE;       //Narrow to Narrow
            ArrayList<Point> newPath = new ArrayList<>(path);
            newPath.add(newPosition);
            Equipment necessaryEquipment = regionMatrix[regionType][newRegionType];
            if (necessaryEquipment == Equipment.NO_CHANGE || equipment == necessaryEquipment) {
                return new State(region, new Point(position.x + xDif, position.y + yDif), equipment, newPath, cost + 1);
            }
            else if (necessaryEquipment != Equipment.NOT_ALLOWED){
                return new State(region, new Point(position.x + xDif, position.y + yDif), necessaryEquipment, newPath, cost + 8);
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }

    @Override
    public int compareTo(State o) {
        return heuristics - o.heuristics;
    }

}
