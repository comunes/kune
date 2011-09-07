package cc.kune.gspace.client.i18n;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.ui.dialogs.tabbed.AbstractTabbedDialog;
import cc.kune.common.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter;
import cc.kune.common.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter.AbstractTabbedDialogView;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.rpcservices.I18nServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.I18nLanguageDTO;
import cc.kune.core.shared.dto.I18nLanguageSimpleDTO;
import cc.kune.gspace.client.i18n.I18nTranslatorPresenter.I18nTranslatorView;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;

public class I18nTranslatorPresenter extends
    AbstractTabbedDialogPresenter<I18nTranslatorView, I18nTranslatorPresenter.I18nTranslatorProxy>
    implements AbstractTabbedDialog, I18nTranslator {

  @ProxyCodeSplit
  public interface I18nTranslatorProxy extends Proxy<I18nTranslatorPresenter> {
  }

  public interface I18nTranslatorView extends AbstractTabbedDialogView {
    void hide();

    void init();

    void setLanguage(I18nLanguageSimpleDTO currentLanguage);

    void show();

  }

  private final I18nUITranslationService i18n;
  private final Provider<I18nServiceAsync> i18nService;
  private final Session session;

  @Inject
  public I18nTranslatorPresenter(final EventBus eventBus, final I18nTranslatorProxy proxy,
      final Session session, final I18nUITranslationService i18n, final I18nTranslatorView view,
      final Provider<I18nServiceAsync> i18nService) {
    super(eventBus, view, proxy);
    this.session = session;
    this.i18n = i18n;
    this.i18nService = i18nService;
  }

  public void doClose() {
    getView().hide();
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
  public I18nTranslatorView getView() {
    return (I18nTranslatorView) super.getView();
  }

  @Override
  public void hide() {
    getView().hide();
  }

  @Override
  protected void onBind() {
    super.onBind();
    getView().init();
  }

  @Override
  public void show() {
    NotifyUser.showProgressLoading();
    if (session.isLogged()) {
      final I18nLanguageDTO userLang = session.getCurrentLanguage();
      getView().setLanguage(new I18nLanguageSimpleDTO(userLang.getCode(), userLang.getEnglishName()));
      getView().show();
    } else {
      NotifyUser.info(i18n.t("Sign in or register to help with the translation"));
    }
    NotifyUser.hideProgress();
  }
}
