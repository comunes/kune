/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.ourproject.kune.platf.client.ui.rte.basic;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.InputMap;
import org.ourproject.kune.platf.client.actions.ui.ActionExtensibleView;
import org.ourproject.kune.platf.client.ui.rte.RichTextArea.FontSize;
import org.ourproject.kune.platf.client.ui.rte.insertlink.LinkInfo;

public interface RTEditorView extends ActionExtensibleView {

    void adjustSize(int height);

    boolean canBeBasic();

    boolean canBeExtended();

    void copy();

    void createLink(String url);

    void cut();

    void delete();

    void focus();

    String getFont();

    String getFontSize();

    String getHTML();

    LinkInfo getLinkInfoIfHref();

    void getRangeInfo();

    String getSelectionText();

    View getSndBar();

    String getText();

    View getTopBar();

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

    void setInputMap(InputMap inputMap);

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
