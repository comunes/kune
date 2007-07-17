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
package org.ourproject.kune.client.ehub;

public class EventSubscription {

    private String name = null;

    private EventSubscriber eventSubscriber = null;

    public EventSubscription(String name, EventSubscriber eventSubscriber) {
        this.name = name;
        this.eventSubscriber = eventSubscriber;
    }

    public EventSubscriber getEventSubscriber() {
        return eventSubscriber;
    }

    public String getName() {
        return name;
    }

}
