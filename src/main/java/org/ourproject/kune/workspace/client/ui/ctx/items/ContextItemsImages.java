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

package org.ourproject.kune.workspace.client.ui.ctx.items;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

/**
 * 
 * http://code.google.com/p/google-web-toolkit/wiki/ImageBundleDesign
 * 
 */
public interface ContextItemsImages extends ImageBundle {

    public static class App {
	private static ContextItemsImages ourInstance = null;

	public static synchronized ContextItemsImages getInstance() {
	    if (ourInstance == null) {
		ourInstance = (ContextItemsImages) GWT.create(ContextItemsImages.class);
	    }
	    return ourInstance;
	}
    }

    /**
     * @gwt.resource bullet_arrow_right.png
     */
    AbstractImagePrototype bulletArrowRight();

    /**
     * @gwt.resource chat-green.png
     */
    AbstractImagePrototype chatGreen();

    /**
     * @gwt.resource chat-blue.png
     */
    AbstractImagePrototype chatBlue();

    /**
     * @gwt.resource folder.png
     */
    AbstractImagePrototype folder();

    /**
     * @gwt.resource folder_add.png
     */
    AbstractImagePrototype folderAdd();

    /**
     * @gwt.resource folderpathmenu.png
     */
    AbstractImagePrototype folderpathmenu();

    /**
     * @gwt.resource go-up.png
     */
    AbstractImagePrototype goUp();

    /**
     * @gwt.resource go-up-light.png
     */
    AbstractImagePrototype goUpLight();

    /**
     * @gwt.resource page.png
     */
    AbstractImagePrototype page();

    /**
     * @gwt.resource page_add.png
     */
    AbstractImagePrototype pageAdd();

    /**
     * @gwt.resource page_white.png
     */
    AbstractImagePrototype pageWhite();

    /**
     * @gwt.resource page_white_add.png
     */
    AbstractImagePrototype pageWhiteAdd();

}