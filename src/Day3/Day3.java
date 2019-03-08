package Day3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.Buffer;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Day3 {

    public static void main(String[] args) {
        File file = new File("E:\\input3.txt");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            List<Square> squares = bufferedReader.lines().map(s -> new Square(s)).collect(Collectors.toList());
            int[][] board = new int[32][32];
            for (Square square: squares) {
                int necessaryArraySize = Math.max(square.getLeft() + square.getWidth(), square.getTop() + square.getHeight());
                if (necessaryArraySize > board.length) {
                    board = enlargeArray(board, necessaryArraySize / board.length + 1);
                }
                for (int i = square.getLeft(); i < square.getLeft() + square.getWidth(); i++) {
                    for (int j = square.getTop(); j < square.getTop()+ square.getHeight(); j++) {
                        board[i][j]++;
                    }
                }
            }
            int result = 0;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (board[i][j] > 1) {
                        result++;
                    }
                }
            }
            System.out.println(result);

            for (Square square: squares) {
                int expectedOccupancy = square.getWidth() * square.getHeight();
                int foundOccupancy = 0;
                for (int i = square.getLeft(); i < square.getLeft() + square.getWidth(); i++) {
                    for (int j = square.getTop(); j < square.getTop()+ square.getHeight(); j++) {
                        if (board[i][j] == 1) {
                            foundOccupancy++;
                        }
                    }
                }
                if (foundOccupancy == expectedOccupancy) {
                    System.out.println(square.getId());
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static int[][] enlargeArray(int[][] originalArray, int factor) {
        int[][] newArray = new int[originalArray.length * factor][originalArray.length * factor];
        for (int i = 0; i < originalArray.length; i ++) {
            System.arraycopy(originalArray[i], 0, newArray[i], 0, originalArray.length);
        }
        return newArray;
    }

    private static class Square {

        private String id;
        private int left, top, width, height;

        /**
         * #27 @ 669,422: 24x26
         * @param square
         */
        private Square(String square) {
            String[] blocks = square.split(" ");
            id = blocks[0];
            String[] coords = blocks[2].substring(0, blocks[2].length() - 1).split(",");
            left = Integer.parseInt(coords[0]);
            top = Integer.parseInt(coords[1]);
            String[] dimensions = blocks[3].split("x");
            width = Integer.parseInt(dimensions[0]);
            height = Integer.parseInt(dimensions[1]);
        }

        public String toString() {
            return "ID: " + getId() + "; LEFT: " + getLeft() + "; TOP : " + getTop() + "; DIMENSIONS: " + getWidth() + " x " + getHeight();
        }

        public String getId() {
            return id;
        }

        public int getLeft() {
            return left;
        }

        public int getTop() {
            return top;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }


}
