package Day13;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TunnelSystem {

    String[][] tunnelSystem;
    ArrayList<Cart> carts;
    HashMap<Long, Cart> cartlocations;

    //50,y=54

    public TunnelSystem() throws FileNotFoundException {
        File file = new File("E:\\input13.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        List<String> lines = bufferedReader.lines().collect(Collectors.toList());
        int numRows = lines.size();
        int numColumns = lines.stream().mapToInt(String::length).max().orElse(-1);
        tunnelSystem = new String[numRows][numColumns];
        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j++)
            tunnelSystem[j][i] = lines.get(j).split("")[i];
        }
        carts = new ArrayList<Cart>();
        cartlocations = new HashMap<Long, Cart>();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                Cart carToAdd = null;
                switch (tunnelSystem[i][j]) {
                    case "<":
                        carToAdd = new Cart(new Point(j, i), "<");
                        tunnelSystem[i][j] = "-";
                        break;
                    case ">":
                        carToAdd = new Cart(new Point(j, i), ">");
                        tunnelSystem[i][j] = "-";
                        break;
                    case "^":
                        carToAdd = new Cart(new Point(j, i), "^");
                        tunnelSystem[i][j] = "|";
                        break;
                    case "v":
                        carToAdd = new Cart(new Point(j, i), "v");
                        tunnelSystem[i][j] = "|";
                        break;
                    default:
                        //do nothing
                }
                if (carToAdd != null) {
                    carts.add(carToAdd);
                    cartlocations.put(carToAdd.getCoordinatesCode(), carToAdd);
                }
            }
        }
        System.out.println("FIRST");
        System.out.println(this);
        System.out.println("/FIRST");

        carts.sort(new CartComparator());
    }

    /**
     * Moves carts
     */
    public void moveCharts() {
        String newRail;
        for (Cart cart: carts) {
            switch (cart.getDirection()) {
                case "<":
                    newRail = tunnelSystem[cart.getLocation().y][cart.getLocation().x - 1];
                    switch (newRail) {
                        case "\\":
                            cart.rotateRight();
                            break;
                        case "/":
                            cart.rotateLeft();
                            break;
                        case "+":
                            intersectionAction(cart);
                            break;
                        default:
                            //Do Nothing
                            break;
                    }
                    cart.move("W", newRail);
                    break;

                case ">":
                    newRail = tunnelSystem[cart.getLocation().y][cart.getLocation().x + 1];
                    switch (newRail) {
                        case "\\":
                            cart.rotateRight();
                            break;
                        case "/":
                            cart.rotateLeft();
                            break;
                        case "+":
                            intersectionAction(cart);
                            break;
                        default:
                            //Do Nothing
                            break;
                    }
                    cart.move("E", newRail);
                    break;

                case "^":
                    newRail = tunnelSystem[cart.getLocation().y - 1][cart.getLocation().x];
                    switch (newRail) {
                        case "\\":
                            cart.rotateLeft();
                            break;
                        case "/":
                            cart.rotateRight();
                            break;
                        case "+":
                            intersectionAction(cart);
                            break;
                        default:
                            //Do Nothing
                            break;
                    }
                    cart.move("N", newRail);
                    break;

                case "v":
                    newRail = tunnelSystem[cart.getLocation().y + 1][cart.getLocation().x];
                    switch (newRail) {
                        case "\\":
                            cart.rotateLeft();
                            break;
                        case "/":
                            cart.rotateRight();
                            break;
                        case "+":
                            intersectionAction(cart);
                            break;
                        default:
                            //Do Nothing
                            break;
                    }
                    cart.move("S", newRail);
                    break;

                default:
                    //Do nothing
                    break;
            }
        }
        carts.sort(new CartComparator());
    }

    public int getNumCarts() {
        return carts.size();
    }

    private void intersectionAction(Cart cart) {
        switch (cart.getAndChangeIntersectionBehavior()) {
            case "left":
                cart.rotateLeft();
                break;
            case "straight":
                //Do Nothing
                break;
            case "right":
                cart.rotateRight();
                break;
            default:
                //Do Nothing
                break;
        }
    }

    /**
     * Looks for collisions
     * Assumes that the list of carts is sorted
     *
     * @return  cart that collides or null if there is no collision
     */
    public Cart collision() {
        for (int i = 0; i < carts.size() - 1; i++) {
            if (carts.get(i).getLocation().x == carts.get(i + 1).getLocation().x && carts.get(i).getLocation().y == carts.get(i + 1).getLocation().y ) {
                return carts.get(i);
            }
        }
        return null;
    }

    public ArrayList<Cart> getCarts() {
        return carts;
    }

    public void removeCollisions() {
        for (int i = 0; i < carts.size() - 1; i++) {
            if (carts.get(i).getLocation().x == carts.get(i + 1).getLocation().x && carts.get(i).getLocation().y == carts.get(i + 1).getLocation().y ) {
                System.out.println(carts.size() + "X");
                System.out.println(carts.get(i).getLocation() + carts.get(i).getDirection());
                System.out.println(carts.get(i + 1).getLocation() + carts.get(i + 1).getDirection());
                System.out.println(this);
                carts.remove(i + 1);
                carts.remove(i);
            }
        }
        for (int i = 0; i < carts.size() - 1; i++) {
            if ((Math.abs(carts.get(i).getLocation().x - carts.get(i + 1).getLocation().x) == 1) && (carts.get(i).getLocation().y == carts.get(i + 1).getLocation().y)) {
                if ((carts.get(i).getDirection().equals("<") && carts.get(i + 1).getDirection().equals(">"))) {
                    System.out.println(carts.size() + "<>");
                    System.out.println(carts.get(i).getLocation() + carts.get(i).getDirection());
                    System.out.println(carts.get(i + 1).getLocation() + carts.get(i + 1).getDirection());
                    System.out.println(this);
                    carts.remove(i + 1);
                    carts.remove(i);
                }
                if ((carts.get(i).getDirection().equals("<") && carts.get(i + 1).getDirection().equals("<")) && carts.get(i + 1).getRail().equals("+")) {
                    System.out.println(carts.size() + "<+<");
                    System.out.println(carts.get(i).getLocation() + carts.get(i).getDirection());
                    System.out.println(carts.get(i + 1).getLocation() + carts.get(i + 1).getDirection());
                    System.out.println(this);
                    carts.remove(i + 1);
                    carts.remove(i);
                }
                if ((carts.get(i).getDirection().equals(">") && carts.get(i + 1).getDirection().equals(">")) && carts.get(i).getRail().equals("+")) {
                    System.out.println(carts.size() + ">+>");
                    System.out.println(carts.get(i).getLocation() + carts.get(i).getDirection());
                    System.out.println(carts.get(i + 1).getLocation() + carts.get(i + 1).getDirection());
                    System.out.println(this);
                    carts.remove(i + 1);
                    carts.remove(i);
                }
            }
        }
        carts.sort(new CartComparatorY());
        for (int i = 0; i < carts.size() - 1; i++) {
            if ((Math.abs(carts.get(i).getLocation().y - carts.get(i + 1).getLocation().y) == 1) && (carts.get(i).getLocation().x == carts.get(i + 1).getLocation().x)) {
                if ((carts.get(i).getDirection().equals("^") && carts.get(i + 1).getDirection().equals("v"))) {
                    System.out.println(carts.size() + "^v");
                    System.out.println(carts.get(i).getLocation() + carts.get(i).getDirection());
                    System.out.println(carts.get(i + 1).getLocation() + carts.get(i + 1).getDirection());
                    System.out.println(this);
                    carts.remove(i + 1);
                    carts.remove(i);
                }
                if ((carts.get(i).getDirection().equals("v") && carts.get(i + 1).getDirection().equals("v")) && carts.get(i + 1).getRail().equals("+")) {
                    System.out.println(carts.size() + "v+v");
                    System.out.println(carts.get(i).getLocation() + carts.get(i).getDirection());
                    System.out.println(carts.get(i + 1).getLocation() + carts.get(i + 1).getDirection());
                    System.out.println(this);
                    carts.remove(i + 1);
                    carts.remove(i);
                }
                if ((carts.get(i).getDirection().equals("^") && carts.get(i + 1).getDirection().equals("^")) && carts.get(i).getRail().equals("+")) {
                    System.out.println(carts.size() + "^+^");
                    System.out.println(carts.get(i).getLocation() + carts.get(i).getDirection());
                    System.out.println(carts.get(i + 1).getLocation() + carts.get(i + 1).getDirection());
                    System.out.println(this);
                    carts.remove(i + 1);
                    carts.remove(i);
                }
            }
        }
        carts.sort(new CartComparator());

    }

    @Override
    public String toString() {
        String stringToReturn = "";
        for (int i = 0; i < tunnelSystem.length; i++) {
            for (int j = 0; j < tunnelSystem[0].length; j++) {
                boolean cartAtPosition = false;
                for (Cart cart: carts) {
                    if (cart.getLocation().x == j && cart.getLocation().y == i) {
                        stringToReturn += cart.getDirection();
                        cartAtPosition = true;
                    }
                }
                if (!cartAtPosition) {
                    stringToReturn += tunnelSystem[i][j];
                }
            }
            stringToReturn += "\n";
        }
        stringToReturn += "\n";
        stringToReturn += "\n";
        return stringToReturn;
    }

}
