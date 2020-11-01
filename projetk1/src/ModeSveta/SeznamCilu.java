package ModeSveta;

import java.util.*;

public class SeznamCilu {

    private int hodnoceniZaSpleni = 1;
    private ArrayList<Cil> seznamSplnenychCilu = new ArrayList<Cil>();
    private ArrayList<Cil> seznamCiluKsplneni = new ArrayList<Cil>();
    private int zbyvaSplnit = 0;

    public SeznamCilu(int hodnoceniZaSpleni) {
        this.hodnoceniZaSpleni = hodnoceniZaSpleni;
    }

    public void addCil(Cil c) {
        seznamCiluKsplneni.add(c);
        zbyvaSplnit++;
    }

    public int getHodnoceniZaSpleni() {
        return hodnoceniZaSpleni;
    }

    public ArrayList<Cil> getSeznamSplnenychCilu() {
        return seznamSplnenychCilu;
    }

    public ArrayList<Cil> getSeznamCiluKsplneni() {
        return seznamCiluKsplneni;
    }

    public int getZbyvaSplnit() {
        return zbyvaSplnit;
    }

    public void kontrolaZbyvajicichCilu() {
        if (!seznamCiluKsplneni.isEmpty()) {
            for (Cil cil : seznamCiluKsplneni) {
                cil.splnujipodmiku();
                if (cil.isSpleno()) {
                    seznamSplnenychCilu.add(cil);

                }

            }

            for (Cil cil : seznamSplnenychCilu) {
               try{
                   seznamCiluKsplneni.remove(cil);
                   zbyvaSplnit--;
               }catch (Exception e ){

               }

            }

        }



    }


    public boolean urovenSplnena() {
        if (zbyvaSplnit == 0)
            return true;
        else
            return false;
    }
}
