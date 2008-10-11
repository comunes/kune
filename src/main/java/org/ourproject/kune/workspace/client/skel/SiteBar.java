package org.ourproject.kune.workspace.client.skel;

import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.Panel;

public class SiteBar extends Panel {

    private final SimpleToolbar simpleToolbar;

    @Deprecated
    public SiteBar() {
        // super.setLayout(new FitLayout());
        super.setBorder(false);
        simpleToolbar = new SimpleToolbar();
        simpleToolbar.setStyleName("k-sitebar");
        simpleToolbar.addSpacer();
        super.add(simpleToolbar);
    }

    @Override
    public void add(final Widget widget) {
        simpleToolbar.add(widget);
    }

    public Widget addFill() {
        return simpleToolbar.addFill();
    }

    public Widget addSeparator() {
        return simpleToolbar.addSeparator();
    }

    public Widget addSpacer() {
        return simpleToolbar.addSpacer();
    }

}
