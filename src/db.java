import java.io.FileNotFoundException;
import java.io.File;
import java.util.Objects;
import java.util.Scanner;
import java.util.ArrayList;
import java.lang.*;

public class db {
    public static File airportDB;
    public static File airplaneDB;
    public static ArrayList<airport> aprts;
    public static ArrayList<Airplane> aplanes;

    //init sets runtime arraylists
    public db(String airportDBs, String airplaneDBs) {
        airportDB = new File(airportDBs);
        airplaneDB = new File(airplaneDBs);
        aprts = readAirports();
        aplanes = readAirplanes();
    }

    //reads the airports from the file, stores in arraylist for runtime use
    public static ArrayList<airport> readAirports() {
        ArrayList<airport> aprts = new ArrayList<>();
        try {
            boolean hasboth = true;
            String nxt = "";
            Scanner sc = new Scanner(airportDB);
            while (sc.hasNextLine()) {
                String ICAO;
                if (!hasboth) {
                    if (nxt == "") {
                        System.out.println("bug line 29");
                    }
                    ICAO = nxt;
                } else {
                    ICAO = sc.nextLine();
                }

                String Loc = sc.nextLine();

                double Long = Double.parseDouble(sc.nextLine());

                double Lat = Double.parseDouble(sc.nextLine());

                double freq = Double.parseDouble(sc.nextLine());

                String[] fts = new String[2];
                fts[0] = sc.nextLine();
                nxt = sc.nextLine();
                if (Objects.equals(nxt, "JA-a")) {
                    fts[1] = nxt;
                    hasboth = true;
                } else {
                    hasboth = false;
                }
                airport a = new airport(ICAO, Loc, fts, Long, Lat, freq);
                aprts.add(a);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return aprts;
    }

    //reads airplanes from the file, stores as arraylist for runtime use
    public static ArrayList<Airplane> readAirplanes() {
        ArrayList<Airplane> aplanes = new ArrayList<>();
        try {
            Scanner sc = new Scanner(airplaneDB);
            while (sc.hasNextLine()) {
                String[] info = sc.nextLine().split(",");
                String model = info[0].trim();
                String make = info[1].trim();
                String fuel = info[2].trim();
                int fuelCapacity = Integer.parseInt(info[3].trim());
                int fuelConsumption = Integer.parseInt(info[4].trim());
                int speed = Integer.parseInt(info[5].trim());
                Airplane a = new Airplane(make, model, fuel, fuelCapacity, fuelConsumption, speed);
                a.setRange();
                aplanes.add(a);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return aplanes;
    }

    public void addAirport(airport a) {
        //do data validation when creating airport?
        //update arraylist
        //update file
    }

    public void addAirplane(Airplane a) {
        //do data validation when creating airplane?
        //update arraylist
        //update file
    }

    public void removeAirport(airport a) {
        //update arraylist
        //update file
    }

    public static void main(String[] args) {
        /*
            //WORKING CODE TO INTERACT WITH DB: (prints for debug)
            db dbinst = new db("src/dbDir/airports.txt", "src/dbDir/airplanes.txt");
            ArrayList<airport> aprts = readAirports();
            for (airport a : aprts) {
                System.out.println(a.CAOid);
                System.out.println(a.APTname);
                System.out.println(a.APRTlongitude);
                System.out.println(a.APRTlatitude);
                for (String s : a.APRTfuelTypes) {
                    System.out.println(s);
                }
            }
            ArrayList<Airplane> aplanes = readAirplanes();
            aplanes = readAirplanes();
            for (Airplane a : aplanes) {
                System.out.println("Make: " + a.make + " Model: " + a.model + " Fuel: " + a.fuel + " Fuel Capacity: " + a.fuelCapacity + " Fuel Consumption: " + a.fuelConsumption + " Speed: " + a.speed + " Range: " + a.range + " units");
            }
         */
    }
}
