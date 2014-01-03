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
package cc.kune.gspace.client.licensewizard.pages;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.SimpleCallback;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.ui.DefaultForm;
import cc.kune.core.client.ui.DefaultFormUtils;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class LicenseWizardFirstForm.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class LicenseWizardFirstForm extends DefaultForm implements LicenseWizardFirstFormView {

  /** The Constant POINT. */
  private static final String POINT = "»&nbsp;";

  /** The Constant RADIO_ANOTHER_ID. */
  public static final String RADIO_ANOTHER_ID = "k-lwff-another";

  /** The Constant RADIO_COPYLEFT_ID. */
  public static final String RADIO_COPYLEFT_ID = "k-lwff-copyleft";

  /** The Constant RADIO_FIELD_NAME. */
  private static final String RADIO_FIELD_NAME = "k-lwff-radio";

  /** The another license radio. */
  private final Radio anotherLicenseRadio;

  /** The copyleft radio. */
  private final Radio copyleftRadio;

  /** The on change. */
  private SimpleCallback onChange;

  /**
   * Instantiates a new license wizard first form.
   * 
   * @param i18n
   *          the i18n
   */
  @Inject
  public LicenseWizardFirstForm(final I18nTranslationService i18n) {
    super.setFrame(true);
    super.setPadding(10);
    super.setAutoHeight(true);
    // super.setHeight(LicenseWizardView.HEIGHT);

    final Label intro = new Label();
    intro.setText(i18n.t("Select the license you prefer using for sharing your group contents with other people:"));
    intro.addStyleName("kune-Margin-10-b");
    final FieldSet fieldSet = new FieldSet();
    // fieldSet.setTitle(i18n.t("license recommended"));
    fieldSet.addStyleName("margin-left: 105px");
    fieldSet.setWidth(250);
    copyleftRadio = DefaultFormUtils.createRadio(fieldSet,
        i18n.t("Use a copyleft license (recommended)"), RADIO_FIELD_NAME, null, RADIO_COPYLEFT_ID);
    anotherLicenseRadio = DefaultFormUtils.createRadio(fieldSet,
        i18n.t("Use another kind of license (advanced)"), RADIO_FIELD_NAME, null, RADIO_ANOTHER_ID);

    final RadioGroup radioGroup = new RadioGroup();
    radioGroup.add(copyleftRadio);
    radioGroup.add(anotherLicenseRadio);
    radioGroup.setOrientation(Orientation.VERTICAL);
    radioGroup.setHideLabel(true);
    radioGroup.addListener(Events.Change, new Listener<BaseEvent>() {
      @Override
      public void handleEvent(final BaseEvent be) {
        onChange.onCallback();
      }
    });

    final FieldSet infoFS = new FieldSet();
    infoFS.setHeadingHtml("Info");
    // infoFS.setFrame(false);
    // infoFS.setIcon("k-info-icon");
    infoFS.setCollapsible(false);
    infoFS.setAutoHeight(true);

    final HTML recommendCopyleft = new HTML();
    final HTML whyALicense = new HTML();
    final HTML youCanChangeTheLicenseLater = new HTML();
    recommendCopyleft.setHTML(POINT
        + i18n.t("We recommend [%s] licenses, specially for practical works",
            TextUtils.generateHtmlLink("http://en.wikipedia.org/wiki/Copyleft", i18n.t("copyleft"))));
    whyALicense.setHTML(POINT
        + TextUtils.generateHtmlLink("http://mirrors.creativecommons.org/getcreative/",
            i18n.t("Why do we need a license?")));
    youCanChangeTheLicenseLater.setHTML(POINT + i18n.t("You can change this license later"));

    infoFS.addStyleName("kune-Margin-20-t");
    add(intro);
    add(radioGroup);
    infoFS.add(recommendCopyleft);
    infoFS.add(whyALicense);
    infoFS.add(youCanChangeTheLicenseLater);
    add(infoFS);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.user.client.ui.IsWidget#asWidget()
   */
  @Override
  public Widget asWidget() {
    return this.getFormPanel();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.licensewizard.pages.LicenseWizardFirstFormView#isCopyleft
   * ()
   */
  @Override
  public boolean isCopyleft() {
    return copyleftRadio.getValue();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.licensewizard.pages.LicenseWizardFirstFormView#onChange
   * (cc.kune.common.shared.utils.SimpleCallback)
   */
  @Override
  public void onChange(final SimpleCallback onChange) {
    this.onChange = onChange;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.ui.DefaultForm#reset()
   */
  @Override
  public void reset() {
    super.reset();
    copyleftRadio.setValue(true);
  }

}
