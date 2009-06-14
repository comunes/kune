package org.ourproject.kune.platf.client.actions.ui;

import java.util.List;

import org.ourproject.kune.platf.client.errors.UIException;

import com.google.gwt.user.client.ui.Composite;

public abstract class AbstractComposedGuiItem extends Composite {
    private GuiActionDescCollection guiItems;
    private final GuiBindingsRegister bindings;

    public AbstractComposedGuiItem(final GuiBindingsRegister bindings) {
        super();
        this.bindings = bindings;
    }

    public void add(final GuiActionDescrip... descriptors) {
        for (final GuiActionDescrip descriptor : descriptors) {
            addAction(descriptor);
        }
    }

    public void addAction(final GuiActionDescrip descriptor) {
        getGuiItems().add(descriptor);
        beforeAddWidget(descriptor);
    }

    public void addAll(final GuiActionDescCollection descriptors) {
        for (final GuiActionDescrip descriptor : descriptors) {
            addAction(descriptor);
        }
    }

    public void addAll(final List<GuiActionDescrip> descriptors) {
        for (final GuiActionDescrip descriptor : descriptors) {
            addAction(descriptor);
        }
    }

    public GuiActionDescCollection getGuiItems() {
        if (guiItems == null) {
            guiItems = new GuiActionDescCollection();
        }
        return guiItems;
    }

    protected abstract void add(AbstractGuiItem item);

    protected void beforeAddWidget(final GuiActionDescrip descrip) {
        if (descrip.mustBeAdded()) {
            final GuiBinding binding = bindings.get(descrip.getType());
            if (binding == null) {
                throw new UIException("Unknown binding for: " + descrip);
            } else {
                final AbstractGuiItem item = binding.create(descrip);
                if (binding.isAttachable()) {
                    if (descrip.getPosition() == GuiActionDescrip.NO_POSITION) {
                        add(item);
                    } else {
                        insert(item, descrip.getPosition());
                    }
                }
            }
        }
    }

    protected abstract void insert(AbstractGuiItem item, int position);
}
