package cc.kune.core.client.actions;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.core.shared.domain.utils.AccessRights;

import com.google.inject.Provider;

public class ActionRegistryByTypeBorrarTest {

    private static final String OTHERACTIONGROUP = "otheractiongroup";
    private static final String SOMEACTIONGROUP = "someactiongroup";
    private GuiActionDescrip action;
    private ActionRegistryByType actionRegistryByType;
    private AccessRights allRights;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Before
    public void before() {
        actionRegistryByType = new ActionRegistryByType();
        action = Mockito.mock(GuiActionDescrip.class);
        allRights = new AccessRights(true, true, true);
        actionRegistryByType.addAction(SOMEACTIONGROUP, new Provider() {
            @Override
            public Object get() {
                return action;
            }
        });
    }

    @Test
    public void testDontGetCurrentActionsOfOtherGroup() {
        assertEquals(0, actionRegistryByType.getCurrentActions(null, false, allRights, OTHERACTIONGROUP).size());
    }

    @Test
    public void testGetCurrentActionsOfGroup() {
        assertEquals(1, actionRegistryByType.getCurrentActions(null, false, allRights, SOMEACTIONGROUP).size());
    }
}
