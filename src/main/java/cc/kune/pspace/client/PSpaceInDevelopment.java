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
package cc.kune.pspace.client;

import cc.kune.common.client.utils.TextUtils;
import cc.kune.common.client.utils.UrlParam;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.services.FileDownloadUtils;
import cc.kune.core.client.state.GroupChangedEvent;
import cc.kune.core.client.state.GroupChangedEvent.GroupChangedHandler;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.gspace.client.options.logo.CurrentEntityChangedEvent;
import cc.kune.gspace.client.options.logo.CurrentEntityChangedEvent.CurrentEntityChangedHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class PSpaceInDevelopment extends Composite {

  interface PSpaceInDevelopmentUiBinder extends UiBinder<Widget, PSpaceInDevelopment> {
  }

  private static PSpaceInDevelopmentUiBinder uiBinder = GWT.create(PSpaceInDevelopmentUiBinder.class);
  @UiField
  FlowPanel container;
  @UiField
  FlowPanel contentPanel;
  private final Provider<FileDownloadUtils> downloadProvider;
  @UiField
  Image entityLogo;
  @UiField
  Label entityName;
  @UiField
  FlowPanel headerPanel;
  private final CoreResources images;
  @UiField
  Label inDevel;
  @UiField
  HTMLPanel inDevelSupport;
  @UiField
  FlowPanel mainPanel;
  @UiField
  FlowPanel photoPanel;

  @Inject
  public PSpaceInDevelopment(final StateManager stateManager, final CoreResources images,
      final Session session, final Provider<FileDownloadUtils> downloadProvider,
      final I18nTranslationService i18n, final EventBus eventBus) {
    this.images = images;
    this.downloadProvider = downloadProvider;
    initWidget(uiBinder.createAndBindUi(this));
    stateManager.onGroupChanged(true, new GroupChangedHandler() {
      @Override
      public void onGroupChanged(final GroupChangedEvent event) {
        setGroupLogo(session.getCurrentState().getGroup(), false);
      }
    });
    inDevel.setText(i18n.t("Right now, the public web space of this group, it's under construction"));
    inDevelSupport.getElement().setInnerHTML(
        i18n.t("[%s] the development",
            TextUtils.generateHtmlLink("http://kune.ourproject.org/join/", i18n.t("Please support"))));
    eventBus.addHandler(CurrentEntityChangedEvent.getType(), new CurrentEntityChangedHandler() {
      @Override
      public void onCurrentLogoChanged(final CurrentEntityChangedEvent event) {
        final GroupDTO group = session.getCurrentState().getGroup();
        setGroupLogo(group, true);
      }
    });
  }

  void setGroupLogo(final GroupDTO group, final boolean noCache) {
    setLogoText(group.getLongName());
    if (group.hasLogo()) {
      setLogoImage(group.getStateToken(), noCache);
      setLogoImageVisible(true);
    } else {
      if (group.isPersonal()) {
        showDefUserLogo();
        setLogoImageVisible(true);
      } else {
        setLogoImageVisible(false);
      }
    }
  }

  private void setLogoImage(final StateToken stateToken, final boolean noCache) {
    entityLogo.setUrl(downloadProvider.get().getLogoImageUrl(stateToken)
        + (noCache ? UrlParam.noCacheStringSuffix() : ""));
  }

  private void setLogoImageVisible(final boolean visible) {
    entityLogo.setVisible(visible);
  }

  private void setLogoText(final String longName) {
    entityName.setText(longName);
  }

  public void showDefUserLogo() {
    (AbstractImagePrototype.create(images.unknown60())).applyTo(entityLogo);
  }
}
