package org.welle.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.Base64;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.welle.constants.Constants;
import org.welle.pojos.Payroll;

@ManagedBean(name = "getAllPayrollsForUser")
@SessionScoped
public class GetAllPayrollsForUser implements Serializable {

    private static final long serialVersionUID = 7826955452063362958L;

    final static Logger logger = Logger.getLogger(GetAllPayrollsForUser.class);

    public GetAllPayrollsForUser() {

    }

    public List<Payroll> getAllPayrollsForUser(final String userId) {

        CloseableHttpClient client = HttpClients.createDefault();

        // This is stupied, but I dont have time to optimize it !!!

        String urlToExecute = Constants.GET_ALL_PAYROLLS_URL.replace("{id}", userId);
        logger.info("Calling url: " + urlToExecute);

        HttpGet httpGet = new HttpGet(urlToExecute);
        String encoding = Base64.getEncoder()
                .encodeToString((Constants.BASIC_USER + ":" + Constants.BASIC_PASSWORD).getBytes());
        httpGet.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);

        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpGet);
            logger.info("Response status: " + response.getStatusLine().getStatusCode());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String json = null;
        try {
            json = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (UnsupportedOperationException | IOException e) {
            e.printStackTrace();
        }

        ObjectMapper mapper = new ObjectMapper();
        List<Payroll> payrolls = null;
        try {
            logger.info("Validate json response: " + json);
            payrolls = mapper.readValue(json, List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return payrolls;
    }

    public void donwloadPayroll(final String payrollName){

    }
}