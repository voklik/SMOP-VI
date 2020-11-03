package ModeSveta.Potreby;

import ModeSveta.Surovina;

public class Potreba {

    private float stav = 100.0f;
    private int horniHranice = 80;
    private final float dolniHranice = 19.9f;
    private float cenaZaTik = 0.25f;
    private float cenaZaCinnost = 1.f;
    private String surovinaKDoplneni = "Voda";
    private String nazev = "Potreba X";

    public Potreba(int horniHranice, String surovina, String nazev) {
        this.horniHranice = horniHranice;
        this.surovinaKDoplneni = surovina;
        this.nazev = nazev;
    }

    public float getStav() {
        return stav;
    }


    //pro testování
    public void setStav(float stav) {
        this.stav = stav;
    }

    public int getHorniHranice() {
        return horniHranice;
    }


    public float getDolniHranice() {
        return dolniHranice;
    }


    public boolean doplnStav(Surovina s) {

        if (surovinaKDoplneni.equals(s.getNazev())) {
            if (s.getPocet() > 0) {
                int potrebamax = Math.abs(Math.round(((100.0f - stav) / s.getKDoplneni())));
                if (s.getPocet() >= potrebamax) {
                    stav += Math.abs(potrebamax * s.getKDoplneni());
                    s.SnizPocet(potrebamax);
                    return true;
                }
                if (s.getPocet() < potrebamax) {
                    stav += Math.abs(s.getPocet() * s.getKDoplneni());
                    s.SnizPocet(s.getPocet());
                    return true;
                }
            }
        }
        return false;

    }


    public void snizPotrebuTik() {
        stav -= cenaZaTik;
    }


    public void snizPotrebuCinnost() {
        stav -= cenaZaCinnost;
    }

    public void getCenaZaTik() {

    }

    public float getCenaZaCinnost() {
        return cenaZaCinnost;
    }

    public String getSurovinaKDoplneni() {
        return surovinaKDoplneni;
    }

    public String getNazev() {
        return nazev;
    }

    public void snizPotrebu(float f) {

        stav -= f;
    }
}