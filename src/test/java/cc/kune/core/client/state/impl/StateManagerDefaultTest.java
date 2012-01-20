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
package cc.kune.core.client.state.impl;

import static org.mockito.Matchers.anyBoolean;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

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
import cc.kune.core.shared.dto.ReservedWordsRegistryDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class StateManagerDefaultTest {

  private static final StateToken EMPTY_TOKEN = null;
  private static final StateToken GROUP1_TOOL1 = new StateToken("group1.tool1");
  private static final StateToken GROUP1_TOOL2 = new StateToken("group1.tool2");
  private static final StateToken GROUP2_TOOL1 = new StateToken("group2.tool1");
  private static final String HASH = "someUserHash";
  private BeforeActionListener beforeChangeListener1;
  private BeforeActionListener beforeChangeListener2;
  private ContentCache contentProvider;
  private EventBusTester eventBus;
  private GroupChangedHandler groupChangeHandler;
  private HistoryWrapper history;
  private Session session;
  private SiteTokenListeners siteTokens;
  private StateAbstractDTO state;
  private StateChangedHandler stateChangeHandler;
  private StateManagerDefault stateManager;
  private TokenMatcher tokenMatcher;
  private ToolChangedHandler toolChangeHandler;

  @Before
  public void before() {
    contentProvider = Mockito.mock(ContentCache.class);
    session = Mockito.mock(Session.class);
    history = Mockito.mock(HistoryWrapper.class);
    tokenMatcher = new TokenMatcher(new ReservedWordsRegistryDTO());
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
    stateManager = new StateManagerDefault(contentProvider, session, history, tokenMatcher, eventBus,
        siteTokens);
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

  private void changeState(final String... tokens) {
    for (final String token : tokens) {
      Mockito.when(state.getStateToken()).thenReturn(new StateToken(token));
      stateManager.setState(state);
    }
  }

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

  private String confBeforeStateChangeListeners(final boolean value, final boolean value2) {
    stateManager.addBeforeStateChangeListener(beforeChangeListener1);
    stateManager.addBeforeStateChangeListener(beforeChangeListener2);
    final String newToken = "something";
    Mockito.when(beforeChangeListener1.beforeAction()).thenReturn(value);
    Mockito.when(beforeChangeListener2.beforeAction()).thenReturn(value2);
    return newToken;
  }

  @Test
  public void getDefGroup() {
    stateManager.processHistoryToken("site.docs");
    verifyGetServerContent();
  }

  public void getWaveToken() {
    stateManager.processHistoryToken("example.com/w+abcd/~/conv+root/b+45kg");
  }

  @Test
  public void normalStartLoggedUser() {
    // When a user enter reload state, also if the application is starting
    // (and the user was logged)
    Mockito.when(history.getToken()).thenReturn("");
    stateManager.refreshCurrentState();
    verifyGetServerContent();
  }

  @Test
  public void oneBeforeStateChangeListenerAddAndRemove() {
    final String newToken = confBeforeStateChangeListeners(false, false);
    stateManager.processHistoryToken(newToken);
    removeBeforeStateChangeListener();
    stateManager.processHistoryToken(newToken);
    verifyGetServerContent();
  }

  @Test
  public void oneBeforeStateChangeListenerFalseAndResume() {
    final String token = confBeforeStateChangeListeners(false, true);
    stateManager.processHistoryToken(token);
    Mockito.verify(history, Mockito.never()).newItem(token);
    removeBeforeStateChangeListener();
    stateManager.resumeTokenChange();
    Mockito.verify(history, Mockito.times(1)).newItem(token);
  }

  @SuppressWarnings("unchecked")
  @Test
  public void oneBeforeStateChangeListenerReturnFalse() {
    stateManager.processHistoryToken(confBeforeStateChangeListeners(true, false));
    Mockito.verify(contentProvider, Mockito.never()).getContent(Mockito.anyString(),
        (StateToken) Mockito.anyObject(), (AsyncCallback<StateAbstractDTO>) Mockito.anyObject());
  }

  @SuppressWarnings("unchecked")
  @Test
  public void oneBeforeStateChangeListenerReturnFalseWithTwo() {
    stateManager.processHistoryToken(confBeforeStateChangeListeners(false, false));
    Mockito.verify(contentProvider, Mockito.never()).getContent(Mockito.anyString(),
        (StateToken) Mockito.anyObject(), (AsyncCallback<StateAbstractDTO>) Mockito.anyObject());
  }

  @Test
  public void oneBeforeStateChangeListenerReturnTrue() {
    stateManager.processHistoryToken(confBeforeStateChangeListeners(true, true));
    verifyGetServerContent();
  }

  private void removeBeforeStateChangeListener() {
    stateManager.removeBeforeStateChangeListener(beforeChangeListener1);
    stateManager.removeBeforeStateChangeListener(beforeChangeListener2);
  }

  @Test
  public void siteTokenFirstLoadDefContentAndFireListener() {
    final HistoryTokenCallback listener = Mockito.mock(HistoryTokenCallback.class);
    final String token = SiteTokens.SIGN_IN;
    stateManager.addSiteToken(token, listener);
    Mockito.when(siteTokens.get(SiteTokens.SIGN_IN)).thenReturn(listener);
    stateManager.processHistoryToken(token);
    Mockito.verify(listener, Mockito.times(1)).onHistoryToken(Mockito.anyString());
    verifyGetServerContent();
  }

  @Test
  public void siteTokenTest() {
    final HistoryTokenCallback listener = Mockito.mock(HistoryTokenCallback.class);
    stateManager.addSiteToken(SiteTokens.SIGN_IN, listener);
    Mockito.when(siteTokens.get(SiteTokens.SIGN_IN)).thenReturn(listener);
    stateManager.processHistoryToken("signIn");
    Mockito.verify(listener, Mockito.times(1)).onHistoryToken(Mockito.anyString());
  }

  @Test
  public void startMustLoadContent() {
    final InitDataDTO initData = Mockito.mock(InitDataDTO.class);
    Mockito.when(history.getToken()).thenReturn("");
    eventBus.fireEvent(new AppStartEvent(initData));
    verifyGetServerContent();
  }

  @SuppressWarnings("unchecked")
  private void verifyGetServerContent() {
    Mockito.verify(contentProvider, Mockito.times(1)).getContent(Mockito.anyString(),
        (StateToken) Mockito.anyObject(), (AsyncCallback<StateAbstractDTO>) Mockito.anyObject());
  }

}
