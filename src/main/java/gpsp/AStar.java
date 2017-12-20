package gpsp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;




public class AStar {

        public int MAX_SIZE;

        AStar(int maxsize){
            this.MAX_SIZE = maxsize;
        }

        public double Heuristic(Point a, Point c)
        {
                return Math.sqrt( (Math.abs(a.x - c.x) + Math.abs(a.y - c.y)) );
        }

        public double Distance(Point a, Point b)
        {
                return Math.sqrt( (Math.abs(a.x - b.x) + Math.abs(a.y - b.y)) );
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



        public ArrayList<Point> AStarSearch(HashMap<Point, ArrayList<Point>> neighbors, Point start, Point goal)
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
