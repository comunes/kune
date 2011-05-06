package cc.kune.gspace.client.viewers;

import cc.kune.common.client.ui.UiUtils;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.shared.dto.HasContent;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.GSpaceArmor;
import cc.kune.gspace.client.tool.ContentViewer;

import com.google.inject.Inject;

public class NoHomePageViewer implements ContentViewer {

  private final ContentTitleWidget contentTitle;
  private final GSpaceArmor gsArmor;
  private final I18nTranslationService i18n;

  @Inject
  public NoHomePageViewer(final GSpaceArmor gsArmor, final I18nTranslationService i18n,
      final ContentCapabilitiesRegistry capabilitiesRegistry) {
    this.gsArmor = gsArmor;
    this.i18n = i18n;
    contentTitle = new ContentTitleWidget(i18n, gsArmor, capabilitiesRegistry.getIconsRegistry());
  }

  @Override
  public void attach() {
    gsArmor.setContentVisible(false);
  }

  @Override
  public void detach() {
    UiUtils.clear(gsArmor.getDocHeader());
    gsArmor.setContentVisible(true);
  }

  @Override
  public void setContent(final HasContent state) {
    contentTitle.setTitle(i18n.t(CoreMessages.USER_DOESN_T_HAVE_A_HOMEPAGE), null, null, false);
  }

}
