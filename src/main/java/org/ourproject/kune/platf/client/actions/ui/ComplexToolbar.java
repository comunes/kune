package org.ourproject.kune.platf.client.actions.ui;

import java.util.List;

import org.ourproject.kune.platf.client.ui.SimpleToolbar;

public class ComplexToolbar extends AbstractComplexGuiItem {

    private final SimpleToolbar toolbar;
    private final GuiBindingsRegister bindings;

    public ComplexToolbar(final GuiBindingsRegister bindings) {
        super();
        this.bindings = bindings;
        toolbar = new SimpleToolbar();
        initWidget(toolbar);
    }

    @Override
    public void add(final AbstractGuiActionDescrip... descriptors) {
        for (final AbstractGuiActionDescrip descrip : descriptors) {
            super.add(descrip);
            addWidget(descrip);
        }
    }

    @Override
    public void addAll(final List<AbstractGuiActionDescrip> descriptors) {
        super.addAll(descriptors);
        for (final AbstractGuiActionDescrip descritor : descriptors) {
            addWidget(descritor);
        }
    }

    private void addWidget(final AbstractGuiActionDescrip descrip) {
        if (descrip.getAddCondition().mustBeAdded()) {
            final boolean visible = descrip.getVisibleCondition().mustBeVisible();
            final GuiBinding binding = bindings.get(descrip.getType());
            final AbstractGuiItem item = binding.create(descrip);
            if (binding.isAttachable()) {
                final int position = descrip.getPosition();
                if (position == AbstractGuiActionDescrip.NO_POSITION) {
                    toolbar.add(item);
                } else {
                    toolbar.insert(item, position);
                }
                item.setVisible(visible);
            }
        }
    }

}
