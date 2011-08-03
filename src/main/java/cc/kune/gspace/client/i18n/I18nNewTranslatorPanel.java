package cc.kune.gspace.client.i18n;


import cc.kune.common.client.notify.NotifyLevelImages;
import cc.kune.common.client.ui.dialogs.tabbed.AbstractTabbedDialogPanel;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.i18n.I18nNewTranslatorPresenter.I18nNewTranslatorView;

import com.google.inject.Inject;

public class I18nNewTranslatorPanel extends AbstractTabbedDialogPanel implements I18nNewTranslatorView {

  private static final int HEIGHT = 400;
  private static final String TRANSLATOR_ERROR_ID = "i18n-trans-panel-error";
  private static final String TRANSLATOR_PANEL_ID = "i18n-trans-panel";
  private static final int WIDTH = 270;

  @Inject
  public I18nNewTranslatorPanel(final I18nTranslationService i18n, final NotifyLevelImages images,
      final TranslatorTabsCollection transGroup) {
    super(TRANSLATOR_PANEL_ID, "", WIDTH, HEIGHT + 80, false, images, TRANSLATOR_ERROR_ID,
        i18n.t("Close"), null, null, null, transGroup);
    super.setIconCls("k-options-icon");
    super.setTitle(i18n.t("Help to translate kune"));
  }

  @Override
  public void hideTranslatorAndIcon() {
    // TODO Auto-generated method stub

  }

}
