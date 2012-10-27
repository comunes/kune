/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
package cc.kune.core.client.groups.newgroup;

import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.client.notify.NotifyLevelImages;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.client.ui.MaskWidgetView;
import cc.kune.common.client.ui.dialogs.BasicTopDialog;
import cc.kune.common.client.ui.dialogs.BasicTopDialog.Builder;
import cc.kune.common.client.ui.dialogs.MessageToolbar;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.ui.DefaultForm;
import cc.kune.core.client.ui.DefaultFormUtils;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasCloseHandlers;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class NewGroupPanel extends ViewImpl implements NewGroupView {
  public static final String CANCEL_BUTTON = "k-ngp-cancel-bt";
  public static final String CLOSED_GROUP_TYPE_ID = "k-ngp-type_of_group_closed";
  public static final String COMM_GROUP_TYPE_ID = "k-ngp-type_of_group_comm";
  public static final String ERROR_MSG_BAR = "k-ngp-error-mb";
  private static final int LABEL_WIDTH = 100;
  public static final String LONGNAME_FIELD = "k-ngp-long_name";
  public static final String NEWGROUP_WIZARD = "k-ngp-wiz";
  public static final String ORG_GROUP_TYPE_ID = "k-ngp-type_of_group_org";
  public static final String PROJ_GROUP_TYPE_ID = "k-ngp-type_of_group_proj";
  public static final String PUBLICDESC_FIELD = "k-ngp-public_desc";
  public static final String REGISTER_BUTTON = "k-ngp-finish-bt";
  public static final String SHORTNAME_FIELD = "k-ngp-short_name";
  public static final String TAGS_FIELD = "k-ngp-tags";
  public static final String TYPEOFGROUP_FIELD = "k-ngp-type_of_group";

  private Radio closedRadio;
  private Radio communityRadio;
  private final BasicTopDialog dialog;

  private final FormPanel form;
  private final GroupFieldFactory groupFieldFactory;
  private final I18nTranslationService i18n;
  private TextField<String> longNameField;
  private final MaskWidgetView mask;
  private final MessageToolbar messageErrorBar;
  private Radio orgRadio;
  private Radio projectRadio;
  private TextArea publicDescField;

  private TextField<String> shortNameField;
  private TextField<String> tag1;

  // private TextField<String> tag2;
  // private TextField<String> tag3;

  @Inject
  public NewGroupPanel(final I18nTranslationService i18n, final NotifyLevelImages img,
      final MaskWidgetView mask, final GroupFieldFactory groupFieldFactory) {
    this.groupFieldFactory = groupFieldFactory;
    final Builder builder = new BasicTopDialog.Builder(NEWGROUP_WIZARD, false, true, i18n.getDirection()).autoscroll(
        true).title(i18n.t("Register a new group"));
    builder.icon("k-newgroup-icon");
    builder.firstButtonTitle(i18n.t("Register")).firstButtonId(REGISTER_BUTTON);
    builder.sndButtonTitle(i18n.t("Cancel")).sndButtonId(CANCEL_BUTTON);
    builder.tabIndexStart(10);
    dialog = builder.build();
    this.i18n = i18n;
    this.mask = mask;
    form = createNewGroupInitialDataForm();

    messageErrorBar = new MessageToolbar(img, ERROR_MSG_BAR);
    form.add(messageErrorBar);
    dialog.getInnerPanel().add(form);
  }

  @Override
  public Widget asWidget() {
    return null;
  }

  @Override
  public void clearData() {
    form.reset();
    shortNameField.focus();
    messageErrorBar.hideErrorMessage();
  }

  private FormPanel createNewGroupInitialDataForm() {
    final FormPanel form = new FormPanel();
    form.setFrame(true);
    form.setPadding(10);
    form.setBorders(false);
    form.setWidth(360);
    form.setLabelWidth(LABEL_WIDTH);
    form.setLabelAlign(LabelAlign.RIGHT);
    form.setButtonAlign(HorizontalAlignment.RIGHT);
    form.setHeaderVisible(false);
    //
    // final Label intro = new Label();
    // intro.setText(i18n.t("Please fill this form to register a new group:"));
    // intro.setStyleName("k-form-intro");
    // form.add(intro);

    longNameField = groupFieldFactory.createLongName(LONGNAME_FIELD);
    longNameField.setTabIndex(1);
    form.add(longNameField);

    shortNameField = groupFieldFactory.createGroupShortName(SHORTNAME_FIELD);
    shortNameField.setTabIndex(2);
    form.add(shortNameField);

    publicDescField = new TextArea();
    publicDescField.setTabIndex(3);
    publicDescField.setFieldLabel(i18n.t("Public description"));
    publicDescField.setName(PUBLICDESC_FIELD);
    publicDescField.setId(PUBLICDESC_FIELD);
    publicDescField.setWidth(DefaultForm.BIG_FIELD_WIDTH);
    publicDescField.setAllowBlank(false);
    publicDescField.setMinLength(10);
    publicDescField.setMaxLength(255);
    publicDescField.setValidationDelay(1000);
    form.add(publicDescField);

    tag1 = createTagField(tag1);
    form.add(tag1);
    // form.add(createTagField(tag2));
    // form.add(createTagField(tag3));

    final FieldSet groupTypeFieldSet = DefaultFormUtils.createFieldSet(i18n.t("Group type"), "210px");
    groupTypeFieldSet.setWidth("210px");
    form.add(groupTypeFieldSet);

    projectRadio = groupFieldFactory.createProjectRadio(groupTypeFieldSet, TYPEOFGROUP_FIELD,
        PROJ_GROUP_TYPE_ID);
    projectRadio.setTabIndex(6);
    projectRadio.setValue(true);

    orgRadio = groupFieldFactory.createOrgRadio(groupTypeFieldSet, TYPEOFGROUP_FIELD, ORG_GROUP_TYPE_ID);
    orgRadio.setTabIndex(7);

    closedRadio = groupFieldFactory.createClosedRadio(groupTypeFieldSet, TYPEOFGROUP_FIELD,
        CLOSED_GROUP_TYPE_ID);
    closedRadio.setTabIndex(8);

    communityRadio = groupFieldFactory.createCommunityRadio(groupTypeFieldSet, TYPEOFGROUP_FIELD,
        COMM_GROUP_TYPE_ID);
    communityRadio.setTabIndex(9);

    return form;
  }

  private TextField<String> createTagField(TextField<String> tag) {
    tag = new TextField<String>();
    tag.setTabIndex(4);
    tag.setFieldLabel(i18n.t("Group tags"));
    tag.setName(TAGS_FIELD);
    // FIXME if we us this for several tags, we should use different ids
    tag.setId(TAGS_FIELD);
    tag.setWidth(DefaultForm.BIG_FIELD_WIDTH);
    tag.setAllowBlank(false);
    Tooltip.to(tag, i18n.t("Type some keywords that define your group"));
    tag.setValidationDelay(1000);
    return tag;
  }

  @Override
  public void focusOnShortName() {
    shortNameField.focus();
  }

  @Override
  public void focusOnLongName() {
    longNameField.focus();
  }

  @Override
  public HasCloseHandlers<PopupPanel> getClose() {
    return dialog.getClose();
  }

  @Override
  public HasClickHandlers getFirstBtn() {
    return dialog.getFirstBtn();
  }

  @Override
  public String getLongName() {
    return longNameField.getValue();
  }

  @Override
  public String getPublicDesc() {
    return publicDescField.getValue();
  }

  @Override
  public HasClickHandlers getSecondBtn() {
    return dialog.getSecondBtn();
  }

  @Override
  public String getShortName() {
    return shortNameField.getValue();
  }

  @Override
  public String getTags() {
    return tag1.getRawValue();
  }

  @Override
  public void hide() {
    dialog.hide();
  }

  @Override
  public void hideMessage() {
    messageErrorBar.hideErrorMessage();
  }

  @Override
  public boolean isClosed() {
    return closedRadio.getValue();
  }

  @Override
  public boolean isCommunity() {
    return communityRadio.getValue();
  }

  @Override
  public boolean isFormValid() {
    return form.isValid();
  }

  @Override
  public boolean isOrganization() {
    return orgRadio.getValue();
  }

  @Override
  public boolean isProject() {
    return projectRadio.getValue();
  }

  public void mask(final String message) {
    mask.mask(dialog);
  }

  @Override
  public void maskProcessing() {
    mask.mask(dialog, i18n.t("Processing"));
  }

  @Override
  public void setLongNameFailed(final String msg) {
    longNameField.markInvalid(msg);
  }

  @Override
  public void setMessage(final String message, final NotifyLevel level) {
    messageErrorBar.setErrorMessage(message, level);
  }

  @Override
  public void setShortNameFailed(final String msg) {
    shortNameField.markInvalid(msg);
  }

  @Override
  public void show() {
    dialog.showCentered();
  }

  @Override
  public void unMask() {
    mask.unMask();
  }

}
