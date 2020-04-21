package gpsp;

import java.io.PrintWriter;
import java.util.ArrayList;

public class Taxi {
    double  x;
    double  y;
    int     id;
    double  rating;


    Taxi(String[] attributes, PrintWriter db_writer){
        boolean available;
        int     capacity;
        String  language; //0=greek, 1=english, 2=both

        boolean long_distance;
//    int     type;
        x = Double.parseDouble(attributes[0]);
        y = Double.parseDouble(attributes[1]);
        id = Integer.parseInt(attributes[2]);
        if (attributes[3].equals("yes")){
            available = true;
        }
        else {
            available = false;
        }

        capacity = 4;//Integer.parseInt(attributes[4].substring(2,2));

        if (attributes[5].equals("greek")){
            language = "gr";
        }
        else if (attributes[5].equals("english")){
            language = "en";
        }
        else{
            language = "both";
        }
        rating = Double.parseDouble(attributes[6]);
        if (attributes[7].equals("yes")){
            long_distance = true;
        }
        else {
            long_distance = false;
        }
        db_writer.println ("taxi("+ x + "," + y + "," + id + "," + available + "," + capacity + "," + language + "," + rating
                + "," + long_distance + ").");

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