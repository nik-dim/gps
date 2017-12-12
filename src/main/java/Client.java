import java.util.ArrayList;


public class Client {
    double  x;
    double  y;

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }

    Client(String[] attributes){
        x = Double.parseDouble(attributes[0]);
        y = Double.parseDouble(attributes[1]);
    }

    double distance(Point p){
        return Math.pow(this.x - p.x , 2) + Math.pow(this.y - p.y , 2);
    }

    Point resolveClient(ArrayList<Point> points){
        double minimum = Double.MAX_VALUE;
        Point foundPoint = null;
        for (Point i : points) {
            if (minimum > this.distance(i)){
                minimum = this.distance(i);
                foundPoint = i;
            }
        }
        return foundPoint;
    }
}