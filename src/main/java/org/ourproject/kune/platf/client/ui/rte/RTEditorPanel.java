package org.ourproject.kune.platf.client.ui.rte;

import java.util.Date;
import java.util.HashMap;

import org.ourproject.kune.platf.client.actions.ActionDescriptor;
import org.ourproject.kune.platf.client.actions.ActionItem;
import org.ourproject.kune.platf.client.actions.ActionItemCollection;
import org.ourproject.kune.platf.client.actions.ActionManager;
import org.ourproject.kune.platf.client.actions.ActionShortcut;
import org.ourproject.kune.platf.client.i18n.I18nUITranslationService;
import org.ourproject.kune.platf.client.ui.rte.RichTextArea.BasicFormatter;
import org.ourproject.kune.platf.client.ui.rte.RichTextArea.ExtendedFormatter;
import org.ourproject.kune.platf.client.ui.rte.RichTextArea.FontSize;
import org.ourproject.kune.platf.client.ui.rte.RichTextArea.Justification;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Widget;

public class RTEditorPanel implements RTEditorView {

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
        createListeners();
    }

    public void addActions(ActionItemCollection<Object> actionItems) {
        for (ActionItem<Object> actionItem : actionItems) {
            ActionDescriptor<Object> action = actionItem.getAction();
            if (action.hasShortcut()) {
                ActionShortcut shortcut = action.getShortcut();
                shortcuts.put(shortcut, actionItem);
            }
        }
    }

    public void addComment(String userName) {
        String time = i18n.formatDateWithLocale(new Date(), true);
        Element span = DOM.createSpan();
        span.setInnerText(i18n.t("type here") + " -" + userName + " " + time);
        DOM.setElementProperty(span.<com.google.gwt.user.client.Element> cast(), "className", "k-rte-comment");
        insertHtml(span.getString());
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

    public Widget getRTE() {
        return rta;
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

    public void setFontName(String name) {
        basic.setFontName(name);
    }

    public void setFontSize(int size) {
        switch (size) {
        case 1:
            basic.setFontSize(FontSize.XX_SMALL);
            break;
        case 2:
            basic.setFontSize(FontSize.X_SMALL);
            break;
        case 3:
            basic.setFontSize(FontSize.SMALL);
            break;
        case 4:
            basic.setFontSize(FontSize.MEDIUM);
            break;
        case 5:
            basic.setFontSize(FontSize.LARGE);
            break;
        case 6:
            basic.setFontSize(FontSize.X_LARGE);
            break;
        case 7:
            basic.setFontSize(FontSize.XX_LARGE);
            break;
        }
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

    private void createListeners() {
        rta.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                if (sender == rta) {
                    // We use the RichTextArea's onKeyUp event to update the
                    // toolbar status. This will catch any cases where the user
                    // moves the cursor using the keyboard, or uses one of the
                    // browser's built-in keyboard shortcuts.
                    updateStatus();
                }
            }
        });
        rta.addKeyboardListener(new KeyboardListener() {

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
                        ActionItem<Object> actionItem = shortcuts.get(new ActionShortcut(keyCode, modifiers));
                        if (actionItem != null) {
                            Log.debug("RTE shortcut pressed (" + modifiers + ", " + keyCode + ")");
                            actionManager.doAction(actionItem);
                        }
                    }
                }
            }
        });
    }

    /**
     * Updates the status of all the stateful buttons.
     */
    private void updateStatus() {
        presenter.updateStatus();
    }
}
