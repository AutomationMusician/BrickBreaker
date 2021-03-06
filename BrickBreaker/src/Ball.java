// Ball.java

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Ball {
	private static final double radius = 10;
	private static final double paddleSqDist = (radius + Paddle.radius)*(radius + Paddle.radius);
	private final double lowerBound, rightBound;
	private Color color = Color.BLACK;
	private Ellipse2D.Double circle = new Ellipse2D.Double();
	private Vector position;
	private Vector velocity;
	
	public Ball(Vector startPosition, boolean paddle) {	
		circle.height = circle.width = 2*radius;
		rightBound = Program.getCanvas().getWidth() - radius;
		lowerBound = Program.getCanvas().getHeight() - radius*(Paddle.radius - Paddle.height)/Paddle.radius;
		double angle;
		if (paddle) {
			angle = (-3.0/4.0 + Math.random()/2.0)*Math.PI; // range of [-3*pi/4, -pi/4)
			position = new Vector(startPosition.x, startPosition.y - Paddle.radius);
		} else {
			angle = 2.0*Math.random()*Math.PI; // range of [0, 2*pi)
			position = new Vector(startPosition.x, startPosition.y);
		}
		velocity = (new Vector(angle)).mult(Program.speed);
	}
	
	public void updatePosition() {
		position.add(velocity);
		checkBoundry();
		checkPaddles();
		checkBricks();
	}
	
	private void checkBoundry() {
		if (position.x < radius) {
			position.x = radius;
			velocity.x = -velocity.x;
		}
		else if (position.x > rightBound) {
			position.x = rightBound;
			velocity.x = -velocity.x;
		}
		
		if (position.y > lowerBound) 
			Program.ballsToRemove.add(this);
		else if (position.y < radius) {
			position.y = radius;
			velocity.y = -velocity.y;
		}
	}
	
	private void checkPaddles() {
		Paddle closest = null;
		Vector relativePosition = null;
		double squareDistance = Double.POSITIVE_INFINITY;
		for (Paddle paddle : Program.paddles) {
			Vector relPosition = Vector.sub(position, paddle.position);
			double sqMag = relPosition.squareMag();
			if (sqMag < squareDistance) {
				squareDistance = sqMag;
				closest = paddle;
				relativePosition = relPosition;
			}
		}
		
		if (squareDistance < paddleSqDist) {
			double angle = relativePosition.heading() + Math.PI/2;
			velocity.rotate(angle);
			velocity.y = -velocity.y;
			velocity.rotate(-angle);
			
			relativePosition.resize(radius + Paddle.radius);
			position = Vector.add(closest.position, relativePosition);
		}
	}
	
	private void checkBricks() {
		Brick closest = null;
		Vector relativePosition = null;
		double squareDistance = Double.POSITIVE_INFINITY;
		for (Brick brick : Program.bricks) {
			Vector relPosition = Vector.sub(position, brick.position);
			double sqMag = relPosition.squareMag();
			if (sqMag < squareDistance) {
				squareDistance = sqMag;
				closest = brick;
				relativePosition = relPosition;
			}
		}
		
		double xDistance = Brick.width/2.0 + radius;
		double yDistance = Brick.height/2.0 + radius;
		
		if (closest != null
			&& relativePosition.x > -xDistance && relativePosition.x < xDistance 
			&& relativePosition.y > -yDistance && relativePosition.y < yDistance) {
			Brick.ContactType contactType = closest.contactType(position);
			switch (contactType) {
				case LEFT:
				case RIGHT:
					velocity.x = -velocity.x;
					break;
				case TOP:
				case BOTTOM:
					velocity.y = -velocity.y;
			}
			
			switch (contactType) {
				case LEFT:
					position.x = closest.position.x - xDistance;
					break;
				case RIGHT:
					position.x = closest.position.x + xDistance;
					break;
				case TOP:
					position.y = closest.position.y - yDistance;
					break;
				case BOTTOM:
					position.y = closest.position.y + yDistance;
			}
			
			closest.contact();
		}
	}
	
	public void checkBall(Ball that) {
		Vector relativePosition = Vector.sub(that.position, this.position);
		if (relativePosition.squareMag() < 4*Ball.radius*Ball.radius) {
			double relativeAngle = relativePosition.heading();
			double rotationAngle = relativeAngle + Math.PI/2;
			
			this.velocity.rotate(rotationAngle);
			this.velocity.y = -this.velocity.y;
			this.velocity.rotate(-rotationAngle);
			
			that.velocity.rotate(rotationAngle);
			that.velocity.y = -that.velocity.y;
			that.velocity.rotate(-rotationAngle);
			
			relativePosition.resize(2*Ball.radius);
			that.position = Vector.add(this.position, relativePosition);
		}
	}
	
	public void display(Graphics2D graphics) {
		circle.x = position.x - radius;
		circle.y = position.y - radius;
		graphics.setColor(color);
		try {
			graphics.fill(circle);
		} catch (java.lang.ClassCastException e) {
			e.printStackTrace();
		}
	}
}
