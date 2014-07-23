package com.daleszynski.raytracer.java.image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Zeigt ein Bild in einem JFrame an
 */
public class DisplayImage {

    JFrame frame;

    /**
     * Erstellt das Bild und nimmt als Parameter ein PaintImage
     * @param image das zu zeichnende Bild
     */
    public DisplayImage(final PaintImage image) {
        final JMenuItem saveItem = new JMenuItem("save");

        saveItem.addActionListener(e -> {
            if (e.getSource() == saveItem) {
                JFileChooser chooser = new JFileChooser();
                // ... und angezeigt
                int result = chooser.showSaveDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        ImageIO.write(image.getImage(), "png", new File(chooser.getSelectedFile()
                                + ".png"));
                    } catch (IOException ex) {
                        System.out.println("Die Datei konnte nicht gespeichert werden: "
                                + ex);
                    }
                }
            }
        });



        frame = new JFrame();
        frame.setLayout(new BorderLayout());

        frame.add(image, BorderLayout.CENTER);
        frame.add(saveItem, BorderLayout.NORTH);
        frame.pack();
        frame.setSize(image.getImage().getWidth(), image.getImage().getHeight());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


    }

    public void setImage(PaintImage img) {
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.removeAll();
        frame.add(img, BorderLayout.CENTER);
        frame.pack();
        frame.repaint();
    }



}
