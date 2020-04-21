package gpsp;

import java.io.PrintWriter;
import java.util.ArrayList;


public class Client {
    double  x;
    double  y;
    double  x_dest;
    double  y_dest;



    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }

    public double getDestX(){
        return x_dest;
    }
    public double getDestY(){
        return y_dest;
    }


//    public int getHour(){
//        return hour;
//    }
//    public int getMin(){
//        return minute;
//    }

    Client(String[] attributes,PrintWriter db_writer){
        int     hour;
        int     minute;
        int     persons;
        String     language;
        int     luggage;

        x = Double.parseDouble(attributes[0]);
        y = Double.parseDouble(attributes[1]);
        x_dest = Double.parseDouble(attributes[2]);
        y_dest = Double.parseDouble(attributes[3]);
        hour = Integer.parseInt(attributes[4].substring(0,2));
        minute = Integer.parseInt(attributes[4].substring(3,5));
        persons = Integer.parseInt(attributes[5]);
        if (attributes[6].equals("greek")){
            language = "gr";
        }
        else if (attributes[6].equals("english")){
            language = "en";
        }
        else{
            language = "both";
        }
        int temp =  hour * 100 + minute;
        luggage = Integer.parseInt(attributes[7]);
        db_writer.println ("client("+ x + "," + y + "," + x_dest + "," + y_dest + "," + temp + "," + persons
                + "," + language + "," + luggage + ").");
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