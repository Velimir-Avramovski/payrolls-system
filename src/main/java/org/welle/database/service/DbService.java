package org.welle.database.service;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.welle.database.models.Employee;

@Named
@RequestScoped
public class DbService {

    @PersistenceContext
    EntityManager em;

    public Employee getEmployeeByName(String name) {
        List<Employee> es = em.createNamedQuery("Employee.findByName").setParameter("name", name).getResultList();
        if (!es.isEmpty()) {
            return es.get(0);
        }
        return null;
    }

}