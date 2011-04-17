/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
 \*/
package cc.kune.core.client.services;

import cc.kune.core.client.resources.CoreResources;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.inject.Inject;

public class ImageUtils {

    private final CoreResources img;

    @Inject
    public ImageUtils(final CoreResources img) {
        this.img = img;
    }

    public ImageResource getImage(final ImageDescriptor imageDescriptor) {
        switch (imageDescriptor) {
        case accept: {
            return img.accept();
        }
        case add: {
            return img.add();
        }
        case addGreen: {
            return img.addGreen();
        }
        case alert: {
            return img.alert();
        }
        case anybody: {
            return img.anybody();
        }
        case arrowDownBlack: {
            return img.arrowDownBlack();
        }
        case arrowDownGreen: {
            return img.arrowDownGreen();
        }
        case arrowDownWhite: {
            return img.arrowDownWhite();
        }
        case arrowLeftGreen: {
            return img.arrowLeftGreen();
        }
        case arrowRightBlack: {
            return img.arrowRightBlack();
        }
        case arrowRightGreen: {
            return img.arrowRightGreen();
        }
        case arrowRightWhite: {
            return img.arrowRightWhite();
        }
        case arrowUpGreen: {
            return img.arrowUpGreen();
        }
        case bulletAdd: {
            return img.bulletAdd();
        }
        case bulletBlack: {
            return img.bulletBlack();
        }
        case bulletBlue: {
            return img.bulletBlue();
        }
        case bulletDelete: {
            return img.bulletDelete();
        }
        case bulletGreen: {
            return img.bulletGreen();
        }
        case bulletPink: {
            return img.bulletPink();
        }
        case bulletPurple: {
            return img.bulletPurple();
        }
        case bulletRed: {
            return img.bulletRed();
        }
        case bulletStar: {
            return img.bulletStar();
        }
        case bulletToggleMinus: {
            return img.bulletToggleMinus();
        }
        case bulletTogglePlus: {
            return img.bulletTogglePlus();
        }
        case bulletYellow: {
            return img.bulletYellow();
        }
        case button15cblue: {
            return img.button15cblue();
        }
        case button15cdark: {
            return img.button15cdark();
        }
        case button15clight: {
            return img.button15clight();
        }
        case button15cxlight: {
            return img.button15cxlight();
        }
        case button15lblue: {
            return img.button15lblue();
        }
        case button15ldark: {
            return img.button15ldark();
        }
        case button15llight: {
            return img.button15llight();
        }
        case button15lxlight: {
            return img.button15lxlight();
        }
        case button15rblue: {
            return img.button15rblue();
        }
        case button15rdark: {
            return img.button15rdark();
        }
        case button15rlight: {
            return img.button15rlight();
        }
        case button15rxlight: {
            return img.button15rxlight();
        }
        case button17cblue: {
            return img.button17cblue();
        }
        case button17cdark: {
            return img.button17cdark();
        }
        case button17clight: {
            return img.button17clight();
        }
        case button17cxlight: {
            return img.button17cxlight();
        }
        case button17lblue: {
            return img.button17lblue();
        }
        case button17ldark: {
            return img.button17ldark();
        }
        case button17llight: {
            return img.button17llight();
        }
        case button17lxlight: {
            return img.button17lxlight();
        }
        case button17rblue: {
            return img.button17rblue();
        }
        case button17rdark: {
            return img.button17rdark();
        }
        case button17rlight: {
            return img.button17rlight();
        }
        case button17rxlight: {
            return img.button17rxlight();
        }
        case button20cblue: {
            return img.button20cblue();
        }
        case button20cdark: {
            return img.button20cdark();
        }
        case button20clight: {
            return img.button20clight();
        }
        case button20cxlight: {
            return img.button20cxlight();
        }
        case button20lblue: {
            return img.button20lblue();
        }
        case button20ldark: {
            return img.button20ldark();
        }
        case button20llight: {
            return img.button20llight();
        }
        case button20lxlight: {
            return img.button20lxlight();
        }
        case button20rblue: {
            return img.button20rblue();
        }
        case button20rdark: {
            return img.button20rdark();
        }
        case button20rlight: {
            return img.button20rlight();
        }
        case button20rxlight: {
            return img.button20rxlight();
        }
        case buttonArrowDown: {
            return img.buttonArrowDown();
        }
        case buttonHelpBlue: {
            return img.buttonHelpBlue();
        }
        case buttonHelpDark: {
            return img.buttonHelpDark();
        }
        case buttonHelpLight: {
            return img.buttonHelpLight();
        }
        case buttonSitebarArrowDown: {
            return img.arrowdownsquarewhite();
        }
        case by80x15: {
            return img.by80x15();
        }
        case bync80x15: {
            return img.bync80x15();
        }
        case byncnd80x15: {
            return img.byncnd80x15();
        }
        case byncsa80x15: {
            return img.byncsa80x15();
        }
        case bynd80x15: {
            return img.bynd80x15();
        }
        case bysa80x15: {
            return img.bysa80x15();
        }
        case cancel: {
            return img.cancel();
        }
        case clear: {
            return img.clear();
        }
        case copyleft: {
            return img.copyleft();
        }
        case cross: {
            return img.cross();
        }
        case crossDark: {
            return img.crossDark();
        }
        case del: {
            return img.del();
        }
        case emblemImportant: {
            return img.emblemImportant();
        }
        case emblemSystem: {
            return img.emblemSystem();
        }
        case error: {
            return img.error();
        }
        case everybody: {
            return img.everybody();
        }
        case fullscreen: {
            return img.fullscreen();
        }
        case gnuFdl: {
            return img.gnuFdl();
        }
        case groupDefIcon: {
            return img.groupDefIcon();
        }
        case groupHome: {
            return img.groupHome();
        }
        case important: {
            return img.important();
        }
        case info: {
            return img.info();
        }
        case infoLight: {
            return img.infoLight();
        }
        case kuneClose: {
            return img.kuneClose();
        }
        case kuneExternalWindow: {
            return img.kuneExternalWindow();
        }
        case kuneHelpTranslationIcon: {
            return img.kuneHelpTranslationIcon();
        }
        case kuneIcon16: {
            return img.kuneIcon16();
        }
        case kuneLogo16px: {
            return img.kuneLogo16px();
        }
        case kunePreferences: {
            return img.kunePreferences();
        }
        case kuneSearchIco: {
            return img.kuneSearchIco();
        }
        case kuneSearchIcoPush: {
            return img.kuneSearchIcoPush();
        }
        case language: {
            return img.language();
        }
        case nobody: {
            return img.nobody();
        }
        case noCopyleft: {
            return img.noCopyleft();
        }
        case nt: {
            return img.nt();
        }
        case opentriangle: {
            return img.opentriangle();
        }
        case personDef: {
            return img.personDef();
        }
        case remove: {
            return img.remove();
        }
        case rssIcon: {
            return img.rssIcon();
        }
        case searchIco: {
            return img.searchIco();
        }
        case spinKuneThundGreen: {
            return img.spinKuneThundGreen();
        }
        case splitterVertBar: {
            return img.splitterVertBar();
        }
        case star10: {
            return img.star10();
        }
        case star20: {
            return img.star20();
        }
        case star30: {
            return img.star30();
        }
        case star40: {
            return img.star40();
        }
        case star50: {
            return img.star50();
        }
        case star60: {
            return img.star60();
        }
        case star70: {
            return img.star70();
        }
        case star80: {
            return img.star80();
        }
        case star90: {
            return img.star90();
        }
        case starGrey: {
            return img.starGrey();
        }
        case starSmallYellow: {
            return img.starSmallYellow();
        }
        case starVsmallYellow: {
            return img.starVsmallYellow();
        }
        case starYellow: {
            return img.starYellow();
        }
        case step1: {
            return img.step1();
        }
        case step2: {
            return img.step2();
        }
        case step3: {
            return img.step3();
        }
        case step4: {
            return img.step4();
        }
        case step5: {
            return img.step5();
        }
        case themeChoose: {
            return img.themeChoose();
        }
        case triangle: {
            return img.triangle();
        }
        default:
            return null;
        }
    }

    public String getImageHtml(final ImageDescriptor imageDescriptor) {
        return AbstractImagePrototype.create(getImage(imageDescriptor)).getHTML();
    }
}
