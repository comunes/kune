package org.ourproject.kune.platf.client.ui.rte;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.ActionCollection;
import org.ourproject.kune.platf.client.actions.ActionDescriptor;
import org.ourproject.kune.platf.client.actions.ActionEnableCondition;
import org.ourproject.kune.platf.client.actions.ActionPressedCondition;
import org.ourproject.kune.platf.client.actions.ActionShortcut;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarPosition;
import org.ourproject.kune.platf.client.actions.ActionToolbarPushButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ContentEditorActionRegistry;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.platf.client.ui.rte.img.RTEImgResources;

import com.calclab.suco.client.events.Listener0;

public class RTEditorPresenter implements RTEditor {

    private static final String EDIT_MENU = "Edit";
    private static final String INSERT_MENU = "Insert";
    private RTEditorView view;
    private boolean extended;
    private AccessRolDTO accessRol;
    private final I18nTranslationService i18n;
    private final Session session;
    private final ActionCollection<Object> extendedActions;
    private final ActionCollection<Object> basicActions;
    private final RTEImgResources imgResources;

    public RTEditorPresenter(ContentEditorActionRegistry contentEditorActionRegistry, I18nTranslationService i18n,
            Session session, RTEImgResources imgResources) {
        this.i18n = i18n;
        this.session = session;
        this.imgResources = imgResources;
        extended = true;
        accessRol = AccessRolDTO.Editor;
        extendedActions = createDefExtendedActions();
        basicActions = createDefBasicActions();
    }

    public void addBasicAction(ActionDescriptor<Object> action) {
        basicActions.add(action);
    }

    public void addBasicActions(ActionCollection<Object> actions) {
        basicActions.addAll(actions);
    }

    public void addExtendedAction(ActionDescriptor<Object> action) {
        extendedActions.add(action);
    }

    public void addExtendedActions(ActionCollection<Object> actions) {
        extendedActions.addAll(actions);
    }

    public void editContent(String content) {
        view.setActions(extendedActions);
    }

    public View getView() {
        return view;
    }

    public void init(RTEditorView view) {
        this.view = view;
    }

    public void setAccessRol(AccessRolDTO accessRol) {
        this.accessRol = accessRol;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    private ActionCollection<Object> createDefBasicActions() {
        ActionCollection<Object> actions = new ActionCollection<Object>();

        ActionToolbarMenuDescriptor<Object> selectAll = new ActionToolbarMenuDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.selectAll();
                    }
                });
        selectAll.setShortcut(new ActionShortcut(true, 'A'));
        selectAll.setTextDescription(i18n.t("Select all"));
        selectAll.setParentMenuTitle(i18n.t(EDIT_MENU));

        ActionToolbarPushButtonDescriptor<Object> bold = new ActionToolbarPushButtonDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.toggleBold();
                    }
                });
        bold.setIconCls(imgResources.bold().getName());
        bold.setToolTip(i18n.t("Toggle Bold"));
        bold.setMustInitialyPressed(new ActionPressedCondition<Object>() {
            public boolean mustBePressed(Object param) {
                return view.isBold();
            }
        });

        ActionToolbarPushButtonDescriptor<Object> italic = new ActionToolbarPushButtonDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.toggleItalic();
                    }
                });
        italic.setIconCls(imgResources.italic().getName());
        italic.setToolTip(i18n.t("Toggle Italic"));
        italic.setMustInitialyPressed(new ActionPressedCondition<Object>() {
            public boolean mustBePressed(Object param) {
                return view.isItalic();
            }
        });

        ActionToolbarPushButtonDescriptor<Object> underline = new ActionToolbarPushButtonDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.toggleUnderline();
                    }
                });
        underline.setIconCls(imgResources.underline().getName());
        underline.setToolTip(i18n.t("Toggle Underline"));
        underline.setMustInitialyPressed(new ActionPressedCondition<Object>() {
            public boolean mustBePressed(Object param) {
                return view.isUnderlined();
            }
        });

        actions.add(bold);
        actions.add(italic);
        actions.add(underline);
        actions.add(selectAll);

        return actions;
    }

    private ActionCollection<Object> createDefExtendedActions() {
        ActionCollection<Object> actions = new ActionCollection<Object>();

        ActionToolbarMenuDescriptor<Object> undo = new ActionToolbarMenuDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.undo();
                    }
                });
        undo.setShortcut(new ActionShortcut(true, 'Z'));
        undo.setTextDescription(i18n.t("Undo"));
        undo.setParentMenuTitle(i18n.t(EDIT_MENU));

        ActionToolbarMenuDescriptor<Object> redo = new ActionToolbarMenuDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.redo();
                    }
                });
        redo.setShortcut(new ActionShortcut(true, 'Y'));
        redo.setTextDescription(i18n.t("Redo"));
        redo.setParentMenuTitle(i18n.t(EDIT_MENU));

        ActionToolbarMenuDescriptor<Object> copy = new ActionToolbarMenuDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.copy();
                    }
                });
        copy.setShortcut(new ActionShortcut(true, 'C'));
        copy.setTextDescription(i18n.t("Copy"));
        copy.setParentMenuTitle(i18n.t(EDIT_MENU));

        ActionToolbarMenuDescriptor<Object> cut = new ActionToolbarMenuDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.cut();
                    }
                });
        cut.setShortcut(new ActionShortcut(true, 'X'));
        cut.setTextDescription(i18n.t("Cut"));
        cut.setParentMenuTitle(i18n.t(EDIT_MENU));

        ActionToolbarMenuDescriptor<Object> paste = new ActionToolbarMenuDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.paste();
                    }
                });
        paste.setShortcut(new ActionShortcut(true, 'V'));
        paste.setTextDescription(i18n.t("Paste"));
        paste.setParentMenuTitle(i18n.t(EDIT_MENU));

        ActionToolbarMenuDescriptor<Object> editHtml = new ActionToolbarMenuDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        NotifyUser.info("In dev");
                    }
                });
        editHtml.setTextDescription(i18n.t("Edit HTML"));
        editHtml.setParentMenuTitle(i18n.t(EDIT_MENU));

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
                        view.insertHorizontalRule();
                    }
                });
        hr.setShortcut(new ActionShortcut(true, 'M'));
        hr.setTextDescription(i18n.t("Horizontal line"));
        hr.setParentMenuTitle(i18n.t(INSERT_MENU));
        hr.setEnableCondition(isExtended());

        ActionToolbarPushButtonDescriptor<Object> strikethrough = new ActionToolbarPushButtonDescriptor<Object>(
                accessRol, ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.toggleStrikethrough();
                    }
                });
        strikethrough.setIconCls(imgResources.strikeout().getName());
        strikethrough.setToolTip(i18n.t("Toggle Strikethrough"));
        strikethrough.setMustInitialyPressed(new ActionPressedCondition<Object>() {
            public boolean mustBePressed(Object param) {
                return view.isStrikethrough();
            }
        });

        actions.add(undo);
        actions.add(redo);
        actions.add(copy);
        actions.add(cut);
        actions.add(paste);
        actions.add(editHtml);
        actions.add(comment);
        actions.add(hr);
        actions.add(strikethrough);

        return actions;
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
