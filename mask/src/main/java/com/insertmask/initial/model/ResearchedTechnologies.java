package com.insertmask.initial.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Embeddable
@Table(name= "researchedtechnologies")
public class ResearchedTechnologies {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "id_customer")
	private Integer id_customer;
	
	@Column(name = "name")
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId_customer() {
		return id_customer;
	}

	public void setId_customer(Integer id) {
		this.id_customer = id;
	}

	public String getNameresearchedtechnlogies() {
		return name;
	}

	public void setNameresearchedtechnlogies(String string) {
		this.name = string;
	}
	

}
