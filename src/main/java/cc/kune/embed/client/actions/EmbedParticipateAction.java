/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
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

package cc.kune.embed.client.actions;

import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.ui.KuneWindowUtils;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.common.shared.res.KuneIcon;
import cc.kune.core.client.sitebar.AbstractSignInAction;
import cc.kune.embed.client.EmbedHelper;

import com.google.inject.Inject;

/**
 * The Class EmbedParticipateAction.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class EmbedParticipateAction extends AbstractSignInAction {

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
  public EmbedParticipateAction() {
    super();
    putValue(Action.NAME, I18n.t("Participate"));
    withIcon(KuneIcon.KUNE);
    putValue(Action.TOOLTIP, I18n.t("Go to [%s] to participate in this group", I18n.getSiteCommonName()));
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
    final String redirect = EmbedHelper.getServer() + "#!" + event.getTarget();
    KuneWindowUtils.open(redirect);
  }

}