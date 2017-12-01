public class Point {
        double  x;
        double  y;
        int     id;


        Point(String[] attributes){
                x = Double.parseDouble(attributes[0]);
                y = Double.parseDouble(attributes[1]);
                id = Integer.parseInt(attributes[2]);
        }
}