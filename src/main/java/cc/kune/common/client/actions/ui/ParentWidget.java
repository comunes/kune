package cc.kune.common.client.actions.ui;

import com.google.gwt.user.client.ui.UIObject;

public interface ParentWidget {

    String PARENT_UI = "PARENT_UI";

    public void add(UIObject uiObject);

    public void insert(final int position, final UIObject widget);
}
