package com.cinema.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.cinema.model.Ticket;

public class TicketDao extends AbstractDao<Ticket> {

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ticket convertToObject(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInsertValues() {
		return null;
	}

	@Override
	public void setParameters(PreparedStatement preparedStatement, Ticket entity) throws SQLException {
		// TODO Auto-generated method stub
		
	}

}
