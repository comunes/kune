package org.ourproject.kune.platf.client.ui.rte;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.ActionCollection;
import org.ourproject.kune.platf.client.actions.ActionDescriptor;
import org.ourproject.kune.platf.client.actions.ActionEnableCondition;
import org.ourproject.kune.platf.client.actions.ActionItem;
import org.ourproject.kune.platf.client.actions.ActionItemCollection;
import org.ourproject.kune.platf.client.actions.ActionPressedCondition;
import org.ourproject.kune.platf.client.actions.ActionShortcut;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarPosition;
import org.ourproject.kune.platf.client.actions.ActionToolbarPushButtonDescriptor;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbar;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.platf.client.ui.rte.img.RTEImgResources;

import com.calclab.suco.client.events.Listener0;
import com.google.gwt.libideas.resources.client.ImageResource;

public class RTEditorPresenter implements RTEditor {

    public enum ActionPosition {
        top, snd
    };

    private static final String EDIT_MENU = "Edit";
    private static final String INSERT_MENU = "Insert";
    private RTEditorView view;
    private boolean extended;
    private AccessRolDTO accessRol;
    private final I18nTranslationService i18n;
    private final Session session;
    private final ActionItemCollection<Object> basicTopActions;
    private final ActionItemCollection<Object> basicSndActions;
    private final ActionItemCollection<Object> extendedTopActions;
    private final ActionItemCollection<Object> extendedSndActions;
    private final RTEImgResources imgResources;
    private final RTEActionTopToolbar topBar;
    private final RTEActionSndToolbar sndBar;

    public RTEditorPresenter(I18nTranslationService i18n, Session session, RTEActionTopToolbar topBar,
            RTEActionSndToolbar sndBar, RTEImgResources imgResources) {
        this.i18n = i18n;
        this.session = session;
        this.topBar = topBar;
        this.sndBar = sndBar;
        sndBar.attach();
        this.imgResources = imgResources;
        extended = true;
        accessRol = AccessRolDTO.Editor;
        basicTopActions = new ActionItemCollection<Object>();
        basicSndActions = new ActionItemCollection<Object>();
        extendedTopActions = new ActionItemCollection<Object>();
        extendedSndActions = new ActionItemCollection<Object>();
    }

    public void addAction(ActionDescriptor<Object> action, boolean basic, ActionPosition position) {
        switch (position) {
        case top:
            if (basic) {
                basicTopActions.add(withNoItem(action));
            } else {
                extendedTopActions.add(withNoItem(action));
            }
            break;
        case snd:
            if (basic) {
                basicSndActions.add(withNoItem(action));
            } else {
                extendedSndActions.add(withNoItem(action));
            }
            break;
        }
    }

    public void addActions(ActionCollection<Object> actions, boolean basic, ActionPosition position) {
        switch (position) {
        case top:
            if (basic) {
                basicTopActions.addAll(withNoItem(actions));
            } else {
                extendedTopActions.addAll(withNoItem(actions));
            }
            break;
        case snd:
            if (basic) {
                basicSndActions.addAll(withNoItem(actions));
            } else {
                extendedSndActions.addAll(withNoItem(actions));
            }
            break;
        }
    }

    public void editContent(String content) {
    }

    public ActionToolbar<Object> getSndBar() {
        return sndBar;
    }

    public ActionToolbar<Object> getTopBar() {
        return topBar;
    }

    public View getView() {
        return view;
    }

    public void init(RTEditorView view) {
        this.view = view;
        createDefBasicActions();
        topBar.addActions(basicTopActions);
        sndBar.addActions(basicSndActions);
        view.addActions(basicTopActions);
        view.addActions(basicSndActions);
        if (isExtended()) {
            createDefExtendedActions();
            view.addActions(extendedTopActions);
            view.addActions(extendedSndActions);
            topBar.addActions(extendedTopActions);
            sndBar.addActions(extendedSndActions);
        }
    }

    public void setAccessRol(AccessRolDTO accessRol) {
        this.accessRol = accessRol;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    private void createDefBasicActions() {
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
        bold.setIconCls(getCssName(imgResources.bold()));
        bold.setToolTip(i18n.t("Toggle Bold"));
        bold.setMustInitialyPressed(new ActionPressedCondition<Object>() {
            public boolean mustBePressed(Object param) {
                return view.isBold();
            }
        });
        bold.setShortcut(new ActionShortcut(true, 'B'));

        ActionToolbarPushButtonDescriptor<Object> italic = new ActionToolbarPushButtonDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.toggleItalic();
                    }
                });
        italic.setIconCls(getCssName(imgResources.italic()));
        italic.setToolTip(i18n.t("Toggle Italic"));
        italic.setMustInitialyPressed(new ActionPressedCondition<Object>() {
            public boolean mustBePressed(Object param) {
                return view.isItalic();
            }
        });
        italic.setShortcut(new ActionShortcut(true, 'I'));

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

        basicTopActions.add(withNoItem(selectAll));
        basicSndActions.add(withNoItem(bold));
        basicSndActions.add(withNoItem(italic));
        basicSndActions.add(withNoItem(underline));
    }

    private void createDefExtendedActions() {
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
        comment.setEnableCondition(new ActionEnableCondition<Object>() {
            public boolean mustBeEnabled(Object param) {
                return session.isLogged();
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
        hr.setTextDescription(i18n.t("Horizontal line"));
        hr.setParentMenuTitle(i18n.t(INSERT_MENU));

        ActionToolbarPushButtonDescriptor<Object> strikethrough = new ActionToolbarPushButtonDescriptor<Object>(
                accessRol, ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.toggleStrikethrough();
                    }
                });
        strikethrough.setIconCls(getCssName(imgResources.strikeout()));
        strikethrough.setToolTip(i18n.t("Toggle Strikethrough"));
        strikethrough.setMustInitialyPressed(new ActionPressedCondition<Object>() {
            public boolean mustBePressed(Object param) {
                return view.isStrikethrough();
            }
        });

        extendedTopActions.add(withNoItem(undo));
        extendedTopActions.add(withNoItem(redo));
        extendedTopActions.add(withNoItem(copy));
        extendedTopActions.add(withNoItem(cut));
        extendedTopActions.add(withNoItem(paste));
        extendedTopActions.add(withNoItem(editHtml));
        extendedTopActions.add(withNoItem(hr));
        extendedTopActions.add(withNoItem(comment));
        extendedSndActions.add(withNoItem(strikethrough));
    }

    private String getCssName(ImageResource imageResource) {
        return ".k-rte-" + imageResource.getName();
    }

    private boolean isExtended() {
        return extended && view.canBeExtended();
    }

    private ActionEnableCondition<Object> isInsertHtmlSupported() {
        return new ActionEnableCondition<Object>() {
            public boolean mustBeEnabled(Object param) {
                return true;
            }
        };
    }

    private ActionItemCollection<Object> withNoItem(ActionCollection<Object> actions) {
        ActionItemCollection<Object> collection = new ActionItemCollection<Object>();
        for (ActionDescriptor<Object> action : actions) {
            // this action don't have a associated item
            collection.add(withNoItem(action));
        }
        return collection;
    }

    private ActionItem<Object> withNoItem(ActionDescriptor<Object> action) {
        return new ActionItem<Object>(action, null);
    }
}
