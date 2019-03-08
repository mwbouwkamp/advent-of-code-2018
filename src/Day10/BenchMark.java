package Day10;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class BenchMark {
    private List<Dot> numbers;

    public BenchMark(List<Dot> numbers) {
        this.numbers = numbers;
    }

    public void invoke() {
        int minX = 0;
        int maxX = 0;
        int minY = 0;
        int maxY = 0;
        long startTime = System.nanoTime();
        for (int i = 0; i < 50; i++) {
            minX = numbers.stream().mapToInt(dot -> dot.point.x).min().orElse(-1);
            maxX = numbers.stream().mapToInt(dot -> dot.point.x).max().orElse(-1);
            minY = numbers.stream().mapToInt(dot -> dot.point.y).min().orElse(-1);
            maxY = numbers.stream().mapToInt(dot -> dot.point.y).max().orElse(-1);
        }
        System.out.println("\n4xstream");
        System.out.println(Double.toString((System.nanoTime() - startTime) / 50000000.0));
        System.out.println(minX + " " + minY + " - " + maxX + " " + maxY);

        startTime = System.nanoTime();
        for (int i = 0; i < 50; i++) {
            minX = Integer.MAX_VALUE;
            maxX = Integer.MIN_VALUE;
            minY = Integer.MAX_VALUE;
            maxY = Integer.MIN_VALUE;
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
        }
        System.out.println("\nfor-loop");
        System.out.println(Double.toString((System.nanoTime() - startTime) / 50000000.0));
        System.out.println(minX + " " + minY + " - " + maxX + " " + maxY);

        Map<String, Integer> resultMap = new HashMap<>();
        startTime = System.nanoTime();
        for (int i = 0; i < 50; i++) {
            resultMap = numbers.stream().collect(new extremesCollector());
        }
        System.out.println("\nintegrated stream");
        System.out.println(Double.toString((System.nanoTime() - startTime) / 50000000.0));
        System.out.println(resultMap.get("xMin") + " " + resultMap.get("yMin") + " - " + resultMap.get("xMax") + " " + resultMap.get("yMax"));

        resultMap = new HashMap<>();
        startTime = System.nanoTime();
        for (int i = 0; i < 50; i++) {
            resultMap = numbers.parallelStream().collect(new extremesCollector());
        }
        System.out.println("\nparallel integrated stream");
        System.out.println(Double.toString((System.nanoTime() - startTime) / 50000000.0));
        System.out.println(resultMap.get("xMin") + " " + resultMap.get("yMin") + " - " + resultMap.get("xMax") + " " + resultMap.get("yMax"));

        for (int i = 0; i < 50; i++) {
            PointStatistics statistics = numbers
                    .stream()
                    .map(s -> s.point)
                    .collect(PointStatistics::new, PointStatistics::accept, PointStatistics::combine);
            minX = statistics.getMinX();
            minY = statistics.getMinY();
            maxX = statistics.getMaxX();
            maxY = statistics.getMaxY();
        }
        System.out.println("\nstatistics");
        System.out.println(Double.toString((System.nanoTime() - startTime) / 50000000.0));
        System.out.println(resultMap.get("xMin") + " " + resultMap.get("yMin") + " - " + resultMap.get("xMax") + " " + resultMap.get("yMax"));
    }
}
