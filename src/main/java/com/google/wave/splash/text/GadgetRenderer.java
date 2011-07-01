/**
 * Copyright 2010 Google Inc.
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
package com.google.wave.splash.text;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.wave.api.Gadget;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;


/**
 * Render gadget iframes from Elements.
 *
 * @author David Byttow
 */
public class GadgetRenderer {
  private static final Set<String> NON_STATE_PROPERTIES = ImmutableSet.of("author", "url");
  private final Provider<HttpServletRequest> requestProvider;
  private final AtomicInteger nextId = new AtomicInteger(0);

  @Inject
  public GadgetRenderer(Provider<HttpServletRequest> requestProvider) {
    this.requestProvider = requestProvider;
  }

  void render(Gadget gadget, List<String> contributors, StringBuilder builder) {
    String id = "g" + nextId.incrementAndGet();
    String parentUrl = requestProvider.get().getRequestURL().toString();

    appendState(gadget, id, contributors, builder);
    builder.append("<iframe class=\"gadget\" "
        + "id=\"").append(id).append("\" "
        + "name=\"").append(id).append("\" "
        + "width=\"100%\" height=\"1px\" ")
        .append("frameBorder=\"no\" scrolling=\"no\" src=\""
            + "http://www.gmodules.com/gadgets/ifr?container=wave&view=default&debug=0&"
            + "lang=en&country=ALL&nocache=0&wave=1")
        .append("&mid=").append(id)
        .append("&parent=").append(Markup.sanitizeAndEncode(parentUrl))
        .append("&url=").append(Markup.sanitizeAndEncode(gadget.getUrl()))
        .append("\"/>");
  }

  private void appendState(Gadget gadget, String id, List<String> contributors,
      StringBuilder builder) {
    String output = "";
    builder.append("<div class=\"invisible\" id=\"")
        .append(id)
        .append("_state\">");
    for (Map.Entry<String, String> entry : gadget.getProperties().entrySet()) {
      if (NON_STATE_PROPERTIES.contains(entry.getKey())) {
        continue;
      }
      renderStateValue("gadget-state", entry.getKey(), entry.getValue(), builder);
    }

    for (String participantId : contributors) {
      renderStateValue("gadget-participant", null, participantId, builder);
    }
    renderStateValue("gadget-author", null, gadget.getProperties().get("author"), builder);
    builder.append("</div>");
  }

  private void renderStateValue(String className, String key, String value,
      StringBuilder builder) {
    // TODO: Is this kosher? Should we encode this data in
    // another way?
    builder.append("<span class=\"")
        .append(className)
        .append("\"");
    if (key != null) {
      builder.append(" hiddenKey=\"")
          .append(Markup.sanitize(key))
          .append('"');
    }
    builder.append(">");
    if (value != null) {
      builder.append(Markup.sanitize(value));
    }
    builder.append("</span>");
  }
}
