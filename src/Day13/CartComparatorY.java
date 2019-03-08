package Day13;

import java.util.Comparator;

public class CartComparatorY implements Comparator<Cart> {

    @Override
    public int compare(Cart cart1, Cart cart2) {
        if (cart1.getLocation().y > cart2.getLocation().y) {
            return 1;
        }
        else if (cart1.getLocation().y < cart2.getLocation().y) {
            return -1;
        }
        else if (cart1.getLocation().x > cart2.getLocation().x) {
            return 1;
        }
        else if (cart1.getLocation().x < cart2.getLocation().x) {
            return -1;
        }
        return 0;
    }
}

