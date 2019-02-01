// Brick.java

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Brick {
	public enum ContactType { TOP, BOTTOM, LEFT, RIGHT };
	public static final double width = 100;
	public static final double height = 50;
	
	public Vector position;
	public Color color = Color.BLACK;
	private int contacts = 1;
	private Rectangle2D.Double rect2d = new Rectangle2D.Double();
	
	public Brick(Vector position) {
		this.position = position;
		rect2d.width = width;
		rect2d.height = height;
	}
	
	public void display(Graphics2D graphics) {
		rect2d.x = position.x - width/2;
		rect2d.y = position.y - height/2;
		graphics.setColor(color);
		graphics.fill(rect2d);
	}
	
	public ContactType contactType(Vector position) {
		Vector relativePosition = Vector.sub(position, this.position);
		
		// width must be greater than or equal to height
		double focalPointDistance = (width - height)/2.0;
		if (relativePosition.x < -focalPointDistance) {
			Vector leftFocalPoint = new Vector(this.position.x - focalPointDistance, this.position.y);
			double angle = Vector.sub(position, leftFocalPoint).heading();
			if (angle > 0) {
				if (angle >= 3.0*Math.PI/4.0) 
					return ContactType.LEFT;
				else 
					return ContactType.BOTTOM;
			} else {
				if (angle <= -3.0*Math.PI/4.0) 
					return ContactType.LEFT;
				else 
					return ContactType.TOP;
			}
		} else if (relativePosition.x > focalPointDistance) {
			Vector rightFocalPoint = new Vector(this.position.x + focalPointDistance, this.position.y);
			double angle = Vector.sub(position, rightFocalPoint).heading();
			if (angle > Math.PI/4.0) 
				return ContactType.BOTTOM;
			else if (angle < -Math.PI/4.0)
				return ContactType.TOP;
			else
				return ContactType.RIGHT;
		} else {
			if (relativePosition.y <= 0) 
				return ContactType.TOP;
			else
				return ContactType.BOTTOM;
		}
	}
	
	public void contact() {
		--contacts;
		if (contacts <= 0) 
			Program.bricksToRemove.add(this);
	}
}
