package Day16;

public class InstructionSet {

    public int[] getBefore() {
        return before;
    }

    public int[] getAfter() {
        return after;
    }

    public int getOpCode() {
        return opCode;
    }

    public int getInputA() {
        return inputA;
    }

    public int getInputB() {
        return inputB;
    }

    public int getOutputC() {
        return outputC;
    }

    private int[] before, after;
    private int opCode;
    private int inputA, inputB, outputC;

    public InstructionSet(int[] before, int[] after, int opCode, int inputA, int inputB, int outputC) {
        this.before = before;
        this.after = after;
        this.opCode = opCode;
        this.inputA = inputA;
        this.inputB = inputB;
        this.outputC = outputC;
    }


}
