package ru.yarikbur.test.game.objects.wall;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Rock extends Wall {
	public Rock() {
		this.setSize(SIZE_16x16);
		this.body = new Body();
		this.body.setType(BodyType.StaticBody);
		this.body.setFixture(this.body.boxShape(this.getSize()[0], this.getSize()[1]));
	}
}
