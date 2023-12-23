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
	
	BrickFloor[][] floor = new BrickFloor[80][45];
	
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
			for (int j = 0; j < floor[i].length; j++) {
				floor[i][j] = new BrickFloor(false, true, 0);
				floor[i][j].setPosition((i * 16), (j * 16));
				
			}
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
		
		for (BrickFloor[] floorRow : floor) {
			for (BrickFloor floor : floorRow) {
				floor.renderObject(batch);
			}
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
		for (BrickFloor[] floorRow : floor) {
			for (BrickFloor floor : floorRow) {
				floor.dispose();
			}
		}
	}
}
