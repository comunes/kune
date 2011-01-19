package cc.kune.common.client.actions.ui;

import java.util.List;

import cc.kune.common.client.actions.ui.bind.GuiBinding;
import cc.kune.common.client.actions.ui.bind.GuiProvider;
import cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.errors.UIException;

import com.google.gwt.user.client.ui.Composite;

public abstract class AbstractComposedGuiItem extends Composite implements IsActionExtensible {
    private final GuiProvider bindings;
    private GuiActionDescCollection guiItems;

    public AbstractComposedGuiItem(final GuiProvider bindings) {
        super();
        this.bindings = bindings;
    }

    public void add(final AbstractGuiActionDescrip... descriptors) {
        for (final AbstractGuiActionDescrip descriptor : descriptors) {
            addAction(descriptor);
        }
    }

    @Override
    public void addAction(final AbstractGuiActionDescrip descriptor) {
        getGuiItems().add(descriptor);
        beforeAddWidget(descriptor);
    }

    @Override
    public void addActions(AbstractGuiActionDescrip... descriptors) {
        for (final AbstractGuiActionDescrip descriptor : descriptors) {
            addAction(descriptor);
        }
    }

    @Override
    public void addActions(final GuiActionDescCollection descriptors) {
        for (final AbstractGuiActionDescrip descriptor : descriptors) {
            addAction(descriptor);
        }
    }

    public void addActions(final List<AbstractGuiActionDescrip> descriptors) {
        for (final AbstractGuiActionDescrip descriptor : descriptors) {
            addAction(descriptor);
        }
    }

    protected abstract void addWidget(AbstractGuiItem item);

    protected void beforeAddWidget(final AbstractGuiActionDescrip descrip) {
        if (descrip.mustBeAdded()) {
            final GuiBinding binding = bindings.get(descrip.getType());
            if (binding == null) {
                throw new UIException("Unknown binding for: " + descrip);
            } else {
                // Log.debug("Creating: " + descrip);
                final AbstractGuiItem item = binding.create(descrip);
                // Log.debug("Adding: " + descrip);
                if (binding.shouldBeAdded()) {
                    if (descrip.getPosition() == AbstractGuiActionDescrip.NO_POSITION) {
                        addWidget(item);
                    } else {
                        insertWidget(item, descrip.getPosition());
                    }
                }
            }
        }
    }

    public GuiActionDescCollection getGuiItems() {
        if (guiItems == null) {
            guiItems = new GuiActionDescCollection();
        }
        return guiItems;
    }

    protected abstract void insertWidget(AbstractGuiItem item, int position);
}
