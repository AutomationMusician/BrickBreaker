// Paddle.java

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Paddle {
	public static final double width = 150;
	public static final double height = 15;
	public static final double radius = (width*width + 4*height*height)/(8*height);
	private static final double speed = 5;
	private final Joystick joystick;
	private Color color = Color.BLACK;
	private Ellipse2D.Double circle = new Ellipse2D.Double();
	public Vector position;
	
	public Paddle(double positionCoefficient, Joystick joystick) {
		circle.width = circle.height = 2.0*radius;
		position = new Vector();
		position.x = Program.getCanvas().getWidth()*(1+positionCoefficient)*0.5;
		position.y = Program.getCanvas().getHeight() + radius - height;
		this.joystick = joystick;
	}
	
	public void updatePosition() {
		if (InputController.isHigh(joystick.LEFT))
			position.x -= speed;
		if (InputController.isHigh(joystick.RIGHT))
			position.x += speed;
		
		double leftBoundry = width/2.0;
		if (position.x < leftBoundry)
			position.x = leftBoundry;
		else {
			double rightBoundry = Program.getCanvas().getWidth() - width/2.0;
			if (position.x > rightBoundry)
				position.x = rightBoundry;
		}
	}
	
	public void display(Graphics2D graphics) {
		circle.x = position.x - radius;
		circle.y = position.y - radius;
		graphics.setColor(color);
		graphics.fill(circle);
	}
}
