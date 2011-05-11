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
package cc.kune.gspace.client.options.pscape;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.client.ui.BasicThumb;
import cc.kune.common.client.ui.IconLabel;
import cc.kune.common.client.utils.TextUtils;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.services.FileDownloadUtils;
import cc.kune.core.client.services.ImageSize;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.options.EntityOptionsView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class EntityOptionsPublicSpaceConfPanel extends FlowPanel implements
    EntityOptionsPublicSpaceConfView {

  private final Image backImage;
  private final Button clearBtn;
  private final Label currentBackLabel;
  private final FileDownloadUtils downUtils;
  private final Label noBackLabel;
  private final IconLabel tabTitle;

  public EntityOptionsPublicSpaceConfPanel(final I18nTranslationService i18n,
      final FileDownloadUtils downUtils, final CoreResources res) {
    this.downUtils = downUtils;
    tabTitle = new IconLabel(res.themeChoose(), i18n.t("Style"));
    super.setHeight(String.valueOf(EntityOptionsView.HEIGHT) + "px");
    super.setWidth(String.valueOf(EntityOptionsView.WIDTH) + "px");

    final HorizontalPanel wsHP = new HorizontalPanel();
    final Label wsThemeInfo = new Label(i18n.t("Change this workspace theme:"));
    wsThemeInfo.addStyleName("kune-Margin-20-tb");
    // final Widget toolbarWsChange = (Widget) wsSelector.getView();
    // toolbarWsChange.addStyleName("kune-Margin-Medium-l");
    wsHP.add(wsThemeInfo);
    // wsHP.add(toolbarWsChange);
    add(wsHP);
    final VerticalPanel backPanel = new VerticalPanel();
    currentBackLabel = new Label(i18n.t("Current background image: "));
    noBackLabel = new Label(i18n.t("Also you can upload any image and select it later as background."));
    backImage = new Image();
    backImage.addStyleName("kune-Margin-Medium-trbl");
    noBackLabel.addStyleName("kune-Margin-Medium-tb");
    clearBtn = new Button(i18n.t("Clear"));
    Tooltip.to(clearBtn, i18n.t("Remove current background image"));
    backPanel.add(noBackLabel);
    backPanel.add(currentBackLabel);
    backPanel.add(backImage);
    backPanel.add(clearBtn);
    add(backPanel);
    final Label wsInfo = new Label(i18n.t("Select and configure the public space theme of this group:"));
    wsInfo.addStyleName("kune-Margin-Medium-tb");
    add(wsInfo);

    final VerticalPanel stylesPanel = new VerticalPanel();
    final ClickHandler clickHandler = new ClickHandler() {

      @Override
      public void onClick(final ClickEvent event) {
        NotifyUser.info(TextUtils.IN_DEVELOPMENT);
      }
    };
    for (int i = 1; i <= 6; i++) {
      final BasicThumb thumb = new BasicThumb("images/styles/styl" + i + ".png", "Style " + i,
          clickHandler);
      thumb.setTooltip(i18n.t("Click to select and configure this theme"));
      add(thumb);
    }
    add(stylesPanel);
    setBackImageVisibleImpl(false);
    super.addStyleName("k-overflow-y-auto");
    super.addStyleName("k-tab-panel");
  }

  @Override
  public void clearBackImage() {
    setBackImageVisibleImpl(false);
  }

  @Override
  public HasClickHandlers getClearBtn() {
    return clearBtn;
  }

  @Override
  public IsWidget getTabTitle() {
    return tabTitle;
  }

  @Override
  public void setBackImage(final StateToken token) {
    backImage.setUrl(downUtils.getImageResizedUrl(token, ImageSize.thumb));
    setBackImageVisibleImpl(true);
  }

  private void setBackImageVisibleImpl(final boolean visible) {
    backImage.setVisible(visible);
    currentBackLabel.setVisible(visible);
    clearBtn.setVisible(visible);
    noBackLabel.setVisible(!visible);
  }
}
