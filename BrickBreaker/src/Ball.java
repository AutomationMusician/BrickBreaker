import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Ball {
	private static final double speed = 2;
	private static final double radius = 10;
	private Color color = Color.BLACK;
	private Ellipse2D.Double circle = new Ellipse2D.Double();
	private Vector position;
	private Vector velocity;
	
	public Ball(Vector paddlePosition) {	
		circle.height = circle.width = 2*radius;
		
		position = new Vector(paddlePosition.x, paddlePosition.y - Paddle.radius);
		double angle = (-3.0/4.0 + Math.random()/2.0)*Math.PI; // range of [-3*pi/4, -pi/4)
		velocity = (new Vector(angle)).mult(speed);
	}
	
	public void updatePosition() {
		position.add(velocity);
	}
	
	public void display(Graphics2D graphics) {
		circle.x = position.x - radius;
		circle.y = position.y - radius;
		graphics.setColor(color);
		graphics.fill(circle);
	}
}
