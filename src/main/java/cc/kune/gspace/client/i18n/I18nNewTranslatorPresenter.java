package cc.kune.gspace.client.i18n;


import cc.kune.common.client.ui.dialogs.tabbed.AbstractTabbedDialog;
import cc.kune.common.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter;

import com.google.inject.Inject;

public class I18nNewTranslatorPresenter extends AbstractTabbedDialogPresenter implements
    AbstractTabbedDialog, I18nTranslator {

  public interface I18nNewTranslatorView extends AbstractTabbedDialogView {

    @Override
    void hide();

    void hideTranslatorAndIcon();

    @Override
    void show();
  }
  private I18nNewTranslatorView view;

  @Inject
  public I18nNewTranslatorPresenter() {
  }

  @Override
  public void doShowTranslator() {
    view.show();
  }

  public void init(final I18nNewTranslatorView view) {
    super.init(view);
    this.view = view;
  }
}
