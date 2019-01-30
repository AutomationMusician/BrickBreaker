
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
	
	public double heading() {
		return Math.atan2(y, x);
	}
	
	public double squareMag() {
		return (x*x + y*y);
	}
	
	public double mag() {
		return Math.sqrt(squareMag());
	}
	
	public static Vector sub(Vector a, Vector b) {
		return new Vector(a.x - b.x, a.y - b.y);
	}
	
	public static Vector rotate(Vector v, double angle) {
		double mag = v.mag();
		double rotatedAngle = v.heading() + angle;
		return new Vector(mag*Math.cos(rotatedAngle), mag*Math.sin(rotatedAngle));
	}
	
}
