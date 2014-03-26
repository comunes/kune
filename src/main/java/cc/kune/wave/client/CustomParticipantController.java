// @formatter:off
/**
 * Copyright 2010, 2012 Google Inc.
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

import java.util.Set;

import javax.annotation.Nullable;

import org.waveprotocol.wave.client.account.Profile;
import org.waveprotocol.wave.client.account.ProfileManager;
import org.waveprotocol.wave.client.common.safehtml.EscapeUtils;
import org.waveprotocol.wave.client.common.util.WindowPromptCallback;
import org.waveprotocol.wave.client.common.util.WindowUtil;
import org.waveprotocol.wave.client.events.ClientEvents;
import org.waveprotocol.wave.client.events.WaveCreationEvent;
import org.waveprotocol.wave.client.wavepanel.WavePanel;
import org.waveprotocol.wave.client.wavepanel.event.EventHandlerRegistry;
import org.waveprotocol.wave.client.wavepanel.event.WaveClickHandler;
import org.waveprotocol.wave.client.wavepanel.impl.edit.ParticipantSelectorWidget;
import org.waveprotocol.wave.client.wavepanel.impl.edit.i18n.ParticipantMessages;
import org.waveprotocol.wave.client.wavepanel.view.ParticipantView;
import org.waveprotocol.wave.client.wavepanel.view.ParticipantsView;
import org.waveprotocol.wave.client.wavepanel.view.View.Type;
import org.waveprotocol.wave.client.wavepanel.view.dom.DomAsViewProvider;
import org.waveprotocol.wave.client.wavepanel.view.dom.ModelAsViewProvider;
import org.waveprotocol.wave.client.wavepanel.view.dom.full.TypeCodes;
import org.waveprotocol.wave.client.widget.popup.UniversalPopup;
import org.waveprotocol.wave.client.widget.profile.ProfilePopupPresenter;
import org.waveprotocol.wave.client.widget.profile.ProfilePopupView;
import org.waveprotocol.wave.model.conversation.Conversation;
import org.waveprotocol.wave.model.util.Pair;
import org.waveprotocol.wave.model.util.Preconditions;
import org.waveprotocol.wave.model.wave.InvalidParticipantAddress;
import org.waveprotocol.wave.model.wave.ParticipantId;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.sitebar.spaces.Space;
import cc.kune.core.client.sitebar.spaces.SpaceSelectEvent;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;

// TODO: Auto-generated Javadoc
/**
 * Installs the add/remove participant controls.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public final class CustomParticipantController {

  /** The views. */
  private final DomAsViewProvider views;

  /** The models. */
  private final ModelAsViewProvider models;

  /** The profiles. */
  private final ProfileManager profiles;

  /** The local domain. */
  private final String localDomain;

  /** The popup. */
  private UniversalPopup popup = null;

  /** The user. */
  private final ParticipantId user;

  /** The event bus. */
  private static EventBus eventBus;

  /** The messages. */
  private static ParticipantMessages messages;

  /**
   * Instantiates a new custom participant controller.
   *
   * @param views the views
   * @param models the models
   * @param profiles the profiles
   * @param localDomain nullable. if provided, automatic suffixing will occur.
   * @param user the user
   */
  CustomParticipantController(
      DomAsViewProvider views, ModelAsViewProvider models,
      ProfileManager profiles,
      String localDomain, ParticipantId user) {
    this.views = views;
    this.models = models;
    this.profiles = profiles;
    this.localDomain = localDomain;
    this.user = user;
  }

  /**
   * Builds and installs the participant control feature.
   *
   * @param panel the panel
   * @param models the models
   * @param profiles the profiles
   * @param localDomain the local domain
   * @param user the user
   * @param messages the messages
   * @param eventBus the event bus
   */
  public static void install(WavePanel panel, ModelAsViewProvider models,
      ProfileManager profiles, String localDomain, ParticipantId user, ParticipantMessages messages, EventBus eventBus) {
    CustomParticipantController.messages = messages;
    CustomParticipantController.eventBus = eventBus;
    CustomParticipantController controller = new CustomParticipantController(panel.getViewProvider(),
        models, profiles, localDomain, user);
    controller.install(panel.getHandlers());
  }

  /**
   * Install.
   *
   * @param handlers the handlers
   */
  private void install(EventHandlerRegistry handlers) {
    handlers.registerClickHandler(TypeCodes.kind(Type.ADD_PARTICIPANT), new WaveClickHandler() {
      @Override
      public boolean onClick(ClickEvent event, Element context) {
        handleAddButtonClicked(context);
        return true;
      }
    });
    handlers.registerClickHandler(TypeCodes.kind(Type.NEW_WAVE_WITH_PARTICIPANTS),
        new WaveClickHandler() {
          @Override
          public boolean onClick(ClickEvent event, Element context) {
            handleNewWaveWithParticipantsButtonClicked(context);
            return true;
          }
        });
    handlers.registerClickHandler(TypeCodes.kind(Type.PARTICIPANT), new WaveClickHandler() {
      @Override
      public boolean onClick(ClickEvent event, Element context) {
        handleParticipantClicked(context);
        return true;
      }
    });
  }

  /**
   * Constructs a list of {@link ParticipantId} with the supplied string with comma
   * separated participant addresses. The method will only succeed if all addresses
   * is valid.
   *
   * @param localDomain if provided, automatic suffixing will occur.
   * @param addresses string with comma separated participant addresses
   * @return the array of {@link ParticipantId} instances constructed using the given
   *         addresses string
   * @throws InvalidParticipantAddress if at least one of the addresses failed validation.
   */
  public static ParticipantId[] buildParticipantList(
      @Nullable String localDomain, String addresses) throws InvalidParticipantAddress {
    Preconditions.checkNotNull(addresses, "Expected non-null address");

    String[] addressList = addresses.split(",");
    ParticipantId[] participants = new ParticipantId[addressList.length];

    for (int i = 0; i < addressList.length; i++) {
      String address = addressList[i].trim();

      if (localDomain != null) {
        if (!address.isEmpty() && address.indexOf("@") == -1) {
          // If no domain was specified, assume that the participant is from the local domain.
          address = address + "@" + localDomain;
        } else if (address.equals("@")) {
          // "@" is a shortcut for the shared domain participant.
          address = address + localDomain;
        }
      }

      // Will throw InvalidParticipantAddress if address is not valid
      participants[i] = ParticipantId.of(address);
    }
    return participants;
  }

  /**
   * Creates a new wave with the participants of the current wave. Showing
   * a popup dialog where the user can chose to deselect users that should not
   * be participants in the new wave
   *
   * @param context the context
   */
  private void handleNewWaveWithParticipantsButtonClicked(Element context) {
    ParticipantsView participantsUi = views.fromNewWaveWithParticipantsButton(context);
    ParticipantSelectorWidget selector = new ParticipantSelectorWidget();
    popup = null;
    selector.setListener(new ParticipantSelectorWidget.Listener() {
      @Override
      public void onSelect(Set<ParticipantId> participants) {
        if (popup != null) {
          popup.hide();
        }
        SpaceSelectEvent.fire(eventBus, Space.userSpace);
        ClientEvents.get().fireEvent(
            new WaveCreationEvent(participants));
      }

      @Override
      public void onCancel() {
        popup.hide();
      }
    });
    popup = selector.showInPopup(user,
        models.getParticipants(participantsUi).getParticipantIds(), profiles);
  }

  /**
   * Shows an add-participant popup.
   *
   * @param context the context
   */
  private void handleAddButtonClicked(final Element context) {
    WindowUtil.prompt("Add a participant(s) (separate with comma ','): ", "", new WindowPromptCallback() {
      @Override
      public void onReturn(String addressString) {
        if (addressString == null) {
          return;
        }

        ParticipantId[] participants;

        try {
          participants = buildParticipantList(localDomain, addressString);
        } catch (InvalidParticipantAddress e) {
          NotifyUser.error(I18n.t("Invalid address: [%s]", addressString));
          return;
        }

        ParticipantsView participantsUi = views.fromAddButton(context);
        Conversation conversation = models.getParticipants(participantsUi);
        for (ParticipantId participant : participants) {
          conversation.addParticipant(participant);
        }
      }
    });
  }

  /**
   * Shows a participation popup for the clicked participant.
   *
   * @param context the context
   */
  private void handleParticipantClicked(Element context) {
    ParticipantView participantView = views.asParticipant(context);
    final Pair<Conversation, ParticipantId> participation = models.getParticipant(participantView);
    Profile profile = profiles.getProfile(participation.second);

    // Summon a popup view from a participant, and attach profile-popup logic to
    // it.
    final ProfilePopupView profileView = participantView.showParticipation();
    ProfilePopupPresenter profileUi = ProfilePopupPresenter.create(profile, profileView, profiles);
    if (!participation.first.getParticipantIds().iterator().next().equals(participation.getSecond())) {
      profileUi.addControl(EscapeUtils.fromSafeConstant(messages.remove()), new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
          participation.first.removeParticipant(participation.second);
          // The presenter is configured to destroy itself on view hide.
          profileView.hide();
        }
      });
    }
    profileUi.show();
  }

}
