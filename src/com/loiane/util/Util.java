package com.loiane.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

import com.loiane.model.Person;

@Component
public class Util {
	
	//json -> Person object
	private Person getContactFromJSON(Object data){
		JSONObject jsonObject = JSONObject.fromObject(data);
		Person newContact = (Person) JSONObject.toBean(jsonObject, Person.class);
		return newContact;
	}
	
	//json -> list of Person objects
	@SuppressWarnings("unchecked")
	private List<Person> getListContactsFromJSON(Object data){
		JSONArray jsonArray = JSONArray.fromObject(data);
		List<Person> newContacts = (List<Person>) JSONArray.toCollection(jsonArray,Person.class);
		System.out.println(newContacts);
		return newContacts;
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getListIdFromJSON(Object data){
		JSONArray jsonArray = JSONArray.fromObject(data);
		List<Integer> idContacts = (List<Integer>) JSONArray.toCollection(jsonArray,Integer.class);
		System.out.println(idContacts);
		return idContacts;
	}

	//Get list of Persons
	public List<Person> getContactsFromRequest(Object data){
	
		List<Person> list = new ArrayList<Person>();
		if (data.toString().indexOf('[') > -1){
			list = getListContactsFromJSON(data);
			System.out.println("list1"+list);
		} else { 
			Person contact = getContactFromJSON(data);
			list.add(contact);
			}
			
		}
		
		return list;
	}
}
