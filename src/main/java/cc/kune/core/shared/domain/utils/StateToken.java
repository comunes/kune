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
package cc.kune.core.shared.domain.utils;

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * <pre>
 * http://code.google.com/p/google-web-toolkit-doc-1-5/wiki/DevGuideHistory
 * http://en.wikipedia.org/wiki/Fragment_identifier
 * http://www.w3.org/DesignIssues/Fragment.html
 * </pre>
 * 
 * <pre>
 * When an agent (such as a Web browser) requests a resource from a Web server, the agent sends the URI to the server, but does not send the fragment.
 * </pre>
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class StateToken implements IsSerializable {

  /** The Constant EMPTYA. */
  private static final String[] EMPTYA = new String[0];

  /** The Constant SEPARATOR. */
  public static final String SEPARATOR = ".";

  /**
   * Encode.
   * 
   * @param group
   *          the group
   * @param tool
   *          the tool
   * @param folder
   *          the folder
   * @param document
   *          the document
   * @return the string
   */
  private static String encode(final String group, final String tool, final String folder,
      final String document) {
    String encoded = "";
    if (group != null) {
      encoded += group;
    }
    if (tool != null) {
      encoded += SEPARATOR + tool;
    }
    if (folder != null) {
      encoded += SEPARATOR + folder;
    }
    if (document != null) {
      encoded += SEPARATOR + document;
    }
    return encoded;
  }

  /** The document. */
  private String document;

  /** The encoded. */
  private String encoded;

  /** The folder. */
  private String folder;

  /** The group. */
  private String group;

  /** The tool. */
  private String tool;

  /**
   * Instantiates a new state token.
   */
  public StateToken() {
    this(null, null, null, null);
  }

  /**
   * Instantiates a new state token.
   * 
   * @param encoded
   *          the encoded
   */
  public StateToken(final String encoded) {
    parse(encoded);
  }

  /**
   * Instantiates a new state token.
   * 
   * @param group
   *          the group
   * @param tool
   *          the tool
   */
  public StateToken(final String group, final String tool) { // NO_UCD
    this(group, tool, null, null);
  }

  /**
   * Instantiates a new state token.
   * 
   * @param group
   *          the group
   * @param tool
   *          the tool
   * @param folder
   *          the folder
   */
  public StateToken(final String group, final String tool, final Long folder) {
    this(group, tool, folder == null ? null : folder.toString(), null);
  }

  /**
   * Instantiates a new state token.
   * 
   * @param group
   *          the group
   * @param tool
   *          the tool
   * @param folder
   *          the folder
   * @param document
   *          the document
   */
  public StateToken(final String group, final String tool, final String folder, final String document) {
    this.group = group;
    this.tool = tool;
    this.folder = folder;
    this.document = document;
    resetEncoded();
  }

  /**
   * Clear document.
   * 
   * @return the state token
   */
  public StateToken clearDocument() {
    this.document = null;
    resetEncoded();
    return this;
  }

  /**
   * Clear folder.
   * 
   * @return the state token
   */
  public StateToken clearFolder() { // NO_UCD
    this.folder = null;
    resetEncoded();
    return this;
  }

  /**
   * Conditional assign.
   * 
   * @param index
   *          the index
   * @param splitted
   *          the splitted
   * @return the string
   */
  private String conditionalAssign(final int index, final String[] splitted) {
    if (splitted.length > index) {
      return splitted[index];
    } else {
      return null;
    }
  }

  /**
   * Copy.
   * 
   * @return the state token
   */
  public StateToken copy() {
    return new StateToken(this.getEncoded());
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final StateToken other = (StateToken) obj;
    if (getEncoded() == null) {
      if (other.getEncoded() != null) {
        return false;
      }
    } else if (!getEncoded().equals(other.getEncoded())) {
      return false;
    }
    return true;
  }

  /**
   * Gets the document.
   * 
   * @return the document
   */
  public String getDocument() {
    return document;
  }

  /**
   * Gets the encoded.
   * 
   * @return the encoded
   */
  public String getEncoded() {
    if (encoded == null) {
      encoded = StateToken.encode(getGroup(), getTool(), getFolder(), getDocument());
    }
    return encoded;
  }

  /**
   * Gets the folder.
   * 
   * @return the folder
   */
  public String getFolder() {
    return folder;
  }

  /**
   * Gets the group.
   * 
   * @return the group
   */
  public String getGroup() {
    return group;
  }

  /**
   * Gets the tool.
   * 
   * @return the tool
   */
  public String getTool() {
    return tool;
  }

  /**
   * Checks for all.
   * 
   * @return true, if successful
   */
  public boolean hasAll() {
    return getGroup() != null && getTool() != null && getFolder() != null && getDocument() != null;
  }

  /**
   * Checks for group.
   * 
   * @return true, if successful
   */
  public boolean hasGroup() {
    return getGroup() != null;
  }

  /**
   * Checks for group and tool.
   * 
   * @return true, if successful
   */
  public boolean hasGroupAndTool() {
    return getGroup() != null && getTool() != null;
  }

  /**
   * Checks for group tool and folder.
   * 
   * @return true, if successful
   */
  public boolean hasGroupToolAndFolder() {
    return getGroup() != null && getTool() != null && getFolder() != null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return getEncoded().hashCode();
  }

  /**
   * Checks for nothing.
   * 
   * @return true, if successful
   */
  public boolean hasNothing() {
    return getGroup() == null && getTool() == null && getFolder() == null && getDocument() == null;
  }

  /**
   * Checks for same container.
   * 
   * @param currentStateToken
   *          the current state token
   * @return true, if successful
   */
  public boolean hasSameContainer(final StateToken currentStateToken) {
    boolean same = false;
    if (copy().clearDocument().getEncoded().equals(currentStateToken.copy().clearDocument().getEncoded())) {
      same = true;
    }
    return same;
  }

  /**
   * Checks if is complete.
   * 
   * @return true, if is complete
   */
  public boolean isComplete() {
    return getDocument() != null;
  }

  /**
   * Parses the.
   * 
   * @param encoded
   *          the encoded
   */
  private void parse(final String encoded) {
    String[] splitted;
    if (encoded != null && encoded.length() > 0) {
      splitted = encoded.split("\\.");
    } else {
      splitted = EMPTYA;
    }
    group = conditionalAssign(0, splitted);
    tool = conditionalAssign(1, splitted);
    folder = conditionalAssign(2, splitted);
    document = conditionalAssign(3, splitted);
    resetEncoded();
  }

  /**
   * Reset encoded.
   */
  private void resetEncoded() {
    encoded = null;
  }

  /**
   * Sets the document.
   * 
   * @param document
   *          the document
   * @return the state token
   */
  public StateToken setDocument(final String document) {
    this.document = document;
    resetEncoded();
    return this;
  }

  /**
   * Sets the document l.
   * 
   * @param document
   *          the document
   * @return the state token
   */
  public StateToken setDocumentL(final Long document) {
    this.document = document == null ? null : document.toString();
    resetEncoded();
    return this;
  }

  /**
   * Sets the encoded.
   * 
   * @param encoded
   *          the new encoded
   */
  public void setEncoded(final String encoded) {
    parse(encoded);
  }

  /**
   * Sets the folder.
   * 
   * @param folder
   *          the folder
   * @return the state token
   */
  public StateToken setFolder(final String folder) { // NO_UCD
    this.folder = folder;
    resetEncoded();
    return this;
  }

  /**
   * Sets the folder l.
   * 
   * @param folder
   *          the folder
   * @return the state token
   */
  public StateToken setFolderL(final Long folder) {
    this.folder = folder == null ? null : folder.toString();
    resetEncoded();
    return this;
  }

  /**
   * Sets the group.
   * 
   * @param group
   *          the group
   * @return the state token
   */
  public StateToken setGroup(final String group) { // NO_UCD
    this.group = group;
    resetEncoded();
    return this;
  }

  /**
   * Sets the tool.
   * 
   * @param tool
   *          the tool
   * @return the state token
   */
  public StateToken setTool(final String tool) { // NO_UCD
    this.tool = tool;
    resetEncoded();
    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return getEncoded();
  }
}
