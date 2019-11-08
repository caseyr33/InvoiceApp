package model;



import java.io.UnsupportedEncodingException;
import java.util.Date;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class EmailUtil {

	/**
	 * Utility method to send simple HTML email
	 * @param session
	 * @param toEmail
	 * @param subject
	 * @param body
	 * @throws MessagingException 
	 * @throws UnsupportedEncodingException
	 * @throws com.sun.mail.smtp.SMTPAddressFailedException
	 */
	public static void sendEmail(Session session, String toEmail, String subject, String body, String invoiceNo) throws MessagingException, javax.mail.SendFailedException , com.sun.mail.smtp.SMTPAddressFailedException,  UnsupportedEncodingException{
		
		try
	    {
	      MimeMessage msg = new MimeMessage(session);
	      //set message headers
	      msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
	      msg.addHeader("format", "flowed");
	      msg.addHeader("Content-Transfer-Encoding", "8bit");

	      msg.setFrom(new InternetAddress("HankSauceApp@gmail.com", "Invoice no: "+ invoiceNo +"; Do not reply to this email"));

	      msg.setReplyTo(InternetAddress.parse("HankSauceApp@gmail.com", false));

	      msg.setSubject(subject, "UTF-8");

	      msg.setText(body, "UTF-8");

	      msg.setSentDate(new Date());

	      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
	      System.out.println("Message is ready");
    	  Transport.send(msg);  

	      System.out.println("EMail Sent Successfully!!");
	    }
	    
		 catch(Exception e){
         return;
         }
		


	}
}