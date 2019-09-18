package org.welle.api;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import org.welle.models.UserValidation;

@Path("/auth")
@ApplicationScoped
public class AuthorizationAPI {

    @PersistenceContext
    EntityManager em;

    @Context
    SecurityContext securityContext;

    @GET
    @Path("/validate/user")
    @Produces("application/json")
    public UserValidation get() {
        return securityContext.isUserInRole("user") ? new UserValidation() : null;
    }
}
