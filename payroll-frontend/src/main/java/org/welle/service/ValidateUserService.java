package org.welle.service;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class ValidateUserService implements Serializable {

    private static final long serialVersionUID = 1247960964052218051L;

    public ValidateUserService() {

    }

    public boolean validateUser() {
        return false;
    }

}