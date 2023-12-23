package ru.yarikbur.test.game.objects.floor;

public class Floor extends ru.yarikbur.test.game.objects.Object {
	public boolean isCanStepOn;
	public boolean isTrap;
	
	protected enum Texture_Properties {
		BRICK("objects/floor/floor_brick_tileset.jpg", 1, 4),
		WOOD("objects/floor/floor_wood_tileset.jpg", 0, 0);
		
		private final String path;
		private final int row;
		private final int collumn;
		
		private Texture_Properties(String path, int row, int collumn) {
			this.path = path;
			this.row = row;
			this.collumn = collumn;
		}
		
		public String getPath() {
			return path;
		}
		
		public int getRow() {
			return row;
		}
		
		public int getCollumn() {
			return collumn;
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
		this.setTextureCollumn(properties.getCollumn());
	}
}
