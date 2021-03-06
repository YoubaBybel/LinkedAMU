package test.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.entities.User;
import main.services.UserManager;
import main.utils.Security;

public class TestUserManager {

	@EJB
	UserManager um;

	private User tonyStark;
	private User captain;
	private User bruceBanner;
	private User docteurStrange;
	private User natachaRomanov;

	@Before
	public void init() throws Exception {
		EJBContainer.createEJBContainer().getContext().bind("inject", this);
		um.findAll().forEach(user -> um.removeUser(user.getId()));

		tonyStark = new User("STARK", "Tony", "iron.man@starkindustry.com", "iamironman");
		captain = new User("CAPTAIN", "America", "sergent@us-navy.com", "oldsoldier");
		bruceBanner = new User("BANNER", "Bruce", "brucebanner@hulk.com", "Ouuuaaahhh");
		docteurStrange = new User("DOCTOR", "Strange", "docteur@strange.com", "pierredutemps");
		natachaRomanov = new User("ROMANOV", "Natacha", "nat_nov@blackwidow.com", "Grrrrrr");
	}

	@After
	public void exit() throws Exception {
		um.findAll().forEach(user -> um.removeUser(user.getId()));
		EJBContainer.createEJBContainer().close();
	}

	@Test
	public void testInject() {
		assertNotNull(um);
	}

	@Test
	public void testCreate() {
		um.createUser(tonyStark);
		assertNotNull(tonyStark);
	}

	@Test
	public void testUpdate() {
		um.createUser(bruceBanner);
		bruceBanner.setName("Willis");
		um.updateUser(bruceBanner);
		assertEquals("Willis", um.findById(bruceBanner.getId()).getName());
	}

	@Test
	public void testDelete() {
		um.findAll().forEach(user -> um.removeUser(user.getId()));
		 um.createUser(docteurStrange);
		 um.removeUser(docteurStrange.getId());
		 assertEquals(0, um.findAll().size());
	}

	@Test
	public void testFindById() {
		um.createUser(natachaRomanov);
		assertEquals(natachaRomanov.getEmail(), um.findById(natachaRomanov.getId()).getEmail());
	}

	@Test
	public void testFindAll() {
		um.findAll().forEach(user -> {
			um.removeUser(user.getId());
		});
		um.createUser(tonyStark);
		um.createUser(bruceBanner);
		um.createUser(docteurStrange);
		um.createUser(natachaRomanov);
		assertEquals(4, um.findAll().size());
	}

	@Test
	public void testFindByName() {
		um.createUser(tonyStark);
		um.createUser(captain);
		um.createUser(bruceBanner);
		um.createUser(docteurStrange);
		um.createUser(natachaRomanov);
		um.findByName("ta").forEach(user -> assertTrue(user.getName().toLowerCase().contains("ta")));
		assertEquals(2, um.findByName("ta").size());
	}

	@Test
	public void testFindByFirstName() {
		um.createUser(tonyStark);
		um.createUser(captain);
		um.createUser(bruceBanner);
		um.createUser(docteurStrange);
		um.createUser(natachaRomanov);
		um.findByFirstName("t").forEach(user -> assertTrue(user.getFirstName().toLowerCase().contains("t")));
		assertEquals(3, um.findByName("t").size());
	}

	@Test
	public void testFindByLogin() throws NoSuchAlgorithmException {
		um.createUser(bruceBanner);
		String bruceEmail = "brucebanner@hulk.com";
		String brucePassword = Security.hashPassword("Ouuuaaahhh");
		assertNotNull(um.findByLogin(bruceEmail, brucePassword));
	}

}
