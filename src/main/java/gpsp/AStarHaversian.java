package gpsp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;




public class AStarHaversian {

    public int MAX_SIZE;
    static final int EARTH_RADIUS = 6371;

    AStarHaversian(int maxsize){
        this.MAX_SIZE = maxsize;
    }

    public static double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }

    public double Heuristic(Point start, Point goal)
    {
        double dLat  = Math.toRadians((goal.getY() - start.getY()));
        double dLong = Math.toRadians((goal.getX() - start.getX()));

        double startLat = Math.toRadians(start.getY());
        double endLat   = Math.toRadians(goal.getY());

        double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c; // <-- d
    }

    public double Distance(Point start, Point goal)
    {
        double dLat  = Math.toRadians((goal.getY() - start.getY()));
        double dLong = Math.toRadians((goal.getX() - start.getX()));

        double startLat = Math.toRadians(start.getY());
        double endLat   = Math.toRadians(goal.getY());

        double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c; // <-- d
    }


    public ArrayList<Point> reconstructPath(HashMap<Point, Point> camefrom, Point start, Point end){
        ArrayList<Point> path = new ArrayList<Point>();
        Point temp = end;
        while (temp != start){
            path.add(temp);
            temp = camefrom.get(temp);
        }
        path.add(start);
        return path;
    }



    public ArrayList<Point> AStarSearchHaversian(HashMap<Point, ArrayList<Point>> neighbors, Point start, Point goal)
    {
        HashMap<Point, Point>  cameFrom = new HashMap< Point, Point>();
        TreeSet<Point> MyTreeSet = new TreeSet<Point>(new MyComparator());
        HashMap< Point, Point > visited = new HashMap< Point, Point >();
        start.setDistance(0);
        start.setHeuristic( Heuristic(start,goal) );
        MyTreeSet.add(start);
        ArrayList<Point> temp = new ArrayList<Point>();
        Point head = start;		// head of treeSet, initially equal to start

        while( !(head.getX()==goal.getX() && head.getY()==goal.getY()) ){
            head = MyTreeSet.pollFirst();
//                        System.out.println(head.getX() + " " + head.getY());
            visited.put(head,head); 						// set head visited
            temp = neighbors.get(head);
            if(temp!=null){
                for (Point p: temp ) {
                    if( !visited.containsKey(p) ){			// if not visited
                        p.setHeuristic( Heuristic(p, goal) );
                        p.setDistance(head.getDistance() + Distance(head, p));
                        MyTreeSet.add(p);
                        cameFrom.put(p, head);
                    }
                }
            }
            while (MyTreeSet.size() > MAX_SIZE ){
                MyTreeSet.pollLast();
            }

        }
        return reconstructPath(cameFrom, start, head);
    }

}
