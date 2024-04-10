import java.text.DecimalFormat;
public class Airplane {
    public String make;
    public String model;
    public String fuel;
    public String trueFuelType;
    public double fuelCapacity;
    public double fuelConsumption;
    public double speed;
    public double range;

    public Airplane(String make, String model, String fuel, double fuelCapacity, double fuelConsumption, double speed) {
        this.make = make;
        this.model = model;
        this.fuel = fuel;
        this.fuelCapacity = fuelCapacity;
        this.fuelConsumption = fuelConsumption;
        this.speed = speed;
        setRange();
    }

    public Airplane() {
        //Empty constructor
    }

    public void setRange() {
        this.range = (double) fuelCapacity / fuelConsumption * (speed*1.852); //converts speed from knots to km/hr
    }
    public void setTrueFuelType(){
        if (fuel.toLowerCase().trim().equals("prop")){
            trueFuelType = "AVGAS";
        }
        if (fuel.toLowerCase().trim().equals("jet")){
            trueFuelType = "JA-a";
        }
        if (fuel.toLowerCase().trim().equals("turboprop")){
            trueFuelType = "JA-a";
        }
    }
    public String forPrint(){
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        //add unit
        return "Make: " + make + ", Model: " + model + ", Fuel Type: " + fuel + ", Capacity(L): " + decimalFormat.format(fuelCapacity) + ", Consumption(L/HR): " + decimalFormat.format(fuelConsumption) + ", Speed: " + decimalFormat.format(speed) + ", Range: " + decimalFormat.format(range);
    }
}
