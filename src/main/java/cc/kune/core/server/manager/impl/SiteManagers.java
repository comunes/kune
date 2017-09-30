/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
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

package cc.kune.core.server.manager.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.server.manager.I18nCountryManager;
import cc.kune.core.server.manager.I18nLanguageManager;
import cc.kune.core.server.manager.I18nTranslationManager;
import cc.kune.core.server.manager.InvitationManager;
import cc.kune.core.server.manager.LicenseManager;
import cc.kune.core.server.manager.ParticipantEntityManager;
import cc.kune.core.server.manager.TagManager;
import cc.kune.core.server.manager.TagUserContentManager;
import cc.kune.core.server.manager.UserManager;
import cc.kune.core.server.manager.UserSignInLogManager;
import cc.kune.core.server.manager.WaveEntityManager;

@Singleton
public class SiteManagers {
  // ls -1d src/main/java/cc/kune/core/server/manager/* | egrep -v
  // "Mbean|Cache|Default" | cut -d "/" -f 9 | cut -d "." -f 1 | awk '{print
  // "@Inject "$1" "tolower( substr( $1, 1, 1 ) ) substr( $1, 2 )";"}'
  @Inject
  GroupManager groupManager;
  @Inject
  I18nCountryManager i18nCountryManager;
  @Inject
  I18nLanguageManager i18nLanguageManager;
  @Inject
  I18nTranslationManager i18nTranslationManager;
  @Inject
  InvitationManager invitationManager;
  @Inject
  LicenseManager licenseManager;
  @Inject
  ParticipantEntityManager participantEntityManager;
  @Inject
  TagManager tagManager;
  @Inject
  TagUserContentManager tagUserContentManager;
  @Inject
  UserManager userManager;
  @Inject
  UserSignInLogManager userSignInLogManager;
  @Inject
  WaveEntityManager waveEntityManager;

  public void reIndexAllEntities() {
    // Warning: With kune stopped check there is no locks in lucene indexes
    // find /var/lib/kune/lucene/ -name '*lock' -exec rm {} \;
    groupManager.reIndex();
    userManager.reIndex();
    i18nCountryManager.reIndex();
    i18nLanguageManager.reIndex();
    i18nTranslationManager.reIndex();
    invitationManager.reIndex();
    licenseManager.reIndex();
    participantEntityManager.reIndex();
    tagManager.reIndex();
    tagUserContentManager.reIndex();
    userSignInLogManager.reIndex();
    // There is a cast error here so we don't reindex right now
    // waveEntityManager.reIndex();
  }

}
