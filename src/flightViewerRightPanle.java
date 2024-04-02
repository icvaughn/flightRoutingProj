import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class flightViewerRightPanle extends JPanel {

    ArrayList<airport> airports;
    ArrayList<airport> flightlist;
    JPanel holderJPanel = new JPanel();

    flightViewerRightPanle(ArrayList<airport> airportes, ArrayList<airport> flightlists) {
        airports = airportes;
        flightlist = flightlists;
        setLayout(new GridLayout(airports.size(), 1));
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
        for (airport aprt : airports) {

            AiportViewer airpotviewer = new AiportViewer(aprt);

            JButton addbutton = new JButton("+");

            addbutton.addActionListener(new ActionListener() {
                // add the airport the button is associated with to the flightPlan ArrayList
                public void actionPerformed(ActionEvent e) {
                    flightlist.add(aprt);
                    repaint();
                };

            });
            // add button to right of the airport Viewer
            airpotviewer.add(addbutton, BorderLayout.EAST);

            holderJPanel.add(airpotviewer, BorderLayout.CENTER);

        }
    };

}
