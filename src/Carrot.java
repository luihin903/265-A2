import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.Polygon;
import java.awt.event.MouseEvent;

import java.util.ArrayList;

import processing.core.PVector;

public class Carrot extends Object {
    
    private int size = 100;
    private static ArrayList<Carrot> carrots = new ArrayList<Carrot>();
    private static boolean adding;
    private static final PVector default_dim = new PVector(100, 100);

    // a constructor that initializes each of the fields with some parameter
    public Carrot(PVector pos, PVector dim, int size, ArrayList<Carrot> carrots) {
        super();
        this.pos = pos;
        this.dim = dim;
        this.size = size;
        Carrot.carrots = carrots;
    }

    private Carrot(MouseEvent e) {
        super(new PVector(e.getX(), e.getY()), default_dim);
    }

    public static ArrayList<Carrot> get() {
        return carrots;
    }

    public static void drawAll(Graphics2D g2) {
        for (Carrot c : carrots) {
            if (c != null) {
                c.draw(g2);
            }
        }
        System.out.println(carrots);
    }

    @Override
    public void draw(Graphics2D g2) {

        if (RabbitApp.drawBoundingBox) drawBoundingBox(g2);
        AffineTransform af = g2.getTransform();

        g2.translate(pos.x, pos.y);
        g2.rotate(Math.toRadians(45));
        g2.scale((double) (size / default_dim.x), (double) (size / default_dim.y));

        g2.setColor(new Color(255, 127, 39));
        Ellipse2D.Double ellipse = new Ellipse2D.Double((int) (-dim.x/4), (int) (-dim.y/2), (int) (dim.x/2), (int) (dim.y/4));
        g2.fill(ellipse);

        int[] xPoints = {(int) (-dim.x/4), 0, (int) (dim.x/4)};
        int[] yPoints = {(int) (-dim.y/8*3), (int) (dim.y/2), (int) (-dim.y/8*3)};
        Polygon polygon = new Polygon(xPoints, yPoints, 3);
        g2.fill(polygon);

        g2.setTransform(af);
    }

    public static void eat(int i, Rabbit rabbit) {
        carrots.get(i).size --;
        if (carrots.get(i).size == 0) {
            carrots.remove(i);
            rabbit.start();
        }
    }

    public static void start(MouseEvent e, Dimension s) {
        carrots.add(new Carrot(e));
        adding = true;
    }

    public static void grow(MouseEvent e) {
        if (adding) {
            Carrot carrot = carrots.get(carrots.size() - 1);
            if (carrot.size < 150) carrot.size ++;
            carrot.pos = new PVector(e.getX(), e.getY());
        }
    }

    public static void grow() {
        if (adding) {
            Carrot carrot = carrots.get(carrots.size() - 1);
            if (carrot.size < 150) carrot.size ++;
        }
    }

    public static void stop() {
        adding = false;
    }

    public boolean hit(MouseEvent e) {
        return PVector.dist(new PVector(e.getX(), e.getY()), this.pos) < size/2;
    }

}
