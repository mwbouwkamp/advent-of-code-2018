package Day4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Day4 {

    public static void main(String[] args) {

        File file = new File("e:\\input4.txt");
        try {
            //Read lines, construct list of Events and sort based on date
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            List<String> lines = bufferedReader.lines().collect(Collectors.toList());
            LinkedList<Event> events = new LinkedList<>();
            for (String line: lines) {
                Date date = getDateFromLine(line);
                String content = getContentFromLine(line);
                events.add(new Event(date, content));
            }
            Collections.sort(events);

            //Duplicate lines with "Guard" to mark start and end of shift (assumes first entry is a line with "Guard")
            for (int i = 1; i < events.size(); i++) {
                if (events.get(i).getContent().contains("Guard")) {
                    events.add(i, events.get(i));
                    i++;
                }
            }

            //Create guards
            Map<String, LinkedList<Event>> eventsOfGuards = new HashMap<>();
            while (events.size() > 0) {
                Event guardExchangeEvent = events.remove(0);
                int index = guardExchangeEvent.getContent().indexOf('#');
                String guardID = guardExchangeEvent.getContent().substring(index, index + 5);
                while (events.size() > 0) {
                    Event eventToCheck = events.remove(0);
                    if (!eventsOfGuards.containsKey(guardID)) {
                        LinkedList<Event> guardEvents = new LinkedList<>();
                        guardEvents.add(guardExchangeEvent);
                        guardEvents.add(eventToCheck);
                        eventsOfGuards.put(guardID, guardEvents);
                    }
                    else {
                        eventsOfGuards.get(guardID).add(eventToCheck);
                    }
                    if (eventToCheck.getContent().contains("Guard")) {
                        break;
                    }
                }
            }
            LinkedList<Guard> guards = new LinkedList<>();
            for (String guard: eventsOfGuards.keySet()) {
                guards.add(new Guard(guard, eventsOfGuards.get(guard)));
            }

            // Solve Day4a
            Guard sleepsMost = guards.stream().max(Comparator.comparingInt(Guard::getAmountOfSleep)).get();
            int maxIndex = sleepsMost.getMaxSleepMinute();
            String guardId = sleepsMost.getGuardID();
            int guardNumber = Integer.parseInt(guardId.substring(1, guardId.length()));
            System.out.println(maxIndex * guardNumber); // 77941

            // Solve Day4b
            Guard sleepsALot = guards.stream().max(Comparator.comparingInt(Guard::getMaxSleepAmountOfMinutes)).get();
            maxIndex = sleepsALot.getMaxSleepMinute();
            guardId = sleepsALot.getGuardID();
            guardNumber = Integer.parseInt(guardId.substring(1, guardId.length()));
            System.out.println(maxIndex * guardNumber);

        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static String getContentFromLine(String line) {
        return line.substring(19);
    }

    private static Date getDateFromLine(String line) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = line.substring(1, 17);
        return format.parse(dateString);
    }

    private static class Event implements Comparable<Event> {
        Date date;
        String content;

        private Event(Date date, String content) {
            this.date = date;
            this.content = content;
        }

        private Date getDate() {
            return date;
        }

        private String getContent() {
            return content;
        }

        @Override
        public int compareTo(Event o) {
            if (getDate().compareTo(o.getDate()) == 0) {
                if (content.contains("falls")) {
                    return 1;
                }
                else {
                    return -1;
                }
            }
            return getDate().compareTo(o.getDate());
        }
    }

    private static class Guard {
        String guardID;
        Map<String, int[]> asleep;

        private Guard(String guardID, LinkedList<Event> guardEntries) {
            this.guardID = guardID;
            asleep = new HashMap<>();
            while (guardEntries.size() > 0) {
                Event start = guardEntries.remove(0);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(start.getDate());
                String day = calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
                int[] minutes = new int[60];
                asleep.put(day, minutes);
                if (!start.getContent().contains("Guard")) {
                    guardEntries.add(0, start);
                }
                while (guardEntries.size() > 0) {
                    Event eventToCheck = guardEntries.remove(0);
                    if (eventToCheck.getContent().contains("falls")) {
                        int minutesPast = getMinutes(eventToCheck);
                        minutes[minutesPast] = 1;
                    }
                    else if (eventToCheck.getContent().contains("wakes")) {
                        int minutesPast = getMinutes(eventToCheck);
                        minutes[minutesPast] = -1;
                    }
                    else {
                        break;
                    }
                }
            }
            for (String day: asleep.keySet()) {
                int[] toModify = asleep.get(day);
                if (toModify[0] == 0) {
                    toModify[0] = -1;
                }
                for (int i = 1; i < toModify.length; i++) {
                    if (toModify[i] == 0) {
                        toModify[i] = toModify[i - 1];
                    }
                }
            }
        }

        public int getMaxSleepMinute() {
            int[] sleepFrequency = getFrequencyArray();
            int max = 0;
            int maxIndex = 0;
            for (int i = 0; i < sleepFrequency.length; i++) {
                if (sleepFrequency[i] > max) {
                    max = sleepFrequency[i];
                    maxIndex = i;
                }
            }
            return maxIndex;
        }

        public int getMaxSleepAmountOfMinutes() {
            int[] sleepFrequency = getFrequencyArray();
            int max = 0;
            for (int i = 0; i < sleepFrequency.length; i++) {
                if (sleepFrequency[i] > max) {
                    max = sleepFrequency[i];
                }
            }
            return max;
        }

        private int[] getFrequencyArray() {
            int[] sleepFrequency = new int[60];
            for (String key: asleep.keySet()) {
                int[] sleep = asleep.get(key);
                for (int i = 0; i < sleep.length; i++) {
                    if (sleep[i] == 1) {
                        sleepFrequency[i] = sleepFrequency[i] + 1;
                    }
                }
            }
            return sleepFrequency;
        }

        public String toString() {
            String stringToReturn = getGuardID();
            stringToReturn += "\n";
            for (String day: asleep.keySet()) {
                stringToReturn += day + ": ";
                int[] array = asleep.get(day);
                for (int i = 0; i < array.length; i++) {
                    if (array[i] == -1) {
                        stringToReturn += ".";
                    }
                    else if (array[i] == 1) {
                        stringToReturn += "#";
                    }
                    else {
                        stringToReturn += "X";
                    }
                }
                stringToReturn += "\n";
            }
            stringToReturn += "\n";
            return stringToReturn;
        }

        private int getAmountOfSleep() {
            return toString().chars().filter(c -> c == '#').sum() - 1;
        }

        private int getMinutes(Event eventToCheck) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(eventToCheck.getDate());
            int intToReturn = calendar.get(Calendar.MINUTE);
            return intToReturn;
        }

        public String getGuardID() {
            return guardID;
        }

        public Map<String, int[]> getAsleep() {
            return asleep;
        }

    }
}