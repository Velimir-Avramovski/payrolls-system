package org.welle.database.models;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "EMPLOYEE")
@NamedQueries({ @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e"),
		@NamedQuery(name = "Employee.findByName", query = "SELECT e FROM Employee e WHERE e.name = :name") })
public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(length = 40)
	private String name;

	private String password;

	public Employee() {
	}

	public Employee(String name) {
		this.name = name;
	}

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

	@Override
	public String toString() {
		return name + " " + id;
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof Employee))
			return false;
		Employee that = (Employee) obj;
		if (that.name.equals(this.name) && that.id == this.id)
			return true;
		else
			return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.name);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
