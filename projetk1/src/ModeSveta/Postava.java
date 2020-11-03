package ModeSveta;

import ModeSveta.Potreby.Energie;
import ModeSveta.Potreby.Hlad;
import ModeSveta.Potreby.Potreba;
import ModeSveta.Potreby.Zizen;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Postava {
    private ArrayList<SeznamCilu> seznamUrovniCilu;
    private SeznamCinnosti seznamCinnosti;
    private Inventar inventar;
    private ArrayList<Potreba> potreby;
    private Hlad hlad;
    private Zizen zizen;
    private Energie energie;
    private String nazev = "";
    private int radek;
    private int sloupec;
    private Svet svet;//slouží pro vyhledávání objektů
    private String obrazek = "";
    private int hp = 100;
    private float hodnoceni = 0;


    public Postava(Svet s, String nazev, int _radek, int sloupec) {
//Protatím se vytvoří jen 3 úrovně cílů
        potreby = new ArrayList<Potreba>();
        seznamCinnosti = new SeznamCinnosti();
        seznamUrovniCilu = new ArrayList<SeznamCilu>();
        seznamUrovniCilu.add(new SeznamCilu(1));
        seznamUrovniCilu.add(new SeznamCilu(2));
        seznamUrovniCilu.add(new SeznamCilu(3));
        inventar = new Inventar(this);
        hlad = new Hlad(60, "Jidlo");
        zizen = new Zizen(60, "Voda");
        energie = new Energie(60, hlad, zizen);
        this.svet = s;

        this.radek = _radek;
        this.sloupec = sloupec;
        this.nazev = nazev;
    }

    public Postava(Svet s, String nazev, String obrazek, int _x, int _y, int horniHraniceHlad, int horniHraniceZizen, int horniHraniceEnergie) {
//Protatím se vytvoří jen 3 úrovně cílů
        potreby = new ArrayList<Potreba>();
        this.radek = _x;
        this.sloupec = _y;
        this.svet = s;
        seznamCinnosti = new SeznamCinnosti();
        seznamUrovniCilu = new ArrayList<SeznamCilu>();
        seznamUrovniCilu.add(new SeznamCilu(1));
        seznamUrovniCilu.add(new SeznamCilu(2));
        seznamUrovniCilu.add(new SeznamCilu(3));
        inventar = new Inventar(this);
        hlad = new Hlad(horniHraniceHlad, "Jidlo");
        zizen = new Zizen(horniHraniceZizen, "Voda");
        energie = new Energie(horniHraniceEnergie, hlad, zizen);
        potreby.add(energie);
        potreby.add(hlad);
        potreby.add(zizen);
        prerovnaniPotreb();

        this.obrazek = obrazek;
        this.nazev = nazev;
    }

    public int getHp() {
        return hp;
    }


    public void vyhodnoceni() {
        if (hp <= 0) {
            hodnoceni = 0.0f;
        } else {
            hodnoceni = 1.0f;
            for (SeznamCilu uroven : seznamUrovniCilu
            ) {
                for (Cil c : uroven.getSeznamSplnenychCilu()
                ) {
                    hodnoceni = hodnoceni + (uroven.getHodnoceniZaSpleni());
                }
            }
        }
    }

    public float getHodnoceni() {
        return hodnoceni;
    }

    private void prerovnaniPotreb() {
        ArrayList<Potreba> novy = new ArrayList<Potreba>();
        do {
            Potreba nejvyssi = new Potreba(0, "", "");

            for (Potreba p : potreby) {
                if (nejvyssi != null) {
                    if (p.getHorniHranice() > nejvyssi.getHorniHranice())
                        nejvyssi = p;
                } else {
                    nejvyssi = p;
                }
            }
            novy.add(nejvyssi);
            potreby.remove(nejvyssi);
        } while (potreby.size() != 0);

        potreby = novy;
    }

    private void provedeniCinnosti() {
        if (!seznamCinnosti.getSeznamCinnosti().isEmpty()) {
            Cinnost prvni = seznamCinnosti.DostatPrvni();
            if (prvni.getAkce().equals("pohyb")) {
                radek = prvni.getRadek();
                sloupec = prvni.getSloupec();

                for (Potreba p : potreby) {
                    p.snizPotrebuTik();

                }

                try {
                    seznamCinnosti.OdebratPrvni();
                } catch (Exception e) {

                }
            } else {

                for (Potreba p : potreby) {
                    p.snizPotrebuCinnost();
                }
                //   System.out.println("PRACUJI ");

                if (!inventar.getHledanáSurovina().getNazev().equals("Nic")) {
                    inventar.getHledanáSurovina().pridatPocet(5);


                }
                inventar.setHledanaSurovinaNic();
                seznamCinnosti.resetSeznamu();
            }
        } else {
            najdiSiCinnost();
        }
    }

    public void kontrolaHP() {
        for (Potreba p : potreby) {
            if (p.getStav() <= 0)
                hp--;

        }
    }

    public void tik() {
          tikvypis();

        kontrolaHP();
        //plní se úkol pro vodu/jídlo ?
        // ano ? Tak se pokračuje
        // ne ? Tak se podívej, jestli nějaká potřeba nepotřebuje se plnit
        // > ne ?, tak zkus plnit cíl
        // > ano ?, tak zkus uspokojit potřebu >
        // mám dost surovin k doplnění ?
        //ano ? tak doplnim co jde
        //ne ? najdi cestu nejblíže ke zdroji

        if (!seznamCinnosti.getSeznamCinnosti().isEmpty() && inventar.getHledanáSurovina().getNazev().equals("Voda") || inventar.getHledanáSurovina().getNazev().equals("Jidlo")) {
            provedeniCinnosti();
        } else {
            Potreba potreba = jePotrebaResitPotrebu();

            if (potreba != null) {

                if (potreba == energie) {
                    //    System.out.println("Odpočívám");
                    potreba.doplnStav(new Surovina("", 0));
                } else {
                    Surovina potrebnaSurovina = inventar.getSurovinu(potreba.getSurovinaKDoplneni());

                    if (potrebnaSurovina.getPocet() > 0) {
                        //    System.out.println("spotřebovávám surovinu " + potrebnaSurovina.getNazev());
                        potreba.doplnStav(potrebnaSurovina);
                    } else {

                        ArrayList<String> objekt = StatickaPravidla.hledatSurovinu(potrebnaSurovina.getNazev());
                        //     System.out.println(" Mám potřebu" + potreba.getNazev() + " a budu hledat " + objekt.get(0));
                        seznamCinnosti.resetSeznamu();
                        inventar.setHledanáSurovina(potrebnaSurovina);
                        hledani(objekt.get(0));
                        provedeniCinnosti();
                    }
                }


            } else {
                //najdu si práci > zatím jenom bude chodit mezi 2body
                if (!seznamCinnosti.getSeznamCinnosti().isEmpty()) {
                    provedeniCinnosti();
                } else {
                    hledatPraci();
                }

            }


        }
/*
        jsouPotrebyvPoradku();

        if (!seznamCinnosti.getSeznamCinnosti().isEmpty()) {
            if (seznamCinnosti.cilSeznamuJeValidniStale(svet)) {

            } else {
                seznamCinnosti.resetSeznamu();
                inventar.setHledanáSurovina(null);
                najdiSiCinnost();


            }
        } else {
            seznamCinnosti.resetSeznamu();
            inventar.setHledanáSurovina(null);
            najdiSiCinnost();


        }
*/
    }

    private void hledatPraci() {
        bezNaProchazku();

        //Je nějaký cíl, který mohu splnit ?
        //  Ano ? splň ho a odeber si suroviny
        //  Ne?     Vezmi první surovinu z inventáře, která není na maximu a vyhledej si nejbližší zdroj
        //  nemáš žádnou surovinu pod maximem ?... tak si bež na procházku  Splnil jsi vše a můžeš už umřít

        int pocetZbyvasplnit = 0;
        for (SeznamCilu seznamCilu : seznamUrovniCilu
        ) {
            if (!seznamCilu.urovenSplnena()) {
                pocetZbyvasplnit += seznamCilu.getZbyvaSplnit();
                for (Cil c : seznamCilu.getSeznamCiluKsplneni()) {
                    if (c.mohuVytvorit(inventar.getSeznamSurovin())) {
                        pocetZbyvasplnit--;
                        c.vytvor(inventar.getSeznamSurovin());

                    }
                }
            }

        }
        for (SeznamCilu seznamCilu : seznamUrovniCilu
        ) {
            seznamCilu.kontrolaZbyvajicichCilu();

        }

        Surovina surovina = null;
        for (Surovina s : inventar.getSeznamSurovin()) {
            if (s.getPocet() < s.getMax()) {
                surovina = s;
            }
        }

        if (surovina == null && pocetZbyvasplnit == 0) {
            bezNaProchazku();
        } else {
            try {
                ArrayList<String> objekt = StatickaPravidla.hledatSurovinu(surovina.getNazev());
                //   System.out.println("Hledám surovinu " + surovina.getNazev() + " budu hledat v objektu" + objekt.get(0));
                seznamCinnosti.resetSeznamu();
                inventar.setHledanáSurovina(surovina);
                hledani(objekt.get(0));
                provedeniCinnosti();
            } catch (Exception e) {

            }
        }


    }

    private void bezNaProchazku() {
        //    System.out.println("jdu doprostřed mapy");
        //   int x = ThreadLocalRandom.current().nextInt(0, svet.getRadek());
        //   int y = ThreadLocalRandom.current().nextInt(0, svet.getSloupec());
        if (radek == 10 && sloupec == 15)
            seznamCinnosti = (svet.hledaniCesty(10, 10, radek, sloupec));
        else
            seznamCinnosti = (svet.hledaniCesty(10, 15, radek, sloupec));
        inventar.setHledanaSurovinaNic();
        provedeniCinnosti();
    }

    private Potreba jePotrebaResitPotrebu() {


        //zjistíme jestli něco je OPRAVDU nutné řešit
        for (Potreba p : potreby
        ) {


            if (p.getStav() < p.getDolniHranice()) {
                return p;

            }

        }
        //nic nutného není potřeba řešit, proto budeme hledat něco, jestli je potřeba řešit, i když to není akutní.

        for (Potreba p : potreby
        ) {
            if (p.getStav() < p.getHorniHranice()) {
                return p;

            }
        }


        return null;

    }


    private void najdiSiCinnost() {
        Potreba potreba = null;

        try {
            //zjistíme jestli něco je OPRAVDU nutné řešit
            for (Potreba p : potreby
            ) {


                if (p.getStav() < p.getDolniHranice()) {
                    potreba = p;
                    break;
                }

            }
            //nic nutného není potřeba řešit, proto budeme hledat něco, jestli je potřeba řešit, i když to není akutní.
            if (potreba == null) {
                for (Potreba p : potreby
                ) {
                    if (p.getStav() < p.getDolniHranice()) {
                        potreba = p;
                        break;
                    }
                }


            }
            //teď už víme, že opravdu nic postava nemusí řešit a může plnit cíle.
            if (potreba == null) {
                //      System.out.println("jdu náhodně");
                int x = ThreadLocalRandom.current().nextInt(0, svet.getRadek());
                int y = ThreadLocalRandom.current().nextInt(0, svet.getSloupec());

                seznamCinnosti = (svet.hledaniCesty(10, 10, radek, sloupec));


            }
            //Tak se našla nějaká potřeba, co je potřeba řešit
            else {
                if (inventar.getHledanáSurovina().getNazev().equals("Voda") || inventar.getHledanáSurovina().getNazev().equals("Jidlo"))
                    return;
                seznamCinnosti.resetSeznamu();
                //      System.out.println("Mažu si starý seznam");
                if (potreba == energie) {
                    //       System.out.println("Odpočívám");
                    potreba.doplnStav(new Surovina("", 0));
                } else {
                    String surovinaText = potreba.getSurovinaKDoplneni();
                    Surovina surovina = inventar.getSurovinu(surovinaText);
                    inventar.setHledanáSurovina(surovina);
                    if (surovina != null) {
                        if (surovina.getPocet() > 0) {
                            potreba.doplnStav(surovina);
                        } else {

                            ArrayList<String> objekt = StatickaPravidla.hledatSurovinu(surovinaText);
                            //    System.out.println(" Mám potřebu" + potreba.getNazev() + " a budu hledat" + objekt.get(0));

                            hledani(objekt.get(0));

                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getCause());
        }

    }


    private void tikvypis() {
        System.out.println("\n\n  TIK  " + svet.getTah() + "  -------------");
        System.out.println("POSTAVA :" + nazev + "  radek:" + radek + " / sloupec:" + sloupec + " HP: " + hp);
        System.out.println("\nPlán\n");
        for (Cinnost c : seznamCinnosti.getSeznamCinnosti()) {
            System.out.println(c.getAkce() + "  na bloku " + c.getSloupec() + "/" + c.getRadek());

        }
        System.out.println("\nPotřeby\n");
        for (Potreba p : potreby) {
            System.out.println("Potreba :" + p.getNazev() + "  stav:" + p.getStav());

        }
        System.out.println("\nInvetář\n");
        for (Surovina s : inventar.getSeznamSurovin()) {
            System.out.println(s.getNazev() + "  v počtu  " + s.getPocet());

        }
        for (Predmet s : inventar.getSeznamPredmetu()) {
            System.out.println(s.getNazev() + "   " + s.isMamTO());

        }
        System.out.println("hledám  " + inventar.getHledanáSurovina().getNazev());
        //    vypsaniCilu();


    }

    public Inventar getInventar() {
        return inventar;
    }

    public ArrayList<SeznamCilu> getSeznamUrovniCilu() {
        return seznamUrovniCilu;
    }


    public void hledani(String hledany) {
        // System.out.println("\n\n\n-----Hledam " + hledany + " a jsem na pozici radek:" + radek + " / sloupec:" + sloupec);

        Blok nejblizsi;
        if (svet.getSvet()[sloupec][radek].getObjekt().getNazev().equals(hledany))
            nejblizsi = svet.getSvet()[sloupec][radek];
        else
            nejblizsi = svet.hledaniBloku(hledany, radek, sloupec);

        //   System.out.println("\n\n\n-----Cíl je na " + nejblizsi.getRadek() + " / " + nejblizsi.getSloupec() + "  a jsem na pozici " + radek + "/" + sloupec);
        seznamCinnosti = svet.hledaniCesty(nejblizsi.getRadek(), nejblizsi.getSloupec(), radek, sloupec);

        for (Cinnost c : seznamCinnosti.getSeznamCinnosti()) {
            //     System.out.println(c.getAkce() + " " + c.getRadek() + "/" + c.getSloupec());
        }

    }


    public String getObrazek() {
        return obrazek;
    }

    public void setObrazek(String obrazek) {
        this.obrazek = obrazek;
    }

    public SeznamCinnosti getSeznamCinnosti() {
        return seznamCinnosti;
    }

    public ArrayList<Potreba> getPotreby() {
        return potreby;
    }

    public Hlad getHlad() {
        return hlad;
    }

    public Zizen getZizen() {
        return zizen;
    }

    public Energie getEnergie() {
        return energie;
    }

    public String getNazev() {
        return nazev;
    }

    public int getRadek() {
        return radek;
    }

    public int getSloupec() {
        return sloupec;
    }

    public Svet getSvet() {
        return svet;
    }

    public String vypisHodnoceni() {
        String text = "Vypis Postava " + nazev + "|HP" + hp + " |Hodnoceni " + hodnoceni + " | hladhranice " + hlad.getHorniHranice() + " | zizen " + zizen.getHorniHranice() + " | energie  " + energie.getHorniHranice();

        return text;

    }
}
