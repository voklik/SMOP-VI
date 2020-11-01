
import ModeSveta.Postava;
import ModeSveta.StatickaPravidla;
import ModeSveta.Surovina;
import ModeSveta.Svet;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JComponent;
import javax.swing.JFrame;


public class StarterI {
    public static DemoPaint demo;
    public static JFrame window = null;
    public static Svet svet;

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        System.out.println("Vítejte v simulátoru");
        System.out.println("1 - Získání datasetu");
        System.out.println("2 - simulace pro X postav s vykreslenim");
        System.out.println("3 - konec aplikace");
        int volba = 100;

        do {


            try {
                System.out.println("Zvolte si typ úlohy");
                volba = console.nextInt();
            } catch (Exception e) {
                System.out.println("Chyba !! zadejte jenom čísla");
            }
            switch (volba) {
                case 1: {
try {
    simulaceBezVykresleniDataSet();
}catch (Exception e){

}
break;
                }
                case 2: {
                    int postav = 0;
                    System.out.println("zadejte počet postav > Zadejte číslo, bez mezer a bez znamínek");
                    do {
                        try {

                            postav = console.nextInt();

                        } catch (Exception e) {
                            System.out.println("Chyba !! zadejte jenom kladná celá čísla");
                        }
                    } while (postav < 1);

                    simulaceSVykreslenim(postav);
                    break;
                }
                case 3: {
                    System.exit(0);
                    break;
                }
            }

        } while (volba != 0);


    }

    private static void simulaceBezVykresleniDataSet() throws IOException {
        svet = new Svet(20, 20);
ArrayList<Postava> dokoncenePostavy=new ArrayList<Postava>();
        for (int hlad = 80; hlad >= 20; hlad =hlad-1)
        {
         //   System.out.println("------------------novaurovenHladu------------");
            for (int zizen = 80; zizen >= 20; zizen =zizen-1) {
              //  System.out.println("------------------novaurovenzizne------------");

                for (int energie = 50; energie >= 50; energie =energie- 2)
                {
System.out.println(hlad+","+zizen+","+energie);
                    System.gc();
                    //    System.out.println("------------------novaurovenenergie------------");
                    //  System.out.println("------------------NOVYSVET------------");


                    svet.getPostavy().add(new Postava(svet, "Adam", "orc.png", 2, 2, hlad, zizen, energie));




                }
            }
        }
        boolean zijeNekdo = false;
        do {
            svet.tikPostavy();
System.out.println("kolo"+svet.getTah());
        } while ( svet.getTah() < 250);
        //  System.out.println("------------------KONECSVETA------------");
try{


            FileWriter csvWriter = new FileWriter("dataset.csv");

            csvWriter.append("Hlad Horni Hranice");
            csvWriter.append(",");
            csvWriter.append("Zizen Horni Hranice");
            csvWriter.append(",");
            csvWriter.append("Energie Horni Hranice");
            csvWriter.append(",");
            csvWriter.append("Hodnoceni");
            csvWriter.append(",");
            csvWriter.append("HP");
            csvWriter.append("\n");


        ArrayList<Postava> seznam = svet.vyhodnoceniSimulace();
        for (Postava postava : seznam
        ) {
            //  dokoncenePostavy.add(postava);
            System.out.println(postava.vypisHodnoceni());
            csvWriter.append(String.valueOf(postava.getHlad().getHorniHranice()));
            csvWriter.append(",");
            csvWriter.append(String.valueOf(postava.getZizen().getHorniHranice()));
            csvWriter.append(",");
            csvWriter.append(String.valueOf(postava.getEnergie().getHorniHranice()));
            csvWriter.append(",");
            csvWriter.append(String.valueOf(postava.getHodnoceni()));
            csvWriter.append(",");
            csvWriter.append(String.valueOf(postava.getHp()));
            csvWriter.append("\n");
        }


            csvWriter.flush();
}
catch (Exception e){

}

    }

    private static void simulaceSVykreslenim(int postav) {
        svet = new Svet(20, 20, postav);
        //    StatickaPravidla.init();


        // Vytvoření okna
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(30, 30, 800, 800);

        window.setResizable(false);
        window.setVisible(true);
        demo = new DemoPaint();
        demo.setSvet(svet);
        demo.setSirka(window.getWidth());
        demo.setVyska(window.getHeight());
        // demo.setBackground(Color.GRAY);
        // window.setBackground(Color.GRAY);
        window.getContentPane().setBackground(Color.GRAY);
        window.getContentPane().add(demo);
        Timer timer = new Timer("Timer");
        TimerTask task = new TimerTask() {
            public void run() {


                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                boolean zijeNekdo = svet.tikPostavy();
                if (zijeNekdo == false||svet.getTah()>100) {
                    timer.cancel();
                    svet.vyhodnoceniSimulace();
                    ArrayList<Postava> seznam = svet.vyhodnoceniSimulace();
                    for (Postava postava : seznam
                    ) {

                        System.out.println(postava.vypisHodnoceni());

                    }

                }
                window.setTitle((dtf.format(now)));
                demo.repaint();
                //window.revalidate();
            }
        };


        long delay = 1000;
        // timer.schedule(task, delay);
        timer.scheduleAtFixedRate(task, delay, 1000);
    }


}