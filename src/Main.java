package gpsp;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;

import com.ugos.jiprolog.engine.JIPEngine;
import com.ugos.jiprolog.engine.JIPQuery;
import com.ugos.jiprolog.engine.JIPSyntaxErrorException;
import com.ugos.jiprolog.engine.JIPTerm;
import com.ugos.jiprolog.engine.JIPTermParser;

public class Main {
    public static void main(String[] args) throws IOException, JAXBException {

        int beamsize = 128;
        int k = 2;
        PrintWriter writer = null;
        PrintWriter db_writer = null;
        long startTime = System.currentTimeMillis();

        File db = new File("db.pl");
        File out = new File("details_"+Integer.toString(beamsize)+".txt");
        if (db.createNewFile()){
            System.out.println("db.pl File is created!");
        }else{
            System.out.println("db.pl File already exists. It will be updated.");
        }
        db_writer = new PrintWriter(db);
        out.createNewFile();
        writer = new PrintWriter(out);
        long endTime = System.currentTimeMillis();
        writer.println("File creation:\t\t\t" + (endTime - startTime) + "ms");
        startTime = endTime;
        CSV_Reader MyReader = new CSV_Reader(db_writer);
        HashMap< Integer , Line > lines = MyReader.readLines("lines.csv");
        ArrayList<Point> points = MyReader.readPoints("nodes.csv", lines);
        ArrayList<Taxi> taxis = MyReader.readTaxis("taxis.csv");
        db_writer.println();
        Client client = MyReader.readClient("client.csv");
        ArrayList<Traffic> traffic = MyReader.readTraffic("traffic.csv");
        db_writer.close();


        ArrayList<Taxi> eligibleTaxis = new ArrayList<Taxi>();
        JIPEngine jip = new JIPEngine();
        jip.consultFile("db.pl");
        jip.consultFile("rules1.pl");
        JIPTermParser parser = jip.getTermParser();
        endTime = System.currentTimeMillis();
        writer.println("Prolog Consult:\t\t\t" + (endTime - startTime) + "ms");
        startTime = endTime;

        JIPQuery jipQuery;
        JIPTerm term;
        Point resolved_client;
        resolved_client = client.resolveClient(points);
        ArrayList<ArrayList<Point>> paths = new ArrayList<ArrayList<Point>>();

        AStarHaversine MyAstarHaversine = new AStarHaversine(beamsize, jip);
        Point start  = new Point(client.getX(),client.getY());
        Point dest = new Point(client.getDestX(),client.getDestY());
        double distance = MyAstarHaversine.Distance(start,dest);
//        System.out.println(distance);
        String flag = "false";
        if( distance >= 5){
            flag = "true";
        }
//        System.out.println(flag);
        jipQuery = jip.openSynchronousQuery(parser.parseTerm("findTaxi(X," + flag + ")."));
        term = jipQuery.nextSolution();
        while (term != null) {
            //System.out.println(term.getVariablesTable().get("X").toString());
            int tempID = Integer.parseInt(term.getVariablesTable().get("X").toString());
            eligibleTaxis.add(taxis.get( (tempID-100)/10 ));
            term = jipQuery.nextSolution();
        }

        eligibleTaxis.sort(new TaxiComparator());
        System.out.println("First " + k + " taxis sorted on their rating");
        int i=0;
        for (Taxi t : eligibleTaxis) {
            if (i<k){
                System.out.println("Taxi with ID = " + t.id + " and Rating = " + t.rating );
            }
            i++;
        }
        HashMap<Point, ArrayList<Point>> neighbors = MyReader.GetNeighbors();
        Point p;
        ArrayList<Point> resolved_taxis = new ArrayList<Point>();
        for (Taxi t: eligibleTaxis ) {
            p = t.resolveTaxi(points);
            resolved_taxis.add(p);
        }
        int idBestPath = -1;
        double min = Double.MAX_VALUE;
        ArrayList<Point> path = new ArrayList<Point>();
        for (Point point: resolved_taxis){
            path  = MyAstarHaversine.AStarSearchHaversine(neighbors,point,resolved_client);
            endTime = System.currentTimeMillis();
            writer.println("A* for Taxi #:" + point.getId() + "\t\t" + (endTime - startTime) + "ms");
            startTime = endTime;
            writer.println("\tTaxiId : \t" + point.getId() + ",");
            writer.println("\tMaxBeamSize : \t" + MyAstarHaversine.getMaxSetSize() + ",");
            writer.println("\tStep Count : \t" + MyAstarHaversine.getStep() + ",");
            paths.add(path);

        }

        writer.close();

        KML_writer.createKML(paths, resolved_taxis, resolved_taxis.size(), idBestPath, client, "output_"+ Integer.toString(beamsize) );
    }
}