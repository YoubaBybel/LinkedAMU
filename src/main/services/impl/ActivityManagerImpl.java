package main.services.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import main.dao.CRUD;
import main.entities.Activity;
import main.entities.User;
import main.services.ActivityManager;

@Stateless
public class ActivityManagerImpl implements ActivityManager {

    @EJB
    CRUD dao;

    @PersistenceContext(unitName = "myData")
    EntityManager em;

    @Override
    public Activity createActivity() {
	return dao.create(new Activity());
    }

    @Override
    public Activity createActivity(Activity activity) {
	return dao.create(activity);
    }

    @Override
    public Activity updateActivity(Activity activity) {
	return dao.update(activity);
    }

    @Override
    public void removeActivity(int id) {
	dao.delete(id, Activity.class);
    }

    @Override
    public Activity findById(int id) {
	return dao.findById(id, Activity.class);
    }

    @Override
    public List<Activity> findAll() {
	return dao.findAll(Activity.class);
    }

    @Override
    public List<Activity> findByYear(int year) {
	return em.createQuery("SELECT a FROM Activity a WHERE year = :year", Activity.class).setParameter("year", year)
		.getResultList();
    }

    @Override
    public List<Activity> findByNature(String nature) {
	return em.createQuery("SELECT a FROM Activity a WHERE nature = :nature", Activity.class)
		.setParameter("nature", nature).getResultList();
    }

    @Override
    public List<Activity> findByTitle(String title) {
	return em.createQuery("SELECT a FROM Activity a WHERE title LIKE :title", Activity.class)
		.setParameter("title", title).getResultList();
    }

    @Override
    public List<Activity> findUserActivities(User user) {
	return em.createQuery("SELECT a FROM Activity a WHERE cv_id = :cv_id", Activity.class)
		.setParameter("cv_id", user.getId())
		.getResultList();
    }
}
