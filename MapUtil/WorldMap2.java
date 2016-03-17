package MapUtil;
import TCPUtil.Server;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.RunnableFuture;

/**
 * This class implements World map. 
 * @author artir2d2
 * 
 */
public class WorldMap2 implements Serializable {
	protected Point size;
	protected Cell matrix[][];
	//private River2 allRivers;
	private int maxHeight;
	public double progress=0.;

	public WorldMap2(int sizeX, int sizeY, int sizeZ) {
		this.size = new Point(sizeX, sizeY, sizeZ);
		this.matrix = new Cell[sizeX][sizeY];
		this.maxHeight = 0;
	}

	public WorldMap2(int sizeX, int sizeY) {
		this.size = new Point(sizeX, sizeY, 0);
		this.matrix = new Cell[sizeX][sizeY];
		this.maxHeight = 0;
	}

	/**
	 * This member of WorldMap2 class creates thread which indicates create() method of new world map!
	 */
	public boolean done = false;
	public Thread createMap = new Thread(new Runnable() {
		@Override
		public void run() {
				create();
				done = true;
		}
	});
	/**
	 * Creates a world map, by placing seeds, growing them and other complex things. This is actualy the core of this class
	 */
	public int create() {
		fillWith(0, 0);
		int count = (int) (size.getX() * size.getY() * 0.33);
		bedRock((int) Math.sqrt(size.getX()), count, 1, 1, 0);

		for (int i = 0; i < 48; i++) {
			count = (int) (count * 0.92);
			randomSeeds((int) Math.sqrt(size.getX()), i + 2, (i + 2), (i + 1));
			growSeeds(count, i + 2, (i + 2), (i + 1));
			System.out.println(i);
			progress+=0.02;
		}
		placeTrees((int)(getXSize()*getYSize()*0.15));
		placeRocks((int)(getXSize()*getYSize()*0.06));
		progress = 1.0;
		return 1;
	}

	public Player placePlayer(String playerName,int playerID) {
		Random random = new Random();
		int x, y;
		Player player;
		while (true) {
			x = random.nextInt((int) (size.getX() * 0.9)) + (int) (size.getX() * 0.05);
			y = random.nextInt((int) (size.getX() * 0.9)) + (int) (size.getX() * 0.05);
			if (matrix[x][y].getCord().getZ() != 0 && matrix[x][y].getAllObjects().size()==0) {
				player = new Player(playerName, x, y, "hero1.png");//+playerID+".png");
				matrix[x][y].addObject(player);
				break;
			}
		}
		return player;
	}

	private void placeRocks(int seedCount){
		Random random = new Random();
		int seedx;
		int seedy;
		while (seedCount > 0) {
			seedx = random.nextInt((int) (size.getX() * 0.9)) + (int) (size.getX() * 0.05);
			seedy = random.nextInt((int) (size.getY() * 0.9)) + (int) (size.getY() * 0.05);
			if (matrix[seedx][seedy].getId() != 0 && matrix[seedx][seedy].getAllObjects().size()==0) {
				String spriteName = "40"+random.nextInt(3)+".png";//random.nextInt(1)
				matrix[seedx][seedy].addObject(new Rock(40, spriteName)); //temp id for the tree = 89, growthstage = 3...for now
			}
			seedCount--;
		}
	}

	private void placeTrees(int seedCount) {
		Random random = new Random();
		int seedx;
		int seedy;
		while (seedCount > 0) {
			seedx = random.nextInt((int) (size.getX() * 0.9)) + (int) (size.getX() * 0.05);
			seedy = random.nextInt((int) (size.getY() * 0.9)) + (int) (size.getY() * 0.05);
			if (matrix[seedx][seedy].getId() != 0 && matrix[seedx][seedy].getAllObjects().size()==0) {
				String spriteName = "30"+random.nextInt(4)+".png";//random.nextInt(1)
				matrix[seedx][seedy].addObject(new Tree(20, spriteName)); //temp id for the tree = 89, growthstage = 3...for now
			}
			seedCount--;
		}
		System.out.println("Done making trees");
	}

	/**
	 * Placing the bedrock seeds and bedrock,
	 * wchich means, this is the lowest terrain and every
	 * other types of terrains shoul be plced on it.
	 * Its not only placing the seeds, but every time it plant a seed
	 * its aslo placing the proper amount of Cells around the seed.
	 * That provides more random shapes of continents, than placing all seeds first
	 * and then placing terains around them.
	 *
	 * @param seeds  - number of seeds wchich have to be placed
	 * @param count  - number of Cells which needs to be placed around the seeds, it should be around 33% of WorldMap Cells
	 * @param z      - height of the lowest terrain on the map
	 * @param type   - type of terrain to place, the value should be like "2" or "1" for easier iteration
	 * @param onType - type of terrain on which this bed rock needs to be placed, method would be helpfull
	 */
	private void bedRock(int seeds, int count, int z, int type, int onType) {
		Random random = new Random();
		int seedx;
		int seedy;
		if (maxHeight < z) {//checking if the height is greater than notified maxHeight to make sure info about highest level is up to date
			maxHeight = z;
		}
			int numSeeds = seeds;
		while (seeds > 0) {//setting up seeds
			seedx = random.nextInt((int) (size.getX() * 0.8)) + (int) (size.getX() * 0.1);
			seedy = random.nextInt((int) (size.getY() * 0.4)) + (int) (size.getY() * 0.2);
			//System.out.println(seedx + "  " + seedy+"   "+(int)(sizex*0.70)+"  "+(int)(sizex*0.15));
			if (matrix[seedx][seedy].getId() == onType) {
				matrix[seedx][seedy] = new Cell(seedx, seedy, z, type);
				growSeeds(count / numSeeds, z, type, onType);
				seeds--;
			}
		}
	}

	/**
	 * Placing the seeds in random positions of specified type
	 *
	 * @param seeds-number of seeds that needs to be placed
	 * @param z            - the heigth of this tile
	 * @param type-type    of seed for example: "forest", "hills" etc.
	 * @param onType       - on what type of terrain it needs to be placed
	 */
	private void randomSeeds(int seeds, int z, int type, int onType) {
		Random random = new Random();
		int seedx;
		int seedy;
		if (maxHeight < z) {//checking if the height is greater than notified maxHeight to make sure info about highest level is up to date
			maxHeight = z;
		}
		while (seeds > 0) {//setting up seeds
			seedx = random.nextInt((int) (size.getX() * 0.9)) + (int) (size.getX() * 0.05);
			seedy = random.nextInt((int) (size.getY() * 0.9)) + (int) (size.getY() * 0.05);
			//System.out.println(seedx + "  " + seedy+"   "+(int)(sizex*0.70)+"  "+(int)(sizex*0.15));
			if (matrix[seedx][seedy].getId() == onType) {
				matrix[seedx][seedy].update(seedx, seedy, z, type);// = new Cell(seedx,seedy,z,type,new MapObject(type));
				seeds--;
			}
		}
	}



	/**
	 * Growing the seeds on matrix.
	 *
	 * @param count  number of tiles to be filled with some terrain
	 * @param type type of terrain (height)
	 * @param z actual height
	 * @param onType type of terrain on which new terrain should be placed
	 * @return returns 0 if everything is ok, 1 if there was not enough space to place all
	 */
	public int growSeeds(int count, int z, int type, int onType) {
		int x;
		int y;
		int previousCount = count;
		Random random = new Random();
		if (maxHeight < z) { //checking if the height is greater than notified maxHeight to make sure info about highest level is up to date
			maxHeight = z;
		}
		while (count > 0) {
			for (y = 0; y < size.getY(); y++) {
				for (x = 0; x < size.getX(); x++) {
					if (matrix[x][y].getId() == type && matrix[x][y].getCord().getZ() == z && count > 0) { //the previous instance must be the same type and the same height, count needs to be greater than 0
						int way = random.nextInt(7); //random choosing map cell in neighbour of existing one
						switch (way) {//trying to put new map cell
							case 0:
								if (((x - 1) > 0) && ((y - 1) > 0) && (matrix[x - 1][y - 1].getId() == onType)) {
									matrix[x - 1][y - 1] = new Cell(x - 1, y - 1, z, type);
									count--;
								}
								break;
							case 1:
								if (((y - 1) > 0) && (matrix[x][y - 1].getId() == onType)) {
									matrix[x][y - 1] = new Cell(x, y - 1, z, type);
									count--;
								}
								break;
							case 2:
								if (((x + 1) < (size.getX() - 1)) && ((y - 1) > 0) && (matrix[x + 1][y - 1].getId() == onType)) {
									matrix[x + 1][y - 1] = new Cell(x + 1, y - 1, z, type);
									count--;
								}
								break;
							case 3:
								if (((x + 1) < (size.getX() - 1)) && (matrix[x + 1][y].getId() == onType)) {
									matrix[x + 1][y] = new Cell(x + 1, y, z, type);
									x++;
									count--;
								}
								break;
							case 4:
								if (((x + 1) < (size.getX() - 1)) && ((y + 1) < (size.getY() - 1)) && (matrix[x + 1][y + 1].getId() == onType)) {
									matrix[x + 1][y + 1] = new Cell(x + 1, y + 1, z, type);
									count--;
								}
								break;
							case 5:
								if (((y + 1) < (size.getY() - 1)) && (matrix[x][y + 1].getId() == onType)) {
									matrix[x][y + 1] = new Cell(x, y + 1, z, type);
									count--;
								}
								break;
							case 6:
								if (((x - 1) > 0) && ((y + 1) < (size.getY() - 1)) && (matrix[x - 1][y + 1].getId() == onType)) {
									matrix[x - 1][y + 1] = new Cell(x - 1, y + 1, z, type);
									count--;
								}
								break;
							case 7:
								if (((x - 1) > 0) && (matrix[x - 1][y].getId() == onType)) {
									matrix[x - 1][y] = new Cell(x - 1, y, z, type);
									count--;
								}
								break;
						}
					}
				}
			}
			if (previousCount == count)
				return 1; //there is no more space to place a cell it needs to leave the while loop otherwise it stay there forever
			else previousCount = count; //here its updates the previous count to know how many tiles was placed
			//x = 0;
			//y = 0;
		}
		return 0;
	}

	/**
	 * Fills whole matrix with Cells defined by argument.
	 * It should be the first "layer" of map before bedRock.
	 * This layer should represent the oceans if
	 * you want to create WorldMap with some continents on it
	 *
	 * @param id - id of fill type
	 * @param z  - height of cells
	 */
	public void fillWith(int id, int z) {
		if (maxHeight < z) {//checking if the height is greater than notified maxHeight to make sure info about highest level is up to date
			maxHeight = z;
		}
		for (int y = 0; y < size.getY(); y++) {//filling whole map with one cell type and height
			for (int x = 0; x < size.getX(); x++) {
				matrix[x][y] = new Cell(x, y, z, id);
			}
		}
		System.out.println("Skonczylem fill2");
	}

	/**
	 * This method lets creator to change every single cell of world map
	 *
	 * @param x  -x position of cell
	 * @param y  - y positio of cell
	 * @param z  - z positon of cell
	 * @param id - id of cell which needs to be placed
	 */
	public void update(int x, int y, int z, int id) {
		if (maxHeight < z) {//checking if the height is greater than notified maxHeight to make sure info about highest level is up to date
			maxHeight = z;
		}
		this.matrix[x][y].update(x, y, z, id);// = new Cell(x,y,z,id,new MapObject(id));
	}

	/**
	 * Return 2D array Cell matrix of this map
	 *
	 * @return returns Cell[][] array
	 */
	public Cell[][] getMatrix() {
		return matrix;
	}

	/**
	 * Returns X size of world map
	 */
	public int getXSize() {
		return (int) size.getX();
	}

	/**
	 * Returns Y size of world map
	 */
	public int getYSize() {
		return (int) size.getY();
	}

	/**
	 * Returns Z size of world map
	 */
	public int getZSize() {
		return size.getZ();
	}

	/**
	 * String output of the map
	 */
	public String toString() {
		String result = "";
		for (int y = 0; y < size.getY(); y++) {
			for (int x = 0; x < size.getX(); x++) {
				System.out.print(matrix[x][y].getId());
			}
			System.out.println("");
		}
		return result;
	}

	public Cell getCell(int x, int y){
		return this.matrix[x][y];
	}

	public static void main(String[] args){
		WorldMap2 ww2 = new WorldMap2(500,500);
		ww2.create();
	}
}
