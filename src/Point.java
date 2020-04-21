package gpsp;

import java.io.PrintWriter;
import java.util.Locale;

public class Point {
        double  x;
        double  y;
        int     id;
        long     node_id;
        double heuristic;
        double distance;
        Point(String[] attributes, PrintWriter db_writer){
                x = Double.parseDouble(attributes[0]);
                y = Double.parseDouble(attributes[1]);
                id = Integer.parseInt(attributes[2]);
                node_id = Long.parseLong(attributes[3]);
//                db_writer.println ("belongsTo("+ node_id + "," + id + ").");
        }
        Point(){}
        Point(double a,double b){
                x = a;
                y = b;
        }


        public double getX() {
                return x;
        }
        public double getY() {
                return y;
        }
        public int getId() {
                return id;
        }
        public long getNodeId() {
            return node_id;
        }
        public double getDistance(){
                return distance;
        }
        public void setX(double i){
                this.x=i;
        }
        public void setY(double i){
                this.y=i;
        }
        public void setId(int i){
                this.id=i;
        }
        public void setHeuristic(double h){
                this.heuristic=h;
        }
        public void setDistance(double d){
                this.distance = d;
        }

        // this may need to change to long
        @Override
        public int hashCode() {
                int v = Double.valueOf(this.x).hashCode() + Double.valueOf(/** Math.pow(10,9)*/ + this.y).hashCode();
                return v;
        }

        @Override
        public boolean equals(Object o)
        {
                if (!(o instanceof Point)){
                        return  false;
                }
                Point other = (Point)o;
                if (this.x != other.x) return false;
                if (this.y != other.y) return false;
                return true;
        }

}