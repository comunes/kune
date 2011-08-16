package cc.kune.lists.client.actions;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;

public class ShowSubscribersOfListBtn extends ButtonDescriptor {

  @Inject
  public ShowSubscribersOfListBtn(final I18nTranslationService i18n, final Session session,
      final CoreResources res) {
    super(AbstractAction.NO_ACTION);
    final int subscribers = session.getContainerState().getAccessLists().getEditors().getList().size();
    final int posts = session.getContainerState().getContainer().getContents().size();
    withText(i18n.t("[%d] subscribed, [%d] posts", subscribers, posts));
    // withToolTip(i18n.t("Subscribe to this list"));
    withStyles("k-def-docbtn, k-fl, k-noborder, k-no-backimage, k-nobackcolor");
  }
}
