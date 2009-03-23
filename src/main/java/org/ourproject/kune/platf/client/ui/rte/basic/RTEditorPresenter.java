package org.ourproject.kune.platf.client.ui.rte.basic;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.ActionAddCondition;
import org.ourproject.kune.platf.client.actions.ActionCollection;
import org.ourproject.kune.platf.client.actions.ActionDescriptor;
import org.ourproject.kune.platf.client.actions.ActionEnableCondition;
import org.ourproject.kune.platf.client.actions.ActionItem;
import org.ourproject.kune.platf.client.actions.ActionItemCollection;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonSeparator;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarPushButtonDescriptor;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbar;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.shortcuts.ShortcutDescriptor;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.ui.TextUtils;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.platf.client.ui.palette.ColorWebSafePalette;
import org.ourproject.kune.platf.client.ui.rte.RichTextArea;
import org.ourproject.kune.platf.client.ui.rte.edithtml.EditHtmlDialog;
import org.ourproject.kune.platf.client.ui.rte.img.RTEImgResources;
import org.ourproject.kune.platf.client.ui.rte.insertimg.InsertImageDialog;
import org.ourproject.kune.platf.client.ui.rte.insertlink.InsertLinkDialog;
import org.ourproject.kune.platf.client.ui.rte.insertlink.LinkInfo;
import org.ourproject.kune.platf.client.ui.rte.insertspecialchar.InsertSpecialCharDialog;
import org.ourproject.kune.platf.client.ui.rte.inserttable.InsertTableDialog;
import org.ourproject.kune.platf.client.utils.DeferredCommandWrapper;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.events.Event0;
import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.ioc.Provider;
import com.google.gwt.libideas.resources.client.ImageResource;

public class RTEditorPresenter implements RTEditor {

    private final String fontNames[] = { "Times New Roman", "Arial", "Courier New", "Georgia", "Trebuchet", "Verdana" };
    private final String fontSizes[] = { "Extra small", "Very small", "Small", "Medium", "Large", "Very large",
            "Extra large" };
    private static final RichTextArea.FontSize[] fontSizesConstants = new RichTextArea.FontSize[] {
            RichTextArea.FontSize.XX_SMALL, RichTextArea.FontSize.X_SMALL, RichTextArea.FontSize.SMALL,
            RichTextArea.FontSize.MEDIUM, RichTextArea.FontSize.LARGE, RichTextArea.FontSize.X_LARGE,
            RichTextArea.FontSize.XX_LARGE };

    private static final String EDIT_MENU = "Edit";
    private static final String INSERT_MENU = "Insert";
    private static final String FORMAT_MENU = "Format";
    private RTEditorView view;
    private boolean extended;
    private final AccessRolDTO accessRol;
    private final I18nTranslationService i18n;
    private final Session session;
    private final ActionItemCollection<Object> actions;
    private final RTEImgResources imgResources;
    private final RTEActionTopToolbar topBar;
    private final RTEActionSndToolbar sndBar;
    private ActionToolbarPushButtonDescriptor<Object> bold;
    private ActionToolbarPushButtonDescriptor<Object> italic;
    private ActionToolbarPushButtonDescriptor<Object> underline;
    private ActionToolbarPushButtonDescriptor<Object> strikethrough;
    private final Event0 onEdit;
    private final InsertLinkDialog insertElement;
    private final ColorWebSafePalette palette;
    private final DeferredCommandWrapper deferred;
    private final ActionAddCondition<Object> canBeBasic;
    private final ActionAddCondition<Object> canBeExtended;
    private final Provider<EditHtmlDialog> editHtmlDialog;
    private final Provider<InsertImageDialog> insertImageDialog;
    private final Provider<InsertTableDialog> insertTableDialog;
    private Listener<String> insertTableListener;
    private Listener<LinkInfo> insertLinkListener;
    private Listener<String> updateHtmlListener;
    private ActionToolbarButtonDescriptor<Object> insertTableBtn;
    private final Provider<InsertSpecialCharDialog> insertSpecialCharDialog;
    protected Listener<String> insertSpecialCharListener;

    public RTEditorPresenter(I18nTranslationService i18n, Session session, RTEActionTopToolbar topBar,
            RTEActionSndToolbar sndBar, RTEImgResources imgResources, InsertLinkDialog textEditorInsertElement,
            ColorWebSafePalette palette, Provider<EditHtmlDialog> editHtmlDialog,
            Provider<InsertImageDialog> insertImageDialog, Provider<InsertTableDialog> insertTableDialog,
            Provider<InsertSpecialCharDialog> insertSpecialCharDialog, DeferredCommandWrapper deferred) {
        this.i18n = i18n;
        this.session = session;
        this.topBar = topBar;
        this.sndBar = sndBar;
        this.insertElement = textEditorInsertElement;
        this.palette = palette;
        this.editHtmlDialog = editHtmlDialog;
        this.insertImageDialog = insertImageDialog;
        this.insertTableDialog = insertTableDialog;
        this.insertSpecialCharDialog = insertSpecialCharDialog;
        this.deferred = deferred;
        styleToolbar(sndBar);
        sndBar.attach();
        this.imgResources = imgResources;
        extended = true;
        accessRol = AccessRolDTO.Editor;
        actions = new ActionItemCollection<Object>();
        this.onEdit = new Event0("onRTEEdit");
        canBeBasic = new ActionAddCondition<Object>() {
            public boolean mustBeAdded(Object param) {
                return view.canBeBasic();
            }
        };
        canBeExtended = new ActionAddCondition<Object>() {
            public boolean mustBeAdded(Object param) {
                return isExtended();
            }
        };
    }

    public void addAction(ActionDescriptor<Object> action) {
        actions.add(withNoItem(action));
    }

    public void addActions(ActionCollection<Object> actioncollection) {
        actions.addAll(withNoItem(actioncollection));
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
        topBar.addActions(actions, topbar);
        sndBar.addActions(actions, sndbar);
        view.addActions(actions);
    }

    public ActionAddCondition<Object> canBeBasic() {
        return canBeBasic();
    }

    public ActionAddCondition<Object> canBeExtended() {
        return canBeExtended();
    }

    public void detach() {
        topBar.clear();
        sndBar.clear();
    }

    public void fireOnEdit() {
        onEdit.fire();
    }

    public View getEditorArea() {
        return view;
    }

    public String getHtml() {
        return view.getHTML();
    }

    public ActionToolbar<Object> getSndBar() {
        return sndBar;
    }

    public String getText() {
        return view.getText();
    }

    public ActionToolbar<Object> getTopBar() {
        return topBar;
    }

    public void init(RTEditorView view) {
        this.view = view;
        createBasicActions();
    }

    public void onEditorFocus() {
        topBar.hideAllMenus();
        sndBar.hideAllMenus();
        palette.hide();
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    public void setFocus(boolean focus) {
        view.setFocus(focus);
    }

    public void setHtml(String html) {
        view.setHTML(html);
        view.focus();
    }

    public void setText(String text) {
        view.setText(text);
        view.focus();
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
        ActionToolbarMenuDescriptor<Object> selectAll = new ActionToolbarMenuDescriptor<Object>(accessRol, topbar,
                new Listener0() {
                    public void onEvent() {
                        view.selectAll();
                    }
                });
        selectAll.setIconCls(getCssName(imgResources.selectall()));
        selectAll.setShortcut(new ShortcutDescriptor(true, 'A'));
        selectAll.setTextDescription(i18n.t("Select all"));
        selectAll.setParentMenuTitle(i18n.t(EDIT_MENU));
        selectAll.setAddCondition(canBeBasic);
        selectAll.setTopSeparator(true);
        selectAll.setBottomSeparator(true);

        bold = new ActionToolbarPushButtonDescriptor<Object>(accessRol, sndbar, new Listener0() {
            public void onEvent() {
                view.toggleBold();
                fireOnEdit();
            }
        });
        bold.setIconCls(getCssName(imgResources.bold()));
        bold.setToolTip(i18n.t("Bold"));
        bold.setShortcut(new ShortcutDescriptor(true, 'B'));
        bold.setAddCondition(canBeBasic);

        italic = new ActionToolbarPushButtonDescriptor<Object>(accessRol, sndbar, new Listener0() {
            public void onEvent() {
                view.toggleItalic();
                fireOnEdit();
            }
        });
        italic.setIconCls(getCssName(imgResources.italic()));
        italic.setToolTip(i18n.t("Italic"));
        italic.setShortcut(new ShortcutDescriptor(true, 'I'));
        italic.setAddCondition(canBeBasic);
        italic.setAddCondition(canBeBasic);

        underline = new ActionToolbarPushButtonDescriptor<Object>(accessRol, sndbar, new Listener0() {
            public void onEvent() {
                view.toggleUnderline();
                fireOnEdit();
            }
        });
        underline.setIconCls(getCssName(imgResources.underline()));
        underline.setToolTip(i18n.t("Underline"));
        underline.setShortcut(new ShortcutDescriptor(true, 'U'));
        underline.setAddCondition(canBeBasic);

        ActionToolbarMenuDescriptor<Object> subscript = new ActionToolbarMenuDescriptor<Object>(accessRol, topbar,
                new Listener0() {
                    public void onEvent() {
                        view.toggleSubscript();
                        fireOnEdit();
                    }
                });
        subscript.setParentMenuTitle(i18n.t(FORMAT_MENU));
        subscript.setTextDescription(i18n.t("Subscript"));
        subscript.setIconCls(getCssName(imgResources.subscript()));
        subscript.setShortcut(new ShortcutDescriptor(true, 188, ","));
        subscript.setAddCondition(canBeBasic);

        ActionToolbarMenuDescriptor<Object> superscript = new ActionToolbarMenuDescriptor<Object>(accessRol, topbar,
                new Listener0() {
                    public void onEvent() {
                        view.toggleSuperscript();
                        fireOnEdit();
                    }
                });
        superscript.setParentMenuTitle(i18n.t(FORMAT_MENU));
        superscript.setTextDescription(i18n.t("Superscript"));
        superscript.setIconCls(getCssName(imgResources.superscript()));
        superscript.setShortcut(new ShortcutDescriptor(true, 190, "."));
        superscript.setAddCondition(canBeBasic);

        ActionToolbarButtonDescriptor<Object> justifyLeft = new ActionToolbarButtonDescriptor<Object>(accessRol,
                sndbar, new Listener0() {
                    public void onEvent() {
                        view.justifyLeft();
                        fireOnEdit();
                    }
                });
        justifyLeft.setIconCls(getCssName(imgResources.alignleft()));
        justifyLeft.setToolTip(i18n.t("Left Justify"));
        justifyLeft.setShortcut(new ShortcutDescriptor(true, 'L'));
        justifyLeft.setAddCondition(canBeBasic);

        ActionToolbarButtonDescriptor<Object> justifyCentre = new ActionToolbarButtonDescriptor<Object>(accessRol,
                sndbar, new Listener0() {
                    public void onEvent() {
                        view.justifyCenter();
                        fireOnEdit();
                    }
                });
        justifyCentre.setIconCls(getCssName(imgResources.centerpara()));
        justifyCentre.setToolTip(i18n.t("Centre Justify"));
        justifyCentre.setShortcut(new ShortcutDescriptor(true, 'E'));
        justifyCentre.setAddCondition(canBeBasic);

        ActionToolbarButtonDescriptor<Object> justifyRight = new ActionToolbarButtonDescriptor<Object>(accessRol,
                sndbar, new Listener0() {
                    public void onEvent() {
                        view.justifyRight();
                        fireOnEdit();
                    }
                });
        justifyRight.setIconCls(getCssName(imgResources.alignright()));
        justifyRight.setToolTip(i18n.t("Right Justify"));
        justifyRight.setShortcut(new ShortcutDescriptor(true, 'R'));
        justifyRight.setAddCondition(canBeBasic);

        ActionToolbarMenuDescriptor<Object> undo = new ActionToolbarMenuDescriptor<Object>(accessRol, topbar,
                new Listener0() {
                    public void onEvent() {
                        view.undo();
                        fireOnEdit();
                    }
                });
        undo.setShortcut(new ShortcutDescriptor(true, 'Z'));
        undo.setTextDescription(i18n.t("Undo"));
        undo.setParentMenuTitle(i18n.t(EDIT_MENU));
        undo.setAddCondition(canBeExtended);
        undo.setIconCls(getCssName(imgResources.undo()));

        ActionToolbarMenuDescriptor<Object> redo = new ActionToolbarMenuDescriptor<Object>(accessRol, topbar,
                new Listener0() {
                    public void onEvent() {
                        view.redo();
                        fireOnEdit();
                    }
                });
        redo.setShortcut(new ShortcutDescriptor(true, 'Y'));
        redo.setTextDescription(i18n.t("Redo"));
        redo.setParentMenuTitle(i18n.t(EDIT_MENU));
        redo.setAddCondition(canBeExtended);
        redo.setBottomSeparator(true);
        redo.setIconCls(getCssName(imgResources.redo()));

        ActionToolbarButtonDescriptor<Object> undoBtn = new ActionToolbarButtonDescriptor<Object>(accessRol, sndbar,
                new Listener0() {
                    public void onEvent() {
                        view.undo();
                        fireOnEdit();
                    }
                });
        undoBtn.setToolTip(i18n.t("Undo"));
        undoBtn.setAddCondition(canBeExtended);
        undoBtn.setIconCls(getCssName(imgResources.undo()));
        undoBtn.setPosition(0);

        ActionToolbarButtonDescriptor<Object> redoBtn = new ActionToolbarButtonDescriptor<Object>(accessRol, sndbar,
                new Listener0() {
                    public void onEvent() {
                        view.redo();
                        fireOnEdit();
                    }
                });
        redoBtn.setToolTip(i18n.t("Redo"));
        redoBtn.setAddCondition(canBeExtended);
        redoBtn.setIconCls(getCssName(imgResources.redo()));
        redoBtn.setPosition(1);
        redoBtn.setRightSeparator(ActionToolbarButtonSeparator.separator);

        ActionToolbarMenuDescriptor<Object> copy = new ActionToolbarMenuDescriptor<Object>(accessRol, topbar,
                new Listener0() {
                    public void onEvent() {
                        view.copy();
                    }
                });
        copy.setShortcut(new ShortcutDescriptor(true, 'C'));
        copy.setTextDescription(i18n.t("Copy"));
        copy.setParentMenuTitle(i18n.t(EDIT_MENU));
        copy.setAddCondition(canBeExtended);
        copy.setIconCls(getCssName(imgResources.copy()));

        ActionToolbarMenuDescriptor<Object> cut = new ActionToolbarMenuDescriptor<Object>(accessRol, topbar,
                new Listener0() {
                    public void onEvent() {
                        view.cut();
                        fireOnEdit();
                    }
                });
        cut.setShortcut(new ShortcutDescriptor(true, 'X'));
        cut.setTextDescription(i18n.t("Cut"));
        cut.setParentMenuTitle(i18n.t(EDIT_MENU));
        cut.setAddCondition(canBeExtended);
        cut.setIconCls(getCssName(imgResources.cut()));

        ActionToolbarMenuDescriptor<Object> paste = new ActionToolbarMenuDescriptor<Object>(accessRol, topbar,
                new Listener0() {
                    public void onEvent() {
                        view.paste();
                        fireOnEdit();
                    }
                });
        paste.setShortcut(new ShortcutDescriptor(true, 'V'));
        paste.setTextDescription(i18n.t("Paste"));
        paste.setParentMenuTitle(i18n.t(EDIT_MENU));
        paste.setAddCondition(canBeExtended);
        paste.setIconCls(getCssName(imgResources.paste()));

        ActionToolbarMenuDescriptor<Object> editHtml = new ActionToolbarMenuDescriptor<Object>(accessRol, topbar,
                new Listener0() {

                    public void onEvent() {
                        if (updateHtmlListener == null) {
                            updateHtmlListener = new Listener<String>() {
                                public void onEvent(String html) {
                                    view.setHTML(html);
                                    fireOnEdit();
                                }
                            };
                        }
                        EditHtmlDialog dialog = editHtmlDialog.get();
                        dialog.setUpdateListener(updateHtmlListener);
                        dialog.show();
                        dialog.setHtml(view.getHTML());
                    }
                });
        editHtml.setIconCls(getCssName(imgResources.edithtml()));
        editHtml.setTextDescription(i18n.t("Edit HTML"));
        editHtml.setParentMenuTitle(i18n.t(EDIT_MENU));
        editHtml.setAddCondition(canBeExtended);

        ActionToolbarMenuDescriptor<Object> comment = new ActionToolbarMenuDescriptor<Object>(accessRol, topbar,
                new Listener0() {
                    public void onEvent() {
                        deferred.addCommand(new Listener0() {
                            public void onEvent() {
                                view.focus();
                                final String author = session.isLogged() ? session.getCurrentUser().getShortName()
                                        : i18n.t("anonymous user");
                                if (view.isAnythingSelected()) {
                                    NotifyUser.askConfirmation(i18n.t("Insert a comment"),
                                            i18n.t("Include the selected text in the comment?"), new Listener0() {
                                                public void onEvent() {
                                                    // include selection in
                                                    // comment
                                                    view.insertCommentUsingSelection(author);
                                                }
                                            }, new Listener0() {
                                                public void onEvent() {
                                                    // not include selection in
                                                    // comment;
                                                    view.insertCommentNotUsingSelection(author);
                                                }
                                            });
                                } else {
                                    // Nothing selected > create comment in
                                    // insertion point
                                    view.insertComment(author);
                                }
                            }
                        });
                    }
                });
        comment.setEnableCondition(new ActionEnableCondition<Object>() {
            public boolean mustBeEnabled(Object param) {
                return session.isLogged();
            }
        });
        comment.setShortcut(new ShortcutDescriptor(true, 'M'));
        comment.setTextDescription(i18n.t("Comment"));
        comment.setParentMenuTitle(i18n.t(INSERT_MENU));
        comment.setEnableCondition(isInsertHtmlSupported());
        comment.setAddCondition(canBeExtended);

        ActionToolbarMenuDescriptor<Object> hr = new ActionToolbarMenuDescriptor<Object>(accessRol, topbar,
                new Listener0() {
                    public void onEvent() {
                        view.focus();
                        view.insertHorizontalRule();
                        fireOnEdit();
                    }
                });
        hr.setTextDescription(i18n.t("Horizontal line"));
        hr.setShortcut(new ShortcutDescriptor(true, true, ' ', "Space"));
        hr.setIconCls(getCssName(imgResources.hfixedline()));
        hr.setParentMenuTitle(i18n.t(INSERT_MENU));
        hr.setAddCondition(canBeExtended);

        ActionToolbarMenuDescriptor<Object> blockquote = new ActionToolbarMenuDescriptor<Object>(accessRol, topbar,
                new Listener0() {
                    public void onEvent() {
                        view.focus();
                        view.insertBlockquote();
                        fireOnEdit();
                    }
                });
        blockquote.setTextDescription(i18n.t("Block Quotation"));
        blockquote.setParentMenuTitle(i18n.t(FORMAT_MENU));
        blockquote.setAddCondition(canBeExtended);

        ActionToolbarButtonDescriptor<Object> hrButton = new ActionToolbarButtonDescriptor<Object>(accessRol, sndbar,
                new Listener0() {
                    public void onEvent() {
                        view.focus();
                        view.insertHorizontalRule();
                        fireOnEdit();
                    }
                });
        hrButton.setIconCls(getCssName(imgResources.hfixedline()));
        hrButton.setToolTip(i18n.t("Horizontal line"));
        hrButton.setAddCondition(canBeExtended);

        strikethrough = new ActionToolbarPushButtonDescriptor<Object>(accessRol, sndbar, new Listener0() {
            public void onEvent() {
                view.toggleStrikethrough();
                fireOnEdit();
            }
        });
        strikethrough.setIconCls(getCssName(imgResources.strikeout()));
        strikethrough.setToolTip(i18n.t("Strikethrough"));
        strikethrough.setRightSeparator(ActionToolbarButtonSeparator.separator);
        strikethrough.setAddCondition(canBeExtended);

        ActionToolbarButtonDescriptor<Object> decreaseIndent = new ActionToolbarButtonDescriptor<Object>(accessRol,
                sndbar, new Listener0() {
                    public void onEvent() {
                        view.leftIndent();
                        fireOnEdit();
                    }
                });
        decreaseIndent.setIconCls(getCssName(imgResources.decrementindent()));
        decreaseIndent.setToolTip(i18n.t("Decrease Indent"));
        decreaseIndent.setAddCondition(canBeExtended);

        ActionToolbarButtonDescriptor<Object> increaseIndent = new ActionToolbarButtonDescriptor<Object>(accessRol,
                sndbar, new Listener0() {
                    public void onEvent() {
                        view.rightIndent();
                        fireOnEdit();
                    }
                });
        increaseIndent.setIconCls(getCssName(imgResources.incrementindent()));
        increaseIndent.setToolTip(i18n.t("Increase Indent"));
        increaseIndent.setAddCondition(canBeExtended);

        ActionToolbarButtonDescriptor<Object> ol = new ActionToolbarButtonDescriptor<Object>(accessRol, sndbar,
                new Listener0() {
                    public void onEvent() {
                        view.insertOrderedList();
                        fireOnEdit();
                    }
                });
        ol.setIconCls(getCssName(imgResources.defaultnumbering()));
        ol.setToolTip(i18n.t("Numbered List"));
        ol.setShortcut(new ShortcutDescriptor(true, '7'));
        ol.setAddCondition(canBeExtended);

        ActionToolbarButtonDescriptor<Object> ul = new ActionToolbarButtonDescriptor<Object>(accessRol, sndbar,
                new Listener0() {
                    public void onEvent() {
                        view.insertUnorderedList();
                        fireOnEdit();
                    }
                });
        ul.setIconCls(getCssName(imgResources.defaultbullet()));
        ul.setToolTip(i18n.t("Bullet List"));
        ul.setShortcut(new ShortcutDescriptor(true, '8'));
        ul.setRightSeparator(ActionToolbarButtonSeparator.separator);
        ul.setAddCondition(canBeExtended);

        ActionToolbarButtonDescriptor<Object> img = new ActionToolbarButtonDescriptor<Object>(accessRol, sndbar,
                new Listener0() {
                    public void onEvent() {
                        insertImageDialog.get().show();
                    }
                });
        img.setIconCls(getCssName(imgResources.images()));
        img.setToolTip(i18n.t("Insert Image"));
        img.setAddCondition(canBeExtended);

        ActionToolbarButtonDescriptor<Object> createOrEditLink = new ActionToolbarButtonDescriptor<Object>(accessRol,
                sndbar, new Listener0() {
                    public void onEvent() {
                        deferred.addCommand(new Listener0() {
                            public void onEvent() {
                                if (insertLinkListener == null) {
                                    insertLinkListener = new Listener<LinkInfo>() {
                                        public void onEvent(LinkInfo linkInfo) {
                                            String link = linkInfo.toString();
                                            Log.debug("Link: " + link);
                                            view.focus();
                                            view.insertHtml(link);
                                            fireOnEdit();
                                        }
                                    };
                                }
                                insertElement.setLinkInfo(view.getLinkInfoIfHref());
                                insertElement.setOnCreateLink(insertLinkListener);
                                insertElement.show();
                            }
                        });
                    }
                });
        createOrEditLink.setIconCls(getCssName(imgResources.link()));
        createOrEditLink.setToolTip(i18n.t("Create or Edit Link"));
        createOrEditLink.setShortcut(new ShortcutDescriptor(true, 'K'));
        createOrEditLink.setAddCondition(canBeExtended);

        ActionToolbarButtonDescriptor<Object> removeLink = new ActionToolbarButtonDescriptor<Object>(accessRol, sndbar,
                new Listener0() {
                    public void onEvent() {
                        view.unlink();
                        fireOnEdit();
                    }
                });
        removeLink.setIconCls(getCssName(imgResources.linkbreak()));
        removeLink.setToolTip(i18n.t("Remove Link"));
        removeLink.setShortcut(new ShortcutDescriptor(true, true, 'K'));
        removeLink.setAddCondition(canBeExtended);

        final ActionToolbarMenuDescriptor<Object> removeFormat = new ActionToolbarMenuDescriptor<Object>(accessRol,
                topbar, new Listener0() {
                    public void onEvent() {
                        view.removeFormat();
                        fireOnEdit();
                    }
                });
        removeFormat.setIconCls(getCssName(imgResources.removeFormat()));
        ShortcutDescriptor ctrl_space = new ShortcutDescriptor(true, ' ', "Space");
        removeFormat.setTextDescription(i18n.t("Clear Formatting") + (ctrl_space.toString()));
        removeFormat.setAddCondition(canBeExtended);
        removeFormat.setParentMenuTitle(i18n.t(FORMAT_MENU));
        removeFormat.setBottomSeparator(true);

        final ActionToolbarButtonDescriptor<Object> removeFormatBtn = new ActionToolbarButtonDescriptor<Object>(
                accessRol, sndbar, new Listener0() {
                    public void onEvent() {
                        view.removeFormat();
                        fireOnEdit();
                    }
                });
        removeFormatBtn.setIconCls(getCssName(imgResources.removeFormat()));
        removeFormatBtn.setToolTip(i18n.t("Clear Formatting"));
        removeFormatBtn.setShortcut(ctrl_space);
        removeFormatBtn.setAddCondition(canBeExtended);
        removeFormatBtn.setRightSeparator(ActionToolbarButtonSeparator.separator);

        final ActionToolbarMenuDescriptor<Object> insertSpecialChar = new ActionToolbarMenuDescriptor<Object>(
                accessRol, topbar, new Listener0() {
                    public void onEvent() {
                        NotifyUser.showProgressLoading();
                        if (insertSpecialCharListener == null) {
                            insertSpecialCharListener = new Listener<String>() {
                                public void onEvent(String character) {
                                    view.insertHtml(character);
                                }
                            };
                        }
                        insertSpecialCharDialog.get().setOnInsertSpecialChar(insertSpecialCharListener);
                        insertSpecialCharDialog.get().show();
                        NotifyUser.hideProgress();
                    }
                });
        insertSpecialChar.setIconCls(getCssName(imgResources.specialchars()));
        insertSpecialChar.setTextDescription(i18n.t("Special characters..."));
        insertSpecialChar.setAddCondition(canBeExtended);
        insertSpecialChar.setParentMenuTitle(i18n.t(INSERT_MENU));
        insertSpecialChar.setTopSeparator(true);

        final ActionToolbarMenuDescriptor<Object> insertMedia = new ActionToolbarMenuDescriptor<Object>(accessRol,
                topbar, new Listener0() {
                    public void onEvent() {
                        NotifyUser.info(TextUtils.IN_DEVELOPMENT);
                    }
                });
        insertMedia.setIconCls(getCssName(imgResources.film()));
        insertMedia.setTextDescription(i18n.t("Media..."));
        insertMedia.setAddCondition(canBeExtended);
        insertMedia.setParentMenuTitle(i18n.t(INSERT_MENU));

        final ActionToolbarMenuDescriptor<Object> insertTable = new ActionToolbarMenuDescriptor<Object>(accessRol,
                topbar, new Listener0() {
                    public void onEvent() {
                        onInsertTablePressed();
                    }
                });
        insertTable.setIconCls(getCssName(imgResources.inserttable()));
        insertTable.setTextDescription(i18n.t("Table ..."));
        insertTable.setAddCondition(canBeExtended);
        insertTable.setParentMenuTitle(i18n.t(INSERT_MENU));

        insertTableBtn = new ActionToolbarButtonDescriptor<Object>(accessRol, sndbar, new Listener0() {
            public void onEvent() {
                onInsertTablePressed();
            }
        });
        insertTableBtn.setIconCls(getCssName(imgResources.inserttable()));
        insertTableBtn.setToolTip(i18n.t("Insert Table"));
        insertTableBtn.setAddCondition(canBeExtended);
        insertTableBtn.setRightSeparator(ActionToolbarButtonSeparator.separator);

        final ActionToolbarButtonDescriptor<Object> fontColor = new ActionToolbarButtonDescriptor<Object>(accessRol,
                sndbar, new Listener0() {
                    public void onEvent() {
                        palette.show(getActionLeftPosition(sndBar, insertTableBtn), getActionTopPosition(sndBar,
                                removeFormatBtn), new Listener<String>() {
                            public void onEvent(String color) {
                                palette.hide();
                                view.setForeColor(color);
                                fireOnEdit();
                            }
                        });
                    }
                });
        fontColor.setIconCls(getCssName(imgResources.fontcolor()));
        fontColor.setToolTip(i18n.t("Text Colour"));
        fontColor.setAddCondition(canBeBasic);

        ActionToolbarButtonDescriptor<Object> backgroundColor = new ActionToolbarButtonDescriptor<Object>(accessRol,
                sndbar, new Listener0() {
                    public void onEvent() {
                        palette.show(getActionLeftPosition(sndBar, fontColor), getActionTopPosition(sndBar, fontColor),
                                new Listener<String>() {
                                    public void onEvent(String color) {
                                        palette.hide();
                                        view.setBackColor(color);
                                        fireOnEdit();
                                    }
                                });
                    }
                });
        backgroundColor.setIconCls(getCssName(imgResources.backcolor()));
        backgroundColor.setToolTip(i18n.t("Text Background Colour"));
        backgroundColor.setAddCondition(canBeBasic);

        final ActionToolbarMenuDescriptor<Object> devInfo = new ActionToolbarMenuDescriptor<Object>(accessRol, topbar,
                new Listener0() {
                    public void onEvent() {
                        deferred.addCommand(new Listener0() {
                            public void onEvent() {
                                view.getRangeInfo();
                            }
                        });
                    }
                });
        devInfo.setTextDescription(i18n.t("Developers info"));
        devInfo.setAddCondition(canBeExtended);
        devInfo.setParentMenuTitle(i18n.t(FORMAT_MENU));
        devInfo.setShortcut(new ShortcutDescriptor(false, true, false, 'I'));

        actions.add(withNoItem(bold));
        actions.add(withNoItem(italic));
        actions.add(withNoItem(underline));
        actions.add(withNoItem(strikethrough));

        actions.add(withNoItem(justifyLeft));
        actions.add(withNoItem(justifyCentre));
        actions.add(withNoItem(justifyRight));

        actions.add(withNoItem(undo));
        actions.add(withNoItem(redo));
        actions.add(withNoItem(copy));
        actions.add(withNoItem(cut));
        actions.add(withNoItem(paste));
        actions.add(withNoItem(selectAll));
        actions.add(withNoItem(editHtml));
        actions.add(withNoItem(removeFormat));
        actions.add(withNoItem(subscript));
        actions.add(withNoItem(superscript));
        actions.add(withNoItem(blockquote));
        actions.add(withNoItem(decreaseIndent));
        actions.add(withNoItem(increaseIndent));
        actions.add(withNoItem(ol));
        actions.add(withNoItem(ul));
        actions.add(withNoItem(removeFormatBtn));
        actions.add(withNoItem(hrButton));
        actions.add(withNoItem(img));
        actions.add(withNoItem(createOrEditLink));
        actions.add(withNoItem(removeLink));
        actions.add(withNoItem(insertTableBtn));
        actions.add(withNoItem(insertTable));
        actions.add(withNoItem(insertMedia));
        actions.add(withNoItem(insertSpecialChar));
        actions.add(withNoItem(comment));
        actions.add(withNoItem(hr));
        actions.add(withNoItem(undoBtn));
        actions.add(withNoItem(redoBtn));
        // actions.add(withNoItem(devInfo));

        for (String fontName : this.fontNames) {
            ActionToolbarMenuDescriptor<Object> fontNameAction = createFontNameAction(canBeBasic, fontName);
            actions.add(withNoItem(fontNameAction));
        }
        for (int fontSize = 0; fontSize < fontSizes.length; fontSize++) {
            ActionToolbarMenuDescriptor<Object> fontSizeAction = createFontSizeAction(canBeBasic, fontSize,
                    fontSizes[fontSize]);
            actions.add(withNoItem(fontSizeAction));
        }
        actions.add(withNoItem(fontColor));
        actions.add(withNoItem(backgroundColor));
    }

    private ActionToolbarMenuDescriptor<Object> createFontNameAction(ActionAddCondition<Object> canBeBasic,
            final String fontName) {
        final ActionToolbarMenuDescriptor<Object> font = new ActionToolbarMenuDescriptor<Object>(accessRol, sndbar,
                new Listener0() {
                    public void onEvent() {
                        view.setFontName(fontName);
                        fireOnEdit();
                    }
                });
        font.setTextDescription("<span style=\"font-family: " + fontName + "\">" + fontName + "</span>");
        font.setParentMenuTooltip(i18n.t("Font"));
        font.setParentMenuIconCls(getCssName(imgResources.charfontname()));
        font.setAddCondition(canBeBasic);
        return font;
    }

    private ActionToolbarMenuDescriptor<Object> createFontSizeAction(ActionAddCondition<Object> canBeBasic,
            final int fontSize, String fontSizeName) {
        final ActionToolbarMenuDescriptor<Object> font = new ActionToolbarMenuDescriptor<Object>(accessRol, sndbar,
                new Listener0() {
                    public void onEvent() {
                        view.setFontSize(fontSizesConstants[fontSize]);
                        fireOnEdit();
                    }
                });
        font.setTextDescription("<font size=\"" + (fontSize + 1) + "\">" + i18n.t(fontSizeName) + "</font>");
        font.setParentMenuTooltip(i18n.t("Font size"));
        font.setParentMenuIconCls(getCssName(imgResources.fontheight()));
        font.setAddCondition(canBeBasic);
        font.setShortcut(new ShortcutDescriptor(true, 48 + fontSize));
        return font;
    }

    private int getActionLeftPosition(ActionToolbar<Object> bar, ActionToolbarButtonDescriptor<Object> action) {
        return bar.getLeftPosition(action);
    }

    private int getActionTopPosition(ActionToolbar<Object> bar, ActionToolbarButtonDescriptor<Object> action) {
        return bar.getTopPosition(action);
    }

    private String getCssName(ImageResource imageResource) {
        return RTEImgResources.SUFFIX + imageResource.getName();
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

    private void onInsertTablePressed() {
        if (insertTableListener == null) {
            insertTableListener = new Listener<String>() {
                public void onEvent(String table) {
                    if (view.isAnythingSelected()) {
                        // FIXME
                        // move to end of selection?
                    }
                    view.insertHtml(table);
                    fireOnEdit();
                }
            };
        }
        insertTableDialog.get().setOnInsertTable(insertTableListener);
        insertTableDialog.get().show();
    }

    private void styleToolbar(ActionToolbar<Object> bar) {
        bar.setNormalStyle();
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
