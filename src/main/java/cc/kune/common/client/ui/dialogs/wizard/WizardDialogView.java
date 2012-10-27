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
package cc.kune.common.client.ui.dialogs.wizard;

import com.google.gwt.user.client.ui.IsWidget;

public interface WizardDialogView {

  void add(IsWidget view);

  void clear();

  void hide();

  boolean isCurrentPage(IsWidget view);

  void mask(final String message);

  void maskProcessing();

  void remove(IsWidget view);

  void setEnabled(boolean back, boolean next, boolean cancel, boolean finish);

  void setEnabledBackButton(final boolean enabled);

  void setEnabledCancelButton(final boolean enabled);

  void setEnabledFinishButton(final boolean enabled);

  void setEnabledNextButton(final boolean enabled);

  void setFinishText(final String text);

  void setListener(WizardListener listener);

  void setVisible(boolean back, boolean next, boolean cancel, boolean finish);

  void setVisibleBackButton(final boolean visible);

  void setVisibleCancelButton(final boolean visible);

  void setVisibleFinishButton(final boolean visible);

  void setVisibleNextButton(final boolean visible);

  void show();

  void show(IsWidget view);

  void unMask();

}
