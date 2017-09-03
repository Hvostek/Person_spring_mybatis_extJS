package com.loiane.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loiane.model.Person;
import com.loiane.service.PersonService;

@Controller
public class PersonController  {

	private PersonService contactService;
	
	@RequestMapping(value="/contact/view.action")
	public @ResponseBody Map<String,? extends Object> view() throws Exception {

		try{

			List<Person> contacts = contactService.getContactList();

			return getMap(contacts);

		} catch (Exception e) {

			return getModelMapError("Error retrieving Contacts from database.");
		}
	}
	
	@RequestMapping(value="/contact/create.action")
	public @ResponseBody Map<String,? extends Object> create(@RequestParam Object data) throws Exception {

		try{

			List<Person> contacts = contactService.create(data);

			return getMap(contacts);

		} catch (Exception e) {

			return getModelMapError("Error trying to create contact.");
		}
	}
	
	@RequestMapping(value="/contact/update.action")
	public @ResponseBody Map<String,? extends Object> update(@RequestParam Object data) throws Exception {
		try{

			List<Person> contacts = contactService.update(data);

			return getMap(contacts);

		} catch (Exception e) {

			return getModelMapError("Error trying to update contact.");
		}
	}
	
	@RequestMapping(value="/contact/delete.action")
	public @ResponseBody Map<String,? extends Object> delete(@RequestParam Object data) throws Exception {
		
		try{

			contactService.delete(data);

			Map<String,Object> modelMap = new HashMap<String,Object>(3);
			modelMap.put("success", true);

			return modelMap;

		} catch (Exception e) {

			return getModelMapError("Error trying to delete contact.");
		}
	}
	
	
	private Map<String,Object> getMap(List<Person> contacts){
		
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		modelMap.put("total", contacts.size());
		modelMap.put("data", contacts);
		modelMap.put("success", true);
		
		return modelMap;
	}
	
	private Map<String,Object> getModelMapError(String msg){

		Map<String,Object> modelMap = new HashMap<String,Object>(2);
		modelMap.put("message", msg);
		modelMap.put("success", false);

		return modelMap;
	} 

	@Autowired
	public void setContactService(PersonService contactService) {
		this.contactService = contactService;
	}

}
