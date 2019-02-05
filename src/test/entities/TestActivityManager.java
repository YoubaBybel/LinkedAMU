package test.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.entities.Activity;
import main.services.ActivityManager;

public class TestActivityManager {

    @EJB
    ActivityManager am;

    private Activity master_2;
    private Activity master_1;
    private Activity stage;
    private Activity licence;

    @Before
    public void init() throws Exception {
	EJBContainer.createEJBContainer().getContext().bind("inject", this);
	master_2 = new Activity(2019, "FORMATION", "Master Info - spé ILD");
	master_1 = new Activity(2018, "FORMATION", "Master Info");
	stage = new Activity(2019, "STAGE", "Sogeti");
	licence = new Activity(2017, "FORMATION", "Licence Info");
    }

    @After
    public void end() throws Exception {
	am.findAll().forEach(activity -> am.removeActivity(activity.getId()));
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
	System.err.println(activities_2019.toString());
	System.err.println(am.findByYear(2019));
	assertEquals(activities_2019.size(), am.findByYear(2019).size());
    }

    @Test
    public void testFindByNature() {

    }

    @Test
    public void testFindByTitle() {

    }

    @Test
    public void testFindUserActivities() {

    }
}
