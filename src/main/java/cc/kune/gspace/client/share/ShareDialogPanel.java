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

package cc.kune.gspace.client.share;

import cc.kune.common.client.ui.dialogs.BasicTopDialog;
import cc.kune.common.client.ui.dialogs.BasicTopDialog.Builder;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.gspace.client.share.ShareDialogPresenter.ShareDialogView;
import cc.kune.lists.shared.ListsToolConstants;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class ShareDialogPanel extends ViewImpl implements ShareDialogView {

  public static final String DIALOG_ID = "sharedialog";
  public static final String FIRSTBUTTON_ID = "sharedialog-close";

  private final BasicTopDialog dialog;
  private final Label dialogIntro;
  private final VerticalPanel vp;

  @Inject
  public ShareDialogPanel(final ShareToListView listView, final ShareToTheNetPanel shareToTheNetPanel,
      final ShareToOthersPanel shareToOthersPanel) {
    final Builder builder = new BasicTopDialog.Builder(DIALOG_ID, false, false, I18n.getDirection()).autoscroll(
        true).width(380);
    // builder.icon(icon);
    builder.firstButtonTitle(I18n.t("Close")).firstButtonId(FIRSTBUTTON_ID);
    dialog = builder.build();
    dialog.getFirstBtn().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        dialog.hide();
      }
    });
    vp = new VerticalPanel();
    dialogIntro = new Label();
    dialogIntro.addStyleName("k-dialog-intro");
    vp.add(dialogIntro);
    vp.add(listView.getView());
    vp.add(shareToOthersPanel);
    vp.add(shareToTheNetPanel);
    dialog.getInnerPanel().add(vp);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.View#asWidget()
   */
  @Override
  public Widget asWidget() {
    return dialog;
  }

  @Override
  public void hide() {
    dialog.hide();
  }

  @Override
  public boolean isVisible() {
    return dialog.isVisible();
  }

  @Override
  public void setTypeId(final String typeId) {
    String dialogTitle;
    String dialogIntroText;
    if (typeId.equals(ListsToolConstants.TYPE_LIST)) {
      dialogTitle = I18n.t("List settings");
      dialogIntroText = I18n.t("Here you can manage the members of this list");
    } else {
      dialogTitle = I18n.t("Share settings");
      dialogIntroText = I18n.t("Here you can define how others can interact with this document");
    }
    dialog.setText(dialogTitle, I18n.getDirection());
    dialogIntro.setText(dialogIntroText);
  }

  @Override
  public void show() {
    dialog.showCentered();
  }

}
