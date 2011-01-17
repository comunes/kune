package cc.kune.common.client.actions.ui;

import cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip;

import com.google.gwt.user.client.ui.IsWidget;

public interface ActionExtensibleView extends IsWidget {

    void addAction(AbstractGuiActionDescrip descriptor);

}
