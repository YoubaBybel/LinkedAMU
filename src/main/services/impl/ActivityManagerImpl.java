package main.services.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import main.dao.CRUD;
import main.entities.Activity;
import main.entities.Activity.Nature;
import main.entities.User;
import main.services.ActivityManager;

@Stateless
public class ActivityManagerImpl implements ActivityManager {

    @EJB
    private CRUD dao;

    @PersistenceContext(unitName = "myData")
    private EntityManager em;

    @Override
    public void createActivity() {
	dao.create(new Activity());
    }

    @Override
    public void createActivity(Activity activity) {
	dao.create(activity);
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
	return em.createQuery("SELECT a FROM Activity a WHERE year = :year", Activity.class)
		.setParameter("year", year)
		.getResultList();
    }

    @Override
    public List<Activity> findByNature(Nature nature) {
	return em.createQuery("SELECT a FROM Activity a WHERE lower(nature) LIKE lower(:nature)", Activity.class)
		.setParameter("nature", "%"+nature+"%")
		.getResultList();
    }

    @Override
    public List<Activity> findByTitle(String title) {
	return em.createQuery("SELECT a FROM Activity a WHERE lower(title) LIKE lower(:title)", Activity.class)
		.setParameter("title", "%"+title+"%")
		.getResultList();
    }

    @Override
    public List<Activity> findUserActivities(User user) {
	return em.createQuery("SELECT a FROM Activity a WHERE user_id = :user_id", Activity.class)
		.setParameter("user_id", user.getId())
		.getResultList();
    }

}
