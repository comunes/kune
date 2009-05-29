package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.actions.PropertyChangeEvent;
import org.ourproject.kune.platf.client.actions.PropertyChangeListener;

public class PushButtonGui extends ButtonGui {

    public PushButtonGui(final PushButtonDescriptor btn) {
        super(btn, true);
        setPressed(btn.isPushed());
        action.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(final PropertyChangeEvent event) {
                if (event.getPropertyName().equals(PushButtonDescriptor.PUSHED)) {
                    setPressed(btn.isPushed());
                }
            }
        });
    }
}
