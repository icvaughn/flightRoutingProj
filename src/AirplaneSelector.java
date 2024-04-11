import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class AirplaneSelector extends JPanel {
    private ArrayList<Airplane> airplanes;
    private Airplane selectedAirplane;
    private DataBaseManager db;
    private JPanel holderPanel = new JPanel();
    private JPanel anotherHolder = new JPanel();

    public AirplaneSelector(ArrayList<Airplane> airplanes, Airplane airplane, DataBaseManager db) {
        this.airplanes = airplanes;
        this.selectedAirplane = airplane;
        this.db = db;
        init();
    }

    private void init() {
        JTextField searchBar = new JTextField("");
        holderPanel.setLayout(new GridLayout(airplanes.size(), 1));
        setLayout(new GridLayout(2, airplanes.size()));
        searchBar.addActionListener(e -> updateAirplanes(searchBar.getText()));
        JScrollPane scrollPane = new JScrollPane(holderPanel);
        anotherHolder.setLayout(new BorderLayout());
        anotherHolder.add(searchBar, BorderLayout.NORTH);
        anotherHolder.add(scrollPane, BorderLayout.CENTER);
        if (selectedAirplane.model != null) {
            anotherHolder.add(new JLabel(selectedAirplane.forPrint()), BorderLayout.SOUTH);
        }
        add(anotherHolder);
    }

    private void updateAirplanes(String searchText) {
        airplanes = db.searchAirplanes(searchText);
        anotherHolder.removeAll();
        holderPanel = new JPanel();
        generateAirplaneList();
        revalidate();
        repaint();
        reinit();
    }

    private void reinit() {
        removeAll();
        init();
    }

    private void generateAirplaneList() {
        for (Airplane airplane : airplanes) {
            JButton airplaneViewer = new JButton(airplane.forPrint());
            airplaneViewer.addActionListener(e -> updateSelectedAirplane(airplane));
            holderPanel.add(airplaneViewer, BorderLayout.CENTER);
        }
    }

    private void updateSelectedAirplane(Airplane airplane) {
        selectedAirplane.copyFrom(airplane);
        reinit();
    }
}