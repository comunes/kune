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
package cc.kune.gspace.client.viewers;

import static cc.kune.gspace.client.viewers.PathToolbarUtils.CSSBTN;
import static cc.kune.gspace.client.viewers.PathToolbarUtils.CSSBTNC;
import static cc.kune.gspace.client.viewers.PathToolbarUtils.CSSBTNL;
import static cc.kune.gspace.client.viewers.PathToolbarUtils.CSSBTNR;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import cc.kune.core.client.registry.ContentCapabilitiesRegistry;

public class FolderViewerPresenterTest {

  private PathToolbarUtils presenter;

  @Before
  public void before() {
    presenter = new PathToolbarUtils(null, null, Mockito.mock(ContentCapabilitiesRegistry.class), null,
        null);
  }

  @Test
  public void with1() {
    assertEquals(CSSBTN, presenter.calculateStyle(0, 1));
  }

  @Test
  public void with2() {
    assertEquals(CSSBTNL, presenter.calculateStyle(0, 2));
    assertEquals(CSSBTNR, presenter.calculateStyle(1, 2));
  }

  @Test
  public void with3() {
    assertEquals(CSSBTNL, presenter.calculateStyle(0, 3));
    assertEquals(CSSBTNC, presenter.calculateStyle(1, 3));
    assertEquals(CSSBTNR, presenter.calculateStyle(2, 3));
  }

  @Test
  public void with4() {
    assertEquals(CSSBTNL, presenter.calculateStyle(0, 4));
    assertEquals(CSSBTNC, presenter.calculateStyle(1, 4));
    assertEquals(CSSBTNC, presenter.calculateStyle(2, 4));
    assertEquals(CSSBTNR, presenter.calculateStyle(3, 4));
  }
}
