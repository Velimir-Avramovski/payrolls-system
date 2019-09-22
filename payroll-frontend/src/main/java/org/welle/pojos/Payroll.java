package org.welle.pojos;

import java.io.Serializable;

public class Payroll implements Serializable {

	private int id;

	private String payrollId;

	private String userId;

	public Payroll() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPayrollId() {
		return payrollId;
	}

	public void setPayrollId(String payrollId) {
		this.payrollId = payrollId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
