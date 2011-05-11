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

import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.core.client.rpcservices.GroupServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.ToolSimpleDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.options.EntityOptions;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Provider;

public abstract class EntityOptionsToolsConfPresenter {

  private final EntityOptions entityOptions;
  private final Provider<GroupServiceAsync> groupService;
  protected final I18nTranslationService i18n;
  protected final Session session;
  protected final StateManager stateManager;
  private EntityOptionsToolsConfView view;

  public EntityOptionsToolsConfPresenter(final Session session, final StateManager stateManager,
      final I18nTranslationService i18n, final EntityOptions entityOptions,
      final Provider<GroupServiceAsync> groupService) {
    this.session = session;
    this.stateManager = stateManager;
    this.i18n = i18n;
    this.entityOptions = entityOptions;
    this.groupService = groupService;
  }

  protected abstract boolean applicable();

  protected abstract Collection<ToolSimpleDTO> getAllTools();

  protected abstract StateToken getDefContentToken();

  protected abstract String getDefContentTooltip();

  protected abstract List<String> getEnabledTools();

  protected abstract StateToken getOperationToken();

  public IsWidget getView() {
    return view;
  }

  protected abstract void gotoDifLocationIfNecessary(String toolName);

  public void init(final EntityOptionsToolsConfView view) {
    this.view = view;
    setState();
    entityOptions.addTab(view, view.getTabTitle());
  }

  private void onCheck(final ToolSimpleDTO tool, final boolean checked) {
    final List<String> enabledTools = getEnabledTools();
    final String toolName = tool.getName();
    setToolCheckedInServer(checked, toolName);
    if (!checked) {
      if (enabledTools.contains(toolName)) {
        // FIXME uncomment this when fixed
        // setToolCheckedInServer(checked, toolName);
        // gotoDifLocationIfNecessary(toolName);
      }
    }
  }

  protected void reset() {
    view.clear();
    entityOptions.hideMessages();
  }

  protected void setState() {
    reset();
    final Collection<ToolSimpleDTO> toolCollection = getAllTools();
    final List<String> enabledTools = getEnabledTools();
    for (final ToolSimpleDTO tool : toolCollection) {
      final boolean checked = enabledTools.contains(tool.getName());
      final boolean enabled = true;
      view.add(tool, enabled, checked, new ClickHandler() {
        @Override
        public void onClick(final ClickEvent event) {
          onCheck(tool, view.isChecked(tool.getName()));
        }
      });
    }
    // Disable def content tool (don't works now)
    final StateToken token = getDefContentToken();
    if (token != null) {
      final String defContentTool = token.getTool();
      if (defContentTool != null) {
        // view.setEnabled(defContentTool, false);
        view.setTooltip(defContentTool, getDefContentTooltip());
      }
    }
  }

  protected void setToolCheckedInServer(final boolean checked, final String toolName) {
    // view.mask();
    // NotifyUser.info("Dis/Enabling tool" + toolName + " - " + checked);
    groupService.get().setToolEnabled(session.getUserHash(), getOperationToken(), toolName, checked,
        new AsyncCallback<Void>() {
          @Override
          public void onFailure(final Throwable caught) {
            view.setChecked(toolName, !checked);
            entityOptions.setErrorMessage(i18n.t("Error configuring the tool"), NotifyLevel.error);
            NotifyUser.error(i18n.t("Error configuring the tool"));
            // view.unmask();
          }

          @Override
          public void onSuccess(final Void result) {
            stateManager.reload();
            // view.unmask();
          }
        });
  }
}
