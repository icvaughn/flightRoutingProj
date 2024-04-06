import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class flightPathRightPanel extends JPanel {

    ArrayList<Airport> Airports;
    ArrayList<Airport> flightlist;
    JPanel holderJPanel = new JPanel();

    TextField searchBar= new TextField();

    flightPathRightPanel(ArrayList<Airport> airportes, ArrayList<Airport> flightlists) {

        Airports = airportes;
        flightlist = flightlists;
        //setLayout(new GridLayout(airports.size(), 1));
        generateAirportList();
        holderJPanel.setLayout(new GridLayout(airportes.size(), 1));
        setLayout(new GridLayout(2,flightlist.size())); //this line is here so that the panels sizing is the same, left and right need to have the same behavior within the parent container
        // Add the text area to a JScrollPane with a vertical scrollbar
        JScrollPane scrollPane = new JScrollPane(holderJPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // Create a vertical scrollbar
        //JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
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
