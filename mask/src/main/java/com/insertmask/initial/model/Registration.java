package com.insertmask.initial.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.insertmask.initial.model.Referents;;

@Entity
@Table(name= "registration")
public class Registration {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "customer")
	private String customer;
	
	@Column(name = "office")
	private String office;
	
	private Integer id_referents;
	
	private Integer id_researchedtechnologies;
	
	
	public Registration() {}
	


	public Integer getId_referents() {
		return id_referents;
	}


	public void setId_referents(Integer id_referents) {
		this.id_referents = id_referents;
	}


	public Integer getId_researchedtechnologies() {
	return id_researchedtechnologies;
	}


	public void setId_researchedtechnologies(Integer id_researchedtechnologies) {
		this.id_researchedtechnologies = id_researchedtechnologies;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}


	
}
