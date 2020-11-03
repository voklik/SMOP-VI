package ModeSveta;

public class Cinnost {


    private int radek, sloupec;
    private String akce; // "cinnost" / "pohyb"

    public Cinnost(int _radek, int sloupec, String akce) {
        this.radek = _radek;
        this.sloupec = sloupec;
        this.akce = akce;
    }


    public int getRadek() {
        return radek;
    }

    public int getSloupec() {
        return sloupec;
    }

    public String getAkce() {
        return akce;
    }
}
