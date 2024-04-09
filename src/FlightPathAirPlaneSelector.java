import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FlightPathAirPlaneSelector extends JPanel {

    ArrayList<Airplane> Airplanes;

    JPanel holderJPanel = new JPanel();

    JPanel anotherHolder = new JPanel();

    FlightPathAirPlaneSelector(ArrayList<Airplane> airplanes) {

        Airplanes = airplanes;
        Init();
    }

    private void Init() {

        JTextField searchbar = new JTextField("");
        holderJPanel.removeAll();
        generateAirplaneList();
        holderJPanel.setLayout(new GridLayout(Airplanes.size(), 1));
        setLayout(new GridLayout(2, Airplanes.size()));

        searchbar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchbar.getText();
                DataBaseManager db = new DataBaseManager("src/dbDir/airports.txt", "src/dbDir/airplanes.txt");

                ArrayList<Airplane> list = db.searchAirplanes(searchText);
                Airplanes.clear();
                Airplanes.addAll(list);

                anotherHolder.removeAll();

                holderJPanel = new JPanel();
                generateAirplaneList();
                revalidate();
                repaint();
                FlightPlanScene parent = (FlightPlanScene) getParent();
                parent.reInit();
                reinit();
            }
        });

        JScrollPane scrollPane = new JScrollPane(holderJPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        anotherHolder.removeAll();

        anotherHolder.setLayout(new BorderLayout());
        anotherHolder.add(searchbar, BorderLayout.NORTH);
        anotherHolder.add(scrollPane, BorderLayout.CENTER);
        add(anotherHolder);

        repaint();
    }

    private void reinit() {
        this.removeAll();
        Init();
    }

    private void generateAirplaneList() {
        for (Airplane plane : Airplanes) {

            JLabel airplaneviewer = new JLabel(plane.forPrint());

            JButton addbutton = new JButton("+");

            addbutton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    repaint();
                }
            });

            airplaneviewer.add(addbutton, BorderLayout.EAST);
            holderJPanel.add(airplaneviewer, BorderLayout.CENTER);
        }
    }
}