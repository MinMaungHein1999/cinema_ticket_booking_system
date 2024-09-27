package com.cinema.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.util.PGInterval;

import com.cinema.database.PgSqlConnectionFactory;
import com.cinema.model.Movie;

public class MovieDao extends AbstractDao<Movie>{
	
	private PgSqlConnectionFactory connectionFactory;
	
	public MovieDao() {
		this.connectionFactory = new PgSqlConnectionFactory();
	}

	@Override
	public Movie findbyId(int id) throws SQLException {
		String query = "select * from movies where id = ?";
		Connection connection = this.connectionFactory.createConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			Movie movie = new Movie();
			movie.setId(resultSet.getInt("id"));
			movie.setTitle(resultSet.getString("title"));
			PGInterval duration = (PGInterval) resultSet.getObject("duration");
			movie.setDuration(duration.getHours() + "hr : "+ duration.getMinutes()+ " min");
			this.connectionFactory.closeConnection();
			return movie;
		}
		
		return null;
	}

	@Override
	public List<Movie> getAll() throws SQLException {
		String query = "select * from movies";
		List<Movie> movies = new ArrayList<>();
		Connection connection = this.connectionFactory.createConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			Movie movie = new Movie();
			movie.setId(resultSet.getInt("id"));
			movie.setTitle(resultSet.getString("title"));
			String duration = resultSet.getString("duration");
			movie.setDuration(duration);		
			movies.add(movie);
		}
		this.connectionFactory.closeConnection();
		return movies;
	}

	@Override
	public void create(Movie movie) throws SQLException {
		String query = "insert into movies (title, duration) values (?, ?)";
		
		Connection connection = this.connectionFactory.createConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, movie.getTitle());
		preparedStatement.setString(2, movie.getDuration());
		preparedStatement.executeUpdate();
		this.connectionFactory.closeConnection();
		
	}

	@Override
	public void delete(Movie movie) throws SQLException {
		String query = "delete from movies where id = ?";
		Connection connection = this.connectionFactory.createConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, movie.getId());
		preparedStatement.executeUpdate();
		this.connectionFactory.closeConnection();
		
	}

}
