import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.lang.*;
import java.io.FileWriter;
import java.util.HashSet;
import java.text.DecimalFormat;
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
                if (sc.hasNextLine()) {
                    nxt = sc.nextLine();
                }

                if (Objects.equals(nxt, "JA-a") || Objects.equals(nxt, "AVGAS")) {
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
    public static void makeMassiveAportDB(String finalName) {
        File apdb = new File(finalName);
        Random randy = new Random();
        DecimalFormat df = new DecimalFormat("#.##");
        String[] locations = {"City1,Country1", "City2,Country2", "City3,Country3", "City4,Country4", "City5,Country5", "City6,Country6", "City7,Country7", "City8,Country8", "City9,Country9", "City10,Country10", "City11,Country11", "City12,Country12"};
        HashSet<String> usedICAOs = new HashSet<>();
        try (FileWriter writer = new FileWriter(apdb)) {
            for (int i = 1; i <= 1000; i++) {
                String ICAO;
                do {
                    ICAO = randy.ints(65, 91)
                            .limit(4)
                            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                            .toString();
                } while (usedICAOs.contains(ICAO));
                usedICAOs.add(ICAO);

                String Loc = locations[randy.nextInt(locations.length)];

                double Long = randy.nextDouble() * 180 * (randy.nextBoolean() ? -1 : 1);
                double Lat = randy.nextDouble() * 90 * (randy.nextBoolean() ? -1 : 1);

                double freq = randy.nextDouble() * 150;

                String[][] fuelTypes = {{"JA-A", "AVGAS"}, {"JA-A"}, {"AVGAS"}};
                String[] fts = fuelTypes[randy.nextInt(fuelTypes.length)];
                Long = Double.parseDouble(df.format(Long));
                Lat = Double.parseDouble(df.format(Lat));
                freq = Double.parseDouble(df.format(freq));
                writer.write(ICAO + "\n");
                writer.write(Loc + "\n");
                writer.write(Long + "\n");
                writer.write(Lat + "\n");
                writer.write(freq + "\n");
                for (String ft : fts) {
                    writer.write(ft + "\n");
                }
            }
        } catch (IOException e) {
            System.err.println("The helpless child laborer could not get the file written: " + e.getMessage());
        }
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
        //db dbinst = new db("src/dbDir/airports.txt", "src/dbDir/airplanes.txt");
        //makeMassiveAportDB("src/dbDir/airports1.txt");

            //WORKING CODE TO INTERACT WITH DB: (prints for debug)
            db dbinst = new db("src/dbDir/airports1.txt", "src/dbDir/airplanes.txt");
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

    }
}
