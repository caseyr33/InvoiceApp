package controller;
import java.util.*;
import javax.mail.*;
import model.EmailUtil;

public class EmailController {

   public static void init(String Email, String Subject, String Body, String invoiceNo) {
	   String fromEmail = "hanksauceapp@gmail.com"; 
	   final String password = "123456789Sauce"; // correct password for gmail id
	   final String toEmail = Email; // can be any email id
	   String subject = "Invoice to " + Subject;
	   String body = Body;
	   System.out.println("SSLEmail Start");
	   Properties props = new Properties();
	   props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
	   props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
	   props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
	   props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
	   props.put("mail.smtp.port", "465"); //SMTP Port
	   Authenticator auth = new Authenticator() {
		   //override the getPasswordAuthentication method
		   protected PasswordAuthentication getPasswordAuthentication() {
			   return new PasswordAuthentication(fromEmail, password);
		   }
	   };
	   Session session = Session.getDefaultInstance(props, auth);
	   System.out.println("Session created");
	   
	   EmailUtil.sendEmail(session, toEmail,subject, body, invoiceNo);
       
   }

}