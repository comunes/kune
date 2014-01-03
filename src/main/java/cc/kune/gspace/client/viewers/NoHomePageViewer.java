/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
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
package cc.kune.gspace.client.viewers;

import cc.kune.common.client.ui.UiUtils;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.shared.dto.HasContent;
import cc.kune.gspace.client.armor.GSpaceArmor;
import cc.kune.gspace.client.tool.ContentViewer;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class NoHomePageViewer.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class NoHomePageViewer implements ContentViewer {

  /** The content title. */
  private final ContentTitleWidget contentTitle;

  /** The gs armor. */
  private final GSpaceArmor gsArmor;

  /** The i18n. */
  private final I18nTranslationService i18n;

  /**
   * Instantiates a new no home page viewer.
   * 
   * @param gsArmor
   *          the gs armor
   * @param i18n
   *          the i18n
   * @param capabilitiesRegistry
   *          the capabilities registry
   */
  @Inject
  public NoHomePageViewer(final GSpaceArmor gsArmor, final I18nTranslationService i18n,
      final ContentCapabilitiesRegistry capabilitiesRegistry) {
    this.gsArmor = gsArmor;
    this.i18n = i18n;
    contentTitle = new ContentTitleWidget(i18n, gsArmor, capabilitiesRegistry.getIconsRegistry());
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.tool.ContentViewer#attach()
   */
  @Override
  public void attach() {
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.tool.ContentViewer#detach()
   */
  @Override
  public void detach() {
    UiUtils.clear(gsArmor.getDocHeader());
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.tool.ContentViewer#setContent(cc.kune.core.shared
   * .dto.HasContent)
   */
  @Override
  public void setContent(final HasContent state) {
    contentTitle.setTitle(i18n.t(CoreMessages.USER_DOESN_T_HAVE_A_HOMEPAGE), null, null, false);
  }

}
