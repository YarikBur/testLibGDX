package ru.yarikbur.test.game.main.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.yarikbur.test.game.main.MainGameWrapper;
import ru.yarikbur.test.game.main.render.Render;

public class Game implements Screen {
	
	final MainGameWrapper wrapper;
	
	OrthographicCamera cam;
	Render render;
	
	public Game(MainGameWrapper wrapper) {
		this.wrapper = wrapper;
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, wrapper.getCameraSize()[0], wrapper.getCameraSize()[1]);
		cam.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
		
		render = new Render(wrapper.batch);
	}

	@Override
	public void show() {
		render.initMap();
		render.initObjectsOnMap();
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(MainGameWrapper.BACKGROUND_COLOR);
		cam.update();
		wrapper.batch.setProjectionMatrix(cam.combined);
		
		
		wrapper.batch.begin();
		
		render.renderMap();
		
		wrapper.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		cam.viewportWidth = MainGameWrapper.VIEWPORT_CONTST;
		cam.viewportHeight = MainGameWrapper.VIEWPORT_CONTST * height / width;
		cam.update();
	}

	@Override
	public void pause() { }

	@Override
	public void resume() { }

	@Override
	public void hide() { }

	@Override
	public void dispose() {
		render.dispose();
	}

}
