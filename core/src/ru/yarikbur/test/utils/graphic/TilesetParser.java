package ru.yarikbur.test.utils.graphic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TilesetParser {
	/**
	 * Helper method for texture slicing
	 * @param texture - texture tileset
	 * @param column - column in texture tileset
	 * @param row - row in texture tileset
	 * @return a 2D array of TextureRegions indexed by [row][column].
	 */
	public static TextureRegion[][] getTileset2D(Texture texture, int column, int row) {
		return TextureRegion.split(texture, texture.getWidth() / column, texture.getHeight() / row);
	}
	
	/**
	 * Helper method for texture slicing
	 * @param texture - texture tileset
	 * @param column - column in texture tileset
	 * @param row - row in texture tileset
	 * @return a 1D array of TextureRegions indexed by [id].
	 */
	public static TextureRegion[] getTileset1D(Texture texture, int column, int row) {
		TextureRegion[][] tmp = getTileset2D(texture, column, row);
		TextureRegion[] output = new TextureRegion[column * row];
		
		int index = 0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				output[index] = tmp[i][j];
				
				index++;
			}
		}
		
		return output;
	}
}
