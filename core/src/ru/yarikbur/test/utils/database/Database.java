package ru.yarikbur.test.utils.database;

import java.sql.*;

public class Database {
	private Connection connection;

	private final String DB_URL;
	private final String DB_USER;
	private final String DB_PASS;

	/**
	 * Initialisation local variables for database
	 * @param base_url JDBC links to MariaDB
	 * @param user Database user
	 * @param password Database password for user
	 */
	public Database(String base_url, String user, String password) {
		DB_URL = base_url;
		DB_USER = user;
		DB_PASS = password;
	}

	/**
	 * Open a database connection
	 */
	public void openConnection() throws SQLException {
		connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
	}

	/**
	 * @return Statement from selected database
	 */
	public Statement getStatement() throws SQLException {
		return connection.createStatement();
	}

	/**
	 * @return Connection a database
	 */
	public Connection getConnection() {
		return this.connection;
	}

	/**
	 * Close a database connection
	 */
	public void closeConnection() throws SQLException {
		this.connection.close();
	}
}
