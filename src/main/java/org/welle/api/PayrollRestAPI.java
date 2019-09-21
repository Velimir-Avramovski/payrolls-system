package org.welle.api;

import java.io.File;
import java.io.IOException;

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

	@Context
	SecurityContext securityContext;

	@Inject
	UserAndPayrollService userAndPayrollService;

	@GET
	@Path("/download/{id}/{year}/{month}")
	@Produces("application/pdf")
	public Response downloadPayrollForUser(@PathParam(value = "id") String id, @PathParam(value = "year") String year,
			@PathParam(value = "month") String month) {
		File fileDownload = null;
		try {
			fileDownload = new File(userAndPayrollService.downloadPayrollForEmployee(id, year, month));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ResponseBuilder response = Response.ok((Object) fileDownload);
		response.header("Content-Disposition", "attachment;filename=" + fileDownload.getName());
		return response.build();
	}

	@GET
	@Path("/get/all/{employeeId}")
	@Produces("application/json")
	public Response getAllPayrollsListForEmployee(@PathParam("employeeId") String employeeId) {
		return Response.status(Response.Status.OK).entity(userAndPayrollService.getAllPayrollsForEmployee(employeeId))
				.build();
	}
}
