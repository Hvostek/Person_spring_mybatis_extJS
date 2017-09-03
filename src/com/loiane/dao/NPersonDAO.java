package com.loiane.dao;

import java.util.List;

import com.loiane.model.Person;

public interface NPersonDAO {

	List<Person> getContacts();
	
	void deleteContact(int id);
	
	Person saveContact(Person person);
	
}
