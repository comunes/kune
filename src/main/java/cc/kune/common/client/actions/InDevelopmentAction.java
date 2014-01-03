package cc.kune.common.client.actions;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.common.shared.utils.TextUtils;

public class InDevelopmentAction extends AbstractExtendedAction {

  @Override
  public void actionPerformed(final ActionEvent event) {
    NotifyUser.info(I18n.t(TextUtils.IN_DEVELOPMENT));
  }

}
