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
        public boolean pathless;
        public ArrayList<point> neighbors = new ArrayList<>();

        public point(double x, double y, String name){
            this.x = x;
            this.y = y;
            this.name = name;
            pathless = false;
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
            pathless = false;
        }
        public point(){
            this.x = 0;
            this.y = 0;
            this.name = "tmp";
            pathless = false;
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
                    if (!pt.pathless){
                        p = pt;
                    }
                }
            }

            return p;
        }
        public boolean isInRange(double r, point Base, point Jump){
            return getDistance(Base,Jump) <= r;
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
            distance = getDistance(base,jump);
        }
        public double getDistance(point base,point jump) {
            final int R = 6371; // Radius of the earth in km

            double lat1 = Math.toRadians(base.x);
            double lon1 = Math.toRadians(base.y);
            double lat2 = Math.toRadians(jump.x);
            double lon2 = Math.toRadians(jump.y);

            double dLat = lat2 - lat1;
            double dLon = lon2 - lon1;

            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

            return R * c;
        }
        public static double calculateHeading(double lat1, double log1, double lat2, double log2){
            double t1 = Math.toRadians(lat1);
            double t2 = Math.toRadians(lat2);
            double delta = Math.toRadians(log2 - log1);

            double y = Math.sin(delta) * Math.cos(t2);
            double x = Math.cos(t1) * Math.sin(t2) - Math.sin(t1) * Math.cos(t2) * Math.cos(delta);
            double theta = Math.atan2(y, x);
            double brng = Math.toDegrees(theta);

            // Ensure the bearing is within [0, 360)
            if (brng < 0) {
                brng += 360;
                brng = brng%360;
            }

            return brng;

        }
        //this is not finished: will work when im back
        public ArrayList<AirportInfo> getBestPath(ArrayList<Airport> pts, Airport start, Airport end, Airplane ap) throws Exception {
            ap.setRange();
            ap.setTrueFuelType();
            //System.out.println(ap.trueFuelType);
            ArrayList<point> ptz = new ArrayList<>();
            for (Airport a : pts){
                if (Arrays.asList(a.APRTfuelTypes).contains(ap.trueFuelType)) {
                    ptz.add(new point(a));
                    //System.out.println(a.CAOid);
                }
            }
            ArrayList<AirportInfo> path = new ArrayList<>();


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
            point prev = cpyStr;
            ArrayList<point> pathpts = new ArrayList<>();
            System.out.println(min.getDistance(startpt,endpt));
            for (point p : cpyStr.neighbors){
                System.out.println(p.name);
            }
            while (!finished) {
                if (ptz.get(ptz.indexOf(current.getPoint(current.name,ptz))).isEnd) {
                    finished = true;
                    continue;
                }
                if (current.name == cpyStr.name && current.neighbors.size() == 0){
                    System.out.println("No path found");
                    throw new Exception("No path found");
                }
                //System.out.println("Range: " + isInRange(100, current, min));
                //System.out.println(min.neighbors.get(0).name);
                if (current.neighbors.size() == 0){
                    //current.pathless = true;
                    System.out.println("Pathless: " + current.name);
                    prev.neighbors.remove(current); //removes neighbors that are pathless
                    if (pathpts.contains(current)){
                        pathpts.remove(current);
                    }
                    current = prev;
                    min = current.getMin(); //returns lowest besides the pathless
                }

                //System.out.println(min.name);
                prev = current;
                current = min;
                min = current.getMin();
                pathpts.add(current);
            }
            for (point p : pathpts){
                path.add(new AirportInfo(DB.searchICAO(p.name)));
            }
            return path;
        }
    }
    public flightPlan(ArrayList<Airport> inputApts, Airport start, Airport end, Airplane ap) {
        point p = new point(0,0,"tmp");
        this.inputApts = inputApts;
        this.start = start;
        this.end = end;
        try {
            this.optimalPath = p.getBestPath(inputApts, start, end, ap);
            this.totalDistance = 0;
            this.totalFuelCost = 0;
            this.totalTimeCost = 0;
            this.totalStops = 0;
            for (AirportInfo ai : optimalPath){
                ai.distance = p.getDistance(new point(ai.thisAirport), new point(optimalPath.get(optimalPath.indexOf(ai)+1).thisAirport));
                double time = ai.distance/ap.speed; //this assumes km and km/hr (and L/hr and L)
                ai.timeCost = time;
                ai.fuelCost = time*ap.fuelConsumption; // Liters (assumes L/Hr)
                totalDistance += ai.distance;
                totalFuelCost += ai.fuelCost;
                totalTimeCost += ai.timeCost;
                totalStops++;
                optimalPath.get(optimalPath.indexOf(ai)).Heading = p.calculateHeading(ai.thisAirport.APRTlatitude, ai.thisAirport.APRTlongitude, optimalPath.get(optimalPath.indexOf(ai)+1).thisAirport.APRTlatitude, optimalPath.get(optimalPath.indexOf(ai)+1).thisAirport.APRTlongitude);
                if (optimalPath.indexOf(ai)+1 == optimalPath.size()-1){
                    System.out.println("End of path");
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("No path found");
        }

    }
    public flightPlan(){
        //empty constructor
    }
    public static void main(String[] args){
        DataBaseManager db = new DataBaseManager("./src/dbDir/airports.txt", "./src/dbDir/airplanes.txt");
        ArrayList<Airplane> src = db.searchAirplanes("lowrange,test");
        Airplane ap = src.get(0);
        System.out.println(ap.forPrint());
        Airport start = db.searchICAO("KAUG");
        Airport end = db.searchICAO("KATL");

        //System.out.println(start.forPrint());
        flightPlan fp = new flightPlan(db.aprts, start,end, ap);
        for (AirportInfo ai : fp.optimalPath){
            System.out.println(ai.thisAirport.forPrint());
            System.out.println("Heading: " + ai.Heading);
        }
        System.out.println("Fuel cost " + fp.totalFuelCost);
        System.out.println("Total Distance " + fp.totalDistance);
        System.out.println("d2: " + fp.optimalPath.get(0).distance);
        System.out.println("Total Time " + fp.totalTimeCost);
        System.out.println("Total Stops " + fp.totalStops);

    }
}
/*AirportInfo ai = new AirportInfo(DB.searchICAO(current.name));

                double distance = current.getDistance(current, min);
                //System.out.println(distance);
                ai.distance = distance;

                double time = ai.distance/ap.speed; //this assumes km and km/hr (and L/hr and L)
                ai.timeCost = time; //hours
                ai.fuelCost = time*ap.fuelConsumption; // Liters (assumes L/Hr)
                ai.Heading = calculateHeading(current.x, current.y, min.x, min.y);
                ai.nextAirport = DB.searchICAO(min.name);
                path.add(ai);

 */