package com.cinema.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cinema.database.PgSqlConnectionFactory;
import com.cinema.model.Seat;
import com.cinema.model.Theatre;

public class SeatDao extends AbstractDao<Seat> {
	
	private PgSqlConnectionFactory connectionFactory;
	private AbstractDao<Theatre> theatreDao;
	
	public SeatDao() {
		this.connectionFactory = new PgSqlConnectionFactory();
		this.theatreDao = new TheatreDao();
	}

	@Override
	public Seat findbyId(int id) throws SQLException {
		String query = "select * from seats where id = ?";
		Connection connection = this.connectionFactory.createConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			Seat seat = new Seat();
			seat.setId(resultSet.getInt("id"));
			seat.setTitle(resultSet.getString("name"));
			int theatre_id = resultSet.getInt("theatre_id");
			Theatre theatre = this.theatreDao.findbyId(theatre_id);
			seat.setTheatre(theatre);
			this.connectionFactory.closeConnection();
			return seat;
		}
		return null;
	}

	@Override
	public List<Seat> getAll() throws SQLException {
		String query = "select * from seats";
		List<Seat> seats = new ArrayList<Seat>();
		Connection connection = this.connectionFactory.createConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			Seat seat = new Seat();
			seat.setId(resultSet.getInt("id"));
			seat.setTitle(resultSet.getString("name"));
			int theatre_id = resultSet.getInt("theatre_id");
			Theatre theatre = this.theatreDao.findbyId(theatre_id);
			seat.setTheatre(theatre);
			seats.add(seat);
		}
		this.connectionFactory.closeConnection();
		return seats;
	}

	@Override
	public void create(Seat seat) throws SQLException {
		String query = "insert into seats (name, theatre_id) values (?, ?)";
		Connection connection = this.connectionFactory.createConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, seat.getTitle());
		preparedStatement.setInt(2, seat.getTheatre().getId());
		preparedStatement.executeUpdate();
		this.connectionFactory.closeConnection();
		
	}

	@Override
	public void delete(Seat seat) throws SQLException {
		String query = "delete from seats where id = ?";
		Connection connection = this.connectionFactory.createConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, seat.getId());
		preparedStatement.executeUpdate();
		this.connectionFactory.closeConnection();
		
	}

	

}
