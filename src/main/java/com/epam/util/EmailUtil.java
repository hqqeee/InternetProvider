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

/**
 * 
 * Enum class that provides methods for sending emails in a single threaded
 * Executor. The class loads email properties from "email.properties" file and
 * uses javax.mail.Session with provided credentials to send emails using
 * javax.mail.Transport. The class also uses a java.util.Queue to store emails
 * that need to be sent. The sendMails() method is executed by the single
 * threaded Executor to send emails in the queue. The
 * addRegistrationEmailToQueue(String, String, String),
 * addSendNewPasswordEmailToQueue(String, String, String), and
 * addReceipt(String, List) methods are used to add emails to the queue. The
 * logging of sent emails is done using org.apache.logging.log4j.Logger.
 *
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public enum EmailUtil {
	/**
	 * The singleton instance of the EmailUtil class
	 */
	INSTANCE;

	/**
	 * Logger instance to log events
	 */
	private static final Logger LOGGER = LogManager.getLogger(EmailUtil.class);
	/**
	 * Queue to store emails to be sent
	 */
	private Queue<Email> emails = new LinkedList<>();
	/**
	 * ExecutorService instance to execute sending of emails in a separate thread
	 */
	private final ExecutorService pool = Executors.newSingleThreadExecutor();
	/**
	 * Authenticator instance to authenticate the email account being used
	 */
	private final Authenticator auth = new Authenticator() {
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication((String) emailProp.get("mail.login"),
					(String) emailProp.get("mail.app.pass"));
		}
	};
	/**
	 * Properties instance that holds the properties(credentials).
	 */
	private final Properties emailProp = new Properties();

	/**
	 * Private constructor that loads email properties
	 */
	private EmailUtil() {
		try {
			emailProp.load(getClass().getClassLoader().getResourceAsStream("email.properties"));
		} catch (IOException e) {
			System.err.println("Cannot load email properties.");
		}
	}

	/**
	 * Sends emails from the queue using a separate thread
	 */
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
					LOGGER.info("Email to {} was sent.", email.getReciver());
				}
			} catch (MessagingException mex) {
				mex.printStackTrace();
			}

		});
	}

	/**
	 * 
	 * Adds a registration email to the email queue. Use sendMails() to send it.
	 * 
	 * @param toEmail  email address of the recipient
	 * @param login    the login of the recipient
	 * @param password the password of the recipient
	 */
	public boolean addRegistrationEmailToQueue(String toEmail, String login, String password) {
		return emails.offer(new Email(toEmail, "Welcome to Maven Telecom",
				"An account in the Maven telecom system has been created for you. Your credentials for logging in: <br>Login: "
						+ login + "<br>Password: " + password
						+ "<br>To protect your account, please change your password as soon as possible."));
	}

	/**
	 * 
	 * Adds a send new password email to the email queue. Use sendMails() to send
	 * it.
	 * 
	 * @param toEmail  email address of the recipient
	 * @param login    the login of the recipient
	 * @param password the password of the recipient
	 */
	public boolean addSendNewPasswordEmailToQueue(String email, String login, String password) {
		return emails.offer(new Email(email, "Password Reset",
				"Your password has been changed.<br>Login: " + login + "<br>Password: " + password
						+ "<br>To protect your account, please change your password as soon as possible."));
	}

	/**
	 * 
	 * Adds a receipt email to the email queue. Use sendMails() to send it.
	 * 
	 * @param email   the email address of the recipient
	 * @param tariffs the list of tariffs
	 * @return true if the email was added to the queue, false otherwise.
	 */
	public boolean addReceipt(String email, List<TariffDTO> tariffs) {
		StringBuilder sb = new StringBuilder();
		sb.append("Dear client, <br>Here is your receipt for using our services.");
		BigDecimal sum = BigDecimal.ZERO;
		int count = 1;
		sb.append("<br> <table border=\"1\">\n" + " <thead> <tr>\n" + "    <th>No</th>\n" + "    <th>Tariff</th>\n"
				+ "    <th>Rate</th>\n" + "  </tr> </thead><tbody>");
		for (TariffDTO tariff : tariffs) {
			sb.append("<tr>\n" + "    <td>" + count + "</td>\n" + "    <td>" + tariff.getName() + "</td>\n" + "    <td>"
					+ tariff.getRate() + "</td>\n" + "  </tr>");
			sum = sum.add(tariff.getRate());
		}
		sb.append("</tbody><tfoot><tr><td></td><td>Sum:</td><td>" + sum.toString() + "</td>\n</tr>\n</tfoot>");

		return emails.offer(new Email(email, "Charging receipt", sb.toString()));
	}

	/**
	 * 
	 * Adds a new email notification about not enough money in the user's
	 * account.Use sendMails() to send it.
	 * 
	 * @param email        The email address of the recipient.
	 * @param notEnoghtSum The minimum amount of money required to continue using
	 *                     the services.
	 * @return True if the email was successfully added to the queue, false
	 *         otherwise.
	 */
	public boolean addNotEnoghtMoneyNotification(String email, BigDecimal notEnoghtSum) {
		return emails.offer(new Email(email, "Replenish your account.",
				"We cannot continue to provide you with our services. "
						+ "You don't have enough money in your account, please top up your account with a minimum of $"
						+ notEnoghtSum + " to continue using our services."));

	}

	/**
	 * Email instance. Contains reciver(email), subject and text of the email to
	 * send.
	 *
	 */
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
