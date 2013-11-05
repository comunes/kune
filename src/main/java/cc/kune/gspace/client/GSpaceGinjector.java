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
package cc.kune.gspace.client;

import cc.kune.core.client.sitebar.search.SitebarSearchPresenter;
import cc.kune.gspace.client.i18n.I18nTranslatorPresenter;
import cc.kune.gspace.client.licensewizard.LicenseWizardPresenter;
import cc.kune.gspace.client.maxmin.MaxMinWorkspacePresenter;
import cc.kune.gspace.client.options.GroupOptionsPresenter;
import cc.kune.gspace.client.options.UserOptionsPresenter;
import cc.kune.gspace.client.tags.TagsSummaryPresenter;
import cc.kune.gspace.client.tool.ContentViewerSelector;
import cc.kune.gspace.client.tool.selector.ToolSelectorPresenter;
import cc.kune.gspace.client.ui.footer.license.EntityLicensePresenter;
import cc.kune.gspace.client.viewers.ContentViewerPresenter;
import cc.kune.gspace.client.viewers.FolderViewerPresenter;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.Ginjector;

// TODO: Auto-generated Javadoc
/**
 * The Interface GSpaceGinjector.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface GSpaceGinjector extends Ginjector {

  /**
   * Gets the content viewer selector.
   *
   * @return the content viewer selector
   */
  ContentViewerSelector getContentViewerSelector();

  /**
   * Gets the docs viewer presenter.
   *
   * @return the docs viewer presenter
   */
  AsyncProvider<ContentViewerPresenter> getDocsViewerPresenter();

  /**
   * Gets the entity license presenter.
   *
   * @return the entity license presenter
   */
  AsyncProvider<EntityLicensePresenter> getEntityLicensePresenter();

  /**
   * Gets the folder viewer presenter.
   *
   * @return the folder viewer presenter
   */
  AsyncProvider<FolderViewerPresenter> getFolderViewerPresenter();

  /**
   * Gets the group options presenter.
   *
   * @return the group options presenter
   */
  AsyncProvider<GroupOptionsPresenter> getGroupOptionsPresenter();

  /**
   * Gets the g space parts.
   *
   * @return the g space parts
   */
  GSpaceParts getGSpaceParts();

  /**
   * Gets the i18n translator presenter.
   *
   * @return the i18n translator presenter
   */
  AsyncProvider<I18nTranslatorPresenter> getI18nTranslatorPresenter();

  /**
   * Gets the license wizard presenter.
   *
   * @return the license wizard presenter
   */
  AsyncProvider<LicenseWizardPresenter> getLicenseWizardPresenter();

  /**
   * Gets the max min workspace presenter.
   *
   * @return the max min workspace presenter
   */
  AsyncProvider<MaxMinWorkspacePresenter> getMaxMinWorkspacePresenter();

  /**
   * Gets the sitebar search presenter.
   *
   * @return the sitebar search presenter
   */
  AsyncProvider<SitebarSearchPresenter> getSitebarSearchPresenter();

  /**
   * Gets the tags summary presenter.
   *
   * @return the tags summary presenter
   */
  AsyncProvider<TagsSummaryPresenter> getTagsSummaryPresenter();

  /**
   * Gets the tool selector presenter.
   *
   * @return the tool selector presenter
   */
  AsyncProvider<ToolSelectorPresenter> getToolSelectorPresenter();

  /**
   * Gets the user options presenter.
   *
   * @return the user options presenter
   */
  AsyncProvider<UserOptionsPresenter> getUserOptionsPresenter();
}
