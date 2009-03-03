package org.ourproject.kune.platf.client.ui.rte;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.ActionCollection;
import org.ourproject.kune.platf.client.actions.ActionDescriptor;
import org.ourproject.kune.platf.client.actions.ActionEnableCondition;
import org.ourproject.kune.platf.client.actions.ActionItem;
import org.ourproject.kune.platf.client.actions.ActionItemCollection;
import org.ourproject.kune.platf.client.actions.ActionShortcut;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonSeparator;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarPosition;
import org.ourproject.kune.platf.client.actions.ActionToolbarPushButtonDescriptor;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbar;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbarPanel;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.ui.SimpleToolbar;
import org.ourproject.kune.platf.client.ui.TextUtils;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.platf.client.ui.rte.img.RTEImgResources;
import org.ourproject.kune.workspace.client.editor.insert.TextEditorInsertElement;

import com.calclab.suco.client.events.Event0;
import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.events.Listener2;
import com.google.gwt.libideas.resources.client.ImageResource;

public class RTEditorPresenter implements RTEditor {

    public enum ActionPosition {
        top, snd
    };

    private static final String EDIT_MENU = "Edit";
    private static final String INSERT_MENU = "Insert";
    private RTEditorView view;
    private boolean extended;
    private final AccessRolDTO accessRol;
    private final I18nTranslationService i18n;
    private final Session session;
    private final ActionItemCollection<Object> basicTopActions;
    private final ActionItemCollection<Object> basicSndActions;
    private final ActionItemCollection<Object> extendedTopActions;
    private final ActionItemCollection<Object> extendedSndActions;
    private final RTEImgResources imgResources;
    private final RTEActionTopToolbar topBar;
    private final RTEActionSndToolbar sndBar;
    private ActionToolbarPushButtonDescriptor<Object> bold;
    private ActionToolbarPushButtonDescriptor<Object> italic;
    private ActionToolbarPushButtonDescriptor<Object> underline;
    private ActionToolbarPushButtonDescriptor<Object> strikethrough;
    private final Event0 onEdit;
    private ActionToolbarPushButtonDescriptor<Object> subscript;
    private ActionToolbarPushButtonDescriptor<Object> superscript;
    private final TextEditorInsertElement insertElement;

    public RTEditorPresenter(I18nTranslationService i18n, Session session, RTEActionTopToolbar topBar,
            RTEActionSndToolbar sndBar, RTEImgResources imgResources, TextEditorInsertElement textEditorInsertElement) {
        this.i18n = i18n;
        this.session = session;
        this.topBar = topBar;
        this.sndBar = sndBar;
        this.insertElement = textEditorInsertElement;
        styleToolbar(topBar);
        styleToolbar(sndBar);
        sndBar.attach();
        this.imgResources = imgResources;
        extended = true;
        accessRol = AccessRolDTO.Editor;
        basicTopActions = new ActionItemCollection<Object>();
        basicSndActions = new ActionItemCollection<Object>();
        extendedTopActions = new ActionItemCollection<Object>();
        extendedSndActions = new ActionItemCollection<Object>();
        this.onEdit = new Event0("onRTEEdit");
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

    public void addOnEditListener(final Listener0 listener) {
        onEdit.add(listener);
    }

    public void editContent(String content) {
    }

    public void fireOnEdit() {
        onEdit.fire();
    }

    public View getEditorArea() {
        return view;
    }

    public ActionToolbar<Object> getSndBar() {
        return sndBar;
    }

    public ActionToolbar<Object> getTopBar() {
        return topBar;
    }

    public void init(RTEditorView view) {
        this.view = view;
        createBasicActions();
        if (view.canBeBasic()) {
            view.addActions(basicTopActions);
            view.addActions(basicSndActions);
            topBar.addActions(basicTopActions);
            sndBar.addActions(basicSndActions);
        }
        if (isExtended()) {
            view.addActions(extendedTopActions);
            view.addActions(extendedSndActions);
            topBar.addActions(extendedTopActions);
            sndBar.addActions(extendedSndActions);
        }
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    public void updateStatus() {
        if (view.canBeBasic()) {
            sndBar.setPushButtonPressed(bold, view.isBold());
            sndBar.setPushButtonPressed(italic, view.isItalic());
            sndBar.setPushButtonPressed(underline, view.isUnderlined());
            sndBar.setPushButtonPressed(subscript, view.isSubscript());
            sndBar.setPushButtonPressed(superscript, view.isSuperscript());
        }
        if (isExtended()) {
            sndBar.setPushButtonPressed(strikethrough, view.isStrikethrough());
        }
    }

    private void createBasicActions() {
        ActionToolbarMenuDescriptor<Object> selectAll = new ActionToolbarMenuDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.selectAll();
                    }
                });
        selectAll.setShortcut(new ActionShortcut(true, 'A'));
        selectAll.setTextDescription(i18n.t("Select all"));
        selectAll.setParentMenuTitle(i18n.t(EDIT_MENU));

        bold = new ActionToolbarPushButtonDescriptor<Object>(accessRol, ActionToolbarPosition.topbar, new Listener0() {
            public void onEvent() {
                view.toggleBold();
            }
        });
        bold.setIconCls(getCssName(imgResources.bold()));
        bold.setToolTip(i18n.t("Toggle Bold"));
        bold.setShortcut(new ActionShortcut(true, 'B'));

        italic = new ActionToolbarPushButtonDescriptor<Object>(accessRol, ActionToolbarPosition.topbar,
                new Listener0() {
                    public void onEvent() {
                        view.toggleItalic();
                    }
                });
        italic.setIconCls(getCssName(imgResources.italic()));
        italic.setToolTip(i18n.t("Toggle Italic"));
        italic.setShortcut(new ActionShortcut(true, 'I'));

        underline = new ActionToolbarPushButtonDescriptor<Object>(accessRol, ActionToolbarPosition.topbar,
                new Listener0() {
                    public void onEvent() {
                        view.toggleUnderline();
                    }
                });
        underline.setIconCls(getCssName(imgResources.underline()));
        underline.setToolTip(i18n.t("Toggle Underline"));
        underline.setShortcut(new ActionShortcut(true, 'U'));

        subscript = new ActionToolbarPushButtonDescriptor<Object>(accessRol, ActionToolbarPosition.topbar,
                new Listener0() {
                    public void onEvent() {
                        view.toggleSubscript();
                    }
                });
        subscript.setIconCls(getCssName(imgResources.superscript()));
        subscript.setToolTip(i18n.t("Toggle Subscript"));
        subscript.setShortcut(new ActionShortcut(true, ','));

        superscript = new ActionToolbarPushButtonDescriptor<Object>(accessRol, ActionToolbarPosition.topbar,
                new Listener0() {
                    public void onEvent() {
                        view.toggleSuperscript();
                    }
                });
        superscript.setIconCls(getCssName(imgResources.superscript()));
        superscript.setToolTip(i18n.t("Toggle Superscript"));
        superscript.setRightSeparator(ActionToolbarButtonSeparator.spacer);
        superscript.setShortcut(new ActionShortcut(true, '.'));

        ActionToolbarButtonDescriptor<Object> justifyLeft = new ActionToolbarButtonDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.justifyLeft();
                    }
                });
        justifyLeft.setIconCls(getCssName(imgResources.alignleft()));
        justifyLeft.setToolTip(i18n.t("Left Justify"));
        justifyLeft.setShortcut(new ActionShortcut(true, 'L'));

        ActionToolbarButtonDescriptor<Object> justifyCentre = new ActionToolbarButtonDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.justifyCenter();
                    }
                });
        justifyCentre.setIconCls(getCssName(imgResources.centerpara()));
        justifyCentre.setToolTip(i18n.t("Centre Justify"));
        justifyCentre.setShortcut(new ActionShortcut(true, 'E'));

        ActionToolbarButtonDescriptor<Object> justifyRight = new ActionToolbarButtonDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.justifyRight();
                    }
                });
        justifyRight.setIconCls(getCssName(imgResources.alignright()));
        justifyRight.setToolTip(i18n.t("Right Justify"));
        justifyRight.setShortcut(new ActionShortcut(true, 'R'));
        justifyRight.setRightSeparator(ActionToolbarButtonSeparator.spacer);

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
        hr.setIconCls(getCssName(imgResources.hfixedline()));
        hr.setParentMenuTitle(i18n.t(INSERT_MENU));

        ActionToolbarButtonDescriptor<Object> hrButton = new ActionToolbarButtonDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.insertHorizontalRule();
                    }
                });
        hrButton.setIconCls(getCssName(imgResources.hfixedline()));
        hrButton.setToolTip(i18n.t("Horizontal line"));
        hrButton.setShortcut(new ActionShortcut(true, true, ' ', "Space"));

        strikethrough = new ActionToolbarPushButtonDescriptor<Object>(accessRol, ActionToolbarPosition.topbar,
                new Listener0() {
                    public void onEvent() {
                        view.toggleStrikethrough();
                    }
                });
        strikethrough.setIconCls(getCssName(imgResources.strikeout()));
        strikethrough.setToolTip(i18n.t("Toggle Strikethrough"));
        strikethrough.setRightSeparator(ActionToolbarButtonSeparator.spacer);

        ActionToolbarButtonDescriptor<Object> decreaseIndent = new ActionToolbarButtonDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.leftIndent();
                    }
                });
        decreaseIndent.setIconCls(getCssName(imgResources.decrementindent()));
        decreaseIndent.setToolTip(i18n.t("Decrease Indent"));

        ActionToolbarButtonDescriptor<Object> increaseIndent = new ActionToolbarButtonDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.rightIndent();
                    }
                });
        increaseIndent.setIconCls(getCssName(imgResources.incrementindent()));
        increaseIndent.setToolTip(i18n.t("Increase Indent"));
        increaseIndent.setRightSeparator(ActionToolbarButtonSeparator.spacer);

        ActionToolbarButtonDescriptor<Object> ol = new ActionToolbarButtonDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.insertOrderedList();
                    }
                });
        ol.setIconCls(getCssName(imgResources.defaultnumbering()));
        ol.setToolTip(i18n.t("Numbered List"));
        ol.setShortcut(new ActionShortcut(true, '7'));

        ActionToolbarButtonDescriptor<Object> ul = new ActionToolbarButtonDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.insertUnorderedList();
                    }
                });
        ul.setIconCls(getCssName(imgResources.defaultbullet()));
        ul.setToolTip(i18n.t("Bullet List"));
        ul.setShortcut(new ActionShortcut(true, '8'));
        ul.setRightSeparator(ActionToolbarButtonSeparator.spacer);

        ActionToolbarButtonDescriptor<Object> img = new ActionToolbarButtonDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        NotifyUser.info(TextUtils.IN_DEVELOPMENT);
                    }
                });
        img.setIconCls(getCssName(imgResources.images()));
        img.setToolTip(i18n.t("Insert Image"));

        ActionToolbarButtonDescriptor<Object> createLink = new ActionToolbarButtonDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        insertElement.show();
                    }
                });
        createLink.setIconCls(getCssName(imgResources.link()));
        createLink.setToolTip(i18n.t("Create Link"));
        createLink.setShortcut(new ActionShortcut(true, 'K'));
        insertElement.addOnCreateLink(new Listener2<String, String>() {
            public void onEvent(String name, String url) {
                view.createLink(url);
            }
        });

        ActionToolbarButtonDescriptor<Object> removeLink = new ActionToolbarButtonDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.unlink();
                    }
                });
        removeLink.setIconCls(getCssName(imgResources.linkbreak()));
        removeLink.setToolTip(i18n.t("Remove Link"));
        removeLink.setShortcut(new ActionShortcut(true, true, 'K'));

        ActionToolbarButtonDescriptor<Object> removeFormat = new ActionToolbarButtonDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.removeFormat();
                    }
                });
        removeFormat.setIconCls(getCssName(imgResources.removeFormat()));
        removeFormat.setToolTip(i18n.t("Clear Formatting"));
        removeFormat.setShortcut(new ActionShortcut(true, ' ', "Space"));

        basicTopActions.add(withNoItem(selectAll));
        basicSndActions.add(withNoItem(bold));
        basicSndActions.add(withNoItem(italic));
        basicSndActions.add(withNoItem(underline));
        extendedSndActions.add(withNoItem(strikethrough));
        basicSndActions.add(withNoItem(subscript));
        basicSndActions.add(withNoItem(superscript));
        basicSndActions.add(withNoItem(justifyLeft));
        basicSndActions.add(withNoItem(justifyCentre));
        basicSndActions.add(withNoItem(justifyRight));

        extendedTopActions.add(withNoItem(undo));
        extendedTopActions.add(withNoItem(redo));
        extendedTopActions.add(withNoItem(copy));
        extendedTopActions.add(withNoItem(cut));
        extendedTopActions.add(withNoItem(paste));
        extendedTopActions.add(withNoItem(editHtml));
        extendedTopActions.add(withNoItem(hr));
        extendedSndActions.add(withNoItem(hrButton));
        extendedSndActions.add(withNoItem(decreaseIndent));
        extendedSndActions.add(withNoItem(increaseIndent));
        extendedSndActions.add(withNoItem(ol));
        extendedSndActions.add(withNoItem(ul));
        extendedSndActions.add(withNoItem(img));
        extendedSndActions.add(withNoItem(createLink));
        extendedSndActions.add(withNoItem(removeLink));
        extendedSndActions.add(withNoItem(removeFormat));
        extendedTopActions.add(withNoItem(comment));
    }

    private String getCssName(ImageResource imageResource) {
        return "k-rte-" + imageResource.getName();
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

    private void styleToolbar(ActionToolbar<Object> topBar) {
        SimpleToolbar toolbar = ((ActionToolbarPanel<Object>) topBar.getView()).getToolbar(ActionToolbarPosition.topbar);
        toolbar.setStyleName("x-toolbar");
        toolbar.addStyleName("x-panel");
        toolbar.addStyleName("k-toolbar-bottom-line");
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
