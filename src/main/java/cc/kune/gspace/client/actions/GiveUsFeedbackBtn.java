package cc.kune.gspace.client.actions;

import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.core.client.resources.nav.NavResources;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.GSpaceArmor;

import com.google.inject.Inject;

public class GiveUsFeedbackBtn extends ButtonDescriptor {

  @Inject
  public GiveUsFeedbackBtn(final GiveUsFeedbackAction action, final I18nTranslationService i18n,
      final NavResources res, final GSpaceArmor armor) {
    super(action);
    withIcon(res.refresh());
    withText(i18n.t("Give us feedback!"));
    withToolTip(i18n.t("Write us with some feedback for help us to improve these services"));
    withStyles("k-noborder, k-nobackcolor, k-no-backimage");
    armor.getToolsSouthToolbar().add(this);
  }

}
