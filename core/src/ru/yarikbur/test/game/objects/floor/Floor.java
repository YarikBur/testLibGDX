package ru.yarikbur.test.game.objects.floor;

public class Floor extends ru.yarikbur.test.game.objects.Object {
	public boolean isCanStepOn;
	public boolean isTrap;
	
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
	
	protected void setIsCanStepOn(boolean value) {
		this.isCanStepOn = value;
	}
	
	protected void setIsTrap(boolean value) {
		this.isTrap = value;
	}
	
	protected void setTextureProperties(Texture_Properties properties) {
		this.setTexture(properties.getPath());
		this.setTextureRow(properties.getRow());
		this.setTextureCollumn(properties.getColumn());
		this.setTextureRegion();
	}
}
