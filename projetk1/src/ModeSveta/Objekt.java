package ModeSveta;

public class Objekt {


   private String nazev="objekt";
   private String obrazek="";
   private int pocet =0;// pro určení kolik se má vygenerovat an začátku simulace > definováno ve statických pravidlech
    private boolean pruchozi=true;

    public Objekt(String nazev, String obrazek, int pocet,boolean pruchoz) {
        this.nazev = nazev;
        this.obrazek = obrazek;
        this.pocet = pocet;
        this.pruchozi=pruchoz;
    }

    public Objekt(String nazev, String obrazek,boolean pruchoz) {
        this.nazev = nazev;
        this.obrazek = obrazek;
        this.pruchozi=pruchoz;
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public String getObrazek() {
        return obrazek;
    }

    public boolean isPruchozi() {
        return pruchozi;
    }

    public void setObrazek(String obrazek) {
        this.obrazek = obrazek;
    }

    public int getPocet() {
        return pocet;
    }

    public void setPocet(int pocet) {
        this.pocet = pocet;
    }
}
