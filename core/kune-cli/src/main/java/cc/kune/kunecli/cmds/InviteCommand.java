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

package cc.kune.kunecli.cmds;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.naturalcli.Command;
import org.naturalcli.ExecutionException;
import org.naturalcli.ICommandExecutor;
import org.naturalcli.InvalidSyntaxException;
import org.naturalcli.ParseResult;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import cc.kune.core.client.rpcservices.InvitationServiceAsync;

@Singleton
public class InviteCommand extends Command {
  public static class InviteICommand implements ICommandExecutor {

    @Inject
    AuthHelper helper;

    @Inject
    InvitationServiceAsync service;

    @Override
    public void execute(final ParseResult result) throws ExecutionException {
      final String user = result.getParameterValue(0).toString();
      final String pass = result.getParameterValue(1).toString();
      helper.loginAndContinue(user, pass, new AsyncCallback<String>() {
        @Override
        public void onFailure(final Throwable caught) {
          LOG.error("Auth error", caught);
        }

        @Override
        public void onSuccess(final String hash) {
          System.out.println("Auth cookie: " + hash);
          final int emailCount = result.getParameterCount() - 2;
          final String[] emails = new String[emailCount];
          for (int i = 0; i < emailCount; i++) {
            emails[i] = result.getParameterValue(2 + i).toString();
          }
          // StateToken is not used in this kind of invitations
          service.inviteToSite(hash, null, emails, new AsyncCallback<Void>() {

            @Override
            public void onFailure(final Throwable caught) {
              LOG.error("Failed to send invitations to " + Arrays.toString(emails));
            }

            @Override
            public void onSuccess(final Void result) {
              LOG.info("Invitations sended");
            }
          });
        }
      });
    }
  }

  public static final Log LOG = LogFactory.getLog(InviteCommand.class);

  @Inject
  public InviteCommand(final InviteICommand cmd) throws InvalidSyntaxException {
    super("invite <youruser:string> <yourpass:string> <someemail@example.com:string> ...",
        "invite some emails to use this kune site", cmd);
  }
}
