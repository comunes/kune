package org.ourproject.kune.platf.client.ui.rte;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.ActionCollection;
import org.ourproject.kune.platf.client.actions.ActionDescriptor;
import org.ourproject.kune.platf.client.actions.ActionEnableCondition;
import org.ourproject.kune.platf.client.actions.ActionShortcut;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarPosition;
import org.ourproject.kune.platf.client.actions.ContentEditorActionRegistry;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;

public class RTEditorPresenter implements RTEditor {

    private static final String EDIT_MENU = "Edit";
    private static final String INSERT_MENU = "Insert";
    private RTEditorView view;
    private boolean autosave;
    private boolean extended;
    private AccessRolDTO accessRol;
    private final I18nTranslationService i18n;
    private final Session session;

    public RTEditorPresenter(ContentEditorActionRegistry contentEditorActionRegistry, I18nTranslationService i18n,
            Session session) {
        this.i18n = i18n;
        this.session = session;
        createDefActions();
        autosave = false;
        extended = true;
        accessRol = AccessRolDTO.Editor;
    }

    public void addAction(ActionDescriptor<StateToken> action) {
        // TODO Auto-generated method stub
    }

    public void addActions(ActionCollection<StateToken> actions) {
        // TODO Auto-generated method stub
    }

    public void editContent(String content, Listener<String> onSave, Listener0 onEditCancelled) {
        // TODO Auto-generated method stub
    }

    public View getView() {
        return view;
    }

    public void init(RTEditorView view) {
        this.view = view;
    }

    public void onSavedSuccessful() {
        // TODO Auto-generated method stub
    }

    public void onSaveFailed() {
        // TODO Auto-generated method stub
    }

    public void setAccessRol(AccessRolDTO accessRol) {
        this.accessRol = accessRol;
    }

    public void setAutosave(boolean autosave) {
        this.autosave = autosave;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    private void createDefActions() {
        ActionToolbarMenuDescriptor<StateToken> undo = new ActionToolbarMenuDescriptor<StateToken>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.undo();
                    }
                });
        undo.setShortcut(new ActionShortcut(true, 'Z'));
        undo.setTextDescription(i18n.t("Undo"));
        undo.setParentMenuTitle(i18n.t(EDIT_MENU));

        ActionToolbarMenuDescriptor<StateToken> redo = new ActionToolbarMenuDescriptor<StateToken>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.redo();
                    }
                });
        redo.setShortcut(new ActionShortcut(true, 'Y'));
        redo.setTextDescription(i18n.t("Redo"));
        redo.setParentMenuTitle(i18n.t(EDIT_MENU));

        ActionToolbarMenuDescriptor<StateToken> selectAll = new ActionToolbarMenuDescriptor<StateToken>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.selectAll();
                    }
                });
        selectAll.setShortcut(new ActionShortcut(true, 'A'));
        selectAll.setTextDescription(i18n.t("Select all"));
        selectAll.setParentMenuTitle(i18n.t(EDIT_MENU));

        ActionToolbarMenuDescriptor<Object> comment = new ActionToolbarMenuDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.addComment(session.getCurrentUser().getShortName());
                    }
                });
        comment.setShortcut(new ActionShortcut(true, 'M'));
        comment.setTextDescription(i18n.t("Comment..."));
        comment.setParentMenuTitle(i18n.t(INSERT_MENU));
        comment.setEnableCondition(isInsertHtmlSupported());

        ActionToolbarMenuDescriptor<Object> hr = new ActionToolbarMenuDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.insertHR();
                    }
                });
        hr.setShortcut(new ActionShortcut(true, 'M'));
        hr.setTextDescription(i18n.t("Horizontal line"));
        hr.setParentMenuTitle(i18n.t(INSERT_MENU));
        hr.setEnableCondition(isExtended());
    }

    private ActionEnableCondition<Object> isExtended() {
        return new ActionEnableCondition<Object>() {
            public boolean mustBeEnabled(Object param) {
                return extended && view.canBeExtended();
            }
        };
    }

    private ActionEnableCondition<Object> isInsertHtmlSupported() {
        return new ActionEnableCondition<Object>() {
            public boolean mustBeEnabled(Object param) {
                return true;
            }
        };
    }
}
