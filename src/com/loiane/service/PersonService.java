package com.loiane.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loiane.dao.PersonDAO;
import com.loiane.model.Person;
import com.loiane.util.Util;

@Service
public class PersonService {
	
	private PersonDAO personDAO;
	private Util util;

	//Get all contacts
	@Transactional(readOnly=true)
	public List<Person> getContactList(){

		return personDAO.getContacts();
	}
	
	//Create new Contact/Contacts
	@Transactional
	public List<Person> create(Object data){
		
        List<Person> newContacts = new ArrayList<Person>();
		
		List<Person> list = util.getContactsFromRequest(data);
		
		for (Person contact : list){
			newContacts.add(personDAO.saveContact(contact));
		}
		System.out.println(newContacts);
		return newContacts;
	}
	
	//Update contact/contacts
	@Transactional
	public List<Person> update(Object data){
		
		List<Person> returnContacts = new ArrayList<Person>();
		
		List<Person> updatedContacts = util.getContactsFromRequest(data);
		
		for (Person contact : updatedContacts){
			returnContacts.add(personDAO.saveContact(contact));
		}
		return returnContacts;
	}
	
	//Delete contact/contacts
	@Transactional
	public void delete(Object data){
		
		if (data.toString().indexOf('[') > -1){
			
			List<Integer> deleteContacts = util.getListIdFromJSON(data);
			
			for (Integer id : deleteContacts){
				personDAO.deleteContact(id);
			}		
		} else {		
			Integer id = Integer.parseInt(data.toString());
			personDAO.deleteContact(id);
		}
	}
	
	@Autowired
	public void setContactDAO(PersonDAO contactDAO) {
		this.personDAO = contactDAO;
	}

	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}
	
}
