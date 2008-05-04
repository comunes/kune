package org.ourproject.kune.platf.server.finders;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.server.PersistenceTest;
import org.ourproject.kune.platf.server.TestDomainHelper;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.I18nCountry;
import org.ourproject.kune.platf.server.domain.I18nLanguage;
import org.ourproject.kune.platf.server.domain.Rate;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.I18nCountryManager;
import org.ourproject.kune.platf.server.manager.I18nLanguageManager;

import com.google.inject.Inject;

public class RateFinderTest extends PersistenceTest {

    @Inject
    Rate rateFinder;
    @Inject
    I18nLanguageManager languageManager;
    @Inject
    I18nCountryManager countryManager;
    private I18nLanguage english;
    private I18nCountry gb;

    @After
    public void close() {
	if (getTransaction().isActive()) {
	    getTransaction().rollback();
	}
    }

    @Before
    public void insertData() throws Exception {
	english = new I18nLanguage(new Long(1819), "English", "English", "en");
	languageManager.persist(english);
	gb = new I18nCountry(new Long(75), "GB", "GBP", ".", "£%n", "", ".", "United Kingdom", "western", ",");
	countryManager.persist(gb);
    }

    @Test
    public void testContentNotRated() {
	final EntityManager manager = openTransaction();

	final Content cd = new Content();
	cd.setLanguage(english);
	cd.setPublishedOn(new Date());
	manager.persist(cd);

	closeTransaction();
	final Double rate = rateFinder.calculateRate(cd);
	final Long rateByUsers = rateFinder.calculateRateNumberOfUsers(cd);
	assertEquals(0, rateByUsers);

	// FIXME: Why null? in other tests this return zero
	assertEquals(null, rate);
    }

    @Test
    public void testContentRateAverage() {
	final EntityManager manager = openTransaction();

	final User user1 = TestDomainHelper.createUser(1);
	final User user2 = TestDomainHelper.createUser(2);

	user1.setLanguage(english);
	user2.setLanguage(english);

	user1.setCountry(gb);
	user2.setCountry(gb);

	manager.persist(user1);
	manager.persist(user2);

	final Content cd = new Content();
	cd.setLanguage(english);
	cd.setPublishedOn(new Date());
	manager.persist(cd);

	manager.persist(new Rate(user1, cd, 1.3));
	manager.persist(new Rate(user2, cd, 3.3));
	// same user and content other rate
	final Rate rateFinded = rateFinder.find(user2, cd);
	rateFinded.setValue(4.3);
	manager.persist(rateFinded);

	closeTransaction();
	final Double rate = rateFinder.calculateRate(cd);
	final Long rateByUsers = rateFinder.calculateRateNumberOfUsers(cd);
	final Double average = (1.3 + 4.3) / 2;
	assertEquals(average, rate);
	assertEquals(2, rateByUsers);
	final Rate newRate1 = rateFinder.find(user1, cd);
	final Rate newRate2 = rateFinder.find(user2, cd);
	assertEquals(1.3, newRate1.getValue());
	assertEquals(4.3, newRate2.getValue());
    }

}
