package com.daleszynski.raytracer.java.raytracer.render_strategy.producer_consumer;


import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

//TODO JavaDoc
public class RenderProducer implements Runnable  {

    private final BlockingQueue<Rectangle> queue;
    private final int width;
    private final int height;

    public RenderProducer(final BlockingQueue<Rectangle> queue, int width, int height) {
        if (queue == null) {
            throw new IllegalArgumentException("queue must not be null");
        }
        this.queue = queue;
        this.width = width;
        this.height = height;
    }
    @Override
    public void run() {

        Rectangle start = new Rectangle(0,0, width, height);
        List<Rectangle> tmp = tileRect(start);
        List<Rectangle> rectangles = new LinkedList<>();
        for (int i = 0; i < tmp.size(); i++) {
            rectangles.addAll(tileRect(tmp.get(i)));
        }

        System.out.println(rectangles.size());
        try {
            for(Rectangle r: rectangles) {
                queue.put(r);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public List<Rectangle> tileRect(Rectangle rect) {
        List<Rectangle> rects = new LinkedList<>();
        Rectangle r1 = new Rectangle(rect.x, rect.y, rect.width/2, rect.height/2);
        Rectangle r2 = new Rectangle(rect.width/2, rect.y, rect.width, rect.height/2);
        Rectangle r3 = new Rectangle(rect.x, rect.height/2, rect.width/2, rect.height);
        Rectangle r4 = new Rectangle(rect.width/2, rect.height/2, rect.width, rect.height);
        rects.add(r1);
        rects.add(r2);
        rects.add(r3);
        rects.add(r4);
        return rects;


    }


}
