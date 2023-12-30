package ru.yarikbur.test.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.yarikbur.test.utils.graphic.TilesetParser;

/**
 * Basic parameters for all types of objects in the game
 */
public class Object {
	protected Texture texture;
	protected TextureRegion[] textureRegion;
	protected int texture_number;
	protected int texture_column;
	protected int texture_row;
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	/**
	 * Sets the texture for a given object
	 * @param path
	 */
	protected void setTexture(String path) {
		this.texture = new Texture(Gdx.files.internal(path));
	}
	
	/**
	 * Sets the texture region for a given object
	 */
	protected void setTextureRegion() {
		this.textureRegion = TilesetParser.getTileset1D(texture, texture_column, texture_row);
	}
	
	/**
	 * Sets the region ID from textures for a given object
	 * @param num - ID (int)
	 */
	protected void setTextureNumber(int num) {
		this.texture_number = num;
	}
	
	/**
	 * Sets the number of columns of regions in the texture
	 * @param value - Texture Column (int)
	 */
	protected void setTextureColumn(int value) {
		this.texture_column = value;
	}
	
	/**
	 * Sets the number of row of regions in the texture
	 * @param value - Texture row (int)
	 */
	protected void setTextureRow(int value) {
		this.texture_row = value;
	}
	
	/**
	 * Sets the X position for a given object
	 * @param value - Position x (int)
	 */
	protected void setX(int value) {
		this.x = value;
	}
	
	/**
	 * Sets the Y position for a given object
	 * @param value - Position Y (int)
	 */
	protected void setY(int value) {
		this.y = value;
	}
	
	/**
	 * Sets position for a given object
	 * @param x - Position x (int)
	 * @param y - Position Y (int)
	 */
	public void setPosition(int x, int y) {
		setX(x);
		setY(y);
	}
	
	/**
	 * Returns the position of this object
	 * @return Integer[x, y]
	 */
	public int[] getPosition() {
		return new int[] {x, y};
	}
	
	/**
	 * Sets the width for a given object
	 * @param width - Object width is pixels
	 */
	protected void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * Sets the height for a given object
	 * @param height - Object height is pixels
	 */
	protected void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * Sets the size for a given object
	 * @param width - Object width is pixels
	 * @param height - Object height is pixels
	 */
	protected void setSize(int width, int height) {
		this.setWidth(width);
		this.setHeight(height);
	}
	
	/**
	 * Returns the size of this object
	 * @return Integer[width, height]
	 */
	public int[] getSize() {
		return new int[] {width, height};
	}
	
	/**
	 * Returns the texture of this object
	 * @return Texture
	 */
	public Texture getTexture() {
		return this.texture;
	}
	
	/**
	 * Draws the given object in the window
	 * @param batch - SpriteBatch
	 */
	public void renderObject(SpriteBatch batch) {
		batch.draw(textureRegion[texture_number], x, y);
	}
	
	/**
	 * Destroys texture
	 */
	public void dispose() {
		texture.dispose();
	}
}
