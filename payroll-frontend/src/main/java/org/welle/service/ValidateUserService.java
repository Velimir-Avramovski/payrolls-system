package org.welle.service;

import java.io.Serializable;

import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(value = "validateUserService")
@SessionScoped
public class ValidateUserService implements Serializable {

    private static final long serialVersionUID = 1247960964052218051L;

    public ValidateUserService() {

    }

    public boolean validateUser() {
        return false;
    }

}