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
package cc.kune.gspace.client.share.items.actions;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.shared.utils.SimpleCallback;
import cc.kune.gspace.client.share.items.AbstractShareItemWithMenuUi;
import cc.kune.gspace.client.share.items.ShareItemDescriptor;

public abstract class AbstractToggleShareItemAction extends AbstractExtendedAction {

  private ShareItemDescriptor onPerformShareItem;
  protected final SimpleCallback onSuccess;
  protected final boolean value;

  AbstractToggleShareItemAction(final boolean value) {
    this.value = value;
    onSuccess = new SimpleCallback() {
      @Override
      public void onCallback() {
        // On action success in server
        final AbstractShareItemWithMenuUi itemUi = getTarget();
        if (itemUi != null && onPerformShareItem != null) {
          itemUi.setValuesViaDescriptor(onPerformShareItem);
        }
      }
    };
  }

  protected AbstractShareItemWithMenuUi getTarget() {
    return (AbstractShareItemWithMenuUi) getValue(ShareItemDescriptor.ITEM);
  }

  public void init(final ShareItemDescriptor onPerformShareItem) {
    this.onPerformShareItem = onPerformShareItem;
  }

}
