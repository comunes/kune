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

    public void setCleanStyle() {
        toolbar.setCleanStyle();
    }

    public void setNormalStyle() {
        toolbar.setNormalStyle();
    }

    @Override
    protected void addWidget(final AbstractGuiItem item, final int position, final boolean visible) {
        item.addStyleName("kune-floatleft");
        if (position == GuiActionDescrip.NO_POSITION) {
            toolbar.add(item);
        } else {
            toolbar.insert(item, position);
        }
        item.setVisible(visible);
    }

}
