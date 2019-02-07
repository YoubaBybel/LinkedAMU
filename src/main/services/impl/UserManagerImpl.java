package main.services.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import main.dao.CRUD;
import main.entities.User;
import main.services.UserManager;

@Stateful
public class UserManagerImpl implements UserManager {

    @EJB
    private CRUD dao;

    @PersistenceContext(unitName = "myData")
    private EntityManager em;

    @PostConstruct
    public void init() {
	System.out.println("init " + this + " with " + em);
    }

    @Override
    public User createUser() {
	return dao.create(new User());
    }

    @Override
    public User createUser(User user) {
	return dao.create(user);
    }

    @Override
    public User updateUser(User user) {
	user.getCv().forEach(activity -> dao.update(activity));
	return dao.update(user);
    }

    @Override
    public void removeUser(int id) {
	dao.delete(id, User.class);
    }

    @Override
    public User findById(int id) {
	return dao.findById(id, User.class);
    }

    @Override
    public List<User> findAll() {
	return dao.findAll(User.class);
    }

    @Override
    public List<User> findByName(String name) {
	return em.createQuery("SELECT u FROM User u WHERE lower(name) LIKE lower(:name)", User.class)
		.setParameter("name", "%"+name+"%")
		.getResultList();
    }

    @Override
    public List<User> findByFirstName(String firstName) {
	return em.createQuery("SELECT u FROM User u WHERE lower(first_name) LIKE lower(:first_name)", User.class)
		.setParameter("first_name", "%"+firstName+"%")
		.getResultList();
    }

	@Override
	public User findByLogin(String email, String password) {
		return this.em
				.createQuery("SELECT u FROM User u WHERE email LIKE :email AND password LIKE :password", User.class)
				.setParameter("email", email).setParameter("password", password).getSingleResult();
	}

    /*
    @Override
    public User save(User user) {
	if (user.getId() == null) {
	    em.persist(user);
	} else {
	    user = em.merge(user);
	}
	return user;
    }
    */
}
