package cc.kune.core.client.actions;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.gspace.client.actions.perspective.ActionsGroup;
import cc.kune.gspace.client.actions.perspective.EditActionsGroup;
import cc.kune.gspace.client.actions.perspective.ViewActionsGroup;

import com.google.inject.Provider;

public class ActionRegistryByTypeTest {

    private GuiActionDescrip action;
    private ActionRegistryByType actionRegistryByType;
    private AccessRights allRights;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Before
    public void before() {
        actionRegistryByType = new ActionRegistryByType();
        action = Mockito.mock(GuiActionDescrip.class);
        allRights = new AccessRights(true, true, true);
        actionRegistryByType.addAction(new Provider() {

            @Override
            public Object get() {
                return action;
            }
        });
    }

    @Test
    public void testDontGetCurrentActionsOfOtherPerspective() {
        Mockito.when(action.getValue(ActionsGroup.KEY)).thenReturn(ViewActionsGroup.class);
        assertEquals(0, actionRegistryByType.getCurrentActions(null, false, allRights, EditActionsGroup.class).size());
    }

    @Test
    public void testGetCurrentActions() {
        Mockito.when(action.getValue(ActionsGroup.KEY)).thenReturn(null);
        assertEquals(1, actionRegistryByType.getCurrentActions(null, false, allRights, null).size());
    }

    @Test
    public void testGetCurrentActionsOfPerspective() {
        Mockito.when(action.getValue(ActionsGroup.KEY)).thenReturn(EditActionsGroup.class);
        assertEquals(1, actionRegistryByType.getCurrentActions(null, false, allRights, EditActionsGroup.class).size());
    }
}
