import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import processing.core.PVector;

public class Rabbit extends Object {

    private PVector vel;
    private int speed;
    private Color color;
    private boolean moving = true;

    // a constructor that initializes each of the fields with some parameter
    public Rabbit(PVector pos, PVector vel, PVector dim, int speed, Color color, boolean moving) {
        super();
        this.pos = pos;
        this.vel = vel;
        this.dim = dim;
        this.speed = speed;
        this.color = color;
        this.moving = moving;
    }

    public Rabbit(PVector pos, PVector dim, int speed) {
        super(pos, dim);
        this.speed = speed;
        this.vel = new PVector(Util.random(-100, 100), Util.random(-100, 100));
        this.color = Color.WHITE;
    }

    @Override
    public void draw(Graphics2D g2) {
        // drawBoundingBox(g2);

        AffineTransform af = g2.getTransform();

        g2.translate(pos.x, pos.y);
        if (vel.x > 0) g2.scale(-1, 1);

        // feet
        g2.setColor(color);
        g2.fillOval((int) (-dim.x/2), (int) (dim.y/12*5), (int) (dim.x/6*4), (int) (dim.y/12));
        g2.fillOval((int) (-dim.x/6*2.5), (int) (dim.y/12*4), (int) (dim.x/6*4), (int) (dim.y/12));
        g2.setColor(Color.BLACK);
        g2.drawOval((int) (-dim.x/2), (int) (dim.y/12*5), (int) (dim.x/6*4), (int) (dim.y/12));
        g2.drawOval((int) (-dim.x/6*2.5), (int) (dim.y/12*4), (int) (dim.x/6*4), (int) (dim.y/12));
        
        // hands
        g2.setColor(color);
        g2.fillOval((int) (-dim.x/2), (int) (dim.y/12*1), (int) (dim.x/6*3), (int) (dim.y/12));
        g2.fillOval((int) (-dim.x/6*2), 0, (int) (dim.x/6*3), (int) (dim.y/12));
        g2.setColor(Color.BLACK);
        g2.drawOval((int) (-dim.x/2), (int) (dim.y/12*1), (int) (dim.x/6*3), (int) (dim.y/12));
        g2.drawOval((int) (-dim.x/6*2), 0, (int) (dim.x/6*3), (int) (dim.y/12));

        // ears
        g2.setColor(color);
        g2.fillOval((int) (-dim.x/6*1.5), (int) (-dim.y/2), (int) (dim.x/6), (int) (dim.y/12*4));
        g2.fillOval((int) (dim.x/6*0.5), (int) (-dim.y/2), (int) (dim.x/6), (int) (dim.y/12*4));
        g2.setColor(Color.BLACK);
        g2.drawOval((int) (-dim.x/6*1.5), (int) (-dim.y/2), (int) (dim.x/6), (int) (dim.y/12*4));
        g2.drawOval((int) (dim.x/6*0.5), (int) (-dim.y/2), (int) (dim.x/6), (int) (dim.y/12*4));

        // body
        g2.setColor(color);
        g2.fillOval((int) (-dim.x/6*2), 0, (int) (dim.x/6*4), (int) (dim.y/2));
        g2.setColor(Color.BLACK);
        g2.drawOval((int) (-dim.x/6*2), 0, (int) (dim.x/6*4), (int) (dim.y/2));

        // head
        g2.setColor(color);
        g2.fillOval((int) (-dim.x/6*2), (int) (-dim.y/12*4), (int) (dim.x/6*4), (int) (dim.y/12*4));
        g2.setColor(Color.BLACK);
        g2.drawOval((int) (-dim.x/6*2), (int) (-dim.y/12*4), (int) (dim.x/6*4), (int) (dim.y/12*4));

        // tail
        g2.setColor(color);
        g2.fillOval((int) (dim.x/6*2), (int) (dim.y/12*3), (int) (dim.x/6), (int) (dim.y/12));
        g2.setColor(Color.BLACK);
        g2.drawOval((int) (dim.x/6*2), (int) (dim.y/12*3), (int) (dim.x/6), (int) (dim.y/12));
                
        // eyes
        g2.setColor(Color.RED);
        g2.fillOval((int) (-dim.x/6), (int) (-dim.y/12*3), (int) (dim.x/6*0.5), (int) (dim.y/12*0.75));
        g2.fillOval((int) (dim.x/6*0.5), (int) (-dim.y/12*3), (int) (dim.x/6*0.5), (int) (dim.y/12*0.75));

        // face
        g2.setColor(Color.BLACK);
        g2.drawLine((int) (-dim.x/6*0.5), (int) (-dim.y/12*2), 0, (int) (-dim.y/12*1.5));
        g2.drawLine((int) (dim.x/6*0.5), (int) (-dim.y/12*2), 0, (int) (-dim.y/12*1.5));
        g2.drawLine(0, (int) (-dim.y/12*1.5), 0, (int) (-dim.y/12*1));
        g2.drawLine(0, (int) (-dim.y/12*1), (int) (-dim.x/6*0.5), (int) (-dim.y/12*0.75));
        g2.drawLine(0, (int) (-dim.y/12*1), (int) (dim.x/6*0.5), (int) (-dim.y/12*0.75));

        g2.setTransform(af);
    }

    public void move(Carrot[] carrots, Dimension s) {
        
        if (moving) {
            seek(carrots);
            vel.normalize();
            vel.mult(speed);

            if (pos.x < dim.x/2 + RabbitApp.margin || pos.x > s.width - RabbitApp.margin - dim.x/2) {
                vel.x *= -1;
            }
            if (pos.y < dim.y/2 + RabbitApp.margin || pos.y > s.height - RabbitApp.margin - dim.y/2) {
                vel.y *= -1;
            }

            pos.add(vel);
        }
    }

    private void seek(Carrot[] carrots) {
        double distance = 9999;

        for (Carrot c : carrots) {
            if (c != null) {
                if (PVector.dist(c.pos, this.pos) < distance) {
                    distance = PVector.dist(c.pos, this.pos);
                    vel = c.pos.copy();
                    vel.sub(this.pos);
                }
            }
        }
    }

    public void eat(Carrot[] carrots) {
        for (int i = 0; i < carrots.length; i ++) {
            if (carrots[i] != null) {
                if (PVector.dist(this.pos, carrots[i].pos) < 50) {
                    stop();
                    Carrot.eat(i, this);
                }
            }
        }
    }

    public void start() {
        moving = true;
    }

    public void stop() {
        moving = false;
    }
}
