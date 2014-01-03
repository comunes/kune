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
package cc.kune.gspace.client.options.general;

import cc.kune.common.client.ui.MaskWidget;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.groups.newgroup.GroupFieldFactory;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.ui.DefaultFormUtils;
import cc.kune.core.shared.dto.GroupType;

import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class GroupOptGeneralPanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GroupOptGeneralPanel extends EntityOptGeneralPanel implements GroupOptGeneralView {

  /** The Constant CLOSED_GROUP_TYPE_ID. */
  public static final String CLOSED_GROUP_TYPE_ID = "k-gogp-type_of_group_closed";

  /** The Constant COMM_GROUP_TYPE_ID. */
  public static final String COMM_GROUP_TYPE_ID = "k-gopg-type_of_group_comm";

  /** The Constant LONG_NAME_FIELD. */
  private static final String LONG_NAME_FIELD = "k-gogp-longname";

  /** The Constant ORG_GROUP_TYPE_ID. */
  public static final String ORG_GROUP_TYPE_ID = "k-gopg-type_of_group_org";

  /** The Constant PROJ_GROUP_TYPE_ID. */
  public static final String PROJ_GROUP_TYPE_ID = "k-gopg-type_of_group_proj";

  /** The Constant SHORT_NAME_FIELD. */
  private static final String SHORT_NAME_FIELD = "k-gogp-shortname";

  /** The Constant TYPEOFGROUP_FIELD. */
  public static final String TYPEOFGROUP_FIELD = "k-gogp-type_of_group";

  /** The closed radio. */
  private final Radio closedRadio;

  /** The community radio. */
  private final Radio communityRadio;

  /** The long name. */
  private final TextField<String> longName;

  /** The org radio. */
  private final Radio orgRadio;

  /** The project radio. */
  private final Radio projectRadio;

  /** The short name. */
  private final TextField<String> shortName;

  /**
   * Instantiates a new group opt general panel.
   * 
   * @param i18n
   *          the i18n
   * @param res
   *          the res
   * @param maskWidget
   *          the mask widget
   * @param groupFieldFactory
   *          the group field factory
   */
  @Inject
  public GroupOptGeneralPanel(final I18nTranslationService i18n, final IconicResources res,
      final MaskWidget maskWidget, final GroupFieldFactory groupFieldFactory) {
    super(maskWidget, res.equalizerWhite(), i18n.t("General"), i18n.t("You can change these values:"));
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.general.GroupOptGeneralView#getGroupType()
   */
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.general.GroupOptGeneralView#getLongName()
   */
  @Override
  public String getLongName() {
    return longName.getValue();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.general.GroupOptGeneralView#getShortName()
   */
  @Override
  public String getShortName() {
    return shortName.getValue();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.general.GroupOptGeneralView#setGroupType(
   * cc.kune.core.shared.dto.GroupType)
   */
  @Override
  public void setGroupType(final GroupType groupType) {
    switch (groupType) {
    case PROJECT:
      projectRadio.setValue(true);
      break;
    case ORGANIZATION:
      orgRadio.setValue(true);
      break;
    case CLOSED:
      closedRadio.setValue(true);
      break;
    case COMMUNITY:
      communityRadio.setValue(true);
      break;
    default:
      throw new RuntimeException("Unexpected grouptype");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.general.GroupOptGeneralView#setLongName(java
   * .lang.String)
   */
  @Override
  public void setLongName(final String longName) {
    this.longName.setValue(longName);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.general.GroupOptGeneralView#setShortName(
   * java.lang.String)
   */
  @Override
  public void setShortName(final String shortName) {
    this.shortName.setValue(shortName);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.general.GroupOptGeneralView#setShortNameEnabled
   * (boolean)
   */
  @Override
  public void setShortNameEnabled(final boolean enabled) {
    this.shortName.setEnabled(enabled);
  }

}
