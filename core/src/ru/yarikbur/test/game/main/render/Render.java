package ru.yarikbur.test.game.main.render;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;

import ru.yarikbur.test.game.main.map.Maps;
import ru.yarikbur.test.game.main.screens.LoaderTmx;

public class Render {
	@SuppressWarnings("unused")
	private SpriteBatch spriteBatch;
	@SuppressWarnings("unused")
	private World world;
	private OrthographicCamera cam;
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer mapRenderer;
	
	public Render(SpriteBatch spriteBatch, World world, OrthographicCamera cam) {
		this.spriteBatch = spriteBatch;
		this.world = world;
		this.cam = cam;
	}
	
	public void initMap(Maps maps, Maps.Seasons seasons) {
		map = new LoaderTmx().load(maps.getPath(), seasons);
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
