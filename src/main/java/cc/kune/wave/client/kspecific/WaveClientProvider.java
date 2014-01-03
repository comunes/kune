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
package cc.kune.wave.client.kspecific;

import cc.kune.wave.client.WebClient;

import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class WaveClientProvider.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class WaveClientProvider implements Provider<WaveClientView> {

  /** The web client prov. */
  private final Provider<? extends WaveClientView> webClientProv;

  /**
   * Instantiates a new wave client provider.
   *
   * @param webClient the web client
   * @param webClientMock the web client mock
   */
  @Inject
  public WaveClientProvider(final Provider<WebClient> webClient,
      final Provider<WebClientMock> webClientMock) {
    webClientProv = webClient;
    // If you want not to load the Webclient Mock
    // webClientProv = webClientMock;
  }

  /* (non-Javadoc)
   * @see com.google.inject.Provider#get()
   */
  @Override
  public WaveClientView get() {
    return webClientProv.get();
  }

}
