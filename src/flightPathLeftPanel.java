import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class flightPathLeftPanel extends JPanel {

    ArrayList<Airport> flightlist;
    JPanel holderJPanel = new JPanel();

    flightPathLeftPanel(ArrayList<Airport> airports, ArrayList<Airport> flightlists) {
        flightlist = flightlists;
        setLayout(new GridLayout(2,flightlist.size()));
        generateAirportList();
        holderJPanel.setLayout(new GridLayout(flightlist.size(), 1));
        // Add the text area to a JScrollPane with a vertical scrollbar
        JScrollPane scrollPane = new JScrollPane(holderJPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        // Create a vertical scrollbar
        //JScrollBar verticalScrollBar = scrollPane.getHorizontalScrollBar();
        scrollPane.setPreferredSize(new Dimension(this.getWidth(),50));
        add(scrollPane);
        //setSize(500,800);

        //add(new JButton("101"));
        repaint();
    };
    private void generateAirportList() {
        // Generates airplane viewers with buttons in order to add them to the flight
        // plan
        for (Airport aprt : flightlist) {

            AiportViewer aptviewer = new AiportViewer(aprt);

            JButton rmButton = new JButton("-");



            aptviewer.add(rmButton, BorderLayout.EAST);

            holderJPanel.add(aptviewer, BorderLayout.CENTER);
            rmButton.addActionListener(new ActionListener() {
                // remove the airport the button is associated with from the flightPlan ArrayList
                public void actionPerformed(ActionEvent e) {
                    flightlist.remove(aprt);
                    holderJPanel = new JPanel();
                    generateAirportList();
                    revalidate();
                    repaint();
                    //line to reinitialize parent scene (refresh display)
                    FlightPlanScene parent = (FlightPlanScene) getParent();
                    parent.reInit();
                };

            });
            // add button to right of the airport Viewer

        }
    };
    public void reinit() {
        this.removeAll();
        generateAirportList();
    };
}
