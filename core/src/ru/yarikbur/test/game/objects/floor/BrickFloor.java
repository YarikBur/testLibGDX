package ru.yarikbur.test.game.objects.floor;

import ru.yarikbur.test.utils.math.Random;

public class BrickFloor extends Floor {
	private static final Texture_Properties TEXTURE_PROPERTIES = Texture_Properties.BRICK;
	
	/**
	 * Constructor a brick floor
	 * @param isTrap - this tile it's a trap or not
	 * @param textureID - texture number a 1D array
	 */
	public BrickFloor(boolean isTrap, int textureID) {
		this.defaultProperties();
		
		this.setTextureNumber(textureID);
		this.setIsTrap(isTrap);
	}
	
	/**
	 * Constructor a brick floor
	 * @param isTrap - this tile it's a trap or not
	 */
	public BrickFloor(boolean isTrap) {
		this.defaultProperties();
		
		this.setTextureNumber(Random.getRandomIntegerInRange(0, (TEXTURE_PROPERTIES.getColumn() - 1)));
		this.setIsTrap(isTrap);
	}
	
	private void defaultProperties() {
		this.setTextureProperties(TEXTURE_PROPERTIES);
		this.setIsCanStepOn(true);
	}
}
