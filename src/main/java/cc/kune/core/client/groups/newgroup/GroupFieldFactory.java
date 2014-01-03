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
package cc.kune.core.client.groups.newgroup;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.ui.DefaultForm;
import cc.kune.core.client.ui.DefaultFormUtils;
import cc.kune.core.shared.CoreConstants;

import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.inject.Inject;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating GroupField objects.
 */
@Singleton
public class GroupFieldFactory {

  /** The i18n. */
  private final I18nTranslationService i18n;

  /**
   * Instantiates a new group field factory.
   * 
   * @param i18n
   *          the i18n
   */
  @Inject
  public GroupFieldFactory(final I18nTranslationService i18n) {
    this.i18n = i18n;
  }

  /**
   * Creates a new GroupField object.
   * 
   * @param fieldSet
   *          the field set
   * @param fieldSetId
   *          the field set id
   * @param radioId
   *          the radio id
   * @return the radio
   */
  public Radio createClosedRadio(final FieldSet fieldSet, final String fieldSetId, final String radioId) {
    final Radio radio = DefaultFormUtils.createRadio(
        fieldSet,
        i18n.t("Closed"),
        fieldSetId,
        i18n.t("A Closed group is a private project, which contents are only accessible to its members (by default)."),
        radioId);
    return radio;
  }

  /**
   * Creates a new GroupField object.
   * 
   * @param fieldSet
   *          the field set
   * @param fieldSetId
   *          the field set id
   * @param radioId
   *          the radio id
   * @return the radio
   */
  public Radio createCommunityRadio(final FieldSet fieldSet, final String fieldSetId,
      final String radioId) {
    final Radio radio = DefaultFormUtils.createRadio(
        fieldSet,
        i18n.t("Community"),
        fieldSetId,
        i18n.t("A community is a group of users with shared interests (for instance the environmental community or the LGBT community). It is open to any new member to join freely without the moderation of the administrators."),
        radioId);
    return radio;
  }

  /**
   * Creates a new GroupField object.
   * 
   * @param fieldId
   *          the field id
   * @return the text field< string>
   */
  public TextField<String> createGroupShortName(final String fieldId) {
    final String minMaxText = i18n.t(CoreMessages.FIELD_MUST_BE_BETWEEN_3_AND_30);
    final TextField<String> field = new TextField<String>();
    field.setFieldLabel(i18n.t("Short name"));
    field.setName(fieldId);
    field.setId(fieldId);
    field.setWidth(175);
    field.setMinLength(3);
    field.setMaxLength(CoreConstants.MAX_SHORT_NAME_SIZE); /*
                                                            * Same in
                                                            * Group.java/
                                                            * shortName
                                                            */
    field.setAllowBlank(false);
    field.setRegex(TextUtils.SHORTNAME_UPPER_REGEXP);
    field.getMessages().setMinLengthText(minMaxText);
    field.getMessages().setMaxLengthText(minMaxText);
    field.getMessages().setRegexText(minMaxText);
    field.addStyleName("k-lower");
    field.setValidationDelay(1000);
    return field;
  }

  /**
   * Creates a new GroupField object.
   * 
   * @param fieldId
   *          the field id
   * @return the text field< string>
   */
  public TextField<String> createLongName(final String fieldId) {
    final TextField<String> field = new TextField<String>();
    field.setFieldLabel(i18n.t("Long name"));
    field.setName(fieldId);
    field.setId(fieldId);
    field.setWidth(DefaultForm.BIG_FIELD_WIDTH);
    field.setAllowBlank(false);
    field.setMinLength(3);
    field.setMaxLength(CoreConstants.MAX_LONG_NAME_SIZE); /*
                                                           * Same in
                                                           * Group.java/longName
                                                           */
    field.setValidationDelay(1000);
    return field;
  }

  /**
   * Creates a new GroupField object.
   * 
   * @param fieldSet
   *          the field set
   * @param fieldSetId
   *          the field set id
   * @param radioId
   *          the radio id
   * @return the radio
   */
  public Radio createOrgRadio(final FieldSet fieldSet, final String fieldSetId, final String radioId) {
    final Radio radio = DefaultFormUtils.createRadio(fieldSet, i18n.t("Organization"), fieldSetId,
        i18n.t("An organization works the same way as a project, "
            + "except that it must be a legal entity."), radioId);
    return radio;
  }

  /**
   * Creates a new GroupField object.
   * 
   * @param fieldSet
   *          the field set
   * @param fieldSetId
   *          the field set id
   * @param radioId
   *          the radio id
   * @return the radio
   */
  public Radio createProjectRadio(final FieldSet fieldSet, final String fieldSetId, final String radioId) {
    final Radio radio = DefaultFormUtils.createRadio(
        fieldSet,
        i18n.t("Project"),
        fieldSetId,
        i18n.t("A project is a kind of group in which the joining of new members is moderated by the project administrators."),
        radioId);
    return radio;
  }

}
