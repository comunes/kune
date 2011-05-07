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
package cc.kune.gspace.client.licensewizard.pages;

import cc.kune.common.client.utils.SimpleCallback;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.ui.DefaultForm;
import cc.kune.core.shared.dto.LicenseDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

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

public class LicenseWizardFrdForm extends DefaultForm implements LicenseWizardFrdFormView {

  public class LicenseData extends BaseModel {

    private static final String LONGNAME = "longname";
    private static final String SHORTNAME = "shortname";
    private static final String URL = "url";

    public LicenseData(final LicenseDTO license) {
      this(license.getShortName(), license.getLongName(), license.getUrl());
    }

    public LicenseData(final String shortname, final String longname, final String url) {
      set(SHORTNAME, shortname);
      set(LONGNAME, longname);
      set(URL, url);
    }

    public String getLongname() {
      return get(LONGNAME);
    }

    public String getShortname() {
      return get(SHORTNAME);
    }

    public String getUrl() {
      return get(URL);
    }
  }

  public static final String COMMON_LICENSES_ID = "k-lwsf-common";
  public static final String OTHER_LICENSES_ID = "k-lwsf-other";
  public static final String RADIO_FIELD_NAME = "k-lwsf-radio";
  private final ComboBox<LicenseData> cb;

  private SimpleCallback onChange;
  private final Session session;

  @Inject
  public LicenseWizardFrdForm(final I18nTranslationService i18n, final Session session) {
    this.session = session;

    // this.onChange = new Event0("onChange");
    setFrame(true);
    super.setPadding(10);
    // super.setHeight(LicenseWizardView.HEIGHT);
    super.setAutoHeight(true);
    final Label intro = new Label();
    intro.setText(i18n.t("Select other kind of licenses:"));

    // final ListStore<LicenseDTO> store = new ListStore<LicenseDTO>();
    // store.add( Arrays.asList( new String[] { "shortname", "longname", "url"
    // });
    // getNonCCLicenses());
    // store.load();

    // final Template template = new
    // Template("<div class=\"x-combo-list-item\">" + "<img src=\"{url}\"> "
    // + "{longname}<div class=\"x-clear\"></div></div>");

    super.setHideLabels(true);

    cb = new ComboBox<LicenseData>();
    cb.setMinChars(1);
    // cb.setFieldLabel("Other licenses");
    cb.setLabelSeparator("");
    cb.setStore(createStore());
    cb.setDisplayField(LicenseData.LONGNAME);
    cb.setValueField(LicenseData.SHORTNAME);
    // cb.setMode(Mode.LOCAL);
    cb.setTriggerAction(TriggerAction.ALL);
    cb.setEmptyText(i18n.t("Select license"));
    cb.setTypeAhead(true);
    cb.setSelectOnFocus(true);
    cb.setEditable(false);
    cb.setWidth(300);
    // cb.setResizable(true);
    // cb.setTpl(template);
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

  @Override
  public Widget asWidget() {
    return this.getFormPanel();
  }

  private ListStore<LicenseData> createStore() {
    final ListStore<LicenseData> list = new ListStore<LicenseData>();

    for (final LicenseDTO license : session.getLicenses()) {
      if (!license.isCC()) {
        list.add(new LicenseData(license));
      }
    }
    return list;
  }

  @Override
  public String getSelectedLicense() {
    return cb.getValue().getShortname();
  }

  @Override
  public void onChange(final SimpleCallback onChange) {
    this.onChange = onChange;
  }

  @Override
  public void reset() {
    super.reset();
  }

  @Override
  public void setFlags(final boolean isCopyleft, final boolean isAppropiateForCulturalWorks,
      final boolean isNonComercial) {

  }
}
