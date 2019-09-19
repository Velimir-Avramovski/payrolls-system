package org.welle.api;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import org.welle.pojos.EmployeDetails;

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
	public EmployeDetails getPayrollForUserAndDate(@PathParam(value = "id") String id,
			@PathParam(value = "year") String year, @PathParam(value = "month") String month) {
				return null;
	}
}
