/** 
 * Container, um Fragen in einer ArrayList abzuspeichern
 * Dabei werden alle �nderungen durch die Standardverwaltungsoperationen nur in der Liste ge�ndert,
 * um diese in die Textdatei zu �bernehmen, muss die save()-Methode aufgerufen werden
 * TODO: Fehlerbehandlung, falls die Dateien nicht eingelesen werden k�nnen
 */

package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import model.*;

public class Fragencontainer implements Iterable<Frage> {
    private static Fragencontainer unique = null;
    private ArrayList<Frage> fragen;
    private String dateiname = ".\\src\\textdateien\\Fragen.txt";
    
    private Fragencontainer() {
	fragen = new ArrayList<Frage>();
	this.load();
	VorlesungenThemenContainer vtc = VorlesungenThemenContainer.instance();
	for (Frage f : fragen) {
	    vtc.addVorlesung(f.getVorlesung());
	    vtc.addThema(f.getThema(), f.getVorlesung());
	}
    }
    
    public static Fragencontainer instance() {
	if (unique == null) {
	    unique = new Fragencontainer();
	}
	return unique;
    }
    
    /**
     * f�gt die Frage der Datenstruktur und der Textdatei hinzu
     * @param f die hinzuzuf�gende Frage
	 * :)
     */
    public void linkFrage(Frage f) {
	//TODO Warum k�nnen Fragen mehrfach eingef�gt werden?
	if (!this.fragen.contains(f)) {
	    this.fragen.add(f);
	    this.save();
	    VorlesungenThemenContainer vtc = VorlesungenThemenContainer.instance();
	    vtc.addVorlesung(f.getVorlesung());
	    vtc.addThema(f.getThema(), f.getVorlesung());
	}
    }
    
    /**
     * entfernt die Frage aus der Datenstruktur und der Textdatei
     * @param f die zu entfernende Frage
     */
    public void unlinkFrage(Frage f) {
	this.fragen.remove(f);
	this.save();
    }
    
    /**
     * gibt eine Liste mit allen Fragen des Programms zur�ck
     * @return die Liste mit allen Fragen des Programms
     */
    public ArrayList<Frage> getFragen() {
	return this.fragen;
    }
    
    /**
     * gibt gefilterte Fragen zur�ck
     * @param vorlesungen alle Vorlesungen, aus denen Themen vorkommen sollen
     * falls vorlesungen null ist, werden alle Vorlesungen ausgew�hlt
     * @param themen alle Themen, zu denen Fragen vorkommen sollen
     * falls themen null ist, werden alle Themen ausgew�hlt
     * @param anzahl die gew�nschte Anzahl an Fragen
     * falls anzahl -1 ist, werden alle Fragen ausgew�hlt
     * @return
     */
    public ArrayList<Frage> getFragenGefiltert(ArrayList<String> vorlesungen, ArrayList<String> themen, int anzahl) {
	ArrayList<Frage> fragen = this.getFragen();
	Object[] fr;
	if (vorlesungen != null && themen != null) {
	    fr = fragen.stream()
		    .filter(f -> vorlesungen.contains(f.getVorlesung()))
		    .filter(f -> themen.contains(f.getThema()))
		    .toArray();
	} else if (vorlesungen == null && themen != null) {
	    fr = fragen.stream()
		    .filter(f -> themen.contains(f.getThema()))
		    .toArray();
	} else if (vorlesungen != null && themen == null) {
	    fr = fragen.stream()
		    .filter(f -> vorlesungen.contains(f.getVorlesung()))
		    .toArray();
	} else {
	    fr = fragen.stream()
		    .toArray();
	}
	fragen = new ArrayList<Frage>();
	if (anzahl > 0) {
	    if (anzahl >= fr.length) {
		for (Object i : fr) {
		    fragen.add((Frage) i);
		}
		return fragen;
	    }
	    ArrayList<Integer> indices = Fragencontainer.indicesFragen(fr.length, anzahl);
	    for (int i : indices) {
		fragen.add((Frage) fr[i]);
	    }
	} else {
	    for (Object i : fr) {
		fragen.add((Frage) i);
	    }
	}
	return fragen;
    }
    
    private static ArrayList<Integer> indicesFragen(int anzahlFragen, int anzahlGewuenscht) {
	Random r = new Random();
	ArrayList<Integer> ret = new ArrayList<>();
	int counter = 0;
	while(counter < anzahlGewuenscht) {
	    int i = r.nextInt(anzahlFragen);
	    if (!ret.contains(i)) {
		ret.add(i);
		counter++;
	    }
	}
	return ret;
    }
    
    /**
     * schreibt die Stringdarstellung der �bergebenen Fragenliste zeilenweise in die Datei
     * @return false, falls (beim Beschreiben der Datei) ein Fehler aufgetreten ist, sonst true
     */
    public boolean save() {
	String n = System.getProperty("line.separator");
	String text = "";
	for (Frage f : fragen) {
	    text += f.toStringTextdatei() + n;
	}
	if (Dateischreiber.inDateiSchreiben(dateiname, text)) {
	    return true;
	}
	return false;
    }
    
    /**
     * holt sich von dem Dateieinleser die einzelnen Zeilen der Textdatei und wandelt diese in eine ArrayList 
     * an Fragen um
     * @return true
     */
    public boolean load() {
	return this.load(this.dateiname);
    }
    
    /**
     * Methode zum Einlesen einer anderen Datei als der gespeicherten Standarddatei
     * @param dateiname
     * @return
     */
    public boolean load(String dateiname) {
	ArrayList<String> zeilen = Dateieinleser.dateiEinlesen(dateiname);
	for(String s : zeilen) {
	    String[] woerter = s.split("\\$");
	    if (woerter.length >= 1) {
		if (woerter[0].equals(Fragentyp.VierAntwortenFrage.toString())) {
		    try {
			if (!fragen.contains(VierAntwortenFrage.StringZuFrage(woerter))) {
			    fragen.add(VierAntwortenFrage.StringZuFrage(woerter));
			}
		    } catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		    }
		} else if(woerter[0].equals(Fragentyp.MuendlicheAntwortFrage.toString())) {
		    try {
			if (!fragen.contains(MuendlicheAntwortFrage.StringZuFrage(woerter))) {
			    fragen.add(MuendlicheAntwortFrage.StringZuFrage(woerter));
			}
		    } catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		    }
		} else {
		    System.out.println("Ungueltige Zeile gelesen");
		}
	    }
	}
	return true;
    }
    
    @Override
    public Iterator<Frage> iterator() {
	return fragen.iterator();
    }

    /*public static void main(String[] args) {
	Fragencontainer f = Fragencontainer.instance();
	//f.linkFrage(new MuendlicheAntwortFrage("Sonstiges", "Sonstiges", "Wie viele "
	//	+ "Informatiker braucht man, um eine Gl�hbirne zu wechseln?", "Keinen, das ist kein Softwareproblem."));
	//f.save();
    } */
    
}
