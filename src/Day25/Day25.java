package Day25;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day25 {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("E:\\input25.txt");
        List<XYZTPoint> points = new BufferedReader(new FileReader(file))
                .lines()
                .map(line -> line.split(","))
                .map(stringArray -> Arrays
                        .stream(stringArray)
                        .mapToInt(string -> Integer.parseInt(string)).toArray())
                .map(intArray -> new XYZTPoint(intArray[0], intArray[1], intArray[2], intArray[3]))
                .collect(Collectors.toList());

        List<Constellation> constellations = new ArrayList<>();
        for (XYZTPoint point: points) {
            List<Constellation> constellationsOfPoint = constellations
                    .stream()
                    .filter(constellation -> constellation.inConstellation(point))
                    .collect(Collectors.toList());
            if (constellationsOfPoint.size() == 0) {
                constellations.add(new Constellation(point));
            }
            else {
                Constellation newConstellation = constellationsOfPoint.remove(0);
                newConstellation.points.add(point);
                constellations.remove(newConstellation);
                while (constellationsOfPoint.size() > 0) {
                    Constellation constellationToAdd = constellationsOfPoint.remove(0);
                    constellations.remove(constellationToAdd);
                    newConstellation.points.addAll(constellationToAdd.points);
                }
                constellations.add(newConstellation);
            }
        }
        System.out.println(constellations.size());

    }
}
