package cc.kune.gspace.client.actions;

import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.resources.nav.NavResources;
import cc.kune.gspace.client.GSpaceArmor;

import com.google.inject.Inject;

public class GiveUsFeedbackBtn extends ButtonDescriptor {

  @Inject
  public GiveUsFeedbackBtn(final GiveUsFeedbackAction action, final I18nUITranslationService i18n,
      final NavResources res, final GSpaceArmor armor) {
    super(action);
    withIcon(res.refresh());
    withText(i18n.t("Give us feedback!"));
    withToolTip(i18n.t("Write us with some feedback for help us to improve the services on [%s]",
        i18n.getSiteCommonName()));
    withStyles("k-noborder, k-nobackcolor, k-no-backimage, k-fl");
    armor.getEntityFooterToolbar().add(this);
  }

}
