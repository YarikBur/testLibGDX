package ru.yarikbur.test.game.main.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.yarikbur.test.game.main.MainGameWrapper;
import ru.yarikbur.test.game.main.map.EngineWorld;
import ru.yarikbur.test.game.main.map.Maps;
import ru.yarikbur.test.game.main.render.RenderMap;
import ru.yarikbur.test.game.main.render.SwitchSeason;
import ru.yarikbur.test.game.objects.entity.Player;

public class GameScreen implements Screen {
	final MainGameWrapper wrapper;
	
	EngineWorld engineWorld;
	OrthographicCamera cam;
	RenderMap render;
	Player player;
	
	private Maps currentMap;
	
	private void initPlayer() {
		player = new Player();
		engineWorld.setPlayer(player);
		player.setPosition(25*16-8, 17*16-8);
		Body body = engineWorld.getWorld().createBody(player.getBodyDef());
		body.createFixture(player.getFixtureDef());
		player.setWorldBody(body);
	}
	
	public GameScreen(MainGameWrapper wrapper) {
		this.wrapper = wrapper;
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, wrapper.getCameraSize()[0], wrapper.getCameraSize()[1]);
		
		engineWorld = new EngineWorld();
		render = new RenderMap(wrapper.batch, engineWorld, cam);
		
		initPlayer();
		
		currentMap = Maps.TestMap;
	}

	@Override
	public void show() {
		render.initMap(currentMap, currentMap.getSeasons());
		render.initObjectsOnMap();
	}
	
	public void updateWorld() {
		
	}
	
	@Override
	public void render(float delta) {
		ScreenUtils.clear(MainGameWrapper.BACKGROUND_COLOR);
		cam.position.set(player.getPosition()[0]+player.getSize()[0]/2, player.getPosition()[1]+player.getSize()[1]/2, 0);
		cam.update();
		wrapper.batch.setProjectionMatrix(cam.combined);
		
		wrapper.batch.begin();
		
		render.renderMap();
		
		player.renderObject(wrapper.batch);
		
		wrapper.batch.end();
		
		engineWorld.update((world) -> {
			Vector2 playerSpeed = new Vector2();
			playerSpeed = Player.direction;
			playerSpeed = playerSpeed.scl((delta / 2) * Player.MAX_SPEED)
					.scl(Player.WEIGHT)
					.clamp(0, Player.WEIGHT + Player.MAX_SPEED);
			
			System.out.println("X direction: " + Player.direction.x + "X speed: " + playerSpeed.x);
			
			player.getBody().applyForce(playerSpeed, player.getVectorPosition(), true);
		});
		engineWorld.render(cam.combined);
		
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
