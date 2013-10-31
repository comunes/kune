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
import cc.kune.gspace.client.share.items.actions.ListNoPublicShareItemDescriptor;
import cc.kune.gspace.client.share.items.actions.ListNoPublicShareItemDescriptor.MakeListPublicMenuItem;
import cc.kune.gspace.client.share.items.actions.ListPublicShareItemDescriptor;
import cc.kune.gspace.client.share.items.actions.ListPublicShareItemDescriptor.MakeListNonPublicMenuItem;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ListPublicShareItemUi extends AbstractShareItemWithMenuUi {

  private final ShareItemDescriptor isNonPublicDescr;
  private final ShareItemDescriptor isPublicDescr;

  @Inject
  public ListPublicShareItemUi(final ActionSimplePanel actionsPanel,
      final ClientFileDownloadUtils downloadUtils, final CommonResources res,
      final IconicResources icons, final ListPublicShareItemDescriptor listPublicDescr,
      final ListNoPublicShareItemDescriptor publicDesc,
      final MakeListNonPublicMenuItem makeListNotPublic, final MakeListPublicMenuItem makeListPublic) {
    super(actionsPanel, downloadUtils, res);
    isPublicDescr = publicDesc;
    isNonPublicDescr = listPublicDescr;
    isPublicDescr.setTarget(this);
    isNonPublicDescr.setTarget(this);
    makeListPublic.setComplementary(isNonPublicDescr);
    makeListNotPublic.setComplementary(isPublicDescr);
  }

  public AbstractShareItemWithMenuUi with(final boolean isPublic) {
    setValuesViaDescriptor(isPublic ? isPublicDescr : isNonPublicDescr);
    return this;
  }
}
