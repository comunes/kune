/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
 * This file is part of kune.
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
 */
public class StateToken implements IsSerializable {
  public static final String SEPARATOR = ".";
  private static final String[] EMPTYA = new String[0];

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

  private String group;
  private String tool;
  private String folder;
  private String document;

  private String encoded;

  public StateToken() {
    this(null, null, null, null);
  }

  public StateToken(final String encoded) {
    parse(encoded);
  }

  public StateToken(final String group, final String tool) { // NO_UCD
    this(group, tool, null, null);
  }

  public StateToken(final String group, final String tool, final Long folder) {
    this(group, tool, folder == null ? null : folder.toString(), null);
  }

  public StateToken(final String group, final String tool, final String folder, final String document) {
    this.group = group;
    this.tool = tool;
    this.folder = folder;
    this.document = document;
    resetEncoded();
  }

  public StateToken clearDocument() {
    this.document = null;
    resetEncoded();
    return this;
  }

  public StateToken clearFolder() { // NO_UCD
    this.folder = null;
    resetEncoded();
    return this;
  }

  public StateToken copy() {
    return new StateToken(this.getEncoded());
  }

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

  public String getDocument() {
    return document;
  }

  public String getEncoded() {
    if (encoded == null) {
      encoded = StateToken.encode(getGroup(), getTool(), getFolder(), getDocument());
    }
    return encoded;
  }

  public String getFolder() {
    return folder;
  }

  public String getGroup() {
    return group;
  }

  public String getTool() {
    return tool;
  }

  public boolean hasAll() {
    return getGroup() != null && getTool() != null && getFolder() != null && getDocument() != null;
  }

  public boolean hasGroup() {
    return getGroup() != null;
  }

  public boolean hasGroupAndTool() {
    return getGroup() != null && getTool() != null;
  }

  public boolean hasGroupToolAndFolder() {
    return getGroup() != null && getTool() != null && getFolder() != null;
  }

  @Override
  public int hashCode() {
    return getEncoded().hashCode();
  }

  public boolean hasNothing() {
    return getGroup() == null && getTool() == null && getFolder() == null && getDocument() == null;
  }

  public boolean hasSameContainer(final StateToken currentStateToken) {
    boolean same = false;
    if (copy().clearDocument().getEncoded().equals(currentStateToken.copy().clearDocument().getEncoded())) {
      same = true;
    }
    return same;
  }

  public boolean isComplete() {
    return getDocument() != null;
  }

  public StateToken setDocument(final Long document) {
    this.document = document == null ? null : document.toString();
    resetEncoded();
    return this;
  }

  public StateToken setDocument(final String document) {
    this.document = document;
    resetEncoded();
    return this;
  }

  public void setEncoded(final String encoded) {
    parse(encoded);
  }

  public StateToken setFolder(final Long folder) {
    this.folder = folder == null ? null : folder.toString();
    resetEncoded();
    return this;
  }

  public StateToken setFolder(final String folder) { // NO_UCD
    this.folder = folder;
    resetEncoded();
    return this;
  }

  public StateToken setGroup(final String group) { // NO_UCD
    this.group = group;
    resetEncoded();
    return this;
  }

  public StateToken setTool(final String tool) { // NO_UCD
    this.tool = tool;
    resetEncoded();
    return this;
  }

  @Override
  public String toString() {
    return getEncoded();
  }

  private String conditionalAssign(final int index, final String[] splitted) {
    if (splitted.length > index) {
      return splitted[index];
    } else {
      return null;
    }
  }

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

  private void resetEncoded() {
    encoded = null;
  }
}
