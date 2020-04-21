package gpsp;
import com.ugos.jiprolog.engine.JIPEngine;
import com.ugos.jiprolog.engine.JIPQuery;
import com.ugos.jiprolog.engine.JIPTerm;
import com.ugos.jiprolog.engine.JIPTermParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;




public class AStarHaversine {
    public JIPEngine jip;
    public int MAX_SIZE;
    static final int EARTH_RADIUS = 6371;
    public int step_count;
    public int max_openset_size;

    public int getStep() {
        return this.step_count;
    }
    public int getMaxSetSize() {
        return max_openset_size;
    }



    AStarHaversine(int maxsize, JIPEngine engine){
        this.jip = engine;
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

    public double Distance(Point A, Point B)
    {
        double dLat  = Math.toRadians((B.getY() - A.getY()));
        double dLong = Math.toRadians((B.getX() - A.getX()));

        double startLat = Math.toRadians(A.getY());
        double endLat   = Math.toRadians(B.getY());

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



    public ArrayList<Point> AStarSearchHaversine(HashMap<Point, ArrayList<Point>> neighbors, Point start, Point goal    )
    {

        HashMap<Point, Point>  cameFrom = new HashMap< Point, Point>();
        TreeSet<Point> MyTreeSet = new TreeSet<Point>(new MyComparator());
        HashMap< Point, Point > visited = new HashMap< Point, Point >();
        start.setDistance(0);
        start.setHeuristic( Heuristic(start,goal) );
        MyTreeSet.add(start);
        ArrayList<Point> temp = new ArrayList<Point>();
        Point head = start;		// head of treeSet, initially equal to start

        step_count = 0;
        max_openset_size = 0;
        JIPTermParser parser = jip.getTermParser();


        JIPQuery jipQuery;
        JIPTerm term;

        while( !(head.getX()==goal.getX() && head.getY()==goal.getY()) ){

            if(max_openset_size < MyTreeSet.size()){
                max_openset_size = MyTreeSet.size();
            }
            if (MyTreeSet.isEmpty()){
                System.out.println("The taxi with ID = " + Integer.toString(start.getId()) + " cannot reach Client due to small beam size");
                return null;
            }
            head = MyTreeSet.pollFirst();

            step_count++;
//                        System.out.println(head.getX() + " " + head.getY());
            visited.put(head,head); 						// set head visited
            temp = neighbors.get(head);
            if(temp!=null){
                for (Point p: temp ) {
                    long x = head.getNodeId();
                    long y = p.getNodeId();

                    jipQuery = jip.openSynchronousQuery(parser.parseTerm("goodNeighbor(" + x + "," + y +  ")."));
                    if (jipQuery.nextSolution() != null) {
                        if( !visited.containsKey(p) ){			// if not visited
                            double coefficient = 1;
                            jipQuery = jip.openSynchronousQuery(parser.parseTerm("traffic_coeffient(Answer," + p.getNodeId()+ ")."));
                            term = jipQuery.nextSolution();
                            if (term != null) {
                                coefficient = coefficient * Double.parseDouble(term.getVariablesTable().get("Answer").toString());
                            }
                            jipQuery = jip.openSynchronousQuery(parser.parseTerm("maxspeed_coeffient(Answer," + p.getNodeId() + ")."));
                            term = jipQuery.nextSolution();
                            if (term != null) {
                                coefficient = coefficient * Double.parseDouble(term.getVariablesTable().get("Answer").toString());
                            }

                            p.setHeuristic(coefficient* Heuristic(p, goal) );
                            p.setDistance(head.getDistance() + Distance(head, p));
                            MyTreeSet.add(p);
                            cameFrom.put(p, head);
                        }
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
