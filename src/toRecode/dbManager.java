package toRecode;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
public class dbManager {
    public static String filePath;
    public static File f;
    public dbManager(){};
    public dbManager(String fp){
        filePath = fp;
        f = new File(filePath);
    }
    public static void makeDB(int s, int max){
        Random randy = new Random();
        try (FileWriter writer = new FileWriter(f)) {
            for (int i = 1; i <= s; i++) {
                String name = "Point" + i;
                int x = randy.nextInt(max+1);
                int y = randy.nextInt(max+1);
                writer.write(name + "\n");
                writer.write(x + "\n");
                if (i == s) {
                    writer.write(y + "");
                } else {
                    writer.write(y + "\n");
                }
            }
        } catch (IOException e) {
            System.err.println("The helpless child laborer could not get the file written: " + e.getMessage());
        }
    }
    public static Point[] readDB(int s){ //expected size
        Point[] pts = new Point[s];
        try {
            Scanner sc = new Scanner(f);
            for (int i = 0; sc.hasNextLine(); i++) {
                String name = sc.nextLine();
                double x = Double.parseDouble(sc.nextLine());
                double y = Double.parseDouble(sc.nextLine());
                pts[i] = new Point(x, y, name);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return pts;
    }
    public static Point[] readDB(int s, String fn){ //expected size
        f = new File(fn);
        Point[] pts = new Point[s];
        try {
            Scanner sc = new Scanner(f);
            for (int i = 0; sc.hasNextLine(); i++) {
                String name = sc.nextLine();
                double x = Double.parseDouble(sc.nextLine());
                double y = Double.parseDouble(sc.nextLine());
                pts[i] = new Point(x, y, name);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return pts;
    }
    /*
    public static void main(String[] args){
        dbManager db = new dbManager("pts.txt");
        //db.makeDB(100,120);
        Point[] thesePTS = db.readDB(100);
        for (Point p : thesePTS) {
            System.out.println(p.name + " " + p.x + " " + p.y);
        }
    }
     */
}
