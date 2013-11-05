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

// TODO: Auto-generated Javadoc
/**
 * A value object that is transformed into a JSON return value for the web client
 * to process.
 *
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
public final class ClientAction {
  
  /** The wave id. */
  private String waveId;
  
  /** The blip id. */
  private String blipId;
  
  /** The action. */
  private String action;
  
  /** The html. */
  private String html = "";

  /** The parent. */
  private String parent;
  
  /** The indent. */
  private boolean indent;
  
  /** The version. */
  private long version;

  /**
   * Instantiates a new client action.
   *
   * @param action the action
   */
  public ClientAction(String action) {
    action(action);
  }

  /**
   * Gets the blip id.
   *
   * @return the blip id
   */
  public String getBlipId() {
    return blipId;
  }

  /**
   * Blip id.
   *
   * @param blipId the blip id
   * @return the client action
   */
  public ClientAction blipId(String blipId) {
    this.blipId = blipId;
    return this;
  }

  /**
   * Wave id.
   *
   * @param waveId the wave id
   * @return the client action
   */
  public ClientAction waveId(String waveId) {
    this.waveId = waveId;
    return this;
  }

  /**
   * Gets the wave id.
   *
   * @return the wave id
   */
  public String getWaveId() {
    return waveId;
  }

  /**
   * Gets the action.
   *
   * @return the action
   */
  public String getAction() {
    return action;
  }

  /**
   * Action.
   *
   * @param action the action
   * @return the client action
   */
  public ClientAction action(String action) {
    this.action = action;
    return this;
  }

  /**
   * Gets the html.
   *
   * @return the html
   */
  public String getHtml() {
    return html;
  }

  /**
   * Html.
   *
   * @param html the html
   * @return the client action
   */
  public ClientAction html(String html) {
    this.html = html;
    return this;
  }

  /**
   * Gets the parent.
   *
   * @return the parent
   */
  public String getParent() {
    return parent;
  }

  /**
   * Parent.
   *
   * @param parent the parent
   * @return the client action
   */
  public ClientAction parent(String parent) {
    this.parent = parent;
    return this;
  }

  /**
   * Checks if is indent.
   *
   * @return true, if is indent
   */
  public boolean isIndent() {
    return indent;
  }

  /**
   * Indent.
   *
   * @return the client action
   */
  public ClientAction indent() {
    this.indent = true;
    return this;
  }

  /**
   * Indent.
   *
   * @param indent the indent
   * @return the client action
   */
  public ClientAction indent(boolean indent) {
    this.indent = indent;
    return this;
  }

  /**
   * Version.
   *
   * @param version the version
   * @return the client action
   */
  public ClientAction version(long version) {
    this.version = version;
    return this;
  }

  /**
   * Gets the version.
   *
   * @return the version
   */
  public long getVersion() {
    return version;
  }
}
