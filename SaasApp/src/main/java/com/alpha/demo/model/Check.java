package com.alpha.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ch")
public class Check {
	@Id
	@Column(nullable = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	

	public Check(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Check() {
		super();
		// TODO Auto-generated constructor stub
	}

}
