import java.io.BufferedReader;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.nio.file.Path;
import java.io.IOException;

public class CSV_Reader {

    public static ArrayList<Point> readPoints (String filename){
        ArrayList<Point> points = new ArrayList<>();
        Path pathToFile = Paths.get(filename);
        try (BufferedReader br = Files.newBufferedReader(pathToFile)){
            String line = br.readLine();
//            line = br.readLine();
            while(line != null){
                String[] attributes = line.split(",");
                Point p = new Point(attributes);
                points.add(p);
                line = br.readLine();
            }
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return points;
    }

}
