package test.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.entities.Activity;
import main.entities.User;
import main.services.ActivityManager;
import main.services.UserManager;

public class TestActivityManager {

	@EJB
	ActivityManager am;

	@EJB
	UserManager um;

	private Activity master_2;
	private Activity master_1;
	private Activity stage;
	private Activity licence;

	
	private User scottLang;
	private User nickFury;

	@Before
	public void init() throws Exception {
		EJBContainer.createEJBContainer().getContext().bind("inject", this);

		am.findAll().forEach(activity -> am.removeActivity(activity.getId()));
		um.findAll().forEach(user -> um.removeUser(user.getId()));

		master_2 = new Activity(2019, "FORMATION", "Master Info - spé ILD");
		master_1 = new Activity(2018, "FORMATION", "Master Info");
		stage = new Activity(2019, "STAGE", "Sogeti");
		licence = new Activity(2017, "FORMATION", "Licence Info");

		scottLang = new User("LANG", "Scott", "scott.lang@antman.com", "iamnotathief");
		nickFury = new User("FURY", "Nick", "nick_fury@shield.com", "imoneeyedman");
	}

	
	@After
	public void end() throws Exception {
		am.findAll().forEach(activity -> am.removeActivity(activity.getId()));
		um.findAll().forEach(user -> am.removeActivity(user.getId()));
		EJBContainer.createEJBContainer().close();
	}

	@Test
	public void testInject() {
		assertNotNull(am);
	}
	
	@Test
	public void testCreate() {
		am.createActivity(master_2);
		assertNotNull(am.findAll());
	}

	@Test
	public void testUpdate() {
		am.createActivity(master_1);
		master_1.setYear(2019);
		am.updateActivity(master_1);
		assertEquals(2019, master_1.getYear());
	}

	@Test
	public void testRemove() {
		am.createActivity(stage);
		am.removeActivity(stage.getId());
		assertEquals(0, am.findAll().size());
	}

	@Test
	public void testFindById() {
		am.createActivity(licence);
		assertEquals(licence.getTitle(), am.findById(licence.getId()).getTitle());
	}

	@Test
	public void testFindAll() {
		am.createActivity(master_2);
		am.createActivity(master_1);
		am.createActivity(stage);
		am.createActivity(licence);
		assertEquals(4, am.findAll().size());
	}

	@Test
	public void testFindByTitle() {
		am.createActivity(master_2);
		am.createActivity(master_1);
		am.createActivity(stage);
		am.createActivity(licence);
		am.findByTitle("master").forEach(activity -> assertTrue(activity.getTitle().toLowerCase().contains("master")));
		assertEquals(2, am.findByTitle("master").size());
	}

	@Test
	public void testFindUserActivities() {
		um.createUser(scottLang);
		um.createUser(nickFury);

		am.createActivity(master_2);
		am.createActivity(master_1);
		am.createActivity(stage);
		am.createActivity(licence);

		List<Activity> nickCv = nickFury.getCv();
		nickCv.add(licence);
		nickCv.add(master_1);
		nickCv.add(master_2);
		nickFury.setCv(nickCv);
		um.updateUser(nickFury);

		List<Activity> scottCv = scottLang.getCv();
		scottCv.add(stage);
		scottLang.setCv(scottCv);
		um.updateUser(scottLang);

		stage.setDescription("Une description du stage");
		am.updateActivity(stage);
		assertEquals(3, am.findUserActivities(nickFury).size());
	}

}
