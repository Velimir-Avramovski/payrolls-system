package org.welle.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.welle.pojos.Payroll;
import org.welle.service.GetAllPayrollsForUser;
import org.welle.utils.SessionUtils;

@ManagedBean
@SessionScoped
public class PayrollsDetails implements Serializable {

   private static final long serialVersionUID = -408716736418240528L;

   final static Logger logger = Logger.getLogger(PayrollsDetails.class);

   @ManagedProperty(value = "#{sessionUtils}")
   SessionUtils sessionUtils;

   @ManagedProperty(value = "#{getAllPayrollsForUser}")
   GetAllPayrollsForUser payrollsForUser;

   private String username;

   private String userId;

   private String downloadPayrollName;

   private List<Payroll> payrolls;

   @PostConstruct
   public void onPostConstruct() {
      HttpSession session = sessionUtils.getSession();
      this.userId = session.getAttribute("userId").toString();
      this.username = session.getAttribute("username").toString();
      logger.info("Get payrolls for user: " + this.userId);
      this.payrolls = payrollsForUser.getAllPayrollsForUser(userId);
      logger.info("Payrolls found: " + payrolls.size());
   }

   public void onClickPayroll(String payrollName) {
      this.downloadPayrollName = payrollName;
   }

   public void downloadPayroll(String payrollName) {
      try {
         payrollsForUser.donwloadPayroll(payrollName);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public void viewPayroll() {

   }

   public PayrollsDetails() {

   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public SessionUtils getSessionUtils() {
      return sessionUtils;
   }

   public void setSessionUtils(SessionUtils sessionUtils) {
      this.sessionUtils = sessionUtils;
   }

   public GetAllPayrollsForUser getPayrollsForUser() {
      return payrollsForUser;
   }

   public void setPayrollsForUser(GetAllPayrollsForUser payrollsForUser) {
      this.payrollsForUser = payrollsForUser;
   }

   public String getUserId() {
      return userId;
   }

   public void setUserId(String userId) {
      this.userId = userId;
   }

   public List<Payroll> getPayrolls() {
      return payrolls;
   }

   public void setPayrolls(List<Payroll> payrolls) {
      this.payrolls = payrolls;
   }

   public String getDownloadPayrollName() {
      return downloadPayrollName;
   }

   public void setDownloadPayrollName(String downloadPayrollName) {
      this.downloadPayrollName = downloadPayrollName;
   }

}