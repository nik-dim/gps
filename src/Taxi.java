
public class Taxi {
    double  x;
    double  y;
    int     id;

    Taxi(String[] attributes){
        x = Double.parseDouble(attributes[0]);
        y = Double.parseDouble(attributes[1]);
        id = Integer.parseInt(attributes[2]);
    }
}