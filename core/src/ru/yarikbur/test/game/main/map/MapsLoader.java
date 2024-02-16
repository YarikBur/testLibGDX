package ru.yarikbur.test.game.main.map;

import ru.yarikbur.test.utils.database.Database;
import ru.yarikbur.test.utils.database.Queries;

public class MapsLoader {
	private final Database database;

	public MapsLoader(Database database) {
		this.database = database;
	}

	public void downloadMap(int map_id) {
		String map = Queries.getGetXmlMap(database, "id", Integer.toString(map_id));
	}

	public void downloadMap(String map_name) {
		String map = Queries.getGetXmlMap(database, "map_name", map_name);
	}
}
