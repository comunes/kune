/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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

// TODO: Auto-generated Javadoc
/**
 * The Class NewListPostAction.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class NewListPostAction extends RolAction {

  /** The Constant CANCEL_ID. */
  public static final String CANCEL_ID = "k-nlistpa-cancel";

  /** The Constant CREATE_ID. */
  public static final String CREATE_ID = "k-nlistpa-create";

  /** The Constant ID. */
  public static final String ID = "k-nlistpa-dialog";

  /** The Constant TEXTBOX_ID. */
  public static final String TEXTBOX_ID = "k-nlistpa-textbox";

  /** The cache. */
  private final ContentCache cache;

  /** The diag. */
  private PromptTopDialog diag;

  /** The folder viewer. */
  private final FolderViewerPresenter folderViewer;

  /** The i18n. */
  private final I18nTranslationService i18n;

  /** The lists service. */
  private final Provider<ListsServiceAsync> listsService;

  /** The session. */
  private final Session session;

  /** The sign in. */
  private final Provider<SignIn> signIn;

  /** The state manager. */
  private final StateManager stateManager;

  /**
   * Instantiates a new new list post action.
   *
   * @param session
   *          the session
   * @param stateManager
   *          the state manager
   * @param i18n
   *          the i18n
   * @param listsService
   *          the lists service
   * @param cache
   *          the cache
   * @param folderViewer
   *          the folder viewer
   * @param signIn
   *          the sign in
   */
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

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.common
   * .client.actions.ActionEvent)
   */
  @Override
  public void actionPerformed(final ActionEvent event) {
    if (session.isLogged()) {
      final PromptTopDialog.Builder builder = new PromptTopDialog.Builder(ID, i18n.t("Title of the new post?"), false,
          true, i18n.getDirection(), new OnEnter() {
            @Override
            public void onEnter() {
              doAction();
            }
          });
      builder.width("320px").height("50px").firstButtonTitle(i18n.t("Post")).sndButtonTitle(
          i18n.t("Cancel")).firstButtonId(CREATE_ID).sndButtonId(CANCEL_ID);
      builder.textFieldWidth(270);
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

  /**
   * Do action.
   */
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
