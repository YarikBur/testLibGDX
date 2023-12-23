package ru.yarikbur.test.main;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import ru.yarikbur.test.game.main.Main;
import ru.yarikbur.test.settings.GlobalSettings;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(GlobalSettings.FOREGROUND_FPS);
		config.setTitle("Test GDX");
		config.setWindowedMode(GlobalSettings.WINDOW_WIDTH, GlobalSettings.WINDOW_HEIGHT);
		new Lwjgl3Application(new Main(), config);
	}
}
