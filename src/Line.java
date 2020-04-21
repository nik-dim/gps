package gpsp;

import java.io.PrintWriter;

public class Line {
    int     line_id;
    String oneway;
    Line(String[] attributes, PrintWriter db_writer){
        String highway,name,lit,lanes,maxspeed,railway,boundary,access,natural,barrier,tunnel,bridge,incline,waterway,busway,toll;
        line_id = Integer.parseInt(attributes[0]);
        highway = attributes[1].equals("") ? "primary" : attributes[1];
        name = attributes[2];
        oneway = attributes[3].equals("") ? "no" : attributes[3];
        lit = attributes[4].equals("") ? "yes" : attributes[4];
        lanes = attributes[5].equals("") ? "1" : attributes[5];
        maxspeed = attributes[6].equals("") ? "50" : attributes[6];
        railway = attributes[7].equals("") ? "no" : attributes[7];
        boundary = attributes[8].equals("") ? "no" : attributes[8];
        access = attributes[9].equals("") ? "yes" : attributes[9];
        natural = attributes[10].equals("") ? "yes" : attributes[10];
        barrier = attributes[11].equals("") ? "no" : attributes[11];
        tunnel = attributes[12].equals("") ? "no" : attributes[12];
        bridge = attributes[13].equals("") ? "no" : attributes[13];
        incline = attributes[14].equals("") ? "no" : attributes[14];
        waterway = attributes[15].equals("") ? "no" : attributes[15];
        busway = attributes[16].equals("") ? "no" : attributes[16];
        toll = attributes[17].equals("") ? "no" : attributes[17];

        db_writer.println ("line("+ line_id +","+ highway +","+ oneway +","+ lit +","+ lanes +","+ maxspeed +","+ railway
                +","+ boundary +","+ access +","+ natural +","+ barrier +","+ tunnel +","+ bridge +","+ waterway
                +","+ busway +","+ toll + ").");
    }
    Line(){}


    public int getId() {
        return line_id;
    }
//    public String getDirection() {
//        return oneway;
//    }

    // this may need to change to long
    @Override
    public int hashCode() {
        int v = Double.valueOf(this.line_id).hashCode();
        return v;
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof Line)){
            return  false;
        }
        Line other = (Line)o;
        if (this.line_id != other.line_id) return false;
        return true;
    }

}