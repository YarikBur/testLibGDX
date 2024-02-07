package ru.yarikbur.test.game.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.yarikbur.test.game.main.screens.GameScreen;
import ru.yarikbur.test.utils.control.Keyboard;
import ru.yarikbur.test.utils.database.Database;
import ru.yarikbur.test.utils.database.Queryes;
import ru.yarikbur.test.utils.files.Properties;
import ru.yarikbur.test.utils.graphic.NewColor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A shell that allows you to transfer and store shared data between switchable screens
 */
public class MainGameWrapper extends Game {
	public static final int VIEWPORT_CONTST = 640;
	public static final Color BACKGROUND_COLOR = NewColor.getHSVColor(247f, .36f, .17f);
	
	public SpriteBatch batch;
	public Keyboard keyboard;

	private Database database_Game;
	
	@Override
	public void create() {
		initGameDatabase();

		Queryes.updatePlayerOnline(database_Game,  "emili", true);

		batch = new SpriteBatch();
		keyboard = new Keyboard();
		
		Gdx.input.setInputProcessor(keyboard);
		
		this.setScreen(new GameScreen(this));
	}

	private void initGameDatabase() {
		Properties properties = new Properties("props/database.properties");

		database_Game = new Database(properties.getProperty("url"),
				properties.getProperty("username"), properties.getProperty("password"));
	}

	public void render() {
		super.render();
	}
	
	public void dispose() {
		batch.dispose();
		Queryes.updatePlayerOnline(database_Game, "emili", false);
	}
	
	/**
	 * Returns the current width of the game window
	 * @return Integer (Width)
	 */
	public int getWindowWidth() {
		return Gdx.graphics.getWidth();
	}
	
	/**
	 * Returns the current height of the game window
	 * @return Integer (Height)
	 */
	public int getWindowHeight() {
		return Gdx.graphics.getHeight();
	}
	
	/**
	 * Returns the current size of the game window
	 * @return Integer[width, height]
	 */
	public int[] getWindowSize() {
		return new int[] {this.getWindowWidth(), this.getWindowHeight()};
	}
	
	/**
	 * Returns the current size camera of the game window
	 * @return Integer[CONSTANT, CONSTANT * Height / Width]
	 */
	public int[] getCameraSize() {
		int w = VIEWPORT_CONTST;
		int h = VIEWPORT_CONTST * this.getWindowHeight() / w;
		
		return new int[] {w, h};
	}

	/**
	 * Returns the current game database
	 * @return Database
	 */
	public Database getDatabase_Game() {
		return database_Game;
	}
}
