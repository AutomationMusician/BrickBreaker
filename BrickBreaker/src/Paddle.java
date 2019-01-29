import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Paddle {
	public static final double width = 100;
	public static final double height = 20;
	public static final double radius = (width*width + 4*height*height)/(8*height);
	private static final double speed = 2;
	private Color color = Color.BLACK;
	private Ellipse2D.Double circle = new Ellipse2D.Double();
	public Vector position;
	
	public Paddle(double positionCoefficient) {
		circle.width = circle.height = 2.0*radius;
		position = new Vector();
		position.x = Program.getCanvas().getWidth()*(1+positionCoefficient)*0.5;
		position.y = Program.getCanvas().getHeight() + radius - height;
		System.out.println(radius);
	}
	
	public void updatePosition() {
		// TODO : use button presses to control position
	}
	
	public void display(Graphics2D graphics) {
		circle.x = position.x - radius;
		circle.y = position.y - radius;
		graphics.setColor(color);
		graphics.fill(circle);
	}
}
