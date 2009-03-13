package org.ourproject.kune.platf.client.ui.rte.basic;

import java.util.Date;
import java.util.HashMap;

import org.ourproject.kune.platf.client.actions.ActionDescriptor;
import org.ourproject.kune.platf.client.actions.ActionItem;
import org.ourproject.kune.platf.client.actions.ActionItemCollection;
import org.ourproject.kune.platf.client.actions.ActionManager;
import org.ourproject.kune.platf.client.actions.ActionShortcut;
import org.ourproject.kune.platf.client.i18n.I18nUITranslationService;
import org.ourproject.kune.platf.client.ui.rte.RichTextArea;
import org.ourproject.kune.platf.client.ui.rte.RichTextArea.BasicFormatter;
import org.ourproject.kune.platf.client.ui.rte.RichTextArea.ExtendedFormatter;
import org.ourproject.kune.platf.client.ui.rte.RichTextArea.FontSize;
import org.ourproject.kune.platf.client.ui.rte.RichTextArea.Justification;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Widget;
import com.xpn.xwiki.wysiwyg.client.dom.Document;
import com.xpn.xwiki.wysiwyg.client.dom.DocumentFragment;
import com.xpn.xwiki.wysiwyg.client.dom.Range;
import com.xpn.xwiki.wysiwyg.client.dom.Selection;

public class RTEditorPanel implements RTEditorView {

    private class EventListener implements ClickListener, ChangeListener, KeyboardListener, FocusListener {

        public void onChange(Widget sender) {
            presenter.fireOnEdit();
        }

        public void onClick(Widget sender) {
            if (sender == rta) {
                // We use the RichTextArea's onKeyUp event to update the
                // toolbar status. This will catch any cases where the user
                // moves the cursor using the keyboard, or uses one of the
                // browser's built-in keyboard shortcuts.
                updateStatus();
            }
        }

        public void onFocus(Widget sender) {
            presenter.onEditorFocus();
        }

        public void onKeyDown(final Widget sender, final char keyCode, final int modifiers) {
        }

        public void onKeyPress(final Widget sender, final char keyCode, final int modifiers) {
        }

        public void onKeyUp(final Widget sender, final char keyCode, final int modifiers) {
            if (sender == rta) {
                // We use the RichTextArea's onKeyUp event to update the
                // toolbar status.
                // This will catch any cases where the user moves the cursor
                // using the keyboard, or uses one of the browser's built-in
                // keyboard shortcuts.

                updateStatus();
                fireEdit();
                if (modifiers != 0) {
                    Log.debug("RTE shortcut pressed (" + modifiers + ", " + keyCode + ")");
                    ActionItem<Object> actionItem = shortcuts.get(new ActionShortcut(keyCode, modifiers));
                    if (actionItem != null) {
                        actionManager.doAction(actionItem);
                        updateStatus();
                    } else {
                        Log.debug("...but not mapped to any action");
                    }
                }
            }
        }

        public void onLostFocus(Widget sender) {

        }
    }
    private final I18nUITranslationService i18n;
    private final RichTextArea rta;
    private final BasicFormatter basic;
    private final ExtendedFormatter extended;
    private final HashMap<ActionShortcut, ActionItem<Object>> shortcuts;
    private final ActionManager actionManager;
    private final RTEditorPresenter presenter;

    public RTEditorPanel(final RTEditorPresenter presenter, I18nUITranslationService i18n, ActionManager actionManager) {
        this.presenter = presenter;
        this.i18n = i18n;
        this.actionManager = actionManager;
        rta = new RichTextArea();
        basic = rta.getBasicFormatter();
        extended = rta.getExtendedFormatter();
        shortcuts = new HashMap<ActionShortcut, ActionItem<Object>>();
        EventListener listener = new EventListener();
        rta.addClickListener(listener);
        rta.addKeyboardListener(listener);
        rta.addFocusListener(listener);
        rta.setWidth("96%");
        rta.setHeight("100%");
    }

    public void addActions(ActionItemCollection<Object> actionItems) {
        for (ActionItem<Object> actionItem : actionItems) {
            ActionDescriptor<Object> action = actionItem.getAction();
            if (action.hasShortcut() && action.mustBeAdded(null)) {
                ActionShortcut shortcut = action.getShortcut();
                shortcuts.put(shortcut, actionItem);
            }
        }
    }

    public void adjustSize(int height) {
        rta.setHeight("" + height);
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
        rta.setFocus(true);
    }

    /**
     * NOTE: If the current browser doesn't support rich text editing this
     * method returns <code>null</code>. You should test the returned value and
     * fail save to an appropriate behavior!<br/>
     * The appropriate test would be: <code><pre>
     * if (rta.isAttached() && rta.getDocument() == null) {
     *   // The current browser doesn't support rich text editing.
     * }
     * </pre></code>
     * 
     * @return The DOM document being edited with this rich text area.
     * 
     *         copied from xwiki
     */
    public Document getDocument() {
        if (rta.getElement().getTagName().equalsIgnoreCase("iframe")) {
            return IFrameElement.as(rta.getElement()).getContentDocument().cast();
        } else {
            return null;
        }
    }

    public String getHtml() {
        return rta.getHTML();
    }

    public Widget getRTE() {
        return rta;
    }

    public String getText() {
        return rta.getText();
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

    public boolean isAttached() {
        return rta.isAttached();
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

    public void setHtml(String html) {
        rta.setHTML(html);
    }

    public void setText(String text) {
        rta.setText(text);
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
        Selection selection = getDocument().getSelection();
        return selection;
    }

    /**
     * Updates the status of all the stateful buttons.
     */
    private void updateStatus() {
        presenter.updateStatus();
    }

}
