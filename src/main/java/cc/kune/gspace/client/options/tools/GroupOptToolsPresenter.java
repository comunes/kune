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
package cc.kune.gspace.client.options.tools;

import java.util.Collection;
import java.util.List;

import cc.kune.core.client.rpcservices.GroupServiceAsync;
import cc.kune.core.client.state.GroupChangedEvent;
import cc.kune.core.client.state.GroupChangedEvent.GroupChangedHandler;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.ContentSimpleDTO;
import cc.kune.core.shared.dto.ToolSimpleDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.options.GroupOptions;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class GroupOptToolsPresenter extends EntityOptToolsPresenter implements
    GroupOptTools {

  @Inject
  public GroupOptToolsPresenter(final StateManager stateManager, final Session session,
      final I18nTranslationService i18n, final GroupOptions entityOptions,
      final Provider<GroupServiceAsync> groupService, final GroupOptToolsView view) {
    super(session, stateManager, i18n, entityOptions, groupService);
    init(view);
    stateManager.onGroupChanged(true, new GroupChangedHandler() {
      @Override
      public void onGroupChanged(final GroupChangedEvent event) {
        setState();
      }
    });
  }

  @Override
  protected boolean applicable() {
    return session.isCurrentStateAGroup();
  }

  @Override
  protected Collection<ToolSimpleDTO> getAllTools() {
    return session.getGroupTools();
  }

  @Override
  protected StateToken getDefContentToken() {
    final ContentSimpleDTO defaultContent = session.getCurrentState().getGroup().getDefaultContent();
    return defaultContent == null ? null : defaultContent.getStateToken();
  }

  @Override
  protected String getDefContentTooltip() {
    // FIXME
    return i18n.t("You cannot disable this tool because it's where the current group home page is located. To do that you have to select other content as the default group home page but in another tool.");
  }

  @Override
  protected List<String> getEnabledTools() {
    return session.getCurrentState().getEnabledTools();
  }

  @Override
  protected StateToken getOperationToken() {
    return session.getCurrentStateToken();
  }

  @Override
  protected void gotoDifLocationIfNecessary(final String toolName) {
    if (session.getCurrentStateToken().getTool().equals(toolName)) {
      final ContentSimpleDTO defaultContent = session.getCurrentState().getGroup().getDefaultContent();
      if (defaultContent != null) {
        stateManager.gotoStateToken(defaultContent.getStateToken());
      }
    }
  }

}
