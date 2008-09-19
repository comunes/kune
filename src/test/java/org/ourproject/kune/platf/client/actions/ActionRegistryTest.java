package org.ourproject.kune.platf.client.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.dto.StateToken;

import com.calclab.suco.client.signal.Slot;

public class ActionRegistryTest {

    private static final String DEF_CONTENT_TYPE_ID = "test";
    private ActionRegistry<StateToken> registry;
    private ActionDescriptor<StateToken> adminAction;
    private ActionDescriptor<StateToken> editorAction;
    private ActionDescriptor<StateToken> viewerAction;

    @Before
    public void before() {
	registry = new ActionRegistry<StateToken>();
	adminAction = new ActionDescriptor<StateToken>(AccessRolDTO.Administrator, ActionPosition.topbarAndItemMenu,
		new Slot<StateToken>() {
		    public void onEvent(final StateToken parameter) {
		    }
		});
	editorAction = new ActionDescriptor<StateToken>(AccessRolDTO.Editor, ActionPosition.topbarAndItemMenu,
		new Slot<StateToken>() {
		    public void onEvent(final StateToken parameter) {
		    }
		});

	viewerAction = new ActionDescriptor<StateToken>(AccessRolDTO.Viewer, ActionPosition.topbarAndItemMenu,
		new Slot<StateToken>() {
		    public void onEvent(final StateToken parameter) {
		    }
		});
    }

    @Test
    public void testAddWhenAdmin() {
	addDefActions();
	checkActionLists(3, 3, new AccessRightsDTO(true, true, true));
    }

    @Test
    public void testAddWhenEditor() {
	addDefActions();
	checkActionLists(2, 2, new AccessRightsDTO(false, true, true));
    }

    @Test
    public void testAddWhenViewer() {
	addDefActions();
	checkActionLists(1, 1, new AccessRightsDTO(false, false, true));
    }

    @Test
    public void testEnablingFalse() {
	adminAction.setEnableCondition(new ActionEnableCondition<StateToken>() {
	    public boolean mustBeEnabled(final StateToken param) {
		return false;
	    }
	});
	registry.addAction(DEF_CONTENT_TYPE_ID, adminAction);
	assertTrue(!registry.checkEnabling(adminAction, new StateToken()));
    }

    @Test
    public void testEnablingTrue() {
	adminAction.setEnableCondition(new ActionEnableCondition<StateToken>() {
	    public boolean mustBeEnabled(final StateToken param) {
		return true;
	    }
	});
	registry.addAction(DEF_CONTENT_TYPE_ID, adminAction);
	assertTrue(registry.checkEnabling(adminAction, new StateToken()));
    }

    private void addDefActions() {
	registry.addAction(DEF_CONTENT_TYPE_ID, adminAction);
	registry.addAction(DEF_CONTENT_TYPE_ID, editorAction);
	registry.addAction(DEF_CONTENT_TYPE_ID, viewerAction);
    }

    private void checkActionLists(final int expectedToolActions, final int expectedItemActions,
	    final AccessRightsDTO accessRightsDTO) {
	assertEquals(expectedItemActions, registry.selectCurrentActions(accessRightsDTO, DEF_CONTENT_TYPE_ID)
		.getToolbarActions().size());
	assertEquals(expectedItemActions, registry.selectCurrentActions(accessRightsDTO, DEF_CONTENT_TYPE_ID)
		.getItemActions().size());
    }
}
