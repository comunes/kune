/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.common.client.actions.gwtui;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuItem;

public class GwtBaseMenuItem extends MenuItem {

  private static Command dummyCommand() {
    return new Command() {
      @Override
      public void execute() {
      }
    };
  }

  public GwtBaseMenuItem() {
    super("", dummyCommand());
  }

  public GwtBaseMenuItem(final String text) {
    super(text, dummyCommand());
  }

  public GwtBaseMenuItem(final String text, final boolean asHtml) {
    super(text, asHtml, dummyCommand());
  }

}
