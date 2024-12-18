package ui;

import Engine.AnalyzerEngine;
import javax.swing.*;

public class LandingPage extends JFrame {
    private JPanel panel;
    private JButton Generate;
    private JComboBox<String> comboBox1;
    private AnalyzerEngine analyzerEngine = new AnalyzerEngine();

    public LandingPage(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Landing Page");
        setContentPane(panel);
        pack();

        // Populate the genre combo box
        populateYearComboBox();

        // Set the frame location to the center of the screen
        setLocationRelativeTo(null);

        Generate.addActionListener(e -> {onClick();});

        isVisible();
    }

    public void onClick(){
        System.out.println(Generate.getText());
        String date = comboBox1.getSelectedItem().toString() + "-12-31T23:59:59Z";
        analyzerEngine.init(date);

        analyzerEngine.topSongsByYear();
        analyzerEngine.topArtistsByYear();
        analyzerEngine.findTopNLongestGaps();
    }

    public void populateYearComboBox(){
        // Get the combo box model
        DefaultComboBoxModel<String> comboBoxModel = (DefaultComboBoxModel<String>) comboBox1.getModel();

        // Add genre values to the combo box model
        for (int x = 2014; x <= 2024; x++) {
            comboBoxModel.addElement(String.valueOf(x));
        }
    }
}
