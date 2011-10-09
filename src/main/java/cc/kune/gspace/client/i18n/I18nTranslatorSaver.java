package cc.kune.gspace.client.i18n;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.rpcservices.I18nServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.I18nTranslationDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class I18nTranslatorSaver {
  private final I18nUITranslationService i18n;
  private final Provider<I18nServiceAsync> i18nService;
  private final Session session;

  @Inject
  public I18nTranslatorSaver(final Session session, final Provider<I18nServiceAsync> i18nService,
      final I18nUITranslationService i18n) {
    this.session = session;
    this.i18nService = i18nService;
    this.i18n = i18n;
  }

  public void save(final I18nTranslationDTO item) {
    NotifyUser.showProgress(i18n.t("Saving"));
    i18nService.get().setTranslation(session.getUserHash(), item.getId(), item.getText(),
        new AsyncCallback<String>() {
          @Override
          public void onFailure(final Throwable caught) {
            NotifyUser.hideProgress();
            if (caught instanceof AccessViolationException) {
              NotifyUser.error(
                  i18n.t("Only to authorized translators"),
                  i18n.t("To help with the translation of this software please contact before with this site administrators"));
            } else {
              NotifyUser.error(i18n.t("Server error saving the translation"));
            }
          }

          @Override
          public void onSuccess(final String result) {
            NotifyUser.hideProgress();
            i18n.setTranslationAfterSave(item.getTrKey(), result);
          }
        });
  }

}
