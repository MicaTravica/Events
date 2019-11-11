package com.app.events.service;

import javax.mail.MessagingException;

public interface MailService {

	void newUser(String email, String token) throws MessagingException;

	void changeEmail(String email) throws MessagingException;

}
