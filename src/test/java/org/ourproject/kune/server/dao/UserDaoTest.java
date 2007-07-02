package org.ourproject.kune.server.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.ourproject.kune.server.model.KUser;

import com.google.inject.Provider;

import junit.framework.TestCase;

public class UserDaoTest extends TestCase {
    private EntityManager em;
    private EntityTransaction tx;
    private UserDaoJPA userDao;

    public void testPersist() {
	KUser user = new KUser("vicente", "vicente@dominio.org");
	assertNull("if not saved, not id", user.getId());
	
	userDao.persist(user);
	assertNotNull("after saved, an id is assigned", user.getId());
	
	KUser retrievedCopy = userDao.get(user.getId());
	assertNotNull("should be something in the db with this id", retrievedCopy);
	assertEquals ("should be equals", user, retrievedCopy);
    }

    @Override
    protected void setUp() throws Exception {
	super.setUp();
	openTransaction();
	createUserDao();
    }

    @Override
    protected void tearDown() throws Exception {
	super.tearDown();
	rollBackAndCloseTransaction();
    }

    private void openTransaction() {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
	this.em = emf.createEntityManager();
	this.tx = em.getTransaction();
	this.tx.begin();
    }

    private void createUserDao() {
	this.userDao = new UserDaoJPA();
	userDao.setEntityManagerProvider(new Provider<EntityManager>() {
	    public EntityManager get() {
		return em;
	    }
	});
    }

    /**
     * hacemos rollback para que los cambios no se guarden en la base de datos y
     * por lo tanto, cada vez que ejeutemos los test, la base de datos SIEMPRE
     * estará vacía (ideal para test)
     * de moento, en nuestro caso, esto daría igual, porque la base de datos
     * se crea en memoria... pero no siempre utilizaremos una base de datos en 
     * memoria para los tests...
     */
    private void rollBackAndCloseTransaction() {
	tx.rollback();
	em.close();
    }

}
