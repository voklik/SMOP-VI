package ModeSveta;

import java.util.ArrayList;

public class Cil {

    private String text;
    private boolean isSpleno = false;
    private Predmet predmet;

    public Cil(Predmet predmet) {
        this.predmet = predmet;
        text = " Mám předmět " + predmet.getNazev() + "?   :";

    }

    public void splnujipodmiku() {
        if (predmet.isMamTO()) {
            isSpleno = true;
        }
    }

    public String getText() {
        return text + isSpleno;
    }

    public boolean isSpleno() {
        return isSpleno;
    }

    public boolean mohuVytvorit(ArrayList<Surovina> mam) {
        boolean mamSuroviny = true;
        for (Surovina s : predmet.getSeznamSurovinKVytvoreni()
        ) {
            for (Surovina mamS : mam) {
                if (mamS.getNazev().equals(s.getNazev())) {
                    if (mamS.getPocet() < s.getPocet())
                        return false;
                }
            }
        }

        return mamSuroviny;
    }

    public void vytvor(ArrayList<Surovina> mam) {
        boolean mamSuroviny = true;
        for (Surovina s : predmet.getSeznamSurovinKVytvoreni()
        ) {
            for (Surovina mamS : mam) {
                if (mamS.getNazev().equals(s.getNazev())) {
                    mamS.SnizPocet(s.getPocet());
                }
            }
        }
        predmet.setMamTO(true);

    }
}
