package org.ourproject.kune.platf.server.finders;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.server.PersistencePreLoadedDataTest;
import org.ourproject.kune.platf.server.TestDomainHelper;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Rate;
import org.ourproject.kune.platf.server.domain.User;

import com.google.inject.Inject;

public class RateFinderTest extends PersistencePreLoadedDataTest {

    @Inject
    Rate rateFinder;
    private EntityManager entityManager;

    @Before
    public void before() {
        entityManager = getManager();
    }

    @Test
    public void testContentNotRated() {
        final Content cd = new Content();
        cd.setLanguage(english);
        cd.setPublishedOn(new Date());
        entityManager.persist(cd);

        closeTransaction();
        final Double rate = rateFinder.calculateRate(cd);
        final Long rateByUsers = rateFinder.calculateRateNumberOfUsers(cd);
        assertEquals(0, (long) rateByUsers);

        // FIXME: Why null? in other tests this return zero
        assertEquals(null, rate);
    }

    @Test
    public void testContentRateAverage() {
        final User user1 = TestDomainHelper.createUser(1);
        final User user2 = TestDomainHelper.createUser(2);

        user1.setLanguage(english);
        user2.setLanguage(english);

        user1.setCountry(gb);
        user2.setCountry(gb);

        entityManager.persist(user1);
        entityManager.persist(user2);

        final Content cd = new Content();
        cd.setLanguage(english);
        cd.setPublishedOn(new Date());
        entityManager.persist(cd);

        entityManager.persist(new Rate(user1, cd, 1.3));
        entityManager.persist(new Rate(user2, cd, 3.3));
        // same user and content other rate
        final Rate rateFinded = rateFinder.find(user2, cd);
        rateFinded.setValue(4.3);
        entityManager.persist(rateFinded);

        closeTransaction();
        final Double rate = rateFinder.calculateRate(cd);
        final Long rateByUsers = rateFinder.calculateRateNumberOfUsers(cd);
        final Double average = (1.3 + 4.3) / 2;
        assertEquals(average, rate);
        assertEquals(new Long(2), rateByUsers);
        final Rate newRate1 = rateFinder.find(user1, cd);
        final Rate newRate2 = rateFinder.find(user2, cd);
        assertEquals(new Double(1.3), newRate1.getValue());
        assertEquals(new Double(4.3), newRate2.getValue());
    }

}
