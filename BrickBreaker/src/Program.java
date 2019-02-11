// Program.java

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class Program {
	private static Window window;
	private static Display canvas;
	public static ArrayList<Ball> balls = new ArrayList<Ball>();
	public static ArrayList<Paddle> paddles = new ArrayList<Paddle>();
	public static ArrayList<Brick> bricks = new ArrayList<Brick>();
	public static Queue<Ball> ballsToRemove = new ArrayDeque<Ball>();
	public static Queue<Brick> bricksToRemove = new ArrayDeque<Brick>();
	
	public static void main(String[] args) throws InterruptedException {
		window = new Window();
		canvas = window.getCanvas();
		window.run();
	}
	
	public static Window getWindow() {
		return window;
	}
	
	public static Display getCanvas() {
		return canvas;
	}
	
	public static boolean isPressed(int keyCode) {
		return window.isPressed(keyCode);
	}
}
