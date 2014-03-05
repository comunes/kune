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
import cc.kune.common.client.actions.ActionStyles;
import cc.kune.common.client.actions.ui.ActionSimplePanel;
import cc.kune.common.client.actions.ui.descrip.LabelDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.log.Log;
import cc.kune.common.client.resources.CommonResources;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.common.shared.utils.SimpleCallback;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.rpcservices.ContentServiceHelper;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.gspace.client.share.ShareToListOnItemRemoved;
import cc.kune.wave.client.KuneWaveProfileManager;

import com.google.inject.Inject;

public class ShareItemOfParticipant extends AbstractShareItemUi {

  private final CommonResources commonResources;
  private final ContentServiceHelper contentService;
  private final KuneWaveProfileManager profileManager;
  private final IconicResources res;

  @Inject
  public ShareItemOfParticipant(final ActionSimplePanel actionsPanel,
      final KuneWaveProfileManager profileManager, final ClientFileDownloadUtils downloadUtils,
      final IconicResources res, final CommonResources commonResources,
      final ContentServiceHelper contentService) {
    super(actionsPanel, downloadUtils);
    this.profileManager = profileManager;
    this.res = res;
    this.commonResources = commonResources;
    this.contentService = contentService;
  }

  public AbstractShareItemUi of(final String participant, final String typeId,
      final ShareToListOnItemRemoved onItemRemoved, final boolean creator) {
    try {
      final ProfileImpl profile = profileManager.getProfile(ParticipantId.of(participant));
      final String address = profile.getAddress();
      // remove @localhost if the participant is local
      withText(profileManager.isLocal(address) ? profileManager.getUsername(address) : address);
      withIcon(profile.getImageUrl());
      if (!creator) {
        final MenuDescriptor menu = new MenuDescriptor(I18n.tWithNT("is editor", "someone is editor"));
        menu.withIcon(commonResources.arrowdownsitebarSmall()).withStyles(
            ActionStyles.MENU_BTN_STYLE_NO_BORDER_RIGHT + ", k-share-item-actions");
        super.add(menu);
        final MenuItemDescriptor remove = new MenuItemDescriptor(menu, true,
            new AbstractExtendedAction() {
              @Override
              public void actionPerformed(final ActionEvent event) {
                contentService.delParticipants(new SimpleCallback() {
                  @Override
                  public void onCallback() {
                    onItemRemoved.onRemove(ShareItemOfParticipant.this);
                  }
                }, participant);
              }
            });
        remove.withText(I18n.t("Remove")).withIcon(res.del());
        super.add(remove);
      } else {
        // Wave creator
        final LabelDescriptor isCreator = new LabelDescriptor(I18n.t("can edit"));
        isCreator.withStyles("k-share-item-noactions");
        super.add(isCreator);
      }
    } catch (final InvalidParticipantAddress e) {
      Log.debug("Cannot add this participant", e);
    }
    return this;
  }
}
