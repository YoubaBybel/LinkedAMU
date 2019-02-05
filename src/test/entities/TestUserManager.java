package test.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.entities.User;
import main.services.UserManager;

public class TestUserManager {

    @EJB
    UserManager um;

    @Before
    public void init() throws Exception {
	EJBContainer.createEJBContainer().getContext().bind("inject", this);
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
	User user = um.createUser(new User("Stark", "Tony", "iron.man@starkindustry.com", "iamironman"));
	assertNotNull(user);
    }

    public void testUpdate() {
	User user = um.createUser(new User("Willis", "Bruce", "brucebanner@hulk.com", "Ouuuaaahhh"));
	user.setName("Banner");
	um.updateUser(user);
	assertEquals("Banner", user.getName());
    }

    @Test
    public void testDelete() {
	User user = um.createUser(new User("Docteur", "Strange", "docteur@strange.com", "pierredutemps"));
	um.removeUser(user.getId());
	assertEquals(0, um.findAll().size());
    }

    @Test
    public void testFindById() {
	User user = um.createUser(new User("Romanov", "Natacha", "nat_nov@blackwidow.com", "Grrrrrr"));
	assertEquals(user.getEmail(), um.findById(user.getId()).getEmail());
    }

    @Test
    public void testFindAll() {
	um.createUser(new User("Stark", "Tony", "iron.man@starkindustry.com", "iamironman"));
	um.createUser(new User("Willis", "Bruce", "brucebanner@hulk.com", "Ouuuaaahhh"));
	um.createUser(new User("Docteur", "Strange", "docteur@strange.com", "pierredutemps"));
	um.createUser(new User("Romanov", "Natacha", "nat_nov@blackwidow.com", "Grrrrrr"));
	assertEquals(4, um.findAll().size());
    }

    @Test
    public void testFindByName() {
	User user = um.createUser(new User("Romanov", "Natacha", "nat_nov@blackwidow.com", "Grrrrrr"));
	assertEquals(user.getName(), um.findById(user.getId()).getName());
    }

    @Test
    public void testFindByFirstName() {
	User user = um.createUser(new User("Stark", "Tony", "iron.man@starkindustry.com", "iamironman"));
	assertEquals(user.getName(), um.findById(user.getId()).getName());
	System.err.println(user);
    }
}
