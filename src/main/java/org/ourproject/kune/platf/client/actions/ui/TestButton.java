package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.actions.AbstractAction;
import org.ourproject.kune.platf.client.actions.Action;
import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.Timer;

public class TestButton {
    public class NotiAction extends AbstractAction {
        public NotiAction() {
            super();
            super.putValue(Action.NAME, "test");
            super.putValue(Action.SHORT_DESCRIPTION, "test button");
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            if (actionEvent.getEvent().getCtrlKey()) {
                NotifyUser.info("Button clicked with ctrl button");
            }
            NotifyUser.info("Button clicked");
        }
    }

    public TestButton(final WorkspaceSkeleton wksp) {
        final NotiAction noti = new NotiAction();

        final PushButtonDescriptor descriptor = new PushButtonDescriptor(noti);

        final DefaultButton btn = new DefaultButton(descriptor);

        wksp.getEntityWorkspace().getBottomTitle().add(btn);
        new Timer() {
            @Override
            public void run() {
                noti.putValue(Action.NAME, "test2");
                noti.putValue(Action.SHORT_DESCRIPTION, "test2 button");
                descriptor.setPushed(false);
            }
        }.schedule(10000);

        descriptor.setPushed(true);

    }
}
