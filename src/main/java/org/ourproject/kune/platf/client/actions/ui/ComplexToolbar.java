package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.ui.FlowToolbar;

public class ComplexToolbar extends AbstractComposedGuiItem implements View {

    private final FlowToolbar toolbar;

    public ComplexToolbar(final GuiBindingsRegister bindings) {
        super(bindings);
        toolbar = new FlowToolbar();
        initWidget(toolbar);
    }

    public void addFill() {
        toolbar.addFill();
    }

    public void addSeparator() {
        toolbar.addSeparator();
    }

    public void addSpacer() {
        toolbar.addSpacer();
    }

    /**
     * Set the blank/clean style
     */
    public void setCleanStyle() {
        toolbar.setCleanStyle();
    }

    /**
     * Set the normal grey style
     */
    public void setNormalStyle() {
        toolbar.setNormalStyle();
    }

    @Override
    protected void add(final AbstractGuiItem item) {
        item.addStyleName("kune-floatleft");
        toolbar.add(item);
    }

    @Override
    protected void insert(final AbstractGuiItem item, final int position) {
        item.addStyleName("kune-floatleft");
        toolbar.insert(item, position);
    }

}
