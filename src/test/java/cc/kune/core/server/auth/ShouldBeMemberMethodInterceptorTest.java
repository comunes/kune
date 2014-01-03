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
import cc.kune.core.server.integration.IntegrationTest;
import cc.kune.core.server.integration.IntegrationTestHelper;
import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.shared.domain.AccessRol;

import com.google.inject.persist.Transactional;

// TODO: Auto-generated Javadoc
/**
 * The Class ShouldBeMemberMethodInterceptorTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ShouldBeMemberMethodInterceptorTest extends IntegrationTest {

  /** The invocation. */
  private MethodInvocation invocation;

  /** The annotation. */
  private ShouldBeMember annotation;

  /** The translator interceptor. */
  private ShouldBeMemberMethodInterceptor translatorInterceptor;

  /**
   * Before.
   */
  @Transactional
  @Before
  public void before() {
    translatorInterceptor = new ShouldBeMemberMethodInterceptor();
    new IntegrationTestHelper(true, this, translatorInterceptor);
    invocation = Mockito.mock(MethodInvocation.class);
    final AccessibleObject accessibleObject = Mockito.mock(AccessibleObject.class);
    Mockito.when(invocation.getMethod()).thenReturn(this.getClass().getMethods()[0]);
    Mockito.when(invocation.getStaticPart()).thenReturn(accessibleObject);
    annotation = Mockito.mock(ShouldBeMember.class);
    Mockito.when(accessibleObject.getAnnotation(ShouldBeMember.class)).thenReturn(annotation);
  }

  /**
   * Invoke.
   * 
   * @throws Throwable
   *           the throwable
   */
  private void invoke() throws Throwable {
    Mockito.when(annotation.rol()).thenReturn(AccessRol.Administrator);
    Mockito.when(annotation.groupKuneProperty()).thenReturn(KuneProperties.UI_TRANSLATOR_GROUP);
    final Object[] arguments = { getHash() };
    Mockito.when(invocation.getArguments()).thenReturn(arguments);
    translatorInterceptor.invoke(invocation);
  }

  /**
   * Not member throws excep.
   * 
   * @throws Throwable
   *           the throwable
   */
  @Test(expected = AccessViolationException.class)
  public void notMemberThrowsExcep() throws Throwable {
    doLoginWithDummyUser();
    invoke();
  }

  /**
   * Super admin do the job.
   * 
   * @throws Throwable
   *           the throwable
   */
  @Test
  public void superAdminDoTheJob() throws Throwable {
    doLogin();
    invoke();
  }
}
