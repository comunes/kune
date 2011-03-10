package cc.kune.core.client.sn.actions.conditions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.UserSimpleDTO;

import com.google.inject.Inject;

@RunWith(JukitoRunner.class)
public class IsNotMeConditionTest {

    @Inject
    GuiActionDescrip descr;
    GroupDTO group;
    private IsMeCondition isMeCondition;
    private IsNotMeCondition isNotMeCondition;
    UserSimpleDTO me;
    GroupDTO myGroup;
    GroupDTO otherGroup;
    UserSimpleDTO otherUser;
    @Inject
    Session session;

    @Before
    public void before() {
        this.me = Mockito.mock(UserSimpleDTO.class);
        this.otherUser = Mockito.mock(UserSimpleDTO.class);
        this.myGroup = Mockito.mock(GroupDTO.class);
        this.otherGroup = Mockito.mock(GroupDTO.class);
        // GWTMockUtilities.
        isNotMeCondition = new IsNotMeCondition(session);
        isMeCondition = new IsMeCondition(session);
        when(me.getShortName()).thenReturn("me");
        when(otherUser.getShortName()).thenReturn("other");
        when(myGroup.getShortName()).thenReturn("me");
        when(otherGroup.getShortName()).thenReturn("otherGroup");
        when(session.getCurrentUser()).thenReturn(me);
    }

    private void login() {
        when(session.getCurrentUser()).thenReturn(me);
        when(session.isLogged()).thenReturn(true);
        when(session.isNotLogged()).thenReturn(false);
    }

    private void logout() {
        when(session.getCurrentUser()).thenReturn(null);
        when(session.isLogged()).thenReturn(false);
        when(session.isNotLogged()).thenReturn(true);
    }

    private void shouldBeAdded(final boolean mustBeAdded) {
        assertTrue(mustBeAdded);
    }

    private void shouldNotBeAdded(final boolean mustNotBeAdded) {
        assertFalse(mustNotBeAdded);
    }

    @Test
    public void testIsMeMyGroup() {
        login();
        when(descr.getTarget()).thenReturn(myGroup);
        shouldBeAdded(isMeCondition.mustBeAdded(descr));
    }

    @Test
    public void testIsMeMyGroupNotLogged() {
        logout();
        when(descr.getTarget()).thenReturn(myGroup);
        shouldNotBeAdded(isMeCondition.mustBeAdded(descr));
    }

    @Test
    public void testIsMeOtherGroup() {
        login();
        when(descr.getTarget()).thenReturn(otherGroup);
        shouldNotBeAdded(isMeCondition.mustBeAdded(descr));
    }

    @Test
    public void testIsMeOtherGroupNotLogged() {
        logout();
        when(descr.getTarget()).thenReturn(otherGroup);
        shouldNotBeAdded(isMeCondition.mustBeAdded(descr));
    }

    @Test
    public void testIsMeOtherUser() {
        login();
        when(descr.getTarget()).thenReturn(otherUser);
        shouldNotBeAdded(isMeCondition.mustBeAdded(descr));
    }

    @Test
    public void testIsMeOtherUserNotLogged() {
        logout();
        when(descr.getTarget()).thenReturn(otherUser);
        shouldNotBeAdded(isMeCondition.mustBeAdded(descr));
    }

    @Test
    public void testIsMeUser() {
        login();
        when(descr.getTarget()).thenReturn(me);
        shouldBeAdded(isMeCondition.mustBeAdded(descr));
    }

    @Test
    public void testIsMeUserNotLogged() {
        logout();
        when(descr.getTarget()).thenReturn(me);
        shouldNotBeAdded(isMeCondition.mustBeAdded(descr));
    }

    @Test
    public void testIsNotMeMeUser() {
        login();
        when(descr.getTarget()).thenReturn(me);
        shouldNotBeAdded(isNotMeCondition.mustBeAdded(descr));
    }

    @Test
    public void testIsNotMeMyGroup() {
        login();
        when(descr.getTarget()).thenReturn(myGroup);
        shouldNotBeAdded(isNotMeCondition.mustBeAdded(descr));
    }

    @Test
    public void testIsNotMeMyGroupNotLogged() {
        logout();
        when(descr.getTarget()).thenReturn(myGroup);
        shouldBeAdded(isNotMeCondition.mustBeAdded(descr));
    }

    @Test
    public void testIsNotMeOtherGroup() {
        login();
        when(descr.getTarget()).thenReturn(otherGroup);
        shouldBeAdded(isNotMeCondition.mustBeAdded(descr));
    }

    @Test
    public void testIsNotMeOtherGroupNotLogged() {
        logout();
        when(descr.getTarget()).thenReturn(otherGroup);
        shouldBeAdded(isNotMeCondition.mustBeAdded(descr));
    }

    @Test
    public void testIsNotMeOtherUser() {
        login();
        when(descr.getTarget()).thenReturn(otherUser);
        shouldBeAdded(isNotMeCondition.mustBeAdded(descr));
    }

    @Test
    public void testIsNotMeOtherUserNotLogged() {
        logout();
        when(descr.getTarget()).thenReturn(otherUser);
        shouldBeAdded(isNotMeCondition.mustBeAdded(descr));
    }

    @Test
    public void testMeUserNotLogged() {
        logout();
        when(descr.getTarget()).thenReturn(me);
        shouldBeAdded(isNotMeCondition.mustBeAdded(descr));
    }
}
