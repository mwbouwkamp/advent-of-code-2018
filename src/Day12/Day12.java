package Day12;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Day12 {

    public static void main(String[] args) {
        File file = new File("E:\\input12.txt");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            List<String> lines = bufferedReader.lines().collect(Collectors.toList());
            String initialStateString = lines.remove(0).replace("initial state: ", "");
            Map<Long, String> oldState = new HashMap<>();
            for (int i = 0; i < initialStateString.length(); i++) {
                oldState.put((long) i, initialStateString.split("")[i]);
            }
            lines.remove(0);
            ArrayList<String> noteStrings = (ArrayList) lines;
            Map<String, String> notes = new HashMap<>();
            for (String noteString: noteStrings) {
                String[] parts = noteString.split(" ");
                notes.put(parts[0], parts[2]);
            }
            for (long i = 1; i <= Long.valueOf("50000000000"); i++) { //50000000000
                Map<Long, String> newState = new HashMap<>();
                long oldMinValue = oldState.keySet().stream().mapToLong(v -> v).min().orElseThrow(NoSuchElementException::new);
                long oldMaxValue = oldState.keySet().stream().mapToLong(v -> v).max().orElseThrow(NoSuchElementException::new);
                oldState.put(oldMinValue - 1, ".");
                oldState.put(oldMinValue - 2, ".");
                oldState.put(oldMinValue - 3, ".");
                oldState.put(oldMinValue - 4, ".");
                oldState.put(oldMaxValue + 1, ".");
                oldState.put(oldMaxValue + 2, ".");
                oldState.put(oldMaxValue + 3, ".");
                oldState.put(oldMaxValue + 4, ".");
                long newMinValue = oldMinValue - 2;
                long newMaxValue = oldMaxValue + 2;
                boolean minFound = false;
                long minPot = -1;
                long maxPot = -1;
                for (long j = newMinValue; j <= newMaxValue; j++) {
                    String toCompare = oldState.get(j - 2) + oldState.get(j - 1) + oldState.get(j) + oldState.get(j + 1) + oldState.get(j + 2);
                    if (notes.containsKey(toCompare)) {
                        newState.put(j, notes.get(toCompare));
                    }
                    else {
                        newState.put(j, oldState.get(j));
                    }
                    if (newState.get(j).equals("#")) {
                        if (!minFound) {
                            minPot = j;
                            minFound = true;
                        }
                        maxPot = j;
                    }
                }
                if (oldState.equals(newState)) {
                    break;
                }
                for (long j = newMinValue ; j < minPot - 4; j++) {
                    newState.remove(j);
                }
                for (long j = maxPot + 5; j <= newMaxValue; j++) {
                    newState.remove(j);
                }
                oldState = newState;



                System.out.print(i + " ");

                long result = 0;
                for (Long pot: oldState.keySet()) {
                    if (oldState.get(pot).equals("#")) {
                        result += pot;
                    }

                }
                System.out.print(result + " ");

                for (long j = newMinValue ; j <= newMaxValue; j++) {
                    if (newState.keySet().contains(j)) {
                        System.out.print(newState.get(j));
                    }
                    else {
                        System.out.print(" ");
                    }
                }
                System.out.println();
            }


            //At some point the result seems the same (although is moving to the right). When extrapolating, the result is 3150000000905


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
