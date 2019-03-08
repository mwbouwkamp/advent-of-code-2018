package Day23;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class Day23 {

    private static List<Nanobot> nanobots;

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("E:\\input23.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<String> lines = reader.lines().collect(Collectors.toList());
        nanobots = lines.stream()
                .map(s -> s
                        .replace("pos=<", "")
                        .replace(">", "")
                        .replace(" r=", "")
                )
                .map(s -> s.split(","))
                .map(s -> new Nanobot(Long.valueOf(s[0]), Long.valueOf(s[1]), Long.valueOf(s[2]), Long.valueOf(s[3])))
                .collect(Collectors.toList());
        Nanobot strongestNanobot = nanobots
                .stream()
                .reduce((s1, s2) -> s1.range > s2.range ? s1 : s2)
                .orElse(null);
        long minX = nanobots
                .stream()
                .mapToLong(s -> s.x)
                .min()
                .orElse(-1);
        long maxX = nanobots
                .stream()
                .mapToLong(s -> s.x)
                .max()
                .orElse(-1);
        long minY = nanobots
                .stream()
                .mapToLong(s -> s.y)
                .min()
                .orElse(-1);
        long maxY = nanobots
                .stream()
                .mapToLong(s -> s.y)
                .max()
                .orElse(-1);
        long minZ = nanobots
                .stream()
                .mapToLong(s -> s.z)
                .min()
                .orElse(-1);
        long maxZ = nanobots
                .stream()
                .mapToLong(s -> s.z)
                .max()
                .orElse(-1);
        long minRange = nanobots
                .stream()
                .mapToLong(s -> s.range)
                .min()
                .orElse(-1);

        //strongest
        long maxNumInRange = nanobots
                .stream()
                .filter(s -> calcManhatten(s, strongestNanobot) <= strongestNanobot.range)
                .count();
        System.out.println("Range of strongest: " + strongestNanobot.range);
        System.out.println("Num in range of strongest: " + maxNumInRange);

        //findmost
        System.out.println("minX: " + minX);
        System.out.println("maxX: " + maxX);
        System.out.println("minY: " + minY);
        System.out.println("maxY: " + maxY);
        System.out.println("minZ: " + minZ);
        System.out.println("maxZ: " + maxZ);
        System.out.println("minRange: " + minRange);


        //Get the projections of the nanobots on the x-axis
        List<CoordRange> nanobotProjectionsXAxis = nanobots
                .stream()
                .map(nanobot -> new CoordRange(nanobot.x - nanobot.range, nanobot.x + nanobot.range, 1))
                .collect(Collectors.toList());
        //Construct ranges that contain the same number of nanobot-projection and convert into a histogram
        SortedMap<Long, ArrayList<CoordRange>> nanobotProjectionHistogramX = populateNanobotProjectionHistogram(nanobotProjectionsXAxis);

        //Get the projections of the nanobots on the y-axis
        List<CoordRange> nanobotProjectionsYAxis = nanobots
                .stream()
                .map(nanobot -> new CoordRange(nanobot.y - nanobot.range, nanobot.y + nanobot.range, 1))
                .collect(Collectors.toList());
        //Construct ranges that contain the same number of nanobot-projection and convert into a histogram
        SortedMap<Long, ArrayList<CoordRange>> nanobotProjectionHistogramY = populateNanobotProjectionHistogram(nanobotProjectionsYAxis);

        //Get the projections of the nanobots on the z-axis
        List<CoordRange> nanobotProjectionsZAxis = nanobots
                .stream()
                .map(nanobot -> new CoordRange(nanobot.z - nanobot.range, nanobot.z + nanobot.range, 1))
                .collect(Collectors.toList());
        //Construct ranges that contain the same number of nanobot-projection and convert into a histogram
        SortedMap<Long, ArrayList<CoordRange>> nanobotProjectionHistogramZ = populateNanobotProjectionHistogram(nanobotProjectionsZAxis);

        ArrayList<CoordRange> maxNanobotProjectionsX = nanobotProjectionHistogramX.get(nanobotProjectionHistogramX.firstKey());
        ArrayList<CoordRange> maxNanobotProjectionsY = nanobotProjectionHistogramY.get(nanobotProjectionHistogramY.firstKey());
        ArrayList<CoordRange> maxNanobotProjectionsZ = nanobotProjectionHistogramZ.get(nanobotProjectionHistogramZ.firstKey());

        ArrayList<Long> xCoordinates = new ArrayList<>();
        for (CoordRange coordRange: maxNanobotProjectionsX) {
            for (long x = coordRange.start; x <= coordRange.end; x++) {
                xCoordinates.add(x);
            }
        }

        ArrayList<Long> yCoordinates = new ArrayList<>();
        for (CoordRange coordRange: maxNanobotProjectionsY) {
            for (long y = coordRange.start; y <= coordRange.end; y++) {
                yCoordinates.add(y);
            }
        }

        ArrayList<Long> zCoordinates = new ArrayList<>();
        for (CoordRange coordRange: maxNanobotProjectionsZ) {
            for (long z = coordRange.start; z <= coordRange.end; z++) {
                zCoordinates.add(z);
            }
        }

        long maxNanobots = 0;
        HashMap<Long, ArrayList<XYZPoint>> pointsMap = new HashMap<>();
        for (long x: xCoordinates) {
            for (long y: yCoordinates) {
                for (long z: zCoordinates) {
                    long numNanobots = getNumNanobots(x, y, z);
                    if (numNanobots > maxNanobots) {
                        pointsMap.remove(maxNanobots);
                        maxNanobots = numNanobots;
                        pointsMap.put(maxNanobots, new ArrayList<>(Arrays.asList(new XYZPoint(x, y, z))));
                    } else if (numNanobots == maxNanobots) {
                        pointsMap.get(maxNanobots).add(new XYZPoint(x, y, z));
                    }
                }
            }
        }
        XYZPoint closest = new XYZPoint(Long.MAX_VALUE, Long.MAX_VALUE, Long.MAX_VALUE);
        for (XYZPoint xyzPoint: pointsMap.get(maxNanobots)) {
            if (Math.abs(xyzPoint.x) + Math.abs(xyzPoint.y) + Math.abs(xyzPoint.z) < Math.abs(closest.x) + Math.abs(closest.y) + Math.abs(closest.z)) {
                closest = xyzPoint;
            }
        }
        System.out.println(closest.x + closest.y + closest.z);








    }

    private static SortedMap<Long, ArrayList<CoordRange>> populateNanobotProjectionHistogram(List<CoordRange> nanobotProjectionsXAxis) {
        List<CoordRange> nanobotProjectionRanges = getCoordRangesOnXAxis(nanobotProjectionsXAxis);
        SortedMap<Long, ArrayList<CoordRange>> nanobotProjectionHistogram = new TreeMap<>(Collections.reverseOrder());
        for (CoordRange coordRange: nanobotProjectionRanges) {
            long key = coordRange.value;
            if (nanobotProjectionHistogram.containsKey(key)) {
                nanobotProjectionHistogram.get(key).add(coordRange);
            }
            else {
                nanobotProjectionHistogram.put(key, new ArrayList<>(Arrays.asList(coordRange)));
            }
        }
        return nanobotProjectionHistogram;
    }

    /**
     * Takes a list of projections (of nanobots) and converts these to coordRanges with the same number of projections
     *
     * @param projectionsToProcess      the list of projections
     * @return                          a list of CoordRange with the same number of projections
     */
    private static List<CoordRange> getCoordRangesOnXAxis(List<CoordRange> projectionsToProcess) {
        ArrayList<CoordRange> coordRanges = new ArrayList<>();
        while (projectionsToProcess.size() > 0) {
            CoordRange coordRangeToProcess = projectionsToProcess.remove(0);
            ArrayList<CoordRange> newCoordRanges = new ArrayList<>(coordRanges);
            for (CoordRange coordRange: coordRanges) {
                if (coordRange.inRange(coordRangeToProcess.start) && coordRange.inRange(coordRangeToProcess.end)) {
                    if (coordRange.start <= coordRangeToProcess.start - 1) {
                        newCoordRanges.add(new CoordRange(coordRange.start, coordRangeToProcess.start - 1, coordRange.value));
                    }
                    newCoordRanges.add(new CoordRange(coordRangeToProcess.start, coordRangeToProcess.end, coordRangeToProcess.value + coordRange.value));
                    if (coordRangeToProcess.end + 1 <= coordRange.end) {
                        newCoordRanges.add(new CoordRange(coordRangeToProcess.end + 1, coordRange.end, coordRange.value));
                    }
                    newCoordRanges.remove(coordRange);
                    coordRangeToProcess = null;
                    break;
                }
                else if (coordRange.inRange(coordRangeToProcess.start)) {
                    if (coordRange.start <= coordRangeToProcess.start - 1) {
                        newCoordRanges.add(new CoordRange(coordRange.start, coordRangeToProcess.start - 1, coordRange.value));
                    }
                    newCoordRanges.add(new CoordRange(coordRangeToProcess.start, coordRange.end, coordRangeToProcess.value + coordRange.value));
                    projectionsToProcess.add(new CoordRange(coordRange.end + 1, coordRangeToProcess.end, coordRangeToProcess.value));
                    newCoordRanges.remove(coordRange);
                    coordRangeToProcess = null;
                    break;

                }
                else if (coordRange.inRange(coordRangeToProcess.end)) {
                    projectionsToProcess.add(new CoordRange(coordRangeToProcess.start, coordRange.start - 1, coordRangeToProcess.value));
                    newCoordRanges.add(new CoordRange(coordRange.start, coordRangeToProcess.end, coordRangeToProcess.value + coordRange.value));
                    if (coordRangeToProcess.end + 1 <= coordRange.end) {
                        newCoordRanges.add(new CoordRange(coordRangeToProcess.end + 1, coordRange.end, coordRange.value));
                    }
                    newCoordRanges.remove(coordRange);
                    coordRangeToProcess = null;
                    break;
                }
            }
            if (null != coordRangeToProcess) {
                newCoordRanges.add(coordRangeToProcess);
            }
            coordRanges = newCoordRanges;
        }
        return coordRanges;
    }

    private static long calcManhatten(Nanobot nanobot1, Nanobot nanobot2) {
        return Math.abs(nanobot1.x - nanobot2.x) + Math.abs(nanobot1.y - nanobot2.y) + Math.abs(nanobot1.z - nanobot2.z);
    }

    private static long getNumNanobots(long x, long y, long z) {
        return nanobots
                .stream()
                .filter(nanobot -> Math.abs(x - nanobot.x) <= nanobot.range
                        && Math.abs(y - nanobot.y) <= nanobot.range
                        && Math.abs(z - nanobot.z) <= nanobot.range)
                .count();
    }

}