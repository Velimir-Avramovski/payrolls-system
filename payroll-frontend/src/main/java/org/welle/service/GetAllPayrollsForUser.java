package org.welle.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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

    public void donwloadPayroll(final String payrollName) throws IOException {
        String[] payrollRequests = payrollName.split("_");

        // This is stupied, but I dont have time to optimize it, we can make HTTP Client
        // and reuse that instead of this boilplate code ...

        String urlToExecute = Constants.DOWNLOAD_PAYROLL_URL.replace("{id}", payrollRequests[0])
                .replace("{year}", payrollRequests[1]).replace("{month}", payrollRequests[2]);
        logger.info("Calling url: " + urlToExecute);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(urlToExecute);
        String encoding = Base64.getEncoder()
                .encodeToString((Constants.BASIC_USER + ":" + Constants.BASIC_PASSWORD).getBytes());
        httpGet.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);

        CloseableHttpResponse cResponse = null;
        try {
            cResponse = client.execute(httpGet);
            logger.info("Response status: " + cResponse.getStatusLine().getStatusCode());
            logger.info("getContentLength: " + cResponse.getEntity().getContentLength());
            logger.info("getContentType: " + cResponse.getEntity().getContentType());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ///////

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
    
        // Initialize response.
        response.reset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
        response.setContentType("application/pdf"); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ServletContext#getMimeType() for auto-detection based on filename.
        response.setHeader("Content-disposition", "attachment; filename=\"" + payrollName + ".pdf\""); // The Save As popup magic is done here. You can give it any filename you want, this only won't work in MSIE, it will use current request URL as filename instead.

        // Write file to response.
        OutputStream output = response.getOutputStream();
        output.write(IOUtils.toByteArray(cResponse.getEntity().getContent()));
        output.close();
    
        // Inform JSF to not take the response in hands.
        facesContext.responseComplete(); // Important! Else JSF will attempt to render the response which obviously will fail since it's already written with a file and closed.
    }
}