package cc.kune.core.client.actions;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.gspace.client.actions.perspective.ActionPerspective;
import cc.kune.gspace.client.actions.perspective.EditPerspective;
import cc.kune.gspace.client.actions.perspective.ViewPerspective;

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
        Mockito.when(action.getValue(ActionPerspective.KEY)).thenReturn(ViewPerspective.class);
        assertEquals(0, actionRegistryByType.getCurrentActions(null, false, allRights, EditPerspective.class).size());
    }

    @Test
    public void testGetCurrentActions() {
        Mockito.when(action.getValue(ActionPerspective.KEY)).thenReturn(null);
        assertEquals(1, actionRegistryByType.getCurrentActions(null, false, allRights, null).size());
    }

    @Test
    public void testGetCurrentActionsOfPerspective() {
        Mockito.when(action.getValue(ActionPerspective.KEY)).thenReturn(EditPerspective.class);
        assertEquals(1, actionRegistryByType.getCurrentActions(null, false, allRights, EditPerspective.class).size());
    }
}
