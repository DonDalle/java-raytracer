package com.daleszynski.raytracer.java.image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class is used to Display an BufferedImage. It also has the ability to save an image by clicking it
 */
public class Display {
    private JLabel imageLabel;

    public Display(BufferedImage image) {
        JFrame frame = new JFrame();
        frame.setSize(image.getWidth(), image.getHeight());
        this.imageLabel = new JLabel(new ImageIcon(image));
        initListener(image);
        frame.getContentPane().add(imageLabel);
        frame.setTitle(image.getWidth() + " x " + image.getHeight() + " px");
        frame.setVisible(true);

    }

    private void initListener(final BufferedImage image) {
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser chooser = new JFileChooser();
                int result = chooser.showSaveDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        ImageIO.write(image, "png", new File(chooser.getSelectedFile()
                                + ".png"));
                    } catch (IOException ex) {
                        System.out.println("Die Datei konnte nicht gespeichert werden: "
                                + ex);
                    }
                }

            }
        });
    }

}
