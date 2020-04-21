package gpsp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.IOException;
import java.util.HashMap;

public class CSV_Reader {

    HashMap< Point , ArrayList<Point> > neighbors = new HashMap< Point , ArrayList<Point> >();
    PrintWriter db_writer = null;
    public CSV_Reader(PrintWriter a){
        db_writer = a;
    }

    public HashMap< Point , ArrayList<Point> > GetNeighbors (){
        return neighbors;
    }

    public ArrayList<Point> readPoints (String filename, HashMap< Integer , Line > lines) throws IOException{
        ArrayList<Point> points = new ArrayList<Point>();
        String splitBy = ",";
        BufferedReader br = new BufferedReader(new FileReader(filename));

        String line = br.readLine();
        int i = 0;
        line = br.readLine();
        String[] attributes = line.split(splitBy);
        Point p = new Point(attributes,db_writer);
        Point prev = p;
        points.add(p);
        while((line = br.readLine()) != null){
            attributes = line.split(splitBy);
            p = new Point(attributes,db_writer);
            points.add(p);
            i++;
            if (points.get(i-1).id == points.get(i).id) {
                if (neighbors.containsKey(p)) {
                    ArrayList<Point> temp = new ArrayList<Point>();
                    temp = neighbors.get(p);
                    temp.add(points.get(i - 1));
                    neighbors.put(p, temp);

                    temp = new ArrayList<Point>();
                    if (neighbors.containsKey(points.get(i - 1))) {
                        temp = neighbors.get(points.get(i - 1));
                    }
                    temp.add(p);
                    neighbors.put(points.get(i - 1), temp);
                }
                else {
                    ArrayList<Point> temp = new ArrayList<Point>();
                    temp.add(points.get(i - 1));
                    neighbors.put(p, temp);


                    if (neighbors.containsKey(points.get(i - 1))) {
                        temp = new ArrayList<Point>();
                        temp = neighbors.get(points.get(i - 1));
                        temp.add(p);
                        neighbors.put(points.get(i - 1), temp);
                    }
                    else {
                        temp = new ArrayList<Point>();
                        temp.add(p);
                        neighbors.put(points.get(i - 1), temp);
                    }
                }
            }
            if (p.getId() == prev.getId()){
                if(lines.get(p.getId()).oneway.equals("yes")){
                    db_writer.println ("next("+ prev.getNodeId() + "," + p.getNodeId() +").");
                }
                else if(lines.get(p.getId()).oneway.equals("no")){
                    db_writer.println ("next("+ prev.getNodeId() + "," + p.getNodeId() +").");
                    db_writer.println ("next("+ p.getNodeId() + "," + prev.getNodeId() +").");
                }
                else{
                    db_writer.println ("next("+ p.getNodeId() + "," + prev.getNodeId() +").");
                }
            }
            prev = p;
        }


        for (Point q : points) {
            db_writer.println ("belongsTo("+ q.getNodeId() + "," + q.getId() + ").");
        }
        br.close();
        return points;
    }

    public ArrayList<Taxi> readTaxis (String filename) throws IOException{
        ArrayList<Taxi> taxis = new ArrayList<Taxi>();
        String splitBy = ",";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine();
        while((line = br.readLine()) != null){
            String[] attributes = line.split(splitBy);
            Taxi t = new Taxi(attributes,db_writer);
            taxis.add(t);
        }
        br.close();
        return taxis;
    }

    public ArrayList<Traffic> readTraffic (String filename) throws IOException{
        ArrayList<Traffic> traffic = new ArrayList<Traffic>();
        String splitBy = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine();
        while((line = br.readLine()) != null){
            String[] attributes = line.split(splitBy,-1);
            Traffic t = new Traffic(attributes,db_writer);
            traffic.add(t);
        }
        br.close();
        return traffic;
    }

    public HashMap< Integer , Line > readLines (String filename) throws IOException{
        HashMap< Integer , Line > lines = new HashMap< Integer , Line >();
        String splitBy = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine();
        while((line = br.readLine()) != null){
            String[] attributes = line.split(splitBy, -1);
            Line l = new Line(attributes,db_writer);
            lines.put(l.getId(),l);
        }
        br.close();
        return lines;
    }

    public Client readClient (String filename) throws IOException{
        String splitBy = ",";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine();
        line = br.readLine();
        String[] attributes = line.split(splitBy);
        Client c = new Client(attributes,db_writer);
        br.close();
        return c;
    }
}