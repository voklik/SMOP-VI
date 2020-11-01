package ModeSveta;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Svet {

    private Blok[][] svet;
    private int radek;
    private int sloupec;
    private ArrayList<Postava> postavy;
    private int tah=0;
//pro získání datasetu
    public Svet(int pocetradku, int pocetsloupcu) {
        this.radek = pocetradku;
        this.sloupec = pocetsloupcu;
        postavy = new ArrayList<Postava>();
        svet = StatickaPravidla.init(pocetradku, pocetsloupcu);

    }


    //pro běžnou simulaci
    public Svet(int pocetradku, int pocetsloupcu, int pocetOsob) {
        this.radek = pocetradku;
        this.sloupec = pocetsloupcu;
        postavy = new ArrayList<Postava>();
        svet = StatickaPravidla.init(pocetradku, pocetsloupcu);
      //  vypisSvet();

     //   Postava p = new Postava(this, "Adam","orc.png", 2, 2, 50, 60,60);
       // postavy.add(p);
        for (int i = 0; i < pocetOsob; i++) {
            int x = ThreadLocalRandom.current().nextInt(0, pocetradku);
            int y = ThreadLocalRandom.current().nextInt(0, pocetsloupcu);
            if (svet[x][y].getObjekt().isPruchozi() == true) {
                postavy.add(new Postava(this, ("Postava " + String.valueOf(i)),"orc.png", x, y, 50, 60,60));
            }
        }
     //   p.getInventar().getSeznamPredmetu().get(0).setMamTO(true);
     //  p.getHlad().setStav(15.0f);
      //  p.getZizen().setStav(30.0f);
 /*
// příklad hledání suroviny
        ArrayList<String> objekty = StatickaPravidla.hledatSurovinu("Drevo");

        //

        for (String s : StatickaPravidla.getSeznamSurovin()) {
            for (String o : StatickaPravidla.hledatSurovinu(s)) {
                System.out.println("Surovinu  " + s + "  najdete v objektu  :" + o);
                for (String n : StatickaPravidla.hledatPotrebnyNastroj(o)) {
                    System.out.println("Potřebný nástroj na objekt " + o + " je : " + n + " a ten mám : " + p.getInventar().mamPredmet(n));


                }

            }
        }

        tikPostavy();
        p.hledani("Strom");*/
    }

    public ArrayList<Postava> getPostavy() {
        return postavy;
    }

    public ArrayList<Postava> vyhodnoceniSimulace()
    {

        for (Postava p :postavy
             ) {
p.vyhodnoceni();
        }

        return postavy;
    }
    public boolean tikPostavy() {

        boolean zijeNekdo=false;
        for (Postava p : postavy
        ) {
            if(p.getHp()>0)
            {zijeNekdo=true;
                p.tik();
            }
        }
   tah+=1;
        return zijeNekdo;
    }

    public int getTah() {
        return tah;
    }

    public void vypisSvet() {
        for (int _radek = 0; _radek < radek; _radek++) {
            for (int _sloupec = 0; _sloupec < sloupec; _sloupec++) {
                System.out.print(" | " + svet[_radek][_sloupec].getObjekt().getNazev() + " |");

            }
            System.out.println("");
        }
    }

    public void vypisSvetSeed() {
        for (int _radek = 0; _radek < this.radek; _radek++) {
            for (int _sloupec = 0; _sloupec < this.sloupec; _sloupec++) {
                System.out.print(" | " + svet[_sloupec][_radek].getCislo() + " |");

            }
            System.out.println("");
        }
    }

    public void vypisSvetxy() {
        System.out.println("------------------\n\n");

        for (int _radek = 0; _radek < this.radek; _radek++) {
            for (int _sloupec = 0; _sloupec < this.sloupec; _sloupec++) {
                System.out.print(" / " + svet[_sloupec][_radek].getRadek() +"|"+svet[_sloupec][_radek].getObjekt().getNazev() +"| "+svet[_sloupec][_radek].getSloupec() + " /");

            }
            System.out.println("");
        }
    }


    public Blok[][] getSvet() {
        return svet;
    }

    public int getRadek() {
        return radek;
    }

    public int getSloupec() {
        return sloupec;
    }

    public Blok hledaniBloku(String hledam, int postavaradek, int postavasloupec) {
        int maxXLevo = postavaradek;
        int maxXVpravo = radek - postavaradek;
        int maxYLevo = postavasloupec;
        int maxYVpravo = radek - postavasloupec;
        int maxRadiusX;
        int maxRadiusY;
        int radiusMax;//pro procházení
        if (maxXLevo < maxYVpravo)
            maxRadiusX = maxXVpravo;
        else
            maxRadiusX = maxXLevo;
        if (maxYLevo < maxYVpravo)
            maxRadiusY = maxYVpravo;
        else
            maxRadiusY = maxYVpravo;

        //víme, kam až můžeme jít
        if (maxRadiusX < maxRadiusY)
            radiusMax = maxRadiusY;
        else
            radiusMax = maxRadiusX;
        Blok nejblizsi = new Blok(radek + 10000, sloupec + 10000);

        for (int radius = 0; radius < radiusMax; radius++) {
        //    System.out.println("Hledám v radiu " + radius);


            //horní radek
            for (int a = postavaradek - radius; a <= postavaradek + radius; a++) {
                try {
                    String nalezlJsem = "  ne";
                    if (svet[a][postavasloupec - radius].getObjekt().getNazev().equals(hledam)) {
                        nalezlJsem = "  ANO";
                        nejblizsi = BlizsiBlok(nejblizsi, (svet[a][postavasloupec - radius]), postavaradek, postavasloupec);
                    }
               //     System.out.print("Hledám  nahoře v :" + a + "/" + (postavasloupec - radius) + nalezlJsem + "\n");
                } catch (Exception e) {

                }
            }


            //dolni radek
            for (int a = postavaradek - radius; a <= postavaradek + radius; a++) {
                try {
                    String nalezlJsem = "  ne";
                    if (svet[a][postavasloupec + radius].getObjekt().getNazev().equals(hledam)) {
                        nalezlJsem = "  ANO";
                        nejblizsi = BlizsiBlok(nejblizsi, (svet[a][postavasloupec + radius]), postavaradek, postavasloupec);
                    }
                //    System.out.print("Hledám v dole :" + a + "/" + (postavasloupec + radius) + nalezlJsem + "\n");

                } catch (Exception e) {

                }
            }


            //levý sloupec
            for (int a = postavasloupec - radius; a <= postavasloupec + radius; a++) {
                try {
                    String nalezlJsem = "  ne";
                    if (svet[postavaradek - radius][a].getObjekt().getNazev().equals(hledam)) {
                        nalezlJsem = "  ANO";
                        nejblizsi = BlizsiBlok(nejblizsi, (svet[postavaradek - radius][a]), postavaradek, postavasloupec);
                    }
               //     System.out.print("Hledám v vlevo:" + (postavaradek - radius) + "/" + (a) + nalezlJsem + "\n");

                } catch (Exception e) {

                }
            }


            //pravý sloupec
            for (int a = postavasloupec - radius; a <= postavasloupec + radius; a++) {
                try {
                    String nalezlJsem = "  ne";
                    if (svet[postavaradek + radius][a].getObjekt().getNazev().equals(hledam)) {
                        nalezlJsem = "  ANO";
                        nejblizsi = BlizsiBlok(nejblizsi, (svet[postavaradek + radius][a]), postavaradek, postavasloupec);
                    }
                //    System.out.print("Hledám v vpravo:" + (postavaradek + radius) + "/" + (a) + nalezlJsem + "\n");

                } catch (Exception e) {

                }
            }

if(nejblizsi.getRadek()!=100+radek&&nejblizsi.getSloupec()!=100+sloupec)
    return nejblizsi;



        }
        if (nejblizsi == null) {
     //       System.out.println("Nenalezeno");
            return null;
        } else {
           // System.out.println("Nejblzisi blok je" + nejblizsi.getRadek() + "/" + nejblizsi.getSloupec());
            return nejblizsi;
        }
    }

    private Blok BlizsiBlok(Blok nejblizsi, Blok b, int poziceradek, int pozicesloupec) {

        if (nejblizsi == null) {
            return b;
        } else {
            int rozdilNejblizsiradek = Math.abs(poziceradek - nejblizsi.getRadek());
            int rozdilNejblizsisloupec = Math.abs(pozicesloupec - nejblizsi.getSloupec());
            int rozdilbradek = Math.abs(poziceradek - b.getRadek());
            int rozdilbsloupec = Math.abs(pozicesloupec - b.getSloupec());

            int rozdilnejbliz = rozdilNejblizsiradek + rozdilNejblizsisloupec;
            int rozdilB = rozdilbradek + rozdilbsloupec;
            if (rozdilB < rozdilnejbliz) {
                return b;
            }
            return nejblizsi;
        }

    }

    private void vycisteniCislaUBloku() {
        for (int i = 0; i < radek; i++) {
            for (int z = 0; z < sloupec; z++) {
                svet[i][z].setCislo(-1);

            }

        }
    }

    public SeznamCinnosti hledaniCesty(int cilradek, int cilsloupec, int postavaradek, int postavasloupec) {



        SeznamCinnosti seznamCinnosti = new SeznamCinnosti();
        if(cilradek==postavaradek&&cilsloupec==postavasloupec)
        {seznamCinnosti.pridatProvedCinnostNa(postavaradek,postavasloupec);
            return seznamCinnosti;
        }
    //    vypisSvetxy();
        vycisteniCislaUBloku();
        seed2(cilradek, cilsloupec);
     //   vypisSvetSeed();
        hledejCest2(seznamCinnosti, postavaradek, postavasloupec);
        return seznamCinnosti;
    }

    private void hledejCest2(SeznamCinnosti seznamCinnosti, int postavaradek, int postavasloupec) {

        Blok nejblizsi=new Blok(getRadek()+100,sloupec+100);

        nejblizsi.setCislo(radek*sloupec*2);
        for (int radius = 1; radius < getRadek(); radius++) {


            //horní radek
            for (int a = postavasloupec - radius; a <= postavasloupec + radius; a++) {
                try {

                    if (nejblizsi == null)
                        nejblizsi = svet[a][postavaradek - radius];

                    else {
                        if (nejblizsi.getCislo() > svet[a][postavaradek - radius].getCislo()&&svet[a][postavaradek - radius].getObjekt().isPruchozi()) {
                            nejblizsi = svet[a][postavaradek - radius];

                        }
                    }


                } catch (Exception e) {

                }
            }


            //dolni radek
            for (int a = postavasloupec - radius; a <= postavasloupec + radius; a++) {
                try {

                    if (nejblizsi == null)
                        nejblizsi = svet[a][postavaradek + radius];

                    else {
                        if (nejblizsi.getCislo() > svet[a][postavaradek + radius].getCislo()&&svet[a][postavaradek + radius].getObjekt().isPruchozi()) {
                            nejblizsi = svet[a][postavaradek + radius];

                        }
                    }

                }catch (Exception e)
                {

                }
            }


            //levý sloupec
            for (int a = postavaradek - radius; a <= postavaradek + radius; a++) {
                try {

                    if (nejblizsi == null)
                        nejblizsi = svet[postavasloupec - radius][a];

                    else {
                        if (nejblizsi.getCislo() > svet[postavasloupec - radius][a].getCislo()&&svet[postavasloupec - radius][a].getObjekt().isPruchozi()) {
                            nejblizsi = svet[postavasloupec - radius][a];

                        }
                    }
                } catch (Exception e) {

                }
            }


            //pravý sloupec
            for (int a = postavaradek - radius; a <= postavaradek + radius; a++) {
                try {

                    if (nejblizsi == null)
                        nejblizsi = svet[postavaradek + radius][a];

                    else {
                        if (nejblizsi.getCislo() > svet[postavasloupec + radius][a].getCislo()&&svet[postavasloupec + radius][a].getObjekt().isPruchozi()) {
                            nejblizsi = svet[postavasloupec + radius][a];

                        }
                    }
                } catch (Exception e) {

                }

            }

            if(nejblizsi!=null)
            {
                if(nejblizsi.getCislo()>0)
                {
             seznamCinnosti.pridatBezNa(nejblizsi.getRadek(),nejblizsi.getSloupec());
                }

                else if(nejblizsi.getCislo()==0){
                    seznamCinnosti.pridatBezNa(nejblizsi.getRadek(),nejblizsi.getSloupec());
                    seznamCinnosti.pridatProvedCinnostNa(nejblizsi.getRadek(),nejblizsi.getSloupec());
                    return;
                }



            }



        }
    }
    private void hledejCestu(SeznamCinnosti seznamCinnosti, int postavaradek, int postavasloupec) {

        //jsem na místě
        try {
            if (svet[postavasloupec][postavaradek].getCislo() == 0) {
                seznamCinnosti.pridatProvedCinnostNa(postavaradek, postavasloupec);
                return;
            }
        } catch (Exception e) {
            System.out.println(e.getCause());
        }

        //nejsem na místě > koukni na 4sousedy a zjisti minimum,které je průchozí, a tam se přesuň

        Blok minimum = null;

        try {
            if (minimum == null && svet[postavasloupec][postavaradek + 1].getCislo() != -1 && svet[postavasloupec][postavaradek + 1].getObjekt().isPruchozi()) {
                minimum = svet[postavasloupec][postavaradek + 1];
            } else {
                if (minimum.getCislo() > svet[postavasloupec][postavaradek + 1].getCislo() && svet[postavasloupec][postavaradek + 1].getCislo() != -1 && svet[postavasloupec][postavaradek + 1].getObjekt().isPruchozi()) {
                    minimum = svet[postavasloupec][postavaradek + 1];
                }
            }


        } catch (Exception e) {
            System.out.println(e.getCause());
        }
        try {
            if (minimum == null && svet[postavasloupec][postavaradek -1 ].getCislo() != -1 && svet[postavasloupec][postavaradek - 1].getObjekt().isPruchozi()) {
                minimum = svet[postavasloupec][postavaradek - 1];
            } else {
                if (minimum.getCislo() > svet[postavasloupec][postavaradek - 1].getCislo() && svet[postavasloupec][postavaradek - 1].getCislo() != -1 && svet[postavasloupec][postavaradek - 1].getObjekt().isPruchozi()) {
                    minimum = svet[postavasloupec][postavaradek - 1];
                }
            }
        } catch (Exception e) {
            System.out.println(e.getCause());
        }
        try {
            if (minimum == null && svet[postavasloupec+1][postavaradek].getCislo() != -1 && svet[postavasloupec+1][postavaradek].getObjekt().isPruchozi()) {
                minimum = svet[postavasloupec][postavaradek ];
            } else {
                if (minimum.getCislo() > svet[postavasloupec+1][postavaradek].getCislo() && svet[postavasloupec+1][postavaradek ].getCislo() != -1 && svet[postavasloupec+1][postavaradek ].getObjekt().isPruchozi()) {
                    minimum = svet[postavasloupec+1][postavaradek];
                }
            }
        } catch (Exception e) {
            System.out.println(e.getCause());
        }
        try {
            if (minimum == null && svet[postavasloupec-1][postavaradek].getCislo() != -1 && svet[postavasloupec-1][postavaradek].getObjekt().isPruchozi()) {
                minimum = svet[postavasloupec][postavaradek ];
            } else {
                if (minimum.getCislo() > svet[postavasloupec-1][postavaradek].getCislo() && svet[postavasloupec-1][postavaradek ].getCislo() != -1 && svet[postavasloupec-1][postavaradek ].getObjekt().isPruchozi()) {
                    minimum = svet[postavasloupec-1][postavaradek];
                }
            }
        } catch (Exception e) {
System.out.println(e.getCause());
        }

//přejdi na toto políčko a dál hledej cestu k cíli
        if (minimum != null) {
            seznamCinnosti.pridatBezNa(minimum.getRadek(), minimum.getSloupec());
            hledejCestu(seznamCinnosti, minimum.getRadek(), minimum.getSloupec());
        }
        //něco se pokazilo ve světě > graf kde blok znázorňuje uzel, tak by takový graf nebyl souvislý
        else {
       //     System.out.println("graf není souvislý");
            return;
        }


    }


    private void seed2(int radek,int sloupec)
    {
        try {
            svet[sloupec][radek].setCislo(0);
        }
        catch (Exception e){
//System.out.println("CHYBA"+radek+"radek/sloupec"+sloupec);
        }
        for (int radius = 1; radius < getRadek(); radius++) {
     //       System.out.println("značím v radiu " + radius);


            //horní radek
            for (int a = sloupec - radius; a <= sloupec + radius; a++) {
                try {
                    svet[a][radek - radius].setCislo(radius);
                          } catch (Exception e) {

                }
            }


            //dolni radek
            for (int a = sloupec - radius; a <= sloupec + radius; a++) {
                try {
                    svet[a][radek + radius].setCislo(radius);    } catch (Exception e) {

                }
            }


            //levý sloupec
            for (int a = radek - radius; a <= radek + radius; a++) {
                try {
                    svet[sloupec-radius][a].setCislo(radius);
                } catch (Exception e) {

                }
            }


            //pravý sloupec
            for (int a = radek - radius; a <= radek + radius; a++) {
                try {
                    svet[sloupec+radius][a].setCislo(radius);
                } catch (Exception e) {

                }
            }



        }
    }


}
