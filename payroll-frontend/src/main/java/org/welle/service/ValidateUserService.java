package org.welle.service;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.welle.constants.Constants;
import org.welle.pojos.UserValidation;

@ManagedBean(name = "validateUserService")
@SessionScoped
public class ValidateUserService implements Serializable {

    final static Logger logger = Logger.getLogger(ValidateUserService.class);

    private static final long serialVersionUID = 1247960964052218051L;

    public ValidateUserService() {

    }

    public UserValidation validateUser(final String username, final String password) {

        CloseableHttpClient client = HttpClients.createDefault();

        // This is stupied, but I dont have time to optimize it !!!

        HttpPost httpPost = new HttpPost(Constants.VALIDATE_USER_URL);
        String encoding = Base64.getEncoder()
                .encodeToString((Constants.BASIC_USER + ":" + Constants.BASIC_PASSWORD).getBytes());
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);

        String jsonPayload = "{\"username\":\"" + username + "\", \"password\":" + "\"" + password + "\"}";
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonPayload);
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        entity.setContentType("application/json");
        httpPost.setEntity(entity);

        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpPost);
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
        UserValidation userValidation = null;
        try {
            logger.info("Validate json response: " + json);
            userValidation = mapper.readValue(json, UserValidation.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userValidation;
    }

}