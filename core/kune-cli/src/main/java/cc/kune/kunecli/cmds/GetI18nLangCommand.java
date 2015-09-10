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

import org.naturalcli.Command;
import org.naturalcli.ExecutionException;
import org.naturalcli.ICommandExecutor;
import org.naturalcli.InvalidSyntaxException;
import org.naturalcli.ParseResult;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import cc.kune.core.client.rpcservices.I18nServiceAsync;
import cc.kune.core.shared.dto.I18nLanguageDTO;

@Singleton
public class GetI18nLangCommand extends Command {

  public static class GetI18nLangICommand implements ICommandExecutor {

    @Inject
    I18nServiceAsync service;

    @Override
    public void execute(ParseResult result) throws ExecutionException {

      service.getInitialLanguage(null, new AsyncCallback<I18nLanguageDTO>() {
        @Override
        public void onFailure(final Throwable caught) {
          System.out.println("Init Failure: " + caught.getMessage());
        }

        @Override
        public void onSuccess(final I18nLanguageDTO result) {
          System.out.println("Init Success, lang:" + result.getEnglishName());
        }
      });

    }
  }

  @Inject
  public GetI18nLangCommand(GetI18nLangICommand cmd) throws InvalidSyntaxException {
    super("siteGetI18nLang", "gets the initial language", cmd);
  }
}
