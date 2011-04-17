package com.example.client;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.sitebar.SiteUserOptions;
import cc.kune.core.client.sitebar.SitebarActions;

import com.google.inject.Inject;

public class HelloWorldActions {

    public class HelloWorldAction extends AbstractExtendedAction {

        @Override
        public void actionPerformed(final ActionEvent event) {
            NotifyUser.info("Hello world!");
        }

    }

    @Inject
    public HelloWorldActions(final CoreResources coreResources, final SitebarActions sitebarActions,
            final SiteUserOptions siteUserOptions) {

        // We can share the action if we don't want to create several (and for
        // instance it doesn't store any value)
        final HelloWorldAction sharedAction = new HelloWorldAction();

        // An action in the sitebar
        final ButtonDescriptor siteBarBtn = new ButtonDescriptor(sharedAction);
        siteBarBtn.withText("HWorld!").withIcon(coreResources.info());
        // FIXME setParent as in siteUsOp
        sitebarActions.getLeftToolbar().add(siteBarBtn);

        // Other action in the sitebar options menu

        // An action in the user options menu
        final MenuItemDescriptor menuItem = new MenuItemDescriptor(sharedAction);
        menuItem.withText("HWorld!").withIcon(coreResources.alert());
        siteUserOptions.addAction(menuItem);

        // Another action in the SocialNet menu

        // Something added directly in the Skeleton (esto en el Panel)

        // IMPORTANT: If you want to add something in a part a don't find how,
        // please ask! Maybe we need a extension point or we need to document
        // better

        // Do something with Chat

    }
}
