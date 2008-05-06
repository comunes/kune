package org.ourproject.kune.workspace.client.workspace.ui;

import org.ourproject.kune.platf.client.extend.ExtensibleWidget;
import org.ourproject.kune.platf.client.extend.ExtensibleWidgetChild;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class BottomIconsTrayPanel extends HorizontalPanel implements ExtensibleWidget {

    public void attach(final ExtensibleWidgetChild child) {
        super.add((Widget) child.getView());
    }

    public void detach(final ExtensibleWidgetChild child) {
        super.remove((Widget) child.getView());
    }

    public void detachAll(final String id) {
        super.clear();
    }

}
