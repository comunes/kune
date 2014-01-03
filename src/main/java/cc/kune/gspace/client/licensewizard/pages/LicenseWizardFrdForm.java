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
import cc.kune.core.client.state.Session;
import cc.kune.core.client.ui.DefaultForm;
import cc.kune.core.shared.dto.LicenseDTO;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class LicenseWizardFrdForm.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class LicenseWizardFrdForm extends DefaultForm implements LicenseWizardFrdFormView {

  /**
   * The Class LicenseData.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  @SuppressWarnings("serial")
  public class LicenseData extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /** The Constant LONGNAME. */
    private static final String LONGNAME = "longname";

    /** The Constant SHORTNAME. */
    private static final String SHORTNAME = "shortname";

    /** The Constant URL. */
    private static final String URL = "url";

    /**
     * Instantiates a new license data.
     * 
     * @param license
     *          the license
     */
    public LicenseData(final LicenseDTO license) {
      this(license.getShortName(), license.getLongName(), license.getUrl());
    }

    /**
     * Instantiates a new license data.
     * 
     * @param shortname
     *          the shortname
     * @param longname
     *          the longname
     * @param url
     *          the url
     */
    public LicenseData(final String shortname, final String longname, final String url) {
      set(SHORTNAME, shortname);
      set(LONGNAME, longname);
      set(URL, url);
    }

    /**
     * Gets the longname.
     * 
     * @return the longname
     */
    public String getLongname() {
      return get(LONGNAME);
    }

    /**
     * Gets the shortname.
     * 
     * @return the shortname
     */
    public String getShortname() {
      return get(SHORTNAME);
    }

    /**
     * Gets the url.
     * 
     * @return the url
     */
    public String getUrl() {
      return get(URL);
    }
  }

  /** The Constant COMMON_LICENSES_ID. */
  public static final String COMMON_LICENSES_ID = "k-lwsf-common";

  /** The Constant OTHER_LICENSES_ID. */
  public static final String OTHER_LICENSES_ID = "k-lwsf-other";

  /** The Constant RADIO_FIELD_NAME. */
  public static final String RADIO_FIELD_NAME = "k-lwsf-radio";

  /** The cb. */
  private final ComboBox<LicenseData> cb;

  /** The on change. */
  private SimpleCallback onChange;

  /** The session. */
  private final Session session;

  /**
   * Instantiates a new license wizard frd form.
   * 
   * @param i18n
   *          the i18n
   * @param session
   *          the session
   */
  @Inject
  public LicenseWizardFrdForm(final I18nTranslationService i18n, final Session session) {
    this.session = session;

    setFrame(true);
    super.setPadding(10);

    super.setAutoHeight(true);
    final Label intro = new Label();
    intro.setText(i18n.t("Select other kind of licenses:"));
    intro.addStyleName("kune-Margin-10-b");

    super.setHideLabels(true);

    cb = new ComboBox<LicenseData>();
    cb.setLabelSeparator("");
    cb.setStore(createStore());
    cb.setDisplayField(LicenseData.LONGNAME);
    cb.setValueField(LicenseData.SHORTNAME);
    cb.setTriggerAction(TriggerAction.ALL);
    cb.setEmptyText(i18n.t("Select license"));
    cb.setWidth(300);
    cb.setTitle("Licenses");
    cb.addListener(Events.Select, new Listener<BaseEvent>() {
      @Override
      public void handleEvent(final BaseEvent be) {
        onChange.onCallback();
      }
    });
    add(intro);
    add(cb);
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

  /**
   * Creates the store.
   * 
   * @return the list store
   */
  private ListStore<LicenseData> createStore() {
    final ListStore<LicenseData> list = new ListStore<LicenseData>();

    for (final LicenseDTO license : session.getLicenses()) {
      if (!license.isCC()) {
        list.add(new LicenseData(license));
      }
    }
    return list;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.licensewizard.pages.LicenseWizardFrdFormView#
   * getSelectedLicense()
   */
  @Override
  public String getSelectedLicense() {
    return cb.getValue().getShortname();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.licensewizard.pages.LicenseWizardFrdFormView#onChange
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
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.licensewizard.pages.LicenseWizardFrdFormView#setFlags
   * (boolean, boolean, boolean)
   */
  @Override
  public void setFlags(final boolean isCopyleft, final boolean isAppropiateForCulturalWorks,
      final boolean isNonComercial) {

  }
}
