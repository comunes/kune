package cc.kune.blogs.client.actions;

import cc.kune.blogs.shared.BlogsConstants;
import cc.kune.core.client.resources.nav.NavResources;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.actions.NewContainerBtn;

import com.google.inject.Inject;

public class NewBlogBtn extends NewContainerBtn {

  @Inject
  public NewBlogBtn(final I18nTranslationService i18n, final NewContainerAction action,
      final NavResources res) {
    super(i18n, action, res.blogAdd(), i18n.t("New blog"), i18n.t("Create a new blog"),
        i18n.t("New blog"), BlogsConstants.TYPE_BLOG);
  }

}
