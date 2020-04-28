package com.insertmask.initial.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Spliterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.lang3.*;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.apache.commons.lang3.StringEscapeUtils;
import org.hibernate.mapping.Map;

import com.codesnippets4all.json.parsers.JSONParser;
import com.codesnippets4all.json.parsers.JsonParserFactory;
import com.insertmask.initial.model.*;

import net.minidev.json.JSONObject;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class Controller {

	@Autowired
	IReferents ref;
	
	@Autowired
	IRegistration reg;
	
	@Autowired
	IResearchedTechnologies resTeck;
	
	int lastIdRef;
	int lastIdResTeck;
	
	
    
    public static String cleanIt(String arg0) {
    	String s;
		s = arg0.replaceAll("[/.<.>.^.+.*.#.]", "").replaceAll("'.", " ");
		return s;
    }
	
	@RequestMapping(method = RequestMethod.POST, value = "/registration")
	@ResponseBody
	//public void insertNewRegistration(@RequestBody Referents obj) {
	//public void insertNewRegistration(@RequestBody Registration rg, @RequestBody Registration rg, @RequestBody Registration rg) {
	public void insertNewRegistration(@RequestBody Object rg) {
		/* JSON EXAMPLE
		 * {
			"namereferents": "referents name",
			"customer": "customer",
			"office": "office",
			"nameresearchedtechnlogies": [{	"id1": "javascript"},{	"id2": "c-sharp"}, {	"id10": "crm dynamics 365"}]
		}
		 * */
		
		HashMap<String, String> listNameresearchedtechnlogies = new HashMap();
		
		ref.findAll().forEach(searchRf -> {
			if(searchRf.getId() != null) {
				lastIdRef = searchRf.getId()+1;
			} else {
				lastIdRef = 1;
			}
			
		});
		
		resTeck.findAll().forEach(searchRf -> {
			if(searchRf.getId_customer() != null) {
				lastIdResTeck = searchRf.getId_customer()+1;
			} else {
				lastIdResTeck = 1;
			}
			if(searchRf.getId_customer() != null) {
				listNameresearchedtechnlogies.put(searchRf.getId().toString(), searchRf.getNameresearchedtechnlogies());
			}
		});
		
		@SuppressWarnings("rawtypes")
		LinkedHashMap map = (LinkedHashMap) rg;
		
		//Try Insert Registration Complete
		try {
			//Try Insert Referent Entity
			try {
				Referents referents = new Referents();
				String namereferents = map.get("namereferents").toString().toString();
				if(namereferents != "") {
					referents.setNamereferents(cleanIt(namereferents));
					ref.save(referents);
				}
			}
			catch(Exception e)  {
				System.out.print(e);
				System.out.println("  namereferents is null or invalid");
				lastIdRef = 0;
			}
			
			//Try Insert Researched Technology Entity
			try {
				ArrayList a = new ArrayList();
				JSONObject obj = new JSONObject();
				a= (ArrayList) map.get("nameresearchedtechnlogies");
				for(int i = 0; i < a.size(); i++) {
					obj.appendField("id"+i, (String) ((LinkedHashMap) a.get(i)).get("id"+i));
					//obj.appendField((a.get(i).toString()),0);
					//obj.appendField((String) ((LinkedHashMap) a.get(0)).get("java"),0);
				}
				a = (ArrayList) map.get("nameresearchedtechnlogies");
				int j = 0;
				for(int i = 0; i < a.size(); i++) {
					if((String) ((LinkedHashMap) a.get(i)).get("id"+j) != null) {
						ResearchedTechnologies researchedTechnologies = new ResearchedTechnologies();
						obj.appendField("id"+j, (String) ((LinkedHashMap) a.get(i)).get("id"+j));
						researchedTechnologies.setNameresearchedtechnlogies(cleanIt(obj.get("id"+j).toString()));
						researchedTechnologies.setId_customer(lastIdResTeck);
						resTeck.save(researchedTechnologies);
					}
					else {
						i--;
					}
					j++;
				}
			}
			catch(Exception e)  {
				System.out.print(e);
				System.out.println("  nameresearchedtechnlogies is null or invalid, insert not execute");
				lastIdResTeck = 0;
			}
			
			//Try Insert Registration Entity
			try {
				Registration registration = new Registration();
				try {
					String customer = map.get("customer").toString().toString();
					if(customer != "") {
						registration.setCustomer(cleanIt(customer));
					}

				}
				catch(Exception e)  {
					System.out.print(e);
					System.out.println("  customer is null or invalid");
				}
				try {
					String office = map.get("office").toString().toString();
					if(office != "")
						registration.setOffice(cleanIt(office));
				}
				catch(Exception e)  {
					System.out.print(e);
					System.out.println("  office is null or invalid");
				}
				if(registration.getCustomer() != null && registration.getOffice() != null) {
					registration.setId_researchedtechnologies(lastIdResTeck);
					registration.setId_referents(lastIdRef);
					System.out.println(registration.getCustomer());
					System.out.println(registration.getOffice());
					System.out.println(registration.getId_referents());
					System.out.println(registration.getId_researchedtechnologies());
					reg.save(registration);
				}
			}
			catch(Exception e)  {
				System.out.print(e);
				System.out.println("  Insert registration Entity not execute");
			}
			
		}
		catch(Exception e)  {
			System.out.print(e);
			System.out.println("  registration not execute");
		}

		
		
//		if(referents.getNamereferents() != null) {
//			
//			//ref.save(referents);
//		}
//		
//		if(researchedTechnologies.getNameresearchedtechnlogies() != null) {
//			//System.out.println(researchedTechnologies);
//			//resTeck.save(researchedTechnologies);
//		}
		
		//referents.setId(lastIdRef);
		//researchedTechnologies.setId(lastIdResTeck);
		
		//System.out.println("sono qui");
		//registration.setIdReferents(lastIdRef);
		//registration.setIdResearchTechnologies(lastIdResTeck);
		
		//ref.save(referents);
		//resTeck.save(researchedTechnologies);
		//reg.save(registration);
		
		//remove all element null
//		int num = -1;
//		if(searchRf.getId_customer() == null) {
			//num = searchRf.getId_customer();
			//System.out.println(num);
//			resTeck.deleteById(searchRf.getId());
//		}
		
		//remove all element for id_customer
//		if(num == 0) {
//			resTeck.deleteById(searchRf.getId());
//		}
		
//		//filtro per id customer in lettura dal db
//		resTeck.findAll().forEach(searchRf -> {
//
//			if(searchRf.getId_customer() == 2) {
//				filter.put(searchRf.getId().toString(), searchRf.getNameresearchedtechnlogies());
//			}
//		});
	}
	
}
