package ModeSveta;


import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class StatickaPravidla {
    private static Objekt trava = new Objekt("Trava", "grass.png", -1, true);//vyplní svět všude, kde není objekt
    private static ArrayList<Predmet> seznamNastroju = new ArrayList<Predmet>();
    private static ArrayList<String> seznamSurovin = new ArrayList<String>();
    private static ArrayList<Objekt> seznamObjektu = new ArrayList<Objekt>();


    private int minSirka = 20;
    private int minVyska = 20;


    //mapa surovin padoucích ze stromu
    private static final Map<String, Surovina> mapaObjektSurovina = Map.of(
            "Strom", new Surovina("Drevo", 1),
            "Ker", new Surovina("Jidlo", 1, 2),
            "Voda", new Surovina("Voda", 1, 2),
            "Balvan", new Surovina("Kamen", 1)
    );

    //mapa nástrojů potřebných k praci s objektem
    // "" znamená, že nic není potřeba
    private static final Map<String, String> mapaObjektNastroj = Map.of(
            "Strom", "Sekera",
            "Ker", "",
            "Voda", "",
            "Balvan", "Krumpac"
    );

    //Vyplnění seznamů a map objekty
    public static Blok[][] init(int radku, int sloupcu) {


        seznamObjektu.add(new Objekt("Strom", "tree.png", 5, true));
        seznamObjektu.add(new Objekt("Ker", "bush.png", 5, true));
        seznamObjektu.add(new Objekt("Voda", "water.png", 5, true));
        seznamObjektu.add(new Objekt("Balvan", "stone.png", 5, true));
        seznamSurovin.add("Drevo");
        seznamSurovin.add("Kamen");
        seznamSurovin.add("Voda");
        seznamSurovin.add("Jidlo");

        seznamNastroju.add(new Predmet("Sekera", 1, new ArrayList<Surovina>(Arrays.asList(new Surovina("Drevo", 3), new Surovina("Kamen", 3)))));
        ;
        seznamNastroju.add(new Predmet("Kladivo", 2, new ArrayList<Surovina>(Arrays.asList(new Surovina("Drevo", 3), new Surovina("Kamen", 3)))));
        ;
        seznamNastroju.add(new Predmet("Nuz", 1, new ArrayList<Surovina>(Arrays.asList(new Surovina("Drevo", 3), new Surovina("Kamen", 3)))));
        ;
        seznamNastroju.add((new Predmet("Krumpac", 1, new ArrayList<Surovina>(Arrays.asList(new Surovina("Drevo", 3), new Surovina("Kamen", 3))))));
        ;
        return generujSvet(radku, sloupcu);// naplní herní svět objekty
    }

    private static Blok[][] generujSvet(int radku, int sloupcu) {

        Blok[][] svet = new Blok[sloupcu][radku];
        for (int radek = 0; radek < radku; radek++) {
            for (int sloupec = 0; sloupec < sloupcu; sloupec++) {
                svet[sloupec][radek] = new Blok(radek, sloupec);

            }
        }


        for (Objekt objekt : seznamObjektu) {
            int pocet = objekt.getPocet();
            for (int i = pocet; pocet > 0; i++) {

                int x = ThreadLocalRandom.current().nextInt(0, radku);
                int y = ThreadLocalRandom.current().nextInt(0, sloupcu);
                if (svet[x][y].getObjekt() == null) {
                    svet[x][y].setObjekt(new Objekt(objekt.getNazev(), objekt.getObrazek(), objekt.isPruchozi()));
                    pocet--;
                }

            }

        }

        for (int i = 0; i < radku; i++) {
            for (int z = 0; z < sloupcu; z++) {
                if (svet[i][z].getObjekt() == null) {
                    svet[i][z].setObjekt(new Objekt(trava.getNazev(), trava.getObrazek(), trava.isPruchozi()));
                }
            }
        }
        return svet;

    }

    public static ArrayList<String> hledatSurovinu(String sur) {
        ArrayList<String> objekty = new ArrayList<String>();

        Iterator<Map.Entry<String, Surovina>>
                iterator = mapaObjektSurovina.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Surovina> entry = iterator.next();
            if (entry.getValue().getNazev() == sur)
                objekty.add(entry.getKey());
            //      System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        return objekty;
    }

    public static ArrayList<String> hledatPotrebnyNastroj(String obj) {
        ArrayList<String> nastroje = new ArrayList<String>();

        Iterator<Map.Entry<String, String>>
                iterator = mapaObjektNastroj.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            if (entry.getKey() == obj)
                nastroje.add(entry.getValue());
            //      System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        return nastroje;
    }

    public static ArrayList<Predmet> getSeznamNastroju() {
        return seznamNastroju;
    }

    public static ArrayList<String> getSeznamSurovin() {
        return seznamSurovin;
    }

    public static Map<String, Surovina> getMapaObjektSurovina() {
        return mapaObjektSurovina;
    }

    public static Map<String, String> getMapaObjektNastroj() {
        return mapaObjektNastroj;
    }


}
    


