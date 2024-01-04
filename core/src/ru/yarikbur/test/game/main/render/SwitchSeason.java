package ru.yarikbur.test.game.main.render;

import ru.yarikbur.test.game.main.map.Maps;

public class SwitchSeason {
	public static void switchSeason(RenderMap renderer, Maps maps, Maps.Seasons seasons) {
		renderer.dispose();
		
		maps.setSeasons(seasons);
		
		renderer.initMap(maps, seasons);
		renderer.initObjectsOnMap();
	}
}
