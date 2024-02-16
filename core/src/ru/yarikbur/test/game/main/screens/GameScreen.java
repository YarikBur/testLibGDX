package ru.yarikbur.test.game.main.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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

public class GameScreen implements Screen {
	final MainGameWrapper wrapper;
	
	EngineWorld engineWorld;
	OrthographicCamera camGame;
	OrthographicCamera camHUD;
	RenderMap render;
	Player player;
	public Keyboard keyboard;

	private String user, player_name;
	
	private final Maps currentMap;

	private float timeSecond = 0f;

	private final BitmapFont fpsFont;


	public void setUser(String user) {
		this.user = user;
	}

	public void setPlayer_name(String player_name){
		this.player_name = player_name;
	}

	public void setPlayerName(String user, String player_name) {
		this.setUser(user);
		this.setPlayer_name(player_name);
	}

	private void initPlayer() {
		player = new Player();
		engineWorld.setPlayer(player);
		player.setPosition(Queries.getLocationPlayer(wrapper.getDatabase_Game(), wrapper.idPlayer));
		Body body = engineWorld.getWorld().createBody(player.getBodyDef());
		body.createFixture(player.getFixtureDef());
		player.setWorldBody(body);
		
		player.setUserData(0, player);
	}

	public GameScreen(MainGameWrapper wrapper) {
		this.wrapper = wrapper;
		keyboard = new Keyboard();
		
		camGame = new OrthographicCamera();
		camGame.setToOrtho(false, wrapper.getCameraSize()[0], wrapper.getCameraSize()[1]);
		camHUD = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camHUD.position.set(camHUD.viewportWidth / 2f, camHUD.viewportHeight / 2f, 1f);

		engineWorld = new EngineWorld();
		render = new RenderMap(wrapper.batch, engineWorld, camGame);
		
		initPlayer();
		
		currentMap = Maps.TestMap;

		Gdx.input.setInputProcessor(keyboard);

		FreeTypeFontGenerator fontGenerator =
				new FreeTypeFontGenerator(Gdx.files.internal("ui/RobotoMono-VariableFont_wght.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter fontParameter =
				new FreeTypeFontGenerator.FreeTypeFontParameter();

		fontParameter.size = 22;

		fpsFont = fontGenerator.generateFont(fontParameter);
	}

	@Override
	public void show() {
		Queries.updatePlayerOnline(wrapper.getDatabase_Game(), wrapper.idPlayer, true);

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

	private void gameRender() {
		camGame.position.set(player.getPosition()[0]+player.getSize()[0]/2f,
				player.getPosition()[1]+player.getSize()[1]/2f, 0);
		camGame.update();
		wrapper.batch.setProjectionMatrix(camGame.combined);

		wrapper.batch.begin();

		render.renderMap();

		player.renderObject(wrapper.batch);

		wrapper.batch.end();
	}

	private void hudRender() {
		camHUD.update();
		wrapper.batch.setProjectionMatrix(camHUD.combined);

		wrapper.batch.begin();
		//player.renderName(wrapper.batch);
		fpsFont.draw(wrapper.batch, "FPS=" + Gdx.graphics.getFramesPerSecond(), 5, camHUD.viewportHeight-5);
		wrapper.batch.end();
	}

	private void updateDatabase(float delta, float timePeriod) {
		timeSecond += delta;
		if (timeSecond > timePeriod) {
			timeSecond -= timePeriod;
			new Thread(() -> {
				Queries.updateLocationPlayer(wrapper.getDatabase_Game(), player.getPosition(), wrapper.idPlayer);
			}).start();
		}
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(MainGameWrapper.BACKGROUND_COLOR);

		updateDatabase(delta, 5f);

		gameRender();

		new Thread(() -> engineWorld.update((world) -> updateWorld())).start();

		engineWorld.render(camGame.combined);

		hudRender();

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
	}

	@Override
	public void resize(int width, int height) {
		camGame.viewportWidth = MainGameWrapper.VIEWPORT_CONST;
		camGame.viewportHeight = MainGameWrapper.VIEWPORT_CONST * height / width;
		camGame.update();
	}

	@Override
	public void pause() { }

	@Override
	public void resume() { }

	@Override
	public void hide() { }

	@Override
	public void dispose() {
		Queries.updatePlayerOnline(wrapper.getDatabase_Game(), wrapper.idPlayer, false);
		render.dispose();
	}
	
}
