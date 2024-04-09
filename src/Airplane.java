import java.text.DecimalFormat;
public class Airplane {
    public String make;
    public String model;
    public String fuel;
    public double fuelCapacity;
    public double fuelConsumption;
    public double speed;
    public double range; // unit based on speed unit m or km
    // this will only be accurate if the units match up (Liters, liters/hr, km/hr or
    // m/hr)

    public Airplane(String make, String model, String fuel, double fuelCapacity, double fuelConsumption, double speed) {
        this.make = make;
        this.model = model;
        this.fuel = fuel;
        this.fuelCapacity = fuelCapacity;
        this.fuelConsumption = fuelConsumption;
        this.speed = speed;
        setRange();
    }

    public void setRange() {
        this.range = (double) fuelCapacity / fuelConsumption * speed;
    }
    public String forPrint(){
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        //add unit
        return "Make: " + make + ", Model: " + model + ", Fuel Type: " + fuel + ", Capacity(L): " + decimalFormat.format(fuelCapacity) + ", Consumption(L/HR): " + decimalFormat.format(fuelConsumption) + ", Speed: " + decimalFormat.format(speed) + ", Range: " + decimalFormat.format(range);
    }
}
