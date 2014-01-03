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
package cc.kune.core.client.ws;

import cc.kune.common.client.resources.CommonResources;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.impl.StateManagerDefault;
import cc.kune.gspace.client.armor.GSpaceArmorDefault;
import cc.kune.gspace.client.armor.resources.GSpaceArmorResources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class CoreView is where the general armor of Kune it created/attached.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class CoreViewImpl extends ViewImpl implements CorePresenter.CoreView {

  /** The armor. */
  private final GSpaceArmorDefault armor;

  /**
   * Instantiates a new core view.
   * 
   * @param armor
   *          the body
   * @param stateManager
   *          the state manager
   */
  @Inject
  public CoreViewImpl(final GSpaceArmorDefault armor, final StateManagerDefault stateManager) {
    this.armor = armor;
    CommonResources.INSTANCE.commonStyle().ensureInjected();
    GWT.<CoreResources> create(CoreResources.class).coreCss().ensureInjected();
    GWT.<GSpaceArmorResources> create(GSpaceArmorResources.class).style().ensureInjected();
    History.addValueChangeHandler(stateManager);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.View#asWidget()
   */
  @Override
  public Widget asWidget() {
    return armor;
  }
}
