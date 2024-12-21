package ui;

import Engine.AnalyzerEngine;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LandingPage extends JFrame {
    private JPanel panel;
    private JButton Generate;
    private JComboBox<String> comboBox1;
    private JTextArea topSongTA;
    private JTextArea analyzedTA;
    private JTextArea topArtistTA;
    private JTextPane topSongByArtist;
    private JComboBox artistCombo;
    private AnalyzerEngine analyzerEngine = new AnalyzerEngine();

    public LandingPage(){
        // housekeeping
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Better wrapped");
        setContentPane(panel);
        pack();

        // Populate the genre combo box
        populateYearComboBox();

        // Set the frame location to the center of the screen
        setLocationRelativeTo(null);

        // add on click event for the button
        Generate.addActionListener(e -> {onClick();});

        onClick();
        isVisible();
    }

    public void mouseReleased(MouseEvent e) {
        if (topArtistTA.getSelectedText() != null) { // See if they selected something
            String s = topArtistTA.getSelectedText();
            System.out.println(s);
            // Do work with String s
        }
    }
    public void onClick(){
        String date = comboBox1.getSelectedItem().toString() + "-12-31T23:59:59Z";
        int year = Integer.parseInt(comboBox1.getSelectedItem().toString()) + 1;
        String afterDate = String.valueOf(year) + "-12-31T23:59:59Z";

        System.out.println("analyzing between dates: " + date + " " + afterDate);

        analyzerEngine.init(date, afterDate);

        analyzerEngine.topSongsByYear();
        analyzerEngine.topArtistsByYear();
//        analyzerEngine.findTopNLongestGaps();

        writeData();
    }

    private void writeData(){
        // reset jtextarea
        topSongTA.setText(" ");
        topArtistTA.setText(" ");
        analyzedTA.setText("");

        // add top songs
        StringBuilder list = new StringBuilder();
        list.append("Top songs of ").append(comboBox1.getSelectedItem().toString()).append( ":\n");

        for (String s : analyzerEngine.topSongs){
            list.append(s).append("\n").append("\n");
        }
        topSongTA.setText(list.toString());

        // add artist info
        list = new StringBuilder();
        list.append("Top artists of ").append(comboBox1.getSelectedItem().toString()).append( ":\n");

        for (String s : analyzerEngine.topArtists){
            list.append(s).append("\n").append("\n");
        }
        topArtistTA.setText(list.toString());

        // add analyzer data
        analyzedTA.append(analyzerEngine.number_of_songs + " songs analyzed");
        topSongTA.setWrapStyleWord(true);
    }
    public void populateYearComboBox(){
        // Get the combo box model
        DefaultComboBoxModel<String> comboBoxModel = (DefaultComboBoxModel<String>) comboBox1.getModel();

        // Add genre values to the combo box model
        for (int x = 2023; x >= 2014; x--) {
            comboBoxModel.addElement(String.valueOf(x));
        }
    }
}
