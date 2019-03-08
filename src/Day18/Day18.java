package Day18;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Day18 {

    static char[][] acres;

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("E:\\input18.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<String> lines = reader.lines().collect(Collectors.toList());
        acres = new char[lines.get(0).length()][lines.size()];
        int row = 0;
        int col = 0;
        for (String line: lines) {
            for (char acre: line.toCharArray()) {
                acres[col][row] = acre;
                col++;
            }
            row++;
            col = 0;
        }
        printAcres(acres);
        long times = 1000000000;
        for (long i = 1; i <= times; i++) {
//            System.out.println("---- " + i + " ---");
            acres = move(acres);
//            printAcres(acres);
            System.out.print(i + " ");
            printResult();
        }
        printResult();
    }

    private static void printResult() {
        int wood = 0;
        int lumber = 0;
        for (int i = 0; i < acres.length; i++) {
            for (int j = 0; j < acres[0].length; j++) {
                if (acres[j][i] == '|') {
                    wood++;
                }
                else if (acres[j][i] == '#') {
                    lumber++;
                }
            }
        }
        System.out.println(lumber * wood);
    }

    private static char[][] move(char[][] acresBefore) {
        char[][] acresAfter = new char[acresBefore[0].length][acresBefore.length];
        for (int i = 0; i < acresAfter.length; i++) {
            for (int j = 0; j < acresAfter[0].length; j++) {
                switch (acresBefore[j][i]) {
                    case '.':
                        HashMap<Character, Integer> neighbours = getNeighbours(j, i, acresBefore);
                        if (neighbours.get('|') >= 3) {
                            acresAfter[j][i] = '|';
                        }
                        else {
                            acresAfter[j][i] = acresBefore[j][i];
                        }
                        break;
                    case '|':
                        neighbours = getNeighbours(j, i, acresBefore);
                        if (neighbours.get('#') >= 3) {
                            acresAfter[j][i] = '#';
                        }
                        else {
                            acresAfter[j][i] = acresBefore[j][i];
                        }
                        break;
                    case '#':
                        neighbours = getNeighbours(j, i, acresBefore);
                        if (neighbours.get('#') < 1 || neighbours.get('|') < 1) {
                            acresAfter[j][i] = '.';
                        }
                        else {
                            acresAfter[j][i] = acresBefore[j][i];
                        }
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            }
        }
        return acresAfter;
    }

    private static HashMap<Character, Integer> getNeighbours(int x, int y, char[][] acres) {
        HashMap<Character, Integer> neighbours = new HashMap<>();
        neighbours.put('.', 0);
        neighbours.put('|', 0);
        neighbours.put('#', 0);
        for (int i = (y == 0 ? y : y - 1);
             i <= (y == acres[0].length - 1 ? y : y + 1);
             i++) {
            for (int j = (x == 0 ? x : x - 1);
                 j <= (x == acres[0].length - 1 ? x : x + 1);
                 j++) {
                neighbours.put(acres[j][i], neighbours.get(acres[j][i]) + 1);
            }
        }
        neighbours.put(acres[x][y], neighbours.get(acres[x][y]) - 1);
        return neighbours;
    }

    private static void printAcres(char[][] acres) {
        for (int i = 0; i < acres.length; i++) {
            for (int j = 0; j < acres[0].length; j++) {
                System.out.print(acres[j][i]);
            }
            System.out.println();
        }
    }
}
