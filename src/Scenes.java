import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Properties;

public class Scenes extends JFrame {

    JPanel currentSceane = null;
    private JButton jbtMakePlan = new JButton("Make Plan");
    private JButton jbtEditAirport = new JButton("Edit airport");
    private JButton jbtEditAirplane = new JButton("Edit airplane");
    private JButton jbtExit = new JButton("Exit");

    private JButton jbtBack = new JButton("Back");

    static ArrayList<airport> airports;

    ArrayList<airport> flightplan;

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

        currentSceane = jpButtons;
        setLayout(new BorderLayout());
        add(currentSceane, BorderLayout.CENTER);
        ;

        jbtMakePlan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(currentSceane);
                currentSceane = new FlightPlanSceane();
                add(currentSceane, BorderLayout.CENTER);
                add(jpBackButton, BorderLayout.NORTH);
                revalidate();
                repaint();

            }
        });
        jbtEditAirport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(currentSceane);
                currentSceane = new flightViewerRightPanle(airports, flightplan);
                add(currentSceane, BorderLayout.CENTER);
                add(jpBackButton, BorderLayout.NORTH);
                revalidate();
                repaint();
            }
        });
        jbtEditAirplane.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(currentSceane);
                currentSceane = new AiportViewer(airports.get(0));
                JButton addbutton = new JButton("+");
                currentSceane.add(addbutton);
                add(currentSceane, BorderLayout.CENTER);
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
                remove(currentSceane);
                remove(jpBackButton);
                currentSceane = jpButtons;
                add(currentSceane);
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
class FlightPlanSceane extends JPanel {
    String[] gas = { "JA-A", "AVGAS" };
    ArrayList<airport> airports;
    ArrayList<airport> flightplan = new ArrayList<airport>();// airports;
    JButton addButton = new JButton("+");

    flightPathBottomPanel bottomPanel = new flightPathBottomPanel(airports, flightplan);

    public JButton jbtEditAirplane = new JButton("Edit plan");
    FlightPlanSceane() {
        jbtEditAirplane.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(bottomPanel);
                bottomPanel = new flightPathBottomPanel(airports, flightplan);
                add(bottomPanel);
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

        db DB = new db("./src/dbDir/airports.txt", "./src/dbDir/airplanes.txt");
        DB.readAirports();
        airports = DB.aprts;

        addPropertyChangeListener("flightplan", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                remove(bottomPanel);
                bottomPanel = new flightPathBottomPanel(airports, flightplan);
                add(bottomPanel, BorderLayout.SOUTH);
                validate();
                repaint();

            }
        });

        setLayout(new BorderLayout());
        flightViewerRightPanle right = new flightViewerRightPanle(airports, flightplan);
        add(right, BorderLayout.EAST);

        JButton addbutton = new JButton("+");

        addbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(flightplan.get(0).APTname);
                repaint();
            };
        });

        add(addbutton, BorderLayout.WEST);
        add( bottomPanel, BorderLayout.SOUTH);
        add( jbtEditAirplane, BorderLayout.CENTER);
        repaint();

    };

}
