package com.app.events.serviceimpl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.app.events.service.MailService;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Async
	@Override
	public void newUser(String email, String token) throws MessagingException {
		MimeMessage mimeMessage= mailSender.createMimeMessage();
		MimeMessageHelper mmHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
		String message = "<html><head><meta charset=\"UTF-8\"></head>"
				+ "<body><h3>Events app - Wellcome!</h3><br>"
				+ "<div><p>You can verify your email "
				+ "<a target=\"_blank\" href = \"http://localhost:8080/api/user/verify/" + token 
				+ "\"><u>here</u></a>!.</p></div></body></html>";
        mmHelper.setText(message, true);
		mmHelper.setTo(email);
		mmHelper.setSubject("Events app - Wellcome!");
		mailSender.send(mimeMessage);
	}
	
	@Async
	@Override
	public void changeEmail(String email) throws MessagingException {
		MimeMessage mimeMessage= mailSender.createMimeMessage();
		MimeMessageHelper mmHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
		String message = "<html><head><meta charset=\"UTF-8\"></head>"
				+ "<body><h3>Events app - verify email!</h3><br>"
				+ "<div><p>You can verify your new email "
				+ "<a target=\"_blank\" href = \"http://localhost:8080/api/email/verify\"><u>here</u></a> "
				+ " !.</p></div></body></html>";
        mmHelper.setText(message, true);
		mmHelper.setTo(email);
		mmHelper.setSubject("Events app - verify email!");
		mailSender.send(mimeMessage);
	}
}
