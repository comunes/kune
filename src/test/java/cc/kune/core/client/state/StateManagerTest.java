package cc.kune.core.client.state;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import cc.kune.common.client.actions.BeforeActionListener;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.StateAbstractDTO;

import com.calclab.suco.testing.events.MockedListener;
import com.calclab.suco.testing.events.MockedListener2;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class StateManagerTest {

    private static final String HASH = "someUserHash";
    private BeforeActionListener beforeChangeListener1;
    private BeforeActionListener beforeChangeListener2;
    private ContentProvider contentProvider;
    private MockedListener2<String, String> groupChangeListener;
    private HistoryWrapper history;
    private Session session;
    private StateAbstractDTO state;
    private MockedListener<StateAbstractDTO> stateChangeListener;
    private StateManagerDefault stateManager;
    private MockedListener2<String, String> toolChangeListener;

    @Before
    public void before() {
        contentProvider = Mockito.mock(ContentProvider.class);
        session = Mockito.mock(Session.class);
        history = Mockito.mock(HistoryWrapper.class);
        final EventBus eventBus = Mockito.mock(EventBus.class);
        stateManager = new StateManagerDefault(contentProvider, session, history, eventBus);
        Mockito.when(session.getUserHash()).thenReturn(HASH);
        state = Mockito.mock(StateAbstractDTO.class);
        stateChangeListener = new MockedListener<StateAbstractDTO>();
        groupChangeListener = new MockedListener2<String, String>();
        toolChangeListener = new MockedListener2<String, String>();
        beforeChangeListener1 = Mockito.mock(BeforeActionListener.class);
        beforeChangeListener2 = Mockito.mock(BeforeActionListener.class);
        stateManager.onStateChanged(stateChangeListener);
        stateManager.onGroupChanged(groupChangeListener);
        stateManager.onToolChanged(toolChangeListener);
        // new NotifyUser(null, null);
    }

    @Test
    public void changeGroupWithNoTool() {
        changeState("group1", "group2");
        assertTrue(groupChangeListener.isCalledWithEquals("", "group1", "group1", "group2"));
        assertTrue(toolChangeListener.isCalledWithEquals("", ""));
        assertTrue(groupChangeListener.isCalled(2));
        assertTrue(stateChangeListener.isCalled(2));
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
        assertTrue(stateChangeListener.isCalled(2));
        assertTrue(groupChangeListener.isCalledWithEquals("", "group2", "group2", "group1"));
        assertTrue(toolChangeListener.isCalledWithEquals("", "tool1", "tool1", "tool2"));
    }

    @Test
    public void changeStateWithDifferentGroupsMustFireListener() {
        changeState("group1.tool1", "group2.tool1");
        assertTrue(stateChangeListener.isCalled(2));
        assertTrue(groupChangeListener.isCalledWithEquals("", "group1", "group1", "group2"));
        assertTrue(toolChangeListener.isCalledWithEquals("", "tool1"));
    }

    @Test
    public void changeStateWithDifferentToolsMustFireListener() {
        changeState("group1.tool1", "group1.tool2");
        assertTrue(stateChangeListener.isCalled(2));
        assertTrue(toolChangeListener.isCalledWithEquals("", "tool1", "tool1", "tool2"));
        assertTrue(groupChangeListener.isCalledWithEquals("", "group1"));
    }

    @Test
    public void changeToNoTool() {
        changeState("group1.tool1", "group1");
        assertTrue(groupChangeListener.isCalledWithEquals("", "group1"));
        assertTrue(toolChangeListener.isCalledWithEquals("", "tool1", "tool1", ""));
        assertTrue(groupChangeListener.isCalledOnce());
        assertTrue(stateChangeListener.isCalled(2));
    }

    @Test
    public void changeToSameToken() {
        changeState("group1.tool1", "group1.tool1");
        assertTrue(groupChangeListener.isCalledWithEquals("", "group1"));
        assertTrue(toolChangeListener.isCalledWithEquals("", "tool1"));
        assertTrue(groupChangeListener.isCalledOnce());
        assertTrue(stateChangeListener.isCalled(2));
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
        stateManager.onHistoryChanged("site.docs");
        Mockito.verify(contentProvider, Mockito.times(1)).getContent(Mockito.anyString(),
                (StateToken) Mockito.anyObject(), (AsyncCallback<StateAbstractDTO>) Mockito.anyObject());
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
        stateManager.onHistoryChanged(newToken);
        removeBeforeStateChangeListener();
        stateManager.onHistoryChanged(newToken);
        Mockito.verify(contentProvider, Mockito.times(1)).getContent(Mockito.anyString(),
                (StateToken) Mockito.anyObject(), (AsyncCallback<StateAbstractDTO>) Mockito.anyObject());
    }

    @Test
    public void oneBeforeStateChangeListenerFalseAndResume() {
        final String token = confBeforeStateChangeListeners(false, true);
        stateManager.onHistoryChanged(token);
        Mockito.verify(history, Mockito.never()).newItem(token);
        removeBeforeStateChangeListener();
        stateManager.resumeTokenChange();
        Mockito.verify(history, Mockito.times(1)).newItem(token);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void oneBeforeStateChangeListenerReturnFalse() {
        stateManager.onHistoryChanged(confBeforeStateChangeListeners(true, false));
        Mockito.verify(contentProvider, Mockito.never()).getContent(Mockito.anyString(),
                (StateToken) Mockito.anyObject(), (AsyncCallback<StateAbstractDTO>) Mockito.anyObject());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void oneBeforeStateChangeListenerReturnFalseWithTwo() {
        stateManager.onHistoryChanged(confBeforeStateChangeListeners(false, false));
        Mockito.verify(contentProvider, Mockito.never()).getContent(Mockito.anyString(),
                (StateToken) Mockito.anyObject(), (AsyncCallback<StateAbstractDTO>) Mockito.anyObject());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void oneBeforeStateChangeListenerReturnTrue() {
        stateManager.onHistoryChanged(confBeforeStateChangeListeners(true, true));
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
        final String token = SiteCommonTokens.SIGNIN;
        stateManager.addSiteToken(token, listener);
        stateManager.onHistoryChanged(token);
        Mockito.verify(listener, Mockito.times(1)).onHistoryToken();
        Mockito.verify(contentProvider, Mockito.times(1)).getContent(Mockito.anyString(),
                (StateToken) Mockito.anyObject(), (AsyncCallback<StateAbstractDTO>) Mockito.anyObject());
    }

    @Test
    public void siteTokenTest() {
        final HistoryTokenCallback siteTokenListener = Mockito.mock(HistoryTokenCallback.class);
        stateManager.addSiteToken("signin", siteTokenListener);
        stateManager.onHistoryChanged("signIn");
        Mockito.verify(siteTokenListener, Mockito.times(1)).onHistoryToken();
    }
}
