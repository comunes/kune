/*******************************************************************************
 * Copyright (C) 2007, 2013 The kune development team (see CREDITS for details)
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
 *******************************************************************************/

package cc.kune.gspace.client.share.items;

import cc.kune.common.client.actions.ui.ActionSimplePanel;
import cc.kune.common.client.resources.CommonResources;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.gspace.client.share.items.actions.ContentNoVisibleShareItemDescriptor;
import cc.kune.gspace.client.share.items.actions.ContentNoVisibleShareItemDescriptor.MakeContentVisibleMenuItem;
import cc.kune.gspace.client.share.items.actions.ContentVisibleShareItemDescriptor;
import cc.kune.gspace.client.share.items.actions.ContentVisibleShareItemDescriptor.MakeContentNoVisibleMenuItem;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ContentVisibleShareItemUi extends AbstractShareItemWithMenuUi {

  private final ShareItemDescriptor contentNoVisibleDescr;
  private final ShareItemDescriptor contentVisibleDescr;

  @Inject
  public ContentVisibleShareItemUi(final ActionSimplePanel actionsPanel,
      final ClientFileDownloadUtils downloadUtils, final CommonResources res,
      final IconicResources icons, final ContentVisibleShareItemDescriptor contentVisibleDescr,
      final ContentNoVisibleShareItemDescriptor contentNoVisibleDesc,
      final MakeContentNoVisibleMenuItem makeContentNoVisibleMenuItem,
      final MakeContentVisibleMenuItem makeContentVisibleMenuItem) {
    super(actionsPanel, downloadUtils, res);
    this.contentNoVisibleDescr = contentNoVisibleDesc;
    this.contentVisibleDescr = contentVisibleDescr;
    // When action is performed we replace this UI item with new values
    this.contentNoVisibleDescr.setTarget(this);
    this.contentVisibleDescr.setTarget(this);
    makeContentVisibleMenuItem.onPerformNewDescriptor(this.contentVisibleDescr);
    makeContentNoVisibleMenuItem.onPerformNewDescriptor(this.contentNoVisibleDescr);
  }

  public AbstractShareItemWithMenuUi with(final boolean isVisible) {
    setValuesViaDescriptor(isVisible ? contentVisibleDescr : contentNoVisibleDescr);
    return this;
  }
}
