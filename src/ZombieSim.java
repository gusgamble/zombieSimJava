import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class ZombieSim extends JFrame implements MouseListener, KeyListener {

	private static final long serialVersionUID = -5176170979783243427L;

	/** The Dot Panel object you will draw to */
	protected static DotPanel dp;

	/* Define constants using static final variables */
	public static final int MAX_X = 200;
	public static final int MAX_Y = 200;
	private static final int DOT_SIZE = 3;
	private static final int NUM_HUMANS = 200;
	private static final int NUM_BUILDINGS = 80;
	private int OFFSET;
	private City world;


	/*
	 * This fills the frame with a "DotPanel", a type of drawing canvas that
	 * allows you to easily draw squares to the screen.
	 */
	public ZombieSim() {
		this.setSize(MAX_X * DOT_SIZE, MAX_Y * DOT_SIZE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Braaiinnnnnsss");
		addMouseListener(this);
		addKeyListener(this);

		/* Create and set the size of the panel */
		dp = new DotPanel(MAX_X, MAX_Y, DOT_SIZE);

		/* Add the panel to the frame */
		Container cPane = this.getContentPane();
		cPane.add(dp);

		/* Initialize the DotPanel canvas:
		 * You CANNOT draw to the panel BEFORE this code is called.
		 * You CANNOT add new widgets to the frame AFTER this is called.
		 */
		this.pack();
		dp.init();
		dp.clear();
		dp.setPenColor(Color.red);
		this.setVisible(true);

		/* Create our city */
		world = new City(MAX_X, MAX_Y, NUM_BUILDINGS, NUM_HUMANS);

		OFFSET = dp.getInsets().bottom;


		/* This is the Run Loop (aka "srculation loop" or "game loop")
		 * It will loop forever, first updating the state of the world
		 * (e.g., having humans take a single step) and then it will
		 * draw the newly updated srculation. Since we don't want
		 * the srculation to run too fast for us to see, it will sleep
		 * after repainting the screen. Currently it sleeps for
		 * 33 milliseconds, so the program will update at about 30 frames
		 * per second.
		 */
		while (true)
		{
			// Run update rules for world and everything in it
			world.update();
			// Draw to screen and then refresh
			world.draw();
			dp.repaintAndSleep(33);

		}

	}

	public static void main(String[] args) {
		/* Create a new GUI window  */
		new ZombieSim();
	}

	@Override

	public void mouseClicked(MouseEvent e) {

	}

	/**
	 * Invoked when a mouse button has been pressed on a component.
	 *
	 * @param
	 */
	@Override

	public void mousePressed(MouseEvent e) {
		int x = e.getX() / dp.getDotSize();
		int y = (int) (((double) e.getY() / (double) dp.getDotSize()) + (double) (OFFSET - 8));
		if (Humanoid.isValidMove(x, y, City.walls)) {
			City.addZombie(x, y);
		}
		else {
			System.out.println(x + " , " + y);
			System.out.println("Not a valid location!");
		}

	}

	@Override

	public void mouseReleased(MouseEvent e) {

	}

	@Override

	public void mouseEntered(MouseEvent e) {

	}

	@Override

	public void mouseExited(MouseEvent e) {

	}

	@Override

	public void keyTyped(KeyEvent e) {

	}

	/**
	 * Invoked when a key has been pressed.
	 * See the class description for {@link KeyEvent} for a definition of
	 * a key pressed event.
	 *
	 * @param e
	 */
	@Override

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			City.clearStatics();
			this.world = new City(MAX_X, MAX_Y, NUM_BUILDINGS, NUM_HUMANS);
		}
	}

	@Override

	public void keyReleased(KeyEvent e) {

	}

}
