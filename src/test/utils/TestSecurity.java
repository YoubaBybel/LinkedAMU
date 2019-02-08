package test.utils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import main.utils.Security;

public class TestSecurity {

    @Test
    public void testPasswordHash() throws NoSuchAlgorithmException, InvalidKeySpecException {
	String password = "Mot_de_passe";
	String hashedPassword = new Security().hashPassword(password);
	System.out.println(hashedPassword.toString());
	System.out.println(password.hashCode());

	String olPasswordString = "Mot_de_passe";
	String confirmHashedPAssword = new Security().hashPassword(password);
	System.out.println(confirmHashedPAssword.toString());
	System.out.println(olPasswordString.hashCode());

	List<String> list = new ArrayList<>();
	list.add(new Security().hashPassword(password));
	list.add(new Security().hashPassword(password));
	list.add(new Security().hashPassword(password));
	list.add(new Security().hashPassword(password));
	list.add(new Security().hashPassword(password));
	list.add(new Security().hashPassword(password));
	list.forEach(psw -> System.err.println(psw.getBytes()));
    }

    @Test
    public void name() {
	System.out.println(Integer.parseInt("2018"));
	//System.out.println(Integer.parseInt("bonjour"));
    }
}
