package cc.kune.gspace.client.i18n;

import cc.kune.common.client.ui.dialogs.tabbed.AbstractTabbedDialog;
import cc.kune.common.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.I18nLanguageDTO;
import cc.kune.core.shared.dto.I18nLanguageSimpleDTO;
import cc.kune.gspace.client.i18n.I18nTranslatorPresenter.I18nTranslatorView;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;

public class I18nTranslatorPresenter extends
    AbstractTabbedDialogPresenter<I18nTranslatorView, I18nTranslatorPresenter.I18nTranslatorProxy>
    implements AbstractTabbedDialog, I18nTranslator {

  @ProxyCodeSplit
  public interface I18nTranslatorProxy extends Proxy<I18nTranslatorPresenter> {
  }

  public interface I18nTranslatorView extends
      cc.kune.common.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter.AbstractTabbedDialogView {

    void init();

    void setLanguage(I18nLanguageSimpleDTO currentLanguage);

  }

  private final Session session;

  @Inject
  public I18nTranslatorPresenter(final EventBus eventBus, final I18nTranslatorProxy proxy,
      final Session session, final I18nUITranslationService i18n, final I18nTranslatorView view) {
    super(eventBus, view, proxy);
    this.session = session;
  }

  public void doClose() {
    getView().hide();
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
    final I18nLanguageDTO userLang = session.getCurrentLanguage();
    getView().setLanguage(new I18nLanguageSimpleDTO(userLang.getCode(), userLang.getEnglishName()));
    getView().show();
  }
}
