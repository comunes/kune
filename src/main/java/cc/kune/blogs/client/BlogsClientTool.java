/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
package cc.kune.blogs.client;

import static cc.kune.blogs.shared.BlogsToolConstants.NAME;
import static cc.kune.blogs.shared.BlogsToolConstants.TYPE_BLOG;
import static cc.kune.blogs.shared.BlogsToolConstants.TYPE_POST;
import static cc.kune.blogs.shared.BlogsToolConstants.TYPE_ROOT;
import static cc.kune.blogs.shared.BlogsToolConstants.TYPE_UPLOADEDFILE;
import cc.kune.blogs.shared.BlogsToolConstants;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.resources.nav.NavResources;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.gspace.client.tool.FoldableAbstractClientTool;
import cc.kune.gspace.client.tool.selector.ToolSelector;

import com.google.inject.Inject;

public class BlogsClientTool extends FoldableAbstractClientTool {

  private final IconicResources icons;

  @Inject
  public BlogsClientTool(final I18nTranslationService i18n, final ToolSelector toolSelector,
      final ContentCapabilitiesRegistry cntCapRegistry, final NavResources navResources,
      final IconicResources icons) {
    super(
        NAME,
        i18n.t(BlogsToolConstants.ROOT_NAME),
        i18n.t("Blogs are a chronological list of posts (ordered by date) about a specific topic. Each post can be commented by the visitors"),
        icons.blogs(), AccessRolDTO.Viewer, toolSelector, cntCapRegistry, i18n, navResources);
    this.icons = icons;

    // registerAclEditableTypes();
    registerAuthorableTypes(TYPE_POST, TYPE_UPLOADEDFILE);
    registerDragableTypes(TYPE_UPLOADEDFILE);
    registerDropableTypes(TYPE_ROOT, TYPE_POST);
    registerPublishModerableTypes(TYPE_POST, TYPE_UPLOADEDFILE);
    registerRateableTypes(TYPE_POST, TYPE_UPLOADEDFILE);
    registerRenamableTypes(TYPE_BLOG, TYPE_POST, TYPE_UPLOADEDFILE);
    registerTageableTypes(TYPE_BLOG, TYPE_UPLOADEDFILE, TYPE_POST);
    // registerTranslatableTypes();
    registerIcons();
  }

  @Override
  public String getName() {
    return NAME;
  }

  private void registerIcons() {
    registerTutorial(TYPE_ROOT);
    // registerTutorial(TYPE_BLOG);
    registerContentTypeIcon(TYPE_ROOT, icons.blogs());
    registerContentTypeIcon(TYPE_BLOG, navResources.blog());
    registerContentTypeIcon(TYPE_POST, navResources.post());
    registerUploadTypesAndMimes(TYPE_UPLOADEDFILE);
    registerEmptyMessages(TYPE_ROOT, i18n.t("There isn't any blog, create one"));
    registerEmptyMessages(TYPE_BLOG, i18n.t("This blog hasn't any post, create one"));
    registerEmptyMessagesNotLogged(TYPE_ROOT, i18n.t("There isn't any blog"));
    registerEmptyMessagesNotLogged(TYPE_BLOG, i18n.t("This blog hasn't any post"));
  }

}
