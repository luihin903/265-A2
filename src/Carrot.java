import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import java.util.Arrays;

import processing.core.PVector;

public class Carrot extends Object {
    
    private int deathCount;
    private static Carrot[] carrots;
    private static int capacity;
    private static int size;
    private static int counter;
    private static final PVector default_dim = new PVector(100, 100);

    // a constructor that initializes each of the fields with some parameter
    public Carrot(PVector pos, PVector dim, int deathCount, Carrot[] carrots, int capacity, int size, int counter) {
        super();
        this.pos = pos;
        this.dim = dim;
        this.deathCount = deathCount;
        Carrot.carrots = carrots;
        Carrot.capacity = capacity;
        Carrot.size = size;
        Carrot.counter = counter;
    }

    public static void init(int maxAmount, Dimension s) {
        capacity = maxAmount;
        size = 0;
        carrots = new Carrot[capacity];
        counter = RabbitApp.FPS * 5;
    }

    private Carrot(Dimension s) {
        super(s, default_dim);
        size ++;
        deathCount = RabbitApp.FPS * 2;
    }

    public static void spawn(Dimension s) {
        if (size < capacity) {
            for (int i = 0; i < carrots.length; i ++) {
                if (carrots[i] == null) {
                    carrots[i] = new Carrot(s);
                    break;
                }
            }
        }
    }

    public static Carrot[] get() {
        return carrots;
    }

    public static void drawAll(Graphics2D g2) {
        for (Carrot c : carrots) {
            if (c != null) {
                c.draw(g2);
            }
        }
        System.out.println(Arrays.toString(carrots));
    }

    @Override
    public void draw(Graphics2D g2) {

        // drawBoundingBox(g2);
        AffineTransform af = g2.getTransform();

        g2.translate(pos.x, pos.y);
        g2.rotate(Math.toRadians(45));
        g2.scale((double) deathCount/(RabbitApp.FPS*2), (double) deathCount/(RabbitApp.FPS*2));

        g2.setColor(new Color(255, 127, 39));
        g2.fillOval((int) (-dim.x/4), (int) (-dim.y/2), (int) (dim.x/2), (int) (dim.y/4));
        int[] xPoints = {(int) (-dim.x/4), 0, (int) (dim.x/4)};
        int[] yPoints = {(int) (-dim.y/8*3), (int) (dim.y/2), (int) (-dim.y/8*3)};
        g2.fillPolygon(xPoints, yPoints, 3);

        g2.setTransform(af);
    }

    public static void count(Dimension s) {
        counter --;
        if (counter == 0) {
            spawn(s);
            reset();
        }
    }

    public static void reset() {
        counter = RabbitApp.FPS * 5;
    }

    public static void eat(int i, Rabbit rabbit) {
        carrots[i].deathCount --;
        if (carrots[i].deathCount == 0) {
            carrots[i] = null;
            reset();
            size --;
            rabbit.start();
        }
    }
}
