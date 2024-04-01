import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class flightViewerRightPanle extends JPanel {

    airport[] airports;
    ArrayList<airport> flightlist;

    flightViewerRightPanle(airport[] airportes, ArrayList<airport> flightlists){
        airports = airportes;
        flightlist = flightlists;
        setLayout(new GridLayout(airports.length, 1));
        generateAirportList();
    };


    private void generateAirportList(){
        for (airport aprt: airports) {
            AiportViewer airpotviewer = new AiportViewer(aprt);
            airpotviewer.setLayout(new BorderLayout());
            JButton addbutton = new JButton("+");

            addbutton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    flightlist.add(aprt);
                    repaint();
                };


            });
            airpotviewer.add(addbutton, BorderLayout.EAST);

            add(airpotviewer, BorderLayout.CENTER);

        }
    };


}
