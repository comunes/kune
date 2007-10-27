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

package org.ourproject.kune.sitebar.client.services;

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
     * @gwt.resource org/ourproject/kune/sitebar/public/images/anybody.png
     */
    AbstractImagePrototype anybody();

    /**
     * @gwt.resource org/ourproject/kune/sitebar/public/images/copyleft.png
     */
    AbstractImagePrototype copyleft();

    /**
     * @gwt.resource org/ourproject/kune/sitebar/public/images/cross-dark.png
     */
    AbstractImagePrototype crossDark();

    /**
     * @gwt.resource org/ourproject/kune/sitebar/public/images/cross.png
     */
    AbstractImagePrototype cross();

    /**
     * @gwt.resource org/ourproject/kune/sitebar/public/images/emblem-important.png
     */
    AbstractImagePrototype emblemImportant();

    /**
     * @gwt.resource org/ourproject/kune/sitebar/public/images/error.png
     */
    AbstractImagePrototype error();

    /**
     * @gwt.resource org/ourproject/kune/sitebar/public/images/important.png
     */
    AbstractImagePrototype important();

    /**
     * @gwt.resource org/ourproject/kune/sitebar/public/images/info.png
     */
    AbstractImagePrototype info();

    /**
     * @gwt.resource org/ourproject/kune/sitebar/public/images/kune-icon16.png
     */
    AbstractImagePrototype kuneIcon16();

    /**
     * @gwt.resource org/ourproject/kune/sitebar/public/images/kune-logo-16px.png
     */
    AbstractImagePrototype kuneLogo16px();

    /**
     * @gwt.resource org/ourproject/kune/sitebar/public/images/kune-search-ico.png
     */
    AbstractImagePrototype kuneSearchIco();

    /**
     * @gwt.resource org/ourproject/kune/sitebar/public/images/kune-search-ico-push.png
     */
    AbstractImagePrototype kuneSearchIcoPush();

    /**
     * @gwt.resource org/ourproject/kune/sitebar/public/images/language.png
     */
    AbstractImagePrototype language();

    /**
     * @gwt.resource org/ourproject/kune/sitebar/public/images/nobody.png
     */
    AbstractImagePrototype nobody();

    /**
     * @gwt.resource org/ourproject/kune/sitebar/public/images/no-copyleft.png
     */
    AbstractImagePrototype noCopyleft();

    /**
     * @gwt.resource org/ourproject/kune/sitebar/public/images/spin-kune-thund-green.gif
     */
    AbstractImagePrototype spinKuneThundGreen();

}
