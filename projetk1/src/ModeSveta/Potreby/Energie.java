package ModeSveta.Potreby;

import ModeSveta.Surovina;

public class Energie extends Potreba { private float stav =100.0f;
    private int horniHranice=40;
    private final float dolniHranice=19.9f;
    private  float cenaZaTik =0.5f;
    private  float cenaZaCinnost =1.5f;
    private Potreba hlad;
    private Potreba zizen;


    public  Energie(int horniHraniceenergie,Potreba hlad, Potreba zizen) {
        super(horniHraniceenergie,"","Energie");
        this.hlad = hlad;
        this.zizen = zizen;
        horniHranice=horniHraniceenergie;

    }


    public float getStav() {
        return stav;
    }


    public int getHorniHranice() {
        return horniHranice;
    }


    public float getDolniHranice() {
        return dolniHranice;
    }


    public boolean doplnStav(Surovina s) {
      zvysPotrebu();
        return true;
    }





    public void snizPotrebuTik() {
        stav-=cenaZaTik;
    }


    public void snizPotrebuCinnost() {
        stav-=cenaZaCinnost;
    }


    public void snizPotrebu(float f) {

        stav-=f;
    }
    private void zvysPotrebu() {

        float kDoplneni = 100.0f-stav;
        hlad.snizPotrebu(kDoplneni);
        zizen.snizPotrebu(kDoplneni);
        stav+=kDoplneni;
    }
}