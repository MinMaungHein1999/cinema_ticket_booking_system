package com.cinema.service;

import java.io.IOException;
import java.sql.SQLException;

import com.cinema.dao.AbstractDao;
import com.cinema.dao.SeatDao;
import com.cinema.dao.TheatreDao;
import com.cinema.model.Seat;
import com.cinema.model.Theatre;

public class SeatService extends BaseService<Seat>{
	
	private static AbstractDao<Seat> seatDao = new SeatDao();
	private TheatreService theatreService;
	private TheatreDao theatreDao;
	
	public SeatService() {
		super(seatDao);
		this.abstractDao = new SeatDao();
		this.theatreDao = new TheatreDao();
		this.theatreService = new TheatreService(theatreDao);
		
	}

	@Override
	public Seat getEntityObject(int id) {
		Seat seat = new Seat();
		seat.setId(id);
		return seat;
	}

	@Override
	public String getEntity() {
		return "Seat";
	}

	@Override
	public void register() throws SQLException, IOException {
		System.out.print("Enter Seat Name : ");
		String name = br.readLine();
		this.theatreService.getAll();
		System.out.print("Enter Theatre Id : ");
		int theatre_id = Integer.parseInt(br.readLine());
		Theatre theatre = new Theatre();
		Seat seat = new Seat();
		seat.setTitle(name);
		theatre.setId(theatre_id);
		seat.setTheatre(theatre);
		this.abstractDao.create(seat);
		
	}

}
