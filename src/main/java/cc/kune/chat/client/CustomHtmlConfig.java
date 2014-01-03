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
package cc.kune.chat.client;

import com.calclab.emite.browser.client.PageAssist;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomHtmlConfig.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class CustomHtmlConfig {

    /**
     * Gets the from meta.
     *
     * @return the from meta
     */
    public static CustomHtmlConfig getFromMeta() {
	final CustomHtmlConfig config = new CustomHtmlConfig();
	config.hasLogin = PageAssist.isMetaTrue("hablar.login");
	config.inline = PageAssist.getMeta("hablar.inline");
	config.width = PageAssist.getMeta("hablar.width");
	config.height = PageAssist.getMeta("hablar.height");
	if (config.width == null) {
	    config.width = "400px";
	}
	if (config.height == null) {
	    config.height = "400px";
	}
	return config;
    }

    /** Width. */
    public String width = "100%";

    /** Height. */
    public String height = "100%";

    /** Install Logger module. */
    public boolean hasLogger = false;

    /** Show or not login panel. */
    public boolean hasLogin = false;

    /** If not null, show 'hablar' inside the div with the id given. */
    public String inline = null;
}
