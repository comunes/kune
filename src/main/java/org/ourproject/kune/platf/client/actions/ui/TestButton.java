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

        final PushButtonDescriptor btn = new PushButtonDescriptor(noti);

        final MenuDescriptor menu = new MenuDescriptor("File", "File menu tooltip", null);
        final MenuItemDescriptor item = new MenuItemDescriptor(menu, noti);
        final MenuItemDescriptor item2 = new MenuItemDescriptor(menu, noti);
        final MenuDescriptor submenu = new MenuDescriptor("Options", "submenu tooltip", null);
        submenu.setParent(menu);
        final MenuItemDescriptor item3 = new MenuItemDescriptor(submenu, noti);
        final MenuCheckItemDescriptor item4 = new MenuCheckItemDescriptor(submenu, noti);
        item4.setChecked(true);
        final MenuRadioItemDescriptor item5 = new MenuRadioItemDescriptor(submenu, noti, "new");
        final MenuRadioItemDescriptor item6 = new MenuRadioItemDescriptor(submenu, noti, "new");
        item5.setChecked(true);
        final ComplexToolbar toolbar = new ComplexToolbar();

        toolbar.add(btn);
        toolbar.add(menu);
        toolbar.add(item);
        toolbar.add(new MenuSeparatorDescriptor(menu));
        toolbar.add(item2);
        toolbar.add(item3);
        toolbar.add(item4);
        toolbar.add(new MenuSeparatorDescriptor(submenu));
        toolbar.add(item5);
        toolbar.add(item6);
        // toolbar.add(new ToolbarSeparatorDescriptor(Type.separator));

        wksp.getEntityWorkspace().getBottomTitle().add(toolbar);

        new Timer() {
            @Override
            public void run() {
                noti.putValue(Action.NAME, "test2");
                noti.putValue(Action.SHORT_DESCRIPTION, "test2 button");
                btn.setPushed(false);
            }
        }.schedule(10000);

        btn.setPushed(true);

    }
}
