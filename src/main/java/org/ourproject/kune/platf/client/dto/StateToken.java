/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.client.dto;

import org.ourproject.kune.platf.client.ui.WindowUtils;

import com.google.gwt.user.client.rpc.IsSerializable;

public class StateToken implements IsSerializable {
    private static final String[] EMPTY = new String[0];
    private static final String DOT = ".";
    private String group;
    private String tool;
    private String folder;
    private String document;
    private String encoded;

    public StateToken(final String group, final String tool, final String folder, final String document) {
        this.setGroup(group);
        this.setTool(tool);
        this.setFolder(folder);
        this.setDocument(document);
        encoded = null;
    }

    public StateToken() {
        this(null, null, null, null);
    }

    public String toString() {
        return getEncoded();
    }

    public String getEncoded() {
        if (encoded == null) {
            encoded = StateToken.encode(getGroup(), getTool(), getFolder(), getDocument());
        }
        return encoded;
    }

    @Override
    public int hashCode() {
        return getEncoded().hashCode();
    }

    public StateToken(final String encoded) {
        String[] splitted;
        if (encoded != null && encoded.length() > 0) {
            splitted = encoded.split("\\.");
        } else {
            splitted = EMPTY;
        }
        setGroup(conditionalAssign(0, splitted));
        setTool(conditionalAssign(1, splitted));
        setFolder(conditionalAssign(2, splitted));
        setDocument(conditionalAssign(3, splitted));
    }

    private String conditionalAssign(final int index, final String[] splitted) {
        if (splitted.length > index) {
            return splitted[index];
        } else {
            return null;
        }
    }

    public static String encode(final String group, final String tool, final String folder, final String document) {
        String encoded = "";
        if (group != null) {
            encoded += group;
        }
        if (tool != null) {
            encoded += DOT + tool;
        }
        if (folder != null) {
            encoded += DOT + folder;
        }
        if (document != null) {
            encoded += DOT + document;
        }
        return encoded;
    }

    public boolean isComplete() {
        return getDocument() != null;
    }

    public boolean hasAll() {
        return getGroup() != null && getTool() != null && getFolder() != null && getDocument() != null;
    }

    public boolean hasGroupToolAndFolder() {
        return getGroup() != null && getTool() != null && getFolder() != null;
    }

    public boolean hasGroupAndTool() {
        return getGroup() != null && getTool() != null;
    }

    public boolean hasGroup() {
        return getGroup() != null;
    }

    public boolean hasNothing() {
        return getGroup() == null && getTool() == null && getFolder() == null && getDocument() == null;
    }

    public void setGroup(final String group) {
        this.group = group;
        encoded = null;
    }

    public String getGroup() {
        return group;
    }

    public void setTool(final String tool) {
        this.tool = tool;
        encoded = null;
    }

    public String getTool() {
        return tool;
    }

    public void setFolder(final String folder) {
        this.folder = folder;
        encoded = null;
    }

    public String getFolder() {
        return folder;
    }

    public void setDocument(final String document) {
        this.document = document;
        encoded = null;
    }

    public String getDocument() {
        return document;
    }

    public String getPublicUrl() {
        String publicUrl = "http://";

        if (group != null) {
            publicUrl += group + DOT;
        }

        // FIXME: Maybe get from InitData the site.domain
        publicUrl += WindowUtils.getLocation().getHostName() + "/";

        if (tool != null) {
            publicUrl += tool;
        }
        if (folder != null) {
            publicUrl += DOT + folder;
        }
        if (document != null) {
            publicUrl += DOT + document;
        }

        return publicUrl;
    }
}
