package ru.yarikbur.test.game.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.yarikbur.test.game.objects.floor.BrickFloor;

/*
 * Main class for start desktop application
 */
public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	
	BrickFloor[] floor = new BrickFloor[8];
	
	@Override
	/*
	 * Create this class
	 * Initialization local non static variables
	 */
	public void create () {
		// Initialization sprite renderer
		batch = new SpriteBatch();
		// Initialization sprite texture
		img = new Texture("badlogic.jpg");
		
		for (int i = 0; i < floor.length; i++) {
			floor[i] = new BrickFloor(false);
			floor[i].setPosition(0, (i * 16));
		}
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
		for (int i = 0; i < floor.length; i++) {
			floor[i].renderObject(batch);
		}
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
