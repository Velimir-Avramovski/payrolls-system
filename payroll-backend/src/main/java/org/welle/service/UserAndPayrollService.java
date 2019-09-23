package org.welle.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Named;

import static org.welle.constants.Constants.FILE_EXTENTION;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.welle.database.models.Employee;
import org.welle.database.models.Payroll;
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
            userValidator.setUserId(employee.getId());
        } else {
            userValidator = new UserValidation();
            userValidator.setAuthtorized(false);
        }
        return userValidator;
    }

    public String downloadPayrollForEmployee(final String id, final String year, final String month)
            throws IOException {
        SftpService sftpClient = new SftpService();
        final String fileName = id + "_" + year + "_" + month;
        File fout = null;
        try {
            sftpClient.connect();
            // This will download it to the server from SFTP.
            // Build the path as it branches on SFTP side.
            fout = sftpClient.download(year + "/" + month + "/" + fileName + FILE_EXTENTION, fileName + FILE_EXTENTION);
            sftpClient.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Return the path for the client.
        return fout.getAbsoluteFile().getAbsolutePath();
    }

    public List<Payroll> getAllPayrollsForEmployee(final String employeeId){
        List<Payroll> payrolls = dbService.getPayrollsById(employeeId);
        return payrolls;
    }

}
