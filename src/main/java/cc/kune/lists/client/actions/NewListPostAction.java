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
import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.auth.SignIn;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.state.ContentCache;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.TokenUtils;
import cc.kune.core.client.ui.dialogs.PromptTopDialog;
import cc.kune.core.client.ui.dialogs.PromptTopDialog.Builder;
import cc.kune.core.client.ui.dialogs.PromptTopDialog.OnEnter;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.gspace.client.viewers.FolderViewerPresenter;
import cc.kune.lists.client.rpc.ListsServiceAsync;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class NewListPostAction extends RolAction {
  public static final String CANCEL_ID = "k-nlistpa-cancel";
  public static final String CREATE_ID = "k-nlistpa-create";
  public static final String ID = "k-nlistpa-dialog";
  public static final String TEXTBOX_ID = "k-nlistpa-textbox";

  private final ContentCache cache;
  private PromptTopDialog diag;
  private final FolderViewerPresenter folderViewer;
  private final I18nTranslationService i18n;
  private final Provider<ListsServiceAsync> listsService;
  private final Session session;
  private final Provider<SignIn> signIn;
  private final StateManager stateManager;

  @Inject
  public NewListPostAction(final Session session, final StateManager stateManager,
      final I18nTranslationService i18n, final Provider<ListsServiceAsync> listsService,
      final ContentCache cache, final FolderViewerPresenter folderViewer, final Provider<SignIn> signIn) {
    super(AccessRolDTO.Viewer, false);
    this.session = session;
    this.stateManager = stateManager;
    this.i18n = i18n;
    this.listsService = listsService;
    this.cache = cache;
    this.folderViewer = folderViewer;
    this.signIn = signIn;
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    if (session.isLogged()) {
      final Builder builder = new PromptTopDialog.Builder(ID, i18n.t("Title of the new post?"), false,
          true, i18n.getDirection(), new OnEnter() {
            @Override
            public void onEnter() {
              doAction();
            }
          });
      builder.width("300px").height("50px").firstButtonTitle(i18n.t("Post")).sndButtonTitle(
          i18n.t("Cancel")).firstButtonId(CREATE_ID).sndButtonId(CANCEL_ID).width(270);
      builder.textboxId(TEXTBOX_ID);
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
    } else {
      signIn.get().setErrorMessage(i18n.t("Sign in or create an account to susbscribe to this list"),
          NotifyLevel.info);
      stateManager.gotoHistoryToken(TokenUtils.addRedirect(SiteTokens.SIGN_IN,
          session.getCurrentStateToken().toString()));
    }
  }

  private void doAction() {
    if (diag.isValid()) {
      NotifyUser.showProgress();
      diag.hide();
      listsService.get().newPost(session.getUserHash(),
          session.getCurrentStateToken().copy().clearDocument(), diag.getTextFieldValue(),
          new AsyncCallbackSimple<StateContentDTO>() {
            @Override
            public void onSuccess(final StateContentDTO state) {
              stateManager.setRetrievedStateAndGo(state);
              NotifyUser.hideProgress();
              NotifyUser.info(i18n.t("Post created. Edit it"));
              folderViewer.highlightTitle();
            }
          });
      cache.remove(session.getCurrentStateToken());
    }
  }

}
