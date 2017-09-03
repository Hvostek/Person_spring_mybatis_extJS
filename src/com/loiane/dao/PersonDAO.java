package com.loiane.dao;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Repository;

import com.loiane.model.Person;

@Repository
public class PersonDAO implements NPersonDAO {

	private static SqlSession session;

	static {
		try {
			String resource = "com/loiane/mybatisconf/mybatis-config.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			session = sqlSessionFactory.openSession();
			session.update("com.loiane.model.Person.createTable");
			session.update("com.loiane.model.Person.insertIntoDB");
		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	public List<Person> getContacts() {
		return session.selectList("com.loiane.model.Person.selectAllPersons");
	}

	public void deleteContact(int id) {
		session.delete("com.loiane.model.Person.deletePerson", id);
	}
	@Override
	public Person saveContact(Person person) {
		try {
			session.update("com.loiane.model.Person.updatePerson", person);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return person;
	}

}
