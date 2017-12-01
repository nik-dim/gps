public class Point {
        double  x;
        double  y;
        int     id;

        Point(String[] attributes){
                x = Double.parseDouble(attributes[0]);
                y = Double.parseDouble(attributes[1]);
                id = Integer.parseInt(attributes[2]);
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

        // this may need to change to long
        @Override
        public int hashCode() {
                return Double.valueOf(this.x /** Math.pow(10,9)*/ + this.y).hashCode();
        }

}