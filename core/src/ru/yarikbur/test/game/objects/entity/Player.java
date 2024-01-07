package ru.yarikbur.test.game.objects.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import ru.yarikbur.test.game.objects.GameObject;

public class Player extends GameObject {
	public static final float MAX_SPEED = 5;
	public static final float WEIGHT = 55;
	public static Vector2 direction = new Vector2();
	public static Vector2 speed = new Vector2();
	
	public static enum STATE {
		CALM("Player/State.png"),
		BACK_WALK("Player/Back_Walk.png"),
		FRONT_WALK("Player/Front_Walk.png"),
		SIDE_WALK("Player/Side/Walk.png");
		
		private String path;
		
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
		
		this.setSize(SIZE_32x32);
		this.body = new Body();
		this.body.setType(BodyType.DynamicBody);
		this.body.setFixture(this.body.boxShape(this.getSize()[0], this.getSize()[1]));
		this.body.getFixtureDef().density = getDensityFromWeight(WEIGHT);
		this.body.getBodyDef().fixedRotation = true;
	}
}
