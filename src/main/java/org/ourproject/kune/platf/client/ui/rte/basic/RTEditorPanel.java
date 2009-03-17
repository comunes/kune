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
import org.ourproject.kune.platf.client.shortcuts.ActionShortcut;
import org.ourproject.kune.platf.client.shortcuts.ActionShortcutRegister;
import org.ourproject.kune.platf.client.shortcuts.GlobalShortcutRegister;
import org.ourproject.kune.platf.client.ui.rte.RichTextArea;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.Widget;
import com.xpn.xwiki.wysiwyg.client.dom.DocumentFragment;
import com.xpn.xwiki.wysiwyg.client.dom.Range;
import com.xpn.xwiki.wysiwyg.client.dom.Selection;

public class RTEditorPanel extends RichTextArea implements RTEditorView {

    private class EventListener implements FocusListener {

        public void onFocus(Widget sender) {
            presenter.onEditorFocus();
        }

        public void onLostFocus(Widget sender) {
        }
    }
    private final I18nUITranslationService i18n;
    private final BasicFormatter basic;
    private final ExtendedFormatter extended;
    private final RTEditorPresenter presenter;
    private final ActionManager actionManager;
    private final ActionShortcutRegister shortcutRegister;
    private final GlobalShortcutRegister globalShortcutReg;

    public RTEditorPanel(final RTEditorPresenter presenter, I18nUITranslationService i18n,
            final ActionManager actionManager, GlobalShortcutRegister globalShortcutReg) {
        this.presenter = presenter;
        this.i18n = i18n;
        this.actionManager = actionManager;
        this.globalShortcutReg = globalShortcutReg;
        basic = getBasicFormatter();
        extended = getExtendedFormatter();
        shortcutRegister = new ActionShortcutRegister();
        EventListener listener = new EventListener();
        addFocusListener(listener);
        setWidth("96%");
        setHeight("100%");

    }

    public void addActions(ActionItemCollection<Object> actionItems) {
        for (ActionItem<Object> actionItem : actionItems) {
            ActionDescriptor<Object> action = actionItem.getAction();
            if (action.hasShortcut() && action.mustBeAdded(null)) {
                ActionShortcut shortcut = action.getShortcut();
                shortcutRegister.put(shortcut, actionItem);
            }
        }
    }

    public void adjustSize(int height) {
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

    public void createLink(String url) {
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

    public void insertBlockquote() {
        DocumentFragment extracted = getFstRange().cloneContents();
        delete();
        insertHtml("<blockquote>" + extracted.getInnerHTML() + "</blockquote>");
        focus();
    }

    public void insertComment(String author) {
        String comment = null;
        createCommentAndSelectIt(author, comment);
    }

    public void insertCommentNotUsingSelection(String author) {
        getFstRange().collapse(false);
        createCommentAndSelectIt(author, null);
        focus();
    }

    public void insertCommentUsingSelection(String author) {
        DocumentFragment extracted = getFstRange().cloneContents();
        extended.delete();
        String comment = extracted.getInnerText();
        createCommentAndSelectIt(author, comment);
        focus();
    }

    public void insertHorizontalRule() {
        extended.insertHorizontalRule();
    }

    public void insertHtml(String html) {
        extended.insertHtml(html);
    }

    public void insertImage(String url) {
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

    public boolean isItalic() {
        return basic.isItalic();
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
    public void onBrowserEvent(Event event) {
        switch (DOM.eventGetType(event)) {
        case Event.ONCLICK:
            updateStatus();
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

    public void setBackColor(String color) {
        basic.setBackColor(color);
    }

    public void setFontName(String name) {
        basic.setFontName(name);
    }

    public void setFontSize(FontSize size) {
        basic.setFontSize(size);
    }

    public void setForeColor(String color) {
        basic.setForeColor(color);
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

    private void createCommentAndSelectIt(String author, String comment) {
        Element commentEl = createCommentElement(author, comment);
        Range innerCommentRange = getDocument().createRange();
        getFstRange().insertNode(commentEl);
        innerCommentRange.selectNodeContents(commentEl.getFirstChild());
        getSelection().addRange(innerCommentRange);
        fireEdit();
    }

    private Element createCommentElement(String userName, String insertComment) {
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

    private boolean isAnEditionKey(int keyCode) {
        if (keyCode != KEY_HOME && keyCode != KEY_END && keyCode != KEY_UP && keyCode != KEY_DOWN
                && keyCode != KEY_LEFT && keyCode != KEY_RIGHT && keyCode != KEY_PAGEDOWN && keyCode != KEY_PAGEUP
                && keyCode != KEY_ESCAPE) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Updates the status of all the stateful buttons.
     */
    private void updateStatus() {
        presenter.updateStatus();
    }

}
