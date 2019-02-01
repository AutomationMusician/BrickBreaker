// Vector.java

public class Vector {
	public double x, y;
	
	public Vector() { }
	
	public Vector(double angle) {
		x = Math.cos(angle);
		y = Math.sin(angle);
	}
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector clone() {
		return new Vector(x, y);
	}
	
	public Vector mult(double factor) {
		x *= factor;
		y *= factor;
		return this;
	}
	
	public Vector add(Vector v) {
		x += v.x;
		y += v.y;
		return this;
	}
	
	public Vector resize(double magnitude) {
		double factor = magnitude/mag();
		x *= factor;
		y *= factor;
		return this;
	}
	
	public double heading() {
		return Math.atan2(y, x);
	}
	
	public double squareMag() {
		return (x*x + y*y);
	}
	
	public double mag() {
		return Math.sqrt(squareMag());
	}
	
	public Vector rotate(double angle) {
		double mag = mag();
		double rotatedAngle = heading() - angle;
		x = mag*Math.cos(rotatedAngle);
		y = mag*Math.sin(rotatedAngle);
		return this;
	}
	
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
	public static Vector add(Vector a, Vector b) {
		return new Vector(a.x + b.x, a.y + b.y);
	}
	
	public static Vector sub(Vector a, Vector b) {
		return new Vector(a.x - b.x, a.y - b.y);
	}
	
}
