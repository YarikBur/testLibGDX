package ru.yarikbur.test.game.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.yarikbur.test.utils.graphic.NewColor;

public class MainGameWrapper extends Game {
	public static final int VIEWPORT_CONTST = 640;
	public static final Color BACKGROUND_COLOR = NewColor.getHSVColor(247f, .36f, .17f);
	
	public SpriteBatch batch;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		
		this.setScreen(new ru.yarikbur.test.game.main.screens.Game(this));
	}
	
	public void render() {
		super.render();
	}
	
	public void dispose() {
		batch.dispose();
	}
	
	/**
	 * Returns the current width of the game window
	 * @return - Integer (Width)
	 */
	public int getWindowWidth() {
		return Gdx.graphics.getWidth();
	}
	
	/**
	 * Returns the current height of the game window
	 * @return - Integer (Height)
	 */
	public int getWindowHeight() {
		return Gdx.graphics.getHeight();
	}
	
	/**
	 * Returns the current size of the game window
	 * @return - Integer[width, height]
	 */
	public int[] getWindowSize() {
		return new int[] {this.getWindowWidth(), this.getWindowHeight()};
	}
	
	public int[] getCameraSize() {
		int w = VIEWPORT_CONTST;
		int h = VIEWPORT_CONTST * this.getWindowHeight() / w;
		
		return new int[] {w, h};
	}
	
}
