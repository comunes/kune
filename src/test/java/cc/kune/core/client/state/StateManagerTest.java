package cc.kune.core.client.state;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.workspace.client.site.SiteToken;

import cc.kune.core.client.actions.BeforeActionListener;
import cc.kune.core.client.notify.SpinerPresenter;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.StateAbstractDTO;

import com.calclab.suco.testing.events.MockedListener;
import com.calclab.suco.testing.events.MockedListener0;
import com.calclab.suco.testing.events.MockedListener2;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class StateManagerTest {

    private static final String HASH = "someUserHash";
    private StateManagerDefault stateManager;
    private HistoryWrapper history;
    private ContentProvider contentProvider;
    private Session session;
    private MockedListener2<String, String> toolChangeListener;
    private MockedListener2<String, String> groupChangeListener;
    private MockedListener<StateAbstractDTO> stateChangeListener;
    private StateAbstractDTO state;
    private BeforeActionListener beforeChangeListener1;
    private BeforeActionListener beforeChangeListener2;

    @Before
    public void before() {
        contentProvider = Mockito.mock(ContentProvider.class);
        session = Mockito.mock(Session.class);
        history = Mockito.mock(HistoryWrapper.class);
        final SpinerPresenter spiner = Mockito.mock(SpinerPresenter.class);
        stateManager = new StateManagerDefault(contentProvider, session, history, spiner);
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
        new NotifyUser(null, null);
    }

    @Test
    public void changeGroupWithNoTool() {
        changeState("group1", "group2");
        assertTrue(groupChangeListener.isCalledWithEquals("", "group1", "group1", "group2"));
        assertTrue(toolChangeListener.isCalledWithEquals("", ""));
        assertTrue(groupChangeListener.isCalled(2));
        assertTrue(stateChangeListener.isCalled(2));
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

    @SuppressWarnings("unchecked")
    @Test
    public void siteTokenFirstLoadDefContentAndFireListener() {
        final MockedListener0 listener = new MockedListener0();
        final String token = SiteToken.signin.toString();
        stateManager.addSiteToken(token, listener);
        stateManager.onHistoryChanged(token);
        assertTrue(listener.isCalledOnce());
        Mockito.verify(contentProvider, Mockito.times(1)).getContent(Mockito.anyString(),
                (StateToken) Mockito.anyObject(), (AsyncCallback<StateAbstractDTO>) Mockito.anyObject());
    }

    @Test
    public void siteTokenTest() {
        final MockedListener0 siteTokenListener = new MockedListener0();
        stateManager.addSiteToken("signin", siteTokenListener);
        stateManager.onHistoryChanged("signIn");
        siteTokenListener.isCalledOnce();
    }

    private void changeState(final String... tokens) {
        for (final String token : tokens) {
            Mockito.when(state.getStateToken()).thenReturn(new StateToken(token));
            stateManager.setState(state);
        }
    }

    private String confBeforeStateChangeListeners(final boolean value, final boolean value2) {
        stateManager.addBeforeStateChangeListener(beforeChangeListener1);
        stateManager.addBeforeStateChangeListener(beforeChangeListener2);
        final String newToken = "something";
        Mockito.when(beforeChangeListener1.beforeAction()).thenReturn(value);
        Mockito.when(beforeChangeListener2.beforeAction()).thenReturn(value2);
        return newToken;
    }

    private void removeBeforeStateChangeListener() {
        stateManager.removeBeforeStateChangeListener(beforeChangeListener1);
        stateManager.removeBeforeStateChangeListener(beforeChangeListener2);
    }
}
