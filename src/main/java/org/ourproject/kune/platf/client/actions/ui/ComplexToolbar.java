package org.ourproject.kune.platf.client.actions.ui;

import java.util.List;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.errors.UIException;
import org.ourproject.kune.platf.client.ui.FlowToolbar;

public class ComplexToolbar extends AbstractComplexGuiItem implements View {

    private final FlowToolbar toolbar;
    private final GuiBindingsRegister bindings;

    public ComplexToolbar(final GuiBindingsRegister bindings) {
        super();
        this.bindings = bindings;
        toolbar = new FlowToolbar();
        initWidget(toolbar);
    }

    @Override
    public void add(final GuiActionDescrip... descriptors) {
        for (final GuiActionDescrip descrip : descriptors) {
            super.add(descrip);
            addWidget(descrip);
        }
    }

    @Override
    public void addAll(final List<GuiActionDescrip> descriptors) {
        super.addAll(descriptors);
        for (final GuiActionDescrip descritor : descriptors) {
            addWidget(descritor);
        }
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

    private void addWidget(final GuiActionDescrip descrip) {
        if (descrip.getAddCondition().mustBeAdded()) {
            final boolean visible = descrip.getVisibleCondition().mustBeVisible();
            final GuiBinding binding = bindings.get(descrip.getType());
            if (binding == null) {
                throw new UIException("Unknown binding for: " + descrip);
            } else {
                final AbstractGuiItem item = binding.create(descrip);
                if (binding.isAttachable()) {
                    item.addStyleName("kune-floatleft");
                    final int position = descrip.getPosition();
                    if (position == GuiActionDescrip.NO_POSITION) {
                        toolbar.add(item);
                    } else {
                        toolbar.insert(item, position);
                    }
                    item.setVisible(visible);
                }
            }
        }
    }

}
