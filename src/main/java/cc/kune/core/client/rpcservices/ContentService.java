/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
package cc.kune.core.client.rpcservices;

import java.util.Date;

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.domain.RateResult;
import cc.kune.core.shared.domain.TagCloudResult;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.ContentSimpleDTO;
import cc.kune.core.shared.dto.I18nLanguageDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("ContentService")
public interface ContentService extends RemoteService {

  void addAuthor(String userHash, StateToken token, String authorShortName) throws DefaultException;

  StateContentDTO addContent(String user, StateToken parentToken, String name, String typeId)
      throws DefaultException;

  StateContainerDTO addFolder(String hash, StateToken parentToken, String typeId, String title)
      throws DefaultException;

  StateContentDTO addGadget(String userHash, StateToken currentStateToken, String gadgetName,
      String typeId, String title, String body);

  void addParticipant(String userHash, StateToken token, String participant) throws DefaultException;

  StateContainerDTO addRoom(String user, StateToken parentToken, String name) throws DefaultException;

  StateContentDTO delContent(String userHash, StateToken token) throws DefaultException;

  StateAbstractDTO getContent(String userHash, StateToken token) throws DefaultException;

  TagCloudResult getSummaryTags(String userHash, StateToken groupToken) throws DefaultException;

  RateResult rateContent(String userHash, StateToken token, Double value) throws DefaultException;

  void removeAuthor(String userHash, StateToken token, String authorShortName) throws DefaultException;

  StateAbstractDTO renameContainer(String userHash, StateToken token, String newName)
      throws DefaultException;

  StateAbstractDTO renameContent(String userHash, StateToken token, String newName)
      throws DefaultException;

  void save(String user, StateToken token, String content) throws DefaultException;

  ContentSimpleDTO setAsDefaultContent(String userHash, StateToken token);

  I18nLanguageDTO setLanguage(String userHash, StateToken token, String languageCode)
      throws DefaultException;

  void setPublishedOn(String userHash, StateToken token, Date date) throws DefaultException;

  StateAbstractDTO setStatus(String userHash, StateToken stateToken, ContentStatus status);

  StateAbstractDTO setStatusAsAdmin(String userHash, StateToken stateToken, ContentStatus status);

  TagCloudResult setTags(String userHash, StateToken token, String tags) throws DefaultException;
}
