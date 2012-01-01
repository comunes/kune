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
package cc.kune.gspace.client.style;

import javax.annotation.Nonnull;

import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.gspace.client.GSpaceArmor;

import com.google.inject.Inject;

public class GSpaceBackManagerImpl implements GSpaceBackManager {

  private static final StateToken NO_TOKEN = new StateToken("none.none.0.0");
  private final ClientFileDownloadUtils downloadUtils;
  private final GSpaceArmor gSpaceArmor;
  private StateToken lastToken;
  private final StateManager stateManager;

  @Inject
  public GSpaceBackManagerImpl(final ClientFileDownloadUtils downloadUtils, final GSpaceArmor gSpaceArmor,
      final StateManager stateManager) {
    this.downloadUtils = downloadUtils;
    this.gSpaceArmor = gSpaceArmor;
    this.stateManager = stateManager;
    lastToken = NO_TOKEN;
  }

  @Override
  public void clearBackImage() {
    gSpaceArmor.clearBackImage();
  }

  @Override
  public void restoreBackImage() {
    if (!lastToken.equals(NO_TOKEN)) {
      gSpaceArmor.setBackImage(downloadUtils.getBackgroundImageUrl(lastToken));
    }
  }

  @Override
  public void setBackImage(final @Nonnull StateToken token) {
    final StateToken tokenNoGroup = token.clearDocument().clearDocument();
    if (!tokenNoGroup.equals(lastToken)) {
      gSpaceArmor.setBackImage(downloadUtils.getBackgroundImageUrl(tokenNoGroup));
      lastToken = tokenNoGroup.copy();
    }

  }
}
