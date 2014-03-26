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

package cc.kune.wave.server;

import org.waveprotocol.box.server.CoreSettings;
import org.waveprotocol.box.server.robots.operations.FetchProfilesService.ProfilesFetcher;
import org.waveprotocol.box.server.robots.operations.GravatarProfilesFetcher;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

/**
 * Profile Fetcher Module.
 * 
 * @author vjrj@apache.org (Vicente J. Ruiz Jurado)
 */
public class CustomProfileFetcherModule extends AbstractModule {

  private final String profileFetcherType;

  @Inject
  public CustomProfileFetcherModule(@Named(CoreSettings.PROFILE_FETCHER_TYPE) final String profilerType) {
    this.profileFetcherType = profilerType;
  }

  @Override
  protected void configure() {
    if ("gravatar".equals(profileFetcherType)) {
      bind(ProfilesFetcher.class).to(GravatarProfilesFetcher.class).in(Singleton.class);
    } else if ("initials".equals(profileFetcherType)) {
      bind(ProfilesFetcher.class).to(CustomInitialsProfilesFetcher.class).in(Singleton.class);
    } else {
      throw new RuntimeException("Unknown profile fetcher type: " + profileFetcherType);
    }
  }
}
