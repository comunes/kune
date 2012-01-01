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
package cc.kune.gspace.client.options.style;

import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.OnFinishUploaderHandler;
import gwtupload.client.IUploader.OnStartUploaderHandler;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.GroupServiceAsync;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.client.services.ImageSize;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateChangedEvent;
import cc.kune.core.client.state.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.gspace.client.options.EntityOptions;
import cc.kune.gspace.client.style.ClearBackImageEvent;
import cc.kune.gspace.client.style.GSpaceBackManager;
import cc.kune.gspace.client.style.SetBackImageEvent;
import cc.kune.gspace.client.themes.GSpaceThemeSelectorPresenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Provider;

public abstract class EntityOptStylePresenter implements EntityOptStyle {
  private final GSpaceBackManager backManager;
  private final EntityOptions entityOptions;
  private final EventBus eventBus;
  private final ClientFileDownloadUtils fileDownloadUtils;
  private final Provider<GroupServiceAsync> groupService;
  private final I18nTranslationService i18n;
  private final Session session;
  private final StateManager stateManager;
  private EntityOptStyleView view;

  protected EntityOptStylePresenter(final EventBus eventBus, final Session session,
      final StateManager stateManager, final EntityOptions entityOptions,
      final Provider<GroupServiceAsync> groupService, final GSpaceBackManager backManager,
      final GSpaceThemeSelectorPresenter styleSelector, final I18nTranslationService i18n,
      final ClientFileDownloadUtils fileDownloadUtils) {
    this.eventBus = eventBus;
    this.session = session;
    this.stateManager = stateManager;
    this.entityOptions = entityOptions;
    this.groupService = groupService;
    this.backManager = backManager;
    this.i18n = i18n;
    this.fileDownloadUtils = fileDownloadUtils;
  }

  private void clearBackImage() {
    groupService.get().clearGroupBackImage(session.getUserHash(), session.getCurrentStateToken(),
        new AsyncCallbackSimple<GroupDTO>() {
          @Override
          public void onSuccess(final GroupDTO result) {
            view.clearBackImage();
            backManager.clearBackImage();
            ClearBackImageEvent.fire(eventBus);
          }
        });
  }

  public IsWidget getView() {
    return view;
  }

  public void init(final EntityOptStyleView view) {
    this.view = view;
    entityOptions.addTab(view, view.getTabTitle());
    setState();
    view.getClearBtn().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        clearBackImage();
      }
    });
    stateManager.onStateChanged(true, new StateChangedHandler() {
      @Override
      public void onStateChanged(final StateChangedEvent event) {
        setState();
      }
    });
    eventBus.addHandler(SetBackImageEvent.getType(), new SetBackImageEvent.SetBackImageHandler() {
      @Override
      public void onSetBackImage(final SetBackImageEvent event) {
        final StateToken token = event.getToken();
        backManager.setBackImage(token);
        setBackImage(event.getToken());
      }
    });
    eventBus.addHandler(ClearBackImageEvent.getType(), new ClearBackImageEvent.ClearBackImageHandler() {
      @Override
      public void onClearBackImage(final ClearBackImageEvent event) {
        view.clearBackImage();
      }
    });
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

  private void onSubmitComplete(final IUploader uploader) {
    final String response = uploader.getServerInfo().message;
    if (uploader.getStatus() == Status.SUCCESS) {
      if (!TextUtils.empty(response)) {
        NotifyUser.info(response);
      }
      SetBackImageEvent.fire(eventBus, session.getCurrentState().getGroup().getStateToken());
    } else {
      onSubmitFailed(response);
    }
  }

  private void onSubmitFailed(final String responseText) {
    NotifyUser.error(i18n.t("Error setting the background"), responseText);
  }

  private void setBackImage(final StateToken token) {
    view.setBackImage(fileDownloadUtils.getBackgroundResizedUrl(token, ImageSize.thumb));
  }

  private void setState() {
    final StateAbstractDTO state = session.getCurrentState();
    final GroupDTO group = state.getGroup();
    if (group.getBackgroundImage() == null) {
      view.clearBackImage();
    } else {
      setBackImage(group.getStateToken());
    }
    view.setUploadParams(session.getUserHash(), session.getCurrentStateToken().toString());
  }

}
