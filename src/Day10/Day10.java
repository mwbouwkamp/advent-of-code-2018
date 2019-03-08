package Day10;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

public class Day10 extends javax.swing.JApplet implements Runnable {

    public static BufferedImage img;
    public static List<Dot> numbers;
    public static final int MAX_WIDTH = 5000;
    public static final int MAX_HEIGHT = 3000;
    public static Thread runner;

//    public static void main(String[] args) {
//        Day10 day10 = new Day10();
//        day10.init();
//        day10.start();
//    }

    public void paint(Graphics screen) {
        super.paint(screen);
        Graphics2D screen2D = (Graphics2D) screen;
        if (img != null) {
            screen2D.drawImage(img, 0, 0, MAX_WIDTH, MAX_HEIGHT, this);
        }
    }

    public void init() {
        File file = new File("E:\\input10.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            numbers = reader.lines()
                    .map(s -> s
                            .replace("position=<", "")
                            .replace("> velocity=<", " ")
                            .replace(">", "")
                            .replace(",", "")
                    )
                    .map(string -> string.split(" ", -1))
                    .map(array -> (Arrays.stream(array)
                            .filter(number -> !number.equals(""))
                            .map(number -> Integer.parseInt(number)).toArray(Integer[]::new)))
                    .map(array -> new Dot(new Point(array[0], array[1]), array[2], array[3]))
                    .collect(Collectors.toCollection(ArrayList::new));

            for (int i = 0; i < 10726; i++) {
                for (Dot number: numbers) {
                    number.move();
                }
            }
            new BenchMark(numbers).invoke();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void start() {
        if (runner == null) {
            runner = new Thread(this);
            runner.start();
        }
    }

    public void stop() {
        if (runner != null) {
            runner = null;
        }
    }

    @Override
    public void run() {
        Thread thread = Thread.currentThread();
        int counter = 0;
        while (runner == thread){
            System.out.println(counter);
            counter++;
            repaint();
            for (Dot number: numbers) {
                number.move();
            }
//            PointStatistics statistics = numbers
//                    .stream()
//                    .map(s -> s.point)
//                    .collect(PointStatistics::new, PointStatistics::accept, PointStatistics::combine);
//            int width = statistics.getMaxX() - statistics.getMinX();
//            int height = statistics.getMaxY() - statistics.getMinY();
            int minX = Integer.MAX_VALUE;
            int maxX = Integer.MIN_VALUE;
            int minY = Integer.MAX_VALUE;
            int maxY = Integer.MIN_VALUE;
            for (Dot dot : numbers) {
                if (dot.point.x < minX) {
                    minX = dot.point.x;
                }
                if (dot.point.x > maxX) {
                    maxX = dot.point.x;
                }
                if (dot.point.y < minY) {
                    minY = dot.point.y;
                }
                if (dot.point.y > maxY) {
                    maxY = dot.point.y;
                }
            }
            int width = maxX - minX;
            int height = maxY - minY;
            int resolution = Math.max(width / MAX_WIDTH, height / MAX_HEIGHT) + 1;
            int[][] grid = new int[MAX_WIDTH][MAX_HEIGHT];
            for (Dot dot: numbers) {
                Point point = dot.point;
                grid[(point.x - minX) / resolution][(point.y - minY) / resolution]++;
            }

            img = new BufferedImage(MAX_WIDTH, MAX_HEIGHT, BufferedImage.TYPE_INT_RGB);
            for (int rc = 0; rc < MAX_WIDTH; rc++) {
                for (int cc = 0; cc < MAX_HEIGHT; cc++) {
                    int grey = 255;
                    if (grid[rc][cc] > 0) {
                        grey = 0;
                    }
                    img.setRGB(rc, cc, new Color(grey, grey, grey).getRGB());
                }
            }
        }


    }
}
