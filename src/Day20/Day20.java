package Day20;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Day20 {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("E:\\input20.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String input = reader.lines().collect(Collectors.toList()).get(0);
        input = input.substring(1, input.length() - 1);
//        input = "WNE"; //3  OK
//        input = "ENWWW(NEEE|SSE(EE|N))"; //10  OK
//        input = "ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN"; //18  OK
//        input = "ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))"; //23  OK
//        input = "WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))"; //31  OK
//        input = "NN(NN|EE(WW|NN))"; //6   OK
//        input = "N(NEWS|)N"; //3    OK
//        input = "NN(EW|)N(NN|EE|W(S|W))";
        input = input.replaceAll("\\|\\)", "|X)");
        System.out.println(input);
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<Room> roomsToProcess = new ArrayList<>();
        Room room = new Room(new Point(0,0), 0, input);
        roomsToProcess.add(room);
        while (roomsToProcess.size() > 0) {
            Room roomToProcess = roomsToProcess.get(0);
            if (roomToProcess.hasPathsToGo()) {
                Room toAdd = roomToProcess.takeStep();
                roomsToProcess.add(toAdd);
            }
            else {
                roomsToProcess.remove(roomToProcess);
                if (addRoom(rooms, roomToProcess)) {
                    rooms.add(roomToProcess);
                }
            }
        }
        int max = Integer.MIN_VALUE;
        int numThousand = 0;
        for (Room r: rooms) {
            if (r.doorsOpened > max) {
                max = r.doorsOpened;
            }
            if (r.doorsOpened >= 1000) {
                numThousand++;
            }
        }
        System.out.println(max);
        System.out.println(numThousand);



    }

    private static boolean addRoom(ArrayList<Room> rooms, Room room) {
        boolean addRoom = true;
        for (Room roomInRooms: rooms) {
            if (roomInRooms.equals(room)) {
                if (room.doorsOpened >= roomInRooms.doorsOpened) {
                    addRoom = false;
                }
            }
        }
        return addRoom;
    }

    private static void print(ArrayList<Room> rooms) {
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (Room room: rooms) {
            minX = Math.min(minX, room.coordinates.x);
            maxX = Math.max(maxX, room.coordinates.x);
            minY = Math.min(minY, room.coordinates.y);
            maxY = Math.max(minY, room.coordinates.y);
        }
        char[][] field = new char[2 * (maxX - minX) + 1][2 * (maxY - minY) + 1];
        for (int i = 0; i < field[0].length; i++) {
            for (int j = 0; j < field.length; j++) {
                field[i][j] = '#';
            }
        }


    }
}
