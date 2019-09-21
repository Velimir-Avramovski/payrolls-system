package org.welle.api;

import java.io.File;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.welle.pojos.EmployeDetails;
import org.welle.service.UserAndPayrollService;

@Path("/action")
@ApplicationScoped
public class PayrollRestAPI {

	@PersistenceContext
	EntityManager em;

	@Context
	SecurityContext securityContext;

	@Inject
	UserAndPayrollService userAndPayrollService;

	@GET
	@Path("/download/{id}/{year}/{month}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response downloadPayrollForUser(@PathParam(value = "id") String id, @PathParam(value = "year") String year,
			@PathParam(value = "month") String month) {
		File fileDownload = userAndPayrollService.downloadPayrollForEmployee(id, year, month);
		ResponseBuilder response = Response.ok((Object) fileDownload);
		response.header("Content-Disposition", "attachment;filename=" + fileDownload.getName());
		return Response.status(Response.Status.OK).entity("OK").build();
	}

	public void getAllPayrollsListForEmployee() {

	}
}
