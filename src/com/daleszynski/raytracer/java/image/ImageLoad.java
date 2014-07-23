package com.daleszynski.raytracer.java.image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Denny Hörtz on 27.04.2014.
 *
 * This program shows a picture in a window with pictured size.
 */
public class ImageLoad {

    /**
     * Main methode, öffnet einen Dialog mit dem man das anhzuzeigende Bild anzeigen kann
     * @param args unbenutzt
     */
    public static void main(String[] args){

        File file = null;
        BufferedImage image = null;
        // JFileChooser-Objekt erstellen
        JFileChooser chooser = new JFileChooser();
        // Dialog zum Öffnen von Dateien anzeigen.
       int result = chooser.showOpenDialog(null);

        // Abfrage, ob auf öffnen geklickt wurde
        if(result == JFileChooser.APPROVE_OPTION){

            try {   // Bild einlesen
                file = chooser.getSelectedFile();
                image = ImageIO.read(file);
            }catch(IOException e){
                e.printStackTrace();
            }

            // JFrame erstellen mit Bildgröße
            JFrame frame = new JFrame(file.getName());
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            if (image != null) {
                frame.setSize(new Dimension(image.getHeight(), image.getWidth()));
            }

            // Bild anzeigen
            JLabel label = new JLabel(new ImageIcon(image));
            frame.getContentPane().add(label);

            frame.pack();
            frame.setVisible(true);
        }
    }



}
