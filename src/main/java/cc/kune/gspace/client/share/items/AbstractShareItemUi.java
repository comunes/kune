/*******************************************************************************
 * Copyright (C) 2007, 2013 The kune development team (see CREDITS for details)
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
 *******************************************************************************/

package cc.kune.gspace.client.share.items;

import cc.kune.common.client.actions.ui.ActionSimplePanel;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.ui.IconLabel;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.shared.dto.GroupDTO;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * The Class AbstractShareItem is a simple element that represent, for instance,
 * a group/user a content is share to
 */
public abstract class AbstractShareItemUi extends Composite {

  private final ActionSimplePanel actionsPanel;
  private final ClientFileDownloadUtils downloadUtils;

  /** The flow. */
  private final FlowPanel flow;

  /** The icon label. */
  private final IconLabel iconLabel;

  /**
   * Instantiates a new abstract share item.
   * 
   * @param url
   *          the url
   * @param text
   *          the text
   * @param actionsPanel
   *          the actions panel
   * @param downloadUtils
   * @param contentServiceAsync
   */
  public AbstractShareItemUi(final ActionSimplePanel actionsPanel,
      final ClientFileDownloadUtils downloadUtils) {
    this.actionsPanel = actionsPanel;
    this.downloadUtils = downloadUtils;
    flow = new FlowPanel();
    iconLabel = new IconLabel();
    flow.add(iconLabel);
    flow.add(this.actionsPanel);
    flow.setStyleName("k-shareitem");
    iconLabel.addStyleName("k-fl");
    this.actionsPanel.addStyleName("k-fr");
    this.actionsPanel.addStyleName("k-share-item-actions");
    initWidget(flow);
  }

  public void add(final GuiActionDescrip... menuItems) {
    actionsPanel.add(menuItems);
  }

  @Deprecated
  public void setGroupName(final GroupDTO group) {
    final boolean isAnUser = group.isPersonal();
    withText((isAnUser ? "" : I18n.t("Group") + ": ") + group.getLongName());
    withIcon(downloadUtils.getGroupLogo(group));
  }

  public AbstractShareItemUi with(final String typeId) {
    return this;
  }

  public AbstractShareItemUi withIcon(final ImageResource img) {
    iconLabel.setLeftIconResource(img);
    return this;
  }

  public AbstractShareItemUi withIcon(final String url) {
    iconLabel.setLeftIconUrl(url);
    return this;
  }

  public AbstractShareItemUi withText(final String text) {
    iconLabel.setText(text);
    return this;
  }

}
