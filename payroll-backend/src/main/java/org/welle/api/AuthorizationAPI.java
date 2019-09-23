package org.welle.api;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.welle.pojos.UserDetails;
import org.welle.pojos.UserValidation;
import org.welle.service.UserAndPayrollService;

@Path("/auth")
@ApplicationScoped
public class AuthorizationAPI {

	@Context
	SecurityContext securityContext;

	@Inject
	UserAndPayrollService userAndPayrollService;

	@POST
	@Path("/validate")
	@Consumes("application/json")
	@Produces("application/json")
	public Response validateUser(UserDetails userDetails) {
		if (securityContext.isUserInRole("user") || securityContext.isUserInRole("admin")) {
			return Response.status(Response.Status.OK).entity(userAndPayrollService.validateUser(userDetails)).build();
		} else {
			UserValidation userValidator = new UserValidation();
			userValidator.setUsername("");
			userValidator.setAuthtorized(false);
			return Response.status(Response.Status.OK).entity(userValidator).build();
		}
	}
}
