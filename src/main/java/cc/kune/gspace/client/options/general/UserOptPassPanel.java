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
import cc.kune.core.client.auth.UserFieldFactory;
import cc.kune.core.client.resources.iconic.IconicResources;

import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.inject.Inject;

public class UserOptPassPanel extends EntityOptGeneralPanel implements UserOptPassView {

  public static final String CURRENT_PASSWD_FIELD = "k-uopp-currentPass";
  public static final String NEW_PASSWD_FIELD = "k-uopp-newPass";
  public static final String NEW_PASSWD_REPEATED_FIELD = "k-uopp-newPassRepeated";
  private final Button changeBtn;
  private final TextField<String> currentPasswd;
  private final TextField<String> newPasswd;
  private final TextField<String> newPasswdRepeated;

  @Inject
  public UserOptPassPanel(final I18nTranslationService i18n, final IconicResources res,
      final MaskWidget maskWidget, final UserFieldFactory userFieldFactory) {
    super(maskWidget, res.lockWhite(), i18n.t("Security"), i18n.t("Change your password:"));
    currentPasswd = userFieldFactory.createUserPasswd(CURRENT_PASSWD_FIELD, i18n.t("Current password"));
    newPasswd = userFieldFactory.createUserPasswd(NEW_PASSWD_FIELD, i18n.t("New password"));
    newPasswdRepeated = userFieldFactory.createUserPasswd(NEW_PASSWD_REPEATED_FIELD,
        i18n.t("New password (repeat it)"));
    changeBtn = new Button(i18n.t("Change it"));
    add(currentPasswd);
    add(newPasswd);
    add(newPasswdRepeated);
    add(changeBtn);
  }

  @Override
  public HasClickHandlers getChangeBtn() {
    return changeBtn;
  }

  @Override
  public String getCurrentPasswd() {
    return currentPasswd.getValue();
  }

  @Override
  public String getNewPasswd() {
    return newPasswd.getValue();
  }

  @Override
  public String getNewPasswdRepeated() {
    return newPasswdRepeated.getValue();
  }
}
