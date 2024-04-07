import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class addAirplanePanel extends JPanel{
	//Gui Elements
	//Two parents panels, panInput and panBtm
	//panInput holds the input elemetns and panBtm holds the Add btn and Error label
	private JPanel panInput = new JPanel();
	
	private JPanel panMake = new JPanel();
	private JLabel lblMake = new JLabel("Please enter the Make");
	private JTextField txtMake = new JTextField("Make");
	private MakeVerifier vMake = new MakeVerifier();
	
	private JPanel panModel = new JPanel();
	private JLabel lblModel = new JLabel("Please enter the Model");
	private JTextField txtModel = new JTextField("Model");
	private ModelVerifier vModel = new ModelVerifier();
	
	private JPanel panType = new JPanel();
	private JLabel lblType = new JLabel("Please select the type of plane");
	private ButtonGroup bg = new ButtonGroup();
	private JRadioButton rbtnType1 = new JRadioButton("Prop");
	private JRadioButton rbtnType2= new JRadioButton("Turbo-prop");
	private JRadioButton rbtnType3 = new JRadioButton("Jet");
	private TypeVerifier vType1 = new TypeVerifier();
	private TypeVerifier vType2 = new TypeVerifier();
	private TypeVerifier vType3 = new TypeVerifier();
	
	private JPanel panCap = new JPanel();
	private JLabel lblCap = new JLabel("Please enter Fuel Capacity (Liters)");
	private JTextField txtCap = new JTextField("Capacity");
	private CapVerifier vCap = new CapVerifier();
	
	private JPanel panBurn = new JPanel();
	private JLabel lblBurn = new JLabel("Please enter fuel burn rate at cruise (Liters/Hour)");
	private JTextField txtBurn = new JTextField("Burn Rate");
	private BurnVerifier vBurn = new BurnVerifier();
	
	private JPanel panSpeed = new JPanel();
	private JLabel lblSpeed = new JLabel("Please enter cruise speed (knots)");
	private JTextField txtSpeed = new JTextField("Speed");
	private SpeedVerifier vSpeed = new SpeedVerifier();
	
	private JPanel panBtm = new JPanel();
	private JButton btnAdd = new JButton("Add");
	private JLabel lblError = new JLabel("Secret Error unlocked; +10 pnts");
	
	//Set for creation of new Airplane object
	private String airplaneMake;
	private String airplaneModel;
	private String airplaneType;
	private int airplaneFuelCap;
	private int airplaneFuelUse;
	private int airplaneSpeed;
	
	public addAirplanePanel() {
		setLayout(new BorderLayout());
		panMake.add(lblMake);
		panMake.add(txtMake);
		panInput.add(panMake);
		
		panModel.add(lblModel);
		panModel.add(txtModel);
		panInput.add(panModel);
		
		panType.add(lblType);
		bg.add(rbtnType1);
		bg.add(rbtnType2);
		bg.add(rbtnType3);
		panType.add(rbtnType1);
		panType.add(rbtnType2);
		panType.add(rbtnType3);
		panInput.add(panType);
		
		
		panCap.add(lblCap);
		panCap.add(txtCap);
		panInput.add(panCap);
		
		panBurn.add(lblBurn);
		panBurn.add(txtBurn);
		panInput.add(panBurn);
		
		panSpeed.add(lblSpeed);
		panSpeed.add(txtSpeed);
		panInput.add(panSpeed);
		
		panInput.setLayout(new GridLayout(6,1,5,5));
		add(panInput,BorderLayout.CENTER);
		
		
		panBtm.add(btnAdd);
		lblError.setVisible(false);
		panBtm.add(lblError);
		add(panBtm,BorderLayout.SOUTH);
		
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("add");
				vMake.verify(txtMake);
				
				if (vMake.verify(txtMake)) {
					if(vModel.verify(txtModel)) {
						if(vType1.verify(rbtnType1)) {
							if(vType2.verify(rbtnType2)) {
								if(vType3.verify(rbtnType3)) {
									if(vCap.verify(txtCap)) {
										if(vBurn.verify(txtBurn)) {
											if(vSpeed.verify(txtBurn)) {
												System.out.println("Verified");
											}
										}
									}
								}
							}
						}
					}
				}
				
			}
		});
	}
	class MakeVerifier extends InputVerifier{
		public boolean verify(JComponent input) {
			try {
				
			}
			catch(Exception e) {
				lblError.setText(e.getMessage());
				lblError.setVisible(true);
			}
			return false;
		}
	}
	class ModelVerifier extends InputVerifier{
		public boolean verify(JComponent input) {
			try {
				
			}
			catch(Exception e) {
				lblError.setText(e.getMessage());
				lblError.setVisible(true);
			}
			return false;
		}
	}
	class TypeVerifier extends InputVerifier{
		public boolean verify(JComponent input) {
			try {
				
			}
			catch(Exception e) {
				lblError.setText(e.getMessage());
				lblError.setVisible(true);
			}
			return false;
		}
	}
	class CapVerifier extends InputVerifier{
		public boolean verify(JComponent input) {
			try {
				
			}
			catch(Exception e) {
				lblError.setText(e.getMessage());
				lblError.setVisible(true);
			}
			return false;
		}
	}
	class BurnVerifier extends InputVerifier{
		public boolean verify(JComponent input) {
			try {
				
			}
			catch(Exception e) {
				lblError.setText(e.getMessage());
				lblError.setVisible(true);
			}
			return false;
		}
	}
	class SpeedVerifier extends InputVerifier{
		public boolean verify(JComponent input) {
			try {
				
			}
			catch(Exception e) {
				lblError.setText(e.getMessage());
				lblError.setVisible(true);
			}
			return false;
		}
	}
	

}