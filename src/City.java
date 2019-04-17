
import java.awt.*;


public class City {

	/** walls is a 2D array with an entry for each space in the city.
	 *  If walls[x][y] is true, that means there is a wall in the space.
	 *  else the space is free. Humans should never go into spaces that
	 *  have a wall.
	 */
	static boolean walls[][];
	private int width, height;
	private Human humans[];
	private boolean humanList[][];
	private static Zombie[] zombies;
	private static boolean[][] zombieList;
	private static int numZombies = 1;
	private int numBombs = 2 + Helper.nextInt(9);
	private Bomb bombs[];
	private boolean bombList[][];
	private int bombRadius = 10;


	/**
	 * Create a new City and fill it with buildings and people.
	 * @param w width of city
	 * @param h height of city
	 * @param numB number of buildings
	 * @param numP number of people
	 */
	public City(int w, int h, int numB, int numP) {
		width = w;
		height = h;
		walls = new boolean[w][h];

		humans = new Human[numP];
		humanList = new boolean[w][h];

		zombies = new Zombie[600];
		zombieList = new boolean[w][h];

		bombs = new Bomb[numBombs];
		bombList = new boolean[w][h];

		randomBuildings(numB);
		populate(numP);


	}

	/**
	 * Generates numPeople random people distributed throughout the city.
	 * People must not be placed inside walls!
	 *
	 * @param numPeople the number of people to generate
	 */
	private void populate(int numPeople) {
		// Generate numPeople new humans randomly placed around the city.
		for (int i = 0; i < numPeople; i++) {
			humans[i] = new Human(ZombieSim.MAX_X, ZombieSim.MAX_Y, walls);
			humanList[humans[i].getX()][humans[i].getY()] = true;
		}
		// add in the zombie
		zombies[numZombies - 1] = new Zombie(ZombieSim.MAX_X, ZombieSim.MAX_Y, walls);
		zombieList[zombies[numZombies - 1].getX()][zombies[numZombies - 1].getY()] = true;
		numZombies++;

		// add the bombs
		for (int i = 0; i < numBombs; i++) {
			bombs[i] = new Bomb(ZombieSim.MAX_X, ZombieSim.MAX_Y, walls);
			bombList[bombs[i].getX()][bombs[i].getY()] = true;
		}

	}

	/**
	 * Generates a random set of numB buildings.
	 *
	 * @param numB the number of buildings to generate
	 */
	private void randomBuildings(int numB) {
		/* Create buildings of a reasonable size for this map */
		int bldgMaxSize = width / 6;
		int bldgMinSize = width / 50;

		/* Produce a bunch of random rectangles and fill in the walls array */
		for (int i = 0; i < numB; i++) {
			int tx, ty, tw, th;
			tx = Helper.nextInt(width);
			ty = Helper.nextInt(height);
			tw = Helper.nextInt(bldgMaxSize) + bldgMinSize;
			th = Helper.nextInt(bldgMaxSize) + bldgMinSize;

			for (int r = ty; r < ty + th; r++) {
				if (r >= height) {
					continue;
				}
				for (int c = tx; c < tx + tw; c++) {
					if (c >= width) {
						break;
					}
					walls[c][r] = true;
				}
			}
		}
	}

	/**
	 * Updates the state of the city for a time step.
	 */
	public void update() {
		// Move humans, zombies, etc
		for (int i = 0; i < humanList[0].length; i++) {
			for (int k = 0; k < humanList[1].length; k++) {
				humanList[i][k] = false;
			}
		}

		for (int i = 0; i < humans.length; i++) {
			if (humans[i] != null) {
				humans[i].move(walls, zombieList);
				humanList[humans[i].getX()][humans[i].getY()] = true;

				if (zombieList[humans[i].getX()][humans[i].getY()]) {
					humanList[humans[i].getX()][humans[i].getY()] = false;
					zombies[numZombies - 1] = new Zombie(humans[i]);
					numZombies++;
					humans[i] = null;
				}
				else if (humans[i].getX() + 1 < 200 && humans[i].getX() + 1 > 0 &&
						 zombieList[humans[i].getX() + 1][humans[i].getY()]) {
					humanList[humans[i].getX() + 1][humans[i].getY()] = false;
					zombies[numZombies - 1] = new Zombie(humans[i]);
					numZombies++;
					humans[i] = null;
				}
				else if (humans[i].getX() - 1 < 200 && humans[i].getX() - 1 > 0 &&
						 zombieList[humans[i].getX() - 1][humans[i].getY()]) {
					humanList[humans[i].getX() - 1][humans[i].getY()] = false;
					zombies[numZombies - 1] = new Zombie(humans[i]);
					numZombies++;
					humans[i] = null;

				}
				else if (humans[i].getY() + 1 < 200 && humans[i].getY() + 1 > 0 &&
						 zombieList[humans[i].getX()][humans[i].getY() + 1]) {
					humanList[humans[i].getX()][humans[i].getY() + 1] = false;
					zombies[numZombies - 1] = new Zombie(humans[i]);
					numZombies++;
					humans[i] = null;

				}
				else if (humans[i].getY() - 1 < 200 && humans[i].getY() - 1 > 0 &&
						 zombieList[humans[i].getX()][humans[i].getY() - 1]) {
					humanList[humans[i].getX()][humans[i].getY() - 1] = false;
					zombies[numZombies - 1] = new Zombie(humans[i]);
					numZombies++;
					humans[i] = null;

				}

				if (humans[i] != null && bombList[humans[i].getX()][humans[i].getY()]) {
					detonateBomb(humans[i].getX(), humans[i].getY());
				}
			}

		}

		for (int i = 0; i < zombieList[0].length; i++) {
			for (int k = 0; k < zombieList[1].length; k++) {
				zombieList[i][k] = false;
			}
		}
		for (int i = 0; i < zombies.length; i++) {
			if (zombies[i] != null) {
				zombies[i].move(walls, humanList);
				zombieList[zombies[i].getX()][zombies[i].getY()] = true;
				if (zombies[i] != null && bombList[zombies[i].getX()][zombies[i].getY()]) {
					detonateBomb(zombies[i].getX(), zombies[i].getY());
				}
			}
		}

	}

	/**
	 * Draw the buildings and all humans.
	 */
	public void draw() {
		/* Clear the screen */
		ZombieSim.dp.clear(Color.black);

		drawWalls();
		drawHumans();
		drawZombies();
		drawBombs();
	}

	/**
	 * Draw the buildings.
	 * First set the color for drawing, then draw a dot at each space
	 * where there is a wall.
	 */
	private void drawWalls() {
		ZombieSim.dp.setPenColor(Color.DARK_GRAY);
		for (int r = 0; r < height; r++)
		{
			for (int c = 0; c < width; c++)
			{
				if (walls[c][r])
				{
					ZombieSim.dp.drawDot(c, r);
				}
			}
		}
	}

	private void drawHumans() {
		for (Human h : humans) {
			if (h != null) {
				ZombieSim.dp.setPenColor(h.getColor());
				ZombieSim.dp.drawDot(h.getX(), h.getY());
			}
		}
	}

	private void drawZombies() {
		for (Zombie z : zombies) {
			if (z != null) {
				ZombieSim.dp.setPenColor(z.getColor());
				ZombieSim.dp.drawDot(z.getX(), z.getY());
			}

		}
	}

	private void drawBombs() {
		for (Bomb b : bombs) {
			if (b != null) {
				ZombieSim.dp.setPenColor(Bomb.getColor());
				ZombieSim.dp.drawDot(b.getX(), b.getY());
			}
		}
	}

	private void detonateBomb(int x, int y) {
		for (int i = x - bombRadius; i <= x + bombRadius; i++) {
			for (int j = y - bombRadius; j <= y + bombRadius; j++) {
				if (i > 0 && i < 200 && j > 0 && j < 200) {
					walls[i][j] = false;
					zombieList[i][j] = false;
					humanList[i][j] = false;

					for (int z = 0; z < zombies.length; z++) {
						if (zombies[z] != null) {
							if (zombies[z].getX() == i && zombies[z].getY() == j) {
								zombies[z] = null;
							}
						}
					}
					for (int h = 0; h < humans.length; h++) {
						if (humans[h] != null) {
							if (humans[h].getX() == i && humans[h].getY() == j) {
								humans[h] = null;
							}
						}
					}
				}
			}
		}
		for (int i = 0; i < bombs.length; i++) {
			if (bombs[i] != null && bombs[i].getX() == x && bombs[i].getY() == y) {
				bombs[i] = null;
				bombList[x][y] = false;
			}
		}
	}

	static void addZombie(int x, int y) {
		zombies[numZombies - 1] = new Zombie(x, y);
		zombieList[zombies[numZombies - 1].getX()][zombies[numZombies - 1].getY()] = true;
		numZombies++;

	}

	static void clearStatics() {
		for (int i = 0; i < zombies.length; i++) {
			zombies[i] = null;
		}
		for (int i = 0; i < zombieList[0].length; i++) {
			for (int j = 0; j < zombieList[1].length; j++) {
				zombieList[i][j] = false;
			}
		}
		numZombies = 1;
	}

}
