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

package org.ourproject.kune.platf.client.utils;

import org.ourproject.kune.workspace.client.workspace.Workspace;

import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;
import com.google.gwt.user.client.ui.Image;

public class PrefetchUtilities {

    public static void preFetchImpImages() {
        String[] imgs = { "images/spin-kune-thund-green.gif", "css/img/button-bg-hard.gif",
                "css/img/button-bg-soft.gif", "css/img/arrow-down-white.gif", "css/img/arrow-right-white.gif",
                "images/lic/bysa80x15.png" };
        for (int i = 0; i < imgs.length; i++) {
            String img = imgs[i];
            Image.prefetch(img);
        }
    }

    public static void doTasksDeferred(final Workspace workspace) {
        DeferredCommand.addCommand(new IncrementalCommand() {
            int i = 0;
            int j = 0;
            int k = 0;

            String[] lic = { "gnu-fdl.gif", "bynd80x15.png", "byncsa80x15.png", "byncnd80x15.png", "bync80x15.png",
                    "by80x15.png", "fal-license.gif" };

            String[] ext = { "basic-dialog/btn-sprite.gif", "form/text-bg.gif", "qtip/close.gif", "s.gif",
                    "shadow-c.png", "shadow-lr.png", "shadow.png", "form/exclamation.gif", "layout/panel-close.gif",
                    "qtip/tip-sprite.gif" };

            String[] extgray = { "basic-dialog/close.gif", "basic-dialog/collapse.gif", "basic-dialog/e-handle.gif",
                    "basic-dialog/hd-sprite.gif", "basic-dialog/s-handle.gif", "basic-dialog/se-handle.gif",
                    "layout/collapse.gif", "layout/expand.gif", "layout/gradient-bg.gif", "layout/panel-close.gif",
                    "layout/panel-title-light-bg.gif", "layout/tab-close-on.gif", "qtip/tip-sprite.gif", "s.gif",
                    "sizer/e-handle-dark.gif", "sizer/s-handle-dark.gif", "sizer/sw-handle-dark.gif",
                    "tabs/tab-sprite.gif" };

            public boolean execute() {

                while (i < lic.length) {
                    String licImg = lic[i];
                    Image.prefetch("images/lic/" + licImg);
                    i++;
                }

                while (j < ext.length) {
                    String extImg = ext[j];
                    Image.prefetch("js/ext/resources/images/default/" + extImg);
                    j++;
                }

                while (k < extgray.length) {
                    String extgrayImg = extgray[k];
                    Image.prefetch("js/ext/resources/images/gray/" + extgrayImg);
                    k++;
                }

                boolean notFinished = i + j + k < lic.length + ext.length + extgray.length;

                boolean finished = !notFinished;

                if (finished) {
                    // In the future maybe: workspace.getLoginComponent()
                }

                return notFinished;
            }
        });

        /*
         * Aero extjs theme
         * Image.prefetch("js/ext/resources/images/aero/basic-dialog/bg-center.gif");
         * Image.prefetch("js/ext/resources/images/aero/basic-dialog/bg-left.gif");
         * Image.prefetch("js/ext/resources/images/aero/basic-dialog/bg-right.gif");
         * Image.prefetch("js/ext/resources/images/aero/basic-dialog/collapse.gif");
         * Image.prefetch("js/ext/resources/images/aero/basic-dialog/hd-sprite.gif");
         * Image.prefetch("js/ext/resources/images/aero/basic-dialog/se-handle.gif");
         * Image.prefetch("js/ext/resources/images/aero/grid/grid-hrow.gif");
         * Image.prefetch("js/ext/resources/images/aero/s.gif");
         * Image.prefetch("js/ext/resources/images/aero/tabs/tab-sprite.gif");
         * Image.prefetch("js/ext/resources/images/aero/tabs/tab-strip-bg.gif");
         */
    }
}
