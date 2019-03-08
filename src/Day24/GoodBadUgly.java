package Day24;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class GoodBadUgly {

    static class Villain {
        String name;
        int good;
        int bad;
        int ugly;

        Villain(String name, int good, int bad, int ugly) {
            this.name = name;
            this.good = good;
            this.bad = bad;
            this.ugly = ugly;
        }
    }

    public static void main(String[] args) {
        List<Villain> villains = new ArrayList<>();
        villains.add(new Villain("Andy", 2, 2, 2));
        villains.add(new Villain("Bob", 2, 2, 1));
        villains.add(new Villain("Charley", 2, 1, 2));
        villains.add(new Villain("Dave", 2, 1, 1));
        villains.add(new Villain("Eddy", 1, 2, 1));
        villains.add(new Villain("Franz", 1, 2, 1));
        villains.add(new Villain("Guy", 1, 1, 2));
        villains.add(new Villain("Harry", 1, 1, 1));

//        List<Villain> bestVillains = villains
//                .stream()
//                .collect(groupingBy(v -> v.good, TreeMap::new, toList()))
//                .lastEntry()
//                .getValue()
//                .stream()
//                .collect(groupingBy(v -> v.bad, TreeMap::new, toList()))
//                .lastEntry()
//                .getValue()
//                .stream()
//                .collect(groupingBy(v -> v.ugly, TreeMap::new, toList()))
//                .lastEntry()
//                .getValue();


        Comparator<Villain> villainComparator = Comparator.<Villain>
                comparingInt(villain -> villain.good)
                .thenComparingInt(villain -> -villain.bad)
                .thenComparingInt(villain -> villain.ugly);

        Villain result = villains.stream()
                .max(villainComparator)
                .orElse(null);

        if (null == result) {
            System.out.println("There is not a single best, worst, ugliest");
        }
        else {
            System.out.println("The best, worst, ugliest is " + result.name);
        }

    }
}
