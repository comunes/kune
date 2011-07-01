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
package com.google.wave.splash.rpc;

/**
 * A value object that is transformed into a JSON return value for the web client
 * to process.
 *
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
public final class ClientAction {
  private String waveId;
  private String blipId;
  private String action;
  private String html = "";

  private String parent;
  private boolean indent;
  private long version;

  public ClientAction(String action) {
    action(action);
  }

  public String getBlipId() {
    return blipId;
  }

  public ClientAction blipId(String blipId) {
    this.blipId = blipId;
    return this;
  }

  public ClientAction waveId(String waveId) {
    this.waveId = waveId;
    return this;
  }

  public String getWaveId() {
    return waveId;
  }

  public String getAction() {
    return action;
  }

  public ClientAction action(String action) {
    this.action = action;
    return this;
  }

  public String getHtml() {
    return html;
  }

  public ClientAction html(String html) {
    this.html = html;
    return this;
  }

  public String getParent() {
    return parent;
  }

  public ClientAction parent(String parent) {
    this.parent = parent;
    return this;
  }

  public boolean isIndent() {
    return indent;
  }

  public ClientAction indent() {
    this.indent = true;
    return this;
  }

  public ClientAction indent(boolean indent) {
    this.indent = indent;
    return this;
  }

  public ClientAction version(long version) {
    this.version = version;
    return this;
  }

  public long getVersion() {
    return version;
  }
}
