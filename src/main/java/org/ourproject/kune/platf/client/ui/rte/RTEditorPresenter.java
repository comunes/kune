package org.ourproject.kune.platf.client.ui.rte;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.ActionAddCondition;
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
    private static final String FORMAT_MENU = "Format";
    private RTEditorView view;
    private boolean extended;
    private final AccessRolDTO accessRol;
    private final I18nTranslationService i18n;
    private final Session session;
    private final ActionItemCollection<Object> topActions;
    private final ActionItemCollection<Object> sndActions;
    private final RTEImgResources imgResources;
    private final RTEActionTopToolbar topBar;
    private final RTEActionSndToolbar sndBar;
    private ActionToolbarPushButtonDescriptor<Object> bold;
    private ActionToolbarPushButtonDescriptor<Object> italic;
    private ActionToolbarPushButtonDescriptor<Object> underline;
    private ActionToolbarPushButtonDescriptor<Object> strikethrough;
    private final Event0 onEdit;
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
        topActions = new ActionItemCollection<Object>();
        sndActions = new ActionItemCollection<Object>();
        this.onEdit = new Event0("onRTEEdit");
    }

    public void addAction(ActionDescriptor<Object> action, ActionPosition position) {
        switch (position) {
        case top:
            topActions.add(withNoItem(action));
            break;
        case snd:
            sndActions.add(withNoItem(action));
            break;
        }
    }

    public void addActions(ActionCollection<Object> actions, ActionPosition position) {
        switch (position) {
        case top:
            topActions.addAll(withNoItem(actions));
            break;
        case snd:
            sndActions.addAll(withNoItem(actions));
            break;
        }
    }

    public void addOnEditListener(final Listener0 listener) {
        onEdit.add(listener);
    }

    public void adjustSize(final int height) {
        view.adjustSize(height);
    }

    public void attach() {
        topBar.clear();
        sndBar.clear();
        view.addActions(topActions);
        view.addActions(sndActions);
        topBar.addActions(topActions);
        sndBar.addActions(sndActions);
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
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    public void updateStatus() {
        if (view.canBeBasic()) {
            sndBar.setPushButtonPressed(bold, view.isBold());
            sndBar.setPushButtonPressed(italic, view.isItalic());
            sndBar.setPushButtonPressed(underline, view.isUnderlined());
        }
        if (isExtended()) {
            sndBar.setPushButtonPressed(strikethrough, view.isStrikethrough());
        }
    }

    private void createBasicActions() {
        ActionAddCondition<Object> canBeBasic = new ActionAddCondition<Object>() {
            public boolean mustBeAdded(Object param) {
                return view.canBeBasic();
            }
        };
        ActionAddCondition<Object> canBeExtended = new ActionAddCondition<Object>() {
            public boolean mustBeAdded(Object param) {
                return isExtended();
            }
        };

        ActionToolbarMenuDescriptor<Object> selectAll = new ActionToolbarMenuDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.selectAll();
                    }
                });
        selectAll.setShortcut(new ActionShortcut(true, 'A'));
        selectAll.setTextDescription(i18n.t("Select all"));
        selectAll.setParentMenuTitle(i18n.t(EDIT_MENU));
        selectAll.setAddCondition(canBeBasic);

        bold = new ActionToolbarPushButtonDescriptor<Object>(accessRol, ActionToolbarPosition.topbar, new Listener0() {
            public void onEvent() {
                view.toggleBold();
            }
        });
        bold.setIconCls(getCssName(imgResources.bold()));
        bold.setToolTip(i18n.t("Bold"));
        bold.setShortcut(new ActionShortcut(true, 'B'));
        bold.setAddCondition(canBeBasic);

        italic = new ActionToolbarPushButtonDescriptor<Object>(accessRol, ActionToolbarPosition.topbar,
                new Listener0() {
                    public void onEvent() {
                        view.toggleItalic();
                    }
                });
        italic.setIconCls(getCssName(imgResources.italic()));
        italic.setToolTip(i18n.t("Italic"));
        italic.setShortcut(new ActionShortcut(true, 'I'));
        italic.setAddCondition(canBeBasic);
        italic.setAddCondition(canBeBasic);

        underline = new ActionToolbarPushButtonDescriptor<Object>(accessRol, ActionToolbarPosition.topbar,
                new Listener0() {
                    public void onEvent() {
                        view.toggleUnderline();
                    }
                });
        underline.setIconCls(getCssName(imgResources.underline()));
        underline.setToolTip(i18n.t("Underline"));
        underline.setShortcut(new ActionShortcut(true, 'U'));
        underline.setAddCondition(canBeBasic);

        ActionToolbarMenuDescriptor<Object> subscript = new ActionToolbarMenuDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.toggleSubscript();
                    }
                });
        subscript.setParentMenuTitle(i18n.t(FORMAT_MENU));
        subscript.setTextDescription(i18n.t("Subscript"));
        subscript.setIconCls(getCssName(imgResources.subscript()));
        subscript.setShortcut(new ActionShortcut(true, ','));
        subscript.setAddCondition(canBeBasic);

        ActionToolbarMenuDescriptor<Object> superscript = new ActionToolbarMenuDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.toggleSuperscript();
                    }
                });
        superscript.setParentMenuTitle(i18n.t(FORMAT_MENU));
        superscript.setTextDescription(i18n.t("Superscript"));
        superscript.setIconCls(getCssName(imgResources.superscript()));
        superscript.setShortcut(new ActionShortcut(true, '.'));
        superscript.setAddCondition(canBeBasic);

        ActionToolbarButtonDescriptor<Object> justifyLeft = new ActionToolbarButtonDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.justifyLeft();
                    }
                });
        justifyLeft.setIconCls(getCssName(imgResources.alignleft()));
        justifyLeft.setToolTip(i18n.t("Left Justify"));
        justifyLeft.setShortcut(new ActionShortcut(true, 'L'));
        justifyLeft.setAddCondition(canBeBasic);

        ActionToolbarButtonDescriptor<Object> justifyCentre = new ActionToolbarButtonDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.justifyCenter();
                    }
                });
        justifyCentre.setIconCls(getCssName(imgResources.centerpara()));
        justifyCentre.setToolTip(i18n.t("Centre Justify"));
        justifyCentre.setShortcut(new ActionShortcut(true, 'E'));
        justifyCentre.setAddCondition(canBeBasic);

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
        justifyRight.setAddCondition(canBeBasic);

        ActionToolbarMenuDescriptor<Object> undo = new ActionToolbarMenuDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.undo();
                    }
                });
        undo.setShortcut(new ActionShortcut(true, 'Z'));
        undo.setTextDescription(i18n.t("Undo"));
        undo.setParentMenuTitle(i18n.t(EDIT_MENU));
        undo.setAddCondition(canBeExtended);

        ActionToolbarMenuDescriptor<Object> redo = new ActionToolbarMenuDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.redo();
                    }
                });
        redo.setShortcut(new ActionShortcut(true, 'Y'));
        redo.setTextDescription(i18n.t("Redo"));
        redo.setParentMenuTitle(i18n.t(EDIT_MENU));
        redo.setAddCondition(canBeExtended);

        ActionToolbarMenuDescriptor<Object> copy = new ActionToolbarMenuDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.copy();
                    }
                });
        copy.setShortcut(new ActionShortcut(true, 'C'));
        copy.setTextDescription(i18n.t("Copy"));
        copy.setParentMenuTitle(i18n.t(EDIT_MENU));
        copy.setAddCondition(canBeExtended);

        ActionToolbarMenuDescriptor<Object> cut = new ActionToolbarMenuDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.cut();
                    }
                });
        cut.setShortcut(new ActionShortcut(true, 'X'));
        cut.setTextDescription(i18n.t("Cut"));
        cut.setParentMenuTitle(i18n.t(EDIT_MENU));
        cut.setAddCondition(canBeExtended);

        ActionToolbarMenuDescriptor<Object> paste = new ActionToolbarMenuDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.paste();
                    }
                });
        paste.setShortcut(new ActionShortcut(true, 'V'));
        paste.setTextDescription(i18n.t("Paste"));
        paste.setParentMenuTitle(i18n.t(EDIT_MENU));
        paste.setAddCondition(canBeExtended);

        ActionToolbarMenuDescriptor<Object> editHtml = new ActionToolbarMenuDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        NotifyUser.info("In dev");
                    }
                });
        editHtml.setTextDescription(i18n.t("Edit HTML"));
        editHtml.setParentMenuTitle(i18n.t(EDIT_MENU));
        editHtml.setAddCondition(canBeExtended);

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
        comment.setAddCondition(canBeExtended);

        ActionToolbarMenuDescriptor<Object> hr = new ActionToolbarMenuDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.insertHorizontalRule();
                    }
                });
        hr.setTextDescription(i18n.t("Horizontal line"));
        hr.setIconCls(getCssName(imgResources.hfixedline()));
        hr.setParentMenuTitle(i18n.t(INSERT_MENU));
        hr.setAddCondition(canBeExtended);

        ActionToolbarButtonDescriptor<Object> hrButton = new ActionToolbarButtonDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.insertHorizontalRule();
                    }
                });
        hrButton.setIconCls(getCssName(imgResources.hfixedline()));
        hrButton.setToolTip(i18n.t("Horizontal line"));
        hrButton.setShortcut(new ActionShortcut(true, true, ' ', "Space"));
        hrButton.setAddCondition(canBeExtended);

        strikethrough = new ActionToolbarPushButtonDescriptor<Object>(accessRol, ActionToolbarPosition.topbar,
                new Listener0() {
                    public void onEvent() {
                        view.toggleStrikethrough();
                    }
                });
        strikethrough.setIconCls(getCssName(imgResources.strikeout()));
        strikethrough.setToolTip(i18n.t("Strikethrough"));
        strikethrough.setRightSeparator(ActionToolbarButtonSeparator.separator);
        strikethrough.setAddCondition(canBeExtended);

        ActionToolbarButtonDescriptor<Object> decreaseIndent = new ActionToolbarButtonDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.leftIndent();
                    }
                });
        decreaseIndent.setIconCls(getCssName(imgResources.decrementindent()));
        decreaseIndent.setToolTip(i18n.t("Decrease Indent"));
        decreaseIndent.setAddCondition(canBeExtended);

        ActionToolbarButtonDescriptor<Object> increaseIndent = new ActionToolbarButtonDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.rightIndent();
                    }
                });
        increaseIndent.setIconCls(getCssName(imgResources.incrementindent()));
        increaseIndent.setToolTip(i18n.t("Increase Indent"));
        increaseIndent.setRightSeparator(ActionToolbarButtonSeparator.spacer);
        increaseIndent.setAddCondition(canBeExtended);

        ActionToolbarButtonDescriptor<Object> ol = new ActionToolbarButtonDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.insertOrderedList();
                    }
                });
        ol.setIconCls(getCssName(imgResources.defaultnumbering()));
        ol.setToolTip(i18n.t("Numbered List"));
        ol.setShortcut(new ActionShortcut(true, '7'));
        ol.setAddCondition(canBeExtended);

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
        ul.setAddCondition(canBeExtended);

        ActionToolbarButtonDescriptor<Object> img = new ActionToolbarButtonDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        NotifyUser.info(TextUtils.IN_DEVELOPMENT);
                    }
                });
        img.setIconCls(getCssName(imgResources.images()));
        img.setToolTip(i18n.t("Insert Image"));
        img.setAddCondition(canBeExtended);

        ActionToolbarButtonDescriptor<Object> createLink = new ActionToolbarButtonDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        insertElement.show();
                    }
                });
        createLink.setIconCls(getCssName(imgResources.link()));
        createLink.setToolTip(i18n.t("Create Link"));
        createLink.setShortcut(new ActionShortcut(true, 'K'));
        createLink.setAddCondition(canBeExtended);
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
        removeLink.setAddCondition(canBeExtended);

        ActionToolbarButtonDescriptor<Object> removeFormat = new ActionToolbarButtonDescriptor<Object>(accessRol,
                ActionToolbarPosition.topbar, new Listener0() {
                    public void onEvent() {
                        view.removeFormat();
                    }
                });
        removeFormat.setIconCls(getCssName(imgResources.removeFormat()));
        removeFormat.setToolTip(i18n.t("Clear Formatting"));
        removeFormat.setShortcut(new ActionShortcut(true, ' ', "Space"));
        removeFormat.setAddCondition(canBeExtended);

        topActions.add(withNoItem(selectAll));
        sndActions.add(withNoItem(bold));
        sndActions.add(withNoItem(italic));
        sndActions.add(withNoItem(underline));
        sndActions.add(withNoItem(strikethrough));
        sndActions.add(withNoItem(justifyLeft));
        sndActions.add(withNoItem(justifyCentre));
        sndActions.add(withNoItem(justifyRight));

        topActions.add(withNoItem(undo));
        topActions.add(withNoItem(redo));
        topActions.add(withNoItem(copy));
        topActions.add(withNoItem(cut));
        topActions.add(withNoItem(paste));
        topActions.add(withNoItem(editHtml));
        topActions.add(withNoItem(hr));
        topActions.add(withNoItem(subscript));
        topActions.add(withNoItem(superscript));
        sndActions.add(withNoItem(hrButton));
        sndActions.add(withNoItem(decreaseIndent));
        sndActions.add(withNoItem(increaseIndent));
        sndActions.add(withNoItem(ol));
        sndActions.add(withNoItem(ul));
        sndActions.add(withNoItem(img));
        sndActions.add(withNoItem(createLink));
        sndActions.add(withNoItem(removeLink));
        sndActions.add(withNoItem(removeFormat));
        topActions.add(withNoItem(comment));
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
