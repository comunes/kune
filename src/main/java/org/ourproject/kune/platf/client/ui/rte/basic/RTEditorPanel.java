package org.ourproject.kune.platf.client.ui.rte.basic;

import static com.google.gwt.user.client.ui.KeyboardListener.KEY_DOWN;
import static com.google.gwt.user.client.ui.KeyboardListener.KEY_END;
import static com.google.gwt.user.client.ui.KeyboardListener.KEY_ESCAPE;
import static com.google.gwt.user.client.ui.KeyboardListener.KEY_HOME;
import static com.google.gwt.user.client.ui.KeyboardListener.KEY_LEFT;
import static com.google.gwt.user.client.ui.KeyboardListener.KEY_PAGEDOWN;
import static com.google.gwt.user.client.ui.KeyboardListener.KEY_PAGEUP;
import static com.google.gwt.user.client.ui.KeyboardListener.KEY_RIGHT;
import static com.google.gwt.user.client.ui.KeyboardListener.KEY_UP;

import java.util.Date;

import org.ourproject.kune.platf.client.actions.ActionDescriptor;
import org.ourproject.kune.platf.client.actions.ActionItem;
import org.ourproject.kune.platf.client.actions.ActionItemCollection;
import org.ourproject.kune.platf.client.actions.ActionManager;
import org.ourproject.kune.platf.client.i18n.I18nUITranslationService;
import org.ourproject.kune.platf.client.shortcuts.GlobalShortcutRegister;
import org.ourproject.kune.platf.client.shortcuts.ShortcutDescriptor;
import org.ourproject.kune.platf.client.shortcuts.ShortcutRegister;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.platf.client.ui.rte.RichTextArea;
import org.ourproject.kune.platf.client.ui.rte.insertlink.LinkExecutableUtils;
import org.ourproject.kune.platf.client.ui.rte.insertlink.LinkInfo;
import org.xwiki.gwt.dom.client.DocumentFragment;
import org.xwiki.gwt.dom.client.Range;
import org.xwiki.gwt.dom.client.Selection;

import com.calclab.suco.client.events.Listener0;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.Widget;

public class RTEditorPanel extends RichTextArea implements RTEditorView {

    private class EventListener implements FocusListener {

        public void onFocus(final Widget sender) {
            presenter.onEditorFocus();
        }

        public void onLostFocus(final Widget sender) {
            presenter.onLostFocus();
        }
    }
    private final I18nUITranslationService i18n;
    private final BasicFormatter basic;
    private final ExtendedFormatter extended;
    private final RTEditorPresenter presenter;
    private final ActionManager actionManager;
    private final ShortcutRegister shortcutRegister;
    private final GlobalShortcutRegister globalShortcutReg;
    private final RTELinkPopup linkCtxMenu;

    public RTEditorPanel(final RTEditorPresenter presenter, final I18nUITranslationService i18n,
            final ActionManager actionManager, final GlobalShortcutRegister globalShortcutReg) {
        this.presenter = presenter;
        this.i18n = i18n;
        this.actionManager = actionManager;
        this.globalShortcutReg = globalShortcutReg;
        basic = getBasicFormatter();
        extended = getExtendedFormatter();
        shortcutRegister = new ShortcutRegister();
        EventListener listener = new EventListener();
        addFocusListener(listener);
        setWidth("96%");
        setHeight("100%");
        linkCtxMenu = new RTELinkPopup();
    }

    public void addActions(final ActionItemCollection<Object> actionItems) {
        for (ActionItem<Object> actionItem : actionItems) {
            ActionDescriptor<Object> action = actionItem.getAction();
            if (action.hasShortcut() && action.mustBeAdded(null)) {
                ShortcutDescriptor shortcut = action.getShortcut();
                shortcutRegister.put(shortcut, actionItem);
            }
        }
    }

    public void addCtxAction(final ActionItem<Object> actionItem) {
        linkCtxMenu.addAction(actionItem, new Listener0() {
            public void onEvent() {
                actionManager.doAction(actionItem);
            }
        });
    }

    public void adjustSize(final int height) {
        setHeight("" + height);
    }

    public boolean canBeBasic() {
        return basic != null;
    }

    public boolean canBeExtended() {
        return extended != null;
    }

    public void copy() {
        extended.copy();
    }

    public void createLink(final String url) {
        extended.createLink(url);
    }

    public void cut() {
        extended.cut();
    }

    public void delete() {
        extended.delete();
    }

    public void focus() {
        setFocus(true);
    }

    public LinkInfo getLinkInfoIfHref() {
        LinkInfo linkinfo = null;
        org.xwiki.gwt.dom.client.Element selectedAnchor = LinkExecutableUtils.getSelectedAnchor(this);
        if (selectedAnchor != null) {
            Range range = getDocument().createRange();
            range.selectNode(selectedAnchor);
            getSelection().addRange(range);
            linkinfo = LinkInfo.parse(selectedAnchor);
        } else {
            linkinfo = new LinkInfo(getSelectionText());
        }
        return linkinfo;
    }

    public void getRangeInfo() {
        // Selection selection = getSelection();
        // String info = "range count: " + selection.getRangeCount() +
        // "<br/>focus offset: " + selection.getFocusOffset()
        // + "<br/>anchor offset:" + selection.getAnchorOffset() +
        // "<br/>range 0 as html: "
        // + selection.getRangeAt(0).toHTML();
        // NotifyUser.info(info);
        String info = "range count: " + getFstRange().getCommonAncestorContainer().getFirstChild().getNodeName();
        NotifyUser.info(info);
    }

    public String getSelectionText() {
        return getFstRange().cloneContents().getInnerText();
    }

    public void hideLinkCtxMenu() {
        linkCtxMenu.hide();

    }

    public void insertBlockquote() {
        DocumentFragment extracted = getFstRange().cloneContents();
        // delete();
        insertHtml("<blockquote>" + extracted.getInnerHTML() + "</blockquote>");
        focus();
    }

    public void insertComment(final String author) {
        String comment = null;
        createCommentAndSelectIt(author, comment);
    }

    public void insertCommentNotUsingSelection(final String author) {
        getFstRange().collapse(false);
        createCommentAndSelectIt(author, null);
        focus();
    }

    public void insertCommentUsingSelection(final String author) {
        DocumentFragment extracted = getFstRange().cloneContents();
        extended.delete();
        String comment = extracted.getInnerText();
        createCommentAndSelectIt(author, comment);
        focus();
    }

    public void insertHorizontalRule() {
        extended.insertHorizontalRule();
    }

    public void insertHtml(final String html) {
        extended.insertHtml(html);
    }

    public void insertImage(final String url) {
        extended.insertImage(url);
    }

    public void insertOrderedList() {
        extended.insertOrderedList();
    }

    public void insertUnorderedList() {
        extended.insertUnorderedList();
    }

    public boolean isAnythingSelected() {
        return !getDocument().getSelection().isCollapsed();
    }

    public boolean isBold() {
        return basic.isBold();
    }

    public boolean isCollapsed() {
        return getFstRange().isCollapsed();
    }

    public boolean isCtxMenuVisible() {
        return linkCtxMenu.isVisible();
    }

    public boolean isItalic() {
        return basic.isItalic();
    }

    public boolean isLink() {
        if (isAttached() && LinkExecutableUtils.getSelectedAnchor(this) != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isStrikethrough() {
        return extended.isStrikethrough();
    }

    public boolean isSubscript() {
        return basic.isSubscript();
    }

    public boolean isSuperscript() {
        return basic.isSuperscript();
    }

    public boolean isUnderlined() {
        return basic.isUnderlined();
    }

    public void justifyCenter() {
        basic.setJustification(Justification.CENTER);
    }

    public void justifyLeft() {
        basic.setJustification(Justification.LEFT);
    }

    public void justifyRight() {
        basic.setJustification(Justification.RIGHT);
    }

    public void leftIndent() {
        extended.leftIndent();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBrowserEvent(final Event event) {
        switch (DOM.eventGetType(event)) {
        case Event.ONCLICK:
            updateStatus();
            updateLinkInfo();
            super.onBrowserEvent(event);
            break;
        case Event.ONKEYDOWN:
            ActionItem rtaActionItem = shortcutRegister.get(event);
            ActionItem actionItem = rtaActionItem != null ? rtaActionItem : globalShortcutReg.get(event);
            if (actionItem != null) {
                updateStatus();
                fireEdit();
                event.cancelBubble(true);
                event.preventDefault();
                actionManager.doAction(actionItem);
                updateStatus();
            } else {
                super.onBrowserEvent(event);
                updateStatus();
                updateLinkInfo();
                if (isAnEditionKey(event.getKeyCode())) {
                    fireEdit();
                }
            }
            break;
        default:
            // Rest of events
            super.onBrowserEvent(event);
            updateStatus();
        }
    }

    public void paste() {
        extended.paste();
    }

    public void redo() {
        extended.redo();
    }

    public void removeFormat() {
        extended.removeFormat();
    }

    public void rightIndent() {
        extended.rightIndent();
    }

    public void selectAll() {
        basic.selectAll();
    }

    public void setBackColor(final String color) {
        basic.setBackColor(color);
    }

    public void setFontName(final String name) {
        basic.setFontName(name);
    }

    public void setFontSize(final FontSize size) {
        basic.setFontSize(size);
    }

    public void setForeColor(final String color) {
        basic.setForeColor(color);
    }

    public void showLinkCtxMenu() {
        DeferredCommand.addCommand(new Command() {
            public void execute() {
                org.xwiki.gwt.dom.client.Element selectedAnchor = LinkExecutableUtils.getSelectedAnchor(RTEditorPanel.this);
                if (selectedAnchor != null) {
                    linkCtxMenu.show(RTEditorPanel.this.getAbsoluteLeft() + selectedAnchor.getAbsoluteLeft(),
                            RTEditorPanel.this.getAbsoluteTop() + selectedAnchor.getAbsoluteTop() + 20);
                }
            }
        });

    }

    public void toggleBold() {
        basic.toggleBold();
    }

    public void toggleItalic() {
        basic.toggleItalic();
    }

    public void toggleStrikethrough() {
        extended.toggleStrikethrough();
    }

    public void toggleSubscript() {
        basic.toggleSubscript();
    }

    public void toggleSuperscript() {
        basic.toggleSuperscript();
    }

    public void toggleUnderline() {
        basic.toggleUnderline();
    }

    public void undo() {
        extended.undo();
    }

    public void unlink() {
        extended.removeLink();
    }

    protected void fireEdit() {
        presenter.fireOnEdit();
    }

    private void createCommentAndSelectIt(final String author, final String comment) {
        Element commentEl = createCommentElement(author, comment);
        Range innerCommentRange = getDocument().createRange();
        getFstRange().insertNode(commentEl);
        innerCommentRange.selectNodeContents(commentEl.getFirstChild());
        getSelection().addRange(innerCommentRange);
        fireEdit();
    }

    private Element createCommentElement(final String userName, final String insertComment) {
        String time = i18n.formatDateWithLocale(new Date(), true);
        Element span = getDocument().createSpanElement();
        String comment = insertComment != null ? insertComment : i18n.t("type your comment here");
        span.setInnerHTML("<em>" + comment + "</em> -" + userName + " " + time);
        DOM.setElementProperty(span.<com.google.gwt.user.client.Element> cast(), "className", "k-rte-comment");
        // insertHtml("&nbsp;" + span.getString() + "&nbsp;");
        return span;
    }

    private Range getFstRange() {
        return getSelection().getRangeAt(0);
    }

    private Selection getSelection() {
        return getDocument().getSelection();
    }

    private boolean isAnEditionKey(final int keyCode) {
        if (keyCode != KEY_HOME && keyCode != KEY_END && keyCode != KEY_UP && keyCode != KEY_DOWN
                && keyCode != KEY_LEFT && keyCode != KEY_RIGHT && keyCode != KEY_PAGEDOWN && keyCode != KEY_PAGEUP
                && keyCode != KEY_ESCAPE) {
            return true;
        } else {
            return false;
        }
    }

    private void updateLinkInfo() {
        presenter.updateLinkInfo();
    }

    /**
     * Updates the status of all the stateful buttons.
     */
    private void updateStatus() {
        presenter.updateStatus();
    }
}
