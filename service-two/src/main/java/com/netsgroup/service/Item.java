package com.netsgroup.service;


import java.io.Serializable;

public class Item implements Serializable {

	private static final long serialVersionUID = 4349229274968827351L;

	private String name;

	private Long age;
	
	private String origin;

	public Item(String name, Long age, String origin) {
		super();
		this.name = name;
		this.age = age;
		this.origin = origin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}
	
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	@Override
	public String toString() {
		return "Item [name=" + name + ", age=" + age + ", origin=" + origin + "]";
	}


}
