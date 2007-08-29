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

package org.ourproject.kune.platf.client.services;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

/**
 * 
 * http://code.google.com/p/google-web-toolkit/wiki/ImageBundleDesign
 * 
 */
public interface Images extends ImageBundle {

    public static class App {
	private static Images ourInstance = null;

	public static synchronized Images getInstance() {
	    if (ourInstance == null) {
		ourInstance = (Images) GWT.create(Images.class);
	    }
	    return ourInstance;
	}
    }

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/add.png
     */
    AbstractImagePrototype add();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/add-green.png
     */
    AbstractImagePrototype addGreen();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/arrow-down-black.gif
     */
    AbstractImagePrototype arrowDownBlack();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/arrow-down-white.gif
     */
    AbstractImagePrototype arrowDownWhite();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/arrow-right-black.gif
     */
    AbstractImagePrototype arrowRightBlack();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/arrow-right-white.gif
     */
    AbstractImagePrototype arrowRightWhite();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/bullet_add.png
     */
    AbstractImagePrototype bulletAdd();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/bullet_black.png
     */
    AbstractImagePrototype bulletBlack();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/bullet_blue.png
     */
    AbstractImagePrototype bulletBlue();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/bullet_delete.png
     */
    AbstractImagePrototype bulletDelete();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/bullet_green.png
     */
    AbstractImagePrototype bulletGreen();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/bullet_pink.png
     */
    AbstractImagePrototype bulletPink();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/bullet_purple.png
     */
    AbstractImagePrototype bulletPurple();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/bullet_red.png
     */
    AbstractImagePrototype bulletRed();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/bullet_star.png
     */
    AbstractImagePrototype bulletStar();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/bullet_toggle_minus.png
     */
    AbstractImagePrototype bulletToggleMinus();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/bullet_toggle_plus.png
     */
    AbstractImagePrototype bulletTogglePlus();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/bullet_yellow.png
     */
    AbstractImagePrototype bulletYellow();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button15cblue.png
     */
    AbstractImagePrototype button15cblue();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button15cdark.png
     */
    AbstractImagePrototype button15cdark();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button15clight.png
     */
    AbstractImagePrototype button15clight();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button15cxlight.png
     */
    AbstractImagePrototype button15cxlight();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button15lblue.png
     */
    AbstractImagePrototype button15lblue();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button15ldark.png
     */
    AbstractImagePrototype button15ldark();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button15llight.png
     */
    AbstractImagePrototype button15llight();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button15lxlight.png
     */
    AbstractImagePrototype button15lxlight();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button15rblue.png
     */
    AbstractImagePrototype button15rblue();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button15rdark.png
     */
    AbstractImagePrototype button15rdark();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button15rlight.png
     */
    AbstractImagePrototype button15rlight();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button15rxlight.png
     */
    AbstractImagePrototype button15rxlight();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button17cblue.png
     */
    AbstractImagePrototype button17cblue();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button17cdark.png
     */
    AbstractImagePrototype button17cdark();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button17clight.png
     */
    AbstractImagePrototype button17clight();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button17cxlight.png
     */
    AbstractImagePrototype button17cxlight();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button17lblue.png
     */
    AbstractImagePrototype button17lblue();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button17ldark.png
     */
    AbstractImagePrototype button17ldark();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button17llight.png
     */
    AbstractImagePrototype button17llight();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button17lxlight.png
     */
    AbstractImagePrototype button17lxlight();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button17rblue.png
     */
    AbstractImagePrototype button17rblue();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button17rdark.png
     */
    AbstractImagePrototype button17rdark();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button17rlight.png
     */
    AbstractImagePrototype button17rlight();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button17rxlight.png
     */
    AbstractImagePrototype button17rxlight();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button20cblue.png
     */
    AbstractImagePrototype button20cblue();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button20cdark.png
     */
    AbstractImagePrototype button20cdark();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button20clight.png
     */
    AbstractImagePrototype button20clight();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button20cxlight.png
     */
    AbstractImagePrototype button20cxlight();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button20lblue.png
     */
    AbstractImagePrototype button20lblue();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button20ldark.png
     */
    AbstractImagePrototype button20ldark();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button20llight.png
     */
    AbstractImagePrototype button20llight();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button20lxlight.png
     */
    AbstractImagePrototype button20lxlight();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button20rblue.png
     */
    AbstractImagePrototype button20rblue();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button20rdark.png
     */
    AbstractImagePrototype button20rdark();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button20rlight.png
     */
    AbstractImagePrototype button20rlight();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button20rxlight.png
     */
    AbstractImagePrototype button20rxlight();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button-arrow-down.png
     */
    AbstractImagePrototype buttonArrowDown();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button-help-blue.png
     */
    AbstractImagePrototype buttonHelpBlue();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button-help-dark.png
     */
    AbstractImagePrototype buttonHelpDark();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button-help-light.png
     */
    AbstractImagePrototype buttonHelpLight();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/button-sitebar-arrow-down.gif
     */
    AbstractImagePrototype buttonSitebarArrowDown();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/by80x15.png
     */
    AbstractImagePrototype by80x15();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/bync80x15.png
     */
    AbstractImagePrototype bync80x15();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/byncnd80x15.png
     */
    AbstractImagePrototype byncnd80x15();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/byncsa80x15.png
     */
    AbstractImagePrototype byncsa80x15();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/bynd80x15.png
     */
    AbstractImagePrototype bynd80x15();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/bysa80x15.png
     */
    AbstractImagePrototype bysa80x15();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/chat.png
     */
    AbstractImagePrototype chat();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/clear.png
     */
    AbstractImagePrototype clear();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/fullscreen.png
     */
    AbstractImagePrototype fullscreen();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/gnu-fdl.gif
     */
    AbstractImagePrototype gnuFdl();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/kune-close.png
     */
    AbstractImagePrototype kuneClose();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/kune-external-window.png
     */
    AbstractImagePrototype kuneExternalWindow();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/kune-help-translation-icon.png
     */
    AbstractImagePrototype kuneHelpTranslationIcon();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/kune-logo-16px.png
     */
    AbstractImagePrototype kuneLogo16px();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/kune-preferences.png
     */
    AbstractImagePrototype kunePreferences();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/kune-search-ico.png
     */
    AbstractImagePrototype kuneSearchIco();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/kune-search-ico-push.png
     */
    AbstractImagePrototype kuneSearchIcoPush();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/opentriangle.gif
     */
    AbstractImagePrototype opentriangle();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/remove.png
     */
    AbstractImagePrototype remove();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/rss-icon.png
     */
    AbstractImagePrototype rssIcon();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/search-ico.png
     */
    AbstractImagePrototype searchIco();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/star-10.png
     */
    AbstractImagePrototype star10();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/star-20.png
     */
    AbstractImagePrototype star20();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/star-30.png
     */
    AbstractImagePrototype star30();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/star-40.png
     */
    AbstractImagePrototype star40();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/star-50.png
     */
    AbstractImagePrototype star50();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/star-60.png
     */
    AbstractImagePrototype star60();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/star-70.png
     */
    AbstractImagePrototype star70();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/star-80.png
     */
    AbstractImagePrototype star80();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/star-90.png
     */
    AbstractImagePrototype star90();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/star-grey.png
     */
    AbstractImagePrototype starGrey();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/star-small-yellow.png
     */
    AbstractImagePrototype starSmallYellow();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/star-vsmall-yellow.png
     */
    AbstractImagePrototype starVsmallYellow();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/star-yellow.png
     */
    AbstractImagePrototype starYellow();

    /**
     * @gwt.resource org/ourproject/kune/platf/public/images/triangle.gif
     */
    AbstractImagePrototype triangle();

}
