package org.ourproject.kune.platf.client.actions.ui;

import java.util.List;

import org.ourproject.kune.workspace.client.skel.Toolbar;

import com.google.gwt.user.client.ui.Widget;

public class ComplexToolbar extends AbstractUIElement {

    private final transient Toolbar toolbar;

    public ComplexToolbar() {
        super();
        toolbar = new Toolbar();
        initWidget(toolbar.getPanel());
    }

    @Override
    public void add(final AbstractUIActionDescriptor descrip) {
        super.add(descrip);
        addWidget(descrip);
    }

    @Override
    public void addAll(final List<AbstractUIActionDescriptor> moreItems) {
        super.addAll(moreItems);
        for (AbstractUIActionDescriptor item : moreItems) {
            addWidget(item);
        }
    }

    private void addWidget(final AbstractUIActionDescriptor descrip) {
        final Widget view = (Widget) descrip.getView();
        if (!descrip.isChild()) {
            toolbar.add(view);
        }
    }
}
