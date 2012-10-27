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
package cc.kune.common.client.actions.ui.descrip;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.Action;

public class PushButtonDescriptor extends ButtonDescriptor {

  public static final String PUSHED = "pushed";

  public PushButtonDescriptor(final AbstractAction action) {
    super(action);
    setPushedImpl(false);
  }

  public PushButtonDescriptor(final PushButtonDescriptor button) {
    this(button.getAction());
  }

  public PushButtonDescriptor(final String text, final AbstractAction action) {
    this(action);
    putValue(Action.NAME, text);
  }

  @Override
  public Class<?> getType() {
    return PushButtonDescriptor.class;
  }

  public boolean isPushed() {
    return (Boolean) getValue(PUSHED);
  }

  public void setPushed(final boolean pushed) {
    setPushedImpl(pushed);
  }

  private void setPushedImpl(final boolean pushed) {
    putValue(PUSHED, pushed);
  }
}
