import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

public class Screen extends Canvas {
	private static final long serialVersionUID = 5241834608422998589L;
	private static Font sanSerifFont = new Font("SanSerif", Font.BOLD, 72);
	
	public final int LEFT = 37;
	public final int UP = 38;
	public final int RIGHT = 39;
	public final int DOWN = 40;
	public final int A = 65;
	public final int S = 83;
	public final int W = 87;
	public final int D = 68;
	
	public final int updatesPerFrame = 2;
	
	private BufferStrategy bufferStrategy;
	private Graphics2D graphics;
	
	public Screen(int width, int height) {
		super();
		setSize(width, height);
	}
	
	public void setup() {

		Program.paddles.add(new Paddle(A, D, -0.5));
		Program.paddles.add(new Paddle(LEFT, RIGHT, 0.5));
		Program.balls.add(new Ball(getWidth()/2.0, getHeight() - 50, 0, -2, Color.BLACK));
		
		
		for (double x=30; x<getWidth() - 50; x += 60) {
			for (double y=30; y<getHeight() - 100; y += 30) {
				if (Math.random() < 0.1)
					Program.bricks.add(new Brick(new Vector(x, y), Color.BLACK));
			}
		}
		
		createBufferStrategy(3);
	    bufferStrategy = getBufferStrategy();
	    graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
	}
	
	public void draw() {
        for (int i=0; i<updatesPerFrame; i++) {
            updateObjects();
            removeObjects();
        }
        display();
        bufferStrategy.show();
	}
	
	private void display() {
    	graphics.setColor(Color.WHITE);
        graphics.clearRect(0, 0, getWidth(), getHeight());
        graphics.fillRect(0, 0, getWidth(), getHeight());
        
        displayObjects();
	}
	
	private void updateObjects() {
		//update objects
        for (Paddle paddle : Program.paddles)
        	paddle.updatePosition();
        
        for (Ball ball : Program.balls) 
        	ball.updatePosition();
	}
	
	private void displayObjects() {
		//display objects
        for (Paddle paddle : Program.paddles)
        	paddle.display(graphics);
        
        for (Brick brick : Program.bricks)
        	brick.display(graphics);
        
        for (Ball ball : Program.balls) 
        	ball.display(graphics);
	}
	
	private void removeObjects() {
		while (!Program.ballsToRemove.isEmpty()) {
			Ball ball = Program.ballsToRemove.remove();
			Program.balls.remove(ball);
		}
		
		while (!Program.bricksToRemove.isEmpty()) {
			Brick brick = Program.bricksToRemove.remove();
			Program.bricks.remove(brick);
		}
		
		if (Program.balls.isEmpty()) 
			noBallsLeft();
		
	}
	
	private void noBallsLeft() {
		graphics.setFont(sanSerifFont);
	    FontMetrics fm = graphics.getFontMetrics();
		try {
			for (int i=5; i>=0; i--) {
				display();
				
				graphics.setColor(Color.RED);
				String str = "Restarting in " + i;
			    int w = fm.stringWidth(str);
			    int h = fm.getAscent();
			    graphics.drawString(str, (getWidth() - w) / 2, (getHeight() - h) / 2);
		        
			    bufferStrategy.show();
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		createBalls();
	}
	
	private void createBalls() {
		for (Paddle paddle : Program.paddles) {
			Vector position = new Vector(paddle.position.x, paddle.position.y - Paddle.radius);
			double angle = (Math.random()/2.0 - 3.0/4.0)*Math.PI; // range of [-3*pi/4, -pi/4]
			Ball ball = new Ball(position, angle, Color.BLACK);
		}
	}
	
	public Graphics2D getGraphics() {
		return graphics;
	}
	
}
