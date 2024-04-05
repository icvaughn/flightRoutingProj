import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class flightViewerRightPanle extends JPanel {

    ArrayList<Airport> Airports;
    ArrayList<Airport> flightlist;
    JPanel holderJPanel = new JPanel();

    TextField searchBar= new TextField();

    flightViewerRightPanle(ArrayList<Airport> airportes, ArrayList<Airport> flightlists) {
        Airports = airportes;
        flightlist = flightlists;
        //setLayout(new GridLayout(airports.size(), 1));
        generateAirportList();
        holderJPanel.setLayout(new GridLayout(airportes.size(), 1));
        // Add the text area to a JScrollPane with a vertical scrollbar
        JScrollPane scrollPane = new JScrollPane(holderJPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // Create a vertical scrollbar
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        add(scrollPane);


        repaint();
    };

    private void generateAirportList() {
        // Generates airplane viewers with buttons in order to add them to the flight
        // plan
        for (Airport aprt : Airports) {

            AiportViewer airpotviewer = new AiportViewer(aprt);

            JButton addbutton = new JButton("+");

            addbutton.addActionListener(new ActionListener() {
                // add the airport the button is associated with to the flightPlan ArrayList
                public void actionPerformed(ActionEvent e) {
                    flightlist.add(aprt);
                    firePropertyChange("flightplan",0,1);
                    repaint();
                    FlightPlanScene parent = (FlightPlanScene) getParent();
                    parent.reInit();
                };

            });
            // add button to right of the airport Viewer
            airpotviewer.add(addbutton, BorderLayout.EAST);

            holderJPanel.add(airpotviewer, BorderLayout.CENTER);

        }
    };

}
