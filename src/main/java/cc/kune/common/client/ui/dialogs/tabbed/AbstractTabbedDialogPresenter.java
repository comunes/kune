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
package cc.kune.common.client.ui.dialogs.tabbed;

import cc.kune.common.client.notify.NotifyLevel;

import com.google.gwt.user.client.ui.IsWidget;

public abstract class AbstractTabbedDialogPresenter implements AbstractTabbedDialog {

  public interface AbstractTabbedDialogView extends IsWidget {

    void activateTab(int index);

    void addTab(IsWidget tab, IsWidget tabTitle);

    void destroy();

    void hide();

    void hideMessages();

    void insertTab(IsWidget tab, IsWidget tabTitle, int position);

    void setErrorMessage(final String message, final NotifyLevel level);

    void show();
  }

  private AbstractTabbedDialogView view;

  @Override
  public void activateTab(final int index) {
    view.activateTab(index);
  }

  @Override
  public void addTab(final IsWidget tab, final IsWidget tabTitle) {
    view.addTab(tab, tabTitle);
  }

  public IsWidget getView() {
    return view;
  }

  @Override
  public void hide() {
    view.hide();
  }

  @Override
  public void hideMessages() {
    view.hideMessages();
  }

  public void init(final AbstractTabbedDialogView view) {
    this.view = view;
  }

  @Override
  public void insertTab(final IsWidget tab, final IsWidget tabTitle, final int index) {
    view.insertTab(tab, tabTitle, index);
  }

  @Override
  public void setErrorMessage(final String message, final NotifyLevel level) {
    view.setErrorMessage(message, level);
  }

  @Override
  public void show() {
    hideMessages();
    view.show();
  }

}
