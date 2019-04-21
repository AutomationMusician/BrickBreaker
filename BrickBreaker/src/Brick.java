// Brick.java

import java.awt.Color;
//import java.awt.Font;
//import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Brick {
	public enum ContactType { TOP, BOTTOM, LEFT, RIGHT };
	//private static Font contactFont = new Font("SanSerif", Font.PLAIN, 24);
	public static final double width = 100;
	public static final double height = 50;
	public static final int maxContacts = 3;
	private static double[] probabilities = new double[maxContacts];
	
	static {
		double sum = 0;
		for (int i=0; i<maxContacts; i++) {
			probabilities[i] = Math.pow(i + 1.0, -2);
			sum += probabilities[i];
		}
		for (int i=0; i<maxContacts; i++) 
			probabilities[i] /= sum;
	}
	
	public Vector position;
	public float hue;
	public Color color;
	//private Color textColor;
	private int contacts;
	private boolean hasBall;
	private Rectangle2D.Double rect2d = new Rectangle2D.Double();
	
	
	public Brick(Vector position, float hue) {
		this.position = position;
		this.hue = hue;
		rect2d.width = width;
		rect2d.height = height;
		
		double randomNum = Math.random();
		
		for (contacts = 0; randomNum > probabilities[contacts]; contacts++)
			randomNum -= probabilities[contacts];
		contacts++;
		updateColor();
		
		hasBall = (Math.random() < 0.2);
	}
	
	public void display(Graphics2D graphics) {
		rect2d.x = position.x - width/2;
		rect2d.y = position.y - height/2;
		graphics.setColor(color);
		try {
			graphics.fill(rect2d);
		} catch (java.lang.ClassCastException e) {
			e.printStackTrace();
		}
		
		/*
		graphics.setColor(textColor);
		graphics.setFont(contactFont);
		FontMetrics fm = graphics.getFontMetrics();
		String str = Integer.toString(contacts);
		int w = fm.stringWidth(str);
	    int h = fm.getAscent();
	    int xPos = (int)(position.x - w/2);
	    int yPos = (int)(position.y + h/2);
	    graphics.drawString(str, xPos, yPos);
	    */
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
	
	private void updateColor() {
		float colorBrightness = 1f - (float)(contacts - 1)/maxContacts;
		color = Color.getHSBColor(hue, 1f, colorBrightness);
		
		/*
		int textBrightness;
		if (colorBrightness > 0.75f)
			textBrightness = 0;
		else
			textBrightness = 255;
		textColor = new Color(textBrightness, textBrightness, textBrightness);
		*/
	}
	
	public void contact() {
		--contacts;
		updateColor();
		if (contacts <= 0) {
			Program.bricksToRemove.add(this);
			if (hasBall) {
				Program.ballsToAdd.add(new Ball(position, false));
			}
		}
	}
}
