import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class LivesCounter {
	private static int MAX_LIVES = 3;
	private static double radius = 30;
	private static double bufferSpace = 5;
	private static int fontSize = 32;
	private static Font font = new Font("SanSerif", Font.PLAIN, fontSize);
	private static Color ballColor = new Color(255, 0, 0, 100);
	private static Color containerColor = new Color(0, 0, 0, 100);
	
	private Ellipse2D.Double[] balls = new Ellipse2D.Double[MAX_LIVES];
	private String[] displayStrings = new String[MAX_LIVES];
	private Rectangle2D.Double container;
	private int lives = MAX_LIVES;
	
	public LivesCounter() {
		double diameter = 2*radius;
		for (int i=0; i<MAX_LIVES; i++) {
			balls[i] = new Ellipse2D.Double(Program.getCanvas().getWidth() - diameter*(i+1) - bufferSpace*i, fontSize + bufferSpace, diameter, diameter);
			if (i == 0) 
				displayStrings[i] = "last life";
			else
				displayStrings[i] = (i+1) + " lives left";
		}
		double width = diameter*MAX_LIVES + bufferSpace*(MAX_LIVES-1);
		container = new Rectangle2D.Double(Program.getCanvas().getWidth() - width, 0, width, fontSize + bufferSpace + diameter);
	}
	
	public int getLives() {
		return lives;
	}
	
	public void reset() {
		lives = MAX_LIVES;
	}
	
	public void decrement() {
		lives--;
	}
	
	public void display(Graphics2D graphics) {
		graphics.setColor(containerColor);
		graphics.fill(container);
		
		graphics.setColor(Color.WHITE);
		graphics.setFont(font);
		FontMetrics fm = graphics.getFontMetrics();
		
		String str = displayStrings[lives-1];
		
		int strWidth = fm.stringWidth(str);
		int strHeight = fm.getAscent();
		graphics.drawString(str, Program.getCanvas().getWidth() - strWidth, strHeight);
		
		graphics.setColor(ballColor);
		for (int i=0; i<lives; i++) {
			graphics.fill(balls[i]);
		}
	}
	
}
