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

package cc.kune.core.client.actions;

import static org.junit.Assert.*;

import org.junit.Test;

import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.dto.AccessRolDTO;

public final class RolActionHelperTest {

  private AccessRights ar(final boolean administrable, final boolean editable, final boolean visible) {
    return new AccessRights(administrable, editable, visible);
  }

  @Test
  public void shouldAddActionForEditorWithHigherRolEditor() {
    assertTrue(RolActionHelper.mustAdd(AccessRolDTO.Viewer, AccessRolDTO.Editor, false, false,
        ar(false, true, true)));
  }

  @Test
  public void shouldAddActionForViewerWithHigherRolEditor() {
    assertTrue(RolActionHelper.mustAdd(AccessRolDTO.Viewer, AccessRolDTO.Editor, false, false,
        ar(false, false, true)));
  }

  @Test
  public void shouldNotAddActionForAdminWithHigherRolEditor() {
    assertFalse(RolActionHelper.mustAdd(AccessRolDTO.Viewer, AccessRolDTO.Editor, false, false,
        ar(true, true, true)));
  }

  @Test
  public void shouldNotAddActionForNotLoggedViewer() {
    assertFalse(RolActionHelper.mustAdd(AccessRolDTO.Viewer, AccessRolDTO.Editor, true, false,
        ar(false, false, true)));
  }

  @Test
  public void shouldNotAddActionForViewerWithHigherRolEditor() {
    assertFalse(RolActionHelper.mustAdd(AccessRolDTO.Viewer, AccessRolDTO.Editor, false, false,
        ar(false, false, false)));
  }

}
