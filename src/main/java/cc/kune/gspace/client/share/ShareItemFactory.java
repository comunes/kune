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

package cc.kune.gspace.client.share;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class ShareItemFactory {

  @Inject
  private static Provider<ShareItemOfAdmin> admin;
  @Inject
  private static Provider<ShareItemEditableByAnyone> editableByAnyone;
  @Inject
  private static Provider<ShareItemOfEditor> editor;
  @Inject
  private static Provider<ShareItemNotEditableByOthers> notEditableByOthers;
  @Inject
  private static Provider<ShareItemNotVisibleByOthers> notVisibleByOthers;
  @Inject
  private static Provider<ShareItemOfOwner> owner;
  @Inject
  private static Provider<ShareItemOfViewer> viewer;
  @Inject
  private static Provider<ShareItemVisibleByAnyone> visibleByAnyone;

  public static ShareItemOfAdmin getAdmin() {
    return admin.get();
  }

  public static ShareItemOfEditor getEditor() {
    return editor.get();
  }

  public static ShareItemOfOwner getOwner() {
    return owner.get();
  }

  public static ShareItemEditableByAnyone getShareItemEditableByAnyone() {
    return editableByAnyone.get();
  }

  public static ShareItemNotEditableByOthers getShareItemNotEditableByOthers() {
    return notEditableByOthers.get();
  }

  public static ShareItemNotVisibleByOthers getShareItemNotVisibleByOthers() {
    return notVisibleByOthers.get();
  }

  public static ShareItemVisibleByAnyone getShareItemVisibleByAnyone() {
    return visibleByAnyone.get();
  }

  public static ShareItemOfViewer getViewer() {
    return viewer.get();
  }

  public ShareItemFactory() {
  }
}
