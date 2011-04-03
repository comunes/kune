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
package cc.kune.core.client.state;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import cc.kune.common.client.actions.BeforeActionListener;
import cc.kune.core.client.state.GroupChangedEvent.GroupChangedHandler;
import cc.kune.core.client.state.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.state.ToolChangedEvent.ToolChangedHandler;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.StateAbstractDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class StateManagerDefaultTest {

    private static final String HASH = "someUserHash";
    private BeforeActionListener beforeChangeListener1;
    private BeforeActionListener beforeChangeListener2;
    private ContentCache contentProvider;
    private EventBusTester eventBus;
    private GroupChangedHandler groupChangeHandler;
    private HistoryWrapper history;
    private Session session;
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
        tokenMatcher = Mockito.mock(TokenMatcher.class);
        eventBus = new EventBusTester();
        stateManager = new StateManagerDefault(contentProvider, session, history, tokenMatcher, eventBus);
        Mockito.when(session.getUserHash()).thenReturn(HASH);
        state = Mockito.mock(StateAbstractDTO.class);
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
        Mockito.verify(stateChangeHandler, Mockito.times(2)).onStateChanged((StateChangedEvent) Mockito.anyObject());
        Mockito.verify(groupChangeHandler, Mockito.times(2)).onGroupChanged((GroupChangedEvent) Mockito.anyObject());
        Mockito.verify(groupChangeHandler, Mockito.times(2)).onGroupChanged((GroupChangedEvent) Mockito.anyObject());
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
        Mockito.verify(stateChangeHandler, Mockito.times(2)).onStateChanged((StateChangedEvent) Mockito.anyObject());
        Mockito.verify(groupChangeHandler, Mockito.times(2)).onGroupChanged((GroupChangedEvent) Mockito.anyObject());
        Mockito.verify(toolChangeHandler, Mockito.times(2)).onToolChanged((ToolChangedEvent) Mockito.anyObject());
        Mockito.verify(groupChangeHandler, Mockito.times(1)).onGroupChanged(new GroupChangedEvent("", "group2"));
        Mockito.verify(groupChangeHandler, Mockito.times(1)).onGroupChanged(new GroupChangedEvent("group2", "group1"));
        Mockito.verify(toolChangeHandler, Mockito.times(1)).onToolChanged(new ToolChangedEvent("", "tool1"));
        Mockito.verify(toolChangeHandler, Mockito.times(1)).onToolChanged(new ToolChangedEvent("tool1", "tool2"));
    }

    @Test
    public void changeStateWithDifferentGroupsMustFireListener() {
        changeState("group1.tool1", "group2.tool1");
        // assertTrue(stateChangeListener.isCalled(2));
        // assertTrue(groupChangeListener.isCalledWithEquals("", "group1",
        // "group1", "group2"));
        // assertTrue(toolChangeListener.isCalledWithEquals("", "tool1"));
        Mockito.verify(stateChangeHandler, Mockito.times(2)).onStateChanged((StateChangedEvent) Mockito.anyObject());
        Mockito.verify(groupChangeHandler, Mockito.times(2)).onGroupChanged((GroupChangedEvent) Mockito.anyObject());
        Mockito.verify(toolChangeHandler, Mockito.times(1)).onToolChanged((ToolChangedEvent) Mockito.anyObject());
        Mockito.verify(groupChangeHandler, Mockito.times(1)).onGroupChanged(new GroupChangedEvent("", "group1"));
        Mockito.verify(groupChangeHandler, Mockito.times(1)).onGroupChanged(new GroupChangedEvent("group1", "group2"));
        Mockito.verify(toolChangeHandler, Mockito.times(1)).onToolChanged(new ToolChangedEvent("", "tool1"));

    }

    @Test
    public void changeStateWithDifferentToolsMustFireListener() {
        changeState("group1.tool1", "group1.tool2");
        Mockito.verify(stateChangeHandler, Mockito.times(2)).onStateChanged((StateChangedEvent) Mockito.anyObject());
        Mockito.verify(groupChangeHandler, Mockito.times(1)).onGroupChanged((GroupChangedEvent) Mockito.anyObject());
        Mockito.verify(toolChangeHandler, Mockito.times(2)).onToolChanged((ToolChangedEvent) Mockito.anyObject());
        Mockito.verify(groupChangeHandler, Mockito.times(1)).onGroupChanged(new GroupChangedEvent("", "group1"));
        Mockito.verify(toolChangeHandler, Mockito.times(1)).onToolChanged(new ToolChangedEvent("", "tool1"));
        Mockito.verify(toolChangeHandler, Mockito.times(1)).onToolChanged(new ToolChangedEvent("tool1", "tool2"));
    }

    @Test
    public void changeToNoTool() {
        changeState("group1.tool1", "group1");
        Mockito.verify(stateChangeHandler, Mockito.times(2)).onStateChanged((StateChangedEvent) Mockito.anyObject());
        Mockito.verify(groupChangeHandler, Mockito.times(1)).onGroupChanged((GroupChangedEvent) Mockito.anyObject());
        Mockito.verify(toolChangeHandler, Mockito.times(2)).onToolChanged((ToolChangedEvent) Mockito.anyObject());
        Mockito.verify(groupChangeHandler, Mockito.times(1)).onGroupChanged(new GroupChangedEvent("", "group1"));
        Mockito.verify(toolChangeHandler, Mockito.times(1)).onToolChanged(new ToolChangedEvent("", "tool1"));
        Mockito.verify(toolChangeHandler, Mockito.times(1)).onToolChanged(new ToolChangedEvent("tool1", ""));
    }

    @Test
    public void changeToSameToken() {
        changeState("group1.tool1", "group1.tool1");
        Mockito.verify(stateChangeHandler, Mockito.times(2)).onStateChanged((StateChangedEvent) Mockito.anyObject());
        Mockito.verify(groupChangeHandler, Mockito.times(1)).onGroupChanged((GroupChangedEvent) Mockito.anyObject());
        Mockito.verify(toolChangeHandler, Mockito.times(1)).onToolChanged((ToolChangedEvent) Mockito.anyObject());
        Mockito.verify(groupChangeHandler, Mockito.times(1)).onGroupChanged(new GroupChangedEvent("", "group1"));
        Mockito.verify(toolChangeHandler, Mockito.times(1)).onToolChanged(new ToolChangedEvent("", "tool1"));
    }

    private String confBeforeStateChangeListeners(final boolean value, final boolean value2) {
        stateManager.addBeforeStateChangeListener(beforeChangeListener1);
        stateManager.addBeforeStateChangeListener(beforeChangeListener2);
        final String newToken = "something";
        Mockito.when(beforeChangeListener1.beforeAction()).thenReturn(value);
        Mockito.when(beforeChangeListener2.beforeAction()).thenReturn(value2);
        return newToken;
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getDefGroup() {
        stateManager.processHistoryToken("site.docs");
        Mockito.verify(contentProvider, Mockito.times(1)).getContent(Mockito.anyString(),
                (StateToken) Mockito.anyObject(), (AsyncCallback<StateAbstractDTO>) Mockito.anyObject());
    }

    public void getWaveToken() {
        stateManager.processHistoryToken("example.com/w+abcd/~/conv+root/b+45kg");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void normalStartLoggedUser() {
        // When a user enter reload state, also if the application is starting
        // (and the user was logged)
        Mockito.when(history.getToken()).thenReturn("");
        stateManager.reload();
        Mockito.verify(contentProvider, Mockito.times(1)).getContent(Mockito.anyString(),
                (StateToken) Mockito.anyObject(), (AsyncCallback<StateAbstractDTO>) Mockito.anyObject());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void oneBeforeStateChangeListenerAddAndRemove() {
        final String newToken = confBeforeStateChangeListeners(false, false);
        stateManager.processHistoryToken(newToken);
        removeBeforeStateChangeListener();
        stateManager.processHistoryToken(newToken);
        Mockito.verify(contentProvider, Mockito.times(1)).getContent(Mockito.anyString(),
                (StateToken) Mockito.anyObject(), (AsyncCallback<StateAbstractDTO>) Mockito.anyObject());
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

    @SuppressWarnings("unchecked")
    @Test
    public void oneBeforeStateChangeListenerReturnTrue() {
        stateManager.processHistoryToken(confBeforeStateChangeListeners(true, true));
        Mockito.verify(contentProvider, Mockito.times(1)).getContent(Mockito.anyString(),
                (StateToken) Mockito.anyObject(), (AsyncCallback<StateAbstractDTO>) Mockito.anyObject());
    }

    private void removeBeforeStateChangeListener() {
        stateManager.removeBeforeStateChangeListener(beforeChangeListener1);
        stateManager.removeBeforeStateChangeListener(beforeChangeListener2);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void siteTokenFirstLoadDefContentAndFireListener() {
        final HistoryTokenCallback listener = Mockito.mock(HistoryTokenCallback.class);
        final String token = SiteTokens.SIGNIN;
        stateManager.addSiteToken(token, listener);
        stateManager.processHistoryToken(token);
        Mockito.verify(listener, Mockito.times(1)).onHistoryToken();
        Mockito.verify(contentProvider, Mockito.times(1)).getContent(Mockito.anyString(),
                (StateToken) Mockito.anyObject(), (AsyncCallback<StateAbstractDTO>) Mockito.anyObject());
    }

    @Test
    public void siteTokenTest() {
        final HistoryTokenCallback siteTokenListener = Mockito.mock(HistoryTokenCallback.class);
        stateManager.addSiteToken("signin", siteTokenListener);
        stateManager.processHistoryToken("signIn");
        Mockito.verify(siteTokenListener, Mockito.times(1)).onHistoryToken();
    }
}
