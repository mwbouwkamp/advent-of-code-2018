package Day15;

import java.util.Comparator;

public class CreatureComparator implements Comparator<Creature> {

    @Override
    public int compare(Creature creature1, Creature creature2) {
        if (creature1.getLocation().y > creature2.getLocation().y) {
            return 1;
        }
        else if (creature1.getLocation().y < creature2.getLocation().y) {
            return -1;
        }
        else if (creature1.getLocation().x > creature2.getLocation().x) {
            return 1;
        }
        else if (creature1.getLocation().x < creature2.getLocation().x) {
            return -1;
        }
        return 0;
    }
}

