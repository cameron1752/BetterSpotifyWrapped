package ui;

import Engine.AnalyzerEngine;
import Engine.SongData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;

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

        // add on action event for the combo
        artistCombo.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                writeData();
            }
        });

        // populate top artist combo box
        addTopNArtistCombo();
        // call generate button on load for initial analyzation
        onClick();
        // lets see it baby
        isVisible();
    }

    public void onClick(){
        String date = comboBox1.getSelectedItem().toString() + "-12-31T23:59:59Z";
        int year = Integer.parseInt(comboBox1.getSelectedItem().toString()) + 1;
        String afterDate = String.valueOf(year) + "-12-31T23:59:59Z";

        System.out.println("analyzing between dates: " + date + " " + afterDate);

        analyzerEngine.init(date, afterDate);

        analyzerEngine.topSongsByYear();
        analyzerEngine.topArtistsByYear();

        addTopNArtistCombo();
//        analyzerEngine.findTopNLongestGaps();

        writeData();
    }

    private void writeData(){
        /*
        * reset the text areas for new data
        */
        topSongTA.setText("");
        topArtistTA.setText("");
        analyzedTA.setText("");

        /*
        * Add top songs of [YEAR] to left hand side
        */
        StringBuilder list = new StringBuilder();
        list.append("Top songs of ").append(comboBox1.getSelectedItem().toString()).append( ":\n");

        for (String s : analyzerEngine.topSongs){
            list.append(s).append("\n").append("\n");
        }
        topSongTA.setText(list.toString());


        /*
        * Add top artist of [YEAR] to right hand side
        */
        list = new StringBuilder();
        list.append("Top artists of ").append(comboBox1.getSelectedItem().toString()).append( ":\n");

        for (String s : analyzerEngine.topArtists){
            list.append(s).append("\n").append("\n");
        }
        topArtistTA.setText(list.toString());

        /*
        * Housekeeping stuff for word wrap and stats at the top
        */
        analyzedTA.append(analyzerEngine.number_of_songs + " songs analyzed");
        topSongTA.setWrapStyleWord(true);

        /*
        * Add top N songs of [ARTIST] to center stage
        */
        list = new StringBuilder();
        list.append("Top songs of ").append(artistCombo.getSelectedItem().toString()).append( ":\n");

        int count = 0;
        // get top songs per artist
        for (SongData s : analyzerEngine.topNArtists){
            if (s.getArtist().equals(artistCombo.getSelectedItem().toString())){
                list.append(++count).append(". ").append(s.getTitle()).append("\n").append("\n");
            }
        }
        topSongByArtist.setText(list.toString());
    }

    public void populateYearComboBox(){
        // Get the combo box model
        DefaultComboBoxModel<String> comboBoxModel = (DefaultComboBoxModel<String>) comboBox1.getModel();

        // Add genre values to the combo box model
        for (int x = 2023; x >= 2014; x--) {
            comboBoxModel.addElement(String.valueOf(x));
        }
    }

    public void addTopNArtistCombo(){
        DefaultComboBoxModel<String> comboBoxModel = (DefaultComboBoxModel<String>) artistCombo.getModel();
        comboBoxModel.removeAllElements();

        HashSet<String> set = new HashSet<>();

        for (SongData s : analyzerEngine.topNArtists){
            set.add(s.getArtist());
        }

        for (String s : set){
            comboBoxModel.addElement(s);
        }
    }
}
