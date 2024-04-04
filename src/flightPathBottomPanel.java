import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class flightPathBottomPanel extends JPanel {

    ArrayList<airport> flightlist;
    JPanel holderJPanel = new JPanel();

    TextField searchBar= new TextField();

    flightPathBottomPanel(ArrayList<airport> airportes, ArrayList<airport> flightlists) {
        flightlist = flightlists;
        setLayout(new GridLayout(2,flightlist.size()));
        generateAirportList();
        holderJPanel.setLayout(new GridLayout(1, flightlist.size()));
        // Add the text area to a JScrollPane with a vertical scrollbar
        JScrollPane scrollPane = new JScrollPane(holderJPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        // Create a vertical scrollbar
        JScrollBar verticalScrollBar = scrollPane.getHorizontalScrollBar();
        scrollPane.setPreferredSize(new Dimension(this.getWidth(),50));
        add(scrollPane);
        setSize(500,800);

        add(new JButton("101"));
        repaint();
    };

    private void generateAirportList() {
        // Generates airplane viewers with buttons in order to add them to the flight
        // plan
        for (airport aprt : flightlist) {

            AiportViewer airpotviewer = new AiportViewer(aprt);

            JButton addbutton = new JButton("-");



            addbutton.addActionListener(new ActionListener() {
                // add the airport the button is associated with to the flightPlan ArrayList
                public void actionPerformed(ActionEvent e) {
                    flightlist.remove(aprt);
                    holderJPanel = new JPanel();
                    generateAirportList();

                    revalidate();
                    repaint();
                };

            });
            // add button to right of the airport Viewer
            airpotviewer.add(addbutton, BorderLayout.EAST);

            holderJPanel.add(airpotviewer, BorderLayout.CENTER);

        }
    };
}
