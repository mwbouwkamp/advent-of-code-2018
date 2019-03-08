package Day15;


import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class State {

    private char[][] board;
    private ArrayList<String> stringBoard;
    private int numState;
    private ArrayList<Creature> creatures;
    private boolean enemiesPresent;


    public State(int width, int height, int numState) {
        board = new char[width][height];
        stringBoard = new ArrayList<>();
        this.numState = numState;
        enemiesPresent = true;
    }

    public void fillStringBoard() {
        stringBoard = new ArrayList<>();
        for (int i = 0; i < board[0].length; i++) {
            String line = "";
            for (int j = 0; j < board.length; j++) {
                line += board[j][i];
            }
            stringBoard.add(line);
        }
    }

    public void performRound() {
        creatures.sort(new CreatureComparator());
        ArrayList<Creature> creaturesAtStartOfState = new ArrayList<>(creatures);
        ArrayList<Creature> creaturesStillAlive = new ArrayList<>(creatures);
        for (Creature creature : creaturesAtStartOfState) {
            if (creaturesStillAlive.contains(creature) && enemiesPresent) {
                creaturesStillAlive = creature.move(this);
                enemiesPresent = checkForOponents();
            }
        }
        creatures = creaturesStillAlive;
        System.out.println();
    }

    public boolean hasEnemies() {
        return enemiesPresent;
    }

    private boolean checkForOponents() {
        int numE = 0;
        int numG = 0;
        for (Creature creature : creatures) {
            if (creature.getTypeOfCreature() == 'E'){
                numE++;
            }
            else {
                numG++;
            }
        }
        return (numE !=0 && numG !=0);
    }


    public void setCreatures(ArrayList<Creature> creatures) {
        this.creatures = creatures;
    }

    public ArrayList<Creature> getCreatures() {
        return creatures;
    }

    public void setSquare(int x, int y, char c) {
        board[x][y] = c;
        fillStringBoard();
    }

    public int getWidth() {
        return board[0].length;
    }

    public int getHeight() {
        return board.length;
    }

    public int getNumState() {
        return  numState;
    }

    public char getSquare(Point point) {
        return board[point.x][point.y];
    }

    @Override
    public String toString() {
        String toReturn = "======== State " + numState + " ========\n";
//        for (int i = 0; i < board.length; i++) {
//            for (int j = 0; j < board[0].length; j++) {
//                toReturn += board[j][i];
//            }
//            toReturn += "\n";
//        }
        for (int i = 0; i < board[0].length; i++) {
            toReturn += stringBoard.get(i) + "\n";
        }
        for (Creature creature: creatures) {
            toReturn += creature + "\n";
        }
        return toReturn;
    }

    public ArrayList<String> getStringBoard() {
        return stringBoard;
    }

    public void setNumState(int numState) {
        this.numState = numState;
    }

    public HashMap<Integer, ArrayList<Integer>> getRanges(State state) {
        HashMap<Integer, ArrayList<Integer>> availablePositions = new HashMap<>();
        for (int i = 0; i < stringBoard.size(); i++) {
            String line = stringBoard.get(i);
            ArrayList<Integer> availablePositionsInLine = new ArrayList<>();
            for (int j = 0; j < line.length(); j ++) {
                if (line.charAt(j) == '.') {
                    availablePositionsInLine.add(j);
                }
            }
            availablePositions.put(i, availablePositionsInLine);
        }
        return availablePositions;
    }


}
