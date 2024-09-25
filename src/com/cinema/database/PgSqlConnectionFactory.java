package com.cinema.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PgSqlConnectionFactory {
	
	private Connection connnection;
	private static final String URL = "jdbc:postgresql://localhost:5432/cinema_db";
	private static final String USERNAME = "postgres";
	private static final String PASSWORD = "root";
	
	public Connection createConnection() throws SQLException {
		this.connnection =  DriverManager.getConnection(URL, USERNAME, PASSWORD);
		return this.connnection;
	}
	
	public void closeConnection() throws SQLException {
		this.connnection.close();
	}

}
	