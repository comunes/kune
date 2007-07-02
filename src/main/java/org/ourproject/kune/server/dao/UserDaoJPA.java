package org.ourproject.kune.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.ourproject.kune.server.model.KUser;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class UserDaoJPA implements UserDao {
    Provider<EntityManager> emProvider;

    public KUser get(Long id) {
	return getEntityManager().find(KUser.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<KUser> getAll() {
	Query query = getEntityManager().createQuery("select u from KUser u");
	return query.getResultList();
    }

    public KUser persist(KUser user) {
	getEntityManager().persist(user);
	return user;
    }

    public KUser update(KUser user) {
	return getEntityManager().merge(user);
    }
    
    private EntityManager getEntityManager() {
	return emProvider.get();
    }

    @Inject public void setEntityManagerProvider(Provider<EntityManager> emProvider) {
        this.emProvider = emProvider;
    }

    
}
