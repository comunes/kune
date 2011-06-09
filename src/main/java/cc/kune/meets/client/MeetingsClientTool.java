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
package cc.kune.meets.client;

import static cc.kune.meets.shared.MeetingsConstants.NAME;
import static cc.kune.meets.shared.MeetingsConstants.TYPE_MEETING;
import static cc.kune.meets.shared.MeetingsConstants.TYPE_ROOT;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.resources.nav.NavResources;
import cc.kune.gspace.client.tool.FoldableAbstractClientTool;
import cc.kune.gspace.client.tool.selector.ToolSelector;

import com.google.inject.Inject;

public class MeetingsClientTool extends FoldableAbstractClientTool {

  private final NavResources navResources;

  @Inject
  public MeetingsClientTool(final I18nUITranslationService i18n, final ToolSelector toolSelector,
      final ContentCapabilitiesRegistry cntCapRegistry, final NavResources navResources) {
    super(NAME, i18n.t("meets"), toolSelector, cntCapRegistry);
    this.navResources = navResources;

    // registerAclEditableTypes(TYPE_DOCUMENT, TYPE_UPLOADEDFILE);
    registerAuthorableTypes(TYPE_MEETING);
    registerDragableTypes(TYPE_MEETING);
    registerDropableTypes(TYPE_ROOT);
    // registerPublishModerableTypes();
    registerRateableTypes(TYPE_MEETING);
    registerRenamableTypes(TYPE_MEETING);
    registerTageableTypes(TYPE_MEETING);
    registerTranslatableTypes(TYPE_MEETING);

    registerIcons();
  }

  @Override
  public String getName() {
    return NAME;
  }

  private void registerIcons() {
    registerContentTypeIcon(TYPE_ROOT, navResources.folder());
    registerContentTypeIcon(TYPE_MEETING, navResources.calendar());
    registerEmptyMessages(TYPE_ROOT, "There isn't any meeting");
  }

}
