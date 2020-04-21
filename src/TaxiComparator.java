package gpsp;

import java.util.Comparator;

public class TaxiComparator implements Comparator<Taxi> {

    public int compare(Taxi a, Taxi b){
        if (a.rating < b.rating) return 1;
        if (a.rating > b.rating) return -1;
        return 0;
    }

}
