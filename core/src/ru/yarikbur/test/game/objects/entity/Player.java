package ru.yarikbur.test.game.objects.entity;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import ru.yarikbur.test.game.objects.GameObject;

public class Player extends GameObject {
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
	}
}
