import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class flightPathRightPanel extends JPanel {

    ArrayList<Airport> Airports;
    ArrayList<Airport> flightlist;
    JPanel holderJPanel = new JPanel();

    JPanel anotherHolder = new JPanel();

    Airplane Airplane;

DataBaseManager db;

    flightPathRightPanel(ArrayList<Airport> airportes, ArrayList<Airport> flightlists, DataBaseManager DB) {

        Airports = airportes;
        flightlist = flightlists;
        db= DB;
//        FlightPlanScene parent = (FlightPlanScene) getParent();
        Init();
    }

    private void Init() {

        JTextField searchbar = new JTextField("");
        holderJPanel.removeAll();
        generateAirportList();
        holderJPanel.setLayout(new GridLayout(Airports.size(), 1));
        setLayout(new GridLayout(2,flightlist.size())); //this line is here so that the panels sizing is the same, left and right need to have the same behavior within the parent container
        // Add the text area to a JScrollPane with a vertical scrollbar

        searchbar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchbar.getText();
                //DataBaseManager db = new DataBaseManager("src/dbDir/airports.txt", "src/dbDir/airplanes.txt");
                ArrayList<Airport> list;
                if(Airplane.model == null){list = db.searchAirports(searchText);}
                else{System.out.println("Airplane model: " + Airplane.trueFuelType);
                    list = db.searchAirports(searchText, Airplane);}
                Airports.clear();
                Airports.addAll(list);

                anotherHolder.removeAll();

                holderJPanel = new JPanel();
                generateAirportList();
                revalidate();
                repaint();
                //line to reinitialize parent scene (refresh display)
//                FlightPlanScene parent = (FlightPlanScene) getParent();
//                parent.left.reinit();
                reinit();

            }



        });


        JScrollPane scrollPane = new JScrollPane(holderJPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        anotherHolder.removeAll();

        anotherHolder.setLayout(new BorderLayout());
        anotherHolder.add(searchbar, BorderLayout.NORTH);
        anotherHolder.add(scrollPane, BorderLayout.CENTER);
        add(anotherHolder);



        repaint();

    }

    void reinit() {
        this.removeAll();
        Init();

    }

    ;

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
                    repaint();
//                    FlightPlanScene parent = (FlightPlanScene) getParent();
//                     parent.reInit();
                };

            });
            // add button to right of the airport Viewer
            airpotviewer.add(addbutton, BorderLayout.EAST);
            holderJPanel.add(airpotviewer, BorderLayout.CENTER);

        }

    };

}
