package Day20;

import java.awt.*;
import java.util.ArrayList;

public class Room {
    Point coordinates;
    Room N, S, W, E;
    int doorsOpened;
    ArrayList<String> pathsToGo;

    public Room(Point coordinates, int doorsOpened, String pathToGo) {
        this.coordinates = coordinates;
        this.doorsOpened = doorsOpened;
        pathsToGo = new ArrayList<>();
        if (pathToGo != null) {
            pathsToGo.add(pathToGo);
        }
    }

    public boolean hasPathsToGo() {
        return pathsToGo.size() > 0;
    }

    public Room takeStep() {
        String pathToGo = pathsToGo.remove(0);
        String step;
        String remainingPath;
        if (pathToGo.length() > 1) {
            step = pathToGo.substring(0, 1);
            remainingPath = pathToGo.substring(1);
        }
        else {
            step = pathToGo;
            remainingPath = null;
        }
        Room newRoom;
        switch (step) {
            case "N":
                newRoom = new Room(new Point(coordinates.x, coordinates.y - 1), doorsOpened + 1, remainingPath);
                N = newRoom;
                newRoom.S = this;
                break;
            case "S":
                newRoom = new Room(new Point(coordinates.x, coordinates.y + 1), doorsOpened + 1, remainingPath);
                S = newRoom;
                newRoom.N = this;
                break;
            case "W":
                newRoom = new Room(new Point(coordinates.x - 1, coordinates.y), doorsOpened + 1, remainingPath);
                W = newRoom;
                newRoom.E = this;
                break;
            case "E":
                newRoom = new Room(new Point(coordinates.x + 1, coordinates.y), doorsOpened + 1, remainingPath);
                E = newRoom;
                newRoom.W = this;
                break;
            case "(":
                char[] withoutInnerBrackets = pathToGo.toCharArray();
                int bracketLevel = 0;
                for (int i = 0; i < withoutInnerBrackets.length; i++) {
                    if (withoutInnerBrackets[i] == '(') {
                        bracketLevel++;
                    }
                    else if (withoutInnerBrackets[i] == ')') {
                        bracketLevel--;
                    }
                    else if (withoutInnerBrackets[i] == '|') {
                        if (bracketLevel > 1) {
                            withoutInnerBrackets[i] = '@';
                        }
                    }
                }
                ArrayList<Integer> dividers = new ArrayList<>();
                bracketLevel = 0;
                for (int i = 0; i < withoutInnerBrackets.length; i++) {
                    if (withoutInnerBrackets[i] == '(') {
                        bracketLevel++;
                    }
                    else if (withoutInnerBrackets[i] == ')') {
                        bracketLevel--;
                        if (bracketLevel == 0) {
                            break;
                        }
                    }
                    else if (withoutInnerBrackets[i] == '|') {
                        dividers.add(i);
                    }
                }
                int startDivider = 0;
                String dividingString = pathToGo;
                for (Integer divider: dividers) {
                    String pathToAdd = dividingString.substring(startDivider, divider);
                    if (pathToAdd.charAt(0) == '(' || pathToAdd.charAt(0) == '|') {
                        pathToAdd = pathToAdd.substring(1);
                    }
                    pathsToGo.add(pathToAdd);
                    startDivider = divider;
                }
                String pathToAdd = dividingString.substring(startDivider + 1);
                if (dividers.size() > 0) {
                    if (pathToAdd.charAt(0) == 'X') {
                        pathToAdd = pathToAdd.substring(2);
                    }
                    if (!pathToAdd.equals("")) {
                        pathsToGo.add(pathToAdd);
                    }
                }






//                int numBrackets = 1;
//                int posClosing = 0;
//                for (int i = 0; i < remainingPath.length(); i++) {
//                    if (remainingPath.charAt(i) == '(') {
//                        numBrackets++;
//                    }
//                    else if (remainingPath.charAt(i) == ')') {
//                        numBrackets--;
//                    }
//                    if (numBrackets == 0) {
//                        posClosing = i;
//                        break;
//                    }
//                }
////                if (remainingPath.charAt(posClosing - 1) == '|') {
////                    //Empty option
////                    String emptyOption = remainingPath.substring(0, posClosing - 1);
////                    remainingPath = remainingPath.substring(posClosing + 1);
////                    pathsToGo.add(emptyOption);
////                    pathsToGo.add(remainingPath);
////                }
////                else {
//                    //Option
//                    int positionDivider = 0;
//                    boolean dividerFound = false;
//                    while (!dividerFound) {
//                        positionDivider = remainingPath.indexOf('|', positionDivider + 1);
//                        int numOpenBrackets = remainingPath.substring(0, positionDivider).length() - remainingPath.substring(0, positionDivider).replace("(", "").length();
//                        int numCloseBrackets = remainingPath.substring(0, positionDivider).length() - remainingPath.substring(0, positionDivider).replace(")", "").length();
//                        if (numOpenBrackets == numCloseBrackets) {
//                            dividerFound = true;
//                        }
//                    }
//                    String choiceOne = remainingPath.substring(0, positionDivider);
//                    String choiceTwo = remainingPath.substring(positionDivider + 1);
//                    if (choiceTwo.charAt(0) =='X') {
//                        choiceTwo = choiceTwo.substring(2);
//                    }
//                    else {
//                        choiceTwo = choiceTwo.substring(0, choiceTwo.length() - 1);
//                    }
//                    pathsToGo.add(choiceOne);
//                    if (!choiceTwo.equals("")) {
//                        pathsToGo.add(choiceTwo);
//                    }
//                    System.out.println(choiceOne + "\n"  + choiceTwo);
////                }
                newRoom = this;
                break;
            case ")":
                newRoom = this;
                break;
            default:
                throw new IllegalArgumentException("Illegal step: " + step);
        }
        return newRoom;
    }

    @Override
    public boolean equals(Object o) {
        Room r = (Room) o;
        return (r.coordinates.x == coordinates.x && r.coordinates.y == coordinates.y);
    }

    @Override
    public String toString() {
        return "[" + coordinates.x + ", " + coordinates.y + "] - " + doorsOpened + " doors";
    }
}
