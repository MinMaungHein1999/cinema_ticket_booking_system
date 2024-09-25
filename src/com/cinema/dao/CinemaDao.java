package com.cinema.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cinema.database.PgSqlConnectionFactory;
import com.cinema.model.Cinema;

public class CinemaDao extends AbstractDao<Cinema>{
	
	private PgSqlConnectionFactory connectionFactory;
	
	public CinemaDao() {
		this.connectionFactory = new PgSqlConnectionFactory();
	}

	@Override
	public Cinema findbyId(int id) throws SQLException {
		String query = "select * from cinemas where id = ?";
		Connection connection = this.connectionFactory.createConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			Cinema cinema = new Cinema();
			cinema.setId(resultSet.getInt("id"));
			cinema.setName(resultSet.getString("name"));
			cinema.setAddress(resultSet.getString("address"));
			this.connectionFactory.closeConnection();
			return cinema;
		}
		return null;
	}

	@Override
	public List<Cinema> getAll() throws SQLException {
		String query = "select * from cinemas";
		List<Cinema> cinemas = new ArrayList<>();
		Connection connection = this.connectionFactory.createConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			Cinema cinema = new Cinema();
			cinema.setId(resultSet.getInt("id"));
			cinema.setName(resultSet.getString("name"));
			cinema.setAddress(resultSet.getString("address"));
			cinemas.add(cinema);
			this.connectionFactory.closeConnection();
		}
		
		return cinemas;
	}

	@Override
	public void create(Cinema entity) throws SQLException {
		String query = "insert into cinemas (name, address) values (?, ?)";
		Connection connection = this.connectionFactory.createConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, entity.getName());
		preparedStatement.setString(2, entity.getAddress());
		preparedStatement.executeUpdate();
		this.connectionFactory.closeConnection();
	}

	@Override
	public void delete(Cinema entity) throws SQLException {
		String query = "delete from cinemas where id = ?";
		Connection connection = this.connectionFactory.createConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, entity.getId());
		preparedStatement.executeUpdate();
		this.connectionFactory.closeConnection();
	}

}
