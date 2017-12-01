import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.nio.file.Path;
import java.io.IOException;

public class CSV_Reader {

    public static ArrayList<Point> readPoints (String filename) throws IOException{
        ArrayList<Point> points = new ArrayList<>();
        String splitBy = ",";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine();
        while((line = br.readLine()) !=null){
            String[] attributes = line.split(splitBy);
            Point p = new Point(attributes);
            points.add(p);
        }
        br.close();
        return points;
    }

    public static ArrayList<Taxi> readTaxis (String filename) throws IOException{
        ArrayList<Taxi> taxis = new ArrayList<>();
        String splitBy = ",";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine();
        while((line = br.readLine()) !=null){
            String[] attributes = line.split(splitBy);
            Taxi t = new Taxi(attributes);
            taxis.add(t);
        }
        br.close();
        return taxis;
    }

    public static Client readClient (String filename) throws IOException{
        String splitBy = ",";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine();
        line = br.readLine();
        String[] attributes = line.split(splitBy);
        Client c = new Client(attributes);
        System.out.println(attributes[1]);

        br.close();
        return c;
    }
}