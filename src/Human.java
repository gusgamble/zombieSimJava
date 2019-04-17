import java.awt.*;

public class Human extends Humanoid {


	public Human(int maxX, int maxY, boolean[][] walls) {
		super(maxX, maxY, walls);
		moveProbability = 10;
		COLOR = Color.white;
	}

	public void move(boolean[][] walls, boolean[][] zombieList) {
		boolean shouldRun = lookAround(zombieList);
		if (shouldRun) {
			run(walls);
		}
		else {
			randomMove(walls);
		}
	}

	private void run(boolean[][] walls) {
		direction = (direction + 2) % 4;

		if (direction == 0) {
			if (isValidMove(this.xPosition, this.yPosition + 2, walls)) {
				this.yPosition = this.yPosition + 2;
			}
			else {
				direction = (direction + 1) % 4;
				run(walls);
			}
		}
		else if (direction == 1) {
			if (isValidMove(this.xPosition + 2, this.yPosition, walls)) {
				this.xPosition = this.xPosition + 2;
			}
			else {
				direction = (direction + 1) % 4;
				run(walls);
			}
		}
		else if (direction == 2) {
			if (isValidMove(this.xPosition, this.yPosition - 2, walls)) {
				this.yPosition = this.yPosition - 2;
			}
			else {
				direction = (direction + 1) % 4;
				run(walls);
			}
		}
		else {
			if (isValidMove(this.xPosition - 2, this.yPosition, walls)) {
				this.xPosition = this.xPosition - 2;
			}
			else {
				direction = (direction + 1) % 4;
				run(walls);
			}
		}
	}

}
