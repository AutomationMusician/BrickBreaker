// Brick.java

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Brick {
	public enum ContactType { TOP, BOTTOM, LEFT, RIGHT };
	private static final Vector defualtDimensions = new Vector(100, 50);
	
	public Vector position;
	public Vector dimensions;
	public Color color;
	private int contacts = 1;
	private Rectangle2D.Double rect2d = new Rectangle2D.Double();
	
	public Brick() {
		position = new Vector();
		dimensions = defualtDimensions.clone();
		color = Color.BLACK;
	}
	
	public Brick(Vector position, Color color) {
		this.position = position;
		this.dimensions = defualtDimensions.clone();
		this.color = color;
	}
	
	public Brick(Vector dimensions, Vector position, Color color) {
		this.position = position;
		this.dimensions = dimensions;
		this.color = color;
	}
	
	public Brick(double width, double height, double x, double y, Color color) {
		position = new Vector(x, y);
		dimensions = new Vector(width, height);
		this.color = color;
	}
	
	private void updateRect2d() {
		rect2d.x = position.x - dimensions.x/2;
		rect2d.y = position.y - dimensions.y/2;
		rect2d.width = dimensions.x;
		rect2d.height = dimensions.y;
	}
	
	public void display(Graphics2D graphics) {
		updateRect2d();
		graphics.setColor(color);
		graphics.fill(rect2d);
	}
	
	public ContactType contactType(Vector position) {
		Vector relativePosition = Vector.sub(position, this.position);
		
		// width is greater than or equal to height
		if (dimensions.x >= dimensions.y) {
			double focalPointDistance = (dimensions.x - dimensions.y)/2.0;
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
		return ContactType.TOP;
		// TODO case if height is larger than width
		/*
		else {
			
		}
		*/
	}
	
	public void contact() {
		--contacts;
		if (contacts <= 0) 
			Program.bricksToRemove.add(this);
	}
}
