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
package cc.kune.wave.client.resources;

import org.waveprotocol.wave.client.wavepanel.view.dom.full.TopConversationViewBuilder;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.ImageResource.RepeatStyle;

// TODO: Auto-generated Javadoc
/**
 * The Class KuneWaveResources.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class KuneWaveResources {
  // Not used now
  /**
   * The Interface Conversation.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface Conversation extends ClientBundle {
    // Note: the CSS file contains a gwt-image reference, so must be defined
    // after the referenced images in this interface.
    /**
     * Css.
     *
     * @return the top conversation view builder. css
     */
    @Source("KuneConversation.css")
    TopConversationViewBuilder.Css css();

    /**
     * Empty toolbar.
     *
     * @return the image resource
     */
    @ImageOptions(repeatStyle = RepeatStyle.Horizontal)
    @Source("toolbar_empty.png")
    ImageResource emptyToolbar();
  }
}
