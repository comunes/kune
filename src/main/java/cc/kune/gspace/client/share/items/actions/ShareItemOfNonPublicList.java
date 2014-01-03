package cc.kune.gspace.client.share.items.actions;

import cc.kune.common.client.actions.ui.ActionSimplePanel;
import cc.kune.common.client.resources.CommonResources;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.gspace.client.share.items.AbstractShareItemWithMenuUi;

import com.google.inject.Inject;

public class ShareItemOfNonPublicList extends AbstractShareItemWithMenuUi {
  @Inject
  public ShareItemOfNonPublicList(final ActionSimplePanel actionsPanel,
      final ClientFileDownloadUtils downloadUtils, final CommonResources res) {
    super(actionsPanel, downloadUtils, res);
  }

}
