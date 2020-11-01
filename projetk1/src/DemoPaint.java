import ModeSveta.Postava;
import ModeSveta.Svet;

import java.awt.*;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

class DemoPaint extends JComponent {


    private Svet svet;
    private int sirka;
    private int vyska;

    public void setSirka(int sirka) {
        this.sirka = sirka;
    }

    public void setVyska(int vyska) {
        this.vyska = vyska;
    }

    public Svet getSvet() {
        return svet;
    }

    public void setSvet(Svet svet) {
        this.svet = svet;
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        try {
            int velikostX=svet.getRadek();
            int velikostY=svet.getSloupec();
            int pomerX=Math.round((sirka/velikostX)*0.9f); //velikost bloku sirka
            int pomerY=Math.round((vyska/velikostY)*0.9f);  //velikost bloku vyska
            for (int radek=0;radek<velikostX;radek++)
            {
                for (int sloupec=0;sloupec<velikostY;sloupec++)
                {

                    Image picture;
                    File imagefile = new File(svet.getSvet()[radek][sloupec].getObjekt().getObrazek());
                    picture = ImageIO.read(imagefile);
                    // imagefile = new File("tree.png");
                    // tree = ImageIO.read(imagefile);
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    g2.drawString(dtf.format(now),0,10);
                    g2.drawImage(picture, (radek*pomerX), (sloupec*pomerY), pomerX-3, pomerY-3, null);
                }

            }

            for (Postava p :svet.getPostavy())
            {
                Image picture;
                File imagefile = new File(p.getObrazek());
                picture = ImageIO.read(imagefile);
                // imagefile = new File("tree.png");
                // tree = ImageIO.read(imagefile);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                g2.drawString(dtf.format(now),0,10);
                g2.drawImage(picture, (p.getSloupec()*pomerX), (p.getRadek()*pomerY), pomerX-3, pomerY-3, null);

            }

            /*
            Image picture;
            Image tree;
            File imagefile = new File("grass.png");
            picture = ImageIO.read(imagefile);
           // imagefile = new File("tree.png");
           // tree = ImageIO.read(imagefile);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            g2.drawString(dtf.format(now),0,10);
            for (int a = 20; a <= 600; a += velikostX+10) {
                for (int b = 20; b <= 600; b += velikostY+10) {




                    g2.drawImage(picture, a, b, velikostX, velikostY, null);
                    //         picture= Toolkit.getDefaultToolkit().getImage("grass.png");
                    //         g2.drawImage(picture, a, b, 25, 25, null);


                    //  g2.drawImage(tree, a, b, 25, 25, null);
                    //  tree = Toolkit.getDefaultToolkit().getImage("tree.png");
                    g2.drawImage(tree, a, b, velikostX, velikostY, null);



                }




            }*/
        }
        catch (Exception e)
        {
System.out.println(e.getCause());
        }



        }


    }