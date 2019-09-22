package org.welle.beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.welle.service.ValidateUserService;
import org.welle.utils.SessionUtils;

@ManagedBean
@SessionScoped
public class Login implements Serializable {

   private static final long serialVersionUID = -408716736418240528L;

   final static Logger logger = Logger.getLogger(Login.class);

   @ManagedProperty(value = "#{validateUserService}")
   ValidateUserService validateUserService;

   @ManagedProperty(value = "#{sessionUtils}")
   SessionUtils sessionUtils;

   private String msg;
   private String username;
   private String password;

   public Login() {

   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getMsg() {
      return msg;
   }

   public void setMsg(String msg) {
      this.msg = msg;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String validateUsernamePassword() {
      logger.info("Login called!");
      boolean valid = validateUserService.validateUser();
      logger.info("valid user: " + valid);
      if (valid) {
         HttpSession session = sessionUtils.getSession();
         session.setAttribute("username", username);
         return "payrolls";
      } else {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
               "Incorrect Username and Passowrd", "Please enter correct username and Password"));
         return "login";
      }
   }

   public String logout() {
      HttpSession session = sessionUtils.getSession();
      session.invalidate();
      return "login";
   }

   public ValidateUserService getValidateUserService() {
      return validateUserService;
   }

   public void setValidateUserService(ValidateUserService validateUserService) {
      this.validateUserService = validateUserService;
   }

   public SessionUtils getSessionUtils() {
      return sessionUtils;
   }

   public void setSessionUtils(SessionUtils sessionUtils) {
      this.sessionUtils = sessionUtils;
   }
}