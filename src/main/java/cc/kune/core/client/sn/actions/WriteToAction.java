package cc.kune.core.client.sn.actions;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.utils.SimpleResponseCallback;
import cc.kune.core.client.resources.nav.NavResources;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class WriteToAction extends AbstractExtendedAction {

  private final Provider<ContentServiceAsync> contentService;
  private final I18nTranslationService i18n;
  private boolean onlyToAdmins;
  private final Session session;
  private final StateManager stateManager;

  @Inject
  public WriteToAction(final Provider<ContentServiceAsync> contentService,
      final I18nTranslationService i18n, final Session session, final StateManager stateManager,
      final NavResources res) {
    this.contentService = contentService;
    this.i18n = i18n;
    this.session = session;
    this.stateManager = stateManager;
    onlyToAdmins = false;
    putValue(AbstractAction.SMALL_ICON, res.pageText());
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    boolean isPerson = false;
    final StateToken token;
    if (event.getTarget() == null) {
      token = session.getCurrentStateToken();
    } else {
      isPerson = EventTargetUtils.isPerson(event);
      token = EventTargetUtils.getTargetToken(event);
    }
    NotifyUser.askConfirmation(
        i18n.t("Confirm, please:"),
        isPerson ? i18n.t("Do you want to write a message to your buddy?")
            : onlyToAdmins ? i18n.t("Do you want to write a message to the admins of this group?")
                : i18n.t("Do you want to write a message to the members of this group?"),
        new SimpleResponseCallback() {
          @Override
          public void onCancel() {
            // Do nothing
          }

          @Override
          public void onSuccess() {
            contentService.get().writeTo(session.getUserHash(), token, onlyToAdmins,
                new AsyncCallbackSimple<String>() {
                  @Override
                  public void onSuccess(final String url) {
                    stateManager.gotoHistoryToken(url);
                    NotifyUser.info("Now you can edit this message");
                  }
                });
          }
        });
  }

  public void setOnlyToAdmin(final boolean onlyToAdmins) {
    this.onlyToAdmins = onlyToAdmins;
  }
}
