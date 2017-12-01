import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Comparator;

public class AStar {

        public HashMap<Point, Point> cameFrom = new  HashMap<Point, Point>();
        public HashMap<Point, Point> costSoFar = new  HashMap<Point, Point>();

        // Note: a generic version of A* would abstract over Location and
        // also Heuristic
        static public double Heuristic(Point a, Client c)
        {
            return Math.abs(a.x - c.x) + Math.abs(a.y - c.y);
        }

        public AStarSearch(HashMap<Point, ArrayList<Point>> neighbors, Point start, Client goal)
        {
            Comparator<Point> comparator = new ();
            PriorityQueue<Point> frontier = new PriorityQueue<Point>();
            frontier.

            cameFrom[start] = start;
            costSoFar[start] = 0;

            while (frontier.Count > 0)
            {
                var current = frontier.Dequeue();

                if (current.Equals(goal))
                {
                    break;
                }

                foreach (var next in graph.Neighbors(current))
                {
                    double newCost = costSoFar[current]
                            + graph.Cost(current, next);
                    if (!costSoFar.ContainsKey(next)
                            || newCost < costSoFar[next])
                    {
                        costSoFar[next] = newCost;
                        double priority = newCost + Heuristic(next, goal);
                        frontier.Enqueue(next, priority);
                        cameFrom[next] = current;
                    }
                }
            }
        }




}
