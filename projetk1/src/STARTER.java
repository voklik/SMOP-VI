
package com.javacodegeeks.snippets.desktop;

        import javax.imageio.ImageIO;
        import java.awt.Component;
        import java.awt.Frame;
        import java.awt.Graphics;
        import java.awt.Graphics2D;
        import java.awt.Image;
        import java.awt.Toolkit;
        import java.io.File;
        import java.io.IOException;

public class STARTER {

    static Image image;

    public static void main(String[] args) {

// The image URL - change to where your image file is located!

        String imageURL = "image.png";

// This call returns immediately and pixels are loaded in the background

      //  image = Toolkit.getDefaultToolkit().getImage("grass.png");


// Create a frame

        Frame frame = new Frame();

// Add a component with a custom paint method

        frame.add(new CustomPaintComponent());

// Display the frame

        int frameWidth = 1000;

        int frameHeight = 1000;

        frame.setSize(frameWidth, frameHeight);

        frame.setVisible(true);

    }

    /**
     * To draw on the screen, it is first necessary to subclass a Component
     * and override its paint() method. The paint() method is automatically called
     * by the windowing system whenever component's area needs to be repainted.
     */
    static class CustomPaintComponent extends Component {

        public void paint(Graphics g) {

            // Retrieve the graphics context; this object is used to paint shapes

            Graphics2D g2d = (Graphics2D)g;

            /**
             * Draw an Image object
             * The coordinate system of a graphics context is such that the origin is at the
             * northwest corner and x-axis increases toward the right while the y-axis increases
             * toward the bottom.
             */

            int x = 0;
            Image picture;
            int y = 0;
for (int a=0; a<=500; a+=30)
{
   for (int b=0; b<=500; b+=30)
     {     try {
         File imagefile = new File("grass.png");
         picture = ImageIO.read(imagefile);
         // picture= Toolkit.getDefaultToolkit().getImage("grass.png");
         g2d.drawImage(picture, a, b, 25, 25, null);
         imagefile=null;
         imagefile = new File("tree.png");
         picture.flush();
         picture = ImageIO.read(imagefile);
           picture = Toolkit.getDefaultToolkit().getImage("tree.png");
         g2d.drawImage(picture, 0, 0, 100, 100, null);
         //   g2.finalize();
     }

     catch (IOException e) {

     }
     finally {

     }

     }
    picture = Toolkit.getDefaultToolkit().getImage("tree.png");
    g2d.drawImage(picture, 0, 0, 250, 250, null);

}

}
}






}