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
import cc.kune.core.client.auth.UserFieldFactory;
import cc.kune.core.client.resources.iconic.IconicResources;

import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class UserOptPassPanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class UserOptPassPanel extends EntityOptGeneralPanel implements UserOptPassView {

  /** The Constant CURRENT_PASSWD_FIELD. */
  public static final String CURRENT_PASSWD_FIELD = "k-uopp-currentPass";

  /** The Constant NEW_PASSWD_FIELD. */
  public static final String NEW_PASSWD_FIELD = "k-uopp-newPass";

  /** The Constant NEW_PASSWD_REPEATED_FIELD. */
  public static final String NEW_PASSWD_REPEATED_FIELD = "k-uopp-newPassRepeated";

  /** The change btn. */
  private final Button changeBtn;

  /** The current passwd. */
  private final TextField<String> currentPasswd;

  /** The new passwd. */
  private final TextField<String> newPasswd;

  /** The new passwd repeated. */
  private final TextField<String> newPasswdRepeated;

  /**
   * Instantiates a new user opt pass panel.
   * 
   * @param i18n
   *          the i18n
   * @param res
   *          the res
   * @param maskWidget
   *          the mask widget
   */
  @Inject
  public UserOptPassPanel(final I18nTranslationService i18n, final IconicResources res,
      final MaskWidget maskWidget) {
    super(maskWidget, res.lockWhite(), i18n.t("Security"), i18n.t("Change your password:"));
    currentPasswd = UserFieldFactory.createUserPasswd(CURRENT_PASSWD_FIELD, i18n.t("Current password"));
    newPasswd = UserFieldFactory.createUserPasswd(NEW_PASSWD_FIELD, i18n.t("New password"));
    newPasswdRepeated = UserFieldFactory.createUserPasswd(NEW_PASSWD_REPEATED_FIELD,
        i18n.t("New password (repeat it)"));
    changeBtn = new Button(i18n.t("Change it"));
    add(currentPasswd);
    add(newPasswd);
    add(newPasswdRepeated);
    add(changeBtn);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.options.general.UserOptPassView#getChangeBtn()
   */
  @Override
  public HasClickHandlers getChangeBtn() {
    return changeBtn;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.general.UserOptPassView#getCurrentPasswd()
   */
  @Override
  public String getCurrentPasswd() {
    return currentPasswd.getValue();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.options.general.UserOptPassView#getNewPasswd()
   */
  @Override
  public String getNewPasswd() {
    return newPasswd.getValue();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.general.UserOptPassView#getNewPasswdRepeated
   * ()
   */
  @Override
  public String getNewPasswdRepeated() {
    return newPasswdRepeated.getValue();
  }
}
