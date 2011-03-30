package cc.kune.core.client.actions;

import java.util.HashMap;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescProviderCollection;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.core.shared.domain.utils.AccessRights;

import com.google.inject.Provider;

/**
 * A registry of actions by content type (doc, post, etc)
 * 
 */
public class ActionRegistryByType {
    private static final String GENERIC = "kgenacts";

    private final HashMap<String, GuiActionDescProviderCollection> actions;

    public ActionRegistryByType() {
        actions = new HashMap<String, GuiActionDescProviderCollection>();
    }

    private void add(final GuiActionDescCollection collection, final GuiActionDescrip descrip, final Object targetItem) {
        descrip.setTarget(targetItem);
        collection.add(descrip);
    }

    public void addAction(final Provider<GuiActionDescrip> action) {
        addAction(action, GENERIC);
    }

    public void addAction(final Provider<GuiActionDescrip> action, final String... typeIds) {
        assert action != null;
        for (final String typeId : typeIds) {
            final GuiActionDescProviderCollection actionColl = getActions(typeId);
            actionColl.add(action);
        }
    }

    private GuiActionDescProviderCollection getActions(final String typeId) {
        GuiActionDescProviderCollection actionColl = actions.get(typeId);
        if (actionColl == null) {
            actionColl = new GuiActionDescProviderCollection();
            actions.put(typeId, actionColl);
        }
        return actionColl;
    }

    public GuiActionDescCollection getCurrentActions(final Object targetItem, final String typeId,
            final boolean isLogged, final AccessRights rights) {
        final GuiActionDescCollection collection = new GuiActionDescCollection();

        for (final Provider<GuiActionDescrip> descripProv : getActions(typeId)) {
            final GuiActionDescrip descrip = descripProv.get();
            final AbstractAction action = descrip.getAction();
            if (action instanceof RolAction) {
                if (mustAdd((RolAction) action, isLogged, rights)) {
                    add(collection, descrip, targetItem);
                }
            } else {
                add(collection, descrip, targetItem);
            }
        }
        return collection;
    }

    private boolean mustAdd(final RolAction action, final boolean isLogged, final AccessRights rights) {
        if (action.isAuthNeed()) {
            if (!isLogged) {
                return false;
            }
        }
        switch (action.getRolRequired()) {
        case Administrator:
            return rights.isAdministrable();
        case Editor:
            return rights.isEditable();
        case Viewer:
        default:
            return rights.isVisible();
        }
    }
}
