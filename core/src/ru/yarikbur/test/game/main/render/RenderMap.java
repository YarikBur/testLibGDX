package ru.yarikbur.test.game.main.render;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import ru.yarikbur.test.game.main.map.Maps;
import ru.yarikbur.test.game.objects.GameObject;
import ru.yarikbur.test.game.objects.floor.BrickFloor;
import ru.yarikbur.test.game.objects.wall.Tree;
import ru.yarikbur.test.utils.graphic.LoaderTmx;

public class RenderMap {
	@SuppressWarnings("unused")
	private SpriteBatch spriteBatch;
	private World world;
	private OrthographicCamera cam;
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer mapRenderer;
	
	private ArrayList<GameObject> objects;
	
	public RenderMap(SpriteBatch spriteBatch, World world, OrthographicCamera cam) {
		this.spriteBatch = spriteBatch;
		this.world = world;
		this.cam = cam;
		
		objects = new ArrayList<GameObject>();
	}
	
	public void initMap(Maps maps, Maps.Seasons seasons) {
		map = new LoaderTmx().load(maps.getPath(), seasons);
		mapRenderer = new OrthogonalTiledMapRenderer(map);
	}
	
	private void initBox2DObjects(GameObject gameObject, int x, int y, int width, int height) {
		gameObject.setPosition(x * width, y * height);
		
		Body body = world.createBody(gameObject.getBodyDef());
		body.createFixture(gameObject.getFixtureDef());
		
		objects.add(gameObject);
	}
	
	@FunctionalInterface
	private interface Parse {
		void execute(Cell cell, int x, int y);
	}
	
	private void parseCells(Parse method, TiledMapTileLayer mapTileLayer) {
		Cell cell = null;
		
		for (int x = 0; x < mapTileLayer.getWidth(); x++) {
			for (int y = 0; y < mapTileLayer.getHeight(); y++) {
				try {
					cell = mapTileLayer.getCell(x, y);
					
					method.execute(cell, x, y);
				} catch (NullPointerException e) { }
			}
		}
	}
	
	private boolean getBooleanProperties(Cell cell, String key) {
		return cell.getTile().getProperties().get(key, Boolean.class);
	}
	
	public void initObjectsOnMap() {
		TiledMapTileLayer mapTileLayer;
		
		for (int i = 0; i < map.getLayers().size(); i++) {
			mapTileLayer = (TiledMapTileLayer) map.getLayers().get(i);
			int tileWidth = mapTileLayer.getTileWidth();
			int tileHeigth = mapTileLayer.getTileHeight();
			
			if (mapTileLayer.getName().indexOf("Layer") == 0) {
				parseCells((cell, x, y) -> {
					boolean isCanStepOn = getBooleanProperties(cell, "isCanStepOn");
					
					if (!isCanStepOn) {
						initBox2DObjects(new BrickFloor(false, isCanStepOn), x, y, tileWidth, tileHeigth);
					}
				}, mapTileLayer);
			} else if (mapTileLayer.getName().indexOf("Tree") == 0) {
				parseCells((cell, x, y) -> {
					boolean isCanStepOn = getBooleanProperties(cell, "isCanStepOn");
					
					if (!isCanStepOn) {
						initBox2DObjects(new Tree(), x, y, tileWidth, tileHeigth);
					}
				}, mapTileLayer);
			}
		}
	}
	
	public void renderMap() {
		mapRenderer.setView(cam);
		mapRenderer.render();
	}
	
	public void dispose() {
		map.dispose();
		mapRenderer.dispose();
		
		for (GameObject obj : objects) {
			obj.dispose();
		}
	}
}
