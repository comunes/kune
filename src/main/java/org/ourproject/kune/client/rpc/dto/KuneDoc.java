/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2 dated June, 1991.
 * 
 * This package is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 *
 */

package org.ourproject.kune.client.rpc.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class KuneDoc implements IsSerializable {
    private String uuid;
    private String name;
    private String content;
    private String licenseName;

    public KuneDoc(String name, String content, String licenseName) {
	this.name = name;
	this.content = content;
	this.licenseName = licenseName;
    }

    public KuneDoc() {
	this(null, null, null);
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getContent() {
	return content;
    }

    public void setContent(String content) {
	this.content = content;
    }

    public String getLicenseName() {
	return licenseName;
    }

    public void setLicenseName(String licenseName) {
	this.licenseName = licenseName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
