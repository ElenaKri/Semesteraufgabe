package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import controller.*;

@SuppressWarnings("serial")
public class VierAntwortenFrageBearbeiteDialog extends JDialog implements ActionListener {
    Fragencontainer container = Fragencontainer.instance();
    JTextField vorlesung = new JTextField("", 20);
    JTextField thema = new JTextField("", 20);
    JTextField frage = new JTextField("", 20);
    JTextField richtigeAntwort = new JTextField("", 20);
    JTextField falscheAntwort1 = new JTextField("", 20);
    JTextField falscheAntwort2 = new JTextField("", 20);
    JTextField falscheAntwort3 = new JTextField("", 20);
    JButton ok;
    JButton abbrechen;
    Frage alteFrage;
    
    
    public VierAntwortenFrageBearbeiteDialog(VierAntwortenFrage frage) {
	this.setModal(true);
	this.setLayout(new BorderLayout());
	this.alteFrage = frage;
	
	JPanel north = new JPanel();
	north.setLayout(new FlowLayout());
	JLabel vorl = new JLabel("Vorlesung: ");
	north.add(vorl);
	north.add(vorlesung);
	JLabel them = new JLabel("Thema: ");
	north.add(them);
	north.add(thema);
	this.add(north, BorderLayout.NORTH);
	
	JPanel center = new JPanel();
	center.setLayout(new BorderLayout());
	
	JPanel centerNorth = new JPanel();
	centerNorth.setLayout(new FlowLayout());
	JLabel fr = new JLabel("Frage: ");
	centerNorth.add(fr);
	centerNorth.add(this.frage);
	center.add(centerNorth, BorderLayout.NORTH);
	
	JPanel centerCenter = new JPanel();
	centerCenter.setLayout(new GridLayout(2, 8));
	JLabel ra = new JLabel("richtige Antwort: ");
	centerCenter.add(ra);
	centerCenter.add(richtigeAntwort);
	JLabel f1 = new JLabel("falsche Antwort: ");
	centerCenter.add(f1);
	centerCenter.add(falscheAntwort1);
	JLabel f2 = new JLabel("falsche Antwort: ");
	centerCenter.add(f2);
	centerCenter.add(falscheAntwort2);
	JLabel f3 = new JLabel("falsche Antwort: ");
	centerCenter.add(f3);
	centerCenter.add(falscheAntwort3);
	center.add(centerCenter);
	
	this.add(center, BorderLayout.CENTER);
	
	JPanel south = new JPanel();
	south.setLayout(new FlowLayout());
	ok = new JButton("OK");
	ok.addActionListener(this);
	south.add(ok);
	abbrechen = new JButton("Abbrechen");
	abbrechen.addActionListener(this);
	south.add(abbrechen);
	this.add(south, BorderLayout.SOUTH);
	
	if (frage == null) {
	    this.setTitle("neue VierAntwortenFrage");
	} else {
	    this.setTitle("VierAntwortenFrage bearbeiten");
	    this.vorlesung.setText(frage.getVorlesung());
	    this.thema.setText(frage.getThema());
	    this.frage.setText(frage.getFrage());
	    this.richtigeAntwort.setText(frage.getAntworten()[0]);
	    this.falscheAntwort1.setText(frage.getAntworten()[1]);
	    this.falscheAntwort2.setText(frage.getAntworten()[2]);
	    this.falscheAntwort3.setText(frage.getAntworten()[3]);
	}
	this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	this.pack();
	this.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
	if (e.getSource().equals(ok)) {
	    if (this.vorlesung.getText() == null || this.vorlesung.getText().equals("")) {
		JOptionPane.showMessageDialog(null, "Der Titel der Vorlesung darf nicht leer sein.");
	    } else if (this.thema.getText() == null || this.thema.getText().equals("")) {
		JOptionPane.showMessageDialog(null, "Das Thema darf nicht leer sein.");
	    } else if (this.frage.getText() == null || this.frage.getText().equals("")) {
		JOptionPane.showMessageDialog(null, "Die Frage darf nicht leer sein.");
	    } else if (this.richtigeAntwort.getText() == null || this.richtigeAntwort.getText().equals("")) {
		JOptionPane.showMessageDialog(null, "Die richtige Antwort darf nicht leer sein.");
	    } else if (this.falscheAntwort1.getText() == null || this.falscheAntwort1.getText().equals("")
		    || this.falscheAntwort2.getText() == null || this.falscheAntwort2.getText().equals("")
		    || this.falscheAntwort3.getText() == null || this.falscheAntwort3.getText().equals("")) {
		JOptionPane.showMessageDialog(null, "Die falschen Antworten d�rfen nicht leer sein.");
	    } else {
		VierAntwortenFrage frage = new VierAntwortenFrage(this.vorlesung.getText(), 
			this.thema.getText(), this.frage.getText(), this.richtigeAntwort.getText(), 
			this.falscheAntwort1.getText(), this.falscheAntwort2.getText(), 
			this.falscheAntwort3.getText());
		container.linkFrage(frage);
		container.unlinkFrage(alteFrage);
		JOptionPane.showMessageDialog(null, "Die Frage wurde hinzugef�gt.");
		this.dispose();
	    }
	} else if (e.getSource().equals(abbrechen)) {
	    this.dispose();
	}
    }
}