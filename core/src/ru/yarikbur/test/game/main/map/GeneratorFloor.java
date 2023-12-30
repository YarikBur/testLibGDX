package ru.yarikbur.test.game.main.map;

import ru.yarikbur.test.game.objects.floor.BrickFloor;
import ru.yarikbur.test.game.objects.floor.Floor;

public class GeneratorFloor {
	private static final int SPACE = 3;
	
	private int width;
	private int height;
	private int[][] floorMap;
	private Floor[][] floor;
	
	public GeneratorFloor(int width, int height) {
		this.width = width;
		this.height = height;
		
		floorMap = new int[width][height];
		floor = new Floor[width][height];
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (i < SPACE || j < SPACE || i >= width - SPACE || j >= height - SPACE)
					floorMap[i][j] = 0;
				else floorMap[i][j] = 1;
			}
		}
	}
	
	public void placeObjects() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Floor tmp = new BrickFloor(false, false, floorMap[i][j]);
				tmp.setPosition(i * tmp.getSize()[0], j * tmp.getSize()[1]);
				
				floor[i][j] = tmp;
			}
		}
	}
	
	public Floor[][] getObjects() {
		return floor;
	}
}
