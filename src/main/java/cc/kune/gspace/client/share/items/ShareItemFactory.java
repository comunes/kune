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

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class ShareItemFactory {
  @Inject
  private static Provider<ContentEditableShareItemUi> contentEditable;
  @Inject
  private static Provider<ContentRolShareItemUi> contentRol;
  @Inject
  private static Provider<ContentVisibleShareItemUi> contentVisible;
  @Inject
  private static Provider<ListPublicShareItemUi> listPublic;
  @Inject
  private static Provider<ListRolShareItemUi> listRol;
  @Inject
  private static Provider<ShareItemOfOwner> owner;
  @Inject
  private static Provider<ShareItemOfParticipant> participant;

  public static ContentEditableShareItemUi getContentEditableByAnyone() {
    return contentEditable.get();
  }

  public static ContentRolShareItemUi createContentItem() {
    return contentRol.get();
  }

  public static ContentVisibleShareItemUi getContentVisibleByAnyone() {
    return contentVisible.get();
  }

  public static ListPublicShareItemUi getListPublicByAnyone() {
    return listPublic.get();
  }

  public static ListRolShareItemUi createListItem() {
    return listRol.get();
  }

  public static ShareItemOfOwner getOwner() {
    return owner.get();
  }

  public static ShareItemOfParticipant getParticipant() {
    return participant.get();
  }

  public ShareItemFactory() {
  }
}
