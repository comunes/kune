package cc.kune.blogs.client.actions;

import cc.kune.blogs.shared.BlogsConstants;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.core.client.resources.nav.NavResources;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.actions.NewContentBtn;

import com.google.inject.Inject;

public class NewPostBtn extends NewContentBtn {

  @Inject
  public NewPostBtn(final I18nTranslationService i18n, final NewContentAction action,
      final NavResources res, final GlobalShortcutRegister shorcutReg) {
    super(i18n, action, res.blogAdd(), shorcutReg, i18n.t("New post"), i18n.t("Create a new blog post"),
        i18n.t("New post"), BlogsConstants.TYPE_POST);
  }

}
