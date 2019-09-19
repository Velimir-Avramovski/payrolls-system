package org.welle.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.welle.database.models.Employee;
import org.welle.database.service.DbService;
import org.welle.pojos.UserDetails;
import org.welle.pojos.UserValidation;

@Named
@RequestScoped
public class UserAndPayrollService {

    @Inject
    DbService dbService;

    public UserValidation validateUser(UserDetails userDetails) {
        Employee employee = dbService.getEmployeeByName(userDetails.getUsername());
        UserValidation userValidator;
        if (employee != null && employee.getPassword().equals(userDetails.getPassword())) {
            userValidator = new UserValidation();
            userValidator.setUsername(userDetails.getUsername());
            userValidator.setAuthtorized(true);
        } else {
            userValidator = new UserValidation();
            userValidator.setAuthtorized(false);
        }
        return userValidator;
    }

}
