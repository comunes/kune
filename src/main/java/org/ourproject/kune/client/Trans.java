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

package org.ourproject.kune.client;
import com.google.gwt.core.client.GWT;

public class Trans {

	private static MainConstants _const = (MainConstants)GWT.create(MainConstants.class);

	public static MainConstants constants() {
		return _const;
	}
	
	public static String t(String constant) {
		try {
		return _const.getString(constant);
		} catch (Exception MissingResourceException) {
			return "The string '" + constant + "' is not in properties, getting the string from db";
		}
	}
}
