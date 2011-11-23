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
 \*/
package cc.kune.gspace.client.tool.selector;

import java.util.HashMap;
import java.util.List;

import cc.kune.common.client.errors.UIException;
import cc.kune.common.client.log.Log;
import cc.kune.common.client.utils.TextUtils;
import cc.kune.core.client.state.GroupChangedEvent;
import cc.kune.core.client.state.GroupChangedEvent.GroupChangedHandler;
import cc.kune.core.client.state.StateChangedEvent;
import cc.kune.core.client.state.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.ToolChangedEvent;
import cc.kune.core.client.state.ToolChangedEvent.ToolChangedHandler;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.gspace.client.tool.selector.ToolSelectorItemPresenter.ToolSelectorItemView;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class ToolSelectorPresenter extends
    Presenter<ToolSelectorPresenter.ToolSelectorView, ToolSelectorPresenter.ToolSelectorProxy> implements
    ToolSelector {

  @ProxyCodeSplit
  public interface ToolSelectorProxy extends Proxy<ToolSelectorPresenter> {
  }

  public interface ToolSelectorView extends View {
    void addItem(ToolSelectorItemView item);
  }

  private final HashMap<String, ToolSelectorItem> tools;

  @Inject
  public ToolSelectorPresenter(final EventBus eventBus, final ToolSelectorView view,
      final ToolSelectorProxy proxy, final StateManager stateManager) {
    super(eventBus, view, proxy);
    tools = new HashMap<String, ToolSelectorItem>();
    stateManager.onStateChanged(true, new StateChangedHandler() {
      @Override
      public void onStateChanged(final StateChangedEvent event) {
        for (final String tool : tools.keySet()) {
          final List<String> enabledTools = event.getState().getEnabledTools();
          if (enabledTools != null && enabledTools.contains(tool)) {
            tools.get(tool).setVisible(true);
          } else {
            tools.get(tool).setVisible(false);
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

  @Override
  public void addTool(final ToolSelectorItem item) {
    final String name = item.getShortName();
    if (name == null) {
      throw new UIException("You cannot add a tool without a name");
    }
    if (tools.get(name) != null) {
      throw new UIException("A tool with the same name already added");
    }
    tools.put(name, item);
    item.setSelected(false);
    getView().addItem(item.getView());
  }

  private void checkTool(final ToolSelectorItem tool) {
    if (tool == null) {
      throw new UIException("Trying to activate an unregistered tool in client");
    }

  }

  void onGroupChanged(final String newGroupName) {
    for (final String name : tools.keySet()) {
      tools.get(name).setGroupShortName(newGroupName);
    }
  }

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

  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }
}
