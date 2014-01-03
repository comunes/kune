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

import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.client.errors.ContentNotFoundException;
import cc.kune.core.server.integration.IntegrationTest;
import cc.kune.core.server.integration.IntegrationTestHelper;
import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.domain.utils.StateToken;

import com.google.inject.persist.Transactional;

// TODO: Auto-generated Javadoc
/**
 * The Class AuthorizatedMethodInterceptorTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class AuthorizatedMethodInterceptorTest extends IntegrationTest {

  /** The auth. */
  private AuthorizatedMethodInterceptor auth;

  /** The auth annotation. */
  private Authorizated authAnnotation;

  /** The invocation. */
  private MethodInvocation invocation;

  /**
   * Before.
   */
  @Transactional
  @Before
  public void before() {
    auth = new AuthorizatedMethodInterceptor();
    new IntegrationTestHelper(true, auth, this);
    invocation = Mockito.mock(MethodInvocation.class);
    final AccessibleObject accessibleObject = Mockito.mock(AccessibleObject.class);
    Mockito.when(invocation.getMethod()).thenReturn(this.getClass().getMethods()[0]);
    Mockito.when(invocation.getStaticPart()).thenReturn(accessibleObject);
    authAnnotation = Mockito.mock(Authorizated.class);
    Mockito.when(accessibleObject.getAnnotation(Authorizated.class)).thenReturn(authAnnotation);

  }

  /**
   * Tes hash null container.
   * 
   * @throws Throwable
   *           the throwable
   */
  @Test(expected = ContentNotFoundException.class)
  public void tesHashNullContainer() throws Throwable {
    // Mockito.when(authAnnotation.accessRolRequired()).thenReturn(AccessRol.Administrator);
    Mockito.when(authAnnotation.actionLevel()).thenReturn(ActionLevel.content);
    final Object[] arguments = { getHash(), new StateToken("group.docs") };
    Mockito.when(invocation.getArguments()).thenReturn(arguments);
    auth.invoke(invocation);
  }

  /**
   * Test hash null content.
   * 
   * @throws Throwable
   *           the throwable
   */
  @Test(expected = ContentNotFoundException.class)
  public void testHashNullContent() throws Throwable {
    Mockito.when(authAnnotation.actionLevel()).thenReturn(ActionLevel.content);
    final Object[] arguments = { getHash(), new StateToken("group.docs.1") };
    Mockito.when(invocation.getArguments()).thenReturn(arguments);
    auth.invoke(invocation);
  }

  /**
   * Wrong group name throws excep.
   * 
   * @throws Throwable
   *           the throwable
   */
  @Test(expected = AccessViolationException.class)
  public void wrongGroupNameThrowsExcep() throws Throwable {
    doLogin();
    Mockito.when(authAnnotation.accessRolRequired()).thenReturn(AccessRol.Administrator);
    Mockito.when(authAnnotation.actionLevel()).thenReturn(ActionLevel.content);
    final Object[] arguments = { getHash(), new StateToken("groupWRONG.docs.1.1") };
    Mockito.when(invocation.getArguments()).thenReturn(arguments);
    auth.invoke(invocation);
  }

}
