package model;
//import java.security.Security;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class SendMail {
	private String host = "smtp.live.com";
	private String username = "pmscrumtool@hotmail.com";
	private String password = "pm123456";
	private String from = "pmscrumtool@hotmail.com";
	private String msg;
	private String to;
	
	public SendMail(String to, String message){
		this.to = to;
		this.msg = message;
	}
	
	public String send(){
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.starttls.enable",true);
		props.put("mail.user", username);
		props.put("mail.password",password);
		props.put("mail.smtp.auth", "true");
		Session mailSession = Session.getInstance(props, null);
		try{
			Transport transport = mailSession.getTransport("smtp");
			MimeMessage message = new MimeMessage(mailSession);
			message.setSubject("Testing javamail plain");
			message.setContent(this.msg, "text/html;charset=UTF-8");
			message.setFrom(new InternetAddress(this.from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			//System.out.println(username);
		//	System.out.println(password);
			transport.connect(username,password);
			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			transport.close();
			}catch (Exception e){
			    	 e.printStackTrace();
			     }
		return "Done sending the invitation to " + to;
	}
	}
			

