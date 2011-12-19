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
package cc.kune.core.client.ui.dialogs;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.ui.MaskWidget;
import cc.kune.common.client.ui.dialogs.wizard.WizardDialog;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.ui.DefaultForm;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class WizardFormDialog extends WizardDialog {

  public WizardFormDialog(final String dialogId, final String header, final boolean modal,
      final boolean minimizable, final String width, final String height, final String backId,
      final String nextId, final String finishId, final String cancelId, final String closeId,
      final I18nTranslationService i18n, final MaskWidget maskWidget) {
    super(dialogId, header, modal, minimizable, width, height, backId, nextId, finishId, cancelId,
        closeId, i18n, maskWidget);
  }

  @Override
  protected int getWidgetCount(final IsWidget view) {
    if (view instanceof Widget) {
      return deck.getWidgetIndex((Widget) view);
    } else if (view instanceof DefaultForm) {
      return deck.getWidgetIndex(((DefaultForm) view).getFormPanel());
    }
    return -1;
  }

  @Override
  protected Widget toWidget(final IsWidget view) {
    if (view instanceof Widget) {
      return (Widget) view;
    } else if (view instanceof DefaultForm) {
      return ((DefaultForm) view).getFormPanel();
    } else {
      NotifyUser.error("Trying to add a unknown element in WizardDialog");
      return null;
    }
  }
}
