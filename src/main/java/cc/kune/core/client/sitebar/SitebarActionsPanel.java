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
package cc.kune.core.client.sitebar;

import cc.kune.common.client.actions.ui.ActionSimplePanel;
import cc.kune.common.client.actions.ui.IsActionExtensible;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.sitebar.SitebarActionsPresenter.SitebarActionsView;
import cc.kune.core.client.sitebar.logo.SiteLogo;
import cc.kune.gspace.client.armor.GSpaceArmor;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.ViewImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class SitebarActionsPanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SitebarActionsPanel extends ViewImpl implements SitebarActionsView {

  /** The about panel. */
  private final Provider<AboutKuneDialog> aboutPanel;

  /** The error panel. */
  private final Provider<ErrorsDialog> errorPanel;

  /** The toolbar left. */
  private final ActionSimplePanel toolbarLeft;

  /** The toolbar right. */
  private final ActionSimplePanel toolbarRight;

  /**
   * Instantiates a new sitebar actions panel.
   * 
   * @param armor
   *          the armor
   * @param toolbarRight
   *          the toolbar right
   * @param toolbarLeft
   *          the toolbar left
   * @param i18n
   *          the i18n
   * @param aboutPanel
   *          the about panel
   * @param errorPanel
   *          the error panel
   * @param siteLogo
   *          the site logo
   */
  @Inject
  public SitebarActionsPanel(final GSpaceArmor armor, final ActionSimplePanel toolbarRight,
      final ActionSimplePanel toolbarLeft, final I18nTranslationService i18n,
      final Provider<AboutKuneDialog> aboutPanel, final Provider<ErrorsDialog> errorPanel,
      final SiteLogo siteLogo) {
    this.toolbarRight = toolbarRight;
    this.toolbarLeft = toolbarLeft;
    this.aboutPanel = aboutPanel;
    this.errorPanel = errorPanel;
    toolbarRight.addStyleName("k-sitebar");
    toolbarRight.addStyleName("k-floatright");
    toolbarLeft.addStyleName("k-sitebar");
    toolbarLeft.addStyleName("k-floatleft");
    armor.getSitebar().add(toolbarLeft);
    armor.getSitebar().add(siteLogo);
    armor.getSitebar().add(toolbarRight);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.View#asWidget()
   */
  @Override
  public Widget asWidget() {
    return toolbarRight;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.SitebarActionsPresenter.SitebarActionsView#
   * getLeftBar()
   */
  @Override
  public IsActionExtensible getLeftBar() {
    return toolbarLeft;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.SitebarActionsPresenter.SitebarActionsView#
   * getRightBar()
   */
  @Override
  public IsActionExtensible getRightBar() {
    return toolbarRight;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.SitebarActionsPresenter.SitebarActionsView#
   * showAboutDialog()
   */
  @Override
  public void showAboutDialog() {
    aboutPanel.get().showCentered();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.SitebarActionsPresenter.SitebarActionsView#
   * showErrorDialog()
   */
  @Override
  public void showErrorDialog() {
    errorPanel.get().showCentered();
  }

}
