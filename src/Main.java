import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Main {
    static airport[] airports;
    public static void main(String[] args) {
        System.out.println("Hello world!");
        String[] gas = {"Jet", "Gasoline"} ;
        airport west = new airport("KEN", "Kenidy Airport", gas, 34.369850, -80.084534);
        airport east = new airport("CEN", "Cenidy Airport", gas, 134.369850, -80.084534);
        airport mid = new airport("MEN", "Menidy Airport", gas, 84.369850, -80.084534);
        airport[] aprts = {west,east,mid};
        airports = aprts;


    }

}