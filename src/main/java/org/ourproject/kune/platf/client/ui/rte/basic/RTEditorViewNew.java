package org.ourproject.kune.platf.client.ui.rte.basic;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.ui.GuiActionCollection;
import org.ourproject.kune.platf.client.ui.rte.RichTextArea.FontSize;
import org.ourproject.kune.platf.client.ui.rte.insertlink.LinkInfo;

public interface RTEditorViewNew extends View {

    void addActions(GuiActionCollection actions);

    void adjustSize(int height);

    boolean canBeBasic();

    boolean canBeExtended();

    void copy();

    void createLink(String url);

    void cut();

    void delete();

    void focus();

    String getHTML();

    LinkInfo getLinkInfoIfHref();

    void getRangeInfo();

    String getSelectionText();

    String getText();

    void hideLinkCtxMenu();

    void insertBlockquote();

    void insertComment(String author);

    void insertCommentNotUsingSelection(String author);

    void insertCommentUsingSelection(String author);

    void insertHorizontalRule();

    void insertHtml(String html);

    void insertImage(String url);

    void insertOrderedList();

    void insertUnorderedList();

    boolean isAnythingSelected();

    boolean isAttached();

    boolean isBold();

    boolean isCtxMenuVisible();

    boolean isItalic();

    boolean isLink();

    boolean isStrikethrough();

    boolean isSubscript();

    boolean isSuperscript();

    boolean isUnderlined();

    void justifyCenter();

    void justifyLeft();

    void justifyRight();

    void leftIndent();

    void paste();

    void redo();

    void removeFormat();

    void rightIndent();

    void selectAll();

    void selectLink();

    void setBackColor(String color);

    void setFocus(boolean focus);

    void setFontName(String name);

    void setFontSize(FontSize fontSize);

    void setForeColor(String color);

    void setHTML(String html);

    void setText(String text);

    void showLinkCtxMenu();

    void toggleBold();

    void toggleItalic();

    void toggleStrikethrough();

    void toggleSubscript();

    void toggleSuperscript();

    void toggleUnderline();

    void undo();

    void unlink();

}
