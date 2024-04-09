import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.util.PriorityQueue;
public class flightPlan {
    //back-end
    public ArrayList<Airport> inputApts;
    public ArrayList<Airport> optimalPath;
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

    public ArrayList<point> bestPathpts = new ArrayList<>();
    //priorqueue?

    //custom class for algorithmic use
    public class point {
        public String name;
        public double x;
        public double y;
        public double weight;
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
                pts.add(new point(a));
            }
            return pts;
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
        //this is not finished: will work when im back
        public ArrayList<AirportInfo> getBestPath(ArrayList<Airport> pts, Airport start, Airport end, Airplane ap){
            ArrayList<AirportInfo> path = new ArrayList<>();
            ArrayList<point> ptz = convert(pts);
            point startpt = new point(start);
            point endpt = new point(end);
            ptz.get(ptz.indexOf(new point(start))).isStart = true;
            ptz.get(ptz.indexOf(new point(end))).isEnd = true;
            point[] ptsArr = ptz.toArray(new point[ptz.size()]);
            for (point p : ptsArr){
                p.setWeight(startpt,endpt, p);
            }
            for (point p : ptsArr){
                p.associateNbs(ptsArr, ap.range);
            }
            boolean finished = false;
            while (!finished){
                point min = startpt.getMin();
                if (min.isEnd){
                    finished = true;
                }
                path.add(new AirportInfo(pts.get(ptz.indexOf(min))));
            }
            return path;
        }
    }
}
