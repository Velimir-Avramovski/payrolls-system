package org.welle.api;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import org.welle.models.EmployeDetails;

/**
 * @author Ken Finnigan
 */
@Path("/payrolls")
@ApplicationScoped
public class PayrollRestAPI {

    @PersistenceContext
    EntityManager em;

    @Context
    SecurityContext securityContext;

    @GET
    @Path("/payroll/{id}/{year}/{month}")
    @Produces("application/json")
    public EmployeDetails getPayrollForUserAndDate(String id, String year, String month) {
        return securityContext.isUserInRole("user") ? new EmployeDetails() : null;
    }
}
