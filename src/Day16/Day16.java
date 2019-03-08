package Day16;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Day16 {

    public static HashMap<Integer, ArrayList<String>> opCodeMap = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("E:\\input16.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        List<String> lines = bufferedReader.lines().collect(Collectors.toList());
        ArrayList<InstructionSet> instructionSets = new ArrayList<>();
        ArrayList<RealSet> realSets = new ArrayList<>();
        while (lines.size() > 0) {
            String line = lines.remove(0);
            if (line.contains("Before")) {
                String[] beforeString = line.replace("Before: [", "").replace("]", "").split(", ");
                int[] before = Arrays.stream(beforeString).mapToInt(Integer::parseInt).toArray();
                line = lines.remove(0);
                String[] opCodeString = line.split(" ");
                int opCode = Integer.parseInt(opCodeString[0]);
                int inputA = Integer.parseInt(opCodeString[1]);
                int inputB = Integer.parseInt(opCodeString[2]);
                int outputC = Integer.parseInt(opCodeString[3]);
                line = lines.remove(0);
                String[] afterString = line.replace("After:  [", "").replace("]", "").split(", ");
                int[] after = Arrays.stream(afterString).mapToInt(Integer::parseInt).toArray();
                instructionSets.add(new InstructionSet(before, after, opCode, inputA, inputB, outputC));
            }
            else {
                if (line.length() > 0) {
                    String[] opCodeString = line.split(" ");
                    int opCode = Integer.parseInt(opCodeString[0]);
                    int inputA = Integer.parseInt(opCodeString[1]);
                    int inputB = Integer.parseInt(opCodeString[2]);
                    int outputC = Integer.parseInt(opCodeString[3]);
                    realSets.add(new RealSet(opCode, inputA, inputB, outputC));
                }
                //Instructions for second half of input
            }
        }
        int atLeastThree = 0;
        opCodeMap = new HashMap<>();
        for (InstructionSet instructionSet: instructionSets) {
            ArrayList<String> opCodeArrayList = opCodeChecks(instructionSet);
            if (opCodeArrayList.size() >=3 ) {
                atLeastThree++;
            }
            if (opCodeMap.keySet().contains(instructionSet.getOpCode())) {
                opCodeMap.get(instructionSet.getOpCode()).retainAll(opCodeArrayList);
            }
            else {
                opCodeMap.put(instructionSet.getOpCode(), opCodeArrayList);
            }
        }
        int totalLenght = getTotalLenght(opCodeMap);
        while (totalLenght > opCodeMap.size()) {
            for (int opCodeKey: opCodeMap.keySet()) {
                if (opCodeMap.get(opCodeKey).size() == 1) {
                    String exclusive = opCodeMap.get(opCodeKey).get(0);
                    for (int opCodeKey2: opCodeMap.keySet()) {
                        if (opCodeKey != opCodeKey2) {
                            opCodeMap.get(opCodeKey2).remove(exclusive);
                        }
                    }
                }
            }
            totalLenght = getTotalLenght(opCodeMap);
        }
        int[] code = new int[] {0, 0, 0, 0};
        for (RealSet realSet: realSets) {
            code = realSet.getOutput(code);
        }



        System.out.println("AT LEAST THREE");
        System.out.println(atLeastThree);
        System.out.println();
        System.out.println("WHICH IS WHICH");
        System.out.println(opCodeMap);
        System.out.println();
        System.out.println("RESULT");
        System.out.println(Arrays.toString(code));

    }

    private static int getTotalLenght(HashMap<Integer, ArrayList<String>> opCodeMap) {
        int totalLenght = 0;
        for (int opCodesKey: opCodeMap.keySet()) {
            totalLenght += opCodeMap.get(opCodesKey).size();
        }
        return totalLenght;
    }


    public static ArrayList<String> opCodeChecks(InstructionSet instructionSet) {
        ArrayList<String> toReturn = new ArrayList<>();
        OpCodes opCodes = new OpCodes();
        int[] addr = opCodes.addr(instructionSet.getBefore(), instructionSet.getInputA(), instructionSet.getInputB(), instructionSet.getOutputC());
        if (Arrays.equals(addr, instructionSet.getAfter())) {
            toReturn.add("addr");
        }
        int[] addi = opCodes.addi(instructionSet.getBefore(), instructionSet.getInputA(), instructionSet.getInputB(), instructionSet.getOutputC());
        if (Arrays.equals(addi, instructionSet.getAfter())) {
            toReturn.add("addi");
        }
        int[] mulr = opCodes.mulr(instructionSet.getBefore(), instructionSet.getInputA(), instructionSet.getInputB(), instructionSet.getOutputC());
        if (Arrays.equals(mulr, instructionSet.getAfter())) {
            toReturn.add("mulr");
        }
        int[] muli = opCodes.muli(instructionSet.getBefore(), instructionSet.getInputA(), instructionSet.getInputB(), instructionSet.getOutputC());
        if (Arrays.equals(muli, instructionSet.getAfter())) {
            toReturn.add("muli");
        }
        int[] banr = opCodes.banr(instructionSet.getBefore(), instructionSet.getInputA(), instructionSet.getInputB(), instructionSet.getOutputC());
        if (Arrays.equals(banr, instructionSet.getAfter())) {
            toReturn.add("banr");
        }
        int[] bani = opCodes.bani(instructionSet.getBefore(), instructionSet.getInputA(), instructionSet.getInputB(), instructionSet.getOutputC());
        if (Arrays.equals(bani, instructionSet.getAfter())) {
            toReturn.add("bani");
        }
        int[] borr = opCodes.borr(instructionSet.getBefore(), instructionSet.getInputA(), instructionSet.getInputB(), instructionSet.getOutputC());
        if (Arrays.equals(borr, instructionSet.getAfter())) {
            toReturn.add("borr");
        }
        int[] bori = opCodes.bori(instructionSet.getBefore(), instructionSet.getInputA(), instructionSet.getInputB(), instructionSet.getOutputC());
        if (Arrays.equals(bori, instructionSet.getAfter())) {
            toReturn.add("bori");
        }
        int[] setr = opCodes.setr(instructionSet.getBefore(), instructionSet.getInputA(), instructionSet.getInputB(), instructionSet.getOutputC());
        if (Arrays.equals(setr, instructionSet.getAfter())) {
            toReturn.add("setr");
        }
        int[] seti = opCodes.seti(instructionSet.getBefore(), instructionSet.getInputA(), instructionSet.getInputB(), instructionSet.getOutputC());
        if (Arrays.equals(seti, instructionSet.getAfter())) {
            toReturn.add("seti");
        }
        int[] gtir = opCodes.gtir(instructionSet.getBefore(), instructionSet.getInputA(), instructionSet.getInputB(), instructionSet.getOutputC());
        if (Arrays.equals(gtir, instructionSet.getAfter())) {
            toReturn.add("gtir");
        }
        int[] gtri = opCodes.gtri(instructionSet.getBefore(), instructionSet.getInputA(), instructionSet.getInputB(), instructionSet.getOutputC());
        if (Arrays.equals(gtri, instructionSet.getAfter())) {
            toReturn.add("gtri");
        }
        int[] gtrr = opCodes.gtrr(instructionSet.getBefore(), instructionSet.getInputA(), instructionSet.getInputB(), instructionSet.getOutputC());
        if (Arrays.equals(gtrr, instructionSet.getAfter())) {
            toReturn.add("gtrr");
        }
        int[] eqir = opCodes.eqir(instructionSet.getBefore(), instructionSet.getInputA(), instructionSet.getInputB(), instructionSet.getOutputC());
        if (Arrays.equals(eqir, instructionSet.getAfter())) {
            toReturn.add("eqir");
        }
        int[] eqri = opCodes.eqri(instructionSet.getBefore(), instructionSet.getInputA(), instructionSet.getInputB(), instructionSet.getOutputC());
        if (Arrays.equals(eqri, instructionSet.getAfter())) {
            toReturn.add("eqri");
        }
        int[] eqrr = opCodes.eqrr(instructionSet.getBefore(), instructionSet.getInputA(), instructionSet.getInputB(), instructionSet.getOutputC());
        if (Arrays.equals(eqrr, instructionSet.getAfter())) {
            toReturn.add("eqrr");
        }
        return toReturn;
    }


}
