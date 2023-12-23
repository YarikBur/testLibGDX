package ru.yarikbur.test.utils.graphic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TilesetParser {
	private TextureRegion[][] tileset;
	
	/**
	 * @param col - column in texture tileset
	 * @param row - row in texture tileset
	 * @param tex - texture tileset
	 */
	public TilesetParser(int col, int row, Texture tex) {
		tileset = TextureRegion.split(tex, tex.getWidth() / col, tex.getHeight() / row);
	}
	
	public TextureRegion[][] getTileset() {
		return tileset;
	}
}
