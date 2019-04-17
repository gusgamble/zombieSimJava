
import java.awt.*;

public class Zombie extends Humanoid {

	public Zombie(int maxX, int maxY, boolean[][] walls) {
		super(maxX, maxY, walls);
		moveProbability = 5;
		COLOR = Color.GREEN;
	}

	public Zombie(Human h) {
		this.xPosition = h.xPosition;
		this.yPosition = h.yPosition;
		COLOR = Color.GREEN;
		this.direction = h.direction;
		h = null;
	}

	public Zombie(int x, int y) {
		this.xPosition = x;
		this.yPosition = y;
		COLOR = Color.GREEN;
		this.direction = Helper.nextInt(4);
	}

	public void move(boolean[][] walls, boolean[][] humanList) {
		boolean needToChase = lookAround(humanList);

		if (needToChase) {
			chase(walls);
		}
		else {
			randomMove(walls);
		}
	}

	private void chase(boolean[][] walls) {
		// direction = (direction +2)%4;

		if (direction == 0) {
			if (isValidMove(this.xPosition, this.yPosition + 2, walls)) {
				this.yPosition = this.yPosition + 2;
			}
		}
		else if (direction == 1) {
			if (isValidMove(this.xPosition + 2, this.yPosition, walls)) {
				this.xPosition = this.xPosition + 2;
			}
		}
		else if (direction == 2) {
			if (isValidMove(this.xPosition, this.yPosition - 2, walls)) {
				this.yPosition = this.yPosition - 2;
			}

		}
		else {
			if (isValidMove(this.xPosition - 2, this.yPosition, walls)) {
				this.xPosition = this.xPosition - 2;
			}
		}
	}

}
