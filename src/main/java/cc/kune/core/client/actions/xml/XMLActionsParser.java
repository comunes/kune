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
package cc.kune.core.client.actions.xml;

import java.util.HashMap;
import java.util.Map;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.SubMenuDescriptor;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.errors.ErrorHandler;
import cc.kune.core.client.registry.NewMenusForTypeIdsRegistry;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.actions.xml.XMLActionsConstants;
import cc.kune.gspace.client.actions.ActionGroups;
import cc.kune.gspace.client.viewers.ContentViewerPresenter;

import com.calclab.emite.core.client.services.Services;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class XMLActionsParser.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class XMLActionsParser {

  /** The Constant SEP. */
  private static final String SEP = "»";

  /** The action registry. */
  private final ActionRegistryByType actionRegistry;

  /** The content service. */
  private final Provider<ContentServiceAsync> contentService;

  /** The content viewer. */
  private final ContentViewerPresenter contentViewer;

  /** The err handler. */
  private final ErrorHandler errHandler;

  /** The i18n. */
  private final I18nTranslationService i18n;

  /** The new menus registry. */
  private final NewMenusForTypeIdsRegistry newMenusRegistry;

  /** The session. */
  private final Session session;

  /** The state manager. */
  private final StateManager stateManager;

  /** The submenus. */
  private final HashMap<String, SubMenuDescriptor> submenus;

  /**
   * Instantiates a new xML actions parser.
   * 
   * @param errHandler
   *          the err handler
   * @param contentViewer
   *          the content viewer
   * @param actionRegistry
   *          the action registry
   * @param contentService
   *          the content service
   * @param session
   *          the session
   * @param stateManager
   *          the state manager
   * @param i18n
   *          the i18n
   * @param newMenusRegistry
   *          the new menus registry
   * @param services
   *          the services
   */
  @Inject
  public XMLActionsParser(final ErrorHandler errHandler, final ContentViewerPresenter contentViewer,
      final ActionRegistryByType actionRegistry, final Provider<ContentServiceAsync> contentService,
      final Session session, final StateManager stateManager, final I18nTranslationService i18n,
      final NewMenusForTypeIdsRegistry newMenusRegistry, final Services services) {
    this.errHandler = errHandler;
    this.contentViewer = contentViewer;
    this.actionRegistry = actionRegistry;
    this.contentService = contentService;
    this.session = session;
    this.stateManager = stateManager;
    this.i18n = i18n;
    this.newMenusRegistry = newMenusRegistry;
    submenus = new HashMap<String, SubMenuDescriptor>();

    // Based on:
    // http://www.roseindia.net/tutorials/gwt/retrieving-xml-data.shtml
    final RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET,
        XMLActionsConstants.ACTIONS_XML_LOCATION_PATH_ABS);
    try {
      requestBuilder.sendRequest(null, new RequestCallback() {
        @Override
        public void onError(final Request request, final Throwable ex) {
          onFailed(ex);
        }

        @Override
        public void onResponseReceived(final Request request, final Response response) {
          parse(new XMLKuneClientActions(services, response.getText()));
        }
      });
    } catch (final RequestException ex) {
      onFailed(ex);
    }
  }

  /**
   * Creates the menu item.
   * 
   * @param descrip
   *          the descrip
   * @param tool
   *          the tool
   * @param origTypeId
   *          the orig type id
   * @param action
   *          the action
   * @return the provider
   */
  private Provider<GuiActionDescrip> createMenuItem(final XMLGuiActionDescriptor descrip,
      final String tool, final String origTypeId, final AbstractAction action) {
    final String path = descrip.getPath();
    final MenuDescriptor menu = newMenusRegistry.get(origTypeId);
    assert menu != null;
    final SubMenuDescriptor submenu = getSubMenu(menu, tool, origTypeId, path);
    final Provider<GuiActionDescrip> menuItemProvider = new Provider<GuiActionDescrip>() {
      @Override
      public GuiActionDescrip get() {
        final MenuItemDescriptor menuItem = new MenuItemDescriptor(action);
        // Warning: getDescription returns \n we have to replace this to
        // spaces before use i18n in the tooltips (anyway tooltips are not
        // working in menu items)
        menuItem.withText(i18n.t(descrip.getDescName())).withToolTip(descrip.getDescription());
        menuItem.setParent(TextUtils.notEmpty(path) ? submenu : menu, false);
        return menuItem;
      }
    };
    return menuItemProvider;
  }

  /**
   * Gets the sub menu.
   * 
   * @param menu
   *          the menu
   * @param tool
   *          the tool
   * @param typeId
   *          the type id
   * @param parentS
   *          the parent s
   * @return the sub menu
   */
  private SubMenuDescriptor getSubMenu(final MenuDescriptor menu, final String tool,
      final String typeId, final String parentS) {
    final String[] path = parentS.split(SEP);
    SubMenuDescriptor current = null;
    for (int i = 0; i < path.length; i++) {
      final String name = path[i];
      final String subpathId = getSubPathId(typeId, path, i);
      SubMenuDescriptor subMenuDescriptor = submenus.get(subpathId);
      if (subMenuDescriptor == null) {
        final GuiActionDescrip parent = (i == 0 ? menu : submenus.get(getSubPathId(typeId, path, i - 1)));
        assert parent != null;
        subMenuDescriptor = new SubMenuDescriptor(parent, false, i18n.t(name));
        // subMenuDescriptor.setVisible(false);
        submenus.put(subpathId, subMenuDescriptor);
        actionRegistry.addAction(tool, ActionGroups.TOPBAR, subMenuDescriptor, typeId);
      }
      current = subMenuDescriptor;
    }
    assert current != null;
    return current;
  }

  /**
   * Gets the sub path id.
   * 
   * @param typeId
   *          the type id
   * @param path
   *          the path
   * @param i
   *          the i
   * @return the sub path id
   */
  private String getSubPathId(final String typeId, final String[] path, final int i) {
    final StringBuffer id = new StringBuffer().append(typeId);
    for (int j = 0; j <= i; j++) {
      id.append(SEP).append(path[j]);
    }
    return id.toString();
  }

  /**
   * On failed.
   * 
   * @param ex
   *          the ex
   */
  private void onFailed(final Throwable ex) {
    errHandler.process(ex);
  }

  /**
   * Parses the.
   * 
   * @param xml
   *          the xml
   */
  private void parse(final XMLKuneClientActions xml) {
    final Map<String, XMLWaveExtension> extensions = xml.getExtensions();
    for (final XMLGuiActionDescriptor descrip : xml.getGuiActionDescriptors()) {
      if (descrip.isEnabled()) {
        final XMLWaveExtension extension = extensions.get(descrip.getExtensionName());
        assert extension != null;
        for (final XMLTypeId typeId : descrip.getTypeIds()) {
          final String origTypeId = typeId.getOrigTypeId();
          final String tool = origTypeId.split("\\.")[0];
          final String contentIntro = descrip.getNewContentTextIntro();
          final String destTypeId = typeId.getDestTypeId();
          AbstractAction action;
          if (origTypeId.equals(destTypeId)) {
            // We are adding a gadget in a doc (nor creating a gadget in a
            // container)
            action = new AddGadgetAction(contentService, session, i18n,
                descrip.getRol().getRolRequired(), descrip.getRol().isAuthNeed(),
                extension.getExtName(), descrip.getDescName(), proxy(extension.getIconUrl(),
                    extension.getGadgetUrl()));
          } else {
            action = new NewGadgetAction(contentService, contentViewer, stateManager, session, i18n,
                descrip.getRol().getRolRequired(), descrip.getRol().isAuthNeed(),
                extension.getExtName(), destTypeId, proxy(extension.getIconUrl(),
                    extension.getGadgetUrl()), descrip.getNewContentTitle(),
                TextUtils.empty(contentIntro) ? "" : contentIntro);
          }
          final Provider<GuiActionDescrip> menuItemProvider = createMenuItem(descrip, tool, origTypeId,
              action);
          actionRegistry.addAction(tool, ActionGroups.TOPBAR, menuItemProvider, origTypeId);
        }
      }
    }
  }

  /**
   * Proxy.
   * 
   * @param iconUrl
   *          the icon url
   * @param gadgetUrl
   *          the gadget url
   * @return the string
   */
  private String proxy(final String iconUrl, final String gadgetUrl) {
    // FIXME: create a proxy servlet for that
    // More info: http://edwardstx.net/2010/06/http-proxy-servlet/
    return "https://www-ig-opensocial.googleusercontent.com/gadgets/proxy?refresh=86400&url=" + iconUrl
        + "&container=ig&gadget=" + gadgetUrl;
  }
}
