/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
package cc.kune.core.client.ui.dialogs.tabbed;

import cc.kune.common.client.notify.NotifyLevel;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public abstract class AbstractTabbedDialogPresenter<V extends View, Proxy_ extends Proxy<?>> extends
    Presenter<View, Proxy<?>> implements AbstractTabbedDialog {

  public interface AbstractTabbedDialogView extends View, IsWidget {
    void activateTab(int index);

    void addTab(IsWidget tab, IsWidget tabTitle);

    void destroy();

    void hide();

    void hideMessages();

    void insertTab(IsWidget tab, IsWidget tabTitle, int position);

    void setErrorMessage(final String message, final NotifyLevel level);

    void show();
  }

  @Inject
  public AbstractTabbedDialogPresenter(final EventBus eventBus, final AbstractTabbedDialogView view,
      final Proxy<?> proxy) {
    super(eventBus, view, proxy);
  }

  @Override
  public void activateTab(final int index) {
    getView().activateTab(index);
  }

  @Override
  public void addTab(final IsWidget tab, final IsWidget tabTitle) {
    getView().addTab(tab, tabTitle);
  }

  @Override
  public AbstractTabbedDialogView getView() {
    return (AbstractTabbedDialogView) super.getView();
  }

  @Override
  public void hide() {
    getView().hide();
  }

  @Override
  public void hideMessages() {
    getView().hideMessages();
  }

  @Override
  public void insertTab(final IsWidget tab, final IsWidget tabTitle, final int index) {
    getView().insertTab(tab, tabTitle, index);
  }

  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }

  @Override
  public void setErrorMessage(final String message, final NotifyLevel level) {
    getView().setErrorMessage(message, level);
  }

  @Override
  public void show() {
    hideMessages();
    getView().show();
  }

}
