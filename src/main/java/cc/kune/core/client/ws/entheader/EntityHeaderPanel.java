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
package cc.kune.core.client.ws.entheader;

import cc.kune.common.client.actions.ui.ActionFlowPanel;
import cc.kune.common.client.actions.ui.GuiProvider;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.client.ws.entheader.EntityHeaderPresenter.EntityHeaderView;
import cc.kune.core.shared.FileConstants;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.gspace.client.armor.GSpaceArmor;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.ViewImpl;

public class EntityHeaderPanel extends ViewImpl implements EntityHeaderView {

  private final Provider<ClientFileDownloadUtils> downloadProvider;
  private final EntityTextLogo entityTextLogo;
  private final CoreResources images;
  private final HorizontalPanel mainPanel;
  private final ActionFlowPanel toolbar;
  private final VerticalPanel vpanel;

  @Inject
  public EntityHeaderPanel(final Provider<ClientFileDownloadUtils> downloadProvider,
      final CoreResources images, final GuiProvider bindings, final GSpaceArmor armor,
      final EntityTextLogo entityTextLogo, final I18nTranslationService i18n) {
    this.entityTextLogo = entityTextLogo;
    mainPanel = new HorizontalPanel();
    mainPanel.setWidth("100%");
    this.downloadProvider = downloadProvider;
    this.images = images;
    vpanel = new VerticalPanel();
    vpanel.setWidth("100%");
    vpanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
    mainPanel.add(entityTextLogo);
    toolbar = new ActionFlowPanel(bindings, i18n);
    vpanel.add(toolbar);
    mainPanel.add(vpanel);
    armor.getEntityHeader().add(mainPanel);
  }

  @Override
  public void addAction(final GuiActionDescrip descriptor) {
    toolbar.add(descriptor);
  }

  @Override
  public void addWidget(final IsWidget view) {
    final Widget widget = (Widget) view;
    vpanel.add(widget);
  }

  @Override
  public Widget asWidget() {
    return mainPanel;
  }

  @Deprecated
  public void setFullLogo(final StateToken stateToken, final boolean clipped) {
    mainPanel.clear();
    final String imageUrl = downloadProvider.get().getImageUrl(stateToken);
    Image logo;
    if (clipped) {
      logo = new Image(imageUrl, 0, 0, FileConstants.LOGO_DEF_WIDTH, FileConstants.LOGO_DEF_SIZE);
    } else {
      logo = new Image(imageUrl);
      logo.setWidth(String.valueOf(FileConstants.LOGO_DEF_WIDTH));
      logo.setHeight(String.valueOf(FileConstants.LOGO_DEF_SIZE));
    }
    mainPanel.add(logo);
  }

  @Override
  public void setLargeFont() {
    entityTextLogo.setLargeFont();
  }

  @Override
  public void setLogoImage(final GroupDTO group, final boolean noCache) {
    final String url = downloadProvider.get().getLogoImageUrl(group.getShortName(), noCache);
    entityTextLogo.setLogoImage(url);
  }

  @Override
  public void setLogoImageVisible(final boolean visible) {
    entityTextLogo.setLogoVisible(visible);
  }

  @Override
  public void setLogoText(final String groupName) {
    entityTextLogo.setLogoText(groupName);
  }

  @Override
  public void setMediumFont() {
    entityTextLogo.setMediumFont();
  }

  @Override
  public void setOnlineStatusGroup(final String group) {
    entityTextLogo.setOnlineStatusGroup(group);
  }

  @Override
  public void setOnlineStatusVisible(final boolean visible) {
    entityTextLogo.setOnlineStatusVisible(visible);
  }

  @Override
  public void setSmallFont() {
    entityTextLogo.setSmallFont();
  }

  @Override
  public void showDefUserLogo() {
    entityTextLogo.setLogoImage(AbstractImagePrototype.create(images.unknown60()));
  }

}
