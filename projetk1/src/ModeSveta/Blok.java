package ModeSveta;

public class Blok {

    private Objekt objekt = null;
    private  int radek;
    private  int sloupec;
    private int cislo=-1;//slouží pro hledání cesty pomocí seedu
    public Blok(int radek, int sloupec) {
        this.radek = radek;
        this.sloupec = sloupec;
    }

    public Objekt getObjekt() {
        return objekt;
    }

    public void setObjekt(Objekt objekt) {
        this.objekt = objekt;
    }

    public int getCislo() {
        return cislo;
    }

    public void setCislo(int cislo) {
        this.cislo = cislo;
    }

    public int getRadek() {
        return radek;
    }

    public int getSloupec() {
        return sloupec;
    }
}
