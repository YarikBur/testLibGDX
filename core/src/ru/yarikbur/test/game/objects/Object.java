package ru.yarikbur.test.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Object {
	protected Texture texture;
	protected int texture_number;
	protected int texture_collumn;
	protected int texture_row;
	
	protected void setTexture(String path) {
		this.texture = new Texture(Gdx.files.internal(path));
	}
	
	protected void setTextureNumber(int num) {
		this.texture_number = num;
	}
	
	protected void setTextureCollumn(int value) {
		this.texture_collumn = value;
	}
	
	protected void setTextureRow(int value) {
		this.texture_row = value;
	}
}
