package org.ourproject.kune.platf.client.ui.rte;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.ActionCollection;

public interface RTEditorView extends View {

    void addComment(String userName);

    boolean canBeExtended();

    void copy();

    void createlink(String url);

    void cut();

    void delete();

    void fontname(String name);

    void fontsize(String size);

    void forecolor(String color);

    void indent();

    void inserthorizontalrule();

    void inserthtml(String html);

    void insertimage(String url);

    void insertorderedlist();

    void insertparagraph();

    void insertunorderedlist();

    void italic();

    void justifycenter();

    void justifyfull();

    void justifyleft();

    void justifyright();

    void outdent();

    void paste();

    void redo();

    void removeformat();

    void selectall();

    void setActions(ActionCollection<Object> actions);

    void strikethrough();

    void subscript();

    void superscript();

    void underline();

    void undo();

    void unlink();
}
