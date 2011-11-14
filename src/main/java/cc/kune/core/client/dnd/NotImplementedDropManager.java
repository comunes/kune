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
package cc.kune.core.client.dnd;

import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.GSpaceArmor;

import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class NotImplementedDropManager {

  private final KuneDragController dragController;
  private final I18nTranslationService i18n;

  @Inject
  public NotImplementedDropManager(final I18nTranslationService i18n, final GSpaceArmor gSpaceArmor,
      final KuneDragController dragController) {
    this.i18n = i18n;
    this.dragController = dragController;
    registerImpl((FlowPanel) gSpaceArmor.getEntityHeader());
  }

  public void register(final Widget widget) {
    registerImpl(widget);
  }

  private void registerImpl(final Widget widget) {
    final NotImplementedDropController dropController = new NotImplementedDropController(widget, i18n);
    dragController.registerDropController(dropController);
    widget.addAttachHandler(new Handler() {
      @Override
      public void onAttachOrDetach(final AttachEvent event) {
        if (!event.isAttached()) {
          dragController.unregisterDropController(dropController);
        }
      }
    });
  }
}
