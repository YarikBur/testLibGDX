package ru.yarikbur.test.game.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.yarikbur.test.game.main.render.Render;

/*
 * Main class for start desktop application
 */
public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Render renderer;
	
	@Override
	/*
	 * Create this class
	 * Initialization local non static variables
	 */
	public void create () {
		// Initialization sprite renderer
		batch = new SpriteBatch();
		
		renderer = new Render(this.batch);
		renderer.initMap();
		renderer.initObjectsOnMap();
	}

	@Override
	/*
	 * Method from renderer on window
	 */
	public void render () {
		// clear window
		ScreenUtils.clear(1, 0, 0, 1);
		
		// Start renderer sprite
		batch.begin();
		// draw sprite
		
		renderer.renderMap();
		// Finish renderer sprite
		batch.end();
	}
	
	@Override
	/*
	 * Method from dispose objects in this class
	 */
	public void dispose () {
		renderer.dispose();
		batch.dispose();
	}
}
