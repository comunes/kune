package cc.kune.common.client.actions.gwtui;

import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;

public class GwtButtonGui extends AbstractGwtButtonGui {

    public GwtButtonGui() {
    }

    public GwtButtonGui(final ButtonDescriptor buttonDescriptor) {
        super(buttonDescriptor);
    }

    public GwtButtonGui(final ButtonDescriptor buttonDescriptor, final boolean enableTongle) {
        super(buttonDescriptor, enableTongle);
    }
}
