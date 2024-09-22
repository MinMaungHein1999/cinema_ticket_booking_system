package com.cinema.service;

import java.io.IOException;
import java.sql.SQLException;

import com.cinema.dao.AbstractDao;
import com.cinema.model.Customer;

public class CustomerService extends BaseService<Customer>{
	
	private AbstractDao customerDao;

	public CustomerService(AbstractDao customerDao) {
		super(customerDao);
		this.customerDao = customerDao;
	}

	@Override
	public Customer getEntityObject(int id) {
		Customer customer = new Customer();
		customer.setId(id);
		return customer;
	}

	@Override
	public String getEntity() {
		return "Customer";
	}

	@Override
	public void register() throws SQLException, IOException {
		System.out.print("Enter customer name: ");
		String name = br.readLine();
		Customer customer = new Customer();
		customer.setName(name);
		this.customerDao.create(customer);
	}

}
