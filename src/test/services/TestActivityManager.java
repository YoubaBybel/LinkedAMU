package test.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.entities.Activity;
import main.entities.Activity.Nature;
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
	Activity activity = am.createActivity(master_2);
	assertNotNull(activity);
    }

    @Test
    public void testUpdate() {
	Activity activity = am.createActivity(master_1);
	activity.setYear(2019);
	am.updateActivity(activity);
	assertEquals(2019, activity.getYear());
    }

    @Test
    public void testRemove() {
	Activity activity = am.createActivity(stage);
	am.removeActivity(activity.getId());
	assertEquals(0, am.findAll().size());
    }

    @Test
    public void testFindById() {
	Activity activity = am.createActivity(licence);
	assertEquals(activity.getTitle(), am.findById(activity.getId()).getTitle());
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
    public void testFindByYear() {
	Activity activity1 = am.createActivity(master_2);
	Activity activity2 = am.createActivity(stage);
	List<Activity> activities_2019 = new ArrayList<>();
	activities_2019.add(activity1);
	activities_2019.add(activity2);
	assertEquals(activities_2019.size(), am.findByYear(2019).size());
    }

    @Test
    public void testFindByNature() {
	am.createActivity(master_2);
	am.createActivity(master_1);
	am.createActivity(stage);
	am.createActivity(licence);
	assertEquals(3, am.findByNature(Nature.FORMATION).size());
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
	User antman = um.createUser(scottLang);
	User nick = um.createUser(nickFury);

	Activity m2 = am.createActivity(master_2);
	Activity m1 = am.createActivity(master_1);
	Activity st = am.createActivity(stage);
	Activity li = am.createActivity(licence);

	List<Activity> nickCv = nick.getCv();
	nickCv.add(li);
	nickCv.add(m1);
	nickCv.add(m2);
	nickFury.setCv(nickCv);
	um.updateUser(nick);

	List<Activity> scottCv = antman.getCv();
	scottCv.add(st);
	antman.setCv(scottCv);
	um.updateUser(antman);

	st.setDescription("Une description du stage");
	am.updateActivity(st);
	assertEquals(3, am.findUserActivities(nick).size());
    }

}
