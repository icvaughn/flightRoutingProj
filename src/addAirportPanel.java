import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class addAirportPanel extends JPanel{
	private JPanel panIcao = new JPanel();
	private JLabel lblIcao = new JLabel("ICAO:");
	private JTextField txtIcao = new JTextField("Please enter ICAO");
	
	private JPanel panName = new JPanel();
	private JLabel lblName = new JLabel("Name of the Airport:");
	private JTextField txtName = new JTextField("Please enter the Name");
	
	private JPanel panLatLong = new JPanel();
	private JLabel lblLatLong = new JLabel("Please enter latitude, then enter longitude");
	private JTextField txtLat = new JTextField("Latitude");
	private JTextField txtLong = new JTextField("Longitude");
	
	private JPanel panComm = new JPanel();
	private JLabel lblComm = new JLabel("Please enter radio freqency of comms");
	private JTextField txtComm = new JTextField("Comms Frequency");
	
	private JPanel panFuel = new JPanel();
	private JLabel lblFuel = new JLabel("Please enter fuel type (AVGAS, JA-a, or both");
	//private ButtonGroup btnFuel = new ButtonGroup();
	private JRadioButton rdFuel1 = new JRadioButton("AVGAS");
	private JRadioButton rdFuel2 = new JRadioButton("JA-a");
	
	JButton jbtAdd = new JButton("Add");
	JPanel panAdd = new JPanel();
	
	private String airportName;
	
	public addAirportPanel() {
		setLayout(new GridLayout(7,1,5,5));
		panIcao.add(lblIcao);
		panIcao.add(txtIcao);
		add(panIcao);
		
		panName.add(lblName);
		panName.add(txtName);
		add(panName);
		
		panLatLong.add(lblLatLong);
		panLatLong.add(txtLat);
		panLatLong.add(txtLong);
		add(panLatLong);
		
		panComm.add(lblComm);
		panComm.add(txtComm);
		add(panComm);
		
		panFuel.add(lblFuel);
		//btnFuel.add(rdFuel1); 
		//btnFuel.add(rdFuel2);
		panFuel.add(rdFuel1);
		panFuel.add(rdFuel2);
		add(panFuel);
		
		panAdd.add(jbtAdd);
		add(panAdd);
		
		jbtAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(txtName.getText());

            }
        });
		
	}
	

}
