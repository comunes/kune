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
package cc.kune.chat.client.actions;

import java.util.ArrayList;
import java.util.List;

import cc.kune.chat.client.ChatClient;
import cc.kune.chat.client.ChatInstances;
import cc.kune.chat.client.resources.ChatResources;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.actions.RolActionAutoUpdated;
import cc.kune.core.client.events.StateChangedEvent;
import cc.kune.core.client.events.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.SocialNetworkDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;

import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;
import com.calclab.emite.im.client.chat.ChatStates;
import com.calclab.emite.xep.muc.client.Occupant;
import com.calclab.emite.xep.muc.client.Room;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.inject.Inject;

public class OpenGroupPublicChatRoomAction extends RolActionAutoUpdated {

  private final ChatClient chatClient;
  private HandlerRegistration hadlerReg;
  protected final I18nTranslationService i18n;
  private boolean inviteMembers;
  protected final Session session;

  @SuppressWarnings("deprecation")
  @Inject
  public OpenGroupPublicChatRoomAction(final Session session,
      final AccessRightsClientManager accessRightsClientManager, final ChatClient chatClient,
      final StateManager stateManager, final I18nTranslationService i18n, final ChatResources res,
      final ChatInstances chatInstances) {
    super(stateManager, session, accessRightsClientManager, AccessRolDTO.Editor, true, false, true);
    this.session = session;
    this.chatClient = chatClient;
    this.i18n = i18n;
    stateManager.onStateChanged(true, new StateChangedHandler() {
      @Override
      public void onStateChanged(final StateChangedEvent event) {
        // setState(session.getCurrentState());
      }
    });
    putValue(Action.NAME, i18n.t("Group's public chat room"));
    putValue(Action.TOOLTIP, i18n.t("Enter to this group public chat room"));
    putValue(Action.SMALL_ICON, res.groupChat());
    setInviteMembersImpl(false);
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    final String currentGroupName = session.getCurrentGroupShortName();
    final Room room = chatClient.joinRoom(currentGroupName, session.getCurrentUser().getShortName());
    inviteMembers(room);
    chatClient.show();
  }

  private void addGroup(final List<XmppURI> membersUris, final GroupDTO member) {
    membersUris.add(chatClient.uriFrom(member.getShortName()));
  }

  private boolean currentGroupsIsAsPerson(final StateAbstractDTO state) {
    return state.getGroup().isPersonal();
  }

  private void inviteMembers(final Room room) {
    if (inviteMembers) {
      hadlerReg = room.addChatStateChangedHandler(true,
          new com.calclab.emite.core.client.events.StateChangedHandler() {
            @Override
            public void onStateChanged(final com.calclab.emite.core.client.events.StateChangedEvent event) {
              if (event.getState().equals(ChatStates.ready)) {
                // When ready we invite to the rest of members
                final SocialNetworkDTO groupMembers = session.getCurrentState().getGroupMembers();
                final List<XmppURI> membersUris = new ArrayList<XmppURI>();
                for (final GroupDTO member : groupMembers.getAccessLists().getAdmins().getList()) {
                  addGroup(membersUris, member);
                }
                for (final GroupDTO member : groupMembers.getAccessLists().getEditors().getList()) {
                  addGroup(membersUris, member);
                }
                for (final Occupant occupant : room.getOccupants()) {
                  // Remove all member that are in the room
                  membersUris.remove(occupant.getJID());
                }
                for (final XmppURI memberNotPresent : membersUris) {
                  room.sendInvitationTo(memberNotPresent,
                      i18n.t("Join us in [%s] public room!", room.getURI().getNode()));
                }
                hadlerReg.removeHandler();
              }
            }
          });
    }
  }

  public void setInviteMembers(final boolean inviteMembers) {
    setInviteMembersImpl(inviteMembers);
  }

  private void setInviteMembersImpl(final boolean inviteMembers) {
    this.inviteMembers = inviteMembers;
  }

  private void setState(final StateAbstractDTO state) {
    final boolean imLogged = session.isLogged();
    if (imLogged && !currentGroupsIsAsPerson(state)) {
      setEnabled(false);
      setEnabled(true);
    } else {
      setEnabled(true);
      setEnabled(false);
    }
  }
}