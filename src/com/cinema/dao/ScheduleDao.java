package com.cinema.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cinema.database.PgSqlConnectionFactory;
import com.cinema.model.Movie;
import com.cinema.model.Schedule;
import com.cinema.model.Theatre;

public class ScheduleDao extends AbstractDao<Schedule>{
	
	private PgSqlConnectionFactory connectionFactory;
	private AbstractDao<Movie> movieDao;
	private AbstractDao<Theatre> theatreDao;
	
	public ScheduleDao() {
		this.connectionFactory = new PgSqlConnectionFactory();
		this.movieDao = new MovieDao();
		this.theatreDao = new TheatreDao();
	}

	@Override
	public Schedule findbyId(int id) throws SQLException {
		String query = "select * from schedules where id = ?";
		Connection connection = this.connectionFactory.createConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			Schedule schedule = new Schedule();
			schedule.setId(resultSet.getInt("id"));
			int movieId = resultSet.getInt("movie_id");
			Movie movie = this.movieDao.findbyId(movieId);
			schedule.setMovie(movie);
			int theatreId = resultSet.getInt("theatre_id");
			Theatre theatre = this.theatreDao.findbyId(theatreId);
			schedule.setThreatre(theatre);
			schedule.setStartTime(resultSet.getTime("start_time"));
			schedule.setEndTime(resultSet.getTime("end_time"));
			schedule.setPublicDate(resultSet.getDate("public_date"));
			this.connectionFactory.closeConnection();
			return schedule;
		}
		return null;
	}

	@Override
	public List<Schedule> getAll() throws SQLException {
		String query = "select * from schedules";
		Connection connection = this.connectionFactory.createConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		ResultSet resultSet = preparedStatement.executeQuery();
		List<Schedule> schedules = new ArrayList<>();
		while(resultSet.next()) {
			Schedule schedule = new Schedule();
			schedule.setId(resultSet.getInt("id"));
			int movieId = resultSet.getInt("movie_id");
			Movie movie = this.movieDao.findbyId(movieId);
			schedule.setMovie(movie);
			int theatreId = resultSet.getInt("theatre_id");
			Theatre theatre = this.theatreDao.findbyId(theatreId);
			schedule.setThreatre(theatre);
			schedule.setStartTime(resultSet.getTime("start_time"));
			schedule.setEndTime(resultSet.getTime("end_time"));
			schedule.setPublicDate(resultSet.getDate("public_date"));
			schedules.add(schedule);
		}
		this.connectionFactory.closeConnection();
		return schedules;
	}

	@Override
	public void create(Schedule schedule) throws SQLException {
		String query = "INSERT INTO schedules (movie_id, theatre_id, start_time, end_time, public_date) VALUES (?, ?, ?, ?, ?)";
		Connection connection = this.connectionFactory.createConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		
		preparedStatement.setInt(1, schedule.getMovie().getId());
		preparedStatement.setInt(2, schedule.getThreatre().getId());
		preparedStatement.setTime(3, schedule.getStartTime());
		preparedStatement.setTime(4, schedule.getEndTime());
		preparedStatement.setDate(5, schedule.getPublicDate());
		
		preparedStatement.executeUpdate();
		this.connectionFactory.closeConnection();
		
	}

	@Override
	public void delete(Schedule schedule) throws SQLException {
		String query = "delete from schedules where id = ?";
		
		Connection connection = this.connectionFactory.createConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, schedule.getId());
		preparedStatement.executeUpdate();
		this.connectionFactory.closeConnection();
		
	}

}
