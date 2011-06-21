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
package cc.kune.gspace.client.options.logo;

import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.OnFinishUploaderHandler;
import gwtupload.client.IUploader.OnStartUploaderHandler;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.options.EntityOptions;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Provider;

public abstract class EntityOptionsLogoPresenter implements GroupOptionsLogo, UserOptionsLogo {
  private final EntityOptions entityOptions;
  protected final EventBus eventBus;
  private final I18nTranslationService i18n;
  protected final Session session;
  protected final Provider<UserServiceAsync> userService;
  protected EntityOptionsLogoView view;

  public EntityOptionsLogoPresenter(final EventBus eventBus, final Session session,
      final EntityOptions entityOptions, final Provider<UserServiceAsync> userService,
      final I18nTranslationService i18n) {
    this.eventBus = eventBus;
    this.session = session;
    this.entityOptions = entityOptions;
    this.userService = userService;
    this.i18n = i18n;
  }

  public IsWidget getView() {
    return view;
  }

  protected void init(final EntityOptionsLogoView view) {
    this.view = view;
    entityOptions.addTab(view, view.getTabTitle());
    setState();
    view.addOnStartUploadHandler(new OnStartUploaderHandler() {
      @Override
      public void onStart(final IUploader uploader) {
        setState();
      }
    });
    view.addOnFinishUploadHandler(new OnFinishUploaderHandler() {
      @Override
      public void onFinish(final IUploader uploader) {
        onSubmitComplete(uploader);
      }
    });
  }

  public void onSubmitComplete(final IUploader uploader) {
    final String response = uploader.getServerInfo().message;
    if (uploader.getStatus() == Status.SUCCESS) {
      if (response != null) {
        NotifyUser.info(response);
      }
      CurrentLogoChangedEvent.fire(eventBus);
    } else {
      onSubmitFailed(response);
    }
  }

  public void onSubmitFailed(final String responseText) {
    NotifyUser.error(i18n.t("Error setting the logo", responseText));
  }

  protected abstract void setState();

}
