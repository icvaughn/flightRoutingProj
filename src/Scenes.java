import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Scenes extends JFrame {

    private JPanel currentScene = null;
    private ArrayList<Airport> airports;
    private ArrayList<Airport> flightPlan = new ArrayList<>();

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
        buttonPanel.add(createButton("Make Plan", e -> switchScene(new FlightPlanScene(), BorderLayout.CENTER)));
        buttonPanel.add(createButton("Add Airplane", e -> switchScene(new addAirplanePanel(), BorderLayout.CENTER)));
        buttonPanel.add(createButton("Add Airport", e -> switchScene(new addAirportPanel(), BorderLayout.CENTER)));
        buttonPanel.add(createButton("Remove Airplane", e -> switchScene(new removeAirplanePanel(), BorderLayout.CENTER)));
        buttonPanel.add(createButton("Remove Airport", e -> switchScene(new removeAirportPanel(), BorderLayout.CENTER)));
        buttonPanel.add(createButton("Modify Airplane", e -> switchScene(new modifyAirplanePanel(), BorderLayout.CENTER)));
        buttonPanel.add(createButton("Modify Airport", e -> switchScene(new modifyAirportPanel(), BorderLayout.CENTER)));
        buttonPanel.add(createButton("Exit", e -> switchScene(new snake.GamePanel(), BorderLayout.CENTER)));
        return buttonPanel;
    }

    private JTextArea createWarningLabel() {
        JTextArea warningLabel = new JTextArea("THIS SOFTWARE IS NOT TO BE USED FOR FLIGHT PLANNING OR NAVIGATIONAL PURPOSE");
        warningLabel.setLineWrap(true);
        warningLabel.setWrapStyleWord(true);
        warningLabel.setEditable(false);
        return warningLabel;
    }

    private void switchScene(JPanel newScene, String position) {
        if (currentScene != null) {
            remove(currentScene);
        }
        currentScene = newScene;
        add(currentScene, position);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        Scenes frame = new Scenes();
        frame.setTitle("Fligth Planer");
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 320);
        frame.setVisible(true);
    }

}

// The class for drawing arcs on a panel
class FlightPlanScene extends JPanel {
    public FlightPathAirPlaneSelector left;
    flightPathRightPanel right;

    public void reInit(){
        remove(CenterPanel);
        CenterPanel = new flightPathLeftPanel(Airports, flightplan);
        add(CenterPanel);
        this.validate();
        this.repaint();
    }
    ArrayList<Airport> Airports;
    ArrayList<Airport> flightplan = new ArrayList<>();// airports;

    ArrayList<Airplane> Airplanes;
    Airplane Ai = new Airplane();
    JButton jbtSubmit = new JButton("Submit");
    DataBaseManager DB = new DataBaseManager("./src/dbDir/airports.txt", "./src/dbDir/airplanes.txt");
    flightPathLeftPanel CenterPanel = new flightPathLeftPanel(Airports, flightplan);

    public JButton jbtEditAirplane = new JButton("Edit plan");
    FlightPlanScene() {
        jbtEditAirplane.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(CenterPanel);
                CenterPanel = new flightPathLeftPanel(Airports, flightplan);
                add(CenterPanel);
                validate();
                repaint();
            }
        });

        jbtSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
             // send flight plan to flight planing algorithm



             //take the return of the flight plan and display the information of the flight plan
                JPanel flightPanthPanel = new JPanel();
                JPanel holderPanel = new JPanel();
                holderPanel.setLayout(new GridLayout(1, flightplan.size()));
                ArrayList<Airport> selctionList = DB.searchAirports("", Ai);
                for(int i = 1; i < flightplan.size() ; i++){
                    flightPlan plan = new flightPlan(selctionList,flightplan.get(i-1), flightplan.get(i)  ,Ai);

                    for (AirportInfo info : plan.optimalPath) {
                        if (info == plan.optimalPath.get(plan.optimalPath.size()-1)){
                            continue;
                        }
                        JPanel panel = new JPanel();
                        panel.setLayout(new GridLayout(6, 1));
                        panel.add(new JLabel("Current Airport: " + info.thisAirport.CAOid + " " + info.thisAirport.APTname + " " + info.thisAirport.freq + " " + info.thisAirport.APRTlatitude + " " + info.thisAirport.APRTlongitude));
                        panel.add(new JLabel("Destination Airport: " + info.nextAirport.CAOid + " " + info.nextAirport.APTname + " " + info.nextAirport.freq + " " + info.nextAirport.APRTlatitude + " " + info.nextAirport.APRTlongitude));
                        panel.add(new JLabel("Leg Distance: " + String.valueOf(info.distance) + " km"));
                        panel.add(new JLabel("Heading of Leg: " + String.valueOf(info.Heading) + " degrees"));
                        panel.add(new JLabel("Fuel Used in Leg: " + String.valueOf(info.fuelCost) + " L"));
                        panel.add(new JLabel("Expected Time of Leg: " + String.valueOf(info.timeCost) + " hours"));


                        holderPanel.add(panel);
                    }
                }

                JScrollPane scrollPane = new JScrollPane(holderPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                flightPanthPanel.setLayout(new BorderLayout());
                flightPanthPanel.add(scrollPane, BorderLayout.CENTER);

                removeAll();
                add(flightPanthPanel,BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });


        DB.readAirports();
        Airports = DB.aprts;
        DB.readAirplanes();
        Airplanes = DB.aplanes;

        setLayout(new BorderLayout());
        right = new flightPathRightPanel(Airports, flightplan, DB);
        right.Airplane = Ai;
        add(right, BorderLayout.EAST);
        left = new FlightPathAirPlaneSelector(Airplanes, Ai, DB);
        add(CenterPanel, BorderLayout.CENTER);
        add(jbtSubmit, BorderLayout.WEST);
        add(left, BorderLayout.SOUTH);
        repaint();

    };

}
