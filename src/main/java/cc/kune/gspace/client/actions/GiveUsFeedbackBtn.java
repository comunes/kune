/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cc.kune.gspace.client.actions;

import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.resources.nav.NavResources;
import cc.kune.gspace.client.armor.GSpaceArmor;

import com.google.inject.Inject;

public class GiveUsFeedbackBtn extends ButtonDescriptor {

  public static final String ID = "k-give-feedback-btn";

  @Inject
  public GiveUsFeedbackBtn(final GiveUsFeedbackAction action, final I18nUITranslationService i18n,
      final NavResources res, final GSpaceArmor armor) {
    super(action);
    withIcon(res.refresh());
    withText(i18n.t("Give us feedback!"));
    withToolTip(i18n.t("Write us with some feedback for help us to improve the services on [%s]",
        i18n.getSiteCommonName()));
    withStyles("k-noborder, k-nobackcolor, k-no-backimage, k-fl");
    withId(ID);
    armor.getEntityFooterToolbar().add(this);
  }

}
