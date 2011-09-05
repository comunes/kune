package cc.kune.gspace.client.i18n;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.ui.dialogs.tabbed.AbstractTabbedDialog;
import cc.kune.common.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.rpcservices.I18nServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.I18nLanguageDTO;
import cc.kune.core.shared.dto.I18nLanguageSimpleDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class I18nNewTranslatorPresenter extends AbstractTabbedDialogPresenter implements
    AbstractTabbedDialog, I18nTranslator {

  public interface I18nNewTranslatorView extends AbstractTabbedDialogView {

    @Override
    void hide();

    void init();

    void setLanguage(I18nLanguageSimpleDTO currentLanguage);

    @Override
    void show();
  }
  private final I18nUITranslationService i18n;
  private final Provider<I18nServiceAsync> i18nService;
  private final Session session;
  private I18nNewTranslatorView view;

  @Inject
  public I18nNewTranslatorPresenter(final Session session, final I18nUITranslationService i18n,
      final I18nNewTranslatorView view, final Provider<I18nServiceAsync> i18nService) {
    this.session = session;
    this.i18n = i18n;
    this.i18nService = i18nService;
    init(view);
  }

  public void doClose() {
    view.hide();
  }

  public void doTranslation(final String id, final String trKey, final String translation) {
    NotifyUser.showProgress(i18n.t("Saving"));
    i18nService.get().setTranslation(session.getUserHash(), id, translation,
        new AsyncCallback<String>() {
          @Override
          public void onFailure(final Throwable caught) {
            NotifyUser.hideProgress();
            NotifyUser.error(i18n.t("Server error saving the translation"));
          }

          @Override
          public void onSuccess(final String result) {
            NotifyUser.hideProgress();
            i18n.setTranslationAfterSave(trKey, result);
          }
        });
  }

  @Override
  public IsWidget getView() {
    return view;
  }

  @Override
  public void hide() {
    view.hide();
  }

  public void init(final I18nNewTranslatorView view) {
    super.init(view);
    this.view = view;
    view.init();
  }

  @Override
  public void show() {
    NotifyUser.showProgressLoading();
    if (session.isLogged()) {
      final I18nLanguageDTO userLang = session.getCurrentLanguage();
      view.setLanguage(new I18nLanguageSimpleDTO(userLang.getCode(), userLang.getEnglishName()));
      view.show();
    } else {
      NotifyUser.info(i18n.t("Sign in or register to help with the translation"));
    }
    NotifyUser.hideProgress();
  }
}
