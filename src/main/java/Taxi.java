import java.util.ArrayList;

public class Taxi {
    double  x;
    double  y;
    int     id;

    Taxi(String[] attributes){
        x = Double.parseDouble(attributes[0]);
        y = Double.parseDouble(attributes[1]);
        id = Integer.parseInt(attributes[2]);
    }

    double distance(Point p){
        return Math.pow(this.x - p.x , 2) + Math.pow(this.y - p.y , 2);
    }

    Point resolveTaxi(ArrayList<Point> points){
        double minimum = Double.MAX_VALUE;
        Point foundPoint = null;
        for (Point i : points) {
            if (minimum > this.distance(i)){
                minimum = this.distance(i);
                foundPoint = i;
            }
        }
        foundPoint.setId(this.id);
        return foundPoint;
    }
}