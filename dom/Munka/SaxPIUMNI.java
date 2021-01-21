package com.journaldev.xml.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.helpers.DefaultHandler;

import com.journaldev.xml.Employee;

public class MyHandler extends DefaultHandler {

    public class Person {
	private String city;
        private int id;
        private int age;
        private String name;
        
	public String getCity() {
            return city;
        }
        public void setCity(String city) {
            this.city = city;
        }

        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public int getAge() {
            return age;
        }
        public void setAge(int age) {
            this.age = age;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        

        @Override
        public String toString() {
            return "Person: ID="+this.id+" Name=" + this.name + " Age=" + this.age + " City=" + this.city;
        }

    }

	
	private List<Person> personList = null;
	private Person person = null;
	private StringBuilder data = null;


	public List<Person> getPersonList() {
		return personList;
	}

    boolean bName = false;
	boolean bAge = false;
	boolean bCity = false;

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		if (qName.equalsIgnoreCase("Person")) {
			String id = attributes.getValue("id");
			person = new Person();
			person.setId(Integer.parseInt(id));
			if (personList == null)
				personList = new ArrayList<>();
		} else if (qName.equalsIgnoreCase("name")) {
			bName = true;
		} else if (qName.equalsIgnoreCase("age")) {
			bAge = true;
		} else if (qName.equalsIgnoreCase("city")) {
			bCity = true;
		}
		data = new StringBuilder();
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (bAge) {
			// age element, set Employee age
			person.setAge(Integer.parseInt(data.toString()));
			bAge = false;
		} else if (bName) {
			person.setName(data.toString());
			bName = false;
		} else if (bCity) {
			person.setCity(data.toString());
			bCity = false;
		}
		
		if (qName.equalsIgnoreCase("Person")) {
			// add Person object to list
			personList.add(person);
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {

		data.append(new String(ch, start, length));
	}
}