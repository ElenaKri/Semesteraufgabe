package gui;

import javax.swing.*;

import controller.Einstellungen;

import java.awt.*;
//import java.util.concurrent.Flow;

//import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

@SuppressWarnings("serial")
public class GuiStatistikStandard extends JFrame {
    private int punktzahl;
    private int aktuelleFrage;
    private int maxFrage;
    
    private JLabel anzeigePunkte;
    private JLabel anzeigeAktuelleFrage;
    private JLabel anzeigeMaxFrage;
    
    public GuiStatistikStandard(String title) {

        super(title);
        //this.setSize(200, 100);
        this.setDefaultCloseOperation((DISPOSE_ON_CLOSE));
        Color officialColor = GuiFarbauswahl.officialColor;
        this.getContentPane().setBackground(officialColor);
        setLayout(new GridLayout(2, 1));
        Font officialFont = Einstellungen.instance().getOfficialFont();

        JPanel frameFrage = new JPanel(new FlowLayout());
        frameFrage.setBackground(officialColor);
        JPanel framePunktzahl = new JPanel(new FlowLayout());
        framePunktzahl.setBackground(officialColor);
        

        JLabel stringPunkte = new JLabel("Punktzahl: ");
        stringPunkte.setFont(officialFont);
        anzeigePunkte = new JLabel(String.valueOf(punktzahl));
        anzeigePunkte.setFont(officialFont);
        JLabel stringFrage = new JLabel("Frage ");
        stringFrage.setFont(officialFont);
        anzeigeAktuelleFrage = new JLabel(String.valueOf(aktuelleFrage));
        anzeigeAktuelleFrage.setFont(officialFont);
        JLabel trennerFrage = new JLabel(" / ");
        trennerFrage.setFont(officialFont);
        anzeigeMaxFrage = new JLabel(String.valueOf(maxFrage));
        anzeigeMaxFrage.setFont(officialFont);
        
        this.setAktuelleFrage(0);

        framePunktzahl.add(stringPunkte);
        framePunktzahl.add(anzeigePunkte);
        frameFrage.add(stringFrage);
        frameFrage.add(anzeigeAktuelleFrage);
        frameFrage.add(trennerFrage);
        frameFrage.add(anzeigeMaxFrage);

        this.add(framePunktzahl);
        this.add(frameFrage);

        pack();
        this.setLocationRelativeTo(null);
        setVisible(true);
    }

    public int getPunktzahl() {
        return punktzahl;
    }

    public void setPunktzahl(int punktzahl) {
        this.punktzahl = punktzahl;
        this.anzeigePunkte.setText(punktzahl + "");
    }

    public int getAktuelleFrage() {
        return aktuelleFrage;
    }

    public void setAktuelleFrage(int aktuelleFrage) {
        this.aktuelleFrage = aktuelleFrage;
        this.anzeigeAktuelleFrage.setText(aktuelleFrage + "");
    }

    public int getMaxFrage() {
        return maxFrage;
    }

    public void setMaxFrage(int maxFrage) {
        this.maxFrage = maxFrage;
        this.anzeigeMaxFrage.setText(maxFrage + "");
    }
    
    
}
