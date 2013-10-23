/*******************************************************************************
 * Copyright (C) 2007, 2013 The kune development team (see CREDITS for details)
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
 *******************************************************************************/

package cc.kune.gspace.client.share.items;

import org.waveprotocol.wave.client.account.impl.ProfileImpl;
import org.waveprotocol.wave.model.wave.InvalidParticipantAddress;
import org.waveprotocol.wave.model.wave.ParticipantId;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.ActionSimplePanel;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.log.Log;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.resources.CommonResources;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.wave.client.KuneWaveProfileManager;

import com.google.inject.Inject;

public class ShareItemOfParticipant extends AbstractShareItemWithMenu {

  private final KuneWaveProfileManager profileManager;
  private final IconicResources res;

  @Inject
  public ShareItemOfParticipant(final ActionSimplePanel actionsPanel,
      final KuneWaveProfileManager profileManager, final ClientFileDownloadUtils downloadUtils,
      final ContentServiceAsync contentServiceAsync, final IconicResources res,
      final CommonResources commonResources) {
    super(I18n.tWithNT("is editor", "someone is editor"), actionsPanel, downloadUtils,
        contentServiceAsync, commonResources);
    this.profileManager = profileManager;
    this.res = res;
  }

  public AbstractShareItem of(final String participant) {
    try {
      final ProfileImpl profile = profileManager.getProfile(ParticipantId.of(participant));
      withText(profile.getFirstName());
      withIcon(profile.getImageUrl());
      final MenuItemDescriptor remove = new MenuItemDescriptor(menu, true, new AbstractExtendedAction() {
        @Override
        public void actionPerformed(final ActionEvent event) {
          // TODO
          NotifyUser.info("In development");
        }
      });
      remove.withText(I18n.t("Remove")).withIcon(res.del());
      super.add(remove);
    } catch (final InvalidParticipantAddress e) {
      Log.debug("Cannot add this participant", e);
    }
    return this;
  }

}
