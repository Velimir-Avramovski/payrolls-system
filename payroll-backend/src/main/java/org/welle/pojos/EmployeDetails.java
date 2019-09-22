package org.welle.pojos;

import java.util.ArrayList;

import org.welle.database.models.Payroll;

public class EmployeDetails {
	
	private String id;
	
	private String name;
	
	private ArrayList<Payroll> payrolls;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Payroll> getPayrolls() {
		return payrolls;
	}

	public void setPayrolls(ArrayList<Payroll> payrolls) {
		this.payrolls = payrolls;
	} 
}