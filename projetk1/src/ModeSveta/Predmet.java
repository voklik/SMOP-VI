package ModeSveta;

import java.util.ArrayList;

public class Predmet {


    private String nazev;
    private boolean mamTO = false;
    private ArrayList<Surovina> seznamSurovinKVytvoreni;
    private int uroven = -3;

    //konstruktor pro statická pravidla
    public Predmet(String nazev, int uroven, ArrayList<Surovina> seznamSurovinKVytvoreni) {
        this.nazev = nazev;
        this.seznamSurovinKVytvoreni = seznamSurovinKVytvoreni;
        this.uroven = uroven;

    }

    //konstruktor pro postavy
    public Predmet(String nazev, Postava p, int uroven, ArrayList<Surovina> seznamSurovinKVytvoreni) {
        this.nazev = nazev;
        this.seznamSurovinKVytvoreni = seznamSurovinKVytvoreni;
        p.getSeznamUrovniCilu().get(uroven - 1).addCil(new Cil(this));
    }

    public String getNazev() {
        return nazev;
    }


    public boolean isMamTO() {
        return mamTO;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public ArrayList<Surovina> getSeznamSurovinKVytvoreni() {
        return seznamSurovinKVytvoreni;
    }


    public int getUroven() {
        return uroven;
    }

    public void setMamTO(boolean mamTO) {
        this.mamTO = mamTO;
    }
}
