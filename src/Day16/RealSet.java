package Day16;

import java.util.Arrays;

import static Day16.Day16.opCodeMap;

public class RealSet {
    int opCode, inputA, inputB, outputC;

    public RealSet(int opCode, int inputA, int inputB, int outputC) {
        this.opCode = opCode;
        this.inputA = inputA;
        this.inputB = inputB;
        this.outputC = outputC;
    }

    public int[] getOutput(int[] before) {
        OpCodes opcodes = new OpCodes();
        String opCodeString = opCodeMap.get(opCode).get(0);
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
                throw new IllegalArgumentException("opCode not allowed: " + opCode);
        }
    }
}
