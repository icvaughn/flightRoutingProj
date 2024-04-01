import java.util.*;
public class Point {
    String name;
    double x;
    double y;
    public Point(){};
    double weight;
    boolean isStart;
    boolean isEnd;
    public List<Point> neighbors = new ArrayList<>();
    public Point(double x, double y, String name){
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public static double[] deriveWeight(Point start, Point end, Point p) {
        Point intercept = new Point();
        double slopeOrig = ((end.y - start.y) / (end.x - start.x));
        double slopePerp = -1 / slopeOrig;
        intercept.name = "intercept";
        intercept.x = (-1*slopeOrig*(end.x)+end.y+slopePerp*(p.x)-p.y)/(slopePerp-slopeOrig);
        intercept.y = slopePerp*(intercept.x-p.x)+p.y;
        double dToIntercept = Math.sqrt(Math.pow(intercept.x - p.x, 2) + Math.pow(intercept.y - p.y, 2));
        double dToEnd = Math.sqrt(((end.x-p.x)*(end.x-p.x)) + ((end.y-p.y)*(end.y-p.y)));
        return new double[]{dToIntercept, dToEnd};
    }
    public static void setWeight(Point start, Point end, Point p) {
        double[] weights = deriveWeight(start, end, p);
        p.weight = weights[1]+weights[0];//weights[1]-weights[0];//Math.sqrt(Math.pow(weights[0],2)+Math.pow(weights[1],2)); //weights[1]-weights[0];//
    }
    public static double getWeight(Point p){
        return p.weight;
    }
    public static void setStart(Point p){
        p.isStart = true;
    }
    public static void setEnd(Point p){
        p.isEnd = true;
    }
    public static boolean isInRange(int r, Point Base, Point Jump){
        return Math.sqrt(Math.pow(Jump.x-Base.x,2)+Math.pow(Jump.y-Base.y,2)) <= r;
    }
    public List<Point> getN(){
        return neighbors;
    }
    public Point getMin(){
        Point p = new Point();
        p.weight = 1000000000;
        if (neighbors.size() == 0){
            return this;
        }
        for (Point pt : neighbors){
            if (pt.weight < p.weight){
                p = pt;
            }
        }
        return p;
    }
    public void associateNbs(Point[] pts, int range){
        for (Point p: pts){
            if (this != p){
                if (isInRange(range, this, p)){
                    if (!neighbors.contains(p)) {
                        neighbors.add(p);
                    }
                }
            }
        }
    }
    public void associateNbs(Point[] pts,Point base, int range){
        for (Point p: pts){
            if (base != p){
                if (isInRange(range, base, p)){
                    if (!neighbors.contains(p)) {
                        neighbors.add(p);
                    }
                }
            }
        }
    }
    public void printNBS(){
        for (Point p: neighbors){
            System.out.println(p.name + " " + p.x + " " + p.y);
        }
    }
    public List<Point> dijkstra(Point start, Point end, Point[] pts) {
        PriorityQueue<Point> queue = new PriorityQueue<>(Comparator.comparingDouble(Point::getWeight));
        Map<Point, Point> previous = new HashMap<>();
        Map<Point, Double> distance = new HashMap<>();

        for (Point point : pts) {
            if (point == start) {
                distance.put(point, 0.0);
            } else {
                distance.put(point, Double.MAX_VALUE);
            }
            queue.add(point);
        }

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            for (Point neighbor : current.getN()) {
                double altDistance = distance.get(current) + current.getWeight(neighbor);
                if (altDistance < distance.get(neighbor)) {
                    distance.put(neighbor, altDistance);
                    previous.put(neighbor, current);
                    queue.remove(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        List<Point> path = new ArrayList<>();
        for (Point point = end; point != null; point = previous.get(point)) {
            path.add(0, point);
        }

        return path;
    }
    /*
    public static void main(String[] args){
        Point[] pts = dbManager.readDB(100, "pts.txt");
        for (Point p: pts){
            p.associateNbs(pts, 10);
            System.out.println(p.name + " " + p.x + " " + p.y);
            System.out.println("Neighbors:");
            p.printNBS();
        }
    }
    */
}
