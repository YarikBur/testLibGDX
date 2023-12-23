package ru.yarikbur.test.game.objects.floor;

/**
 * Type of game object - floor
 */
public class Floor extends ru.yarikbur.test.game.objects.Object {
	public boolean isCanStepOn;
	public boolean isTrap;
	
	/**
	 * Properties of texture varieties for a given type of object. The settings store: The path to the textures, the number of lines in the texture tileset and the number of columns in the texture tileset
	 */
	protected enum Texture_Properties {
		BRICK("objects/floor/brick_tileset.jpg", 1, 4),
		WOOD("objects/floor/wood_tileset.jpg", 0, 0);
		
		private final String path;
		private final int row;
		private final int column;
		
		private Texture_Properties(String path, int row, int column) {
			this.path = path;
			this.row = row;
			this.column = column;
		}
		
		public String getPath() {
			return path;
		}
		
		public int getRow() {
			return row;
		}
		
		public int getColumn() {
			return column;
		}
	}
	
	/**
	 * Can the player walk on this object?
	 * @param value - true or false
	 */
	protected void setIsCanStepOn(boolean value) {
		this.isCanStepOn = value;
	}
	
	/**
	 * Is this object a trap?
	 * @param value - true or false
	 */
	protected void setIsTrap(boolean value) {
		this.isTrap = value;
	}
	
	/**
	 * Apply texture settings for this object
	 * @param properties - Texture_Properties
	 */
	protected void setTextureProperties(Texture_Properties properties) {
		this.setTexture(properties.getPath());
		this.setTextureRow(properties.getRow());
		this.setTextureColumn(properties.getColumn());
		this.setTextureRegion();
	}
}
