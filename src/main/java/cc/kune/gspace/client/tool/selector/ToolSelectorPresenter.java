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
 \*/
package cc.kune.gspace.client.tool.selector;

import java.util.HashMap;
import java.util.List;

import cc.kune.common.client.errors.UIException;
import cc.kune.common.client.log.Log;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.actions.RolActionHelper;
import cc.kune.core.client.events.GroupChangedEvent;
import cc.kune.core.client.events.GroupChangedEvent.GroupChangedHandler;
import cc.kune.core.client.events.StateChangedEvent;
import cc.kune.core.client.events.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.events.ToolChangedEvent;
import cc.kune.core.client.events.ToolChangedEvent.ToolChangedHandler;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.gspace.client.tool.selector.ToolSelectorItemPresenter.ToolSelectorItemView;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class ToolSelectorPresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ToolSelectorPresenter extends
    Presenter<ToolSelectorPresenter.ToolSelectorView, ToolSelectorPresenter.ToolSelectorProxy> implements
    ToolSelector {

  /**
   * The Interface ToolSelectorProxy.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  @ProxyCodeSplit
  public interface ToolSelectorProxy extends Proxy<ToolSelectorPresenter> {
  }

  /**
   * The Interface ToolSelectorView.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface ToolSelectorView extends View {

    /**
     * Adds the item.
     * 
     * @param item
     *          the item
     */
    void addItem(ToolSelectorItemView item);
  }

  /** The tools. */
  private final HashMap<String, ToolSelectorItem> tools;

  /**
   * Instantiates a new tool selector presenter.
   * 
   * @param eventBus
   *          the event bus
   * @param view
   *          the view
   * @param proxy
   *          the proxy
   * @param stateManager
   *          the state manager
   */
  @Inject
  public ToolSelectorPresenter(final EventBus eventBus, final ToolSelectorView view,
      final ToolSelectorProxy proxy, final StateManager stateManager) {
    super(eventBus, view, proxy);
    tools = new HashMap<String, ToolSelectorItem>();
    stateManager.onStateChanged(true, new StateChangedHandler() {
      @Override
      public void onStateChanged(final StateChangedEvent event) {
        for (final String toolName : tools.keySet()) {
          final StateAbstractDTO state = event.getState();
          final List<String> enabledTools = state.getEnabledTools();
          if (enabledTools != null && enabledTools.contains(toolName)) {
            final ToolSelectorItem tool = tools.get(toolName);
            // Set visible only when allowed
            tool.setVisible(RolActionHelper.isAuthorized(tool.getVisibleForRol(), state.getGroupRights()));
          } else {
            tools.get(toolName).setVisible(false);
          }
        }
      }
    });
    stateManager.onGroupChanged(true, new GroupChangedHandler() {
      @Override
      public void onGroupChanged(final GroupChangedEvent event) {
        ToolSelectorPresenter.this.onGroupChanged(event.getNewGroup());
      }
    });
    stateManager.onToolChanged(false, new ToolChangedHandler() {
      @Override
      public void onToolChanged(final ToolChangedEvent event) {
        ToolSelectorPresenter.this.onToolChanged(event.getPreviousTool(), event.getNewTool(),
            event.getPreviousToken(), event.getNewToken());
      }
    });
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.tool.selector.ToolSelector#addTool(cc.kune.gspace
   * .client.tool.selector.ToolSelectorItem)
   */
  @Override
  public void addTool(final ToolSelectorItem item) {
    final String name = item.getToolShortName();
    if (name == null) {
      throw new UIException("You cannot add a tool without a name");
    }
    if (tools.get(name) != null) {
      throw new UIException("A tool with the same name already added");
    }
    tools.put(name, item);
    item.setSelected(false);
    // if (!name.equals(TrashToolConstants.NAME)) {
    getView().addItem(item.getView());
    // }
  }

  /**
   * Check tool.
   * 
   * @param tool
   *          the tool
   */
  private void checkTool(final ToolSelectorItem tool) {
    if (tool == null) {
      throw new UIException("Trying to activate an unregistered tool in client");
    }

  }

  /**
   * On group changed.
   * 
   * @param newGroupName
   *          the new group name
   */
  void onGroupChanged(final String newGroupName) {
    for (final String name : tools.keySet()) {
      tools.get(name).setGroupShortName(newGroupName);
    }
  }

  /**
   * On tool changed.
   * 
   * @param oldTool
   *          the old tool
   * @param newTool
   *          the new tool
   * @param oldToken
   *          the old token
   * @param newToken
   *          the new token
   */
  void onToolChanged(final String oldTool, final String newTool, final StateToken oldToken,
      final StateToken newToken) {
    Log.debug("Registered tools: " + tools.keySet().toString());
    if (TextUtils.notEmpty(oldTool)) {
      final ToolSelectorItem tool = tools.get(oldTool);
      checkTool(tool);
      tool.setSelected(false);
      if (newToken != null && oldToken != null && newToken.getGroup().equals(oldToken.getGroup())) {
        // only if we don't change of group
        tool.setToken(oldToken);
      }
    }
    if (TextUtils.notEmpty(newTool)) {
      final ToolSelectorItem tool = tools.get(newTool);
      checkTool(tool);
      tool.setSelected(true);
      tool.setGroupShortName(newToken.getGroup());
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
   */
  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }
}
