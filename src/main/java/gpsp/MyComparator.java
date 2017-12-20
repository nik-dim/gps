package gpsp;

import java.util.Comparator;

public class MyComparator implements Comparator<Point> {

    public int compare(Point a, Point b){
        if (a.distance + a.heuristic < b.distance + b.heuristic) return -1;
        if (a.distance + a.heuristic > b.distance + b.heuristic) return 1;
        return 0;
    }

}
