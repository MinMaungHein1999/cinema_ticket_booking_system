package com.cinema.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PgSqlConnectionFactory {
	
	private static final String URL = "jdbc:postgresql://localhost:5432/cinema_db";
	private static final String USERNAME = "postgres";
	private static final String PASSWORD = "root";
	
	public Connection createConnection() throws SQLException {
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}

}
	