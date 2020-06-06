package com.jack.sharding.po;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 7342443178706890459L;
	private Long id;

	private String city;
	
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
