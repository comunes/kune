/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cc.kune.core.server.finders;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cc.kune.core.server.PersistencePreLoadedDataTest;
import cc.kune.core.server.TestDomainHelper;
import cc.kune.domain.Content;
import cc.kune.domain.Rate;
import cc.kune.domain.User;
import cc.kune.domain.finders.RateFinder;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class RateFinderTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class RateFinderTest extends PersistencePreLoadedDataTest {

  /** The entity manager. */
  private EntityManager entityManager;

  /** The rate finder. */
  @Inject
  RateFinder rateFinder;

  /**
   * Before.
   */
  @Before
  public void before() {
    entityManager = getManager();
  }

  /**
   * Test content not rated.
   */
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

  /**
   * Test content rate average.
   */
  @Test
  @Ignore
  public void testContentRateAverage() {
    final User user1 = TestDomainHelper.createUser(1);
    final User user2 = TestDomainHelper.createUser(2);

    user1.setLanguage(english);
    user2.setLanguage(english);

    user1.setCountry(gb);
    user2.setCountry(gb);

    entityManager.persist(user1.getUserGroup());
    entityManager.persist(user2.getUserGroup());
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
    assertEquals(Long.valueOf(2), rateByUsers);
    final Rate newRate1 = rateFinder.find(user1, cd);
    final Rate newRate2 = rateFinder.find(user2, cd);
    assertEquals(new Double(1.3), newRate1.getValue());
    assertEquals(new Double(4.3), newRate2.getValue());
  }

}
