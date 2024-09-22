package com.cinema.model;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
	private int id;
	private Movie movie;
	private Theatre threatre;
	private TimeZone timezone;
	private List<Ticket> tickets = new ArrayList<Ticket>();
	
}
