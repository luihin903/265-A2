import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import processing.core.PVector;

public class RabbitPanel extends JPanel implements ActionListener {
    
    private Rabbit rabbit;
    private Dimension size;
    private Timer t;

    public RabbitPanel(Dimension initialSize) {
        super();

        size = initialSize;

        rabbit = new Rabbit(new PVector(size.width/2, size.height/2), new PVector(50, 100), 2);
        Carrot.init(5, initialSize);
        Tree.init(5, initialSize);
        Flower.init(5, initialSize);

        t = new Timer((int) (1000/RabbitApp.FPS), this);
        t.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        size = getSize();
        setBackground(Color.GRAY);

        Graphics2D g2 = (Graphics2D) g;

        Forest.draw(g2, size);
        Tree.drawAll(g2);
        Carrot.drawAll(g2);
        Flower.drawAll(g2);
        rabbit.draw(g2);

        // g2.drawOval(0, 0, 100, 100);
        // g2.drawOval(size.width-20-100, size.height-20-100, 100, 100);
        // for (int i = 0; i < 20; i ++) {
        //     g2.drawLine(i*100, 0, i*100, size.height);
        //     g2.drawLine(0, i*100, size.width, i*100);
        // }
        // System.out.println(size.width);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        rabbit.move(Carrot.get(), getSize());
        rabbit.eat(Carrot.get());
        Carrot.count(getSize());

        repaint();
    }
}
