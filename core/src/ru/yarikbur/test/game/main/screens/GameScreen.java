package ru.yarikbur.test.game.main.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.yarikbur.test.game.main.MainGameWrapper;
import ru.yarikbur.test.game.main.map.EngineWorld;
import ru.yarikbur.test.game.main.map.Maps;
import ru.yarikbur.test.game.main.render.RenderMap;
import ru.yarikbur.test.game.main.render.SwitchSeason;

public class GameScreen implements Screen {
	private static final float SPEED_CAM = 3f;
	
	final MainGameWrapper wrapper;
	
	EngineWorld engineWorld;
	OrthographicCamera cam;
	RenderMap render;
	
	private Maps currentMap;
	
	public GameScreen(MainGameWrapper wrapper) {
		this.wrapper = wrapper;
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, wrapper.getCameraSize()[0], wrapper.getCameraSize()[1]);
		cam.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
		
		engineWorld = new EngineWorld();
		render = new RenderMap(wrapper.batch, engineWorld.getWorld(), cam);
		
		currentMap = Maps.TestMap;
	}

	@Override
	public void show() {
		render.initMap(currentMap, currentMap.getSeasons());
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
		
		engineWorld.render(cam.combined);
		
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			cam.translate(-SPEED_CAM, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			cam.translate(0, -SPEED_CAM);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			cam.translate(SPEED_CAM, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			cam.translate(0, SPEED_CAM);
		}
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
			SwitchSeason.switchSeason(render, currentMap, Maps.Seasons.Winter);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
			SwitchSeason.switchSeason(render, currentMap, Maps.Seasons.Spring);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
			SwitchSeason.switchSeason(render, currentMap, Maps.Seasons.Summer);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.V)) {
			SwitchSeason.switchSeason(render, currentMap, Maps.Seasons.Autumn);
		}
		
		cam.position.x = MathUtils.clamp(cam.position.x, 
				cam.viewportWidth / 2, 
				cam.viewportWidth + cam.viewportWidth / 2);
		
		cam.position.y = MathUtils.clamp(cam.position.y, 
				cam.viewportHeight / 2, 
				cam.viewportHeight + cam.viewportHeight / 2);
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
