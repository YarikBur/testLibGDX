package ru.yarikbur.test.game.main.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import ru.yarikbur.test.utils.control.Keyboard;
import ru.yarikbur.test.utils.database.Queries;
import ru.yarikbur.test.utils.math.Hash;

public class GameScreen implements Screen {
	final MainGameWrapper wrapper;
	
	EngineWorld engineWorld;
	OrthographicCamera cam;
	RenderMap render;
	Player player;
	public Keyboard keyboard;

	private String user, player_name;
	
	private final Maps currentMap;


	public void setUser(String user) {
		this.user = user;
	}

	public void setPlayer_name(String player_name){
		this.player_name = player_name;
	}

	public void setUserData(String user, String player_name) {
		this.setUser(user);
		this.setPlayer_name(player_name);
	}

	private void initPlayer() {
		player = new Player();
		engineWorld.setPlayer(player);
		player.setPosition(25*16-8, 17*16-8);
		Body body = engineWorld.getWorld().createBody(player.getBodyDef());
		body.createFixture(player.getFixtureDef());
		player.setWorldBody(body);
		
		player.setUserData(0, player);
	}

	public GameScreen(MainGameWrapper wrapper) {
		this.wrapper = wrapper;
		keyboard = new Keyboard();
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, wrapper.getCameraSize()[0], wrapper.getCameraSize()[1]);
		
		engineWorld = new EngineWorld();
		render = new RenderMap(wrapper.batch, engineWorld, cam);
		
		initPlayer();
		
		currentMap = Maps.TestMap;

		Gdx.input.setInputProcessor(keyboard);
	}

	@Override
	public void show() {
		Queries.updatePlayerOnline(wrapper.getDatabase_Game(), player_name, true);

		render.initMap(currentMap, currentMap.getSeasons());
		render.initObjectsOnMap();

		player.setPlayerName(user, player_name);
	}
	
	public void updateWorld() {
		Vector2 playerSpeed = new Vector2(Player.direction);

		player.getBody().setLinearVelocity(playerSpeed);

		Vector2 playerPosition = new Vector2(player.getBody().getPosition());
		playerPosition.x -= 8;

		player.setPosition(playerPosition);
	}
	
	float time = 0;
	
	@Override
	public void render(float delta) {
		ScreenUtils.clear(MainGameWrapper.BACKGROUND_COLOR);
		cam.position.set(player.getPosition()[0]+player.getSize()[0]/2f, player.getPosition()[1]+player.getSize()[1]/2f, 0);
		cam.update();
		wrapper.batch.setProjectionMatrix(cam.combined);

		wrapper.batch.begin();
		
		render.renderMap();
		
		player.renderObject(wrapper.batch);
		
		wrapper.batch.end();

		engineWorld.update((world) -> updateWorld());
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
		cam.viewportWidth = MainGameWrapper.VIEWPORT_CONST;
		cam.viewportHeight = MainGameWrapper.VIEWPORT_CONST * height / width;
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

		Queries.updatePlayerOnline(wrapper.getDatabase_Game(), player_name, false);
	}
	
}
