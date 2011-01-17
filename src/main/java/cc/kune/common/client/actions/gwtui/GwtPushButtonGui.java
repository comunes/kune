package cc.kune.common.client.actions.gwtui;

import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.ui.descrip.PushButtonDescriptor;

public class GwtPushButtonGui extends GwtButtonGui {
    public GwtPushButtonGui() {
    }

    public GwtPushButtonGui(final PushButtonDescriptor btn) {
        super(btn, true);
        setPressed(btn.isPushed());
        btn.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(final PropertyChangeEvent event) {
                if (event.getPropertyName().equals(PushButtonDescriptor.PUSHED)) {
                    setPressed(btn.isPushed());
                }
            }
        });
    }

}
