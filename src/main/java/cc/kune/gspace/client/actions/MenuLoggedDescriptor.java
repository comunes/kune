package cc.kune.gspace.client.actions;

import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.core.client.events.AccessRightsChangedEvent;
import cc.kune.core.client.events.AccessRightsChangedEvent.AccessRightsChangedHandler;
import cc.kune.core.client.state.AccessRightsClientManager;

public class MenuLoggedDescriptor extends MenuDescriptor {
  public MenuLoggedDescriptor(final AccessRightsClientManager rightsManager) {
    rightsManager.onRightsChanged(true, new AccessRightsChangedHandler() {
      @Override
      public void onAccessRightsChanged(final AccessRightsChangedEvent event) {
        MenuLoggedDescriptor.this.setVisible(event.getCurrentRights().isEditable());
      }
    });
  }

}
