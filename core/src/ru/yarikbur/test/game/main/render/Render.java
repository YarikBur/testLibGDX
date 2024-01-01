package ru.yarikbur.test.game.main.render;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class Render {
	private SpriteBatch spriteBatch;
	private World world;
	private OrthographicCamera cam;
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer mapRenderer;
	
	public Render(SpriteBatch spriteBatch, World world, OrthographicCamera cam) {
		this.spriteBatch = spriteBatch;
		this.world = world;
		this.cam = cam;
	}
	
	public void initMap() {
		map = new TmxMapLoader().load("map/test_map.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map);
	}
	
	public void initObjectsOnMap() {
		
	}
	
	public void renderMap() {
		mapRenderer.setView(cam);
		mapRenderer.render();
	}
	
	public void dispose() {
		map.dispose();
		mapRenderer.dispose();
	}
}
