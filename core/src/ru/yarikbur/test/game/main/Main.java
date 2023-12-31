package ru.yarikbur.test.game.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.yarikbur.test.game.main.render.Render;
import ru.yarikbur.test.utils.graphic.NewColor;


public class Main extends ApplicationAdapter {
	private static final float VIEWPORT_CONTST = 640.0f;
	private static final Color BACKGROUND_COLOR = NewColor.getHSVColor(247f, .36f, .17f);
	
	OrthographicCamera cam;
	SpriteBatch batch;
	Render renderer;
	
	@Override
	public void create () {
		cam = new OrthographicCamera();
		cam.setToOrtho(false, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		cam.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
		
		batch = new SpriteBatch();
		
		renderer = new Render(this.batch);
		renderer.initObjectsOnMap();
	}
	
	
	@Override
	public void render () {
		ScreenUtils.clear(BACKGROUND_COLOR);
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		
		
		batch.begin();
		
		renderer.renderMap();
		
		batch.end();
	}
	
	@Override
	public void resize(int width, int height) {
		cam.viewportWidth = VIEWPORT_CONTST;
		cam.viewportHeight = VIEWPORT_CONTST * height / width;
		cam.update();
	}
	
	@Override
	public void dispose () {
		renderer.dispose();
		batch.dispose();
	}
}
