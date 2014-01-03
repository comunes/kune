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
package cc.kune.core.server.auth;

import java.lang.reflect.AccessibleObject;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import cc.kune.core.client.errors.SessionExpiredException;
import cc.kune.core.client.errors.UserMustBeLoggedException;
import cc.kune.core.server.integration.IntegrationTest;
import cc.kune.core.server.integration.IntegrationTestHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class AuthenticatedMethodInterceptorTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class AuthenticatedMethodInterceptorTest extends IntegrationTest {

  /** The auth. */
  private AuthenticatedMethodInterceptor auth;

  /** The auth annotation. */
  private Authenticated authAnnotation;

  /** The invocation. */
  private MethodInvocation invocation;

  /**
   * Before.
   */
  @Before
  public void before() {
    auth = new AuthenticatedMethodInterceptor();
    new IntegrationTestHelper(true, auth, this);
    invocation = Mockito.mock(MethodInvocation.class);
    final AccessibleObject accessibleObject = Mockito.mock(AccessibleObject.class);
    Mockito.when(invocation.getMethod()).thenReturn(this.getClass().getMethods()[0]);
    Mockito.when(invocation.getStaticPart()).thenReturn(accessibleObject);
    authAnnotation = Mockito.mock(Authenticated.class);
    Mockito.when(accessibleObject.getAnnotation(Authenticated.class)).thenReturn(authAnnotation);
  }

  /**
   * Hash null and mandatory must do nothing.
   * 
   * @throws Throwable
   *           the throwable
   */
  @Test(expected = UserMustBeLoggedException.class)
  public void hashNullAndMandatoryMustDoNothing() throws Throwable {
    Mockito.when(authAnnotation.mandatory()).thenReturn(true);
    final Object[] arguments = { null };
    Mockito.when(invocation.getArguments()).thenReturn(arguments);
    auth.invoke(invocation);
  }

  /**
   * Hash null and not mandatory must do nothing.
   * 
   * @throws Throwable
   *           the throwable
   */
  @Test
  public void hashNullAndNotMandatoryMustDoNothing() throws Throwable {
    Mockito.when(authAnnotation.mandatory()).thenReturn(false);
    final Object[] arguments = { null };
    Mockito.when(invocation.getArguments()).thenReturn(arguments);
    auth.invoke(invocation);
  }

  /**
   * Hash null as string and not mandatory must do nothing.
   * 
   * @throws Throwable
   *           the throwable
   */
  @Test
  public void hashNullAsStringAndNotMandatoryMustDoNothing() throws Throwable {
    Mockito.when(authAnnotation.mandatory()).thenReturn(false);
    final Object[] arguments = { "null" };
    Mockito.when(invocation.getArguments()).thenReturn(arguments);
    auth.invoke(invocation);
  }

  /**
   * Other hash and mandatory and logged must session exp.
   * 
   * @throws Throwable
   *           the throwable
   */
  @Test(expected = SessionExpiredException.class)
  public void otherHashAndMandatoryAndLoggedMustSessionExp() throws Throwable {
    doLogin();
    Mockito.when(authAnnotation.mandatory()).thenReturn(true);
    final Object[] arguments = { "other-hash" };
    Mockito.when(invocation.getArguments()).thenReturn(arguments);
    auth.invoke(invocation);
  }

  /**
   * Same hash and mandatory and logged must session exp.
   * 
   * @throws Throwable
   *           the throwable
   */
  @Test
  public void sameHashAndMandatoryAndLoggedMustSessionExp() throws Throwable {
    doLogin();
    Mockito.when(authAnnotation.mandatory()).thenReturn(true);
    final Object[] arguments = { getHash() };
    Mockito.when(invocation.getArguments()).thenReturn(arguments);
    auth.invoke(invocation);
  }

  /**
   * Some hash and mandatory and not logged must session exp.
   * 
   * @throws Throwable
   *           the throwable
   */
  @Test(expected = SessionExpiredException.class)
  public void someHashAndMandatoryAndNotLoggedMustSessionExp() throws Throwable {
    Mockito.when(authAnnotation.mandatory()).thenReturn(true);
    final Object[] arguments = { "some-hash" };
    Mockito.when(invocation.getArguments()).thenReturn(arguments);
    auth.invoke(invocation);
  }
}
