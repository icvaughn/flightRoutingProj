import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.awt.Desktop;
public class Scenes extends JFrame {

    private JPanel currentScene = null;

    public Scenes() {
        add(createButtonPanel(), BorderLayout.EAST);
        add(createWarningLabel(), BorderLayout.SOUTH);
        pack();
    }


    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        return button;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(8,1));
        buttonPanel.add(createButton("Make Plan", e -> switchScene(new FlightPlanScene())));
        buttonPanel.add(createButton("Add Airplane", e -> switchScene(new addAirplanePanel())));
        buttonPanel.add(createButton("Add Airport", e -> switchScene(new addAirportPanel())));
        buttonPanel.add(createButton("Remove Airplane", e -> switchScene(new removeAirplanePanel())));
        buttonPanel.add(createButton("Remove Airport", e -> switchScene(new removeAirportPanel())));
        buttonPanel.add(createButton("Modify Airplane", e -> switchScene(new modifyAirplanePanel())));
        buttonPanel.add(createButton("Modify Airport", e -> switchScene(new modifyAirportPanel())));
        buttonPanel.add(createButton("Exit", e -> switchScene(new snake.GamePanel())));
        return buttonPanel;
    }

    private JTextArea createWarningLabel() {
        JTextArea warningLabel = new JTextArea("THIS SOFTWARE IS NOT TO BE USED FOR FLIGHT PLANNING OR NAVIGATIONAL PURPOSE");
        warningLabel.setLineWrap(true);
        warningLabel.setWrapStyleWord(true);
        warningLabel.setEditable(false);
        return warningLabel;
    }

    private void switchScene(JPanel newScene) {
        if (currentScene != null) {
            remove(currentScene);
        }
        currentScene = newScene;
        add(currentScene, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        Scenes frame = new Scenes();
        frame.setTitle("Flight Planner");
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 320);
        frame.setVisible(true);
    }



public static class FlightPlanScene extends JPanel {
    private DataBaseManager DB = new DataBaseManager("./src/dbDir/airports.txt", "./src/dbDir/airplanes.txt");
    private ArrayList<Airport> airports = DB.aprts;
    private ArrayList<Airplane> airplanes = DB.aplanes;


    private JComboBox airplaneDropDown;
    private JComboBox firstAirportDropDown;
    private JComboBox lastAirportDropDown;
    private JComboBox additionalAirportsDropDown;

    public FlightPlanScene() {
        DB.readAirports();
        DB.readAirplanes();

        setLayout(new BorderLayout());
        add(createDropDownPanel(), BorderLayout.CENTER);
        add(createSubmitButton(), BorderLayout.WEST);
    }

    private JPanel createDropDownPanel() {
        JPanel holderPanel = new JPanel(new GridLayout(2, 2));
        holderPanel.add(createDropDown("Airplane", airplanes));
        holderPanel.add(createDropDown("First Airport", airports));
        holderPanel.add(createDropDown("Last Airport", airports));
        holderPanel.add(createDropDown("Additional Airports", airports));
        return holderPanel;
    }

    private JPanel createDropDown(String name, ArrayList<?> list) {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(new JLabel(name));

        JComboBox<Object> comboBox = new JComboBox<>(list.toArray());
        switch (name) {
            case "Airplane" -> airplaneDropDown = comboBox;
            case "First Airport" -> firstAirportDropDown = comboBox;
            case "Last Airport" -> lastAirportDropDown = comboBox;
            case "Additional Airports" -> additionalAirportsDropDown = comboBox;
        }

        panel.add(comboBox);
        return panel;
    }

    private JButton createSubmitButton() {
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            removeAll();
            add(createFlightPathPanel(), BorderLayout.CENTER);
            revalidate();
            repaint();
        });
        return submitButton;
    }

    private JScrollPane createFlightPathPanel() {
        ArrayList<Airport> flightplan = new ArrayList<>();
        Airplane airplane = (Airplane)airplaneDropDown.getSelectedItem();
        flightplan.add(((Airport) firstAirportDropDown.getSelectedItem()));
        //flightplan.add(((Airport) additionalAirportsDropDown.getSelectedItem()));
        flightplan.add(((Airport) lastAirportDropDown.getSelectedItem()));
        JPanel holderPanel = new JPanel(new GridLayout(1, flightplan.size()));
        assert airplane != null;
        ArrayList<Airport> selectionList = DB.searchAirports("", airplane);
        try {
            for (int i = 1; i < flightplan.size(); i++) {
                flightPlan plan = new flightPlan(selectionList, flightplan.get(i - 1), flightplan.get(i), airplane);
                holderPanel.add(plan.pathDisplay);
            }
        } catch (Exception e) {
            e.printStackTrace();
            for (int eee = 0; eee < 10; eee++) {
                System.out.println("FIX THIS EXCEPTION HDL ISAAAC LINE 143");
                File file = new File("./src/dbDir/airports.txt");
                try {
                    Desktop.getDesktop().open(file);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
        return new JScrollPane(holderPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    private JPanel createLegPanel(AirportInfo info) {
        JPanel panel = new JPanel(new GridLayout(6, 1));
        panel.add(new JLabel("Current Airport: " + info.thisAirport.CAOid + " " + info.thisAirport.APTname + " " + info.thisAirport.freq + " " + info.thisAirport.APRTlatitude + " " + info.thisAirport.APRTlongitude));
        panel.add(new JLabel("Destination Airport: " + info.nextAirport.CAOid + " " + info.nextAirport.APTname + " " + info.nextAirport.freq + " " + info.nextAirport.APRTlatitude + " " + info.nextAirport.APRTlongitude));
        panel.add(new JLabel("Leg Distance: " + String.valueOf(info.distance) + " km"));
        panel.add(new JLabel("Heading of Leg: " + String.valueOf(info.Heading) + " degrees"));
        panel.add(new JLabel("Fuel Used in Leg: " + String.valueOf(info.fuelCost) + " L"));
        panel.add(new JLabel("Expected Time of Leg: " + String.valueOf(info.timeCost) + " hours"));
        return panel;
    }
}
}
