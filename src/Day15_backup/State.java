package Day15_backup;


import Day15_backup.Creature;

import java.awt.*;
import java.util.ArrayList;

public class State {

    private char[][] board;
    private int numState;
    private ArrayList<Creature> creatures;
    private boolean enemiesPresent;


    public State(int width, int height, int numState) {
        board = new char[width][height];
        this.numState = numState;
        enemiesPresent = true;
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
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                toReturn += board[j][i];
            }
            toReturn += "\n";
        }
        for (Creature creature: creatures) {
            toReturn += creature + "\n";
        }
        toReturn += "\n";
        return toReturn;
    }




    public void setNumState(int numState) {
        this.numState = numState;
    }
}
