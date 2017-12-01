
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
}