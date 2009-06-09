package org.ourproject.kune.platf.client.actions.ui;

public class ToolbarSeparatorBinding extends GuiBindingAdapter {
    @Override
    public AbstractGuiItem create(final GuiActionDescrip descriptor) {
        final ToolbarSeparatorDescriptor sepDescrip = (ToolbarSeparatorDescriptor) descriptor;
        final ComplexToolbar toolbar = (ComplexToolbar) sepDescrip.getToolbar();
        switch (sepDescrip.getSeparatorType()) {
        case fill:
            toolbar.addFill();
            break;
        case separator:
            toolbar.addSeparator();
            break;
        case spacer:
            toolbar.addSpacer();
            break;
        default:
            break;
        }
        return super.create(descriptor);
    }

    @Override
    public boolean isAttachable() {
        return false;
    }

}
