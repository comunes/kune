/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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

package cc.kune.wave.client;

import static com.google.gwt.query.client.GQuery.$;

import org.waveprotocol.box.webclient.search.SearchPanelRenderer;
import org.waveprotocol.box.webclient.search.SearchPanelWidget;
import org.waveprotocol.wave.client.events.ClientEvents;
import org.waveprotocol.wave.client.events.WaveCreationEvent;

import br.com.rpa.client._paperelements.PaperFab;
import cc.kune.common.client.events.EventBusInstance;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.dnd.KuneDragController;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.client.sitebar.spaces.Space;
import cc.kune.core.client.sitebar.spaces.SpaceSelectEvent;
import cc.kune.gspace.client.armor.GSpaceArmor;
import cc.kune.polymer.client.PolymerId;
import cc.kune.polymer.client.PolymerUtils;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CustomSearchPanelWidget extends SearchPanelWidget {

  @Inject
  public CustomSearchPanelWidget(final KuneWaveProfileManager profiles,
      final KuneDragController dragController, final ClientFileDownloadUtils downUtils,
      final CoreResources res, final GSpaceArmor armor) {
    super(new SearchPanelRenderer(profiles), dragController, downUtils, res, armor);
    armor.wrapDiv(PolymerId.INBOX_RESULT).add(this);
    final PaperFab newMsg = PaperFab.wrap(PolymerId.INBOX_NEW_MESSAGE.getId());
    Tooltip.to(newMsg, I18n.t("New message"));
    newMsg.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(final ClickEvent event) {
        // FIXME Dirty workaround while we improve the Wave integration
        SpaceSelectEvent.fire(EventBusInstance.get(), Space.userSpace);
        // Show the message panel
        PolymerUtils.setMainSelected();
        ClientEvents.get().fireEvent(new WaveCreationEvent());
        // $(".org-waveprotocol-wave-client-widget-toolbar-buttons-HorizontalToolbarButtonWidget-Css-overlay").click();
      }
    });
  }

  @Override
  protected void onAttach() {
    super.onAttach();
    // FIXME Dirty workaround while we improve the Wave integration
    $(".org-waveprotocol-box-webclient-search-SearchPanelWidget-Css-search").hide();
    $(".org-waveprotocol-box-webclient-search-SearchPanelWidget-Css-toolbar").hide();
    $(".org-waveprotocol-box-webclient-search-SearchPanelWidget-Css-list").css("top", "0px");
  }

}
