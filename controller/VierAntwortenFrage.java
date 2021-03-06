/**
 * Klasse, die eine Frage repräsentiert, die eine Fragestellung und vier Antworten hat, von denen genau eine
 * richtig ist (vgl. Wer wird Millionär)
 */

package controller;

import java.util.Arrays;

public class VierAntwortenFrage extends Frage {
    String frage;
    String[] antworten = new String[4];
    
    public VierAntwortenFrage(String vorlesung, String thema,
	    String frage, String a1, String a2, String a3, String a4) {
	super(Fragentyp.VierAntwortenFrage, vorlesung, thema, 1);
	this.setFrage(frage);
	this.setAntworten(a1, a2, a3, a4);
    }
    
    /** 
     * Setzt als Fragestellung dieser VierAntwortenFrage den übergebenen String
     * @param frage
     * @throws IllegalArgumentException, falls der String kürzer als 1 Zeichen oder leer ist.
     */
    public void setFrage(String frage) throws IllegalArgumentException {
	if (frage == null || frage.length() == 0) {
	    throw new IllegalArgumentException("Der String Frage muss länger sein.");
	}
	this.frage = frage;
    }
    
    /** 
     * Setzt als Antwortmöglichkeiten dieser VierAntwortenFrage die übergebenen Strings in der Reihenfolge
     * @param a1, a2, a3, a4 die vier Antwortmöglichkeiten
     * @throws IllegalArgumentException, falls einer der Strings kürzer als 1 Zeichen oder leer ist.
     */
    public void setAntworten(String a1, String a2, String a3, String a4) throws IllegalArgumentException {
	if (a1 == null || a1.length() == 0 || a2 == null || a2.length() == 0 || a3 == null || a3.length() == 0 || a4 == null || a4.length() == 0) {
	    throw new IllegalArgumentException("Einer der Antwortstrings ist zu kurz.");
	}
	antworten[0] = a1;
	antworten[1] = a2;
	antworten[2] = a3;
	antworten[3] = a4;
    }
    
    public String getFrage() {
	return this.frage;
    }
    
    public String[] getAntworten() {
	return this.antworten;
    }

    

    /**
     * Stringdarstellung der Frage, wie sie in der Textdatei stehen sollte
     * Format: typ$schwierigkeit$vorlesung$thema$frage$a1$a2$a3$a4$indexRichtigeAntwort
     */
    @Override
    public String toString() {
	return  this.getFrage() + " " +
		this.antworten[0] + " - " + 
		this.antworten[1] + " - " + 
		this.antworten[2] + " - " +
		this.antworten[3];
    }
    
    //@Override
    public static Frage StringZuFrage(String[] woerter) throws IllegalArgumentException {
	if(woerter.length != 8) {
		throw new IllegalArgumentException("Zeile mit zu wenig Attributen gelesen");
	} else {
	    try {
		String vorlesung = woerter[1];
		String thema = woerter[2];
		String frage = woerter[3];
		String a1 = woerter[4];
		String a2 = woerter[5];
		String a3 = woerter[6];
		String a4 = woerter[7];
		return new VierAntwortenFrage(vorlesung, thema,
			    frage, a1, a2, a3, a4);
	    } catch (NumberFormatException e) {
		System.out.println("Fehler!");
	    } catch (IllegalArgumentException e) {
		System.out.println("Fehler!");
	    }
	}
	return null;
    }

    @Override
    public String toStringTextdatei() {
	return  this.getTyp().toString() + "$" +
		this.getVorlesung() + "$" + 
		this.getThema() + "$" + 
		this.getFrage() + "$" + 
		this.antworten[0] + "$" + 
		this.antworten[1] + "$" + 
		this.antworten[2] + "$" +
		this.antworten[3];
    }
    
    @Override 
    public boolean equals(Object o) {
	if (o != null && o.getClass().equals(this.getClass())) {
	    VierAntwortenFrage frage = (VierAntwortenFrage) o;
	    return (this.getVorlesung().equals(frage.getVorlesung()) && this.getThema().equals(frage.getThema())
		    && this.getMaxPunkte() == frage.getMaxPunkte() && this.getFrage().equals(frage.getFrage())
		    && Arrays.equals(this.getAntworten(), frage.getAntworten()));
	}	
	return false;
    }

}
