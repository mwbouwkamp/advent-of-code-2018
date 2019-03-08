package Day24;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Unit {
    long hitpoints;
    AttackMode attackMode;
    ArrayList<AttackMode> immunity;
    ArrayList<AttackMode> weakness;
    long damage;
    long initiative;

    static class UnitBuilder {
        long hitpoints;
        AttackMode attackMode;
        ArrayList<AttackMode> immunity;
        ArrayList<AttackMode> weakness;
        long damage;
        long initiative;

        UnitBuilder(long hitpoints, AttackMode attackMode, long damage, long initiative) {
            this.hitpoints = hitpoints;
            this.attackMode = attackMode;
            this.immunity = new ArrayList<>();
            this.weakness = new ArrayList<>();
            this.damage = damage;
            this.initiative = initiative;
        }

        UnitBuilder setImmunity(ArrayList<AttackMode> immunity) {
            this.immunity = immunity;
            return this;
        }

        UnitBuilder setWeakness(ArrayList<AttackMode> weakness) {
            this.weakness = weakness;
            return this;
        }

        Unit build() {
            Unit unit = new Unit();
            unit.hitpoints = hitpoints;
            unit.attackMode = attackMode;
            unit.immunity = immunity;
            unit.weakness = weakness;
            unit.damage = damage;
            unit.initiative = initiative;
            return unit;
        }

    }

    long calcDamageFactor(Unit unit) {
        if (immunity.contains(unit.attackMode)) {
            return 0;
        }
        else if (weakness.contains(unit.attackMode)) {
            return 2;
        }
        else {
            return 1;
        }
    }

    @Override
    public String toString() {
        String toReturn = "Unit with hitpoints " + hitpoints
                + ", attackMode: " + attackMode
                + ", damage: " + damage
                + ", initiative: " + initiative;
        if (immunity.size() > 0) {
            toReturn += " and immunity";
            for (AttackMode attackMode: immunity) {
                toReturn += " " + attackMode;
            }
        }
        if (weakness.size() > 0) {
            toReturn += " and weakness";
            for (AttackMode attackMode: weakness) {
                toReturn += " " + attackMode;
            }
        }
        return toReturn;
    }


}
