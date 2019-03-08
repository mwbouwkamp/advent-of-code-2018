package Day9;

import java.util.Arrays;

public class Day9 {

    //430 players; last marble is worth 71588 points
    public static final int NUM_PLAYERS = 430;
    public static final int MAX_MARBLES = 7158800;

    public static void main(String[] args) {
        CircularList circle = new CircularList(0);
        long[] players = new long[NUM_PLAYERS];
        int currentPlayer = -1;
        for (int i = 1; i <= MAX_MARBLES; i++) {
            System.out.println(100 * i / MAX_MARBLES + " %");
            currentPlayer++;
            currentPlayer %= NUM_PLAYERS;
            if (i % 23 != 0) {
                circle.moveClock(1);
                circle.add(i);
            }
            else {
                circle.moveCounterClock(7);
                players[currentPlayer] += i + circle.remove();
            }
        }


//        System.out.println(circle);

        System.out.println(Arrays.stream(players).parallel().reduce((a, b) -> a > b ? a : b).orElse(-1));


        //422748
    }

    private static class CircularList {

        private Node currentNode;
        private Node initialNode;
        private int numNodes;

        public CircularList(int valueInitialNode) {
            this.currentNode = new Node(valueInitialNode);
            this.initialNode = currentNode;
            this.numNodes = 1;
        }

        public void moveClock(int times) {
            for (int i = 0; i < times; i++) {
                currentNode = currentNode.next;
            }
        }

        public void moveCounterClock(int times) {
            for (int i = 0; i < times; i++) {
                currentNode = currentNode.prev;
            }
        }

        public void add(int valueNewNode) {
            Node newNode = new Node(valueNewNode);
            currentNode.next.prev = newNode;
            newNode.prev = currentNode;
            newNode.next = currentNode.next;
            currentNode.next = newNode;
            currentNode = newNode;
            numNodes++;
        }

        public int remove() {
            Node nodeToRemove = currentNode;
            currentNode = nodeToRemove.next;
            currentNode.prev = nodeToRemove.prev;
            currentNode.prev.next = currentNode;
            numNodes--;
            return nodeToRemove.number;
        }

        @Override
        public String toString() {
            String toReturn = "";
            Node pointer = initialNode;
            toReturn += pointer.number + " ";
            for (int i = 0; i < numNodes - 1; i++) {
                pointer = pointer.next;
                toReturn += pointer.number + " ";
            }
            return toReturn;
        }

        private class Node {

            private int number;
            private Node next;
            private Node prev;

            public Node(int number) {
                this.number = number;
                this.next = this;
                this.prev = this;
            }
        }

    }
}
