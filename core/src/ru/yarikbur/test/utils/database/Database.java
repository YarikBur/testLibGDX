package ru.yarikbur.test.utils.database;

import com.badlogic.gdx.Gdx;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class Database {
	private Connection connection;

	private final String DB_URL;
	private final String DB_USER;
	private final String DB_PASS;

	public Database(String base_url, String user, String password) {
		DB_URL = base_url;
		DB_USER = user;
		DB_PASS = password;
	}

	public void openConnection() throws SQLException {
		connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
	}

	public ResultSet query(String query) throws SQLException {
		openConnection();

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);

		closeConnection();
		statement.close();
		resultSet.close();

		return resultSet;
	}

	public Statement getSatement() throws SQLException {
		return connection.createStatement();
	}

	public Connection getConnection() {
		return this.connection;
	}

	public void closeConnection() throws SQLException {
		this.connection.close();
	}
}
