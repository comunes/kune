package org.ourproject.kune.platf.server.finders;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.ourproject.kune.platf.server.PersistenceTest;
import org.ourproject.kune.platf.server.TestDomainHelper;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Rate;
import org.ourproject.kune.platf.server.domain.User;

import com.google.inject.Inject;

public class ContentDescriptorFinderTest extends PersistenceTest {
    @Inject
    Content finder;

    @Test
    public void testAverage() {
	EntityManager manager = openTransaction();

	User user1 = TestDomainHelper.createUser(1);
	User user2 = TestDomainHelper.createUser(2);
	manager.persist(user1);
	manager.persist(user2);

	Content cd = new Content();
	manager.persist(cd);

	manager.persist(new Rate(user1, cd, 1.3));
	manager.persist(new Rate(user2, cd, 5.3));
	Double rate = finder.calculateRate(cd);
	Double average = (1.3 + 5.3) / 2;
	assertEquals(average, rate);
	closeTransaction();
    }
}
