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
        repaint();
    };


    private void generateAirportList(){
        //Generates airplane viewers with buttons in order to add them to the flight plan
        for (airport aprt: airports) {

            AiportViewer airpotviewer = new AiportViewer(aprt);

            JButton addbutton = new JButton("+");

            addbutton.addActionListener(new ActionListener() {
                //add the airport the button is associated with to the flightPlan ArrayList
                public void actionPerformed(ActionEvent e) {
                    flightlist.add(aprt);
                    repaint();
                };


            });
            //add button to right of the airport Viewer
            airpotviewer.add(addbutton, BorderLayout.EAST);

            add(airpotviewer, BorderLayout.CENTER);

        }
    };


}
