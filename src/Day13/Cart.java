package Day13;

import java.awt.*;
import java.util.ArrayList;

public class Cart {

    private Point location;
    private String direction;
    private String rail;
    private static final ArrayList<String> DIRECTIONS = new ArrayList<String>() {
        {
            add("^");
            add(">");
            add("v");
            add("<");
        }
    };

    private static final ArrayList<String> INTERSECTION_BEHAVIOR = new ArrayList<String>() {
        {
            add("left");
            add("straight");
            add("right");
        }
    };

    String currentIntersectionBehavior;

    public Cart(Point location, String direction) {
        this.location = location;
        this.direction = direction;
        currentIntersectionBehavior = "left";
    }

    public Point getLocation() {
        return location;
    }

    public String getDirection() {
        return direction;
    }

    public String getAndChangeIntersectionBehavior() {
        String intersectionBehavior = currentIntersectionBehavior;
        switch (currentIntersectionBehavior) {
            case "left":
                currentIntersectionBehavior = "straight";
                break;
            case "straight":
                currentIntersectionBehavior = "right";
                break;
            case "right":
                currentIntersectionBehavior = "left";
                break;
            default:
                //DoNothing
        }
        return intersectionBehavior;
    }

    public String getRail() {
        return rail;
    }

    public void rotateLeft() {
        int currentDirection = DIRECTIONS.indexOf(direction);
        direction = (currentDirection > 0) ? DIRECTIONS.get(currentDirection - 1) : DIRECTIONS.get(DIRECTIONS.size() - 1);
    }

    public void rotateRight() {
        int currentDirection = DIRECTIONS.indexOf(direction);
        direction = (currentDirection < DIRECTIONS.size() -1) ? DIRECTIONS.get(currentDirection + 1) : DIRECTIONS.get(0);
    }

    public void move(String direction, String rail) {
        switch (direction) {
            case "N":
                this.location.y--;
                break;
            case "S":
                this.location.y++;
                break;
            case "E":
                this.location.x++;
                break;
            case "W":
                this.location.x--;
                break;
            default:
                throw new IllegalArgumentException("Illegal direction: " + direction);

        }
        this.rail = rail;
    }

    public long getCoordinatesCode() {
        return location.x * 100000 + location.y;
    }
}
