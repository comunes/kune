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
package cc.kune.core.client.state.impl;

import static org.mockito.Matchers.anyBoolean;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.waveprotocol.wave.util.escapers.jvm.JavaWaverefEncoder;

import cc.kune.common.client.actions.BeforeActionListener;
import cc.kune.core.client.events.AppStartEvent;
import cc.kune.core.client.events.AppStartEvent.AppStartHandler;
import cc.kune.core.client.events.GroupChangedEvent;
import cc.kune.core.client.events.GroupChangedEvent.GroupChangedHandler;
import cc.kune.core.client.events.StateChangedEvent;
import cc.kune.core.client.events.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.events.ToolChangedEvent;
import cc.kune.core.client.events.ToolChangedEvent.ToolChangedHandler;
import cc.kune.core.client.events.UserSignInEvent;
import cc.kune.core.client.events.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.events.UserSignOutEvent;
import cc.kune.core.client.events.UserSignOutEvent.UserSignOutHandler;
import cc.kune.core.client.state.ContentCache;
import cc.kune.core.client.state.HistoryTokenCallback;
import cc.kune.core.client.state.HistoryWrapper;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokenListeners;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.TokenMatcher;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.InitDataDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Class StateManagerDefaultTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class StateManagerDefaultTest {

  /** The Constant EMPTY_TOKEN. */
  private static final StateToken EMPTY_TOKEN = null;

  /** The Constant GROUP1_TOOL1. */
  private static final StateToken GROUP1_TOOL1 = new StateToken("group1.tool1");

  /** The Constant GROUP1_TOOL2. */
  private static final StateToken GROUP1_TOOL2 = new StateToken("group1.tool2");

  /** The Constant GROUP2_TOOL1. */
  private static final StateToken GROUP2_TOOL1 = new StateToken("group2.tool1");

  /** The Constant HASH. */
  private static final String HASH = "someUserHash";

  /** The before change listener1. */
  private BeforeActionListener beforeChangeListener1;

  /** The before change listener2. */
  private BeforeActionListener beforeChangeListener2;

  /** The content provider. */
  private ContentCache contentProvider;

  /** The event bus. */
  private EventBusTester eventBus;

  /** The group change handler. */
  private GroupChangedHandler groupChangeHandler;

  /** The history. */
  private HistoryWrapper history;

  /** The session. */
  private Session session;

  /** The site tokens. */
  private SiteTokenListeners siteTokens;

  /** The state. */
  private StateAbstractDTO state;

  /** The state change handler. */
  private StateChangedHandler stateChangeHandler;

  /** The state manager. */
  private StateManagerDefault stateManager;

  /** The tool change handler. */
  private ToolChangedHandler toolChangeHandler;

  /**
   * Before.
   */
  @Before
  public void before() {
    contentProvider = Mockito.mock(ContentCache.class);
    session = Mockito.mock(Session.class);
    history = Mockito.mock(HistoryWrapper.class);
    TokenMatcher.init(JavaWaverefEncoder.INSTANCE);
    siteTokens = Mockito.mock(SiteTokenListeners.class);
    eventBus = new EventBusTester();
    Mockito.when(session.getUserHash()).thenReturn(HASH);
    Mockito.doAnswer(new Answer<Object>() {
      @Override
      public Object answer(final InvocationOnMock invocation) throws Throwable {
        eventBus.addHandler(UserSignInEvent.getType(), (UserSignInHandler) invocation.getArguments()[1]);
        return null;
      }
    }).when(session).onUserSignIn(anyBoolean(), (UserSignInHandler) Mockito.anyObject());
    Mockito.doAnswer(new Answer<Object>() {
      @Override
      public Object answer(final InvocationOnMock invocation) throws Throwable {
        eventBus.addHandler(UserSignOutEvent.getType(),
            (UserSignOutHandler) invocation.getArguments()[1]);
        return null;
      }
    }).when(session).onUserSignOut(anyBoolean(), (UserSignOutHandler) Mockito.anyObject());
    Mockito.doAnswer(new Answer<Object>() {
      @Override
      public Object answer(final InvocationOnMock invocation) throws Throwable {
        eventBus.addHandler(AppStartEvent.getType(), (AppStartHandler) invocation.getArguments()[1]);
        return null;
      }
    }).when(session).onAppStart(anyBoolean(), (AppStartHandler) Mockito.anyObject());
    state = Mockito.mock(StateAbstractDTO.class);
    stateManager = new StateManagerDefault(contentProvider, session, history, eventBus, siteTokens, null);
    stateChangeHandler = Mockito.mock(StateChangedHandler.class);
    groupChangeHandler = Mockito.mock(GroupChangedHandler.class);
    toolChangeHandler = Mockito.mock(ToolChangedHandler.class);
    beforeChangeListener1 = Mockito.mock(BeforeActionListener.class);
    beforeChangeListener2 = Mockito.mock(BeforeActionListener.class);
    eventBus.addHandler(StateChangedEvent.getType(), stateChangeHandler);
    eventBus.addHandler(GroupChangedEvent.getType(), groupChangeHandler);
    eventBus.addHandler(ToolChangedEvent.getType(), toolChangeHandler);
    // new NotifyUser(null, null);
  }

  /**
   * Change group with no tool.
   */
  @Test
  public void changeGroupWithNoTool() {
    changeState("group1", "group2");
    Mockito.verify(stateChangeHandler, Mockito.times(2)).onStateChanged(
        (StateChangedEvent) Mockito.anyObject());
    Mockito.verify(groupChangeHandler, Mockito.times(2)).onGroupChanged(
        (GroupChangedEvent) Mockito.anyObject());
    Mockito.verify(groupChangeHandler, Mockito.times(2)).onGroupChanged(
        (GroupChangedEvent) Mockito.anyObject());
  }

  /**
   * Change state.
   * 
   * @param tokens
   *          the tokens
   */
  private void changeState(final String... tokens) {
    for (final String token : tokens) {
      Mockito.when(state.getStateToken()).thenReturn(new StateToken(token));
      stateManager.setState(state);
    }
  }

  /**
   * Change state with different and groups tools must fire listener.
   */
  @Test
  public void changeStateWithDifferentAndGroupsToolsMustFireListener() {
    changeState("group2.tool1", "group1.tool2");
    Mockito.verify(stateChangeHandler, Mockito.times(2)).onStateChanged(
        (StateChangedEvent) Mockito.anyObject());
    Mockito.verify(groupChangeHandler, Mockito.times(2)).onGroupChanged(
        (GroupChangedEvent) Mockito.anyObject());
    Mockito.verify(toolChangeHandler, Mockito.times(2)).onToolChanged(
        (ToolChangedEvent) Mockito.anyObject());
    Mockito.verify(groupChangeHandler, Mockito.times(1)).onGroupChanged(
        new GroupChangedEvent("", "group2"));
    Mockito.verify(groupChangeHandler, Mockito.times(1)).onGroupChanged(
        new GroupChangedEvent("group2", "group1"));
    Mockito.verify(toolChangeHandler, Mockito.times(1)).onToolChanged(
        new ToolChangedEvent(EMPTY_TOKEN, GROUP2_TOOL1));
    Mockito.verify(toolChangeHandler, Mockito.times(1)).onToolChanged(
        new ToolChangedEvent(GROUP2_TOOL1, GROUP1_TOOL2));
  }

  /**
   * Change state with different groups must fire listener.
   */
  @Test
  public void changeStateWithDifferentGroupsMustFireListener() {
    changeState("group1.tool1", "group2.tool1");
    // assertTrue(stateChangeListener.isCalled(2));
    // assertTrue(groupChangeListener.isCalledWithEquals("", "group1",
    // "group1", "group2"));
    // assertTrue(toolChangeListener.isCalledWithEquals("", "tool1"));
    Mockito.verify(stateChangeHandler, Mockito.times(2)).onStateChanged(
        (StateChangedEvent) Mockito.anyObject());
    Mockito.verify(groupChangeHandler, Mockito.times(2)).onGroupChanged(
        (GroupChangedEvent) Mockito.anyObject());
    Mockito.verify(toolChangeHandler, Mockito.times(1)).onToolChanged(
        (ToolChangedEvent) Mockito.anyObject());
    Mockito.verify(groupChangeHandler, Mockito.times(1)).onGroupChanged(
        new GroupChangedEvent("", "group1"));
    Mockito.verify(groupChangeHandler, Mockito.times(1)).onGroupChanged(
        new GroupChangedEvent("group1", "group2"));
    Mockito.verify(toolChangeHandler, Mockito.times(1)).onToolChanged(
        new ToolChangedEvent(null, GROUP1_TOOL1));

  }

  /**
   * Change state with different tools must fire listener.
   */
  @Test
  public void changeStateWithDifferentToolsMustFireListener() {
    changeState("group1.tool1", "group1.tool2");
    Mockito.verify(stateChangeHandler, Mockito.times(2)).onStateChanged(
        (StateChangedEvent) Mockito.anyObject());
    Mockito.verify(groupChangeHandler, Mockito.times(1)).onGroupChanged(
        (GroupChangedEvent) Mockito.anyObject());
    Mockito.verify(toolChangeHandler, Mockito.times(2)).onToolChanged(
        (ToolChangedEvent) Mockito.anyObject());
    Mockito.verify(groupChangeHandler, Mockito.times(1)).onGroupChanged(
        new GroupChangedEvent("", "group1"));
    Mockito.verify(toolChangeHandler, Mockito.times(1)).onToolChanged(
        new ToolChangedEvent(EMPTY_TOKEN, GROUP1_TOOL1));
    Mockito.verify(toolChangeHandler, Mockito.times(1)).onToolChanged(
        new ToolChangedEvent(GROUP1_TOOL1, GROUP1_TOOL2));
  }

  /**
   * Change to no tool.
   */
  @Test
  public void changeToNoTool() {
    changeState("group1.tool1", "group1");
    Mockito.verify(stateChangeHandler, Mockito.times(2)).onStateChanged(
        (StateChangedEvent) Mockito.anyObject());
    Mockito.verify(groupChangeHandler, Mockito.times(1)).onGroupChanged(
        (GroupChangedEvent) Mockito.anyObject());
    Mockito.verify(toolChangeHandler, Mockito.times(2)).onToolChanged(
        (ToolChangedEvent) Mockito.anyObject());
    Mockito.verify(groupChangeHandler, Mockito.times(1)).onGroupChanged(
        new GroupChangedEvent("", "group1"));
    Mockito.verify(toolChangeHandler, Mockito.times(1)).onToolChanged(
        new ToolChangedEvent(EMPTY_TOKEN, GROUP1_TOOL1));
    Mockito.verify(toolChangeHandler, Mockito.times(1)).onToolChanged(
        new ToolChangedEvent(GROUP1_TOOL1, new StateToken("group1")));
  }

  /**
   * Change to same token.
   */
  @Test
  public void changeToSameToken() {
    changeState("group1.tool1", "group1.tool1");
    Mockito.verify(stateChangeHandler, Mockito.times(2)).onStateChanged(
        (StateChangedEvent) Mockito.anyObject());
    Mockito.verify(groupChangeHandler, Mockito.times(1)).onGroupChanged(
        (GroupChangedEvent) Mockito.anyObject());
    Mockito.verify(toolChangeHandler, Mockito.times(1)).onToolChanged(
        (ToolChangedEvent) Mockito.anyObject());
    Mockito.verify(groupChangeHandler, Mockito.times(1)).onGroupChanged(
        new GroupChangedEvent("", "group1"));
    Mockito.verify(toolChangeHandler, Mockito.times(1)).onToolChanged(
        new ToolChangedEvent(EMPTY_TOKEN, GROUP1_TOOL1));
  }

  /**
   * Conf before state change listeners.
   * 
   * @param value
   *          the value
   * @param value2
   *          the value2
   * @return the string
   */
  private String confBeforeStateChangeListeners(final boolean value, final boolean value2) {
    stateManager.addBeforeStateChangeListener(beforeChangeListener1);
    stateManager.addBeforeStateChangeListener(beforeChangeListener2);
    final String newToken = "something";
    Mockito.when(beforeChangeListener1.beforeAction()).thenReturn(value);
    Mockito.when(beforeChangeListener2.beforeAction()).thenReturn(value2);
    return newToken;
  }

  /**
   * Gets the def group.
   * 
   * @return the def group
   */
  @Test
  public void getDefGroup() {
    stateManager.processHistoryToken("site.docs");
    verifyGetServerContent();
  }

  /**
   * Gets the wave token.
   * 
   * @return the wave token
   */
  public void getWaveToken() {
    stateManager.processHistoryToken("example.com/w+abcd/~/conv+root/b+45kg");
  }

  /**
   * Normal start logged user.
   */
  @Test
  public void normalStartLoggedUser() {
    // When a user enter reload state, also if the application is starting
    // (and the user was logged)
    Mockito.when(history.getToken()).thenReturn("");
    stateManager.refreshCurrentState();
    verifyGetServerContent();
  }

  /**
   * One before state change listener add and remove.
   */
  @Test
  public void oneBeforeStateChangeListenerAddAndRemove() {
    final String newToken = confBeforeStateChangeListeners(false, false);
    stateManager.processHistoryToken(newToken);
    removeBeforeStateChangeListener();
    stateManager.processHistoryToken(newToken);
    verifyGetServerContent();
  }

  /**
   * One before state change listener false and resume.
   */
  @Test
  public void oneBeforeStateChangeListenerFalseAndResume() {
    final String token = confBeforeStateChangeListeners(false, true);
    stateManager.processHistoryToken(token);
    Mockito.verify(history, Mockito.never()).newItem(token);
    removeBeforeStateChangeListener();
    stateManager.resumeTokenChange();
    Mockito.verify(history, Mockito.times(1)).newItem(token);
  }

  /**
   * One before state change listener return false.
   */
  @SuppressWarnings("unchecked")
  @Test
  public void oneBeforeStateChangeListenerReturnFalse() {
    stateManager.processHistoryToken(confBeforeStateChangeListeners(true, false));
    Mockito.verify(contentProvider, Mockito.never()).getContent(Mockito.anyString(),
        (StateToken) Mockito.anyObject(), (AsyncCallback<StateAbstractDTO>) Mockito.anyObject());
  }

  /**
   * One before state change listener return false with two.
   */
  @SuppressWarnings("unchecked")
  @Test
  public void oneBeforeStateChangeListenerReturnFalseWithTwo() {
    stateManager.processHistoryToken(confBeforeStateChangeListeners(false, false));
    Mockito.verify(contentProvider, Mockito.never()).getContent(Mockito.anyString(),
        (StateToken) Mockito.anyObject(), (AsyncCallback<StateAbstractDTO>) Mockito.anyObject());
  }

  /**
   * One before state change listener return true.
   */
  @Test
  public void oneBeforeStateChangeListenerReturnTrue() {
    stateManager.processHistoryToken(confBeforeStateChangeListeners(true, true));
    verifyGetServerContent();
  }

  /**
   * Removes the before state change listener.
   */
  private void removeBeforeStateChangeListener() {
    stateManager.removeBeforeStateChangeListener(beforeChangeListener1);
    stateManager.removeBeforeStateChangeListener(beforeChangeListener2);
  }

  /**
   * Site token first load def content and fire listener.
   */
  @Test
  public void siteTokenFirstLoadDefContentAndFireListener() {
    final HistoryTokenCallback listener = Mockito.mock(HistoryTokenCallback.class);
    final String token = SiteTokens.SIGN_IN;
    stateManager.addSiteToken(token, listener);
    Mockito.when(siteTokens.get(SiteTokens.SIGN_IN)).thenReturn(listener);
    stateManager.processHistoryToken(token);
    verifyGetServerContent();
  }

  /**
   * Start must load content.
   */
  @Test
  public void startMustLoadContent() {
    final InitDataDTO initData = Mockito.mock(InitDataDTO.class);
    Mockito.when(history.getToken()).thenReturn("");
    eventBus.fireEvent(new AppStartEvent(initData));
    verifyGetServerContent();
  }

  /**
   * Verify get server content.
   */
  @SuppressWarnings("unchecked")
  private void verifyGetServerContent() {
    Mockito.verify(contentProvider, Mockito.times(1)).getContent(Mockito.anyString(),
        (StateToken) Mockito.anyObject(), (AsyncCallback<StateAbstractDTO>) Mockito.anyObject());
  }

}
