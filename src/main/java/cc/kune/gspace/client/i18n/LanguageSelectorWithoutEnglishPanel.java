package cc.kune.gspace.client.i18n;

import cc.kune.core.client.state.Session;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;

public class LanguageSelectorWithoutEnglishPanel extends AbstractLanguageSelectorPanel {

  @Inject
  public LanguageSelectorWithoutEnglishPanel(final I18nTranslationService i18n, final Session session) {
    super(i18n, session, false);
  }

}
