package ru.yarikbur.test.utils.database;

import java.sql.SQLException;

public class Queryes {
	public static final String SET_ONLINE = "UPDATE `online` SET `online`='%d' WHERE `player`='%s';";

	/**
	 * Converted boolean variable to integer
	 * @param bool Boolean variable
	 * @return Integer
	 */
	private static int boolToInt(boolean bool) {
		return (bool) ? 1 : 0;
	}

	/**
	 * A method responsible for changing a player’s online status according to a template
	 * UPDATE `online` SET `online`='%d' WHERE `player`='%s';
	 * @param database Wrapper database (ru.yarikbur.test.utils.database)
	 * @param player A unique character name for the player.
	 * @param online Settable online status
	 */
	public static void updatePlayerOnline(Database database, String player, boolean online) {
		update(database, String.format(SET_ONLINE, boolToInt(online), player));
	}

	/**
	 * Method responsible for changing fields in database tables
	 * @param database Wrapper database (ru.yarikbur.test.utils.database)
	 * @param query Sting SQL query
	 */
	public static void update(Database database, String query) {
		try {
			database.openConnection();

			database.getStatement().executeUpdate(query);

			database.closeConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


}
