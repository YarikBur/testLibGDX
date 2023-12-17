package ru.yarikbur.test.game.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/*
 * Main class for start desktop application
 */
public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	
	@Override
	/*
	 * Create this class
	 * Initialization local non static variables
	 */
	public void create () {
		// Init sprite renderer
		batch = new SpriteBatch();
		// Init sprite texture
		img = new Texture("badlogic.jpg");
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
		batch.draw(img, 0, 0);
		// Finish renderer sprite
		batch.end();
	}
	
	@Override
	/*
	 * Method from dispose objects in this class
	 */
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
