package ru.yarikbur.test.game.objects.floor;

public class BrickFloor extends Floor {
	private static final Texture_Properties TEXTURE_PROPERTIES = Texture_Properties.BRICK;
	
	public BrickFloor(boolean isCanStepOn, boolean isTrap) {
		this.setTextureProperties(TEXTURE_PROPERTIES);
		
		this.setIsCanStepOn(isCanStepOn);
		this.setIsTrap(isTrap);
	}
}
