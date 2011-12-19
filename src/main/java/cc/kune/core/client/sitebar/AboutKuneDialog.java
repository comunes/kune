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
package cc.kune.core.client.sitebar;

import cc.kune.common.client.ui.dialogs.BasicTopDialog;
import cc.kune.common.shared.i18n.I18nTranslationService;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class AboutKuneDialog {

  interface Binder extends UiBinder<Widget, AboutKuneDialog> {
  }
  public static final String ABOUT_KUNE_BTN_ID = "kune-about-button-diag";
  public static final String ABOUT_KUNE_ID = "kune-about-diag";
  private static final Binder BINDER = GWT.create(Binder.class);
  private final BasicTopDialog dialog;
  @UiField
  FlowPanel flow;
  @UiField
  Frame frame;

  @Inject
  public AboutKuneDialog(final I18nTranslationService i18n) {
    dialog = new BasicTopDialog.Builder(ABOUT_KUNE_ID, true, true, i18n.getDirection()).title(
        i18n.tWithNT("About Kune", "title of dialog")).autoscroll(false).firstButtonTitle(i18n.t("Ok")).firstButtonId(
        ABOUT_KUNE_BTN_ID).tabIndexStart(1).height("300px").width("410px").build();
    dialog.getInnerPanel().add(BINDER.createAndBindUi(this));
    dialog.getFirstBtn().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        dialog.hide();
      }
    });
  }

  public void showCentered() {
    dialog.showCentered();
  }

}
