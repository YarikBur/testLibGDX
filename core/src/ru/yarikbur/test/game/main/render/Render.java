package ru.yarikbur.test.game.main.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.yarikbur.test.game.main.map.GeneratorFloor;
import ru.yarikbur.test.game.objects.floor.Floor;

public class Render {
	private static final int MAP_WIDTH = 80;
	private static final int MAP_HEIGHT = 45;
	
	private GeneratorFloor generatorFloor;
	
	private Floor[][] floor;
	private SpriteBatch spriteBatch;
	
	public Render(SpriteBatch spriteBatch) {
		this.spriteBatch = spriteBatch;
	}
	
	public void initMap() {
		generatorFloor = new GeneratorFloor(MAP_WIDTH, MAP_HEIGHT);
	}
	
	public void initObjectsOnMap() {
		generatorFloor.placeObjects();
		
		floor = generatorFloor.getObjects();
	}
	
	public void renderMap() {
		for (Floor[] floors : this.floor) {
			for (Floor floor : floors) {
				floor.renderObject(spriteBatch);
			}
		}
	}
	
	public void dispose() {
		for (Floor[] floors : this.floor) {
			for (Floor floor : floors) {
				floor.dispose();
			}
		}
	}
}
