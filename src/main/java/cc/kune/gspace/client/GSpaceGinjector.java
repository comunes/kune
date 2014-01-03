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
package cc.kune.gspace.client;

import cc.kune.core.client.sitebar.search.SitebarSearchPresenter;
import cc.kune.gspace.client.i18n.I18nTranslatorPresenter;
import cc.kune.gspace.client.licensewizard.LicenseWizardPresenter;
import cc.kune.gspace.client.maxmin.MaxMinWorkspacePresenter;
import cc.kune.gspace.client.options.GroupOptionsPresenter;
import cc.kune.gspace.client.options.UserOptionsPresenter;
import cc.kune.gspace.client.share.ShareDialogPresenter;
import cc.kune.gspace.client.tags.TagsSummaryPresenter;
import cc.kune.gspace.client.tool.ContentViewerSelector;
import cc.kune.gspace.client.tool.selector.ToolSelectorPresenter;
import cc.kune.gspace.client.ui.footer.license.EntityLicensePresenter;
import cc.kune.gspace.client.viewers.ContentViewerPresenter;
import cc.kune.gspace.client.viewers.FolderViewerPresenter;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.Ginjector;

public interface GSpaceGinjector extends Ginjector {

  ContentViewerSelector getContentViewerSelector();

  AsyncProvider<ContentViewerPresenter> getDocsViewerPresenter();

  AsyncProvider<EntityLicensePresenter> getEntityLicensePresenter();

  AsyncProvider<FolderViewerPresenter> getFolderViewerPresenter();

  AsyncProvider<GroupOptionsPresenter> getGroupOptionsPresenter();

  GSpaceParts getGSpaceParts();

  AsyncProvider<I18nTranslatorPresenter> getI18nTranslatorPresenter();

  AsyncProvider<LicenseWizardPresenter> getLicenseWizardPresenter();

  AsyncProvider<MaxMinWorkspacePresenter> getMaxMinWorkspacePresenter();

  AsyncProvider<ShareDialogPresenter> getShareDialogPresenter();

  AsyncProvider<SitebarSearchPresenter> getSitebarSearchPresenter();

  AsyncProvider<TagsSummaryPresenter> getTagsSummaryPresenter();

  AsyncProvider<ToolSelectorPresenter> getToolSelectorPresenter();

  AsyncProvider<UserOptionsPresenter> getUserOptionsPresenter();
}
