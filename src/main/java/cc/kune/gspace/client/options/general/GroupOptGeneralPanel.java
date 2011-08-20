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
package cc.kune.gspace.client.options.general;

import cc.kune.common.client.ui.MaskWidget;
import cc.kune.core.client.groups.newgroup.GroupFieldFactory;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.inject.Inject;

public class GroupOptGeneralPanel extends EntityOptGeneralPanel implements GroupOptGeneralView {

  private static final String LONG_NAME_FIELD = "k-gogp-longname";
  private static final String SHORT_NAME_FIELD = "k-gogp-shortname";
  private final TextField<String> longName;
  private final TextField<String> shortName;

  @Inject
  public GroupOptGeneralPanel(final I18nTranslationService i18n, final CoreResources res,
      final MaskWidget maskWidget) {
    super(i18n, res, maskWidget);
    shortName = GroupFieldFactory.createUserShortName(SHORT_NAME_FIELD);
    longName = GroupFieldFactory.createUserLongName(LONG_NAME_FIELD);
    add(shortName);
    add(longName);
  }

  @Override
  public String getLongName() {
    return longName.getValue();
  }

  @Override
  public String getShortName() {
    return shortName.getValue();
  }

  @Override
  public void setLongName(final String longName) {
    this.longName.setValue(longName);
  }

  @Override
  public void setShortName(final String shortName) {
    this.shortName.setValue(shortName);
  }

}
