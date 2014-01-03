/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
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
package cc.kune.gspace.client.style;

import cc.kune.common.client.log.Log;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.gspace.client.armor.GSpaceArmor;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class GSpaceBackgroundManagerImpl.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GSpaceBackgroundManagerImpl implements GSpaceBackgroundManager {

  /** The download utils. */
  private final ClientFileDownloadUtils downloadUtils;

  /** The g space armor. */
  private final GSpaceArmor gSpaceArmor;

  /** The no cache. */
  private boolean noCache;

  /** The session. */
  private final Session session;

  /**
   * Instantiates a new g space background manager impl.
   * 
   * @param downloadUtils
   *          the download utils
   * @param gSpaceArmor
   *          the g space armor
   * @param session
   *          the session
   */
  @Inject
  public GSpaceBackgroundManagerImpl(final ClientFileDownloadUtils downloadUtils,
      final GSpaceArmor gSpaceArmor, final Session session) {
    this.downloadUtils = downloadUtils;
    this.gSpaceArmor = gSpaceArmor;
    this.session = session;
    noCache = false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.style.GSpaceBackgroundManager#clearBackgroundImage()
   */
  @Override
  public void clearBackgroundImage() {
    gSpaceArmor.clearBackImage();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.style.GSpaceBackgroundManager#restoreBackgroundImage
   * ()
   */
  @Override
  public void restoreBackgroundImage() {
    final StateToken token = session.getCurrentStateToken();
    if (token != null) {
      gSpaceArmor.setBackImage(downloadUtils.getBackgroundImageUrl(token, noCache));
    } else {
      Log.info("Not restoring group background");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.style.GSpaceBackgroundManager#setBackgroundImage()
   */
  @Override
  public void setBackgroundImage() {
    final StateToken token = session.getCurrentStateToken();
    if (token != null) {
      Log.info("Set background for " + token + " noCache " + noCache);
      gSpaceArmor.setBackImage(downloadUtils.getBackgroundImageUrl(token, noCache));
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.style.GSpaceBackgroundManager#setNoCache(boolean)
   */
  @Override
  public void setNoCache(final boolean noCache) {
    this.noCache = noCache;
  }
}
