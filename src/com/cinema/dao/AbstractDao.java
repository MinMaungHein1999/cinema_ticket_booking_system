package com.cinema.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cinema.database.PgSqlConnectionFactory;

public abstract class AbstractDao<T> {
	
	private PgSqlConnectionFactory connectionFactory;
	
	public AbstractDao() {
		this.connectionFactory = new PgSqlConnectionFactory();
	}
	
	public abstract String getTableName();
	public abstract T convertToObject(ResultSet resultSet)throws SQLException;
	public abstract String getInsertValues();
	public abstract void setParameters(PreparedStatement preparedStatement, T entity)throws SQLException;
	
	public String getInsertQuery(){
		String query = "insert into "+ getTableName() +" "+ this.getInsertValues();
		return query;
	}
	
	public T findbyId(int id) throws SQLException{
		String query = "select * from "+ getTableName() +" where id = ?";
		Connection connection = this.connectionFactory.createConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			T object = this.convertToObject(resultSet);
			this.connectionFactory.closeConnection();
			return object;
		}
		return null;
	}
	
	public List<T> getAll() throws SQLException {
		List<T> objects = new ArrayList<>();
		String query = "select * from "+ this.getTableName();
		Connection connection = this.connectionFactory.createConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			T object = this.convertToObject(resultSet);
			objects.add(object);
		}
		this.connectionFactory.closeConnection();
		return objects;
	}
	
	public void create(T entity) throws SQLException{
		String query = this.getInsertQuery();
		Connection connection = this.connectionFactory.createConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		this.setParameters(preparedStatement, entity);
		preparedStatement.executeUpdate();
		this.connectionFactory.closeConnection();
	}
	
	public void delete(int id) throws SQLException{
		String query = "delete from "+ this.getTableName()+ " where id = ?";
		Connection connection = this.connectionFactory.createConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, id);
		preparedStatement.executeUpdate();
		this.connectionFactory.closeConnection();
	}
	

}
