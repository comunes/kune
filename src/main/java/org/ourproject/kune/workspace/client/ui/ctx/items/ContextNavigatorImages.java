/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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

package org.ourproject.kune.workspace.client.ui.ctx.items;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

/**
 * 
 * http://code.google.com/p/google-web-toolkit/wiki/ImageBundleDesign
 * 
 */
public interface ContextNavigatorImages extends ImageBundle {

    public static class App {
	private static ContextNavigatorImages ourInstance = null;

	@Deprecated
	public static synchronized ContextNavigatorImages getInstance() {
	    if (ourInstance == null) {
		ourInstance = (ContextNavigatorImages) GWT.create(ContextNavigatorImages.class);
	    }
	    return ourInstance;
	}
    }

    @Resource("bullet_arrow_right.png")
    AbstractImagePrototype bulletArrowRight();

    @Resource("chat-blue.png")
    AbstractImagePrototype chatBlue();

    @Resource("chat-green.png")
    AbstractImagePrototype chatGreen();

    @Resource("folder.png")
    AbstractImagePrototype folder();

    @Resource("folder_add.png")
    AbstractImagePrototype folderAdd();

    @Resource("folder-go-up.png")
    AbstractImagePrototype folderGoUp();

    @Resource("folder-go-up-light.png")
    AbstractImagePrototype folderGoUpLight();

    @Resource("folderpathmenu.png")
    AbstractImagePrototype folderpathmenu();

    @Resource("go-up.png")
    AbstractImagePrototype goUp();

    @Resource("go-up-light.png")
    AbstractImagePrototype goUpLight();

    @Resource("page.png")
    AbstractImagePrototype page();

    @Resource("page_add.png")
    AbstractImagePrototype pageAdd();

    @Resource("page_white.png")
    AbstractImagePrototype pageWhite();

    @Resource("page_white_add.png")
    AbstractImagePrototype pageWhiteAdd();

}
