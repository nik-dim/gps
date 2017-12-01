import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {

        ArrayList<Point> points = CSV_Reader.readPoints("nodes.csv");
        ArrayList<Taxi> taxis = CSV_Reader.readTaxis("taxis.csv");
        Client client = CSV_Reader.readClient("client.csv");
        System.out.println("Hello World!");
        Point p;
        for (Taxi t: taxis ) {
            p = t.resolveTaxi(points);
            System.out.println(t.id +": " + p.getX()+" "+ p.getY() + " " + p.getId() );
        }
    }
}