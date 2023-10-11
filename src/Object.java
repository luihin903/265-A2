import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import processing.core.PVector;

public class Object {
    
    protected PVector pos, dim;

    public Object(PVector pos, PVector dim) {
        this.pos = pos;
        this.dim = dim;
    }

    public Object() {}

    public Object(Dimension s) {
        pos = Util.random(s);
    }

    public Object(Dimension s, PVector default_dim) {
        dim = default_dim;
        pos = Util.random(s, dim);
    }

    protected void draw(Graphics2D g2) {}

    public void drawBoundingBox(Graphics2D g2) {
        AffineTransform af = g2.getTransform();

        g2.setColor(Color.RED);
        g2.drawRect((int) (pos.x-dim.x/2), (int) (pos.y-dim.y/2), (int) dim.x, (int) dim.y);

        g2.setTransform(af);
    }
}
