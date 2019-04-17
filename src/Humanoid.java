import java.awt.*;

abstract public class Humanoid {
	protected static int moveProbability;
	protected int xPosition;
	protected int yPosition;
	protected int direction;
	protected Color COLOR;

	protected Humanoid(int maxX, int maxY, boolean[][] walls) {

		int xPos = Helper.nextInt(maxX);
		int yPos = Helper.nextInt(maxY);

		while (!isValidMove(xPos, yPos, walls)) {
			xPos = Helper.nextInt(maxX);
			yPos = Helper.nextInt(maxY);
		}

		xPosition = xPos;
		yPosition = yPos;
		direction = Helper.nextInt(4);

	}

	protected Humanoid() {
	}

	public static boolean isValidMove(int x, int y, boolean[][] walls) {
		return (x > 0 && y > 0) && (x < walls.length && y < walls.length) && !walls[x][y];
	}

	abstract public void move(boolean[][] walls, boolean[][] listOfOtherHumanoids);

	void randomMove(boolean[][] walls) {
		int newX;
		int newY;

		if (Helper.nextInt(moveProbability * 2) == Helper.nextInt(moveProbability)) {
			this.direction = (this.direction + 1) % 4;
		}
		if (Helper.nextInt(moveProbability * 2) == Helper.nextInt(moveProbability)) {
			this.direction = (this.direction - 1) % 4;
		}

		if (direction == 0) {
			if (this.yPosition + 1 <= 0 && this.yPosition + 1 >= 200) {
				this.direction = Helper.nextInt(4);
				randomMove(walls);
			}
			newX = this.xPosition;
			newY = this.yPosition + 1;
			if (isValidMove(newX, newY, walls)) {
				this.xPosition = newX;
				this.yPosition = newY;
			}
			else {
				this.direction = Helper.nextInt(4);
				randomMove(walls);
			}
		}
		else if (direction == 1) {
			if (this.xPosition + 1 <= 0 && this.xPosition + 1 >= 200) {
				this.direction = Helper.nextInt(4);
				randomMove(walls);
			}
			newX = this.xPosition + 1;
			newY = this.yPosition;
			if (isValidMove(newX, newY, walls)) {
				this.xPosition = newX;
				this.yPosition = newY;
			}
			else {
				this.direction = Helper.nextInt(4);
				randomMove(walls);
			}
		}
		else if (direction == 2) {
			if (this.yPosition - 1 <= 0 && this.yPosition - 1 >= 200) {
				this.direction = Helper.nextInt(4);
				randomMove(walls);
			}
			newX = this.xPosition;
			newY = this.yPosition - 1;
			if (isValidMove(newX, newY, walls)) {
				this.xPosition = newX;
				this.yPosition = newY;
			}
			else {
				this.direction = Helper.nextInt(4);
				randomMove(walls);
			}
		}
		else {
			if (this.xPosition - 1 <= 0 && this.xPosition - 1 >= 200) {
				this.direction = Helper.nextInt(4);
				randomMove(walls);
			}
			newX = this.xPosition - 1;
			newY = this.yPosition;
			if (isValidMove(newX, newY, walls)) {
				this.xPosition = newX;
				this.yPosition = newY;
			}
			else {
				this.direction = Helper.nextInt(4);
				randomMove(walls);
			}
		}
	}

	protected boolean lookAround(boolean[][] listOfOtherHumanoids) {

		int squaresToLook = 10;
		if (direction == 0) {
			while (squaresToLook > 2) {
				if (this.yPosition + squaresToLook <= 0 && this.yPosition + squaresToLook >= 200) {
					return listOfOtherHumanoids[this.xPosition][this.yPosition];
				}
				squaresToLook--;
			}
		}
		else if (direction == 1) {
			while (squaresToLook > 2) {
				if (this.xPosition + squaresToLook <= 0 && this.xPosition + squaresToLook >= 200) {
					return listOfOtherHumanoids[this.xPosition][this.yPosition];
				}
				squaresToLook--;
			}

		}
		else if (direction == 2) {
			while (squaresToLook > 2) {
				if (this.yPosition - squaresToLook <= 0 && this.yPosition - squaresToLook >= 200) {
					return listOfOtherHumanoids[this.xPosition][this.yPosition];
				}
				squaresToLook--;
			}

		}
		else {
			while (squaresToLook > 2) {
				if (this.xPosition - squaresToLook <= 0 && this.xPosition - squaresToLook >= 200) {
					return listOfOtherHumanoids[this.xPosition][this.yPosition];
				}
				squaresToLook--;
			}
		}
		return false;
	}

	public int getX() {
		return this.xPosition;
	}

	public int getY() {
		return this.yPosition;
	}

	public Color getColor() {
		return COLOR;
	}

}
