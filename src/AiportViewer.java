import javax.swing.*;
import java.awt.*;
import java.text.ParsePosition;
import java.util.*;

public class AiportViewer extends JPanel {

    String CAOid = "";
    String APTname = "";
    String[] APRTfuleTypes;
    double APRTlongitude;
    double APRTlatitude;

    int x= 0;
    int y= 0 ;


    public AiportViewer (airport airport){
        //initilization
         CAOid = airport.CAOid;
         APTname = airport.APTname;
         APRTfuleTypes = airport.APRTfuleTypes;
         APRTlongitude = airport.APRTlongitude;
         APRTlatitude = airport.APRTlatitude;




         setLayout(new BorderLayout());
        //creates a jlable for the airportinformation and adding it to the center border view
         String fuleTypes = new String();
        for (String string:APRTfuleTypes) {
            fuleTypes = fuleTypes + string +", ";
        }
        add(new JLabel(CAOid+ "\n"+ APTname + "\n" + fuleTypes+ "\n"+ String.valueOf(APRTlatitude) + ", "+ String.valueOf(APRTlongitude)), BorderLayout.CENTER);
        repaint();
    }
}

//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//
//        y += 10;
//        g.drawString(CAOid, x, y );
//        y+=10;
//        g.drawString(APTname, x, y );
//        y+=10;
//        int addx = 5;
//        for (String fluletype:APRTfuleTypes) {
//
//            g.drawString(fluletype, addx + x, y );
//
//            addx += fluletype.length() * 10;
//        }
//        y+=10;
//        g.drawString((APRTlatitude + (" , ") + APRTlongitude), x, y);
//
//    }
//
//}
