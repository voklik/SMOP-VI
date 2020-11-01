package ModeSveta;

import java.util.ArrayList;
import java.util.Arrays;

public class Inventar {



    private ArrayList<Predmet> seznamPredmetu;
    private ArrayList<Surovina> seznamSurovin;

    private Surovina hledanáSurovina = null;
    public Inventar(Postava postava) {

        //inicializace seznamů
        this.seznamPredmetu = new ArrayList<Predmet>();
        this.seznamSurovin  = new ArrayList<Surovina>();

        //přidání surovin a předmětů ze statických pravidel
        for (Predmet p:StatickaPravidla.getSeznamNastroju())
        {
            seznamPredmetu.add(new Predmet(p.getNazev(),postava,p.getUroven(),p.getSeznamSurovinKVytvoreni()));
        }

        for (String s:StatickaPravidla.getSeznamSurovin())
        {
            seznamSurovin.add(new Surovina(s,0));  }
    }


    public ArrayList<Predmet> getSeznamPredmetu() {
        return seznamPredmetu;
    }

    public ArrayList<Surovina> getSeznamSurovin() {
        return seznamSurovin;
    }

    public Surovina getSurovinu(String s)
    {
        for (Surovina surovina:seznamSurovin)
        {
            if(surovina.getNazev().equals(s))
                return surovina;
        }
        return null;
    }

    public Boolean mamPredmet (String predmetHledam)
    {
        for (Predmet predmet:seznamPredmetu) {

            if (predmet.getNazev().equals(predmetHledam))
            {
                if(predmet.isMamTO()==true)
                    return true;
                    else return false;
            }
        }

        return false;
    }

    public Surovina getHledanáSurovina() {
        if(hledanáSurovina==null)
        setHledanaSurovinaNic();
        return hledanáSurovina;
    }

    public void setHledanáSurovina(Surovina hledanáSurovina) {
        this.hledanáSurovina = hledanáSurovina;
    }
    public void setHledanaSurovinaNic()
    {
        hledanáSurovina = new Surovina("Nic",0);
    }

}
