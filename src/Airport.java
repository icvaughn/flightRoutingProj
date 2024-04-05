public class airport {
    String CAOid = "";
    String APTname = "";
    String[] APRTfuelTypes;
    double APRTlongitude;
    double APRTlatitude;
    double freq;

    public airport(String CAO, String Name, String[] fuelTypes, double Longitude, double Latitude, double fr) {
        CAOid = CAO;
        APTname = Name;
        APRTfuelTypes = fuelTypes;
        APRTlongitude = Longitude;
        APRTlatitude = Latitude;
        freq = fr;
    }




}
