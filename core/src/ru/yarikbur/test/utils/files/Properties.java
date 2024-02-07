package ru.yarikbur.test.utils.files;

import com.badlogic.gdx.Gdx;

import java.io.IOException;
import java.io.InputStream;

public class Properties {
	private final java.util.Properties properties;

	public Properties(String file_path) {
		properties = new java.util.Properties();

		try (InputStream in = Gdx.files.internal(file_path).read()) {
			properties.load(in);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String getProperty(String key) {
		return this.properties.getProperty(key);
	}
}
