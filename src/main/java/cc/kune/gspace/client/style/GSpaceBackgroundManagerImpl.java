/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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

import cc.kune.common.client.log.Log;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.gspace.client.armor.GSpaceArmor;

import com.google.inject.Inject;

public class GSpaceBackgroundManagerImpl implements GSpaceBackgroundManager {

  private final ClientFileDownloadUtils downloadUtils;
  private final GSpaceArmor gSpaceArmor;
  private boolean noCache;
  private final Session session;

  @Inject
  public GSpaceBackgroundManagerImpl(final ClientFileDownloadUtils downloadUtils,
      final GSpaceArmor gSpaceArmor, final Session session) {
    this.downloadUtils = downloadUtils;
    this.gSpaceArmor = gSpaceArmor;
    this.session = session;
    noCache = false;
  }

  @Override
  public void clearBackgroundImage() {
    gSpaceArmor.clearBackImage();
  }

  @Override
  public void restoreBackgroundImage() {
    final StateToken token = session.getCurrentStateToken();
    if (token != null) {
      gSpaceArmor.setBackImage(downloadUtils.getBackgroundImageUrl(token, noCache));
    } else {
      Log.info("Not restoring group background");
    }
  }

  @Override
  public void setBackgroundImage() {
    final StateToken token = session.getCurrentStateToken();
    if (token != null) {
      Log.info("Set background for " + token + " noCache " + noCache);
      gSpaceArmor.setBackImage(downloadUtils.getBackgroundImageUrl(token, noCache));
    }
  }

  @Override
  public void setNoCache(final boolean noCache) {
    this.noCache = noCache;
  }
}
