import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.lang.*;

public class dbInit {
    public static File airportDB;
    public static File airplaneDB;
    public dbInit(String airportDBs, String airplaneDBs){
        airportDB = new File(airportDBs);
        airplaneDB = new File(airplaneDBs);
    }
    //replace string with airport
    public static ArrayList<airport> readAirports(){
        ArrayList<airport> aprts = new ArrayList<airport>();
        try {
            boolean hasboth = true;
            String nxt = "";
            Scanner sc = new Scanner(airportDB);
            while (sc.hasNextLine()) {
                String ICAO;
                if (!hasboth) {
                    if(nxt == ""){
                        System.out.println("bug line 29");
                    }
                    ICAO = nxt;
                } else {
                    ICAO = sc.nextLine();
                }

                String Loc = sc.nextLine();

                Double Long = Double.parseDouble(sc.nextLine());

                Double Lat = Double.parseDouble(sc.nextLine());

                Double freq = Double.parseDouble(sc.nextLine());

                String[] fts = new String[2];
                fts[0] = sc.nextLine();
                nxt = sc.nextLine();
                if (Objects.equals(nxt, "JA-a")) {
                    fts[1] = nxt;
                    hasboth = true;
                } else {
                    hasboth = false;
                }
                for (String s : fts) {
                    System.out.println(s);
                }
                airport a = new airport(ICAO, Loc, fts, Long, Lat);
                aprts.add(a);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return aprts;
    }
    public static void main(String[] args){
        dbInit db = new dbInit("src/airports.txt", "src/airplanes.txt");
        ArrayList<airport> aprts = db.readAirports();
        for (airport a : aprts) {
            System.out.println(a.CAOid);
            System.out.println(a.APTname);
            System.out.println(a.APRTlongitude);
            System.out.println(a.APRTlatitude);
            for (String s : a.APRTfuelTypes) {
                System.out.println(s);
            }
        }
    }
}
