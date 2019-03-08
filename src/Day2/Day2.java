package Day2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

public class Day2 {

    public static void main(String[] args) {
        Day2 day2a = new Day2();
        day2a.getDay2A();
    }

    public boolean getDay2A() {
        File file = new File("e:\\input2.txt");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            List<String> lines = bufferedReader.lines().collect(Collectors.toList());
            long two = lines.stream()
                    .filter(s -> timesPresent(s, 2))
                    .count();
            long three = lines.stream()
                    .filter(s -> timesPresent(s, 3))
                    .count();
            System.out.println(two + " x " + three + " = " + two * three);

            //Day2b
            String result = "";
            for (String code1: lines) {
                for (String  code2: lines) {
                    result += numDifferentTwo(code1, code2);
                }
            }
            System.out.println(result.substring(0, result.length() / 2));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    private static boolean timesPresent(String code, int numOccurances) {
        long sum = code.chars()
                .map(c -> (int) code.chars()
                        .filter(d -> d == c)
                        .count())
                .filter(c -> c == numOccurances)
                .count();
        return sum > 0;
    }

    private static String numDifferentTwo(String code1, String code2) {
        String sameChars = "";
        int numDifferent = 0;
        for (int i = 0; i < code1.length(); i++) {
            if (code1.charAt(i) != code2.charAt(i)) {
                numDifferent++;
                if (numDifferent > 1) {
                    return "";
                }
            }
            else {
                sameChars += code1.charAt(i);
            }
        }
        return (numDifferent == 0) ? "" : sameChars;
    }
}
