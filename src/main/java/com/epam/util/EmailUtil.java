package com.epam.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.services.dto.TariffDTO;

public enum EmailUtil {

	INSTANCE;

	private static final Logger LOGGER = LogManager.getLogger(EmailUtil.class);
	private Queue<Email> emails = new LinkedList<>();

	private final ExecutorService pool = Executors.newSingleThreadExecutor();

	private final Authenticator auth = new Authenticator() {
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication((String) emailProp.get("mail.login"),
					(String) emailProp.get("mail.app.pass"));
		}
	};

	private final Properties emailProp = new Properties();

	private EmailUtil() {
		try {
			emailProp.load(getClass().getClassLoader().getResourceAsStream("email.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendMails() {
		pool.execute(() -> {
			Session session = Session.getInstance(emailProp, auth);
			try {
				for (;;) {
					Email email = emails.poll();
					if (email == null) {
						break;
					}
					MimeMessage message = new MimeMessage(session);
					message.setFrom(new InternetAddress((String) emailProp.get("mail.login")));
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getReciver()));
					message.setSubject(email.getSubject());
					message.setText(email.getText());
					Transport.send(message);
					LOGGER.info("Email to " + email.getReciver() + " was sent.");
				}
			} catch (MessagingException mex) {
				mex.printStackTrace();
			}

		});
	}

	public boolean addRegistrationEmailToQueue(String toEmail, String login, String password) {
		return emails.offer(new Email(toEmail, "Welcome to Maven Telecom",
				"An account in the Maven telecom system has been created for you. Your credentials for logging in: <br>Login: "
						+ login + "<br>Password: " + password
						+ "<br>To protect your account, please change your password as soon as possible."));
	}

	public boolean addSendNewPasswordEmailToQueue(String email, String login, String password) {
		return emails.offer(new Email(email, "Password Reset",
				"Your password has been changed.<br>Login: " + login + "<br>Password: " + password
						+ "<br>To protect your account, please change your password as soon as possible."));
	}

	public boolean addReceipt(String email, List<TariffDTO> tariffs) {
		StringBuilder sb = new StringBuilder();
		sb.append("Dear client, <br> Here's your were charge for using out service.");
		BigDecimal sum = BigDecimal.ZERO;
		int count = 1;
		sb.append("<br> <table border=\"1\">\n" + " <thead> <tr>\n" + "    <th>No</th>\n" + "    <th>Tariff</th>\n"
				+ "    <th>Rate</th>\n" + "  </tr> </thead><tbody>");
		for (TariffDTO tariff : tariffs) {
			sb.append("<tr>\n" + "    <td>" + count + "</td>\n" + "    <td>" + tariff.getName() + "</td>\n" + "    <td>"
					+ tariff.getRate() + "</td>\n" + "  </tr>");
			sum = sum.add(tariff.getRate());
		}
		sb.append("</tbody><tfoot><tr><td></td><td>Sum:</td><td>" + sum.toString()
				+ "</td>\n</tr>\n</tfoot>");

		return emails.offer(new Email(email, "Charging receipt", sb.toString()));
	}

	public boolean addNotEnoghtMoneyNotification(String email, BigDecimal notEnoghtSum) {
		return emails.offer(new Email(email, "Replenish your account.",
				"We cannot continue to provide you with our services. "
						+ "You don't have enough money in your account, please top up your account with a minimum of $"
						+ notEnoghtSum + " to continue using our services."));

	}

	private class Email {
		private String reciver;
		private String subject;
		private String text;

		Email(String receiver, String subject, String text) {
			this.reciver = receiver;
			this.subject = subject;
			this.text = text;
		}

		public String getReciver() {
			return reciver;
		}

		public String getSubject() {
			return subject;
		}

		public String getText() {
			return text;
		}
	}
}
