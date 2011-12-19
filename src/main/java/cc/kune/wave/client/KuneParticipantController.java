/**
 * Copyright 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package cc.kune.wave.client;

import org.waveprotocol.wave.client.account.Profile;
import org.waveprotocol.wave.client.account.ProfileManager;
import org.waveprotocol.wave.client.common.safehtml.EscapeUtils;
import org.waveprotocol.wave.client.wavepanel.WavePanel;
import org.waveprotocol.wave.client.wavepanel.event.EventHandlerRegistry;
import org.waveprotocol.wave.client.wavepanel.event.WaveClickHandler;
import org.waveprotocol.wave.client.wavepanel.view.ParticipantView;
import org.waveprotocol.wave.client.wavepanel.view.ParticipantsView;
import org.waveprotocol.wave.client.wavepanel.view.View.Type;
import org.waveprotocol.wave.client.wavepanel.view.dom.DomAsViewProvider;
import org.waveprotocol.wave.client.wavepanel.view.dom.ModelAsViewProvider;
import org.waveprotocol.wave.client.wavepanel.view.dom.full.TypeCodes;
import org.waveprotocol.wave.client.widget.profile.ProfilePopupPresenter;
import org.waveprotocol.wave.client.widget.profile.ProfilePopupView;
import org.waveprotocol.wave.model.conversation.Conversation;
import org.waveprotocol.wave.model.util.Pair;
import org.waveprotocol.wave.model.wave.InvalidParticipantAddress;
import org.waveprotocol.wave.model.wave.ParticipantId;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;

/**
 * Installs the add/remove participant controls.
 * 
 */
public final class KuneParticipantController {
  private static I18nTranslationService i18n;

  /**
   * Builds and installs the participant control feature.
   */
  public static void install(final WavePanel panel, final ModelAsViewProvider models,
      final ProfileManager profiles, final String localDomain, final I18nTranslationService i18n) {
    KuneParticipantController.i18n = i18n;
    final KuneParticipantController controller = new KuneParticipantController(panel.getViewProvider(),
        models, profiles, localDomain);
    controller.install(panel.getHandlers());
  }
  private final String localDomain;
  private final ModelAsViewProvider models;
  private final ProfileManager profiles;

  private final DomAsViewProvider views;

  /**
   * @param localDomain
   *          nullable. if provided, automatic suffixing will occur.
   */
  KuneParticipantController(final DomAsViewProvider views, final ModelAsViewProvider models,
      final ProfileManager profiles, final String localDomain) {
    this.views = views;
    this.models = models;
    this.profiles = profiles;
    this.localDomain = localDomain;
  }

  /**
   * Shows an add-participant popup.
   */
  private void handleAddButtonClicked(final Element context) {

    ParticipantId p;
    String address = Window.prompt("Add a participant: ", "");
    if (address == null) {
      return;
    }
    address = address.trim();
    if (localDomain != null) {
      if (!address.isEmpty() && address.indexOf("@") == -1) {
        // If no domain was specified, assume that the participant is from the
        // local domain.
        address = address + "@" + localDomain;
      } else if (address.equals("@")) {
        // "@" is a shortcut for the shared domain participant.
        address = address + localDomain;
      }
    }

    try {
      p = ParticipantId.of(address);
    } catch (final InvalidParticipantAddress e) {
      NotifyUser.error(i18n.t("Invalid address: [%s]", address));
      return;
    }

    final ParticipantsView participantsUi = views.fromAddButton(context);
    final Conversation conversation = models.getParticipants(participantsUi);
    conversation.addParticipant(p);
  }

  /**
   * Shows a participation popup for the clicked participant.
   */
  private void handleParticipantClicked(final Element context) {
    final ParticipantView participantView = views.asParticipant(context);
    final Pair<Conversation, ParticipantId> participation = models.getParticipant(participantView);
    final Profile profile = profiles.getProfile(participation.second);

    // Summon a popup view from a participant, and attach profile-popup logic to
    // it.
    final ProfilePopupView profileView = participantView.showParticipation();
    final ProfilePopupPresenter profileUi = ProfilePopupPresenter.create(profile, profileView, profiles);
    if (!participation.first.getParticipantIds().iterator().next().equals(participation.getSecond())) {
      profileUi.addControl(EscapeUtils.fromSafeConstant("Remove"), new ClickHandler() {
        @Override
        public void onClick(final ClickEvent event) {
          participation.first.removeParticipant(participation.second);
          // The presenter is configured to destroy itself on view hide.
          profileView.hide();
        }
      });
    }
    profileUi.show();
  }

  private void install(final EventHandlerRegistry handlers) {
    handlers.registerClickHandler(TypeCodes.kind(Type.ADD_PARTICIPANT), new WaveClickHandler() {
      @Override
      public boolean onClick(final ClickEvent event, final Element context) {
        handleAddButtonClicked(context);
        return true;
      }
    });
    handlers.registerClickHandler(TypeCodes.kind(Type.PARTICIPANT), new WaveClickHandler() {
      @Override
      public boolean onClick(final ClickEvent event, final Element context) {
        handleParticipantClicked(context);
        return true;
      }
    });
  }
}
