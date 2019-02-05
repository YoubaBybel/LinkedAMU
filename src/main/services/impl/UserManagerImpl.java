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
	return this.em.createQuery("SELECT u FROM User u WHERE name LIKE :name", User.class).setParameter("name", name)
		.getResultList();
    }

    @Override
    public List<User> findByFirstName(String firstName) {
	return this.em.createQuery("SELECT u FROM User u WHERE first_name LIKE :first_name", User.class)
		.setParameter("first_name", firstName).getResultList();
    }

}
