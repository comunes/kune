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
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.groups.newgroup.GroupFieldFactory;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.ui.DefaultFormUtils;
import cc.kune.core.shared.dto.GroupType;

import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.inject.Inject;

public class GroupOptGeneralPanel extends EntityOptGeneralPanel implements GroupOptGeneralView {

  public static final String CLOSED_GROUP_TYPE_ID = "k-gogp-type_of_group_closed";
  public static final String COMM_GROUP_TYPE_ID = "k-gopg-type_of_group_comm";
  private static final String LONG_NAME_FIELD = "k-gogp-longname";
  public static final String ORG_GROUP_TYPE_ID = "k-gopg-type_of_group_org";

  public static final String PROJ_GROUP_TYPE_ID = "k-gopg-type_of_group_proj";

  private static final String SHORT_NAME_FIELD = "k-gogp-shortname";
  public static final String TYPEOFGROUP_FIELD = "k-gogp-type_of_group";
  private final Radio closedRadio;
  private final Radio communityRadio;
  private final TextField<String> longName;
  private final Radio orgRadio;
  private final Radio projectRadio;
  private final TextField<String> shortName;

  @Inject
  public GroupOptGeneralPanel(final I18nTranslationService i18n, final CoreResources res,
      final MaskWidget maskWidget, final GroupFieldFactory groupFieldFactory) {
    super(maskWidget, res.emblemSystem(), i18n.t("General"), i18n.t("You can change these values:"));
    shortName = groupFieldFactory.createGroupShortName(SHORT_NAME_FIELD);
    longName = groupFieldFactory.createLongName(LONG_NAME_FIELD);
    add(shortName);
    add(longName);

    final FieldSet groupTypeFieldSet = DefaultFormUtils.createFieldSet(i18n.t("Group type"), "210px");
    groupTypeFieldSet.setWidth("210px");
    add(groupTypeFieldSet);

    projectRadio = groupFieldFactory.createProjectRadio(groupTypeFieldSet, TYPEOFGROUP_FIELD,
        PROJ_GROUP_TYPE_ID);
    projectRadio.setTabIndex(3);
    projectRadio.setValue(true);

    orgRadio = groupFieldFactory.createOrgRadio(groupTypeFieldSet, TYPEOFGROUP_FIELD, ORG_GROUP_TYPE_ID);
    orgRadio.setTabIndex(4);

    closedRadio = groupFieldFactory.createClosedRadio(groupTypeFieldSet, TYPEOFGROUP_FIELD,
        CLOSED_GROUP_TYPE_ID);
    closedRadio.setTabIndex(5);

    communityRadio = groupFieldFactory.createCommunityRadio(groupTypeFieldSet, TYPEOFGROUP_FIELD,
        COMM_GROUP_TYPE_ID);
    communityRadio.setTabIndex(6);
  }

  @Override
  public GroupType getGroupType() {
    if (projectRadio.getValue()) {
      return GroupType.PROJECT;
    } else if (orgRadio.getValue()) {
      return GroupType.ORGANIZATION;
    } else if (closedRadio.getValue()) {
      return GroupType.CLOSED;
    } else {
      return GroupType.COMMUNITY;
    }
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

  @Override
  public void setShortNameEnabled(final boolean enabled) {
    this.shortName.setEnabled(enabled);
  }

}
