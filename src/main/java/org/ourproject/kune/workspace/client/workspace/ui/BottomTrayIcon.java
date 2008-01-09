package org.ourproject.kune.workspace.client.workspace.ui;

import org.ourproject.kune.platf.client.View;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.MenuBar;

public class BottomTrayIcon extends MenuBar implements View {

    public BottomTrayIcon(final String title) {
        super(true);
        super.setTitle(title);
        super.addStyleDependentName("kune-IconBottomMenu-offset");
        super.addStyleName("kune-Margin-Medium-r");
        super.setStyleName("kune-IconBottomPanel");
    }

    public void addMainButton(final AbstractImagePrototype icon, final Command cmd) {
        super.addItem(icon.getHTML(), true, cmd);
    }

}
