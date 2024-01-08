package ru.yarikbur.test.game.objects.floor;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

/**
 * Game object - floor made of bricks
 */
public class LayerBorder extends Floor {
//	private static final Texture_Properties TEXTURE_PROPERTIES = Texture_Properties.BRICK;
	
	/**
	 * Constructor a brick floor
	 * @param isTrap - this tile it's a trap or not
	 * @param isCanStepOn - player is can step on this floor
	 * @param textureID - texture number a 1D array
	 */
	public LayerBorder(boolean isTrap, boolean isCanStepOn, int textureID) {
		this.defaultProperties();
		
		this.setTextureNumber(textureID);
		this.setIsTrap(isTrap);
		this.setIsCanStepOn(true);
	}
	
	/**
	 * Constructor a brick floor
	 * @param isTrap - this tile it's a trap or not
	 * @param isCanStepOn - player is can step on this floor
	 */
	public LayerBorder(boolean isTrap, boolean isCanStepOn) {
		this.defaultProperties();
		
//		this.setTextureNumber(0);
		this.setIsTrap(isTrap);
		this.setIsCanStepOn(true);
	}
	
	/**
	 * Standard object settings regardless of the constructor
	 */
	private void defaultProperties() {
//		this.setTextureProperties(TEXTURE_PROPERTIES);
		this.setSize(SIZE_16x16);
		
		this.body = new Body();
		this.body.setType(BodyType.StaticBody);
		this.body.setFixture(this.body.boxShape(this.getSize()[0], this.getSize()[1]));
		
		this.color = BORDER;
	}
}
