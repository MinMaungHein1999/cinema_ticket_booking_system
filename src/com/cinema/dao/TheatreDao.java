package com.cinema.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cinema.database.PgSqlConnectionFactory;
import com.cinema.model.Theatre;

public class TheatreDao extends AbstractDao<Theatre> {
	
	private PgSqlConnectionFactory connectionFactory;
	private CinemaDao cinemaDao;
	
	public TheatreDao() {
		this.connectionFactory = new PgSqlConnectionFactory();
		this.cinemaDao = new CinemaDao();
	}

	@Override
	public Theatre findbyId(int id) throws SQLException {
		String query = "select * from theatres where id = ?";
		Connection connection = this.connectionFactory.createConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			Theatre threatre = new Theatre();
			threatre.setId(resultSet.getInt("id"));
			threatre.setName(resultSet.getString("name"));
			int cinema_id = resultSet.getInt("cinema_id");
			threatre.setCinema(this.cinemaDao.findbyId(cinema_id));
			return threatre;
		}
		
		return null;
	}

	@Override
	public List<Theatre> getAll() throws SQLException {
		String query = "select * from theatres";
		List<Theatre> threaters = new ArrayList<>();
		Connection connection = this.connectionFactory.createConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			Theatre threatre = new Theatre();
			threatre.setId(resultSet.getInt("id"));
			threatre.setName(resultSet.getString("name"));
			int cinema_id = resultSet.getInt("cinema_id");
			threatre.setCinema(this.cinemaDao.findbyId(cinema_id));
			threaters.add(threatre);
		}
		return threaters;
	}

	@Override
	public void create(Theatre threatre) throws SQLException {
		String query = "insert into theatres (name, cinema_id) values (?, ?)";
		Connection connection = this.connectionFactory.createConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, threatre.getName());
		preparedStatement.setInt(2, threatre.getCinema().getId());
		preparedStatement.executeUpdate();
	}

	@Override
	public void delete(Theatre theatre) throws SQLException {
		String query = "delete from theatres where id = ?";
		Connection connection = this.connectionFactory.createConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, theatre.getId());
		preparedStatement.executeUpdate();
	}

}
