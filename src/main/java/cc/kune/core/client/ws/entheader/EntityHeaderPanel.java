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
package cc.kune.core.client.ws.entheader;

import org.gwtbootstrap3.client.ui.html.Text;

import br.com.rpa.client._coreelements.CoreIconButton;
import cc.kune.common.client.actions.ui.GuiProvider;
import cc.kune.common.client.actions.ui.IsActionExtensible;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.ui.WrappedFlowPanel;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.avatar.MediumAvatarDecorator;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.client.sn.GroupMembersUpdatedEvent;
import cc.kune.core.client.sn.UserFollowersUpdatedEvent;
import cc.kune.core.client.ws.entheader.EntityHeaderPresenter.EntityHeaderView;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.gspace.client.armor.GSpaceArmor;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * The Class EntityHeaderPanel.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class EntityHeaderPanel extends ViewImpl implements EntityHeaderView {

  private final MediumAvatarDecorator decorator;
  /** The download provider. */
  private final Provider<ClientFileDownloadUtils> downloadProvider;
  private final CoreIconButton followersBtn;

  private final Text groupLongName;
  /** The images. */
  private final CoreResources images;
  private final Image logo;
  private final Element logoShadow;
  private final Anchor shortNameAnchor;
  /** The toolbar. */
  private final IsActionExtensible toolbar;

  /**
   * Instantiates a new entity header panel.
   *
   * @param downloadProvider
   *          the download provider
   * @param images
   *          the images
   * @param bindings
   *          the bindings
   * @param armor
   *          the armor
   * @param entityTextLogo
   *          the entity text logo
   * @param i18n
   *          the i18n
   */
  @Inject
  public EntityHeaderPanel(final EventBus eventBus,
      final Provider<ClientFileDownloadUtils> downloadProvider, final CoreResources images,
      final GuiProvider bindings, final GSpaceArmor armor, final I18nTranslationService i18n,
      final MediumAvatarDecorator decorator) {
    this.downloadProvider = downloadProvider;
    this.images = images;
    this.decorator = decorator;
    toolbar = armor.getHeaderActionsTopToolbar();

    logo = armor.getGroupLogo();
    logo.removeFromParent();
    final WrappedFlowPanel groupNamePanel = armor.getGroupName();
    groupLongName = new Text();
    groupNamePanel.add(groupLongName);
    logoShadow = armor.getLogoShadow();
    final WrappedFlowPanel shorNamePanel = armor.getGroupShortName();
    shortNameAnchor = new Anchor();
    shorNamePanel.add(shortNameAnchor);
    decorator.setWidget(logo);
    logoShadow.appendChild(((Widget) decorator).getElement());
    followersBtn = (CoreIconButton) armor.getFollowersButton();
    eventBus.addHandler(GroupMembersUpdatedEvent.getType(),
        new GroupMembersUpdatedEvent.GroupMembersUpdatedHandler() {
      @Override
      public void onGroupMembersUpdated(final GroupMembersUpdatedEvent event) {
        final int members = event.getMembers();
        followersBtn.setText(I18n.t(members == 1 ? "[%d] member" : "[%d] members", members));
      }
    });
    eventBus.addHandler(UserFollowersUpdatedEvent.getType(),
        new UserFollowersUpdatedEvent.UserFollowersUpdatedHandler() {
      @Override
      public void onUserFollowersUpdated(final UserFollowersUpdatedEvent event) {
        final int followers = event.getFollowers();
            // followersBtn.setText(I18n.t(followers == 1 ? "One follower" :
            // "[%d] followers", followers));
        followersBtn.setText(I18n.t(followers == 1 ? "One buddy" : "[%d] buddies", followers));
      }
    });
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.ws.entheader.EntityHeaderPresenter.EntityHeaderView
   * #addAction(cc.kune.common.client.actions.ui.descrip.GuiActionDescrip)
   */
  @Override
  public void addAction(final GuiActionDescrip descriptor) {
    toolbar.add(descriptor);
  }

  @Override
  public void addAll(final GuiActionDescCollection actionsRegistry) {
    toolbar.addAll(actionsRegistry);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.View#asWidget()
   */
  @Override
  public Widget asWidget() {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.ws.entheader.EntityHeaderPresenter.EntityHeaderView
   * #setLogoImage(cc.kune.core.shared.dto.GroupDTO, boolean)
   */
  @Override
  public void setLogoImage(final GroupDTO group) {
    final String shortName = group.getShortName();
    final String url = downloadProvider.get().getLogoImageUrl(shortName);
    logo.setUrl(url);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.ws.entheader.EntityHeaderPresenter.EntityHeaderView
   * #setLogoImageVisible(boolean)
   */
  @Override
  public void setLogoImageVisible(final boolean visible) {
    logo.setVisible(visible);
    logoShadow.getStyle().setDisplay(visible ? Display.BLOCK : Display.NONE);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.ws.entheader.EntityHeaderPresenter.EntityHeaderView
   * #setLogoText(java.lang.String)
   */
  @Override
  public void setLogoText(final String groupName, final String groupShortName) {
    groupLongName.setText(groupName);
    shortNameAnchor.setText("#" + groupShortName);
    shortNameAnchor.setHref("#!" + groupShortName);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.ws.entheader.EntityHeaderPresenter.EntityHeaderView
   * #setOnlineStatusGroup(java.lang.String)
   */
  @Override
  public void setOnlineStatusGroup(final String group) {
    decorator.setItem(group);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.ws.entheader.EntityHeaderPresenter.EntityHeaderView
   * #setOnlineStatusVisible(boolean)
   */
  @Override
  public void setOnlineStatusVisible(final boolean visible) {
    decorator.setDecoratorVisible(visible);
  }

  @Override
  public void toolbarClear() {
    toolbar.clear();
  }

}
