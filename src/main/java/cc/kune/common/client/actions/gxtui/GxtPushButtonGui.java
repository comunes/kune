package cc.kune.common.client.actions.gxtui;

import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.PushButtonDescriptor;

public class GxtPushButtonGui extends GxtButtonGui {

    public GxtPushButtonGui() {
        super();
    }

    @Override
    public AbstractGuiItem create(final AbstractGuiActionDescrip descriptor) {
        final AbstractGuiItem item = super.create(descriptor);
        final PushButtonDescriptor btn = (PushButtonDescriptor) descriptor;
        setPressed(btn.isPushed());
        btn.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(final PropertyChangeEvent event) {
                if (event.getPropertyName().equals(PushButtonDescriptor.PUSHED)) {
                    setPressed(btn.isPushed());
                }
            }
        });
        return item;
    }
}
