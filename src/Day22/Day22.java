package Day22;

import java.awt.*;
import java.util.*;

public class Day22 {

    public static void main(String[] args) {
        int depth = 5355;
        Point target = new Point(14, 796);

//        depth = 510;
//        target = new Point(10, 10);

        Region region = new Region(target, depth);
        System.out.println("Risk :" + region.getRisk());

        ArrayList<Point> startingPath = new ArrayList<>();
        startingPath.add(new Point(0, 0));
        State startingState = new State(region, new Point(0,0), State.Equipment.TORCH, startingPath, 0);
        State solution = new State(region, new Point(0, 0), State.Equipment.TORCH, new ArrayList<>(), 1122);
        SortedStateList fringe = new SortedStateList();
        HashMap<Point, SmallState> visited = new HashMap<>();
        fringe.add(startingState);
        int count = 0;
        while (fringe.size() > 0) {
            if (count == 100) {
                System.out.println(fringe.size());
                count = 0;
            }
            else{
                count++;
            }
            State stateToCheck = fringe.removeFirst();
            visited.put(stateToCheck.position, stateToCheck.getSmallState());
            if (stateToCheck.cost < solution.cost) {
                if (stateToCheck.position.equals(target) && stateToCheck.cost < solution.cost) {
                    solution = stateToCheck;
                    System.out.println("solution: " + stateToCheck.cost);
                } else {
                    ArrayList<State> children = stateToCheck.getChildren();
                    for (State child : children) {
                        boolean addChild = true;
                        if (child.cost > solution.cost) {
                            addChild = false;
                        }
                        else if (visited.keySet().contains(child.position)) {
                            SmallState visitedChild = visited.get(child.position);
                            if (child.equipment == visitedChild.equipment && child.cost > visitedChild.cost) {
                                addChild = false;
                            }
                        }
                        else if (child.equipment == fringe.getEquipmentOfState(child) && child.cost >= fringe.getCostOfState(child)) {
                            addChild = false;
                        }
                        if (addChild) {
                            fringe.add(child);
                        }
                    }
                }
            }
        }
        System.out.println(solution.cost);  //1121 too high; not 819; not 1120; not 1111; not 1107
        System.out.println(solution.path.size());


    }
}
