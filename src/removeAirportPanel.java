import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.lang.*;
public class removeAirportPanel extends JPanel {
    public ArrayList<Airport> airportsSearch;
    public JPanel results;
    public Airport selectedAirport;
    public removeAirportPanel() {
        setLayout(null);
        JLabel label = new JLabel("Remove Airport");
        label.setBounds(10, 10, 100,25);

        JTextField searchbar = new JTextField("Search");
        JLabel searchWarning = new JLabel("Capital sensitive, ICAO or Name only, partial search is supported");
        searchWarning.setBounds(10, 70, 400, 25);
        results = new JPanel();

        searchbar.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                searchbar.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                searchbar.setText("Search");
            }
        });
        searchbar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchbar.getText();
                DataBaseManager db = new DataBaseManager("src/dbDir/airports.txt", "src/dbDir/airplanes.txt");
                ArrayList<Airport> aports = new ArrayList<>();
                airportsSearch = db.searchAirports(searchText);
                results.removeAll();
                results.setLayout(new GridLayout(aports.size(), 1, 5, 5));
                for (Airport aa: airportsSearch){
                    JButton airportLabel = new JButton(aa.forPrint());
                    airportLabel.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Set the selected airport to the airport associated with the button
                            selectedAirport = aa;
                            int response = JOptionPane.showConfirmDialog(null,
                                    "Do you want to remove the airport: \n" + selectedAirport.forPrint() + "?",
                                    "Confirm",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE);

                            // If the user clicked "Yes", remove the airport from the database
                            if (response == JOptionPane.YES_OPTION) {
                                db.removeAirport(selectedAirport);
                                System.out.println(selectedAirport.forPrint() + " has been removed");
                                results.remove(airportLabel);
                                results.revalidate();
                                results.repaint();
                            }
                        }
                    });
                    results.add(airportLabel);
                }
                getParent().revalidate();
                getParent().repaint();
            }
        });

        JScrollPane scrollPane = new JScrollPane(results, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(10, 100, 1000, 100);
        searchbar.setBounds(10, 40, 150, 25);
        add(scrollPane);
        add(label);
        add(searchWarning);
        add(searchbar);
    }

    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(new removeAirportPanel());
        frame.setVisible(true);
    }
}
