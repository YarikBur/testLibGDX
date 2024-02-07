package ru.yarikbur.test.game.main.render;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import ru.yarikbur.test.game.main.map.EngineWorld;
import ru.yarikbur.test.game.main.map.Maps;
import ru.yarikbur.test.game.objects.floor.LayerBorder;
import ru.yarikbur.test.game.objects.wall.Rock;
import ru.yarikbur.test.game.objects.wall.Tree;
import ru.yarikbur.test.utils.graphic.LoaderTmx;

public class RenderMap {
	@SuppressWarnings("unused")
	private SpriteBatch spriteBatch;
	private EngineWorld world;
	private OrthographicCamera cam;
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer mapRenderer;

	/**
	 * Initialization method for class RenderMap
	 * @param spriteBatch - SpriteBatch
	 * @param world - EngineWorld
	 * @param cam - OrthographicCamera
	 */
	public RenderMap(SpriteBatch spriteBatch, EngineWorld world, OrthographicCamera cam) {
		this.spriteBatch = spriteBatch;
		this.world = world;
		this.cam = cam;
	}

	/**
	 * Initialization map
	 * @param maps - Enum maps
	 * @param seasons - Texture weather seasons
	 */
	public void initMap(Maps maps, Maps.Seasons seasons) {
		map = new LoaderTmx().load(maps.getPath(), seasons);
		mapRenderer = new OrthogonalTiledMapRenderer(map);
	}

	/**
	 *
	 */
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
				} catch (NullPointerException ignored) { }
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

			if (mapTileLayer.getName().indexOf("Layer") == 0) {
				parseCells((cell, x, y) -> {
					boolean isCanStepOn = getBooleanProperties(cell, "isCanStepOn");
					
					if (!isCanStepOn) {
						world.addObject(new LayerBorder(false, isCanStepOn), x, y);
					}
				}, mapTileLayer);
			} else if (mapTileLayer.getName().indexOf("Trees") == 0) {
				parseCells((cell, x, y) -> {
					boolean isCanStepOn = getBooleanProperties(cell, "isCanStepOn");
					
					if (!isCanStepOn) {
						world.addObject(new Tree(), x, y);
					}
				}, mapTileLayer);
			} else if (mapTileLayer.getName().indexOf("Rocks") == 0) {
				parseCells((cell, x, y) -> {
					boolean isCanStepOn = getBooleanProperties(cell, "isCanStepOn");
					
					if (!isCanStepOn) {
						world.addObject(new Rock(), x, y);
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
		world.clearAllObjects();
	}
}
