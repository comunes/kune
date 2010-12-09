/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
 \*/
package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.platf.client.PlatfMessages;
import org.ourproject.kune.platf.client.actions.ActionItem;
import org.ourproject.kune.platf.client.actions.ActionItemCollection;
import org.ourproject.kune.platf.client.actions.ActionManager;
import org.ourproject.kune.platf.client.actions.MenuItemsContainer;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbarPanel;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbarView;
import org.ourproject.kune.platf.client.ui.AbstractToolbar;
import org.ourproject.kune.platf.client.ui.BasicThumb;
import org.ourproject.kune.workspace.client.skel.SummaryPanel;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import cc.kune.core.shared.dto.UserSimpleDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.suco.client.events.Listener;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.menu.Menu;

public class BuddiesSummaryPanel extends SummaryPanel implements BuddiesSummaryView {

    private final MenuItemsContainer<UserSimpleDTO> menuItemsContainer;
    private final FlowPanel flowPanel;
    private final Label otherBuddiesLabel;
    private final I18nTranslationService i18n;
    private final ActionManager actionManager;
    private final AbstractToolbar toolbar;
    private final Label noBuddiesPublic;

    public BuddiesSummaryPanel(final BuddiesSummaryPresenter presenter, final WorkspaceSkeleton ws,
            final I18nTranslationService i18n, final ActionManager actionManager,
            final ActionToolbarView<UserSimpleDTO> actionToolbarView) {
        super(i18n.t("Buddies"), i18n.t("This user buddies"), ws);
        this.actionManager = actionManager;
        menuItemsContainer = new MenuItemsContainer<UserSimpleDTO>();
        this.i18n = i18n;
        final VerticalPanel vp = new VerticalPanel();
        flowPanel = new FlowPanel();
        otherBuddiesLabel = new Label();
        otherBuddiesLabel.addStyleName("kune-Margin-Small-trbl");
        vp.add(flowPanel);
        vp.add(otherBuddiesLabel);
        toolbar = ((ActionToolbarPanel<UserSimpleDTO>) actionToolbarView).getToolbar();
        toolbar.setBlankStyle();
        vp.add((Widget) toolbar);
        super.add(vp);
        addInSummary();
        noBuddiesPublic = new Label(i18n.t(PlatfMessages.BUDDIES_NOT_PUBLIC));
        noBuddiesPublic.addStyleName("kune-Margin-7-trbl");
        noBuddiesPublic.addStyleName("k-text-gray");
    }

    public void addBuddie(final UserSimpleDTO user, final ActionItemCollection<UserSimpleDTO> actionCollection,
            final String avatarUrl, final String tooltipTitle, final String tooltip) {
        final ClickHandler listener = new ClickHandler() {
            public void onClick(final ClickEvent event) {
                final Element sender = event.getRelativeElement();
                final Menu menu = menuItemsContainer.get(user.getShortName());
                if (menu.getItems().length > 0) {
                    menu.showAt(sender.getAbsoluteLeft(), sender.getAbsoluteTop() + 5);
                }
            }
        };
        menuItemsContainer.createItemMenu(user.getShortName(), actionCollection,
                new Listener<ActionItem<UserSimpleDTO>>() {
                    public void onEvent(final ActionItem<UserSimpleDTO> actionItem) {
                        doAction(actionItem);
                    }
                });

        final BasicThumb thumb = new BasicThumb(avatarUrl.equals(NOAVATAR) ? "images/persons/person2-32.png"
                : avatarUrl, AVATARSIZE, user.getShortName(), AVATARLABELMAXSIZE, false, listener);
        thumb.setTooltip(tooltipTitle, tooltip);
        flowPanel.add(thumb);
    }

    @Override
    public void clear() {
        flowPanel.clear();
        clearOtherUsers();
        menuItemsContainer.clear();
        toolbar.removeAll();
        super.doLayoutIfNeeded();
    }

    public void clearOtherUsers() {
        otherBuddiesLabel.setText("");
    }

    public void setNoBuddies() {
        otherBuddiesLabel.setText(i18n.t("This user has no buddies"));
    }

    public void setOtherUsers(final String text) {
        otherBuddiesLabel.setText(text);
    }

    public void showBuddiesNotVisible() {
        flowPanel.add(noBuddiesPublic);
        super.doLayoutIfNeeded();
    }

    private void doAction(final ActionItem<UserSimpleDTO> actionItem) {
        actionManager.doAction(actionItem);
    }
}
