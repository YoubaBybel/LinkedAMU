package main.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Security {

    public Security() {
    }

    public String hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
	SecureRandom random = new SecureRandom();
	byte[] salt = new byte[16];
	random.nextBytes(salt);
	KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
	SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
	byte[] hash = factory.generateSecret(spec).getEncoded();
	return hash.toString();
    }

    public boolean isSame(String password1, String password2) throws NoSuchAlgorithmException, InvalidKeySpecException {
	String pass1 = hashPassword(password1);
	String pass2 = hashPassword(password2);
	return pass1.equals(pass2);
    }


}
