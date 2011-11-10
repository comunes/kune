package cc.kune.core.client.sn.actions;

import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.sitebar.search.EntitySearchPanel;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class AddMemberSearchPanel extends EntitySearchPanel {

  @Inject
  public AddMemberSearchPanel(final CoreResources img, final I18nUITranslationService i18n) {
    super(img, i18n);
  }

}
