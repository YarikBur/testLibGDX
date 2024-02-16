package ru.yarikbur.test.game.objects.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import ru.yarikbur.test.game.objects.GameObject;

public class Player extends GameObject {
	public static final float START_SPEED = 55;
	public static final float MAX_SPEED = START_SPEED + 5;
	private static final int USER_FONT_SIZE = 12;
	private static final int PLAYER_NAME_FONT_SIZE = 12;
	public static Vector2 direction = new Vector2();
	public static Vector2 speed = new Vector2();

	private static String user, player_name;

	FreeTypeFontGenerator fontGenerator;
	private BitmapFont userFont;
	private BitmapFont playerFont;
	
	public static enum STATE {
		CALM("Player/State.png"),
		BACK_WALK("Player/Back_Walk.png"),
		FRONT_WALK("Player/Front_Walk.png"),
		SIDE_WALK("Player/Side/Walk.png");
		
		private final String path;
		
		STATE(String path) {
			this.path = path;
		}
		
		public String getPath() {
			return this.path;
		}
	}
	
	public Player() {
		this.setTexture(STATE.CALM.getPath());
		this.setTextureColumn(3);
		this.setTextureRow(1);
		this.setTextureRegion();
		this.setTextureNumber(0);
		
		this.setSize(new int[] {14, 14});
		this.body = new Body();
		this.body.setType(BodyType.DynamicBody);
		this.body.setFixture(this.body.boxShape(this.getSize()[0], this.getSize()[1]));
		this.body.getFixtureDef().density = getDensityFromWeight(1);
		this.body.getBodyDef().fixedRotation = true;
		this.body.getFixtureDef().friction = .2f;
		
		this.color = DYNAMIC;
	}

	private void fontGenerate() {
		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("ui/RobotoMono-VariableFont_wght.ttf"));
		FreeTypeFontParameter fontParameter = new FreeTypeFontParameter();

		fontParameter.borderColor = Color.BLACK;
		fontParameter.borderWidth = 1;
		fontParameter.size = USER_FONT_SIZE;
		userFont = fontGenerator.generateFont(fontParameter);

		fontParameter.size = PLAYER_NAME_FONT_SIZE;
		playerFont = fontGenerator.generateFont(fontParameter);

		fontGenerator.dispose();
	}

	public void setPlayerName(String user, String player_name) {
		Player.user = user;
		Player.player_name = player_name;

		fontGenerate();
	}
}
