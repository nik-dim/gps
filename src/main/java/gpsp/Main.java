package gpsp;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException, JAXBException {
        String option = "Haversine";
        if (args.length == 0 || args.length > 2){
            System.out.println("Give exactly 2 arguments:");
            System.out.println("\t1st\t : size of beam");
            System.out.println("\t2nd(optional) : h for Haversine(default) or m for Manhattan");
            System.exit(0);
        }
        else if( args.length == 2){
            if (args[1].equals("h")){
                option = "Haversine";
            }
            else if (args[1].equals("m")){
                option = "Manhattan";
            }
            else{
                System.out.println("Give exactly 2 arguments:");
                System.out.println("\t1st\t : size of beam");
                System.out.println("\t2nd(optional) : h for Haversine(default) or m for Manhattan");
                System.exit(0);
            }
        }

        // writer

        PrintWriter writer = null;
        if (option.equals("Haversine")) {
            File file = new File( option + args[0] + "_details.csv");
            if (file.createNewFile()){
                System.out.println(".csv File is created!");
            }else{
                System.out.println(".csv File already exists.");
            }
            writer = new PrintWriter(file);
            writer.println("Taxi ID, Max openSet Size, A* Steps, Distance");
        }



        // writer

        String nameOfOutputFile = option + args[0] ;
        System.out.println("The GPS uses " + option.toUpperCase() + " distance");
        System.out.println("Size of beam = " + args[0]);
        long startTime = System.currentTimeMillis();
        long startTime_0 = System.currentTimeMillis();
        CSV_Reader MyReader = new CSV_Reader();
        ArrayList<Point> points = MyReader.readPoints("nodes.csv");
        ArrayList<Taxi> taxis = MyReader.readTaxis("taxis.csv");
        Client client = MyReader.readClient("client.csv");
        HashMap<Point, ArrayList<Point>> neighbors = MyReader.GetNeighbors();
        long endTime = System.currentTimeMillis();

        System.out.println("Preprocessing\t:\t" + Long.toString(endTime-startTime)+ " ms" );
        startTime = endTime;
        Point p;
        ArrayList<Point> resolved_taxis = new ArrayList<Point>();
        for (Taxi t: taxis ) {
            p = t.resolveTaxi(points);
            resolved_taxis.add(p);
        }
        Point resolved_client;
        resolved_client = client.resolveClient(points);
        ArrayList<ArrayList<Point>> paths = new ArrayList<ArrayList<Point>>();

        AStar MyAstar = new AStar(Integer.parseInt(args[0]));
        AStarHaversine MyAstarHaversine = new AStarHaversine(Integer.parseInt(args[0]));
        int idBestPath = -1;
        double min = Double.MAX_VALUE;
        ArrayList<Point> path = new ArrayList<Point>();
        for (Point point: resolved_taxis){
            if (option.equals("Haversine")){
                path  = MyAstarHaversine.AStarSearchHaversine(neighbors,point,resolved_client);
            }
            else{
                path  = MyAstar.AStarSearch(neighbors,point,resolved_client);
            }
            if (option.equals("Haversine")) {
                if (path == null) {
                    writer.print(point.getId() + ",");
                    writer.print(MyAstarHaversine.getMaxSetSize() + ",");
                    writer.print(MyAstarHaversine.getStep() + ",");
                    writer.println("-");
                } else {
                    if (min > path.get(0).getDistance()) {
                        idBestPath = point.getId();
                        min = path.get(0).getDistance();
                    }
                    writer.print(point.getId() + ",");
                    writer.print(MyAstarHaversine.getMaxSetSize() + ",");
                    writer.print(MyAstarHaversine.getStep() + ",");
                    writer.println(path.get(0).getDistance());
                }
            }
            paths.add(path);
//            path.clear();
        }
        if (option.equals("Haversine")) {
            writer.close();
        }
        endTime = System.currentTimeMillis();
        System.out.println("A* algorithm\t:\t" + Long.toString(endTime-startTime)+ " ms" );
        startTime = endTime;

        KML_writer.createKML(paths, resolved_taxis, idBestPath, client, nameOfOutputFile);
        endTime = System.currentTimeMillis();
        System.out.println("KML file\t:\t" + Long.toString(endTime-startTime)+ " ms" );
        System.out.println("Total Time\t:\t" + Long.toString(endTime-startTime_0)+ " ms\n" );
    }
}