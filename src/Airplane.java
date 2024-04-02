public class Airplane {
    public String make;
    public String model;
    public String fuel;
    public int fuelCapacity;
    public int fuelConsumption;
    public int speed;
    public double range; // unit based on speed unit m or km
    // this will only be accurate if the units match up (Liters, liters/hr, km/hr or
    // m/hr)

    public Airplane(String make, String model, String fuel, int fuelCapacity, int fuelConsumption, int speed) {
        this.make = make;
        this.model = model;
        this.fuel = fuel;
        this.fuelCapacity = fuelCapacity;
        this.fuelConsumption = fuelConsumption;
        this.speed = speed;
    }

    public void setRange() {
        this.range = (double) fuelCapacity / fuelConsumption * speed;
    }
}
