package dao.impl;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CRUDImpl {

    @PersistenceContext(unitName = "myData")
    EntityManager em;

    @PostConstruct
    public void init() {
        System.out.println("init " + this + " with " + em);
    }

    public <T> T create(T entity) {
        em.persist(entity);
        return entity;
    }

    public <T> T update(T entity) {
        return em.merge(entity);
    }

    public <T> void delete(Object id) {
        em.remove(id);
    }

    public <T> T findById(int id, Class<T> aClass) {
        return em.find(aClass, id);
    }

    public <T> List<T> findAll(Class<T> aClass) {
        return em.createQuery("From " + aClass.getSimpleName(), aClass)
                .getResultList();
    }
}
