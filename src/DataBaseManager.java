//Dylans neck of the woods
import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.io.FileWriter;
import java.util.HashSet;
import java.text.DecimalFormat;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
public class DataBaseManager {
    public static File airportDB;
    public static File airplaneDB;
    public static ArrayList<Airport> aprts;
    public static ArrayList<Airplane> aplanes;

    //init sets runtime arraylists
    public DataBaseManager(String airportDBs, String airplaneDBs) {
        airportDB = new File(airportDBs);
        airplaneDB = new File(airplaneDBs);
        aprts = readAirports();
        aplanes = readAirplanes();
    }
    //reads the airports from the file, stores in arraylist for runtime use
    public static ArrayList<Airport> readAirports() {
        ArrayList<Airport> aprts = new ArrayList<>();
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

                if (Objects.equals(nxt.trim(), "JA-a") || Objects.equals(nxt.trim(), "AVGAS")) {
                    fts[1] = nxt;
                    hasboth = true;
                } else {
                    hasboth = false;
                }
                Airport a = new Airport(ICAO, Loc, fts, Long, Lat, freq);
                aprts.add(a);
            }
            sc.close();
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
                String make = info[0].trim();
                String model = info[1].trim();
                String fuel = info[2].trim();
                int fuelCapacity = Integer.parseInt(info[3].trim());
                int fuelConsumption = Integer.parseInt(info[4].trim());
                int speed = Integer.parseInt(info[5].trim());
                Airplane a = new Airplane(make, model, fuel, fuelCapacity, fuelConsumption, speed);
                a.setRange();
                aplanes.add(a);
            }
            sc.close();
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
                    if (ft != null) {
                        if (ft != fts[fts.length - 1] || fts[fts.length-1] != null) {
                            writer.write(ft + "\n");
                        } else {
                            writer.write(ft);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("The helpless child laborer could not get the file written: " + e.getMessage());
        }
    }
    public static void addAirport(Airport a) { //epic logic to ensure no trailing spaces
        //data validation on the add buttons, not here
        //update arrlist
        aprts = readAirports();
        if (!containsAirport(aprts,a)) { //if we update synchronously, only this check is needed to prevent duplicates within the file as well
            aprts.add(a);
            try {
                FileWriter writer = new FileWriter(airportDB, true);
                writer.write("\n" + a.CAOid.trim() + "\n");
                writer.write(a.APTname.trim() + "\n");
                writer.write(a.APRTlongitude + "\n");
                writer.write(a.APRTlatitude + "\n");
                writer.write(a.freq + "\n");
                for (String ft : a.APRTfuelTypes) {
                    if (ft != null) {
                        if (ft != a.APRTfuelTypes[a.APRTfuelTypes.length - 1]) {
                            writer.write(ft.trim() + "\n");
                        } else {
                            writer.write(ft.trim());
                        }
                    }
                }
                writer.close();
            } catch (IOException e) {
                System.err.println("IOException within addAirport(): " + e.getMessage());
            }
        } else {
            System.out.println("why would i let you do that");
        }
        //update file
    }

    public static void addAirplane(Airplane a) {
        //do data validation when creating airplane?
        //update arraylist
        //update file
        aplanes = readAirplanes();
        if (!containsAirplane(aplanes,a)){
            aplanes.add(a);
            try {
                FileWriter writer = new FileWriter(airplaneDB, true);
                writer.write("\n" + a.make.trim() + "," + a.model.trim() + "," + a.fuel.trim() + "," + a.fuelCapacity + "," + a.fuelConsumption + "," + a.speed);
                writer.close();
            } catch (IOException e) {
                System.err.println("IOException within addAirplane(): " + e.getMessage());
            }
        } else {
            System.out.println("why would i let you do that");
        }
    }
    public static boolean containsAirport(ArrayList<Airport> aprtz, Airport a) {
        for (Airport aprt : aprtz) {
            if (aprt.CAOid.equals(a.CAOid)) {
                //System.out.println("ICAO matched");
                if (aprt.APTname.equals(a.APTname)) {
                    //System.out.println("Name matched");
                    if (aprt.APRTlatitude == a.APRTlatitude) {
                        //System.out.println("Lat matched");
                        if (aprt.APRTlongitude == a.APRTlongitude) {
                            //System.out.println("Long matched");
                            if (aprt.freq == a.freq) {
                                //System.out.println("Freq matched");
                                if (aprt.APRTfuelTypes[0].equals(a.APRTfuelTypes[0])) {
                                    //System.out.println("Fuel matched");
                                    //System.out.print
                                    // ++ln(aprt.APRTfuelTypes.length);
                                    //System.out.println(a.APRTfuelTypes.length);
                                    //aprtz from file reader always has 2 fuel types because of the reader logic.
                                    if (a.APRTfuelTypes.length == 1) {
                                        return true;
                                    } else if (a.APRTfuelTypes.length == 2) {
                                        if (aprt.APRTfuelTypes[1].equals(a.APRTfuelTypes[1])) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    public static boolean containsAirplane(ArrayList<Airplane> aplanes, Airplane a) {
        for (Airplane ap : aplanes) {
            if (ap.make.equals(a.make)) {
                if (ap.model.equals(a.model)) {
                    if (ap.fuel.equals(a.fuel)) {
                        if (ap.fuelCapacity == a.fuelCapacity) {
                            if (ap.fuelConsumption == a.fuelConsumption) {
                                if (ap.speed == a.speed) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    public static void removeAirplane(Airplane a) {
        if (containsAirplane(aplanes,a)){
            aplanes.remove(a);
            File orig = airplaneDB;
            File temp = new File("src/dbDir/temp.txt");
            try {
                BufferedReader reader = new BufferedReader(new FileReader(orig));
                BufferedWriter writer = new BufferedWriter(new FileWriter(temp));
                String lineToRemove = a.make.trim() + "," + a.model.trim() + "," + a.fuel.trim() + "," + a.fuelCapacity + "," + a.fuelConsumption + "," + a.speed;
                String currentLine;
                while((currentLine = reader.readLine()) != null) {
                    reader.mark(1000);
                    if (reader.readLine() == null){
                        writer.write(currentLine);
                        continue;
                    }
                    reader.reset();
                    reader.mark(1000);
                    //this case is to prevent error-prone formatting when the last line in the file is the target to remove
                    if (reader.readLine().equals(lineToRemove) && reader.readLine() == null) {
                        writer.write(currentLine);
                        continue;
                    }
                    reader.reset();

                    if (currentLine.equals(lineToRemove)) continue;
                    writer.write(currentLine + "\n");
                }
                writer.close();
                reader.close();

                if (!orig.delete()) {
                    System.out.println("File delete failed, attempting alternative.");
                    Files.move(temp.toPath(), orig.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
                if (!temp.renameTo(orig)) {
                    System.out.println("File rename failed");
                }
            } catch (IOException e) {
                System.err.println("IOException within removeAirplane(): " + e.getMessage());
            }
        } else {
            System.out.println("You cant remove what is not there (subtract 10 iq points!)");
        }
    }
    public static void removeAirport(Airport a) {
        //update arraylist
        //update file
        if (containsAirport(aprts,a)){
            aprts.remove(a);
            File orig = airportDB;
            File temp = new File("src/dbDir/tempp.txt");
            try {
                BufferedReader reader = new BufferedReader(new FileReader(orig));
                BufferedWriter writer = new BufferedWriter(new FileWriter(temp));
                String currentLine;
                while ((currentLine = reader.readLine()) != null) {
                    reader.mark(1000);
                    if (currentLine.trim().equals(a.CAOid.trim())) {
                        if (reader.readLine().trim().equals(a.APTname.trim())) {
                            if (Double.parseDouble(reader.readLine().trim()) == a.APRTlongitude) {
                                if (Double.parseDouble(reader.readLine().trim()) == a.APRTlatitude) {
                                    if (Double.parseDouble(reader.readLine().trim()) == a.freq) {
                                        if (reader.readLine().trim().equals(a.APRTfuelTypes[0].trim())) {
                                            if (a.APRTfuelTypes.length == 1 ) {
                                                continue;
                                            } else if (reader.readLine().trim().equals(a.APRTfuelTypes[1].trim())) {
                                                    continue;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    reader.reset();
                    reader.mark(1000);
                    if (reader.readLine() == null) {
                        writer.write(currentLine);
                        continue;
                    }
                    reader.reset();
                    reader.mark(1000);
                    if (Objects.equals(reader.readLine().trim(), a.CAOid.trim())) {
                        if (Objects.equals(reader.readLine().trim(), a.APTname.trim())) {
                            if (Double.parseDouble(reader.readLine().trim()) == a.APRTlongitude) {
                                if (Double.parseDouble(reader.readLine().trim()) == a.APRTlatitude) {
                                    if (Double.parseDouble(reader.readLine().trim()) == a.freq) {
                                        if (reader.readLine().trim().equals(a.APRTfuelTypes[0].trim())) {
                                            if (a.APRTfuelTypes.length == 1 ) {
                                                if (reader.readLine() == null) {
                                                    writer.write(currentLine);
                                                    continue;
                                                }
                                            } else if (reader.readLine().trim().equals(a.APRTfuelTypes[1].trim())) {
                                                if (reader.readLine() == null) {
                                                    writer.write(currentLine);
                                                    continue;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    reader.reset();
                    writer.write(currentLine + "\n");
                }
                writer.close();
                reader.close();
                if (!orig.delete()) {
                    System.out.println("File delete failed, attempting alternative.");
                    Files.move(temp.toPath(), orig.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
                if (!temp.renameTo(orig)) {
                    System.out.println("File rename failed");
                }
            } catch (IOException e) {
                System.err.println("IOexception within removeAirport(): " + e.getMessage());
            }
        } else {
            System.out.println("You cant remove what is not there (subtract 10 iq points!)");
        }
    }
    public static void modifyAirplane(Airplane old, Airplane n3w) {
        //update arraylist
        //update file
        if (old == n3w){
            return;
        }
        if (containsAirplane(aplanes,n3w)){
            System.out.println("You cant modify an airplane to be the same as another airplane");
            return;
        }
        if (containsAirplane(aplanes,old)){
            aplanes.remove(old);
            aplanes.add(n3w);
            removeAirplane(old);
            addAirplane(n3w);
        }
    }
    public static void modifyAirport(Airport old, Airport nw) {
        //update arraylist
        //update file
        if (old == nw){
            return;
        }
        if (containsAirport(aprts,nw)){
            System.out.println("You cant modify an airport to be the same as another airport");
            return;
        }
        if (containsAirport(aprts,old)){
            aprts.remove(old);
            aprts.add(nw);
            removeAirport(old);
            addAirport(nw);
        }
    }
    public static void main(String[] args) {
        // this main method is for testing db functionality, for the developers
        // if z li touches this code hes bypassing what i intended for him to be able to test
        // and thus will be able to break it, hopefully otherwise he will have a hard time breaking this code,
        // the checks are pretty redundant
        //db dbinst = new db("src/dbDir/airports.txt", "src/dbDir/airplanes.txt");
        //makeMassiveAportDB("src/dbDir/airports1.txt");

            //WORKING CODE TO INTERACT WITH DB: (prints for debug)
            DataBaseManager dbinst = new DataBaseManager("src/dbDir/airports.txt", "src/dbDir/airplanes.txt");
            aplanes = readAirplanes();
            aprts = readAirports();
            //dbinst.addAirport(new Airport("KJFK", "John F. Kennedy International Airport", new String[]{"JA-A","AVGAS"}, 40.6413, -73.7781, 100.0));
            //dbinst.addAirplane(new Airplane("Boeing", "Shittyplane4", "JA-A", 10000, 100, 500));
            //dbinst.addAirplane(new Airplane("Boeing", "Shittyplane5", "JA-A", 10000, 100, 500));
            //dbinst.removeAirplane(new Airplane("Boeing", "Shittyplane5", "JA-A", 10000, 100, 500));
            Airport a = new Airport("KJFK", "Johne F. Kennedy International Airport", new String[]{"AVGAS"}, 40.6413, -73.7781, 100.0);
            Airport a1 = new Airport("KJFK (modified)", "John F. Kennedy International Airport", new String[]{"AVGAS","JA-a"}, 40.6413, -73.7781, 100.0);
            Airport a2 = new Airport("CYYZ","Torontoo Pearson, Toronto, Canada", new String[]{"AVGAS","JA-a"}, 79.62, 43.68, 122.275);
            //dbinst.addAirport(a);
            //dbinst.addAirport(a1);
            //dbinst.addAirport(a2);
            //dbinst.removeAirport(a2);
            //dbinst.modifyAirport(a,a1);
            //dbinst.addAirport(a2);
            //dbinst.modifyAirport(a1,a);
        /*
            for (Airport a : aprts) {
                System.out.println(a.CAOid);
                System.out.println(a.APTname);
                System.out.println(a.APRTlongitude);
                System.out.println(a.APRTlatitude);
                System.out.println(a.freq);
                for (String s : a.APRTfuelTypes) {
                    System.out.println(s);
                }
            }


            for (Airplane a : aplanes) {
                System.out.println("Make: " + a.make + " Model: " + a.model + " Fuel: " + a.fuel + " Fuel Capacity: " + a.fuelCapacity + " Fuel Consumption: " + a.fuelConsumption + " Speed: " + a.speed + " Range: " + a.range + " units");

            }*/

    }
}
