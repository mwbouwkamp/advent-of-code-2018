package Day19;

public class OpCodes {



//    Addition:
//
//    addr (add register) stores into register C the result of adding register A and register B.
//    addi (add immediate) stores into register C the result of adding register A and value B.

    public int[] addr(int[] before, int inputA, int inputB, int outputC) {
        int[] toReturn = before.clone();
        toReturn[outputC] = before[inputA] + before[inputB];
        return toReturn;
    }

    public int[] addi(int[] before, int inputA, int inputB, int outputC) {
        int[] toReturn = before.clone();
        toReturn[outputC] = before[inputA] + inputB;
        return toReturn;
    }

//    Multiplication:
//
//    mulr (multiply register) stores into register C the result of multiplying register A and register B.
//    muli (multiply immediate) stores into register C the result of multiplying register A and value B.

    public int[] mulr(int[] before, int inputA, int inputB, int outputC) {
        int[] toReturn = before.clone();
        toReturn[outputC] = before[inputA] * before[inputB];
        return toReturn;
    }

    public int[] muli(int[] before, int inputA, int inputB, int outputC) {
        int[] toReturn = before.clone();
        toReturn[outputC] = before[inputA] * inputB;
        return toReturn;
    }

//    Bitwise AND:
//
//    banr (bitwise AND register) stores into register C the result of the bitwise AND of register A and register B.
//    bani (bitwise AND immediate) stores into register C the result of the bitwise AND of register A and value B.

    public int[] banr(int[] before, int inputA, int inputB, int outputC) {
        int[] toReturn = before.clone();
        toReturn[outputC] = before[inputA] & before[inputB];
        return toReturn;
    }

    public int[] bani(int[] before, int inputA, int inputB, int outputC) {
        int[] toReturn = before.clone();
        toReturn[outputC] = before[inputA] & inputB;
        return toReturn;
    }

//    Bitwise OR:
//
//    borr (bitwise OR register) stores into register C the result of the bitwise OR of register A and register B.
//    bori (bitwise OR immediate) stores into register C the result of the bitwise OR of register A and value B.

    public int[] borr(int[] before, int inputA, int inputB, int outputC) {
        int[] toReturn = before.clone();
        toReturn[outputC] = before[inputA] | before[inputB];
        return toReturn;
    }

    public int[] bori(int[] before, int inputA, int inputB, int outputC) {
        int[] toReturn = before.clone();
        toReturn[outputC] = before[inputA] | inputB;
        return toReturn;
    }

//    Assignment:
//
//    setr (set register) copies the contents of register A into register C. (Input B is ignored.)
//    seti (set immediate) stores value A into register C. (Input B is ignored.)

    public int[] setr(int[] before, int inputA, int inputB, int outputC) {
        int[] toReturn = before.clone();
        toReturn[outputC] = before[inputA];
        return toReturn;
    }

    public int[] seti(int[] before, int inputA, int inputB, int outputC) {
        int[] toReturn = before.clone();
        toReturn[outputC] = inputA;
        return toReturn;
    }

//    Greater-than testing:
//
//    gtir (greater-than immediate/register) sets register C to 1 if value A is greater than register B. Otherwise, register C is set to 0.
//    gtri (greater-than register/immediate) sets register C to 1 if register A is greater than value B. Otherwise, register C is set to 0.
//    gtrr (greater-than register/register) sets register C to 1 if register A is greater than register B. Otherwise, register C is set to 0.

    public int[] gtir(int[] before, int inputA, int inputB, int outputC) {
        int[] toReturn = before.clone();
        if (inputA > before[inputB]) {
            toReturn[outputC] = 1;
        }
        else {
            toReturn[outputC] = 0;
        }
        return toReturn;
    }

    public int[] gtri(int[] before, int inputA, int inputB, int outputC) {
        int[] toReturn = before.clone();
        if (before[inputA] > inputB) {
            toReturn[outputC] = 1;
        }
        else {
            toReturn[outputC] = 0;
        }
        return toReturn;
    }

    public int[] gtrr(int[] before, int inputA, int inputB, int outputC) {
        int[] toReturn = before.clone();
        if (before[inputA] > before[inputB]) {
            toReturn[outputC] = 1;
        }
        else {
            toReturn[outputC] = 0;
        }
        return toReturn;
    }

//    Equality testing:
//
//    eqir (equal immediate/register) sets register C to 1 if value A is equal to register B. Otherwise, register C is set to 0.
//    eqri (equal register/immediate) sets register C to 1 if register A is equal to value B. Otherwise, register C is set to 0.
//    eqrr (equal register/register) sets register C to 1 if register A is equal to register B. Otherwise, register C is set to 0.

    public int[] eqir(int[] before, int inputA, int inputB, int outputC) {
        int[] toReturn = before.clone();
        if (inputA == before[inputB]) {
            toReturn[outputC] = 1;
        }
        else {
            toReturn[outputC] = 0;
        }
        return toReturn;
    }

    public int[] eqri(int[] before, int inputA, int inputB, int outputC) {
        int[] toReturn = before.clone();
        if (before[inputA] == inputB) {
            toReturn[outputC] = 1;
        }
        else {
            toReturn[outputC] = 0;
        }
        return toReturn;
    }

    public int[] eqrr(int[] before, int inputA, int inputB, int outputC) {
        int[] toReturn = before.clone();
        if (before[inputA] == before[inputB]) {
            toReturn[outputC] = 1;
        }
        else {
            toReturn[outputC] = 0;
        }
        return toReturn;
    }
}
