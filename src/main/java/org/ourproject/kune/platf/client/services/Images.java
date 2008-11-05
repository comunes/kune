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
package org.ourproject.kune.platf.client.services;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

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

    @Resource("org/ourproject/kune/platf/public/images/accept.png")
    AbstractImagePrototype accept();

    @Resource("org/ourproject/kune/platf/public/images/add.png")
    AbstractImagePrototype add();

    @Resource("org/ourproject/kune/platf/public/images/add-green.png")
    AbstractImagePrototype addGreen();

    @Resource("org/ourproject/kune/platf/public/images/alert.png")
    AbstractImagePrototype alert();

    @Resource("org/ourproject/kune/platf/public/images/anybody.png")
    AbstractImagePrototype anybody();

    @Resource("org/ourproject/kune/platf/public/images/arrow-down-black.gif")
    AbstractImagePrototype arrowDownBlack();

    @Resource("org/ourproject/kune/platf/public/images/arrow-down-green.png")
    AbstractImagePrototype arrowDownGreen();

    @Resource("org/ourproject/kune/platf/public/images/arrow-down-white.gif")
    AbstractImagePrototype arrowDownWhite();

    @Resource("org/ourproject/kune/platf/public/images/arrow-left-green.png")
    AbstractImagePrototype arrowLeftGreen();

    @Resource("org/ourproject/kune/platf/public/images/arrow-right-black.gif")
    AbstractImagePrototype arrowRightBlack();

    @Resource("org/ourproject/kune/platf/public/images/arrow-right-green.png")
    AbstractImagePrototype arrowRightGreen();

    @Resource("org/ourproject/kune/platf/public/images/arrow-right-white.gif")
    AbstractImagePrototype arrowRightWhite();

    @Resource("org/ourproject/kune/platf/public/images/arrow-up-green.png")
    AbstractImagePrototype arrowUpGreen();

    @Resource("org/ourproject/kune/platf/public/images/bullet_add.png")
    AbstractImagePrototype bulletAdd();

    @Resource("org/ourproject/kune/platf/public/images/bullet_black.png")
    AbstractImagePrototype bulletBlack();

    @Resource("org/ourproject/kune/platf/public/images/bullet_blue.png")
    AbstractImagePrototype bulletBlue();

    @Resource("org/ourproject/kune/platf/public/images/bullet_delete.png")
    AbstractImagePrototype bulletDelete();

    @Resource("org/ourproject/kune/platf/public/images/bullet_green.png")
    AbstractImagePrototype bulletGreen();

    @Resource("org/ourproject/kune/platf/public/images/bullet_pink.png")
    AbstractImagePrototype bulletPink();

    @Resource("org/ourproject/kune/platf/public/images/bullet_purple.png")
    AbstractImagePrototype bulletPurple();

    @Resource("org/ourproject/kune/platf/public/images/bullet_red.png")
    AbstractImagePrototype bulletRed();

    @Resource("org/ourproject/kune/platf/public/images/bullet_star.png")
    AbstractImagePrototype bulletStar();

    @Resource("org/ourproject/kune/platf/public/images/bullet_toggle_minus.png")
    AbstractImagePrototype bulletToggleMinus();

    @Resource("org/ourproject/kune/platf/public/images/bullet_toggle_plus.png")
    AbstractImagePrototype bulletTogglePlus();

    @Resource("org/ourproject/kune/platf/public/images/bullet_yellow.png")
    AbstractImagePrototype bulletYellow();

    @Resource("org/ourproject/kune/platf/public/images/button15cblue.png")
    AbstractImagePrototype button15cblue();

    @Resource("org/ourproject/kune/platf/public/images/button15cdark.png")
    AbstractImagePrototype button15cdark();

    @Resource("org/ourproject/kune/platf/public/images/button15clight.png")
    AbstractImagePrototype button15clight();

    @Resource("org/ourproject/kune/platf/public/images/button15cxlight.png")
    AbstractImagePrototype button15cxlight();

    @Resource("org/ourproject/kune/platf/public/images/button15lblue.png")
    AbstractImagePrototype button15lblue();

    @Resource("org/ourproject/kune/platf/public/images/button15ldark.png")
    AbstractImagePrototype button15ldark();

    @Resource("org/ourproject/kune/platf/public/images/button15llight.png")
    AbstractImagePrototype button15llight();

    @Resource("org/ourproject/kune/platf/public/images/button15lxlight.png")
    AbstractImagePrototype button15lxlight();

    @Resource("org/ourproject/kune/platf/public/images/button15rblue.png")
    AbstractImagePrototype button15rblue();

    @Resource("org/ourproject/kune/platf/public/images/button15rdark.png")
    AbstractImagePrototype button15rdark();

    @Resource("org/ourproject/kune/platf/public/images/button15rlight.png")
    AbstractImagePrototype button15rlight();

    @Resource("org/ourproject/kune/platf/public/images/button15rxlight.png")
    AbstractImagePrototype button15rxlight();

    @Resource("org/ourproject/kune/platf/public/images/button17cblue.png")
    AbstractImagePrototype button17cblue();

    @Resource("org/ourproject/kune/platf/public/images/button17cdark.png")
    AbstractImagePrototype button17cdark();

    @Resource("org/ourproject/kune/platf/public/images/button17clight.png")
    AbstractImagePrototype button17clight();

    @Resource("org/ourproject/kune/platf/public/images/button17cxlight.png")
    AbstractImagePrototype button17cxlight();

    @Resource("org/ourproject/kune/platf/public/images/button17lblue.png")
    AbstractImagePrototype button17lblue();

    @Resource("org/ourproject/kune/platf/public/images/button17ldark.png")
    AbstractImagePrototype button17ldark();

    @Resource("org/ourproject/kune/platf/public/images/button17llight.png")
    AbstractImagePrototype button17llight();

    @Resource("org/ourproject/kune/platf/public/images/button17lxlight.png")
    AbstractImagePrototype button17lxlight();

    @Resource("org/ourproject/kune/platf/public/images/button17rblue.png")
    AbstractImagePrototype button17rblue();

    @Resource("org/ourproject/kune/platf/public/images/button17rdark.png")
    AbstractImagePrototype button17rdark();

    @Resource("org/ourproject/kune/platf/public/images/button17rlight.png")
    AbstractImagePrototype button17rlight();

    @Resource("org/ourproject/kune/platf/public/images/button17rxlight.png")
    AbstractImagePrototype button17rxlight();

    @Resource("org/ourproject/kune/platf/public/images/button20cblue.png")
    AbstractImagePrototype button20cblue();

    @Resource("org/ourproject/kune/platf/public/images/button20cdark.png")
    AbstractImagePrototype button20cdark();

    @Resource("org/ourproject/kune/platf/public/images/button20clight.png")
    AbstractImagePrototype button20clight();

    @Resource("org/ourproject/kune/platf/public/images/button20cxlight.png")
    AbstractImagePrototype button20cxlight();

    @Resource("org/ourproject/kune/platf/public/images/button20lblue.png")
    AbstractImagePrototype button20lblue();

    @Resource("org/ourproject/kune/platf/public/images/button20ldark.png")
    AbstractImagePrototype button20ldark();

    @Resource("org/ourproject/kune/platf/public/images/button20llight.png")
    AbstractImagePrototype button20llight();

    @Resource("org/ourproject/kune/platf/public/images/button20lxlight.png")
    AbstractImagePrototype button20lxlight();

    @Resource("org/ourproject/kune/platf/public/images/button20rblue.png")
    AbstractImagePrototype button20rblue();

    @Resource("org/ourproject/kune/platf/public/images/button20rdark.png")
    AbstractImagePrototype button20rdark();

    @Resource("org/ourproject/kune/platf/public/images/button20rlight.png")
    AbstractImagePrototype button20rlight();

    @Resource("org/ourproject/kune/platf/public/images/button20rxlight.png")
    AbstractImagePrototype button20rxlight();

    @Resource("org/ourproject/kune/platf/public/images/button-arrow-down.png")
    AbstractImagePrototype buttonArrowDown();

    @Resource("org/ourproject/kune/platf/public/images/button-help-blue.png")
    AbstractImagePrototype buttonHelpBlue();

    @Resource("org/ourproject/kune/platf/public/images/button-help-dark.png")
    AbstractImagePrototype buttonHelpDark();

    @Resource("org/ourproject/kune/platf/public/images/button-help-light.png")
    AbstractImagePrototype buttonHelpLight();

    @Resource("org/ourproject/kune/platf/public/images/arrowdown.gif")
    AbstractImagePrototype buttonSitebarArrowDown();

    @Resource("org/ourproject/kune/platf/public/images/by80x15.png")
    AbstractImagePrototype by80x15();

    @Resource("org/ourproject/kune/platf/public/images/bync80x15.png")
    AbstractImagePrototype bync80x15();

    @Resource("org/ourproject/kune/platf/public/images/byncnd80x15.png")
    AbstractImagePrototype byncnd80x15();

    @Resource("org/ourproject/kune/platf/public/images/byncsa80x15.png")
    AbstractImagePrototype byncsa80x15();

    @Resource("org/ourproject/kune/platf/public/images/bynd80x15.png")
    AbstractImagePrototype bynd80x15();

    @Resource("org/ourproject/kune/platf/public/images/bysa80x15.png")
    AbstractImagePrototype bysa80x15();

    @Resource("org/ourproject/kune/platf/public/images/cancel.png")
    AbstractImagePrototype cancel();

    @Resource("org/ourproject/kune/platf/public/images/clear.png")
    AbstractImagePrototype clear();

    @Resource("org/ourproject/kune/platf/public/images/copyleft.png")
    AbstractImagePrototype copyleft();

    @Resource("org/ourproject/kune/platf/public/images/cross.png")
    AbstractImagePrototype cross();

    @Resource("org/ourproject/kune/platf/public/images/cross-dark.png")
    AbstractImagePrototype crossDark();

    @Resource("org/ourproject/kune/platf/public/images/del.png")
    AbstractImagePrototype del();

    @Resource("org/ourproject/kune/platf/public/images/emblem-important.png")
    AbstractImagePrototype emblemImportant();

    @Resource("org/ourproject/kune/platf/public/images/error.png")
    AbstractImagePrototype error();

    @Resource("org/ourproject/kune/platf/public/images/everybody.png")
    AbstractImagePrototype everybody();

    @Resource("org/ourproject/kune/platf/public/images/fullscreen.png")
    AbstractImagePrototype fullscreen();

    @Resource("org/ourproject/kune/platf/public/images/gnu-fdl.gif")
    AbstractImagePrototype gnuFdl();

    @Resource("org/ourproject/kune/platf/public/images/group-def-icon.png")
    AbstractImagePrototype groupDefIcon();

    @Resource("org/ourproject/kune/platf/public/images/group-home.png")
    AbstractImagePrototype groupHome();

    @Resource("org/ourproject/kune/platf/public/images/important.png")
    AbstractImagePrototype important();

    @Resource("org/ourproject/kune/platf/public/images/info.png")
    AbstractImagePrototype info();

    @Resource("org/ourproject/kune/platf/public/images/info-light.png")
    AbstractImagePrototype infoLight();

    @Resource("org/ourproject/kune/platf/public/images/kune-close.png")
    AbstractImagePrototype kuneClose();

    @Resource("org/ourproject/kune/platf/public/images/kune-external-window.png")
    AbstractImagePrototype kuneExternalWindow();

    @Resource("org/ourproject/kune/platf/public/images/kune-help-translation-icon.png")
    AbstractImagePrototype kuneHelpTranslationIcon();

    @Resource("org/ourproject/kune/platf/public/images/kune-icon16.png")
    AbstractImagePrototype kuneIcon16();

    @Resource("org/ourproject/kune/platf/public/images/kune-logo-16px.png")
    AbstractImagePrototype kuneLogo16px();

    @Resource("org/ourproject/kune/platf/public/images/kune-preferences.png")
    AbstractImagePrototype kunePreferences();

    @Resource("org/ourproject/kune/platf/public/images/kune-search-ico.png")
    AbstractImagePrototype kuneSearchIco();

    @Resource("org/ourproject/kune/platf/public/images/kune-search-ico-push.png")
    AbstractImagePrototype kuneSearchIcoPush();

    @Resource("org/ourproject/kune/platf/public/images/language.png")
    AbstractImagePrototype language();

    @Resource("org/ourproject/kune/platf/public/images/nobody.png")
    AbstractImagePrototype nobody();

    @Resource("org/ourproject/kune/platf/public/images/no-copyleft.png")
    AbstractImagePrototype noCopyleft();

    @Resource("org/ourproject/kune/platf/public/images/nt.png")
    AbstractImagePrototype nt();

    @Resource("org/ourproject/kune/platf/public/images/opentriangle.gif")
    AbstractImagePrototype opentriangle();

    @Resource("org/ourproject/kune/platf/public/images/person-def.png")
    AbstractImagePrototype personDef();

    @Resource("org/ourproject/kune/platf/public/images/remove.png")
    AbstractImagePrototype remove();

    @Resource("org/ourproject/kune/platf/public/images/rss-icon.png")
    AbstractImagePrototype rssIcon();

    @Resource("org/ourproject/kune/platf/public/images/search-ico.png")
    AbstractImagePrototype searchIco();

    @Resource("org/ourproject/kune/platf/public/images/spin-kune-thund-green.gif")
    AbstractImagePrototype spinKuneThundGreen();

    @Resource("org/ourproject/kune/platf/public/images/splitter-vert-bar.gif")
    AbstractImagePrototype splitterVertBar();

    @Resource("org/ourproject/kune/platf/public/images/star-10.png")
    AbstractImagePrototype star10();

    @Resource("org/ourproject/kune/platf/public/images/star-20.png")
    AbstractImagePrototype star20();

    @Resource("org/ourproject/kune/platf/public/images/star-30.png")
    AbstractImagePrototype star30();

    @Resource("org/ourproject/kune/platf/public/images/star-40.png")
    AbstractImagePrototype star40();

    @Resource("org/ourproject/kune/platf/public/images/star-50.png")
    AbstractImagePrototype star50();

    @Resource("org/ourproject/kune/platf/public/images/star-60.png")
    AbstractImagePrototype star60();

    @Resource("org/ourproject/kune/platf/public/images/star-70.png")
    AbstractImagePrototype star70();

    @Resource("org/ourproject/kune/platf/public/images/star-80.png")
    AbstractImagePrototype star80();

    @Resource("org/ourproject/kune/platf/public/images/star-90.png")
    AbstractImagePrototype star90();

    @Resource("org/ourproject/kune/platf/public/images/star-grey.png")
    AbstractImagePrototype starGrey();

    @Resource("org/ourproject/kune/platf/public/images/star-small-yellow.png")
    AbstractImagePrototype starSmallYellow();

    @Resource("org/ourproject/kune/platf/public/images/star-vsmall-yellow.png")
    AbstractImagePrototype starVsmallYellow();

    @Resource("org/ourproject/kune/platf/public/images/star-yellow.png")
    AbstractImagePrototype starYellow();

    @Resource("org/ourproject/kune/platf/public/images/step1.png")
    AbstractImagePrototype step1();

    @Resource("org/ourproject/kune/platf/public/images/step2.png")
    AbstractImagePrototype step2();

    @Resource("org/ourproject/kune/platf/public/images/step3.png")
    AbstractImagePrototype step3();

    @Resource("org/ourproject/kune/platf/public/images/step4.png")
    AbstractImagePrototype step4();

    @Resource("org/ourproject/kune/platf/public/images/step5.png")
    AbstractImagePrototype step5();

    @Resource("org/ourproject/kune/platf/public/images/theme-choose.png")
    AbstractImagePrototype themeChoose();

    @Resource("org/ourproject/kune/platf/public/images/triangle.gif")
    AbstractImagePrototype triangle();
}
