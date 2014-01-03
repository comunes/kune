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

package cc.kune.core.client.embed;

import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.log.Log;
import cc.kune.common.client.ui.KuneWindowUtils;
import cc.kune.common.client.utils.WindowUtils;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.sitebar.AbstractSignInAction;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.TokenUtils;
import cc.kune.gspace.client.viewers.EmbedHelper;

import com.google.inject.Inject;

/**
 * The Class EmbedSignInAction.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class EmbedSignInAction extends AbstractSignInAction {

  /**
   * Instantiates a new sitebar sign in action.
   * 
   * @param stateManager
   *          the state manager
   * @param i18n
   *          the i18n
   * @param session
   *          the session
   */
  @Inject
  public EmbedSignInAction() {
    super();
    final String signInText = EmbedConfiguration.get().getSignInText();
    putValue(Action.NAME, I18n.t(signInText == null ? signInText : "Participate"));
    putValue(Action.TOOLTIP,
        I18n.t("Please sign in [%s] to participate in this document", I18n.getSiteCommonName()));
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.common
   * .client.actions.ActionEvent)
   */
  @Override
  public void actionPerformed(final ActionEvent event) {
    // If we want to go to the document it self: (String) event.getTarget()
    final String redirect = EmbedHelper.getServer() + "#!"
        + TokenUtils.addRedirect(SiteTokens.SIGN_IN, WindowUtils.getHref());
    Log.info("Redirecting to: " + redirect);
    KuneWindowUtils.open(redirect);
  }

}