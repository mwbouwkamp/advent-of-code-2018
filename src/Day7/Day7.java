package Day7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class Day7 {

    static final int ASCII = 64;
    static final int EXTRA = 60;
    static final int NUMBER_OF_WORKERS = 5;
    static String resultingString;
    static Set<Character> allCharacters;

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("E:\\input7.txt");
        List<String> beforeAfters = new BufferedReader(new FileReader(file))
                .lines()
                .map(input -> input
                        .replace("Step ", "")
                        .replace(" must be finished before step ", "")
                        .replace(" can begin.", ""))
                .collect(toList());


//        beforeAfters = new LinkedList<>();
//        beforeAfters.add("CA");
//        beforeAfters.add("CF");
//        beforeAfters.add("AB");
//        beforeAfters.add("AD");
//        beforeAfters.add("BE");
//        beforeAfters.add("DE");
//        beforeAfters.add("FE");

        resultingString = "";
        Map<Character, Integer> parentsHistogram = populateParentsHistogram(beforeAfters);
        Map<Character, Integer> characterCosts = populateCharacterCosts(beforeAfters);
        allCharacters = new HashSet<>(characterCosts.keySet());
        char[] workers = new char[NUMBER_OF_WORKERS];
        int time = 0;
        while (characterCosts.size() > 0) {
            time++;
            for (int i = 0; i < workers.length; i++) {
                if (workers[i] == '\u0000') {
                    char lowestCharacter = getLowestCharacter(parentsHistogram);
                    parentsHistogram.remove(lowestCharacter);
                    if (lowestCharacter != '#' && allCharacters.contains(lowestCharacter)) {
                        workers[i] = lowestCharacter;
                        allCharacters.remove(lowestCharacter);
                    }

                }
            }
            for (int i = 0; i < workers.length; i++) {
                if (workers[i] != '\u0000') {
                    characterCosts.put(workers[i], characterCosts.get(workers[i]) - 1);
                }
            }
            for (char key: characterCosts.keySet()) {
                if (characterCosts.get(key) == 0) {
                    characterCosts.remove(key);
                    resultingString += key;
                    beforeAfters = beforeAfters
                            .stream()
                            .filter(string -> string.charAt(0) != key)
                            .collect(Collectors.toList());
                    parentsHistogram = populateParentsHistogram(beforeAfters);
                    for (int i = 0; i < workers.length; i++) {
                        if (workers[i] == key) {
                            workers[i] = '\u0000';
                        }
                    }
                    break;
                }
            }


//            char lowestCharacter = getLowestCharacter(parentsHistogram);
//            resultingString += lowestCharacter;
//            beforeAfters = beforeAfters.stream().filter(string -> string.charAt(0) != lowestCharacter).collect(Collectors.toList());
//            parentsHistogram = populateParentsHistogram(beforeAfters);
        }
        System.out.println(time + " " + resultingString);

    }

    private static char getLowestCharacter(Map<Character, Integer> parentsHistogram) {
        if (parentsHistogram.size() == 0) {
            Iterator<Character> iterator = allCharacters.iterator();
            if (iterator.hasNext()) {
                return iterator.next();
            }
            else {
                return '#';
            }
        }
        else {
            return parentsHistogram
                    .keySet()
                    .stream()
                    .filter(character -> parentsHistogram.get(character) == 0)
                    .reduce((char1, char2) -> char1 < char2 ? char1 : char2)
                    .orElse('#');
        }
    }

    private static Map<Character, Integer> populateCharacterCosts(List<String> beforeAfters) {
        Map<Character, Integer> characterCosts = new HashMap<>();
        for (String beforeAfter: beforeAfters) {
            if (!characterCosts.keySet().contains(beforeAfter.charAt(0))) {
                characterCosts.put(beforeAfter.charAt(0), beforeAfter.charAt(0) - ASCII + EXTRA);
            }
            if (!characterCosts.keySet().contains(beforeAfter.charAt(1))) {
                characterCosts.put(beforeAfter.charAt(1), beforeAfter.charAt(1) - ASCII + EXTRA);
            }
        }

        return characterCosts;
    }

    private static Map<Character, Integer> populateParentsHistogram(List<String> beforeAfters) {
        Map<Character, Integer> parentsHistogram = new HashMap<>();
        for (String beforeAfter: beforeAfters) {
            if (!parentsHistogram.keySet().contains(beforeAfter.charAt(0))) {
                parentsHistogram.put(beforeAfter.charAt(0), 0);
            }
        }
        for (String beforeAfter: beforeAfters) {
            parentsHistogram.put(beforeAfter.charAt(1), parentsHistogram.keySet().contains(beforeAfter.charAt(1)) ? parentsHistogram.get(beforeAfter.charAt(1)) + 1 : 1);
        }
        return  parentsHistogram;
    }
}