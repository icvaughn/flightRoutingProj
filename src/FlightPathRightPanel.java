
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FlightPathRightPanel extends JPanel {
    private ArrayList<Airport> airports;
    private ArrayList<Airport> flightList;
    private DataBaseManager db;

    public FlightPathRightPanel(ArrayList<Airport> airports, ArrayList<Airport> flightList, DataBaseManager db) {
        this.airports = airports;
        this.flightList = flightList;
        this.db = db;
        setLayout(new GridLayout(2, 1));
        add(createSearchBar());
        add(createScrollPane());
    }

    private JTextField createSearchBar() {
        JTextField searchBar = new JTextField("");
        searchBar.addActionListener(e -> updateAirports(searchBar.getText()));
        return searchBar;
    }

    private JScrollPane createScrollPane() {
        return new JScrollPane(createAirportList());
    }

    private void updateAirports(String searchText) {
        airports.clear();
        airports.addAll(db.searchAirports(searchText));
        removeAll();
        add(createSearchBar());
        add(createScrollPane());
        revalidate();
        repaint();
    }

    private JPanel createAirportList() {
        JPanel airportListPanel = new JPanel(new GridLayout(airports.size(), 1));
        airports.forEach(airport -> airportListPanel.add(createAirportViewer(airport)));
        return airportListPanel;
    }

    private JPanel createAirportViewer(Airport airport) {
        AiportViewer airportViewer = new AiportViewer(airport);
        airportViewer.add(createAddButton(airport), BorderLayout.EAST);
        return airportViewer;
    }

    private JButton createAddButton(Airport airport) {
        JButton addButton = new JButton("+");
        addButton.addActionListener(e -> {
            flightList.add(airport);
            repaint();
        });
        return addButton;
    }
}
