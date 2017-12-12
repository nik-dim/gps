import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.IOException;
import java.util.HashMap;

public class CSV_Reader {

    HashMap< Point , ArrayList<Point> > neighbors = new HashMap< Point , ArrayList<Point> >();

    public HashMap< Point , ArrayList<Point> > GetNeighbors (){
        return neighbors;
    }

    public ArrayList<Point> readPoints (String filename) throws IOException{
        ArrayList<Point> points = new ArrayList<Point>();
//        ArrayList<Point> temp = new ArrayList<Point>();
        String splitBy = ",";
        BufferedReader br = new BufferedReader(new FileReader(filename));

        String line = br.readLine();
        int i = 0;
//        line = br.readLine();
        String[] attributes = line.split(splitBy);
        Point p = new Point(attributes);
        points.add(p);
        while((line = br.readLine()) != null){
            attributes = line.split(splitBy);
            p = new Point(attributes);
            points.add(p);
            i++;
            if (points.get(i-1).id == points.get(i).id) {
                if (neighbors.containsKey(p)) {
                    ArrayList<Point> temp = new ArrayList<Point>();
                    temp = neighbors.get(p);
                    temp.add(points.get(i - 1));
                    neighbors.put(p, temp);

                    temp = new ArrayList<Point>();
                    if (neighbors.containsKey(points.get(i - 1))) {
                        temp = neighbors.get(points.get(i - 1));
                    }
                    temp.add(p);
                    neighbors.put(points.get(i - 1), temp);
                }
                else {
                    ArrayList<Point> temp = new ArrayList<Point>();
                    temp.add(points.get(i - 1));
                    neighbors.put(p, temp);


                    if (neighbors.containsKey(points.get(i - 1))) {
                        temp = new ArrayList<Point>();
                        temp = neighbors.get(points.get(i - 1));
                        temp.add(p);
                        neighbors.put(points.get(i - 1), temp);
                    }
                    else {
                        temp = new ArrayList<Point>();
                        temp.add(p);
                        neighbors.put(points.get(i - 1), temp);
                    }
                }
            }
        }
        br.close();
        return points;
    }

    public ArrayList<Taxi> readTaxis (String filename) throws IOException{
        ArrayList<Taxi> taxis = new ArrayList<Taxi>();
        String splitBy = ",";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine();
        while((line = br.readLine()) != null){
            String[] attributes = line.split(splitBy);
            Taxi t = new Taxi(attributes);
            taxis.add(t);
        }
        br.close();
        return taxis;
    }

    public Client readClient (String filename) throws IOException{
        String splitBy = ",";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine();
        line = br.readLine();
        String[] attributes = line.split(splitBy);
        Client c = new Client(attributes);
        br.close();
        return c;
    }
}