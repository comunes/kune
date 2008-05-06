package org.ourproject.kune.workspace.client.workspace.ui;

import org.ourproject.kune.platf.client.extend.ExtensibleWidget;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class BottomIconsTrayPanel extends HorizontalPanel implements ExtensibleWidget {

    public void attach(final String id, final Widget widget) {
        super.add(widget);
    }

    public void detach(final String id, final Widget widget) {
        super.remove(widget);
    }

    public void detachAll(final String id) {
        super.clear();
    }

}
