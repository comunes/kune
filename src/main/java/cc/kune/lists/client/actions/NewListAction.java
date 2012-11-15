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
package cc.kune.lists.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.actions.FieldValidationUtil;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.state.ContentCache;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.ui.dialogs.PromptTopDialog;
import cc.kune.core.client.ui.dialogs.PromptTopDialog.Builder;
import cc.kune.core.client.ui.dialogs.PromptTopDialog.OnEnter;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.gspace.client.viewers.FolderViewerPresenter;
import cc.kune.lists.client.rpc.ListsServiceAsync;
import cc.kune.lists.shared.ListsToolConstants;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class NewListAction extends RolAction {
  public static final String CANCEL_ID = "k-nla-cancel";
  public static final String CREATE_ID = "k-nla-create";
  public static final String ID = "k-nla-dialog";
  public static final String TEXTBOX_ID = "k-nla-textbox";

  private final ContentCache cache;
  private PromptTopDialog diag;
  private final FolderViewerPresenter folderViewer;
  private final I18nTranslationService i18n;
  private final Provider<ListsServiceAsync> listsService;
  private final Session session;
  private final StateManager stateManager;

  @Inject
  public NewListAction(final Session session, final StateManager stateManager,
      final I18nTranslationService i18n, final Provider<ListsServiceAsync> listsService,
      final ContentCache cache, final FolderViewerPresenter folderViewer) {
    super(AccessRolDTO.Administrator, true);
    this.session = session;
    this.stateManager = stateManager;
    this.i18n = i18n;
    this.listsService = listsService;
    this.cache = cache;
    this.folderViewer = folderViewer;
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    final Builder builder = new PromptTopDialog.Builder(ID, i18n.t("Name of the new list?"), false,
        true, i18n.getDirection(), new OnEnter() {
          @Override
          public void onEnter() {
            doAction();
          }
        });
    builder.width("300px").height("50px").firstButtonTitle(i18n.t("Create")).sndButtonTitle(
        i18n.t("Cancel")).firstButtonId(CREATE_ID).sndButtonId(CANCEL_ID).width(270);
    FieldValidationUtil.restrictToUnixName(builder, TEXTBOX_ID);
    diag = builder.build();
    diag.showCentered();
    diag.focusOnTextBox();
    diag.getSecondBtn().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        diag.hide();
      }
    });
    diag.getFirstBtn().addClickHandler(new ClickHandler() {

      @Override
      public void onClick(final ClickEvent event) {
        doAction();
      }
    });

  }

  private void doAction() {
    if (diag.isValid()) {
      NotifyUser.showProgress();
      diag.hide();
      listsService.get().createList(session.getUserHash(), session.getCurrentStateToken(),
          diag.getTextFieldValue(), ListsToolConstants.TYPE_LIST, true,
          new AsyncCallbackSimple<StateContainerDTO>() {
            @Override
            public void onSuccess(final StateContainerDTO state) {
              stateManager.setRetrievedStateAndGo(state);
              NotifyUser.hideProgress();

              NotifyUser.info(i18n.t("List created"));
              folderViewer.highlightTitle();
            }
          });
      cache.remove(session.getCurrentStateToken());
    }
  }

}
