package com.daleszynski.raytracer.java.image;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * Wird benutzt um den Aktuellen Renderfortschritt zu zeigen
 */

public class DisplayThread extends Thread{


    /**
     * das Fenster
     */
    private final JFrame frame;

    /**
     * Anzahl der fertig gerenderten Pixeln
     */
    public static double finishedPixels = 0;

    /**
     * Anzahl der Pixel
     */
    private final double numberOfPixels;

    /**
     * FLag wird gesetzt wenn das Fenster gescholssen werden soll.
     */
    private boolean closed = false;

    /**
     * Die Fortschrittsanzeige
     */
    private final JProgressBar progressBar;

    /**
     * Erstellt das Fenster
     * @param width Breite
     * @param height Höhe
     * @param img Das darzustellende Bild
     */
    public DisplayThread(final int width, final int height, BufferedImage img) {
        finishedPixels = 0;
        this.numberOfPixels = width*height;
        frame  = new JFrame();
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(new JLabel(new ImageIcon(img)), BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        progressBar = new JProgressBar();
        progressBar.getPercentComplete();
        progressBar.setStringPainted(true);
        frame.add(progressBar, BorderLayout.SOUTH);

    }

    /**
     * Wird von außen benutzt um den Thread zu beenden.
     */
    public void close() {
        this.closed = true;
    }

    @Override
    public void run() {
        while(!closed) {
            try {
                Thread.sleep(15);
                progressBar.setValue((int) (100*finishedPixels/numberOfPixels));
                frame.repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.frame.setVisible(false);
    }
}
