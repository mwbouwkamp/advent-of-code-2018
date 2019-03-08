package Day19;

import Day16.OpCodes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Day19 {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("E:\\input19.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<String> lines;
        int ip;
        boolean test = false;
        if (test) {
            ip = 0;
            lines = new ArrayList<>();
            lines = new ArrayList<>();
            lines.add("seti 5 0 1");
            lines.add("seti 6 0 2");
            lines.add("addi 0 1 0");
            lines.add("addr 1 2 3");
            lines.add("setr 1 0 0");
            lines.add("seti 8 0 4");
            lines.add("seti 9 0 5");
        }
        else {
            lines = reader.lines().collect(Collectors.toList());
            ArrayList<String[]> instructions = new ArrayList<>();
            ip = Integer.parseInt(lines.remove(0).replace("#ip ", ""));
            for (String line : lines) {
                instructions.add(line.split(" "));
            }
        }

        int[] start = new int[6];
        start[0] = 1;
        int[] end = new int[6];
        System.out.print(Arrays.toString(start) + " - ");
        while (true) {
            String[] instruction = lines.get(start[ip]).split(" ");
            String opCodeString = instruction[0];
            int inputA = Integer.parseInt(instruction[1]);
            int inputB = Integer.parseInt(instruction[2]);
            int outputC = Integer.parseInt(instruction[3]);
            end = getOutput(start, opCodeString, inputA, inputB, outputC);
            System.out.println(Arrays.toString(end));
            start = end;
            start[ip]= start[ip] + 1;
            if (start[ip] < lines.size()) {
                System.out.print(Arrays.toString(start) + " - ");
            }
            else {
                break;
            }

        }

    }

    public static int[] getOutput(int[] before, String opCodeString, int inputA, int inputB, int outputC) {
        Day16.OpCodes opcodes = new OpCodes();
        switch (opCodeString) {
            case "addr":
                return opcodes.addr(before, inputA, inputB, outputC);
            case "addi":
                return opcodes.addi(before, inputA, inputB, outputC);
            case "mulr":
                return opcodes.mulr(before, inputA, inputB, outputC);
            case "muli":
                return opcodes.muli(before, inputA, inputB, outputC);
            case "banr":
                return opcodes.banr(before, inputA, inputB, outputC);
            case "bani":
                return opcodes.bani(before, inputA, inputB, outputC);
            case "borr":
                return opcodes.borr(before, inputA, inputB, outputC);
            case "bori":
                return opcodes.bori(before, inputA, inputB, outputC);
            case "seti":
                return opcodes.seti(before, inputA, inputB, outputC);
            case "setr":
                return opcodes.setr(before, inputA, inputB, outputC);
            case "gtir":
                return opcodes.gtir(before, inputA, inputB, outputC);
            case "gtri":
                return opcodes.gtri(before, inputA, inputB, outputC);
            case "gtrr":
                return opcodes.gtrr(before, inputA, inputB, outputC);
            case "eqir":
                return opcodes.eqir(before, inputA, inputB, outputC);
            case "eqri":
                return opcodes.eqri(before, inputA, inputB, outputC);
            case "eqrr":
                return opcodes.eqrr(before, inputA, inputB, outputC);
            default:
                throw new IllegalArgumentException("opCode not allowed: " + opCodeString);
        }
    }

}
