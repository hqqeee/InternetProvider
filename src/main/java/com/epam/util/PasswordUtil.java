package com.epam.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordUtil {
	public static String hashPassword(String password) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e);
		}
		md.reset();
		md.update(password.getBytes());
		byte[] mdArray = md.digest();
		// Convert the array of bytes (which is 8 bits) to a string
		// of character (which is 16 bits in Java)
		StringBuilder sb = new StringBuilder(mdArray.length * 2);
		for (byte b : mdArray) {
			int v = b & 0xff;
			if (v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}
		return sb.toString();
	}

	public static String getRandomString(int length) {
		String chrs = "0123456789abcdefghijklmnopqrstuvwxyz-_ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    SecureRandom secureRandom = null;
		try {
			secureRandom = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return secureRandom.ints(length, 0, chrs.length()).mapToObj(i -> chrs.charAt(i))
	      .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
	}

}
