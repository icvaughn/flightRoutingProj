import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Scenes extends JFrame {

    JPanel currentScene = null;
    private JButton jbtMakePlan = new JButton("Make Plan");
    private JButton jbtEditAirport = new JButton("Edit airport");
    private JButton jbtEditAirplane = new JButton("Edit airplane");
    private JButton jbtExit = new JButton("Exit");
    private JButton jbtBack = new JButton("Back");
    private JButton jbtAddAirport = new JButton("Add airport");
    private JButton jbtAddAirplane = new JButton("Add airplane");
    private JButton jbtRmvAirport = new JButton("Remove airport");
    private JButton jbtRmvAirplane = new JButton("Remove airplane");
    private JTextArea txtDontUseThisLol = new JTextArea("THIS SOFTWARE IS NOT TO BE USED FOR FLIGHT PLANNING OR NAVIGATIONAL PURPOSE");
    static ArrayList<Airport> Airports;

    ArrayList<Airport> flightplan;

    public Scenes() {

        // create airport list



        // Create Panel jpButtons to hold two Buttons "<=" and "right =>"
        JPanel jpButtons = new JPanel();
        jpButtons.add(jbtMakePlan);
        jpButtons.add(jbtEditAirport);
        jpButtons.add(jbtEditAirplane);
        jpButtons.add(jbtAddAirplane);
        jpButtons.add(jbtAddAirport);
        jpButtons.add(jbtRmvAirport);
        jpButtons.add(jbtRmvAirplane);
        jpButtons.add(jbtExit);

        JPanel jpBackButton = new JPanel();
        jpBackButton.add(jbtBack);

        currentScene = jpButtons;
        setLayout(new BorderLayout());
        add(currentScene, BorderLayout.CENTER);
        
        add(txtDontUseThisLol, BorderLayout.SOUTH);
        txtDontUseThisLol.setLineWrap(true);
        txtDontUseThisLol.setWrapStyleWord(true);
        
        ;
        jbtAddAirplane.addActionListener(new ActionListener() {	//new additions
            public void actionPerformed(ActionEvent e) {
                remove(currentScene);
                currentScene = new addAirplanePanel();
                add(currentScene, BorderLayout.CENTER);
                add(jpBackButton, BorderLayout.NORTH);
                revalidate();
                repaint();
            }
        });
        jbtAddAirport.addActionListener(new ActionListener() { //new additions
            public void actionPerformed(ActionEvent e) {
                remove(currentScene);
                currentScene = new addAirportPanel();
                add(currentScene, BorderLayout.CENTER);
                add(jpBackButton, BorderLayout.NORTH);
                revalidate();
                repaint();
            }
        });
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
                currentScene = new modifyAirportPanel();
                add(currentScene, BorderLayout.CENTER);
                add(jpBackButton, BorderLayout.NORTH);
                revalidate();
                repaint();
            }
        });
        jbtEditAirplane.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(currentScene);
                currentScene = new modifyAirplanePanel();
                add(currentScene, BorderLayout.CENTER);
                add(jpBackButton, BorderLayout.NORTH);
                revalidate();
                repaint();
            }
        });
        jbtRmvAirport.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		remove(currentScene);
        		currentScene = new removeAirportPanel();
        		add(currentScene,BorderLayout.CENTER);
        		add(jpBackButton, BorderLayout.NORTH);
        		revalidate();
            	repaint();
        	}
        });
        jbtRmvAirplane.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		remove(currentScene);
        		currentScene = new removeAirplanePanel();
        		add(currentScene,BorderLayout.CENTER);
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
             // send flight plan to flight planing algorithm//


             //take the return of the flight plan and display the information of the flight plan


            }
        });

        DataBaseManager DB = new DataBaseManager("./src/dbDir/airports.txt", "./src/dbDir/airplanes.txt");
        DB.readAirports();
        Airports = DB.aprts;
        DB.readAirplanes();
        Airplanes = DB.aplanes;

        setLayout(new BorderLayout());
        right = new flightPathRightPanel(Airports, flightplan, DB);
        add(right, BorderLayout.EAST);
        left = new FlightPathAirPlaneSelector(Airplanes, Ai, DB);
        add(CenterPanel, BorderLayout.CENTER);
        add(jbtSubmit, BorderLayout.WEST);
        add(left, BorderLayout.SOUTH);
        repaint();

    };

}
