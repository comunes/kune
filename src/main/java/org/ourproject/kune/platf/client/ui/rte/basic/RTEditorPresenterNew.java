package org.ourproject.kune.platf.client.ui.rte.basic;

import static org.ourproject.kune.platf.client.ui.rte.basic.AbstractRTEAction.NO_ICON;
import static org.ourproject.kune.platf.client.ui.rte.basic.AbstractRTEAction.NO_TEXT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.AbstractAction;
import org.ourproject.kune.platf.client.actions.Action;
import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.actions.InputMap;
import org.ourproject.kune.platf.client.actions.KeyStroke;
import org.ourproject.kune.platf.client.actions.ui.ButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ui.GuiActionDescCollection;
import org.ourproject.kune.platf.client.actions.ui.GuiActionDescrip;
import org.ourproject.kune.platf.client.actions.ui.GuiAddCondition;
import org.ourproject.kune.platf.client.actions.ui.MenuCheckItemDescriptor;
import org.ourproject.kune.platf.client.actions.ui.MenuDescriptor;
import org.ourproject.kune.platf.client.actions.ui.MenuItemDescriptor;
import org.ourproject.kune.platf.client.actions.ui.MenuRadioItemDescriptor;
import org.ourproject.kune.platf.client.actions.ui.MenuSeparatorDescriptor;
import org.ourproject.kune.platf.client.actions.ui.PushButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ui.ToolbarSeparatorDescriptor;
import org.ourproject.kune.platf.client.actions.ui.ToolbarSeparatorDescriptor.Type;
import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.shortcuts.Keyboard;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.platf.client.ui.palette.ColorWebSafePalette;
import org.ourproject.kune.platf.client.ui.rte.RichTextArea;
import org.ourproject.kune.platf.client.ui.rte.edithtml.EditHtmlDialog;
import org.ourproject.kune.platf.client.ui.rte.img.RTEImgResources;
import org.ourproject.kune.platf.client.ui.rte.insertimg.ImageInfo;
import org.ourproject.kune.platf.client.ui.rte.insertimg.InsertImageDialog;
import org.ourproject.kune.platf.client.ui.rte.insertlink.InsertLinkDialog;
import org.ourproject.kune.platf.client.ui.rte.insertlink.LinkInfo;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.InsertMediaDialog;
import org.ourproject.kune.platf.client.ui.rte.insertspecialchar.InsertSpecialCharDialog;
import org.ourproject.kune.platf.client.ui.rte.inserttable.InsertTableDialog;
import org.ourproject.kune.platf.client.utils.DeferredCommandWrapper;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.events.Event0;
import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.ioc.Provider;
import com.google.gwt.libideas.resources.client.ImageResource;
import com.google.gwt.user.client.Event;

public class RTEditorPresenterNew implements RTEditorNew {

    public class BackgroundColorAction extends AbstractRTEAction {

        public BackgroundColorAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            createPalette();
            final Event event = actionEvent.getEvent();
            palette.show(event.getClientX(), event.getClientY(), new Listener<String>() {
                public void onEvent(final String color) {
                    palette.hide();
                    view.setBackColor(color);
                    fireOnEdit();
                }
            });
        }
    }

    public class BlockquoteAction extends AbstractRTEAction {
        public BlockquoteAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            view.focus();
            view.insertBlockquote();
            fireOnEdit();
        }
    }

    public class BoldAction extends AbstractRTEAction {
        public BoldAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            view.toggleBold();
            fireOnEdit();
        }
    }

    public class CommentAction extends AbstractRTEAction {
        public CommentAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
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

        @Override
        public boolean isEnabled() {
            return session.isLogged();
        }
    }

    public class CopyAction extends AbstractRTEAction {
        public CopyAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            view.copy();
        }
    }

    public class CreateOrEditLinkAction extends AbstractRTEAction {
        public CreateOrEditLinkAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            deferred.addCommand(new Listener0() {
                private InsertLinkDialog insLinkDialog;

                public void onEvent() {
                    if (insLinkListener == null) {
                        insLinkListener = new Listener<LinkInfo>() {
                            public void onEvent(final LinkInfo linkInfo) {
                                final String link = linkInfo.toString();
                                Log.debug("Link: " + link);
                                view.focus();
                                view.insertHtml(link);
                                fireOnEdit();
                            }
                        };
                    }
                    final LinkInfo linkInfo = view.getLinkInfoIfHref();

                    if (insLinkDialog == null) {
                        insLinkDialog = insLinkDialogPv.get();
                    }
                    insLinkDialog.setLinkInfo(linkInfo);
                    insLinkDialog.setOnCreateLink(insLinkListener);
                    insLinkDialog.show();
                    hideLinkCtxMenu();
                    final String href = linkInfo.getHref();
                    if (href.length() > 0) {
                        if (href.startsWith("mailto")) {
                            insLinkDialog.activateTab(2);
                        } else {
                            insLinkDialog.activateTab(1);
                        }
                    }
                }
            });
        }
    }

    public class CutAction extends AbstractRTEAction {
        public CutAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            view.cut();
            fireOnEdit();
        }
    }

    public class DecreaseIndentAction extends AbstractRTEAction {
        public DecreaseIndentAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            view.leftIndent();
            fireOnEdit();
        }
    }

    public class DevInfoAction extends AbstractRTEAction {
        public DevInfoAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            deferred.addCommand(new Listener0() {
                public void onEvent() {
                    view.getRangeInfo();
                }
            });
        }
    }

    public class EditHtmlAction extends AbstractRTEAction {
        private EditHtmlDialog editHtmlDialog;

        public EditHtmlAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            if (updHtmlListener == null) {
                updHtmlListener = new Listener<String>() {
                    public void onEvent(final String html) {
                        view.setHTML(html);
                        fireOnEdit();
                    }
                };
            }
            if (editHtmlDialog == null) {
                editHtmlDialog = editHtmlDialogPv.get();
            }
            editHtmlDialog.setUpdateListener(updHtmlListener);
            editHtmlDialog.show();
            hideLinkCtxMenu();
            editHtmlDialog.setHtml(view.getHTML());
        }
    }

    public class FontAction extends AbstractRTEAction {
        public static final String FONT_NAME = "fontname";

        public FontAction(final String fontName, final String tooltip, final ImageResource icon) {
            super("<span style=\"font-family: " + fontName + "\">" + fontName + "</span>", tooltip, icon);
            super.putValue(FONT_NAME, fontName);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            final String fontName = getFontName();
            view.setFontName(fontName);
            fontMenu.setText(fontName);
            fireOnEdit();
        }

        private String getFontName() {
            return (String) super.getValue(FONT_NAME);
        }
    }

    public class FontColorAction extends AbstractRTEAction {
        public FontColorAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            createPalette();
            final Event event = actionEvent.getEvent();
            palette.show(event.getClientX(), event.getClientY(), new Listener<String>() {
                public void onEvent(final String color) {
                    palette.hide();
                    view.setForeColor(color);
                    fireOnEdit();
                }
            });
            hideLinkCtxMenu();
        }
    }

    public class FontSizeAction extends AbstractRTEAction {
        private final int fontSize;

        public FontSizeAction(final String fontSizeName, final int fontSize, final String tooltip,
                final ImageResource icon) {
            super("<font size=\"" + (fontSize + 1) + "\">" + fontSizeName + "</font>", tooltip, icon);
            this.fontSize = fontSize;
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            view.setFontSize(FONT_SIZES[fontSize]);
            fireOnEdit();
            // fontSizeMenu.setText(fontSizeName);
        }
    }

    public class HrAction extends AbstractRTEAction {
        public HrAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            view.focus();
            view.insertHorizontalRule();
            fireOnEdit();
        }
    }

    public class ImgAction extends AbstractRTEAction {
        public ImgAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            deferred.addCommand(new Listener0() {
                private InsertImageDialog insImgDialog;

                public void onEvent() {
                    if (insImgListener == null) {
                        insImgListener = new Listener<ImageInfo>() {
                            public void onEvent(final ImageInfo imageInfo) {
                                Log.debug("Image: " + imageInfo);
                                view.focus();
                                view.insertHtml(imageInfo.toString());
                                fireOnEdit();
                            }
                        };
                    }
                    if (insImgDialog == null) {
                        insImgDialog = insImgDialogProv.get();
                    }
                    insImgDialog.reset();
                    insImgDialog.setOnCreateImage(insImgListener);
                    insImgDialog.show();
                    hideLinkCtxMenu();
                }
            });
        }
    }

    public class IncreaseIndentAction extends AbstractRTEAction {
        public IncreaseIndentAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            view.rightIndent();
            fireOnEdit();
        }
    }

    public class InsertMediaAction extends AbstractRTEAction {
        public InsertMediaAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            deferred.addCommand(new Listener0() {
                private InsertMediaDialog insMediaDialog;

                public void onEvent() {
                    if (insMediaListener == null) {
                        insMediaListener = new Listener<String>() {
                            public void onEvent(final String html) {
                                Log.debug("Media: " + html);
                                view.focus();
                                view.insertHtml(html);
                                fireOnEdit();
                            }
                        };
                    }
                    if (insMediaDialog == null) {
                        insMediaDialog = insMediaDialogPv.get();
                    }
                    insMediaDialog.setOnCreate(insMediaListener);
                    insMediaDialog.show();
                    hideLinkCtxMenu();
                }
            });
        }
    }

    public class InsertSpecialCharAction extends AbstractRTEAction {
        private InsertSpecialCharDialog insCharDialog;

        public InsertSpecialCharAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            NotifyUser.showProgressLoading();
            if (insCharListener == null) {
                insCharListener = new Listener<String>() {
                    public void onEvent(final String character) {
                        view.insertHtml(character);
                    }
                };
            }
            if (insCharDialog == null) {
                insCharDialog = insCharDialogProv.get();
            }
            insCharDialog.setOnInsertSpecialChar(insCharListener);
            insCharDialog.show();
            hideLinkCtxMenu();
            NotifyUser.hideProgress();
        }
    }

    public class InsertTableAction extends AbstractRTEAction {
        private InsertTableDialog insTableDialog;

        public InsertTableAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            if (insTableListener == null) {
                insTableListener = new Listener<String>() {
                    public void onEvent(final String table) {
                        view.insertHtml(table);
                        fireOnEdit();
                    }
                };
            }
            if (insTableDialog == null) {
                insTableDialog = insTableDialogPv.get();
            }
            insTableDialog.setOnInsertTable(insTableListener);
            insTableDialog.show();
        }
    }

    public class ItalicAction extends AbstractRTEAction {
        public ItalicAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            view.toggleItalic();
            fireOnEdit();
        }
    }

    public class JustifyCentreAction extends AbstractRTEAction {
        public JustifyCentreAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            view.justifyCenter();
            fireOnEdit();
        }
    }

    public class JustifyLeftAction extends AbstractRTEAction {
        public JustifyLeftAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            view.justifyLeft();
            fireOnEdit();
        }
    }

    public class JustifyRightAction extends AbstractRTEAction {
        public JustifyRightAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            view.justifyRight();
            fireOnEdit();
        }
    }

    public class OlAction extends AbstractRTEAction {
        public OlAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            view.insertOrderedList();
            fireOnEdit();
        }
    }

    public class PasteAction extends AbstractRTEAction {
        public PasteAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            view.paste();
            fireOnEdit();
        }
    }

    public class RedoAction extends AbstractRTEAction {
        public RedoAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            view.redo();
            fireOnEdit();
        }
    }

    public class RemoveFormatAction extends AbstractRTEAction {
        public RemoveFormatAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            view.removeFormat();
            fireOnEdit();
        }
    }

    public class RemoveLinkAction extends AbstractRTEAction {
        public RemoveLinkAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            deferred.addCommand(new Listener0() {
                public void onEvent() {
                    view.unlink();
                    hideLinkCtxMenu();
                    fireOnEdit();
                }
            });
        }
    }

    public class SelectAllAction extends AbstractRTEAction {
        public SelectAllAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            view.selectAll();
        }
    }

    public class StrikethroughAction extends AbstractRTEAction {
        public StrikethroughAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            view.toggleStrikethrough();
            fireOnEdit();
        }
    }

    public class SubscriptAction extends AbstractRTEAction {
        public SubscriptAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            view.toggleSubscript();
            fireOnEdit();
        }
    }

    public class SuperscriptAction extends AbstractRTEAction {
        public SuperscriptAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            view.toggleSuperscript();
            fireOnEdit();
        }
    }

    public class UlAction extends AbstractRTEAction {
        public UlAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            view.insertUnorderedList();
            fireOnEdit();
        }
    }

    public class UnderlineAction extends AbstractRTEAction {
        public UnderlineAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            view.toggleUnderline();
            fireOnEdit();
        }
    }

    public class UndoAction extends AbstractRTEAction {
        public UndoAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            view.undo();
            fireOnEdit();
        }
    }

    private static final String FONT_NAMES[] = { "Times New Roman", "Arial", "Courier New", "Georgia", "Trebuchet",
            "Verdana" };
    private static final String FONT_SIZE_NAMES[] = { "Extra small", "Very small (normal)", "Small", "Medium", "Large",
            "Very large", "Extra large" };
    private static final RichTextArea.FontSize[] FONT_SIZES = new RichTextArea.FontSize[] {
            RichTextArea.FontSize.XX_SMALL, RichTextArea.FontSize.X_SMALL, RichTextArea.FontSize.SMALL,
            RichTextArea.FontSize.MEDIUM, RichTextArea.FontSize.LARGE, RichTextArea.FontSize.X_LARGE,
            RichTextArea.FontSize.XX_LARGE };
    private static final String FONT_GROUP = "fontgroup";
    private static final String FONT_SIZEGROUP = "fontsizegroup";

    private RTEditorViewNew view;
    private boolean extended;
    private final I18nTranslationService i18n;
    private final Session session;
    private final RTEImgResources imgResources;
    private final Event0 onEdit;
    private final DeferredCommandWrapper deferred;
    private final Provider<ColorWebSafePalette> paletteProvider;
    private final Provider<InsertLinkDialog> insLinkDialogPv;
    private final Provider<EditHtmlDialog> editHtmlDialogPv;
    private final Provider<InsertImageDialog> insImgDialogProv;
    private final Provider<InsertTableDialog> insTableDialogPv;
    private final Provider<InsertSpecialCharDialog> insCharDialogProv;
    private final Provider<InsertMediaDialog> insMediaDialogPv;
    private Listener<String> insTableListener;
    private Listener<LinkInfo> insLinkListener;
    private Listener<ImageInfo> insImgListener;
    private Listener<String> insMediaListener;
    private Listener<String> updHtmlListener;
    protected Listener<String> insCharListener;
    protected ColorWebSafePalette palette;
    private PushButtonDescriptor bold;
    private PushButtonDescriptor italic;
    private PushButtonDescriptor underline;
    private PushButtonDescriptor strikethrough;
    private final GuiAddCondition basicAddCond;
    private final GuiAddCondition extendedAddCond;
    private final InputMap inputMap;
    private MenuDescriptor editMenu;
    private MenuDescriptor insertMenu;
    private MenuDescriptor formatMenu;
    private MenuDescriptor linkCtxMenu;
    private GuiActionDescCollection actions;
    private MenuDescriptor fontMenu;
    private MenuDescriptor fontSizeMenu;
    private MenuDescriptor fileMenu;
    private final List<MenuDescriptor> menus;
    private boolean attached;
    private final Map<String, MenuCheckItemDescriptor> fontActions;
    private MenuCheckItemDescriptor currentFontItem;

    public RTEditorPresenterNew(final I18nTranslationService i18n, final Session session,
            final RTEImgResources imgResources, final Provider<InsertLinkDialog> insLinkDialog,
            final Provider<ColorWebSafePalette> palette, final Provider<EditHtmlDialog> editHtmlDialog,
            final Provider<InsertImageDialog> insertImageDialog, final Provider<InsertMediaDialog> insertMediaDialog,
            final Provider<InsertTableDialog> insertTableDialog, final Provider<InsertSpecialCharDialog> insCharDialog,
            final DeferredCommandWrapper deferred) {
        this.i18n = i18n;
        this.session = session;
        this.insLinkDialogPv = insLinkDialog;
        this.paletteProvider = palette;
        this.editHtmlDialogPv = editHtmlDialog;
        this.insImgDialogProv = insertImageDialog;
        this.insMediaDialogPv = insertMediaDialog;
        this.insTableDialogPv = insertTableDialog;
        this.insCharDialogProv = insCharDialog;
        this.deferred = deferred;
        inputMap = new InputMap();
        menus = new ArrayList<MenuDescriptor>();

        this.imgResources = imgResources;
        this.onEdit = new Event0("onRTEEdit");
        extendedAddCond = new GuiAddCondition() {
            public boolean mustBeAdded() {
                return isAndCanBeExtended();
            }
        };
        basicAddCond = new GuiAddCondition() {
            public boolean mustBeAdded() {
                return view.canBeBasic();
            }
        };
        attached = false;
        fontActions = new HashMap<String, MenuCheckItemDescriptor>();
    }

    public void addAction(final GuiActionDescrip descriptor) {
        actions.add(descriptor);
        checkForMenus(descriptor);
    }

    public void addActions(final GuiActionDescCollection descriptors) {
        actions.addAll(descriptors);
        for (final GuiActionDescrip descriptor : descriptors) {
            checkForMenus(descriptor);
        }
    }

    public void addOnEditListener(final Listener0 listener) {
        onEdit.add(listener);
    }

    public void adjustSize(final int height) {
        view.adjustSize(height);
    }

    public void attach() {
        if (!attached) {
            attached = true;
            view.addActions(actions);
            view.setInputMap(inputMap);
        }
    }

    public void detach() {
        // topBar.clear();
        // sndBar.clear();
    }

    public void fireOnEdit() {
        onEdit.fire();
    }

    public GuiAddCondition getBasicAddCondition() {
        return basicAddCond;
    }

    public MenuDescriptor getEditMenu() {
        return editMenu;
    }

    public View getEditorArea() {
        return view;
    }

    public GuiAddCondition getExtendedAddCondition() {
        return extendedAddCond;
    }

    public MenuDescriptor getFileMenu() {
        return fileMenu;
    }

    public MenuDescriptor getFormatMenu() {
        return formatMenu;
    }

    public String getHtml() {
        return view.getHTML();
    }

    public MenuDescriptor getInsertMenu() {
        return insertMenu;
    }

    public MenuDescriptor getLinkCtxMenu() {
        return linkCtxMenu;
    }

    public View getSndBar() {
        return view.getSndBar();
    }

    public String getText() {
        return view.getText();
    }

    public View getTopBar() {
        return view.getTopBar();
    }

    public void init(final RTEditorViewNew view) {
        this.view = view;
        createMainMenus();
        createBasicActions();
    }

    public void onEditorFocus() {
        hideMenus();
    }

    public void onLostFocus() {
        // Nothing for the moment
    }

    public void reset() {
        hideMenus();
        hideLinkCtxMenu();
    }

    public void setActionShortcut(final KeyStroke key, final AbstractAction action) {
        inputMap.put(key, action);
        action.putValue(Action.ACCELERATOR_KEY, key);
    }

    public void setActionShortcut(final KeyStroke key, final AbstractAction mainAction, final AbstractAction... actions) {
        setActionShortcut(key, mainAction);
        for (final AbstractAction action : actions) {
            action.putValue(Action.ACCELERATOR_KEY, key);
        }
    }

    public void setExtended(final boolean newValue) {
        this.extended = newValue;
    }

    public void setFocus(final boolean focus) {
        view.setFocus(focus);
    }

    public void setHtml(final String html) {
        view.setHTML(html);
        view.focus();
    }

    public void setText(final String text) {
        view.setText(text);
        view.focus();
    }

    public void updateLinkInfo() {
        deferred.addCommand(new Listener0() {
            public void onEvent() {
                if (isAndCanBeExtended() && view.isLink()) {
                    view.showLinkCtxMenu();
                } else {
                    hideLinkCtxMenu();
                }
            }
        });
    }

    public void updateStatus() {
        if (view.canBeBasic()) {
            bold.setPushed(view.isBold());
            italic.setPushed(view.isItalic());
            underline.setPushed(view.isUnderlined());
            deferred.addCommand(new Listener0() {
                public void onEvent() {
                    updateFont();
                    // Log.warn(view.getFontSize());
                }
            });
        }
        if (isAndCanBeExtended()) {
            strikethrough.setPushed(view.isStrikethrough());
        }
    }

    private void checkForMenus(final GuiActionDescrip descriptor) {
        if (descriptor instanceof MenuDescriptor) {
            menus.add((MenuDescriptor) descriptor);
        }
    }

    private void createBasicActions() {

        final MenuSeparatorDescriptor editMenuSep = new MenuSeparatorDescriptor(editMenu);
        final MenuSeparatorDescriptor insertMenuSep = new MenuSeparatorDescriptor(insertMenu);
        final MenuSeparatorDescriptor formatMenuSep = new MenuSeparatorDescriptor(formatMenu);
        insertMenuSep.setAddCondition(extendedAddCond);

        final ToolbarSeparatorDescriptor sndbarSep = new ToolbarSeparatorDescriptor(Type.separator, getSndBar());
        final ToolbarSeparatorDescriptor sndbarSepExt = new ToolbarSeparatorDescriptor(Type.separator, getSndBar());
        sndbarSepExt.setAddCondition(extendedAddCond);

        final SelectAllAction selectAllAction = new SelectAllAction(i18n.t("Select all"), AbstractRTEAction.NO_TEXT,
                imgResources.selectall());
        final MenuItemDescriptor select = new MenuItemDescriptor(editMenu, selectAllAction);
        setActionShortcut(KeyStroke.getKeyStroke(Character.valueOf('A'), Keyboard.MODIFIER_CTRL), selectAllAction);

        final BoldAction boldAction = new BoldAction(NO_TEXT, i18n.t("Bold"), imgResources.bold());
        bold = new PushButtonDescriptor(boldAction);
        setActionShortcut(KeyStroke.getKeyStroke(Character.valueOf('B'), Keyboard.MODIFIER_CTRL), boldAction);

        final ItalicAction italicAction = new ItalicAction(NO_TEXT, i18n.t("Italic"), imgResources.italic());
        italic = new PushButtonDescriptor(italicAction);
        setActionShortcut(KeyStroke.getKeyStroke(Character.valueOf('I'), Keyboard.MODIFIER_CTRL), italicAction);

        final UnderlineAction underlineAction = new UnderlineAction(NO_TEXT, i18n.t("Underline"),
                imgResources.underline());
        underline = new PushButtonDescriptor(underlineAction);
        setActionShortcut(KeyStroke.getKeyStroke(Character.valueOf('U'), Keyboard.MODIFIER_CTRL), underlineAction);

        final SubscriptAction subscriptAction = new SubscriptAction(i18n.t("Subscript"), NO_TEXT,
                imgResources.subscript());
        final MenuItemDescriptor subscript = new MenuItemDescriptor(formatMenu, subscriptAction);
        setActionShortcut(KeyStroke.getKeyStroke(Keyboard.KEY_COMMA, Keyboard.MODIFIER_CTRL), subscriptAction);

        final SuperscriptAction superscriptAction = new SuperscriptAction(i18n.t("Superscript"), NO_TEXT,
                imgResources.superscript());
        final MenuItemDescriptor superscript = new MenuItemDescriptor(formatMenu, superscriptAction);
        setActionShortcut(KeyStroke.getKeyStroke(Keyboard.KEY_PERIOD, Keyboard.MODIFIER_CTRL), superscriptAction);

        final JustifyLeftAction jfyLeftAction = new JustifyLeftAction(NO_TEXT, i18n.t("Left Justify"),
                imgResources.alignleft());
        final ButtonDescriptor justifyLeft = new ButtonDescriptor(jfyLeftAction);
        setActionShortcut(KeyStroke.getKeyStroke(Character.valueOf('L'), Keyboard.MODIFIER_CTRL), jfyLeftAction);

        final JustifyCentreAction jfyCentreAction = new JustifyCentreAction(NO_TEXT, i18n.t("Centre Justify"),
                imgResources.centerpara());
        final ButtonDescriptor justifyCentre = new ButtonDescriptor(jfyCentreAction);
        setActionShortcut(KeyStroke.getKeyStroke(Character.valueOf('E'), Keyboard.MODIFIER_CTRL), jfyCentreAction);

        final JustifyRightAction jfyRightAction = new JustifyRightAction(NO_TEXT, i18n.t("Right Justify"),
                imgResources.alignright());
        final ButtonDescriptor justifyRight = new ButtonDescriptor(jfyRightAction);
        setActionShortcut(KeyStroke.getKeyStroke(Character.valueOf('R'), Keyboard.MODIFIER_CTRL), jfyRightAction);

        final UndoAction undoAction = new UndoAction(i18n.t("Undo"), NO_TEXT, imgResources.undo());
        final UndoAction undoActionBtn = new UndoAction(NO_TEXT, i18n.t("Undo"), imgResources.undo());
        final MenuItemDescriptor undo = new MenuItemDescriptor(editMenu, undoAction);
        final ButtonDescriptor undoBtn = new ButtonDescriptor(undoActionBtn);
        undoBtn.setPosition(0);
        setActionShortcut(KeyStroke.getKeyStroke(Character.valueOf('Z'), Keyboard.MODIFIER_CTRL), undoAction);

        final RedoAction redoAction = new RedoAction(i18n.t("Redo"), NO_TEXT, imgResources.redo());
        final RedoAction redoActionBtn = new RedoAction(NO_TEXT, i18n.t("Redo"), imgResources.redo());
        final MenuItemDescriptor redo = new MenuItemDescriptor(editMenu, redoAction);
        final ButtonDescriptor redoBtn = new ButtonDescriptor(redoActionBtn);
        redoBtn.setPosition(1);
        setActionShortcut(KeyStroke.getKeyStroke(Character.valueOf('Y'), Keyboard.MODIFIER_CTRL), redoAction);

        final CopyAction copyAction = new CopyAction(i18n.t("Copy"), NO_TEXT, imgResources.copy());
        final MenuItemDescriptor copy = new MenuItemDescriptor(editMenu, copyAction);
        setActionShortcut(KeyStroke.getKeyStroke(Character.valueOf('C'), Keyboard.MODIFIER_CTRL), copyAction);

        final CutAction cutAction = new CutAction(i18n.t("Cut"), NO_TEXT, imgResources.cut());
        final MenuItemDescriptor cut = new MenuItemDescriptor(editMenu, cutAction);
        setActionShortcut(KeyStroke.getKeyStroke(Character.valueOf('X'), Keyboard.MODIFIER_CTRL), cutAction);

        final PasteAction pasteAction = new PasteAction(i18n.t("Paste"), NO_TEXT, imgResources.paste());
        final MenuItemDescriptor paste = new MenuItemDescriptor(editMenu, pasteAction);
        setActionShortcut(KeyStroke.getKeyStroke(Character.valueOf('V'), Keyboard.MODIFIER_CTRL), pasteAction);

        final EditHtmlAction editHtmlAction = new EditHtmlAction(i18n.t("Edit HTML"), NO_TEXT, imgResources.edithtml());
        final MenuItemDescriptor editHtml = new MenuItemDescriptor(editMenu, editHtmlAction);
        editHtml.setAddCondition(extendedAddCond);

        final CommentAction commentAction = new CommentAction(i18n.t("Comment"), NO_TEXT, NO_ICON);
        final MenuItemDescriptor comment = new MenuItemDescriptor(insertMenu, commentAction);
        comment.setAddCondition(extendedAddCond);
        setActionShortcut(KeyStroke.getKeyStroke(Character.valueOf('M'), Keyboard.MODIFIER_CTRL), commentAction);

        final HrAction hlineAction = new HrAction(i18n.t("Horizontal line"), NO_TEXT, imgResources.hfixedline());
        final HrAction hlineBtnAction = new HrAction(NO_TEXT, i18n.t("Horizontal line"), imgResources.hfixedline());
        final MenuItemDescriptor hline = new MenuItemDescriptor(insertMenu, hlineAction);
        final ButtonDescriptor hlineBtn = new ButtonDescriptor(hlineBtnAction);
        hline.setAddCondition(extendedAddCond);
        hlineBtn.setAddCondition(extendedAddCond);
        setActionShortcut(KeyStroke.getKeyStroke(' ', Keyboard.MODIFIER_CTRL + Keyboard.MODIFIER_SHIFT), hlineAction);

        final BlockquoteAction blockquoteAction = new BlockquoteAction(i18n.t("Block Quotation"), NO_TEXT, NO_ICON);
        final MenuItemDescriptor blockquote = new MenuItemDescriptor(formatMenu, blockquoteAction);
        blockquote.setAddCondition(extendedAddCond);

        final StrikethroughAction strikeAction = new StrikethroughAction(NO_TEXT, i18n.t("Strikethrough"),
                imgResources.strikeout());
        strikethrough = new PushButtonDescriptor(strikeAction);
        strikethrough.setAddCondition(extendedAddCond);

        final DecreaseIndentAction decreIndentAction = new DecreaseIndentAction(NO_TEXT, i18n.t("Decrease Indent"),
                imgResources.decrementindent());
        final ButtonDescriptor decreaseIndent = new ButtonDescriptor(decreIndentAction);
        decreaseIndent.setAddCondition(extendedAddCond);

        final IncreaseIndentAction increIndentAction = new IncreaseIndentAction(NO_TEXT, i18n.t("Increase Indent"),
                imgResources.incrementindent());
        final ButtonDescriptor increaseIndent = new ButtonDescriptor(increIndentAction);
        increaseIndent.setAddCondition(extendedAddCond);

        final OlAction olistAction = new OlAction(NO_TEXT, i18n.t("Numbered List"), imgResources.defaultnumbering());
        final ButtonDescriptor olist = new ButtonDescriptor(olistAction);
        olist.setAddCondition(extendedAddCond);
        setActionShortcut(KeyStroke.getKeyStroke(Character.valueOf('7'), Keyboard.MODIFIER_CTRL), olistAction);

        final UlAction ulistAction = new UlAction(NO_TEXT, i18n.t("Bullet List"), imgResources.defaultbullet());
        final ButtonDescriptor ulist = new ButtonDescriptor(ulistAction);
        ulist.setAddCondition(extendedAddCond);
        setActionShortcut(KeyStroke.getKeyStroke(Character.valueOf('8'), Keyboard.MODIFIER_CTRL), ulistAction);

        final ImgAction imgAction = new ImgAction(i18n.t("Image..."), NO_TEXT, imgResources.images());
        final ImgAction imgBtnAction = new ImgAction(NO_TEXT, i18n.t("Insert Image"), imgResources.images());
        final MenuItemDescriptor img = new MenuItemDescriptor(insertMenu, imgAction);
        final ButtonDescriptor imgBtn = new ButtonDescriptor(imgBtnAction);
        img.setAddCondition(extendedAddCond);
        imgBtn.setAddCondition(extendedAddCond);

        final InsertMediaAction insertMediaAction = new InsertMediaAction(i18n.t("Audio/Video..."), NO_TEXT,
                imgResources.film());
        final MenuItemDescriptor insertMedia = new MenuItemDescriptor(insertMenu, insertMediaAction);
        insertMedia.setAddCondition(extendedAddCond);

        final CreateOrEditLinkAction editLinkAction = new CreateOrEditLinkAction(i18n.t("Link..."), NO_TEXT,
                imgResources.link());
        final CreateOrEditLinkAction editLinkBtnAction = new CreateOrEditLinkAction(NO_TEXT,
                i18n.t("Create or Edit Link"), imgResources.link());
        final CreateOrEditLinkAction editLinkCtxAction = new CreateOrEditLinkAction(i18n.t("Change"), NO_TEXT,
                imgResources.link());
        final MenuItemDescriptor editLink = new MenuItemDescriptor(insertMenu, editLinkAction);
        final MenuItemDescriptor editLinkCtx = new MenuItemDescriptor(linkCtxMenu, editLinkCtxAction);
        final ButtonDescriptor editLinkBtn = new ButtonDescriptor(editLinkBtnAction);
        editLink.setAddCondition(extendedAddCond);
        editLinkBtn.setAddCondition(extendedAddCond);
        editLinkCtx.setAddCondition(extendedAddCond);
        setActionShortcut(KeyStroke.getKeyStroke(Character.valueOf('K'), Keyboard.MODIFIER_CTRL), editLinkAction,
                editLinkBtnAction);

        final KeyStroke key_SK = KeyStroke.getKeyStroke(Character.valueOf('K'), Keyboard.MODIFIER_CTRL
                + Keyboard.MODIFIER_SHIFT);
        final RemoveLinkAction delLinkBtnAction = new RemoveLinkAction(NO_TEXT, i18n.t("Remove Link"),
                imgResources.linkbreak());
        final RemoveLinkAction delLinkCtxAction = new RemoveLinkAction(i18n.t("Remove"), NO_TEXT,
                imgResources.linkbreak());
        final MenuItemDescriptor removeLinkCtx = new MenuItemDescriptor(linkCtxMenu, delLinkCtxAction);
        final ButtonDescriptor removeLinkBtn = new ButtonDescriptor(delLinkBtnAction);
        removeLinkBtn.setAddCondition(extendedAddCond);
        removeLinkCtx.setAddCondition(extendedAddCond);
        setActionShortcut(key_SK, delLinkBtnAction);

        final RemoveFormatAction remFormatAction = new RemoveFormatAction(i18n.t("Clear Formatting..."), NO_TEXT,
                imgResources.removeFormat());
        final RemoveFormatAction remFormatBtnAc = new RemoveFormatAction(NO_TEXT, i18n.t("Clear Formatting..."),
                imgResources.removeFormat());
        final MenuItemDescriptor removeFormat = new MenuItemDescriptor(formatMenu, remFormatAction);
        final ButtonDescriptor removeFormatBtn = new ButtonDescriptor(remFormatBtnAc);
        removeFormat.setAddCondition(extendedAddCond);
        removeFormatBtn.setAddCondition(extendedAddCond);
        setActionShortcut(KeyStroke.getKeyStroke(Character.valueOf(' '), Keyboard.MODIFIER_CTRL), remFormatAction,
                remFormatBtnAc);

        final InsertSpecialCharAction insCharAction = new InsertSpecialCharAction(i18n.t("Special characters..."),
                NO_TEXT, imgResources.specialchars());
        final MenuItemDescriptor insertSpecialChar = new MenuItemDescriptor(insertMenu, insCharAction);
        insertSpecialChar.setAddCondition(extendedAddCond);

        final InsertTableAction insTableAction = new InsertTableAction(i18n.t("Table..."), NO_TEXT,
                imgResources.inserttable());
        final InsertTableAction insTableBtnAction = new InsertTableAction(NO_TEXT, i18n.t("Insert Table"),
                imgResources.inserttable());
        final MenuItemDescriptor insertTable = new MenuItemDescriptor(insertMenu, insTableAction);
        final ButtonDescriptor insertTableBtn = new ButtonDescriptor(insTableBtnAction);
        insertTable.setAddCondition(extendedAddCond);
        insertTableBtn.setAddCondition(extendedAddCond);

        final FontColorAction fontColorAction = new FontColorAction(NO_TEXT, i18n.t("Text Colour"),
                imgResources.fontcolor());
        final ButtonDescriptor fontColor = new ButtonDescriptor(fontColorAction);
        fontColor.setAddCondition(basicAddCond);

        final BackgroundColorAction backColorAction = new BackgroundColorAction(NO_TEXT,
                i18n.t("Text Background Colour"), imgResources.backcolor());
        final ButtonDescriptor backgroundColor = new ButtonDescriptor(backColorAction);
        backgroundColor.setAddCondition(basicAddCond);

        final DevInfoAction devInfoAction = new DevInfoAction(i18n.t("Developers info"), NO_TEXT,
                imgResources.specialchars());
        final MenuItemDescriptor devInfo = new MenuItemDescriptor(formatMenu, devInfoAction);
        devInfo.setAddCondition(extendedAddCond);
        setActionShortcut(KeyStroke.getKeyStroke(Character.valueOf('I'), Keyboard.MODIFIER_ALT), devInfoAction);

        actions = new GuiActionDescCollection();
        actions.add(fileMenu, editMenu, formatMenu, insertMenu, undo, redo, editMenuSep, copy, cut, paste, editMenuSep,
                select, editMenuSep, editHtml, editLink, img, insertTable, insertMedia, insertMenuSep,
                insertSpecialChar, comment, hline, removeFormat, formatMenuSep, insertMenuSep, undoBtn, redoBtn,
                sndbarSep, bold, italic, underline, strikethrough, sndbarSep, justifyLeft, justifyCentre, justifyRight,
                decreaseIndent, increaseIndent, olist, ulist, sndbarSep, removeFormatBtn, sndbarSepExt, hlineBtn,
                imgBtn, editLinkBtn, removeLinkBtn, insertTableBtn, sndbarSepExt, subscript, superscript, blockquote,
                fontColor, backgroundColor, fontSizeMenu, fontMenu, editLinkCtx, removeLinkCtx);
        // actions.add(devInfo);

        setLocation(TOPBAR, new GuiActionDescrip[] { fileMenu, editMenu, insertMenu, formatMenu, editMenuSep,
                subscript, superscript, undo, redo, editMenuSep, copy, cut, paste, editMenuSep, select, editMenuSep,
                editHtml, comment, hline, blockquote, img, insertTable, insertMedia, editLink, removeFormat,
                formatMenuSep, insertMenuSep, insertSpecialChar, insertTable, devInfo });
        setLocation(SNDBAR, new GuiActionDescrip[] { undoBtn, redoBtn, sndbarSep, sndbarSepExt, bold, italic,
                underline, strikethrough, justifyLeft, justifyCentre, justifyRight, undoBtn, redoBtn, hlineBtn,
                decreaseIndent, increaseIndent, olist, ulist, hlineBtn, imgBtn, editLinkBtn, removeLinkBtn,
                removeFormatBtn, insertTableBtn, fontColor, backgroundColor, fontMenu, fontSizeMenu });
        setLocation(LINKCTX, new GuiActionDescrip[] { removeLinkCtx, editLinkCtx });

        for (final String fontName : FONT_NAMES) {
            createFontAction(fontMenu, fontName);
        }

        for (int fontSize = 0; fontSize < FONT_SIZE_NAMES.length; fontSize++) {
            createFontSizeAction(fontSizeMenu, fontSize);
        }
    }

    private void createFontAction(final MenuDescriptor fontMenu, final String fontName) {
        final FontAction fontAction = new FontAction(fontName, NO_TEXT, NO_ICON);
        final MenuRadioItemDescriptor font = new MenuRadioItemDescriptor(fontMenu, fontAction, FONT_GROUP);
        font.setAddCondition(basicAddCond);
        font.setLocation(SNDBAR);
        fontActions.put(fontName.toLowerCase(), font);
        actions.add(font);
    }

    private void createFontSizeAction(final MenuDescriptor fontSizeMenu, final int fontSize) {
        final String fontSizeName = i18n.t(FONT_SIZE_NAMES[fontSize]);
        final FontSizeAction fontSizeAction = new FontSizeAction(fontSizeName, fontSize, NO_TEXT, NO_ICON);
        final MenuRadioItemDescriptor fontSizeItem = new MenuRadioItemDescriptor(fontSizeMenu, fontSizeAction,
                FONT_SIZEGROUP);
        fontSizeItem.setAddCondition(basicAddCond);
        setActionShortcut(KeyStroke.getKeyStroke(Character.valueOf(((char) (48 + fontSize))), Keyboard.MODIFIER_CTRL),
                fontSizeAction);
        fontSizeItem.setLocation(SNDBAR);
        actions.add(fontSizeItem);
    }

    private void createMainMenus() {
        menus.add(fileMenu = new MenuDescriptor(i18n.t("File")));
        menus.add(editMenu = new MenuDescriptor(i18n.t("Edit")));
        menus.add(insertMenu = new MenuDescriptor(i18n.t("Insert")));
        menus.add(formatMenu = new MenuDescriptor(i18n.t("Format")));
        menus.add(linkCtxMenu = new MenuDescriptor(i18n.t("Change Link")));
        menus.add(fontMenu = new MenuDescriptor(NO_TEXT, i18n.t("Font"), imgResources.charfontname()));
        menus.add(fontSizeMenu = new MenuDescriptor(NO_TEXT, i18n.t("Font size"), imgResources.fontheight()));
        insertMenu.setAddCondition(extendedAddCond);
    }

    private void createPalette() {
        if (palette == null) {
            palette = paletteProvider.get();
        }
    }

    private void hideLinkCtxMenu() {
        if (view.isCtxMenuVisible()) {
            view.hideLinkCtxMenu();
        }
    }

    private void hideMenus() {
        for (final MenuDescriptor menu : menus) {
            menu.hide();
        }
        if (palette != null) {
            palette.hide();
        }
    }

    private boolean isAndCanBeExtended() {
        return extended && view.canBeExtended();
    }

    private void setLocation(final String location, final GuiActionDescrip[] descripts) {
        for (final GuiActionDescrip descript : descripts) {
            descript.setLocation(location);
        }
    }

    private void updateFont() {
        final String currentFont = view.getFont();
        final MenuCheckItemDescriptor item = fontActions.get(currentFont);
        if (currentFontItem != null && item != currentFontItem) {
            currentFontItem.setChecked(false);
        }
        if (item != null) {
            item.setChecked(true);
            currentFontItem = item;
            fontMenu.setText((String) item.getValue(FontAction.FONT_NAME));
        } else {
            fontMenu.setText("&nbsp;");
        }
    }
}
