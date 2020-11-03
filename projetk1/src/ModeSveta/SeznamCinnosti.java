package ModeSveta;

import java.util.ArrayList;

public class SeznamCinnosti {

    private ArrayList<Cinnost> seznamCinnosti;

    public SeznamCinnosti() {
        seznamCinnosti = new ArrayList<Cinnost>();
    }


    public boolean cilSeznamuJeValidniStale(Svet svet) {
        if (seznamCinnosti.isEmpty())
            return false;
        Cinnost posledni = seznamCinnosti.get(seznamCinnosti.size() - 1);

        if (svet.getSvet()[posledni.getSloupec()][posledni.getRadek()].getObjekt() == null)
            return false;
        else
            return true;

    }

    public void resetSeznamu() {
        seznamCinnosti.clear();
    }

    public void pridatBezNa(int x, int y) {
        seznamCinnosti.add(new Cinnost(x, y, "pohyb"));
    }

    public void pridatProvedCinnostNa(int x, int y) {
        seznamCinnosti.add(new Cinnost(x, y, "cinnost"));
    }

    public Cinnost DostatPrvni() {
        if (!seznamCinnosti.isEmpty()) {
            return seznamCinnosti.get(0);
        } else
            return null;
    }

    public void OdebratPrvni() {
        if (!seznamCinnosti.isEmpty()) {
            seznamCinnosti.remove(0);
        }
    }

    public ArrayList<Cinnost> getSeznamCinnosti() {
        return seznamCinnosti;
    }

    public void setSeznamCinnosti(ArrayList<Cinnost> seznamCinnosti) {
        this.seznamCinnosti = seznamCinnosti;
    }


}
