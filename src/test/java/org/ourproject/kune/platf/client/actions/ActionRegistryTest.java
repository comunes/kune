package org.ourproject.kune.platf.client.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.dto.StateToken;

import com.calclab.suco.client.listener.Listener;

public class ActionRegistryTest {

    private static final String DEF_CONTENT_TYPE_ID = "test";
    private ActionRegistry<StateToken> registry;
    private ActionDescriptor<StateToken> adminAction;
    private ActionDescriptor<StateToken> editorAction;
    private ActionDescriptor<StateToken> viewerAction;

    @Test
    public void actionsEmptyButNeverNull() {
	checkActionLists(0, new AccessRightsDTO(true, true, true), true);
	checkActionLists(0, new AccessRightsDTO(true, true, true), false);
	checkActionLists(0, new AccessRightsDTO(false, true, true), true);
	checkActionLists(0, new AccessRightsDTO(false, true, true), false);
	checkActionLists(0, new AccessRightsDTO(false, false, true), true);
	checkActionLists(0, new AccessRightsDTO(false, false, true), false);
    }

    @Before
    public void before() {
	registry = new ActionRegistry<StateToken>();
	adminAction = new ActionDescriptor<StateToken>(AccessRolDTO.Administrator, ActionPosition.topbarAndItemMenu,
		new Listener<StateToken>() {
		    public void onEvent(final StateToken parameter) {
		    }
		});
	editorAction = new ActionDescriptor<StateToken>(AccessRolDTO.Editor, ActionPosition.topbarAndItemMenu,
		new Listener<StateToken>() {
		    public void onEvent(final StateToken parameter) {
		    }
		});

	viewerAction = new ActionDescriptor<StateToken>(AccessRolDTO.Viewer, ActionPosition.topbarAndItemMenu,
		new Listener<StateToken>() {
		    public void onEvent(final StateToken parameter) {
		    }
		});
    }

    @Test
    public void testAddWhenAdmin() {
	addDefActions();
	checkActionLists(3, new AccessRightsDTO(true, true, true), true);
	checkActionLists(3, new AccessRightsDTO(true, true, true), false);
    }

    @Test
    public void testAddWhenEditor() {
	addDefActions();
	checkActionLists(2, new AccessRightsDTO(false, true, true), true);
	checkActionLists(2, new AccessRightsDTO(false, true, true), false);
    }

    @Test
    public void testAddWhenViewer() {
	addDefActions();
	checkActionLists(1, new AccessRightsDTO(false, false, true), true);
	checkActionLists(1, new AccessRightsDTO(false, false, true), false);
    }

    @Test
    public void testEnablingFalse() {
	adminAction.setEnableCondition(new ActionEnableCondition<StateToken>() {
	    public boolean mustBeEnabled(final StateToken param) {
		return false;
	    }
	});
	registry.addAction(adminAction, DEF_CONTENT_TYPE_ID);
	assertTrue(!registry.checkEnabling(adminAction, new StateToken()));
    }

    @Test
    public void testEnablingTrue() {
	adminAction.setEnableCondition(new ActionEnableCondition<StateToken>() {
	    public boolean mustBeEnabled(final StateToken param) {
		return true;
	    }
	});
	registry.addAction(adminAction, DEF_CONTENT_TYPE_ID);
	assertTrue(registry.checkEnabling(adminAction, new StateToken()));
    }

    private void addDefActions() {
	registry.addAction(adminAction, DEF_CONTENT_TYPE_ID);
	registry.addAction(editorAction, DEF_CONTENT_TYPE_ID);
	registry.addAction(viewerAction, DEF_CONTENT_TYPE_ID);
    }

    private void checkActionLists(final int expectedActions, final AccessRightsDTO accessRightsDTO,
	    final boolean toolbarActions) {
	assertEquals(expectedActions, registry.getCurrentActions(new StateToken(), DEF_CONTENT_TYPE_ID,
		accessRightsDTO, toolbarActions).size());
    }
}
