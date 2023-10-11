import javax.swing.JFrame;

import processing.core.PVector;

public class RabbitApp extends JFrame {
	
	public static final int margin = 20;
	public static final int FPS = 30;

	public RabbitApp(String title) {
		super(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 600);

		RabbitPanel panel = new RabbitPanel(this.getSize());
		this.add(panel);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		RabbitApp app = new RabbitApp("My Rabbit App");
	}
}
