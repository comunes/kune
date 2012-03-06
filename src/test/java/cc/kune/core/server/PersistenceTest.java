/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
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
package cc.kune.core.server;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.Before;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

public abstract class PersistenceTest {
  private final String persistenceUnit;
  private final String propetiesFileName;
  @Inject
  @DataSourceKune
  Provider<EntityManager> provider;

  public PersistenceTest() {
    // test: use memory
    // test_db: use mysql
    // Also configurable ein TestHelper
    this(TestConstants.PERSISTENCE_MYSQL_UNIT, "kune.properties");
  }

  public PersistenceTest(final String persistenceUnit, final String propetiesFileName) {
    this.persistenceUnit = persistenceUnit;
    this.propetiesFileName = propetiesFileName;
  }

  public void closeTransaction() {
    getManager().getTransaction().commit();
  }

  protected EntityManager getManager() {
    return provider.get();
  }

  public EntityTransaction getTransaction() {
    return getManager().getTransaction();
  }

  public EntityManager openTransaction() {
    final EntityManager manager = getManager();
    final EntityTransaction transaction = manager.getTransaction();
    transaction.begin();
    return manager;
  }

  public void persist(final Object... entities) {
    for (final Object entity : entities) {
      getManager().persist(entity);
    }
  }

  @Transactional
  @Before
  public void prepare() {
    final Injector injector = TestHelper.create(new PlatformServerModule(), persistenceUnit,
        propetiesFileName);
    // final PersistService persistence =
    // injector.getInstance(PersistService.class);
    // To Debug insert breakpoint here
    // persistence.start();
    injector.injectMembers(this);
  }

  public void rollbackTransaction() {
    final EntityTransaction transaction = getManager().getTransaction();
    if (transaction.isActive()) {
      transaction.rollback();
    }
  }

}
