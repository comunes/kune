package cc.kune.gspace.client.i18n;

import cc.kune.common.client.utils.TextUtils;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;

public class I18nTranslatedGridPanel extends Composite {

  private final Label tabTitle;

  @Inject
  public I18nTranslatedGridPanel(final I18nTranslationService i18n) {
    tabTitle = new Label(i18n.t("Translated"));
    initWidget(new Label(TextUtils.IN_DEVELOPMENT));

  }

  public IsWidget getTabTitle() {
    return tabTitle;
  }
}
