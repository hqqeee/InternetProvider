package com.epam.util;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public enum EmailUtil {

	INSTANCE;

	private final Properties emailProp = new Properties();

	private EmailUtil() {
		try {
			emailProp.load(getClass().getClassLoader().getResourceAsStream("email.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean sendMail(String toEmail, String subject, String text) {
		Authenticator auth = new Authenticator() {
			// override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication((String) emailProp.get("mail.login"),
						(String) emailProp.get("mail.app.pass"));
			}
		};
		Session session = Session.getInstance(emailProp, auth);

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress((String) emailProp.get("mail.login")));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			message.setSubject(subject);
			message.setText(text);
			Transport.send(message);
		} catch (MessagingException mex) {
			mex.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean sendRegistrationEmail(String toEmail, String login, String password) {
		return sendMail(toEmail, "Welcome to Maven Telecom",
				"An account in the Maven telecom system has been created for you. Your credentials for logging in: <br>Login: "
						+ login + "<br>Password: " + password
						+ "<br>To protect your account, please change your password as soon as possible.");
	}

	public boolean sendNewPassword(String email, String login, String password) {
		return sendMail(email, "Password Reset", "Your password has been changed.<br>Login: " + login + "<br>Password: "
				+ password + "<br>To protect your account, please change your password as soon as possible.");
	}

}
