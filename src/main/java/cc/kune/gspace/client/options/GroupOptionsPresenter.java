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
package cc.kune.gspace.client.options;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.events.StateChangedEvent;
import cc.kune.core.client.events.UserSignOutEvent;
import cc.kune.core.client.events.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.events.UserSignOutEvent.UserSignOutHandler;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.gspace.client.options.GroupOptionsPresenter.GroupOptionsView;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;

public class GroupOptionsPresenter extends
    AbstractTabbedDialogPresenter<GroupOptionsView, GroupOptionsPresenter.GroupOptionsProxy> implements
    GroupOptions {

  @ProxyCodeSplit
  public interface GroupOptionsProxy extends Proxy<GroupOptionsPresenter> {
  }

  public interface GroupOptionsView extends EntityOptionsView {
    void addAction(GuiActionDescrip descriptor);
  }

  public static final String GROUP_OPTIONS_ICON = "k-eop-icon";
  private final I18nTranslationService i18n;
  private final CoreResources img;
  private ButtonDescriptor prefsItem;
  private final Session session;
  private final StateManager stateManager;

  @Inject
  public GroupOptionsPresenter(final EventBus eventBus, final GroupOptionsProxy proxy,
      final StateManager stateManager, final Session session, final I18nTranslationService i18n,
      final CoreResources img, final GroupOptionsView view) {
    super(eventBus, view, proxy);
    this.stateManager = stateManager;
    this.session = session;
    this.i18n = i18n;
    this.img = img;
  }

  private void createActions() {
    final AbstractExtendedAction groupPrefsAction = new AbstractExtendedAction() {
      @Override
      public void actionPerformed(final ActionEvent event) {
        show();
      }
    };
    // groupPrefsAction.putValue(Action.NAME, i18n.t("Group options"));
    groupPrefsAction.putValue(Action.SMALL_ICON, img.prefs());
    prefsItem = new ButtonDescriptor(groupPrefsAction);
    prefsItem.withStyles("k-ent-header-btn, k-btn-min");
    // k-noborder,
    // k-nobackcolor,
    // k-btn-min");
    prefsItem.setId(GROUP_OPTIONS_ICON);
    prefsItem.withToolTip(i18n.t("Set your group preferences here"));
    prefsItem.setVisible(false);
    getView().addAction(prefsItem);
  }

  @Override
  public GroupOptionsView getView() {
    return (GroupOptionsView) super.getView();
  }

  @Override
  protected void onBind() {
    super.onBind();
    createActions();
    stateManager.onStateChanged(true, new StateChangedHandler() {
      @Override
      public void onStateChanged(final StateChangedEvent event) {
        final StateAbstractDTO state = event.getState();
        if (!state.getGroup().isPersonal() && state.getGroupRights().isAdministrable()) {
          prefsItem.setVisible(true);
        } else {
          getView().hide();
          prefsItem.setVisible(false);
        }
      }
    });
    session.onUserSignOut(false, new UserSignOutHandler() {
      @Override
      public void onUserSignOut(final UserSignOutEvent event) {
        getView().hide();
      }
    });
  }

  @Override
  public void showTooltip() {
    prefsItem.toggleTooltipVisible();
  }
}
