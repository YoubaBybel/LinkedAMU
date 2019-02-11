package main.dao.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import main.dao.CRUD;

@Stateless
public class CRUDImpl implements CRUD {

    @PersistenceContext(unitName = "myData")
    private EntityManager em;

    @PostConstruct
    public void init() {
	System.out.println("init " + this + " with " + em);
    }

    @Override
    public <T> void create(T entity) {
	em.persist(entity);
    }

    @Override
    public <T> T update(T entity) {
	return em.merge(entity);
    }

    @Override
    public <T> void delete(Integer id, Class<T> aClass) {
	T entity = this.em.find(aClass, id);
	if (entity != null) {
	    em.remove(entity);
	}
    }

    @Override
    public <T> T findById(Integer id, Class<T> aClass) {
	return em.find(aClass, id);
    }

    @Override
    public <T> List<T> findAll(Class<T> aClass) {
	return em.createQuery("From " + aClass.getSimpleName(), aClass).getResultList();
    }

}
