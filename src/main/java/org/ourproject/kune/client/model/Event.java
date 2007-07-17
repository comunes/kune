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
package org.ourproject.kune.client.model;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Event extends Model implements IsSerializable, Cloneable {

    private String name;

    private Date timestamp;

    private String publisherData = null;

    public Event() {
        timestamp = new Date();
    }

    public Event(String name) {
        this();
        this.name = name;
    }

    public Event(String name, String publisherData) {
        this(name);
        this.publisherData = publisherData;
    }

    public String getName() {
        return name;
    }

    public void setType(String name) {
        this.name = name;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getPublisherData() {
        return publisherData;
    }

    public void setPublisherData(String publisherData) {
        this.publisherData = publisherData;
    }

}
