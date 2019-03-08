package Day13;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.awt.*;
import java.io.FileNotFoundException;

public class Day13 {

    public static void main (String[] args) {
        try {
            System.out.println("ssssssssssss");
            TunnelSystem tunnelSystem = new TunnelSystem();
            boolean collision = false;
            while (!collision) {
                tunnelSystem.moveCharts();
                Cart collidingCart = tunnelSystem.collision();
                collision = (collidingCart != null);
            }
            System.out.println(tunnelSystem.collision().getLocation());

            tunnelSystem = new TunnelSystem();
            while (tunnelSystem.getNumCarts() > 1) {
                tunnelSystem.moveCharts();
                tunnelSystem.removeCollisions();
            }
            System.out.println(tunnelSystem.carts.get(0).getLocation());


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
