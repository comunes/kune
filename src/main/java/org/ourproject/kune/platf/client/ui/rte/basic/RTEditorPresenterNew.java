package org.ourproject.kune.platf.client.ui.rte.basic;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.AbstractAction;
import org.ourproject.kune.platf.client.actions.Action;
import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.actions.InputMap;
import org.ourproject.kune.platf.client.actions.KeyStroke;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbar;
import org.ourproject.kune.platf.client.actions.ui.AbstractGuiActionDescrip;
import org.ourproject.kune.platf.client.actions.ui.ButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ui.GuiActionCollection;
import org.ourproject.kune.platf.client.actions.ui.GuiAddCondition;
import org.ourproject.kune.platf.client.actions.ui.MenuDescriptor;
import org.ourproject.kune.platf.client.actions.ui.MenuItemDescriptor;
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
import com.google.gwt.user.client.ui.KeyboardListener;

public class RTEditorPresenterNew implements RTEditorNew {

    public abstract class AbstractRTEAction extends AbstractAction {

        public AbstractRTEAction() {
            super();
        }

        public AbstractRTEAction(final String text) {
            this(text, null, null);
        }

        public AbstractRTEAction(final String text, final ImageResource icon) {
            this(text, null, icon);
        }

        public AbstractRTEAction(final String text, final String tooltip, final ImageResource icon) {
            super();
            super.putValue(Action.NAME, text);
            super.putValue(Action.SHORT_DESCRIPTION, tooltip);
            super.putValue(Action.SMALL_ICON, icon);
        }
    }

    public class BackgroundColorAction extends AbstractRTEAction {

        public BackgroundColorAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            getPalette();
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
        private transient EditHtmlDialog editHtmlDialog;

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
        private final transient String fontName;

        public FontAction(final String fontName, final String tooltip, final ImageResource icon) {
            super("<span style=\"font-family: " + fontName + "\">" + fontName + "</span>", tooltip, icon);
            this.fontName = fontName;
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            view.setFontName(fontName);
            fontMenu.setText(fontName);
            fireOnEdit();
        }
    }

    public class FontColorAction extends AbstractRTEAction {
        public FontColorAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            getPalette();
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
        private final transient String fontSizeName;
        private final transient int fontSize;

        public FontSizeAction(final String fontSizeName, final int fontSize, final String tooltip,
                final ImageResource icon) {
            super("<font size=\"" + (fontSize + 1) + "\">" + fontSizeName + "</font>", tooltip, icon);
            this.fontSizeName = fontSizeName;
            this.fontSize = fontSize;
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            view.setFontSize(FONT_SIZES[fontSize]);
            fireOnEdit();
            fontSizeMenu.setText(fontSizeName);
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
        private transient InsertSpecialCharDialog insCharDialog;

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
        private transient InsertTableDialog insTableDialog;

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

    private static final String NONE = null;
    private static final ImageResource NO_ICON = null;

    private transient RTEditorViewNew view;
    private boolean extended;
    private final transient I18nTranslationService i18n;
    private final transient Session session;
    private final transient RTEImgResources imgResources;
    private final transient RTEActionTopToolbar topBar;
    private final transient RTEActionSndToolbar sndBar;
    private final transient Event0 onEdit;
    private final transient DeferredCommandWrapper deferred;
    private final transient Provider<ColorWebSafePalette> paletteProvider;
    private final transient Provider<InsertLinkDialog> insLinkDialogPv;
    private final transient Provider<EditHtmlDialog> editHtmlDialogPv;
    private final transient Provider<InsertImageDialog> insImgDialogProv;
    private final transient Provider<InsertTableDialog> insTableDialogPv;
    private final transient Provider<InsertSpecialCharDialog> insCharDialogProv;
    private final transient Provider<InsertMediaDialog> insMediaDialogPv;
    private transient Listener<String> insTableListener;
    private transient Listener<LinkInfo> insLinkListener;
    private transient Listener<ImageInfo> insImgListener;
    private transient Listener<String> insMediaListener;
    private transient Listener<String> updHtmlListener;
    protected transient Listener<String> insCharListener;
    protected transient ColorWebSafePalette palette;
    private transient PushButtonDescriptor bold;
    private transient PushButtonDescriptor italic;
    private transient PushButtonDescriptor underline;
    private transient PushButtonDescriptor strikethrough;
    private transient final GuiAddCondition basicAddCond;
    private transient final GuiAddCondition extendedAddCond;
    private transient final InputMap inputMap;
    private transient MenuDescriptor editMenu;
    private transient MenuDescriptor insertMenu;
    private transient MenuDescriptor formatMenu;
    private transient MenuDescriptor linkCtxMenu;
    private transient GuiActionCollection actions;
    private transient MenuDescriptor fontMenu;
    private transient MenuDescriptor fontSizeMenu;

    public RTEditorPresenterNew(final I18nTranslationService i18n, final Session session,
            final RTEActionTopToolbar topBar, final RTEActionSndToolbar sndBar, final RTEImgResources imgResources,
            final Provider<InsertLinkDialog> insLinkDialog, final Provider<ColorWebSafePalette> palette,
            final Provider<EditHtmlDialog> editHtmlDialog, final Provider<InsertImageDialog> insertImageDialog,
            final Provider<InsertMediaDialog> insertMediaDialog, final Provider<InsertTableDialog> insertTableDialog,
            final Provider<InsertSpecialCharDialog> insCharDialog, final DeferredCommandWrapper deferred) {
        this.i18n = i18n;
        this.session = session;
        this.topBar = topBar;
        this.sndBar = sndBar;
        this.insLinkDialogPv = insLinkDialog;
        this.paletteProvider = palette;
        this.editHtmlDialogPv = editHtmlDialog;
        this.insImgDialogProv = insertImageDialog;
        this.insMediaDialogPv = insertMediaDialog;
        this.insTableDialogPv = insertTableDialog;
        this.insCharDialogProv = insCharDialog;
        this.deferred = deferred;
        inputMap = new InputMap();

        styleToolbar(sndBar);
        sndBar.attach();
        this.imgResources = imgResources;
        extended = true;
        this.onEdit = new Event0("onRTEEdit");
        extendedAddCond = new GuiAddCondition() {
            public boolean mustBeAdded() {
                return isExtended();
            }
        };
        basicAddCond = new GuiAddCondition() {
            public boolean mustBeAdded() {
                return view.canBeBasic();
            }
        };
    }

    public void addAction(final AbstractGuiActionDescrip descriptor) {
        actions.add(descriptor);
    }

    public void addActions(final GuiActionCollection actioncollection) {
        actions.addAll(actioncollection);
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
        // FIXME
        // topBar.addActions(actions, TOPBAR);
        // sndBar.addActions(actions, SNDBAR);
        // view.addActions(actions);
    }

    public void detach() {
        topBar.clear();
        sndBar.clear();
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

    public ActionToolbar<Object> getSndBar() {
        return sndBar;
    }

    public String getText() {
        return view.getText();
    }

    public ActionToolbar<Object> getTopBar() {
        return topBar;
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

    public void setExtended(final boolean extended) {
        this.extended = extended;
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
                if (isExtended() && view.isLink()) {
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
        }
        if (isExtended()) {
            strikethrough.setPushed(view.isStrikethrough());
        }
    }

    private void crateFontAction(final MenuDescriptor fontMenu, final String fontName) {
        final FontAction fontAction = new FontAction(fontName, NONE, NO_ICON);
        final MenuItemDescriptor font = new MenuItemDescriptor(fontMenu, fontAction);
        font.setAddCondition(basicAddCond);
        font.setLocation(SNDBAR);
        actions.add(font);
    }

    private void createBasicActions() {

        final MenuSeparatorDescriptor editMenuSep = new MenuSeparatorDescriptor(editMenu);
        final MenuSeparatorDescriptor insertMenuSep = new MenuSeparatorDescriptor(insertMenu);
        final MenuSeparatorDescriptor formatMenuSep = new MenuSeparatorDescriptor(formatMenu);

        final ToolbarSeparatorDescriptor separator = new ToolbarSeparatorDescriptor(Type.separator);

        final SelectAllAction selectAllAction = new SelectAllAction(i18n.t("Select all"), NONE,
                imgResources.selectall());
        final MenuItemDescriptor select = new MenuItemDescriptor(editMenu, selectAllAction);
        setActionShortcut(KeyStroke.getKeyStroke('A', KeyboardListener.MODIFIER_CTRL), selectAllAction);

        final BoldAction boldAction = new BoldAction(NONE, i18n.t("Bold"), imgResources.bold());
        bold = new PushButtonDescriptor(boldAction);
        setActionShortcut(KeyStroke.getKeyStroke('B', KeyboardListener.MODIFIER_CTRL), boldAction);

        final ItalicAction italicAction = new ItalicAction(NONE, i18n.t("Italic"), imgResources.italic());
        italic = new PushButtonDescriptor(italicAction);
        setActionShortcut(KeyStroke.getKeyStroke('I', KeyboardListener.MODIFIER_CTRL), italicAction);

        final UnderlineAction underlineAction = new UnderlineAction(NONE, i18n.t("Underline"), imgResources.underline());
        underline = new PushButtonDescriptor(underlineAction);
        setActionShortcut(KeyStroke.getKeyStroke('U', KeyboardListener.MODIFIER_CTRL), underlineAction);

        final SubscriptAction subscriptAction = new SubscriptAction(i18n.t("Subscript"), NONE, imgResources.subscript());
        final MenuItemDescriptor subscript = new MenuItemDescriptor(formatMenu, subscriptAction);
        setActionShortcut(KeyStroke.getKeyStroke(Keyboard.KEY_COMMA, KeyboardListener.MODIFIER_CTRL), subscriptAction);

        final SuperscriptAction superscriptAction = new SuperscriptAction(i18n.t("Superscript"), NONE,
                imgResources.superscript());
        final MenuItemDescriptor superscript = new MenuItemDescriptor(formatMenu, superscriptAction);
        setActionShortcut(KeyStroke.getKeyStroke(Keyboard.KEY_PERIOD, KeyboardListener.MODIFIER_CTRL),
                superscriptAction);

        final JustifyLeftAction jfyLeftAction = new JustifyLeftAction(NONE, i18n.t("Left Justify"),
                imgResources.alignleft());
        final ButtonDescriptor justifyLeft = new ButtonDescriptor(jfyLeftAction);
        setActionShortcut(KeyStroke.getKeyStroke('L', KeyboardListener.MODIFIER_CTRL), jfyLeftAction);

        final JustifyCentreAction jfyCentreAction = new JustifyCentreAction(NONE, i18n.t("Centre Justify"),
                imgResources.centerpara());
        final ButtonDescriptor justifyCentre = new ButtonDescriptor(jfyCentreAction);
        setActionShortcut(KeyStroke.getKeyStroke('E', KeyboardListener.MODIFIER_CTRL), jfyCentreAction);

        final JustifyRightAction jfyRightAction = new JustifyRightAction(NONE, i18n.t("Right Justify"),
                imgResources.alignright());
        final ButtonDescriptor justifyRight = new ButtonDescriptor(jfyRightAction);
        setActionShortcut(KeyStroke.getKeyStroke('R', KeyboardListener.MODIFIER_CTRL), jfyRightAction);

        final UndoAction undoAction = new UndoAction(i18n.t("Undo"), NONE, imgResources.undo());
        final UndoAction undoActionBtn = new UndoAction(NONE, i18n.t("Undo"), imgResources.undo());
        final MenuItemDescriptor undo = new MenuItemDescriptor(editMenu, undoAction);
        final ButtonDescriptor undoBtn = new ButtonDescriptor(undoActionBtn);
        undoBtn.setPosition(0);
        setActionShortcut(KeyStroke.getKeyStroke(Keyboard.KEY_Z, KeyboardListener.MODIFIER_CTRL), undoAction);

        final RedoAction redoAction = new RedoAction(i18n.t("Redo"), NONE, imgResources.redo());
        final RedoAction redoActionBtn = new RedoAction(NONE, i18n.t("Redo"), imgResources.redo());
        final MenuItemDescriptor redo = new MenuItemDescriptor(editMenu, redoAction);
        final ButtonDescriptor redoBtn = new ButtonDescriptor(redoActionBtn);
        redoBtn.setPosition(1);
        setActionShortcut(KeyStroke.getKeyStroke(Keyboard.KEY_Y, KeyboardListener.MODIFIER_CTRL), redoAction);

        final CopyAction copyAction = new CopyAction(i18n.t("Copy"), NONE, imgResources.copy());
        final MenuItemDescriptor copy = new MenuItemDescriptor(editMenu, copyAction);
        setActionShortcut(KeyStroke.getKeyStroke(Keyboard.KEY_C, KeyboardListener.MODIFIER_CTRL), copyAction);

        final CutAction cutAction = new CutAction(i18n.t("Cut"), NONE, imgResources.cut());
        final MenuItemDescriptor cut = new MenuItemDescriptor(editMenu, cutAction);
        setActionShortcut(KeyStroke.getKeyStroke(Keyboard.KEY_X, KeyboardListener.MODIFIER_CTRL), cutAction);

        final PasteAction pasteAction = new PasteAction(i18n.t("Paste"), NONE, imgResources.paste());
        final MenuItemDescriptor paste = new MenuItemDescriptor(editMenu, pasteAction);
        setActionShortcut(KeyStroke.getKeyStroke(Keyboard.KEY_V, KeyboardListener.MODIFIER_CTRL), pasteAction);

        final EditHtmlAction editHtmlAction = new EditHtmlAction(i18n.t("Edit HTML"), NONE, imgResources.edithtml());
        final MenuItemDescriptor editHtml = new MenuItemDescriptor(editMenu, editHtmlAction);
        editHtml.setAddCondition(extendedAddCond);

        final CommentAction commentAction = new CommentAction(i18n.t("Comment"), NONE, NO_ICON);
        final MenuItemDescriptor comment = new MenuItemDescriptor(insertMenu, commentAction);
        comment.setAddCondition(extendedAddCond);
        setActionShortcut(KeyStroke.getKeyStroke(Keyboard.KEY_M, KeyboardListener.MODIFIER_CTRL), commentAction);

        final HrAction hlineAction = new HrAction(i18n.t("Horizontal line"), NONE, imgResources.hfixedline());
        final HrAction hlineBtnAction = new HrAction(NONE, i18n.t("Horizontal line"), imgResources.hfixedline());
        final MenuItemDescriptor hline = new MenuItemDescriptor(insertMenu, hlineAction);
        final ButtonDescriptor hlineBtn = new ButtonDescriptor(hlineBtnAction);
        hline.setAddCondition(extendedAddCond);
        hlineBtn.setAddCondition(extendedAddCond);
        setActionShortcut(
                KeyStroke.getKeyStroke(' ', KeyboardListener.MODIFIER_CTRL & KeyboardListener.MODIFIER_SHIFT),
                hlineAction);

        final BlockquoteAction blockquoteAction = new BlockquoteAction(i18n.t("Block Quotation"), NONE,
                imgResources.hfixedline());
        final MenuItemDescriptor blockquote = new MenuItemDescriptor(formatMenu, blockquoteAction);
        blockquote.setAddCondition(extendedAddCond);

        final StrikethroughAction strikeAction = new StrikethroughAction(NONE, i18n.t("Strikethrough"),
                imgResources.strikeout());
        strikethrough = new PushButtonDescriptor(strikeAction);
        strikethrough.setAddCondition(extendedAddCond);

        final DecreaseIndentAction decreIndentAction = new DecreaseIndentAction(NONE, i18n.t("Decrease Indent"),
                imgResources.decrementindent());
        final ButtonDescriptor decreaseIndent = new ButtonDescriptor(decreIndentAction);
        decreaseIndent.setAddCondition(extendedAddCond);

        final IncreaseIndentAction increIndentAction = new IncreaseIndentAction(NONE, i18n.t("Increase Indent"),
                imgResources.incrementindent());
        final ButtonDescriptor increaseIndent = new ButtonDescriptor(increIndentAction);
        increaseIndent.setAddCondition(extendedAddCond);

        final OlAction olistAction = new OlAction(NONE, i18n.t("Numbered List"), imgResources.defaultnumbering());
        final ButtonDescriptor olist = new ButtonDescriptor(olistAction);
        olist.setAddCondition(extendedAddCond);
        setActionShortcut(KeyStroke.getKeyStroke(Keyboard.KEY_7, KeyboardListener.MODIFIER_CTRL), olistAction);

        final UlAction ulistAction = new UlAction(NONE, i18n.t("Bullet List"), imgResources.defaultbullet());
        final ButtonDescriptor ulist = new ButtonDescriptor(ulistAction);
        ulist.setAddCondition(extendedAddCond);
        setActionShortcut(KeyStroke.getKeyStroke(Keyboard.KEY_8, KeyboardListener.MODIFIER_CTRL), ulistAction);

        final ImgAction imgAction = new ImgAction(i18n.t("Image..."), NONE, imgResources.images());
        final ImgAction imgBtnAction = new ImgAction(NONE, i18n.t("Insert Image"), imgResources.images());
        final MenuItemDescriptor img = new MenuItemDescriptor(insertMenu, imgAction);
        final ButtonDescriptor imgBtn = new ButtonDescriptor(imgBtnAction);
        img.setAddCondition(extendedAddCond);
        imgBtn.setAddCondition(extendedAddCond);

        final InsertMediaAction insertMediaAction = new InsertMediaAction(i18n.t("Audio/Video..."), NONE,
                imgResources.film());
        final MenuItemDescriptor insertMedia = new MenuItemDescriptor(insertMenu, insertMediaAction);
        insertMedia.setAddCondition(extendedAddCond);

        final CreateOrEditLinkAction editLinkAction = new CreateOrEditLinkAction(i18n.t("Link..."), NONE,
                imgResources.link());
        final CreateOrEditLinkAction editLinkBtnAction = new CreateOrEditLinkAction(NONE,
                i18n.t("Create or Edit Link"), imgResources.link());
        final CreateOrEditLinkAction editLinkCtxAction = new CreateOrEditLinkAction(i18n.t("Change"), NONE,
                imgResources.link());
        final MenuItemDescriptor editLink = new MenuItemDescriptor(insertMenu, editLinkAction);
        final MenuItemDescriptor editLinkCtx = new MenuItemDescriptor(linkCtxMenu, editLinkCtxAction);
        final ButtonDescriptor editLinkBtn = new ButtonDescriptor(editLinkBtnAction);
        editLink.setAddCondition(extendedAddCond);
        editLinkBtn.setAddCondition(extendedAddCond);
        editLinkCtx.setAddCondition(extendedAddCond);
        setActionShortcut(KeyStroke.getKeyStroke(Keyboard.KEY_K, KeyboardListener.MODIFIER_CTRL), editLinkAction,
                editLinkBtnAction);

        final KeyStroke key_K = KeyStroke.getKeyStroke(Keyboard.KEY_K, KeyboardListener.MODIFIER_CTRL
                & KeyboardListener.MODIFIER_SHIFT);
        final RemoveLinkAction delLinkBtnAction = new RemoveLinkAction(NONE, i18n.t("Remove Link"),
                imgResources.linkbreak());
        final RemoveLinkAction delLinkCtxAction = new RemoveLinkAction(i18n.t("Remove"), NONE, imgResources.linkbreak());
        final MenuItemDescriptor removeLinkCtx = new MenuItemDescriptor(linkCtxMenu, delLinkCtxAction);
        final ButtonDescriptor removeLinkBtn = new ButtonDescriptor(delLinkBtnAction);
        removeLinkBtn.setAddCondition(extendedAddCond);
        removeLinkCtx.setAddCondition(extendedAddCond);
        setActionShortcut(key_K, delLinkBtnAction);

        final RemoveFormatAction remFormatAction = new RemoveFormatAction(i18n.t("Clear Formatting..."), NONE,
                imgResources.removeFormat());
        final RemoveFormatAction remFormatBtnAc = new RemoveFormatAction(NONE, i18n.t("Clear Formatting..."),
                imgResources.removeFormat());
        final MenuItemDescriptor removeFormat = new MenuItemDescriptor(formatMenu, remFormatAction);
        final ButtonDescriptor removeFormatBtn = new ButtonDescriptor(remFormatBtnAc);
        removeFormat.setAddCondition(extendedAddCond);
        removeFormatBtn.setAddCondition(extendedAddCond);
        setActionShortcut(KeyStroke.getKeyStroke(' ', KeyboardListener.MODIFIER_CTRL), remFormatAction, remFormatBtnAc);

        final InsertSpecialCharAction insCharAction = new InsertSpecialCharAction(i18n.t("Special characters..."),
                NONE, imgResources.specialchars());
        final MenuItemDescriptor insertSpecialChar = new MenuItemDescriptor(insertMenu, insCharAction);
        insertSpecialChar.setAddCondition(extendedAddCond);

        final InsertTableAction insTableAction = new InsertTableAction(i18n.t("Table..."), NONE,
                imgResources.inserttable());
        final InsertTableAction insTableBtnAction = new InsertTableAction(NONE, i18n.t("Insert Table"),
                imgResources.inserttable());
        final MenuItemDescriptor insertTable = new MenuItemDescriptor(insertMenu, insTableAction);
        final ButtonDescriptor insertTableBtn = new ButtonDescriptor(insTableBtnAction);
        insertTable.setAddCondition(extendedAddCond);
        insertTableBtn.setAddCondition(extendedAddCond);

        final FontColorAction fontColorAction = new FontColorAction(NONE, i18n.t("Text Colour"),
                imgResources.fontcolor());
        final ButtonDescriptor fontColor = new ButtonDescriptor(fontColorAction);
        fontColor.setAddCondition(extendedAddCond);

        final BackgroundColorAction backColorAction = new BackgroundColorAction(NONE, i18n.t("Text Background Colour"),
                imgResources.backcolor());
        final ButtonDescriptor backgroundColor = new ButtonDescriptor(backColorAction);
        backgroundColor.setAddCondition(basicAddCond);

        final DevInfoAction devInfoAction = new DevInfoAction(i18n.t("Developers info"), NONE,
                imgResources.specialchars());
        final MenuItemDescriptor devInfo = new MenuItemDescriptor(formatMenu, devInfoAction);
        devInfo.setAddCondition(extendedAddCond);
        setActionShortcut(KeyStroke.getKeyStroke(Keyboard.KEY_I, KeyboardListener.MODIFIER_ALT), devInfoAction);

        fontMenu = new MenuDescriptor(NONE, i18n.t("Font"), imgResources.charfontname());
        fontSizeMenu = new MenuDescriptor(NONE, i18n.t("Font size"), imgResources.fontheight());

        actions = new GuiActionCollection();
        actions.add(editMenuSep, subscript, superscript, undo, redo, editMenuSep, copy, cut, paste, editMenuSep,
                select, editHtml, comment, hline, blockquote, img, insertTable, insertMedia, editLink, removeFormat,
                formatMenuSep, insertMenuSep, insertSpecialChar, insertTable, devInfo, undoBtn, redoBtn, separator,
                bold, italic, underline, strikethrough, justifyLeft, justifyCentre, justifyRight, undoBtn, redoBtn,
                hlineBtn, separator, decreaseIndent, increaseIndent, olist, ulist, separator, hlineBtn, imgBtn,
                editLinkBtn, removeLinkBtn, removeFormatBtn, separator, insertTableBtn, separator, fontColor,
                backgroundColor);

        setLocation(TOPBAR, new AbstractGuiActionDescrip[] { editMenuSep, subscript, superscript, undo, redo,
                editMenuSep, copy, cut, paste, editMenuSep, select, editHtml, comment, hline, blockquote, img,
                insertTable, insertMedia, editLink, removeFormat, formatMenuSep, insertMenuSep, insertSpecialChar,
                insertTable, devInfo });
        setLocation(SNDBAR, new AbstractGuiActionDescrip[] { undoBtn, redoBtn, separator, bold, italic, underline,
                strikethrough, justifyLeft, justifyCentre, justifyRight, undoBtn, redoBtn, hlineBtn, separator,
                decreaseIndent, increaseIndent, olist, ulist, separator, hlineBtn, imgBtn, editLinkBtn, removeLinkBtn,
                removeFormatBtn, separator, insertTableBtn, separator, fontColor, backgroundColor });
        setLocation(LINKCTX, new AbstractGuiActionDescrip[] { editLinkCtx, removeLinkCtx });

        for (final String fontName : FONT_NAMES) {
            crateFontAction(fontMenu, fontName);
        }

        for (int fontSize = 0; fontSize < FONT_SIZE_NAMES.length; fontSize++) {
            createFontSizeAction(fontSizeMenu, fontSize);
        }
    }

    private void createFontSizeAction(final MenuDescriptor fontSizeMenu, final int fontSize) {
        final FontSizeAction fontSizeAction = new FontSizeAction(i18n.t(FONT_SIZE_NAMES[fontSize]), fontSize, NONE,
                NO_ICON);
        final MenuItemDescriptor fontSizeItem = new MenuItemDescriptor(fontSizeMenu, fontSizeAction);
        fontSizeItem.setAddCondition(basicAddCond);
        setActionShortcut(KeyStroke.getKeyStroke(48 + fontSize, KeyboardListener.MODIFIER_CTRL), fontSizeAction);
        fontSizeItem.setLocation(SNDBAR);
        actions.add(fontSizeItem);
    }

    private void createMainMenus() {
        editMenu = new MenuDescriptor(i18n.t("Edit"));
        insertMenu = new MenuDescriptor(i18n.t("Insert"));
        formatMenu = new MenuDescriptor(i18n.t("Format"));
        linkCtxMenu = new MenuDescriptor(i18n.t("Change Link"));
    }

    private void getPalette() {
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
        topBar.hideAllMenus();
        sndBar.hideAllMenus();
        if (palette != null) {
            palette.hide();
        }
    }

    private boolean isExtended() {
        return extended && view.canBeExtended();
    }

    private void setActionShortcut(final KeyStroke key, final AbstractAction mainAction,
            final AbstractAction... actions) {
        inputMap.put(key, mainAction);
        mainAction.putValue(Action.ACCELERATOR_KEY, key);
        for (final AbstractAction action : actions) {
            action.putValue(Action.ACCELERATOR_KEY, key);
        }
    }

    private void setLocation(final String location, final AbstractGuiActionDescrip[] descripts) {
        for (final AbstractGuiActionDescrip descript : descripts) {
            descript.setLocation(location);
        }
    }

    private void styleToolbar(final ActionToolbar<Object> bar) {
        bar.setNormalStyle();
    }
}
