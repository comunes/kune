package cc.kune.core.client.sn.actions;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.sitebar.search.SitebarSearchPresenter;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class AddNewBuddiesAction extends AbstractExtendedAction {

  private final I18nTranslationService i18n;
  private final Provider<SitebarSearchPresenter> searcher;

  @Inject
  public AddNewBuddiesAction(final I18nTranslationService i18n, final CoreResources res,
      final Provider<SitebarSearchPresenter> searcher) {
    this.i18n = i18n;
    this.searcher = searcher;
    putValue(Action.NAME, i18n.t("Add a new buddy"));
    putValue(Action.SMALL_ICON, res.addGreen());
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    NotifyUser.info(i18n.t("Search the user you want to add and in his/her homepage press '"
        + CoreMessages.ADD_AS_A_BUDDIE + "'"));
    searcher.get().focus();
  }

}
