import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.util.PriorityQueue;
//this algorithm is flat earth asf. will remodel when i have time (before pres)
public class flightPlan {
    //back-end
    public ArrayList<Airport> inputApts;
    public ArrayList<AirportInfo> optimalPath;
    public Airport start;
    public Airport end;
    public DataBaseManager DB = new DataBaseManager("./src/dbDir/airports.txt", "./src/dbDir/airplanes.txt");

    //for display
    public JPanel graphDisplay = new JPanel();
    public JPanel pathDisplay = new JPanel();
    public JPanel pathInfoScrn = new JPanel();

    //more back end
    public AirportInfo[] pathInfo;
    public double totalDistance;
    public double totalFuelCost;
    public double totalTimeCost;
    public int totalStops;

    //public ArrayList<point> bestPathpts = new ArrayList<>();
    //priorqueue?

    //custom class for algorithmic use
    public class point {
        public String name;
        public double x;
        public double y;
        public double weight;
        public double distance;
        public boolean isStart;
        public boolean isEnd;
        public boolean hasAVGAS;
        public boolean hasJAa;
        public ArrayList<point> neighbors = new ArrayList<>();

        public point(double x, double y, String name){
            this.x = x;
            this.y = y;
            this.name = name;
        }
        public point(double x, double y, String name, boolean isStart, boolean isEnd, boolean hasAVGAS, boolean hasJAa){
            this.x = x;
            this.y = y;
            this.name = name;
            if (isStart && isEnd){
                throw new IllegalArgumentException("point cannot be both start and end");
            }
            this.isStart = isStart;
            this.isEnd = isEnd;
            this.hasAVGAS = hasAVGAS;
            this.hasJAa = hasJAa;
        }
        public point(){
            this.x = 0;
            this.y = 0;
            this.name = "tmp";
        }
        public point(Airport a){
            this.x = a.APRTlatitude;
            this.y = a.APRTlongitude;
            this.name = a.CAOid;
            this.hasAVGAS = Arrays.asList(a.APRTfuelTypes).contains("AVGAS");
            this.hasJAa = Arrays.asList(a.APRTfuelTypes).contains("JA-a");
        }
        public ArrayList<point> convert(ArrayList<Airport> apts){
            ArrayList<point> pts = new ArrayList<>();
            for (Airport a : apts){
                point p = new point(a);
                //System.out.println(p.name);
                if (!pts.contains(p)){
                    pts.add(p);
                }
            }
            return pts;
        }
        public point getPoint(String name, ArrayList<point> pts){
            for (point p : pts){
                if (p.name.equals(name)){
                    return p;
                }
            }
            return new point();
        }
        public double[] deriveWeight(point thisPT, point nxtPT, point p) {
            point intercept = new point(0,0,"imaginary");
            double slopeOrig = ((nxtPT.y - thisPT.y) / (nxtPT.x - thisPT.x));
            double slopePerp = -1 / slopeOrig;
            intercept.name = "intercept";
            intercept.x = (-1*slopeOrig*(nxtPT.x)+nxtPT.y+slopePerp*(p.x)-p.y)/(slopePerp-slopeOrig);
            intercept.y = slopePerp*(intercept.x-p.x)+p.y;
            double dToIntercept = Math.sqrt(Math.pow(intercept.x - p.x, 2) + Math.pow(intercept.y - p.y, 2));
            double dTonxtPT = Math.sqrt(((nxtPT.x-p.x)*(nxtPT.x-p.x)) + ((nxtPT.y-p.y)*(nxtPT.y-p.y)));
            return new double[]{dToIntercept, dTonxtPT};
        }
        public void setWeight(point start, point end, point p) {
            double[] weights = deriveWeight(start,end, p);
            p.weight = weights[1]+weights[0];//weights[1]-weights[0];//Math.sqrt(Math.pow(weights[0],2)+Math.pow(weights[1],2)); //weights[1]-weights[0];//
        }

        public point getMin(){
            point p = new point(0,0,"tmp");
            p.weight =  Integer.MAX_VALUE;
            if (neighbors.size() == 0){
                return this;
            }
            for (point pt : neighbors){
                if (pt.weight < p.weight){
                    p = pt;
                }
            }
            return p;
        }
        public boolean isInRange(double r, point Base, point Jump){
            return Math.sqrt(Math.pow(Jump.x-Base.x,2)+Math.pow(Jump.y-Base.y,2)) <= r;
        }
        public void associateNbs(point[] pts, double range){
            for (point p: pts){
                if (this != p){
                    if (isInRange(range, this, p)){
                        if (!neighbors.contains(p)) {
                            neighbors.add(p);
                        }
                    }
                }
            }
        }
        public void associateNbs(point[] pts,point base, double range){
            for (point p: pts){
                if (base != p){
                    if (isInRange(range, base, p)){
                        if (!neighbors.contains(p)) {
                            neighbors.add(p);
                        }
                    }
                }
            }
        }
        public void setDistance(point base,point jump){
            distance = Math.sqrt(Math.pow(jump.x-base.x,2)+Math.pow(jump.y-base.y,2));
        }
        public double getDistance(point base,point jump){
            return Math.sqrt(Math.pow(jump.x-base.x,2)+Math.pow(jump.y-base.y,2));
        }
        public static double calculateHeading(double log1, double lat1, double log2, double lat2){

            double temp = (lat2 - lat1) / Math.sqrt( (log2-log1)*(log2-log1) + (lat2-lat1)*(lat2-lat1) );
            double theta = (180.0 / Math.PI) * Math.acos(temp);

            //System.out.println("The value of theta is " + theta);

            return theta;

        }
        //this is not finished: will work when im back
        public ArrayList<AirportInfo> getBestPath(ArrayList<Airport> pts, Airport start, Airport end, Airplane ap){
            ap.setRange();
            ap.setTrueFuelType();
            System.out.println(ap.trueFuelType);
            ArrayList<point> ptz = new ArrayList<>();
            for (Airport a : pts){
                if (Arrays.asList(a.APRTfuelTypes).contains(ap.trueFuelType)) {
                    ptz.add(new point(a));
                    //System.out.println(a.CAOid);
                }
            }
            ArrayList<AirportInfo> path = new ArrayList<>();

            for (point p : ptz){
                System.out.println(p.name);
            }
            point startpt = new point(start);
            point endpt = new point(end);
            ptz.get(ptz.indexOf(startpt.getPoint(startpt.name,ptz))).isStart = true;
            ptz.get(ptz.indexOf(endpt.getPoint(endpt.name,ptz))).isEnd = true;
            point cpyStr = ptz.get(ptz.indexOf(startpt.getPoint(startpt.name,ptz)));
            point cpyEnd = ptz.get(ptz.indexOf(endpt.getPoint(endpt.name,ptz)));
            point[] ptsArr = ptz.toArray(new point[ptz.size()]);

            for (point p : ptz){
                p.setWeight(cpyStr, cpyEnd, p);
            }
            for (point p : ptz){
                p.associateNbs(ptsArr, ap.range);

            }
            boolean finished = false;
            point min = cpyStr;
            point current = cpyStr;
            while (!finished) {
                if (min.neighbors.size() == 0){
                    throw new IllegalArgumentException("No path found");
                }
                if (ptz.get(ptz.indexOf(current.getPoint(current.name,ptz))).isEnd) {
                    finished = true;
                    continue;
                }
                //System.out.println(min.neighbors.get(0).name);
                for (point p : min.neighbors){
                    System.out.println(min.name + " -Neighbor: " + p.name);
                }
                //System.out.println(min.name);
                current = min;
                min = current.getMin();

                AirportInfo ai = new AirportInfo(DB.searchICAO(current.name));

                double distance = current.getDistance(current, min);
                //System.out.println(distance);
                ai.distance = distance;

                double time = ai.distance/ap.speed; //this assumes km and km/hr (and L/hr and L)
                ai.timeCost = time; //hours
                ai.fuelCost = time*ap.fuelConsumption; // Liters (assumes L/Hr)
                ai.Heading = calculateHeading(current.x, current.y, min.x, min.y);
                ai.nextAirport = DB.searchICAO(min.name);
                path.add(ai);

            }
            return path;
        }
    }
    public flightPlan(ArrayList<Airport> inputApts, Airport start, Airport end, Airplane ap){
        point p = new point(0,0,"tmp");
        this.inputApts = inputApts;
        this.start = start;
        this.end = end;
        this.optimalPath = p.getBestPath(inputApts, start, end, ap);
        this.totalDistance = 0;
        this.totalFuelCost = 0;
        this.totalTimeCost = 0;
        this.totalStops = 0;
        for (AirportInfo ai : optimalPath){
            totalDistance += ai.distance;
            totalFuelCost += ai.fuelCost;
            totalTimeCost += ai.timeCost;
            totalStops++;
        }
    }
    public flightPlan(){
        //empty constructor
    }
    public static void main(String[] args){
        DataBaseManager db = new DataBaseManager("./src/dbDir/airports.txt", "./src/dbDir/airplanes.txt");
        Airplane ap = new Airplane("Boeing", "747", "jet", 100000, 1000, 900);
        Airport start = db.searchICAO("KIAD");
        Airport end = db.searchICAO("KCAE");
        //System.out.println(start.forPrint());
        flightPlan fp = new flightPlan(db.aprts, start,end, ap);
        for (AirportInfo ai : fp.optimalPath){
            System.out.println(ai.thisAirport.forPrint());
            System.out.println("Heading: " + ai.Heading);
        }
        System.out.println("Fuel cost " + fp.totalFuelCost);
        System.out.println("Total Distance " + fp.totalDistance);
        System.out.println("Total Time " + fp.totalTimeCost);
        System.out.println("Total Stops " + fp.totalStops);
    }
}
