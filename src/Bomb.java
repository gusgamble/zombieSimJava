
import java.awt.*;

public class Bomb {
	private int xPosition;
	private int yPosition;
	private static final Color COLOR = Color.RED;


	public Bomb(int maxX, int maxY, boolean[][] walls) {
		int xPos = Helper.nextInt(maxX);
		int yPos = Helper.nextInt(maxY);

		while (!Humanoid.isValidMove(xPos, yPos, walls)) {
			xPos = Helper.nextInt(maxX);
			yPos = Helper.nextInt(maxY);
		}

		xPosition = xPos;
		yPosition = yPos;
	}

	public int getX() {
		return this.xPosition;
	}

	public int getY() {
		return this.yPosition;
	}

	public static Color getColor() {
		return COLOR;
	}

}
