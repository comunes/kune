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
import cc.kune.common.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.StateChangedEvent;
import cc.kune.core.client.state.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;

public class GroupOptionsPresenter extends AbstractTabbedDialogPresenter implements GroupOptions {
  public static final String GROUP_OPTIONS_ICON = "k-eop-icon";
  private final I18nTranslationService i18n;
  private final CoreResources img;
  private ButtonDescriptor prefsItem;
  private GroupOptionsView view;

  @Inject
  public GroupOptionsPresenter(final StateManager stateManager, final I18nTranslationService i18n,
      final CoreResources img, final GroupOptionsView view) {
    this.i18n = i18n;
    this.img = img;
    stateManager.onStateChanged(true, new StateChangedHandler() {
      @Override
      public void onStateChanged(final StateChangedEvent event) {
        final StateAbstractDTO state = event.getState();
        if (!state.getGroup().isPersonal() && state.getGroupRights().isAdministrable()) {
          prefsItem.setVisible(true);
        } else {
          view.hide();
          prefsItem.setVisible(false);
        }
      }
    });
    init(view);
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
    prefsItem.withStyles("k-ent-header-btn, k-noborder, k-nobackcolor, k-btn-min");
    prefsItem.setId(GROUP_OPTIONS_ICON);
    prefsItem.setVisible(false);
    view.addAction(prefsItem);
  }

  private void init(final GroupOptionsView view) {
    super.init(view);
    this.view = view;
    createActions();
  }
}
