package org.ourproject.kune.platf.server.finders;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;

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

import com.google.gwt.user.client.rpc.SerializableException;
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

    @Before
    public void insertData() throws SerializableException {
        english = new I18nLanguage(new Long(1819), "English", "English", "en");
        languageManager.persist(english);
        gb = new I18nCountry(new Long(75), "GB", "GBP", ".", "£%n", "", ".", "United Kingdom", "western", ",");
        countryManager.persist(gb);
    }

    @Test
    public void testContentRateAverage() {
        EntityManager manager = openTransaction();

        User user1 = TestDomainHelper.createUser(1);
        User user2 = TestDomainHelper.createUser(2);

        user1.setLanguage(english);
        user2.setLanguage(english);

        user1.setCountry(gb);
        user2.setCountry(gb);

        manager.persist(user1);
        manager.persist(user2);

        Content cd = new Content();
        manager.persist(cd);

        manager.persist(new Rate(user1, cd, 1.3));
        manager.persist(new Rate(user2, cd, 3.3));
        // same user and content other rate
        Rate rateFinded = rateFinder.find(user2, cd);
        rateFinded.setValue(4.3);
        manager.persist(rateFinded);

        closeTransaction();
        Double rate = rateFinder.calculateRate(cd);
        Long rateByUsers = rateFinder.calculateRateNumberOfUsers(cd);
        Double average = (1.3 + 4.3) / 2;
        assertEquals(average, rate);
        assertEquals(2, rateByUsers);
        Rate newRate1 = rateFinder.find(user1, cd);
        Rate newRate2 = rateFinder.find(user2, cd);
        assertEquals(1.3, newRate1.getValue());
        assertEquals(4.3, newRate2.getValue());
    }

    @Test
    public void testContentNotRated() {
        EntityManager manager = openTransaction();

        Content cd = new Content();
        manager.persist(cd);

        closeTransaction();
        Double rate = rateFinder.calculateRate(cd);
        Long rateByUsers = rateFinder.calculateRateNumberOfUsers(cd);
        assertEquals(0, rateByUsers);

        // FIXME: Why null? in other tests this return zero
        assertEquals(0, rate);
    }
}
