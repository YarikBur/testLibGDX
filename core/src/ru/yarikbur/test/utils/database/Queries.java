package ru.yarikbur.test.utils.database;

import ru.yarikbur.test.utils.math.Hash;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Queries {
	public static final String SET_ONLINE = "UPDATE `online` SET `online`='%d' WHERE `player`='%s';";
	public static final String GET_USER_HASH = "SELECT `password` FROM `users` WHERE `login`='%s';";
	public static final String GET_USER_PLAYERS = "SELECT `player` FROM `players` WHERE `user`='%s';";

	/**
	 * Converted boolean variable to integer
	 * @param bool Boolean variable
	 * @return Integer
	 */
	private static int boolToInt(boolean bool) {
		return (bool) ? 1 : 0;
	}

	/**
	 * A method responsible for changing a player’s online status according to a template:
	 * "UPDATE `online` SET `online`='%d' WHERE `player`='%s';"
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

	public static boolean checkPassword(Database database, String user, String password) {
		ResultSet result = select(database, String.format(GET_USER_HASH, user));

		try {
			if (result.next())
				if (result.getString("password").equals(Hash.sha512(password)))
					return true;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return false;
	}

	public static ArrayList<String> getPlayers(Database database, String user) {
		ResultSet result = select(database, String.format(GET_USER_PLAYERS, user));
		ArrayList<String> players = new ArrayList<String>();

		try {
			while (result.next()) {
				players.add(result.getString("player"));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return players;
	}

	public static ResultSet select(Database database, String query) {
		try {
			database.openConnection();

			ResultSet set = database.getStatement().executeQuery(query);

			database.closeConnection();

			return set;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
