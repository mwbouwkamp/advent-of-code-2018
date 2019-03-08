package Day22;

public class SortedStateList {

    int size;
    Node starting;
    Node ending;

    class Node {
        State state;
        Node next;
        Node prev;

        Node(State state) {
            this.state = state;
        }
    }

    SortedStateList() {
        this.size = 0;
        this.starting = null;
        this.ending = null;
    }

    void add(State state) {
        Node toAdd = new Node(state);
        if (size == 0) {
            starting = toAdd;
            ending = toAdd;
            size++;
        }
        else {
            Node toCompare = starting;
            boolean added = false;
            while (toCompare.next != null) {
                if (toAdd.state.heuristics < toCompare.state.heuristics) {
                    Node next = toCompare.next;
                    toAdd.next = next;
                    toAdd.prev = toCompare;
                    toCompare.next = toAdd;
                    next.prev = toAdd;
                    size++;
                    added = true;
                    break;
                }
                toCompare = toCompare.next;
            }
            if (!added) {
                ending.next = toAdd;
                toAdd.prev = ending;
                ending = toAdd;
                size++;
            }
        }
    }

    int getCostOfState(State state) {
        if (size == 0) {
            return Integer.MAX_VALUE;
        }
        else {
            Node pointer = starting;
            while (pointer != ending) {
                if (pointer.state.position.equals(state.position)) {
                    return pointer.state.cost;
                }
                pointer = pointer.next;
            }
        }
        return Integer.MAX_VALUE;
    }

    State.Equipment getEquipmentOfState(State state) {
        if (size == 0) {
            return State.Equipment.NOT_ALLOWED;
        }
        else {
            Node pointer = starting;
            while (pointer != ending) {
                if (pointer.state.position.equals(state.position)) {
                    return pointer.state.equipment;
                }
                pointer = pointer.next;
            }
        }
        return State.Equipment.NOT_ALLOWED;
    }

    State removeFirst() {
        State toReturn = null;
        if (size == 0) {
            return toReturn;
        }
        else if (size == 1) {
            toReturn = starting.state;
            starting = null;
            ending = null;
            size--;
            return toReturn;
        }
        else {
            toReturn = starting.state;
            starting = starting.next;
            starting.prev = null;
            size--;
            return toReturn;
        }
    }

    int size() {
        return size;
    }

}
