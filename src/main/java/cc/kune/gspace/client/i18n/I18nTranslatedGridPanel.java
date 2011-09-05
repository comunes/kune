package cc.kune.gspace.client.i18n;

import cc.kune.core.shared.dto.I18nLanguageSimpleDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;

public class I18nTranslatedGridPanel extends Composite {

  private final I18nCellList list;
  private final Label tabTitle;

  @Inject
  public I18nTranslatedGridPanel(final I18nTranslationService i18n, final I18nCellList list) {
    this.list = list;
    tabTitle = new Label(i18n.t("Translated"));
    initWidget(list);
  }

  public IsWidget getTabTitle() {
    return tabTitle;
  }

  public void setLanguage(final I18nLanguageSimpleDTO language) {
    list.setLanguage(language, false);
  }
}
