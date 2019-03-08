package Day1;

import java.io.*;
import java.util.LinkedList;

public class Day1 {

    public static void main(String[] args) {
        Day1 day1 = new Day1();
        System.out.println(day1.GetDay1A());
        System.out.println(day1.getDay1B());
    }

    public int GetDay1A() {
        File file = new File("e:\\input1.txt");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            return bufferedReader
                    .lines()
                    .mapToInt(Integer::parseInt)
                    .sum();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getDay1B() {
        File file = new File("e:\\input1.txt");
        LinkedList<Integer> visited = new LinkedList<>();
        try {
            int current = 0;
            while (true) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    current += Integer.parseInt(line);
                    if (visited.contains(current)) {
                        return current;
                    }
                    visited.add(current);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
