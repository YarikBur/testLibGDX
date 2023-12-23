package ru.yarikbur.test.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.yarikbur.test.utils.graphic.TilesetParser;

public class Object {
	protected Texture texture;
	protected TextureRegion[] textureRegion;
	protected int texture_number;
	protected int texture_column;
	protected int texture_row;
	protected int x;
	protected int y;
	
	protected void setTexture(String path) {
		this.texture = new Texture(Gdx.files.internal(path));
	}
	
	protected void setTextureRegion() {
		this.textureRegion = TilesetParser.getTileset1D(texture, texture_column, texture_row);
	}
	
	protected void setTextureNumber(int num) {
		this.texture_number = num;
	}
	
	protected void setTextureCollumn(int value) {
		this.texture_column = value;
	}
	
	protected void setTextureRow(int value) {
		this.texture_row = value;
	}
	
	protected void setX(int value) {
		this.x = value;
	}
	
	protected void setY(int value) {
		this.y = value;
	}
	
	public void setPosition(int x, int y) {
		setX(x);
		setY(y);
	}
	
	public Texture getTexture() {
		return this.texture;
	}
	
	public void renderObject(SpriteBatch batch) {
		batch.draw(textureRegion[texture_number], x, y);
	}
	
	public void dispose() {
		texture.dispose();
	}
}
