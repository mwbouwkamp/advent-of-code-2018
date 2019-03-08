package Day22;

import java.util.Comparator;

public class StateComparator implements Comparator<State> {

    @Override
    public int compare(State state1, State state2) {
        if (state1.heuristics > state2.heuristics) {
            return 1;
        }
        else if (state1.heuristics < state2.heuristics) {
            return -1;
        }
        else {
            return 0;
        }
    }
}
