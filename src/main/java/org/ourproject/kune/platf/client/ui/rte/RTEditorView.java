package org.ourproject.kune.platf.client.ui.rte;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.ActionCollection;

public interface RTEditorView extends View {

    void addComment(String userName);

    boolean canBeExtended();

    void copy();

    void createLink(String url);

    void cut();

    void delete();

    void insertHorizontalRule();

    void insertHtml(String html);

    void insertImage(String url);

    void insertOrderedList();

    void insertUnorderedList();

    boolean isBold();

    boolean isItalic();

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

    void setActions(ActionCollection<Object> actions);

    void setFontName(String name);

    void setFontSize(int size);

    void setForeColor(String color);

    void toggleBold();

    void toggleItalic();

    void toggleStrikethrough();

    void toggleSubscript();

    void toggleSuperscript();

    void toggleUnderline();

    void undo();

    void unlink();
}
