package Day8;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Day8 {

    public static void main(String[] args) {
        File file = new File("E:\\input8.txt");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            List<Integer> numbers = Arrays.stream(bufferedReader.readLine().split(" ")).map(s -> Integer.parseInt(s)).collect(Collectors.toList());

//            numbers = new LinkedList<>();
//            numbers.add(2);
//            numbers.add(3);
//            numbers.add(0);
//            numbers.add(3);
//            numbers.add(10);
//            numbers.add(11);
//            numbers.add(12);
//            numbers.add(1);
//            numbers.add(1);
//            numbers.add(0);
//            numbers.add(1);
//            numbers.add(99);
//            numbers.add(2);
//            numbers.add(1);
//            numbers.add(1);
//            numbers.add(2);


            System.out.println(numbers);

            //get first node
            Node firstNode = new Node(numbers.remove(0), numbers.remove(0), 0);
            LinkedList<Node> nodes = new LinkedList<>();
            LinkedList<Node> fifo = new LinkedList<>();
            fifo.add(firstNode);
            while (fifo.size() > 0) {
                Node toCheck = fifo.getLast();
                if (toCheck.addMore()) {
                    Node childToAdd = new Node(numbers.remove(0), numbers.remove(0), toCheck.numParents + 1);
                    toCheck.addNode(childToAdd);
                    fifo.add(childToAdd);
                }
                else {
                    for (int i = 0; i < toCheck.numMetas; i++) {
                        toCheck.metas[i] = numbers.remove(0);
                    }
                    nodes.add(toCheck);
                    fifo.remove(toCheck);
                }
            }
            int result = 0;
            for (Node node: nodes) {
                System.out.println(node);
                result += node.getTotalMetas();
            }
            System.out.println(result);
            System.out.println(firstNode.getNodeValue());















        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static class Node {

        private int numChildren;
        private int numParents;
        private int numMetas;
        int[] metas;
        List<Node> children;
        int addedChildren;

        public Node(int numChildren, int numMetas, int numParents) {
            this.numChildren = numChildren;
            this.numMetas = numMetas;
            this.metas = new int[numMetas];
            this.children = new LinkedList<>();
            this.addedChildren = 0;
            this.numParents = numParents;
        }

        public boolean hasChildren() {
            return numChildren > 0;
        }

        public void addNode(Node nodeToAdd) {
            children.add(nodeToAdd);
            addedChildren++;
        }

        public boolean addMore() {
            return numChildren > addedChildren;
        }

        public int getNodeValue() {
            int intToReturn = 0;
            for (Integer meta: metas) {
                if (numChildren == 0) {
                    return getTotalMetas();
                }
                if (meta <= children.size()) {
                    intToReturn += children.get(meta - 1).getNodeValue();
                }
            }
            return intToReturn;
        }


        @Override
        public String toString() {
            String metaString = "";
            for (Integer meta: metas) {
                metaString += meta + ", ";
            }
            metaString = metaString.substring(0, metaString.length() - 2);
            return "numChildren: " + numChildren + "; numMetas: " + numMetas + "; Sum Metas: " + metaString;
        }

        public int getTotalMetas() {
            return Arrays.stream(metas).sum();
        }
    }
}
