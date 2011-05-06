package cc.kune.wiki.client.actions;

import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.core.client.resources.nav.NavResources;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.actions.NewContentBtn;
import cc.kune.wiki.shared.WikiConstants;

import com.google.inject.Inject;

public class NewWikiBtn extends NewContentBtn {

  @Inject
  public NewWikiBtn(final I18nTranslationService i18n, final NewContentAction action,
      final NavResources res, final GlobalShortcutRegister shorcutReg) {
    super(i18n, action, res.wikipageAdd(), shorcutReg, i18n.t("New wikipage"),
        i18n.t("Create a New Wikipage here. "
            + "This document will be a new 'Page' in the public web if you publish it"),
        i18n.t("New wikipage"), WikiConstants.TYPE_WIKIPAGE);
  }

}
