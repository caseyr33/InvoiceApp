package model;



import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailUtil {

	/**
	 * Utility method to send simple HTML email
	 * @param session
	 * @param toEmail
	 * @param subject
	 * @param body
	 */
	public static void sendEmail(Session session, String toEmail, String subject, String body, String invoiceNo){
		
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
	      BodyPart messageBodyPart = new MimeBodyPart();
	      messageBodyPart.setText("Please review the following receipt, reach out to our team if you dont recognise the details from this transaction. Thank you." + "\r\n");
	      Multipart multipart = new MimeMultipart(); // Set text message part
	      multipart.addBodyPart(messageBodyPart);
	      messageBodyPart = new MimeBodyPart();
	      String filename = "Receipt.pdf";
	      DataSource source = new FileDataSource(filename);
	      messageBodyPart.setDataHandler(new DataHandler(source));
	      messageBodyPart.setFileName(filename);
	      multipart.addBodyPart(messageBodyPart);

	         // Send the complete message parts
	         msg.setContent(multipart);
    	  Transport.send(msg);  

	      System.out.println("EMail Sent Successfully!!");
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	}


}