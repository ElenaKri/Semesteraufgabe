package gui;

import javax.swing.*;
import javax.swing.border.Border;

import controller.Einstellungen;
import controller.MuendlicheAntwortFrage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.util.concurrent.Flow;

@SuppressWarnings("serial")
public class GuiMuendlicheAntwortFrage extends GuiFrage implements ActionListener {

    private String loesungText;
    private String frageText;

    private JButton loesung = new JButton("Loesung anzeigen");
    private JButton exit = new JButton("Ende");
    private JButton frage = new JButton("Frage anzeigen");
    private JButton ja = new JButton("Ja");
    private JButton nein = new JButton("Nein");
    private JButton weiter = new JButton("Naechste Frage");

    private JLabel richtigeAntwort = new JLabel("Richtige Antwort?");

    private JTextArea frageFeld = new JTextArea(12, 20);

    private JPanel navigation;
    
    private SpielManager manager;
    private boolean richtig = false;
    private boolean antwortGegeben = false;

    public GuiMuendlicheAntwortFrage(String title, String frageT, String loesungT) {

        super(title);
        setAntwortGegeben(false);
        //this.setSize(500, 500);
        this.setDefaultCloseOperation((DO_NOTHING_ON_CLOSE));
        Color officialColor = GuiFarbauswahl.officialColor;
        this.getContentPane().setBackground(officialColor);
        
        Font officialFont = Einstellungen.instance().getOfficialFont();

        frageText = frageT;
        loesungText = loesungT;


        frageFeld = new JTextArea(frageText,12, 20);
        JScrollPane scrollFrageFeld = new JScrollPane(frageFeld);
        frageFeld.setEnabled(false);
        frageFeld.setDisabledTextColor(Color.black);
        frageFeld.setLineWrap(true);
        frageFeld.setWrapStyleWord(true);
        frageFeld.setFont(officialFont);

        this.add(scrollFrageFeld, BorderLayout.NORTH);


        JPanel south = new JPanel(new BorderLayout());
        south.setBackground(officialColor);
        this.add(south, BorderLayout.SOUTH);

        JPanel frageNavigation = new JPanel(new FlowLayout());
        frageNavigation.setBackground(officialColor);
        south.add(frageNavigation, BorderLayout.WEST);
        frageNavigation.add(loesung);
        frageNavigation.add(frage);
        loesung.addActionListener(this);
        frage.addActionListener(this);
        loesung.setFont(officialFont);
        frage.setFont(officialFont);

        JPanel navigation = new JPanel(new FlowLayout());
        navigation.setBackground(officialColor);
        south.add(navigation, BorderLayout.EAST);

        navigation.add(weiter);
        weiter.setFont(officialFont);
        navigation.add(exit);
        exit.setFont(officialFont);
        exit.addActionListener(this);


        JPanel loesungPanel = new JPanel(new FlowLayout());
        loesungPanel.setBackground(officialColor);
        south.add(loesungPanel, BorderLayout.NORTH);

        loesungPanel.add(richtigeAntwort);
        richtigeAntwort.setFont(officialFont);
        loesungPanel.add(ja);
        ja.setFont(officialFont);
        ja.addActionListener(this);
        loesungPanel.add(nein);
        nein.setFont(officialFont);
        nein.addActionListener(this);
        frage.setVisible(false);
        ja.setVisible(false);
        nein.setVisible(false);
        richtigeAntwort.setVisible(false);
        weiter.setVisible(false);
        weiter.addActionListener(this);

        this.setSize(600, 600);
        this.setLocationRelativeTo(null);
        setVisible(true);
    }

    public GuiMuendlicheAntwortFrage(MuendlicheAntwortFrage f, SpielManager manager) {
	this("Frage", f.getFrage(), f.getAntwort());
	this.manager = manager;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        // Die Quelle wird mit getSource() abgefragt und mit den
        // Buttons abgeglichen.
        if(e.getSource() == exit){
            manager.next(-1);
            new Hauptmenue("Hauptmenue");
            dispose();
        } else if(e.getSource() == loesung) {
            frageFeld.replaceRange(loesungText, 0, frageText.length());
            setAntwortGegeben(true);
            loesung.setVisible(false);
            richtigeAntwort.setVisible(true);
            ja.setVisible(true);
            nein.setVisible(true);
            frage.setVisible(true);
        } else if(e.getSource() == frage) {
            frageFeld.replaceRange(frageText, 0, loesungText.length());
            loesung.setVisible(true);
            frage.setVisible(false);
        } else if(e.getSource() == ja) {
            ja.setBackground(Color.green);
            ja.setEnabled(false);
            nein.setEnabled(false);
            weiter.setVisible(true);
            richtig = true;
        } else if(e.getSource() == nein) {
            nein.setBackground(Color.red);
            ja.setEnabled(false);
            nein.setEnabled(false);
            weiter.setVisible(true);
        } else if(e.getSource() == weiter) {
            if (richtig) {
        	manager.next(1);
            } else {
        	manager.next(0);
            }
            this.dispose();
        }
    }

    @Override
    public void showAnswer() {
        frageFeld.replaceRange(loesungText, 0, frageText.length());
        loesung.setVisible(false);
        richtigeAntwort.setVisible(true);
        ja.setVisible(true);
        nein.setVisible(true);
        setVisible(true);
        nein.setBackground(Color.red);
        ja.setEnabled(false);
        nein.setEnabled(false);
        weiter.setVisible(true);
    }

    public void setAntwortGegeben (boolean wert) {
        antwortGegeben = wert;
    }

    public boolean getAntwortGegeben () {
        return antwortGegeben;
    }

    public void deactivateAnswers() {
        loesung.setEnabled(false);
    }

    public void activateAnswers() {
        loesung.setEnabled(true);
    }

    public void weiterInvisible() {
        weiter.setVisible(false);
    }
}
