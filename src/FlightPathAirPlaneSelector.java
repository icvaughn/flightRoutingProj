import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class FlightPathAirPlaneSelector extends JPanel {

    ArrayList<Airplane> Airplanes;

    Airplane Airplane;
    JPanel holderJPanel = new JPanel();

    JPanel anotherHolder = new JPanel();

    DataBaseManager db;

    FlightPathAirPlaneSelector(ArrayList<Airplane> airplanes,Airplane airplane, DataBaseManager DB) {

        Airplane = airplane;
        Airplanes = airplanes;
        db = DB;
        Init();
    }

    private void Init() {

        JTextField searchbar = new JTextField("");
        holderJPanel.removeAll();
        generateAirplaneList();
        holderJPanel.setLayout(new GridLayout(Airplanes.size(), 1));
        setLayout(new GridLayout(2, Airplanes.size()));


        setSize(500, 800);

        searchbar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchbar.getText();

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
        if(Airplane.model != null) anotherHolder.add(new JLabel(Airplane.forPrint()), BorderLayout.SOUTH);
        add(anotherHolder);
        repaint();
    }

    void reinit() {
        this.removeAll();
        Init();
    }

    private void generateAirplaneList() {
        for (Airplane plane : Airplanes) {

            JButton airplaneviewer = new JButton(plane.forPrint());


            airplaneviewer.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Airplane.make = plane.make;
                    Airplane.model = plane.model;
                    Airplane.fuel = plane.fuel;
                    Airplane.fuelCapacity = plane.fuelCapacity;
                    Airplane.speed = plane.speed;
                    Airplane.fuelConsumption = plane.fuelConsumption;
                    Airplane.range = plane.range;
                    Airplane.searchFuel = plane.searchFuel;
                    repaint();
                    FlightPlanScene parent = (FlightPlanScene) getParent();
                    //parent.Ai = Airplane;
                    parent.right.Airports = db.searchAirports("", Airplane);
                    parent.right.reinit();
                    reinit();
                    revalidate();
                    repaint();
                }
            });

            holderJPanel.add(airplaneviewer, BorderLayout.CENTER);
        }
    }
}