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
package cc.kune.gspace.client.options.tools;

import java.util.Collection;
import java.util.List;

import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.errors.ToolIsDefaultException;
import cc.kune.core.client.rpcservices.GroupServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.ToolSimpleDTO;
import cc.kune.gspace.client.options.EntityOptions;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityOptToolsPresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class EntityOptToolsPresenter {

  /** The entity options. */
  private final EntityOptions entityOptions;

  /** The group service. */
  private final Provider<GroupServiceAsync> groupService;

  /** The i18n. */
  protected final I18nTranslationService i18n;

  /** The session. */
  protected final Session session;

  /** The state manager. */
  protected final StateManager stateManager;

  /** The view. */
  private EntityOptToolsView view;

  /**
   * Instantiates a new entity opt tools presenter.
   * 
   * @param session
   *          the session
   * @param stateManager
   *          the state manager
   * @param i18n
   *          the i18n
   * @param entityOptions
   *          the entity options
   * @param groupService
   *          the group service
   */
  public EntityOptToolsPresenter(final Session session, final StateManager stateManager,
      final I18nTranslationService i18n, final EntityOptions entityOptions,
      final Provider<GroupServiceAsync> groupService) {
    this.session = session;
    this.stateManager = stateManager;
    this.i18n = i18n;
    this.entityOptions = entityOptions;
    this.groupService = groupService;
  }

  /**
   * Applicable.
   * 
   * @return true, if successful
   */
  protected abstract boolean applicable();

  /**
   * Gets the all tools.
   * 
   * @return the all tools
   */
  protected abstract Collection<ToolSimpleDTO> getAllTools();

  /**
   * Gets the def content token.
   * 
   * @return the def content token
   */
  protected abstract StateToken getDefContentToken();

  /**
   * Gets the def content tooltip.
   * 
   * @return the def content tooltip
   */
  protected abstract String getDefContentTooltip();

  /**
   * Gets the enabled tools.
   * 
   * @return the enabled tools
   */
  protected abstract List<String> getEnabledTools();

  /**
   * Gets the operation token.
   * 
   * @return the operation token
   */
  protected abstract StateToken getOperationToken();

  /**
   * Gets the view.
   * 
   * @return the view
   */
  public IsWidget getView() {
    return view;
  }

  /**
   * Goto dif location if necessary.
   * 
   * @param toolName
   *          the tool name
   */
  protected abstract void gotoDifLocationIfNecessary(String toolName);

  /**
   * Inits the.
   * 
   * @param view
   *          the view
   */
  public void init(final EntityOptToolsView view) {
    this.view = view;
    setState();
    entityOptions.addTab(view, view.getTabTitle());
  }

  /**
   * On check.
   * 
   * @param tool
   *          the tool
   * @param checked
   *          the checked
   */
  private void onCheck(final ToolSimpleDTO tool, final boolean checked) {
    final List<String> enabledTools = getEnabledTools();
    final String toolName = tool.getName();
    setToolCheckedInServer(checked, toolName);
    if (!checked) {
      if (enabledTools.contains(toolName)) {
        // FIXME uncomment this when fixed
        // setToolCheckedInServer(checked, toolName);
        gotoDifLocationIfNecessary(toolName);
      }
    }
  }

  /**
   * Reset.
   */
  protected void reset() {
    view.clear();
    entityOptions.hideMessages();
  }

  /**
   * Sets the state.
   */
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

  /**
   * Sets the tool checked in server.
   * 
   * @param checked
   *          the checked
   * @param toolName
   *          the tool name
   */
  protected void setToolCheckedInServer(final boolean checked, final String toolName) {
    // view.mask();
    // NotifyUser.info("Dis/Enabling tool" + toolName + " - " + checked);
    groupService.get().setToolEnabled(session.getUserHash(), getOperationToken(), toolName, checked,
        new AsyncCallback<Void>() {
          @Override
          public void onFailure(final Throwable caught) {
            if (caught instanceof ToolIsDefaultException) {
              NotifyUser.error(getDefContentTooltip());
            } else {
              entityOptions.setErrorMessage(i18n.t("Error configuring the tool"), NotifyLevel.error);
              NotifyUser.error(i18n.t("Error configuring the tool"));
            }
            view.setChecked(toolName, !checked);
            // view.unmask();
          }

          @Override
          public void onSuccess(final Void result) {
            stateManager.refreshCurrentStateWithoutCache();
            // view.unmask();
          }
        });
  }
}
