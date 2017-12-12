import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class Main {

    public static void main(String[] args) throws IOException, JAXBException {
        long startTime = System.currentTimeMillis();
        long startTime_0 = System.currentTimeMillis();
        CSV_Reader MyReader = new CSV_Reader();
        ArrayList<Point> points = MyReader.readPoints("C:\\Users\\nikdim\\IdeaProjects\\gps\\nodes.csv");
        ArrayList<Taxi> taxis = MyReader.readTaxis("C:\\Users\\nikdim\\IdeaProjects\\gps\\taxis.csv");
        Client client = MyReader.readClient("C:\\Users\\nikdim\\IdeaProjects\\gps\\client.csv");
        HashMap<Point, ArrayList<Point>> neighbors = MyReader.GetNeighbors();
        long endTime = System.currentTimeMillis();

        System.out.println("Preprocessing\t:\t" + Long.toString(endTime-startTime)+ " ms" );
        startTime = endTime;
        Point p;
        ArrayList<Point> resolved_taxis = new ArrayList<Point>();
        for (Taxi t: taxis ) {
            p = t.resolveTaxi(points);
            resolved_taxis.add(p);
        }
        Point resolved_client;
        resolved_client = client.resolveClient(points);
        ArrayList<ArrayList<Point>> paths = new ArrayList<ArrayList<Point>>();
        AStar MyAstar = new AStar(10000);
        int idBestPath = -1;
        double min = Double.MAX_VALUE;
        for (Point point: resolved_taxis){
            ArrayList<Point> path  = MyAstar.AStarSearch(neighbors,point,resolved_client);
            if( min > path.get(0).getDistance()){
                idBestPath = point.getId();
                min = path.get(0).getDistance();
            }
            paths.add(path);
        }
        endTime = System.currentTimeMillis();
        System.out.println("A* algorithm\t:\t" + Long.toString(endTime-startTime)+ " ms" );
        startTime = endTime;

        KML_writer.createKML(paths, resolved_taxis, idBestPath, client);
        endTime = System.currentTimeMillis();
        System.out.println("KML file\t\t:\t" + Long.toString(endTime-startTime)+ " ms" );
        System.out.println("Total Time\t\t:\t" + Long.toString(endTime-startTime_0)+ " ms" );
    }
}