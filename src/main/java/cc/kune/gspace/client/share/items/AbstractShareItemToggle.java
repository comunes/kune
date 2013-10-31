package cc.kune.gspace.client.share.items;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.ui.ActionSimplePanel;
import cc.kune.common.client.resources.CommonResources;
import cc.kune.core.client.services.ClientFileDownloadUtils;

import com.google.gwt.resources.client.ImageResource;

public class AbstractShareItemToggle extends AbstractShareItemWithMenuUi {

  public AbstractShareItemToggle(final ActionSimplePanel actionsPanel,
      final ClientFileDownloadUtils downloadUtils, final CommonResources res) {
    super(actionsPanel, downloadUtils, res);
  }

  public void withNonPublicValues(final ImageResource nonPublicItemIcon, final String nonPublicItemText,
      final String nonPublicMenuText, final AbstractAction... nonPublicActions) {

  }

  public void withPublicValues(final ImageResource publicItemIcon, final String publicItemText,
      final String publicMenuText, final AbstractAction... publicActions) {
  }

}
