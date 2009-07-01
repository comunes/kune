package org.ourproject.kune.platf.client.actions.ui;

import com.google.gwt.user.client.ui.HorizontalPanel;

public class SimpleGuiItem extends AbstractComposedGuiItem implements ActionExtensibleView {

    private final HorizontalPanel hpanel;

    public SimpleGuiItem(final GuiBindingsRegister bindings) {
        super(bindings);
        hpanel = new HorizontalPanel();
        initWidget(hpanel);
    }

    @Override
    protected void add(final AbstractGuiItem item) {
        hpanel.add(item);
    }

    @Override
    protected void insert(final AbstractGuiItem item, final int position) {
        hpanel.insert(item, position);
    }

}
