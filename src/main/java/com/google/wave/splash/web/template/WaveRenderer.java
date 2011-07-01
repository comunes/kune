/**
 * Copyright 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.google.wave.splash.web.template;

import com.google.inject.ImplementedBy;
import com.google.wave.splash.rpc.ClientAction;
import com.google.wave.api.Blip;
import com.google.wave.api.Element;
import com.google.wave.api.ParticipantProfile;
import com.google.wave.api.Wavelet;

import java.util.List;

/**
 * Does the actual conversion of a wavelet/blipdata tree into html. This
 * interface is mainly to satisfy a circular dependency with ContentRenderer.
 *
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
@ImplementedBy(ThreadedWaveRenderer.class) // We only have one impl.
public interface WaveRenderer {
  /**
   * Page number sentinel value indicating that all pages are expected.
   */
  int ALL_PAGES = -1;

  /**
   * Renders an inline reply thread at the correct offset location inside a blip.
   * @param element The element representing the position of the offset inline reply
   * @param index The offset into the blip's text at which this element occurs
   * @param builder The current HTML content StringBuilder of the wave so far
   */
  void renderInlineReply(Element element, int index, StringBuilder builder);

  /**
   *
   * @param wavelet A wavelet to render as a single html blob.
   * @param page The page number to send back. Use this to implement paging,
   *     if you specify page 1, the client action will only contain the second
   *     page as computed during the current render.
   * @return the client action.
   */
  ClientAction render(Wavelet wavelet, int page);

  /**
   * Renders the header of a wavelet, including participants.
   * @param profiles A list of profiles for each participant in the wave, in correct order.
   * @return A {@code ClientAction} that inserts the rendered participant list in the header
   *   portion of the DOM.
   */
  ClientAction renderHeader(List<ParticipantProfile> profiles);

  /**
   * Renders the content of a blip as html. If a title is specified, renders
   * that specially as the root blip.
   *
   * @param blipData The blip whose content you want to render
   * @param title    The title string if this is a root blip or null
   * @return Rendered HTML string with markup
   */
  String toHtml(Blip blipData, String title);

  ClientAction renderNotFound();
}
