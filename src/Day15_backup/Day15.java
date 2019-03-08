package Day15_backup;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day15 {

    public static void main(String[] args) throws FileNotFoundException {
        //Setup
        File file = new File("E:\\input15.txt");
        List<String> lines = readInput(file);
        State emptyState = setupStartState(lines);
        State currenState = createState(emptyState, emptyState.getCreatures(), 0);
        System.out.print(currenState);
        while (currenState.hasEnemies()) {
            currenState.performRound();
            if (currenState.hasEnemies()) {
                currenState = createState(emptyState, currenState.getCreatures(), currenState.getNumState() + 1);
                System.out.print(currenState);
            }
        }
        System.out.println(currenState);
        int score = 0;
        for (Creature creature : currenState.getCreatures()) {
            score += creature.getScore();
        }
        System.out.println(currenState.getNumState() * score);


    }

    private static State setupStartState(List<String> lines) {
        State emptyState = new State(lines.get(0).length(), lines.size(), 0);
        ArrayList<Creature> creatures = new ArrayList<>();
        for (int i = 0; i < lines.get(0).length(); i++) {
            for (int j = 0; j < lines.size(); j++) {
                switch (lines.get(j).charAt(i)) {
                    case '.':
                        emptyState.setSquare(i, j, '.');
                        break;
                    case '#':
                        emptyState.setSquare(i, j, '#');
                        break;
                    case 'E':
                        emptyState.setSquare(i, j, '.');
                        creatures.add(new Creature(new Point(i, j), 'E'));
                        break;
                    case 'G':
                        emptyState.setSquare(i, j, '.');
                        creatures.add(new Creature(new Point(i, j), 'G'));
                        break;
                    default:
                        throw new IllegalArgumentException("not a valid input at position [" + i + ", " + j + "]");
                }
            }
        }
        emptyState.setCreatures(creatures);
        return emptyState;
    }

    private static List<String> readInput(File file) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        return reader.lines().collect(Collectors.toList());
    }

    public static State createState(State state, ArrayList<Creature> creatures, int numState) {
        State newState = new State(state.getWidth(), state.getHeight(), numState);
        for (int i = 0; i < state.getWidth(); i++) {
            for (int j = 0; j < state.getHeight(); j++) {
                newState.setSquare(i, j, state.getSquare(new Point(i, j)));
            }
        }
        for (Creature creature: creatures) {
            newState.setSquare(creature.getLocation().x, creature.getLocation().y, creature.getTypeOfCreature());
        }
        newState.setCreatures(state.getCreatures());
        return newState;
    }
}
