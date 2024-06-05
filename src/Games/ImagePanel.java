package Games;

import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;


public class ImagePanel extends JPanel{
    private Image backgroundImage;

    public ImagePanel(String filename, int width, int height){
        try{
            backgroundImage = ImageIO.read(new File(filename));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(backgroundImage != null){
            g.drawImage(backgroundImage, 0, 0, this);
        }
    }
}