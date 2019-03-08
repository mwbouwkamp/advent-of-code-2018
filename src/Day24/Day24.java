package Day24;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Day24 {

    static Map<String, AttackMode> attackModeMap;
    static ArrayList<Group> originalGroups;

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("E:\\input24.txt");
        List<String> lines = new BufferedReader(new FileReader(file)).lines().collect(toList());
        attackModeMap = populateAttackModeMap();
        originalGroups = initGroups(lines);

        String winner;

        String type = "day1";
        if (type.equals("day1")) {
            winner = calcResult(originalGroups);
            System.out.println(winner);
        }
        else {

//
//        long low = 0;
//        long high = 100;
//        boolean loosing = true;
//        while (low != high) {
//            System.out.println("---");
//            long boost = (high + low) / 2;
//            System.out.println("Boost:  " + boost);
//            ArrayList<Group> newGroup = new ArrayList<>();
//            for (Group group: originalGroups) {
//                Group groupToAdd;
//                if (group.type.equals("Immune System")) {
//                    Unit newUnit = new Unit.UnitBuilder(group.unit.hitpoints, group.unit.attackMode, group.unit.damage + boost, group.unit.initiative)
//                            .setImmunity(group.unit.immunity)
//                            .setWeakness(group.unit.weakness)
//                            .build();
//                    groupToAdd = new Group(group.name, group.type, newUnit, group.numberOfUnits);
//                }
//                else {
//                    Unit newUnit = new Unit.UnitBuilder(group.unit.hitpoints, group.unit.attackMode, group.unit.damage, group.unit.initiative)
//                            .setImmunity(group.unit.immunity)
//                            .setWeakness(group.unit.weakness)
//                            .build();
//                    groupToAdd = new Group(group.name, group.type, newUnit, group.numberOfUnits);
//                }
//                newGroup.add(groupToAdd);
//            }
//            winner = calcResult(newGroup);
//            System.out.println(winner);
//            if (winner.equals("Infection")) {
//                low = boost + 1;
//            }
//            else {
//                high = boost - 1;
//            }
//        }

            //4035 too high

            int boost = 34;
            ArrayList<Group> newGroup = new ArrayList<>();
            for (Group group : originalGroups) {
                Group groupToAdd;
                if (group.type.equals("Immune System")) {
                    Unit newUnit = new Unit.UnitBuilder(group.unit.hitpoints, group.unit.attackMode, group.unit.damage + boost, group.unit.initiative)
                            .setImmunity(group.unit.immunity)
                            .setWeakness(group.unit.weakness)
                            .build();
                    groupToAdd = new Group(group.name, group.type, newUnit, group.numberOfUnits);
                } else {
                    Unit newUnit = new Unit.UnitBuilder(group.unit.hitpoints, group.unit.attackMode, group.unit.damage, group.unit.initiative)
                            .setImmunity(group.unit.immunity)
                            .setWeakness(group.unit.weakness)
                            .build();
                    groupToAdd = new Group(group.name, group.type, newUnit, group.numberOfUnits);
                }
                newGroup.add(groupToAdd);
            }
            winner = calcResult(newGroup);
            System.out.println(winner);
        }

    }

    private static String calcResult(ArrayList<Group> groups) {
        while(bothAlive(groups)) {
            //target selection
            Comparator<Group> targetSelectionOrderComparator = Comparator.<Group>
                    comparingLong(group -> -group.effectivePower)
                    .thenComparingLong(group -> group.unit.initiative);
            Collections.sort(groups, targetSelectionOrderComparator);
            List<Group> targets = new ArrayList<>(groups);
            for (Group attacker: groups) {
                List<Group> targetsForAttacker = targets
                        .stream()
                        .filter(group -> !group.type.equals(attacker.type))     //Make sure it is not attacking its own
                        .filter(group -> !group.unit.immunity.contains(attacker.unit.attackMode))      //Make sure it is not selecting a target that is immune
//                        .filter(group -> attacker.effectivePower * group.unit.calcDamageFactor(attacker.unit) >= group.unit.hitpoints)      //Make sure it does damage
                        .collect(Collectors.toList());
                Comparator<Group> groupComparator = Comparator.<Group>
                        comparingLong(group -> group.unit.calcDamageFactor(attacker.unit))
                        .thenComparingLong(group -> group.effectivePower)
                        .thenComparingLong(group -> group.unit.initiative);
                Group selectedTarget = targetsForAttacker
                        .stream()
                        .filter(target -> !attacker.type.equals(target.type))
                        .max(groupComparator)
                        .orElse(null);
                if (null != selectedTarget) {
                    attacker.target = selectedTarget;
                    targets.remove(selectedTarget);
                }
            }

            //Attacking
            Comparator<Group> attackingOrderComparator = Comparator.<Group>
                    comparingLong(group -> -group.unit.initiative);
            groups.sort(attackingOrderComparator);
            for (Group attacker : groups) {
                if (null != attacker.target && attacker.effectivePower > 0) {
                    System.out.println("ATTACK: " + attacker + " ||||| ATTACKS |||| " + attacker.target);
                    long damage = attacker.effectivePower * attacker.target.unit.calcDamageFactor(attacker.unit);
                    System.out.println("DAMAGE " + damage);
                    attacker.target.numberOfUnits -= damage / attacker.target.unit.hitpoints;
                    attacker.target.calcEffectivePower();
                }
            }

            //Cleanup dead groups
            ArrayList<Group> livingGroups = new ArrayList<>();
            for (Group group : groups) {
                group.target = null;
                if (group.numberOfUnits > 0) {
                    livingGroups.add(group);
                }

            }
            groups = livingGroups;
        }
        long result = 0;
        for (Group group: groups) {
            result += group.numberOfUnits;
        }
        System.out.println("Units left: " + result);
        return groups.get(0).type;
    }

    private static boolean bothAlive(ArrayList<Group> groups) {
        long immuneSystem = 0;
        long infection = 0;
        for (Group group: groups) {
            switch (group.type) {
                case"Immune System":
                    immuneSystem++;
                    break;
                case "Infection":
                    infection++;
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
        return immuneSystem > 0 && infection > 0;
    }

    private static ArrayList<Group> initGroups(List<String> lines) {
        ArrayList<Group> groups = new ArrayList<>();
        String groupType = "";
        long immuneSystemIndex = 0;
        long infectionSystemIndex = 0;
        while (lines.size() > 0) {
            String lineToProcess = lines.remove(0);
            if (lineToProcess.equals("Immune System:") || lineToProcess.equals("Infection:")) {
                groupType = lineToProcess;
            }
            else if (lineToProcess.length() > 0) {
                //Get substrings to process data
                String unitsHitpoints;
                String immunityWeaknesses;
                if (lineToProcess.contains("(")) {
                    immunityWeaknesses = lineToProcess.substring(lineToProcess.indexOf("(") + 1,lineToProcess.indexOf(")"));
                    unitsHitpoints = lineToProcess.substring(0, lineToProcess.indexOf("("));
                }
                else {
                    immunityWeaknesses = "";
                    unitsHitpoints = lineToProcess.substring(0, lineToProcess.indexOf("with an attack"));
                }
                String damageInitiative = lineToProcess.substring(lineToProcess.indexOf("with an attack"));

                //process unitsHitpoints
                String[] unitsHitpointsArray = unitsHitpoints.split("[;,\\s]+");
                long numberOfUnits = Integer.parseInt(unitsHitpointsArray[0]);
                long hitpoints = Integer.parseInt(unitsHitpointsArray[4]);

                //processs immunityWeakness
                String[] immunityWeaknessArray = immunityWeaknesses.split("[;,\\s]+");
                String immunityOrWeakness = "";
                ArrayList<AttackMode> immunities = new ArrayList<>();
                ArrayList<AttackMode> weaknesses = new ArrayList<>();
                for (String string: immunityWeaknessArray) {
                    if (string.equals("immune") || string.equals("weak")) {
                        immunityOrWeakness = string;
                    }
                    else {
                        if (!string.equals("to") && string.length() > 0) {
                            switch (immunityOrWeakness) {
                                case "immune":
                                    immunities.add(attackModeMap.get(string));
                                    break;
                                case "weak":
                                    weaknesses.add(attackModeMap.get(string));
                                    break;
                                default:
                                    throw new IllegalArgumentException("Illegal value immunitiyOrWeakness: " + immunityOrWeakness + " in lineToProcess: " + lineToProcess);
                            }
                        }
                    }
                }

                //Process damageUnitiative
                String[] damageInitiativeArray = damageInitiative.split("[;,\\s]+");
                long damage = Integer.parseInt(damageInitiativeArray[5]);
                AttackMode attackMode = attackModeMap.get(damageInitiativeArray[6]);
                long initiative = Integer.parseInt(damageInitiativeArray[10]);

               //Make unit
                Unit.UnitBuilder unitBuilder = new Unit.UnitBuilder(hitpoints, attackMode, damage, initiative);
               if (immunities.size() > 0) {
                   unitBuilder.setImmunity(immunities);
               }
               if (weaknesses.size() > 0) {
                   unitBuilder.setWeakness(weaknesses);
               }
               Unit unitToAdd = unitBuilder.build();

               String groupTypeName;
               String groupName;
               switch (groupType) {
                   case "Immune System:":
                       immuneSystemIndex++;
                       groupTypeName = "Immune System";
                       groupName = "IS " + immuneSystemIndex;
                       break;
                   case "Infection:":
                       infectionSystemIndex++;
                       groupTypeName = "Infection";
                       groupName = "IF " + infectionSystemIndex;
                       break;
                   default:
                       throw new IllegalArgumentException();
               }
               groups.add(new Group(groupName, groupTypeName, unitToAdd, numberOfUnits));
            }
        }
        return groups;
    }

    private static Map<String, AttackMode> populateAttackModeMap() {
        Map<String, AttackMode> attackModeMap = new TreeMap<>();
        attackModeMap.put("fire", AttackMode.FIRE);
        attackModeMap.put("slashing", AttackMode.SLASHING);
        attackModeMap.put("bludgeoning", AttackMode.BLUDGEONING);
        attackModeMap.put("radiation", AttackMode.RADIATION);
        attackModeMap.put("cold", AttackMode.COLD);
        return attackModeMap;
    }

}
