package ModeSveta;

public class Surovina {
    private final int max=20;
    private String nazev;
    private  int pocet=0;
    private  float KDoplneni=-1;

    //konstruktor pro Suroviny, které doplňují potřeby
    public Surovina(String nazev, int pocet,float kDoplneni) {
        this.nazev = nazev;
        this.pocet = pocet;
        this.KDoplneni=kDoplneni;
    }
    //konstruktor pro Suroviny, které nedoplňují potřeby
    public Surovina(String nazev, int pocet) {
        this.nazev = nazev;
        this.pocet = pocet;

    }

    public String getNazev() {
        return nazev;
    }

    public int getMax() {
        return max;
    }

    public int getPocet() {
        return pocet;
    }
    public void pridatPocet(int i)
    {
        pocet+=i;
        if(pocet>max)
            pocet=max;
    }

    public void setPocet(int pocet) {
        this.pocet = pocet;
    }
    public void SnizPocet(int pocet) {
        this.pocet -= pocet;
    }

    public float getKDoplneni() {
        return KDoplneni;
    }
}
