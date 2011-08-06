package cc.kune.gspace.client.i18n;

import cc.kune.common.client.notify.NotifyLevelImages;
import cc.kune.common.client.ui.dialogs.tabbed.AbstractTabbedDialogPanel;
import cc.kune.core.shared.dto.I18nLanguageDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.i18n.I18nNewTranslatorPresenter.I18nNewTranslatorView;

import com.google.inject.Inject;

public class I18nNewTranslatorPanel extends AbstractTabbedDialogPanel implements I18nNewTranslatorView {

  private static final int HEIGHT = 400;
  private static final String TRANSLATOR_ERROR_ID = "i18n-trans-panel-error";
  private static final String TRANSLATOR_PANEL_ID = "i18n-trans-panel";
  private static final int WIDTH = 400;
  private final LanguageSelectorPanel lanSelectorPanel;
  private final I18nToTranslateGridPanel toTranslateGrid;
  private final I18nTranslatedGridPanel translatedGrid;
  private final I18nTranslateRecomendPanel transRecommend;

  @Inject
  public I18nNewTranslatorPanel(final I18nTranslationService i18n, final NotifyLevelImages images,
      final TranslatorTabsCollection transGroup, final LanguageSelectorPanel lanSelectorPanel,
      final I18nToTranslateGridPanel toTranslateGrid, final I18nTranslatedGridPanel translatedGrid,
      final I18nTranslateRecomendPanel transRecommend) {
    super(TRANSLATOR_PANEL_ID, "", WIDTH, HEIGHT + 80, false, false, images, TRANSLATOR_ERROR_ID,
        i18n.t("Close"), null, null, null, transGroup);
    this.lanSelectorPanel = lanSelectorPanel;
    this.toTranslateGrid = toTranslateGrid;
    this.translatedGrid = translatedGrid;
    this.transRecommend = transRecommend;
    super.setIconCls("k-options-icon");
    super.setTitle(i18n.t("Help to translate kune"));
    super.getInnerPanel().insert(lanSelectorPanel, 0);
    lanSelectorPanel.setLangTitle(i18n.t(""));
    lanSelectorPanel.setLangSeparator("");
  }

  @Override
  public void init() {
    addTab(toTranslateGrid, toTranslateGrid.getTabTitle());
    addTab(translatedGrid, translatedGrid.getTabTitle());
    addTab(transRecommend, transRecommend.getTabTitle());
  }

  @Override
  public void setLanguage(final I18nLanguageDTO currentLanguage) {
    lanSelectorPanel.setLanguage(currentLanguage.getCode());
  }

  @Override
  public void show() {
    super.show();
  }

}
