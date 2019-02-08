package test.utils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.junit.Assert;
import org.junit.Test;

import main.utils.Security;

public class TestSecurity {

    @Test
    public void testPasswordHash() throws NoSuchAlgorithmException, InvalidKeySpecException {
	String password = "Mot_de_passe";
	String hashedPassword = Security.hashPassword(password);

	String confirmPassword = "Mot_de_passe";
	String hashedConfirmPassword = Security.hashPassword(confirmPassword);

	System.err.println("password: " + password);
	System.err.println("password.getBytes(): " + password.getBytes());
	System.err.println();
	System.err.println("hashedPassword: " + hashedPassword);
	System.err.println("hashedPassword.getBytes(): " + hashedPassword.getBytes());
	System.err.println();
	System.err.println("confirmPassword: " + confirmPassword);
	System.err.println("confirmPassword.getBytes(): " + confirmPassword.getBytes());
	System.err.println();
	System.err.println("hashedConfirmPassword: " + hashedConfirmPassword);
	System.err.println("hashedConfirmPassword.getBytes(): " + hashedConfirmPassword.getBytes());

	Assert.assertTrue(Security.isSame(hashedConfirmPassword, hashedPassword));
	Assert.assertTrue(Security.isSame(hashedConfirmPassword.getBytes(), hashedPassword.getBytes()));
    }

}
