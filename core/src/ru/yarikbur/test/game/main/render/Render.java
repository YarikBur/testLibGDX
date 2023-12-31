package ru.yarikbur.test.game.main.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import ru.yarikbur.test.game.main.map.GeneratorFloor;
import ru.yarikbur.test.game.objects.floor.Floor;

public class Render {
	private static final int MAP_WIDTH = 80;
	private static final int MAP_HEIGHT = 45;
	
	private GeneratorFloor generatorFloor;
	
	private Floor[][] floor;
	private SpriteBatch spriteBatch;
	private World world;
	
	public Render(SpriteBatch spriteBatch, World world) {
		this.spriteBatch = spriteBatch;
		this.world = world;
	}
	
	public void initMap() {
		generatorFloor = new GeneratorFloor(MAP_WIDTH, MAP_HEIGHT);
	}
	
	public void initObjectsOnMap() {
		generatorFloor.placeObjects();
		
		floor = generatorFloor.getObjects();
		
		for (Floor[] floors : this.floor) {
			for (Floor floor : floors) {
				Body body = world.createBody(floor.getBodyDef());
				body.createFixture(floor.getFixtureDef());
			}
		}
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
