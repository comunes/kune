/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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

package cc.kune.wave.client;

import org.waveprotocol.box.webclient.search.Digest;
import org.waveprotocol.box.webclient.search.DigestView;
import org.waveprotocol.box.webclient.search.SearchPanelView;
import org.waveprotocol.box.webclient.search.SearchView;
import org.waveprotocol.wave.client.widget.toolbar.GroupingToolbar.View;

public class DummySearchPanelView implements SearchPanelView {

  @Override
  public void clearDigests() {
  }

  @Override
  public DigestView getFirst() {
    return null;
  }

  @Override
  public DigestView getLast() {
    return null;
  }

  @Override
  public DigestView getNext(final DigestView arg0) {
    return null;
  }

  @Override
  public DigestView getPrevious(final DigestView arg0) {
    return null;
  }

  @Override
  public SearchView getSearch() {
    return null;
  }

  @Override
  public View getToolbar() {
    return null;
  }

  @Override
  public void init(final Listener arg0) {
  }

  @Override
  public DigestView insertAfter(final DigestView arg0, final Digest arg1) {
    return null;
  }

  @Override
  public DigestView insertBefore(final DigestView arg0, final Digest arg1) {
    return null;
  }

  @Override
  public void reset() {
  }

  @Override
  public void setShowMoreVisible(final boolean arg0) {
  }

  @Override
  public void setTitleText(final String arg0) {
  }

}
