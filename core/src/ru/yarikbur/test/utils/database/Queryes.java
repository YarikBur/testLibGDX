package ru.yarikbur.test.utils.database;

import java.sql.SQLException;

public class Queryes {
	public static final String SET_ONLINE = "UPDATE `online` SET `online`='%d' WHERE `player`='%s';";

	private static int boolToInt(boolean bool) {
		return (bool) ? 1 : 0;
	}

	public static void updatePlayerOnline(Database database, String player, boolean online) {
		update(database, String.format(SET_ONLINE, boolToInt(online), player));
	}

	public static void update(Database database, String query) {
		try {
			database.openConnection();

			database.getSatement().executeUpdate(query);

			database.closeConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


}
