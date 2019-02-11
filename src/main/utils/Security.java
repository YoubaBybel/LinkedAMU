package main.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public interface Security {

	static String hashPassword(String password) throws NoSuchAlgorithmException {
		MessageDigest messageDigest;
		StringBuffer stringBuffer = new StringBuffer();

		messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update(password.getBytes());
		byte[] hashedPassword = messageDigest.digest();
		for (byte bytes : hashedPassword) {
			stringBuffer.append(String.format("%02x", bytes & 0xff));
		}
		return stringBuffer.toString();
	}

	static boolean isSame(String password1, String password2) throws NoSuchAlgorithmException {
		return password1.equals(password2);
	}

	static boolean isSame(byte[] password1, byte[] password2) throws NoSuchAlgorithmException {
		return MessageDigest.isEqual(password1, password2);
	}

}
