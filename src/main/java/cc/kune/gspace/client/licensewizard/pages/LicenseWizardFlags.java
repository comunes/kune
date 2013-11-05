/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
package cc.kune.gspace.client.licensewizard.pages;

import cc.kune.common.client.resources.CommonResources;
import cc.kune.common.client.ui.IconLabel;
import cc.kune.common.client.ui.KuneWindowUtils;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.iconic.IconicResources;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;

public class LicenseWizardFlags extends FlowPanel {
  private final IconLabel copyleft;
  private final IconLabel culturalWorks;
  private final IconLabel nonCommercialReasons;
  private final IconLabel nonCopyleft;
  private final IconLabel nonCulturalWorks;

  public LicenseWizardFlags(final IconicResources images, final CommonResources commonRes,
      final I18nTranslationService i18n) {
    copyleft = new IconLabel(images.copyleftGrey(), i18n.t("This is a copyleft license."));
    nonCopyleft = new IconLabel(images.noCopyleftGrey(), i18n.t("This is not a copyleft license."));
    culturalWorks = new IconLabel(images.info(), i18n.t("This is appropriate for free cultural works."));
    nonCulturalWorks = new IconLabel(commonRes.alert(),
        i18n.t("This is not appropriate for free cultural works."));
    nonCommercialReasons = new IconLabel(commonRes.alert(),
        i18n.t("Reasons not to use a non commercial license."));
    addLink(copyleft, "http://en.wikipedia.org/wiki/Copyleft");
    addLink(nonCopyleft, "http://en.wikipedia.org/wiki/Copyleft");
    addLink(culturalWorks, "http://freedomdefined.org/");
    addLink(nonCulturalWorks, "http://freedomdefined.org/");
    addLink(nonCommercialReasons, "http://freedomdefined.org/Licenses/NC");
    add(copyleft);
    add(nonCopyleft);
    add(culturalWorks);
    add(nonCulturalWorks);
    add(nonCommercialReasons);
  }

  private void addLink(final IconLabel label, final String url) {
    label.addDomHandler(new ClickHandler() {

      @Override
      public void onClick(final ClickEvent event) {
        openWindow(url);
      }

      private void openWindow(final String url) {
        KuneWindowUtils.open(url);
      }
    }, ClickEvent.getType());

    label.addStyleName("k-info-links");
  }

  public void setCopyleft(final boolean isCopyleft) {
    copyleft.setVisible(isCopyleft);
    nonCopyleft.setVisible(!isCopyleft);
  }

  public void setCulturalWorks(final boolean isAppropiateForCulturalWorks) {
    culturalWorks.setVisible(isAppropiateForCulturalWorks);
    nonCulturalWorks.setVisible(!isAppropiateForCulturalWorks);
  }

  public void setNonComercial(final boolean isNonComercial) {
    nonCommercialReasons.setVisible(isNonComercial);
  }

  public void setVisible(final boolean isCopyleft, final boolean isAppropiateForCulturalWorks,
      final boolean isNonComercial) {
    setCopyleft(isCopyleft);
    setCulturalWorks(isAppropiateForCulturalWorks);
    setNonComercial(isNonComercial);
  }
}
