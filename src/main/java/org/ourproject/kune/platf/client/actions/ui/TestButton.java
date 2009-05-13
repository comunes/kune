package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.actions.AbstractAction;
import org.ourproject.kune.platf.client.actions.Action;
import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.Timer;

public class TestButton extends DefaultButton {
    public class NotiAction extends AbstractAction {
        public NotiAction() {
            super.putValue(Action.NAME, "test");
            super.putValue(Action.SHORT_DESCRIPTION, "test button");
        }

        public void actionPerformed(final ActionEvent event) {
            NotifyUser.info("Button clicked");
        }
    }

    public TestButton(final WorkspaceSkeleton ws) {
        super();
        ws.getEntityWorkspace().getBottomTitle().add(this);
        final NotiAction noti = new NotiAction();
        super.setAction(noti);
        new Timer() {
            @Override
            public void run() {
                noti.putValue(Action.NAME, "test2");
                noti.putValue(Action.SHORT_DESCRIPTION, "test2 button");
            }
        }.schedule(5000);
    }
}
