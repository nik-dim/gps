package gpsp;

import java.io.PrintWriter;
import java.util.ArrayList;

public class Traffic {



    Traffic(String[] attributes, PrintWriter db_writer){
        int  line_id;
        String[] slots = new String[3];
        line_id = Integer.parseInt(attributes[0]);
        slots[0] = "low"; slots[1] = "low"; slots[2] = "low";
        if (attributes.length>2){
            String[] temp = attributes[2].split("\\|", -1);
            for (int i = 0; i < temp.length; i++){
                slots[i] = temp[i].equals("") ? "low" : temp[i].substring(12);
            }
        }

        db_writer.println ("traffic("+ line_id + "," + slots[0] + "," + slots[1] + "," + slots[2] + ").");

    }

}