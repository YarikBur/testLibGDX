package ru.yarikbur.test.utils.graphic;

import com.badlogic.gdx.graphics.Color;

public class NewColor {
	/**
	 * Return new color from HSV palette
	 * @param hue - The Hue in degree from 0 to 360
	 * @param saturation - The Saturation from 0 to 1
	 * @param value - The Value (brightness) from 0 to 1
	 * @return
	 */
	public static Color getHSVColor(float hue, float saturation, float value) {
		return new Color().fromHsv(hue, saturation, value);
	}
	
	public static Color getRGBAColor() {
		return null;
	}
}
