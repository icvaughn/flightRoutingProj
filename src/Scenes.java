import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class Scenes extends JFrame {

    JPanel currentScene = null;
    private JButton jbtMakePlan = new JButton("Make Plan");
    private JButton jbtEditAirport = new JButton("Edit airport");
    private JButton jbtEditAirplane = new JButton("Edit airplane");
    private JButton jbtExit = new JButton("Exit");
    private JButton jbtBack = new JButton("Back");

    static ArrayList<Airport> Airports;

    ArrayList<Airport> flightplan;

    public Scenes() {

        // create airport list


        // Create Panel jpButtons to hold two Buttons "<=" and "right =>"
        JPanel jpButtons = new JPanel();
        jpButtons.add(jbtMakePlan);
        jpButtons.add(jbtEditAirport);
        jpButtons.add(jbtEditAirplane);
        jpButtons.add(jbtExit);

        JPanel jpBackButton = new JPanel();
        jpBackButton.add(jbtBack);

        currentScene = jpButtons;
        setLayout(new BorderLayout());
        add(currentScene, BorderLayout.CENTER);
        ;

        jbtMakePlan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(currentScene);
                currentScene = new FlightPlanScene();
                add(currentScene, BorderLayout.CENTER);
                add(jpBackButton, BorderLayout.NORTH);
                revalidate();
                repaint();

            }
        });
        jbtEditAirport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(currentScene);
                currentScene = new flightPathRightPanel(Airports, flightplan);
                add(currentScene, BorderLayout.CENTER);
                add(jpBackButton, BorderLayout.NORTH);
                revalidate();
                repaint();
            }
        });
        jbtEditAirplane.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(currentScene);
                currentScene = new AiportViewer(Airports.get(0));
                JButton addbutton = new JButton("+");
                currentScene.add(addbutton);
                add(currentScene, BorderLayout.CENTER);
                add(jpBackButton, BorderLayout.NORTH);
                revalidate();
                repaint();
            }
        });
        jbtExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();

            }
        });

        jbtBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(currentScene);
                remove(jpBackButton);
                currentScene = jpButtons;
                add(currentScene);
                repaint();
            }
        });

        pack();
    }

    public static void main(String[] args) {
        Scenes frame = new Scenes();
        frame.setTitle("TEST1NO5IsaacVaughn");
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 320);
        frame.setVisible(true);
    }

}

// The class for drawing arcs on a panel
class FlightPlanScene extends JPanel {
    public void reInit(){
        remove(leftPanel);
        leftPanel = new flightPathLeftPanel(Airports, flightplan);
        add(leftPanel);
        this.validate();
        this.repaint();
    }
    ArrayList<Airport> Airports;
    ArrayList<Airport> flightplan = new ArrayList<>();// airports;
    JButton addButton = new JButton("+");

    flightPathLeftPanel leftPanel = new flightPathLeftPanel(Airports, flightplan);

    public JButton jbtEditAirplane = new JButton("Edit plan");
    FlightPlanScene() {
        jbtEditAirplane.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(leftPanel);
                leftPanel = new flightPathLeftPanel(Airports, flightplan);
                add(leftPanel);
                validate();
                repaint();
            }
        });



        // String[] gas = {"JA-A", "AVGAS"} ;
        // airport west = new airport("KEN", "Kenidy Airport", gas, 34.369850,
        // -80.084534, 100.0);
        // airport east = new airport("CEN", "Cenidy Airport", gas, 134.369850,
        // -80.084534, 100.0);
        // airport mid = new airport("MEN", "Menidy Airport", gas, 84.369850,
        // -80.084534, 100.0);
        // airport[] aprts = {west,east,mid};
        // airports = aprts;

        DataBaseManager DB = new DataBaseManager("./src/dbDir/airports.txt", "./src/dbDir/airplanes.txt");
        DB.readAirports();
        Airports = DB.aprts;

        addPropertyChangeListener("flightplan", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                //remove(leftPanel);
                leftPanel = new flightPathLeftPanel(Airports, flightplan);
                //add(leftPanel, BorderLayout.SOUTH);
                validate();
                repaint();

            }
        });

        setLayout(new BorderLayout());
        flightPathRightPanel right = new flightPathRightPanel(Airports, flightplan);
        add(right, BorderLayout.EAST);

        /*JButton addbutton = new JButton("+");

        addbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(flightplan.get(0).APTname);
                repaint();
            };
        });

        add(addbutton, BorderLayout.WEST);*/
        add( leftPanel, BorderLayout.CENTER);
        //add( jbtEditAirplane, BorderLayout.WEST);
        repaint();

    };

}
