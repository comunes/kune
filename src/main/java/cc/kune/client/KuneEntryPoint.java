/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
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
package cc.kune.client;

import cc.kune.common.client.utils.MetaUtils;
import cc.kune.common.client.utils.WindowUtils;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.state.SiteParameters;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.gwtplatform.mvp.client.DelayedBindRegistry;

/**
 * The KuneEntryPoint is used to start kune complete client
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class KuneEntryPoint extends AbstractKuneEntryPoint {

  /** The Constant HOME_IDS_DEF_SUFFIX. */
  protected static final String HOME_IDS_DEF_SUFFIX = "-def";

  /** The Constant HOME_IDS_PREFIX. */
  protected static final String HOME_IDS_PREFIX = "k-home-";

  /** The ginjector. */
  KuneGinjector ginjector;

  /**
   * On module load continue.
   */
  @Override
  protected void onContinueModuleLoad() {
    ginjector.getSpinerPresenter();

    ginjector.getSessionExpirationManager();
    ginjector.getEventBusWithLogger();
    ginjector.getErrorsDialog();
    ginjector.getCorePresenter().get().forceReveal();
    ginjector.getOnAppStartFactory();
    ginjector.getSessionChecker().check(new AsyncCallbackSimple<Void>() {
      @Override
      public void onSuccess(final Void result) {
        // Do nothing
      }
    });

    ginjector.getStateManager();
    ginjector.getGwtGuiProvider();
    ginjector.getGroupMembersPresenter();

    /* Tools (order in GUI) */
    ginjector.getDocsParts();
    ginjector.getBlogsParts();
    ginjector.getWikiParts();
    ginjector.getEventsParts();
    ginjector.getTasksParts();
    ginjector.getListsParts();
    ginjector.getChatParts();
    ginjector.getBartersParts();
    ginjector.getTrashParts();

    ginjector.getSiteLogo();
    ginjector.getChatClient();
    ginjector.getCoreParts();
    ginjector.getGSpaceParts();
    ginjector.getPSpaceParts();
    ginjector.getHSpaceParts();

    ginjector.getXmlActionsParser();
    ginjector.getContentViewerSelector().init();

    ginjector.getGlobalShortcutRegister().enable();
  }

  /**
   * On start module load.
   */
  @Override
  protected void onStartModuleLoad() {
    setHomeLocale();

    ginjector = GWT.create(KuneGinjector.class);
    DelayedBindRegistry.bind(ginjector);
  }

  /**
   * Home set locale. In ws.html there is some no visible elements with the
   * different locales and we only show the current locale
   */
  private void setHomeLocale() {
    final String currentLocale = WindowUtils.getParameter(SiteParameters.LOCALE);

    final String meta = MetaUtils.get("kune.home.ids");
    if (meta != null) {
      final String[] ids = meta.split(",[ ]*");

      for (final String id : ids) {
        final RootPanel someElement = RootPanel.get(HOME_IDS_PREFIX + id + "-" + currentLocale);
        final RootPanel defElement = RootPanel.get(HOME_IDS_PREFIX + id + HOME_IDS_DEF_SUFFIX);
        if (someElement != null) {
          someElement.setVisible(true);
        } else if (defElement != null) {
          defElement.setVisible(true);
        }
      }
    }
  }

}
