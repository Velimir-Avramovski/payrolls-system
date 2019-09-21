package org.welle.database.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "PAYROLL")
@NamedQueries({ @NamedQuery(name = "Payroll.findAll", query = "SELECT e FROM Payroll e"),
		@NamedQuery(name = "Payroll.findById", query = "SELECT e FROM Payroll e WHERE e.id = :id"),
		@NamedQuery(name = "Payroll.findByUserId", query = "SELECT e FROM Payroll e WHERE e.userId = :userId") })
public class Payroll implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "PAYROLL_ID")
	private String payrollId;
	
	@Column(name = "USER_ID")
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
