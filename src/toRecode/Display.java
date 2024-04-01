import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.*;
public class Display {
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Point[] pts;
                dbManager dbmgr1 = new dbManager("pts.txt");
                dbmgr1.makeDB(5000, 120);
                pts = dbManager.readDB(5000, "pts.txt");
                Point[] sE = setRandomPath(pts);
                for(Point pp : pts){
                    Point.setWeight(sE[0], sE[1], pp);
                }

                for (Point p : pts){
                    p.associateNbs(pts,p, 10);
                }
                //pts[50].printNBS();
                JFrame frame = new JFrame("Graph");
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1000, 1000);
                Point[] finalPts = pts;
                java.util.List<Point> path = pts[0].dijkstra(sE[0], sE[1], pts);
                JPanel p = new JPanel(){
                    protected void paintComponent(Graphics g){
                        super.paintComponent(g);
                        for (Point pt : finalPts){
                            g.setColor(Color.BLACK);
                            if (pt.isStart) {
                                g.setColor(Color.RED);
                                g.drawString("Start", (int)pt.x*7, getHeight()-(int)pt.y*7);
                                g.fillOval((int)pt.x*7-5, getHeight()-(int)pt.y*7-5, 10, 10);
                            } else if (pt.isEnd) {
                                g.setColor(Color.GREEN);
                                g.drawString("End", (int)pt.x*7, getHeight()-(int)pt.y*7);
                                g.fillOval((int)pt.x*7-5, getHeight()-(int)pt.y*7-5, 10, 10);
                            } else {
                                g.fillOval((int)pt.x*7-2, getHeight()-(int)pt.y*7-2, 5, 5);
                            }
                            Point min = pt.getMin();
                        }
                        for (int i = 0; i < path.size()-1; i++){
                            if (i == path.size()-1){
                                break;
                            }
                            g.setColor(Color.BLUE);
                            g.drawArc((int)path.get(i).x*7-10*7, getHeight()-(int)path.get(i).y*7-10*7, 20*7, 20*7, 0, 360);
                            g.setColor(Color.BLACK);
                            g.drawLine((int)path.get(i).x*7, getHeight()-(int)path.get(i).y*7, (int)path.get(i+1).x*7, getHeight()-(int)path.get(i+1).y*7);
                        }
                        repaint();
                    }
                };
                frame.add(p);
            }
        });
    }
    public static Point[] setRandomPath(Point[] pts){
        Random randy = new Random();
        int start = randy.nextInt(pts.length-1);
        int end = randy.nextInt(pts.length-1);
        Point.setStart(pts[start]);
        Point.setEnd(pts[end]);
        Point[] path = new Point[2];
        path[0] = pts[start];
        path[1] = pts[end];
        return path;
    }

}
