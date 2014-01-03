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
package cc.kune.chat.client.actions;

import java.util.ArrayList;
import java.util.List;

import cc.kune.chat.client.ChatClient;
import cc.kune.chat.client.resources.ChatResources;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.actions.RolActionAutoUpdated;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.SocialNetworkDTO;

import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;
import com.calclab.emite.im.client.chat.ChatStates;
import com.calclab.emite.xep.muc.client.Occupant;
import com.calclab.emite.xep.muc.client.Room;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class OpenGroupPublicChatRoomAction.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class OpenGroupPublicChatRoomAction extends RolActionAutoUpdated {

  /** The chat client. */
  private final ChatClient chatClient;
  
  /** The hadler reg. */
  private HandlerRegistration hadlerReg;
  
  /** The i18n. */
  protected final I18nTranslationService i18n;
  
  /** The invite members. */
  private boolean inviteMembers;
  
  /** The session. */
  protected final Session session;

  /**
   * Instantiates a new open group public chat room action.
   *
   * @param session the session
   * @param accessRightsClientManager the access rights client manager
   * @param chatClient the chat client
   * @param stateManager the state manager
   * @param i18n the i18n
   * @param res the res
   */
  @Inject
  public OpenGroupPublicChatRoomAction(final Session session,
      final AccessRightsClientManager accessRightsClientManager, final ChatClient chatClient,
      final StateManager stateManager, final I18nTranslationService i18n, final ChatResources res) {
    super(stateManager, session, accessRightsClientManager, AccessRolDTO.Editor, true, false, true);
    this.session = session;
    this.chatClient = chatClient;
    this.i18n = i18n;
    putValue(Action.NAME, i18n.t("Group's public chat room"));
    putValue(Action.TOOLTIP, i18n.t("Enter to this group public chat room"));
    putValue(Action.SMALL_ICON, res.groupChat());
    setInviteMembersImpl(false);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.common.client.actions.ActionEvent)
   */
  @Override
  public void actionPerformed(final ActionEvent event) {
    final String currentGroupName = session.getCurrentGroupShortName();
    final Room room = chatClient.joinRoom(currentGroupName, session.getCurrentUser().getShortName());
    inviteMembers(room);
    chatClient.show();
  }

  /**
   * Adds the group.
   *
   * @param membersUris the members uris
   * @param member the member
   */
  private void addGroup(final List<XmppURI> membersUris, final GroupDTO member) {
    membersUris.add(chatClient.uriFrom(member.getShortName()));
  }

  /**
   * Invite members.
   *
   * @param room the room
   */
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

  /**
   * Sets the invite members.
   *
   * @param inviteMembers the new invite members
   */
  public void setInviteMembers(final boolean inviteMembers) {
    setInviteMembersImpl(inviteMembers);
  }

  /**
   * Sets the invite members impl.
   *
   * @param inviteMembers the new invite members impl
   */
  private void setInviteMembersImpl(final boolean inviteMembers) {
    this.inviteMembers = inviteMembers;
  }
}
