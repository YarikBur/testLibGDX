package ru.yarikbur.test.utils.database;

import ru.yarikbur.test.utils.math.Hash;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Queries {
	public static final String SET_ONLINE = "UPDATE `online` SET `online`='%d' WHERE `id_player`='%s';";
	public static final String GET_USER_HASH = "SELECT `password` FROM `users` WHERE `login`='%s';";
	public static final String GET_USER_PLAYERS = "SELECT `player` FROM `players` WHERE `user`='%s';";
	public static final String GET_USER_ID = "SELECT `id` FROM `users` WHERE `login`='%s';";
	public static final String GET_PLAYER_ID = "SELECT `id` FROM `players` WHERE `player`='%s';";
	public static final String GET_PLAYER_LOCATION =
			"SELECT `id_map`,`location` FROM `players_location` WHERE `id_player`='%s';";
	public static final String SET_PLAYER_LOCATION =
			"UPDATE `players_location` SET `location`='%s' WHERE `id_player`='%s';";

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
	 * @param player_id A unique character id.
	 * @param online Settable online status
	 */
	public static void updatePlayerOnline(Database database, int player_id, boolean online) {
		update(database, String.format(SET_ONLINE, boolToInt(online), player_id));
	}

	public static void updateLocationPlayer(Database database, int[] position, int player_id) {
		String loc = position[0] + ":" + position[1];
		update(database, String.format(SET_PLAYER_LOCATION, loc, player_id));
	}

	public static int[] getLocationPlayer(Database database, int player_id) {
		int[] location = new int[2];
		ResultSet result = select(database, String.format(GET_PLAYER_LOCATION, player_id));

		try {
			if (result.next()) {
				String[] loc = result.getString("location").split(":");

				for (int i = 0; i < loc.length; i++) {
					location[i] = Integer.parseInt(loc[i]);
				}

			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return location;
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

	/**
	 * Checks the entered username and password
	 * @param database Wrapper database (ru.yarikbur.test.utils.database)
	 * @param user User whose password needs to be checked
	 * @param password Verification password
	 * @return Returns "true" if the password and user match
	 */
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

	/**
	 *
	 * @param database
	 * @param query
	 * @param str
	 * @return
	 */
	public static int getUID(Database database, String query, String str) {
		ResultSet result = select(database, String.format(query, str));

		try {
			if (result.next()) {
				return result.getInt("id");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return -1;
	}

	/**
	 * Returns the user's character names
	 * @param database Wrapper database (ru.yarikbur.test.utils.database)
	 * @param user The name of the user from whom you want to take all the characters
	 * @return Returns an ArrayList with character names
	 */
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

	/**
	 * The method responsible for retrieving fields in a database table.s
	 * @param database Wrapper database (ru.yarikbur.test.utils.database)
	 * @param query Sting SQL query
	 */
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
