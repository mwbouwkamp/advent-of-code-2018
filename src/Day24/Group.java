package Day24;

import java.util.*;


public class Group {

    String name;
    String type;
    Unit unit;
    long numberOfUnits;
    long effectivePower;
    Group target;

    Group(String name, String type, Unit unit, long numberOfUnits) {
        this.name = name;
        this.type = type;
        this.unit = unit;
        this.numberOfUnits = numberOfUnits;
        calcEffectivePower();
    }

    void calcEffectivePower() {
        this.effectivePower = numberOfUnits * unit.damage;
    }

    @Override
    public String toString() {
        return type + " with " + numberOfUnits + " units: " + unit;
    }
}
